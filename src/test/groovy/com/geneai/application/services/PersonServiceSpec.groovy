package com.geneai.application.services

import com.geneai.samples.PersonSample
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

class PersonServiceSpec extends Specification implements PersonSample {

    def personRepository = new InMemoryPersonRepository()
    def personDtoMapper = new PersonDtoMapperImpl()

    @Subject
    def personService = new PersonService(personRepository, personDtoMapper)

    def "should save a person successfully"() {
        given: "a person DTO"
        def dto = createPersonDTO(firstName: "John", lastName: "Doe")

        when: "saving the person"
        def result = personService.savePerson(dto)

        then: "person is saved and returned with UUID"
        result.uuid != null
        result.firstName == "John"
        result.lastName == "Doe"
        result.birthDate == LocalDate.of(1990, 1, 1)
        result.birthPlace == "New York"

        and: "person exists in repository"
        personRepository.findByUuid(result.uuid).isPresent()
    }

    def "should save a person with parents"() {
        given: "existing parents in repository"
        def father = createPersonDTO(uuid: UUID.randomUUID(), firstName: "James", lastName: "Doe")
        def mother = createPersonDTO(uuid: UUID.randomUUID(), firstName: "Jane", lastName: "Doe")
        
        personService.savePerson(father)
        personService.savePerson(mother)

        and: "a person DTO with parent UUIDs"
        def dto = createPersonDTO(firstName: "Child", lastName: "Doe", fatherUuid: father.uuid, motherUuid: mother.uuid)

        when: "saving the person"
        def result = personService.savePerson(dto)

        then: "person is saved with parents linked"
        [result.fatherUuid, result.motherUuid].containsAll([father.uuid, mother.uuid])

        and: "domain model in repository has parents"
        def savedPerson = personRepository.findByUuid(result.uuid).get()
        savedPerson.parents.size() == 2
        savedPerson.parents.any { it.uuid == father.uuid }
        savedPerson.parents.any { it.uuid == mother.uuid }
    }

    def "should throw exception when trying to add more than 2 parents"() {
        given: "a person already having 2 parents"
        def p1 = createPersonDTO(uuid: UUID.randomUUID(), firstName: "P1")
        def p2 = createPersonDTO(uuid: UUID.randomUUID(), firstName: "P2")
        def p3 = createPersonDTO(uuid: UUID.randomUUID(), firstName: "P3")
        
        personService.savePerson(p1)
        personService.savePerson(p2)
        personService.savePerson(p3)

        def dto = createPersonDTO(firstName: "Child", fatherUuid: p1.uuid, motherUuid: p2.uuid)
        
        def savedDto = personService.savePerson(dto)
        def savedPerson = personRepository.findByUuid(savedDto.uuid).get()

        when: "trying to add third parent directly to domain object"
        def thirdParent = personRepository.findByUuid(p3.uuid).get()
        savedPerson.addParent(thirdParent)

        then: "exception is thrown"
        thrown(IllegalArgumentException)
    }

    def "should fetch all people"() {
        given: "some people in repository"
        personService.savePerson(createPersonDTO(firstName: "Alice"))
        personService.savePerson(createPersonDTO(firstName: "Bob"))

        when: "fetching all people"
        def result = personService.getAllPeople()

        then: "all people are returned"
        result.size() == 2
        result.any { it.firstName == "Alice" }
        result.any { it.firstName == "Bob" }
    }
}
