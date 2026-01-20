package com.geneai.application.services;

import com.geneai.application.ports.input.PersonUseCase;
import com.geneai.application.ports.output.PersonRepository;
import com.geneai.domain.model.Person;
import com.geneai.infrastructure.adapters.input.rest.dto.PersonDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
class PersonService implements PersonUseCase {

    private final PersonRepository personRepository;
    private final PersonDtoMapper personDtoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PersonDTO> getAllPeople() {
        log.debug("Fetching all people from repository");
        return personRepository.findAll().stream()
                .map(personDtoMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public PersonDTO savePerson(PersonDTO personDTO) {
        log.debug("Saving person to repository: {} {}", personDTO.getFirstName(), personDTO.getLastName());
        Person person = preparePerson(personDTO);
        Person savedPerson = personRepository.save(person);
        log.info("Successfully saved person with UUID: {}", savedPerson.getUuid());
        return personDtoMapper.toDTO(savedPerson);
    }

    private Person preparePerson(PersonDTO dto) {
        Person person = personDtoMapper.toDomain(dto);
        if (person.getUuid() == null) {
            person.setUuid(UUID.randomUUID());
            log.debug("Generated new UUID for person: {}", person.getUuid());
        }
        addParents(person, dto);
        return person;
    }

    private void addParents(Person person, PersonDTO dto) {
        List<UUID> parentUuids = Stream.of(dto.getFatherUuid(), dto.getMotherUuid())
                .filter(Objects::nonNull)
                .toList();

        if (!parentUuids.isEmpty()) {
            log.debug("Adding parents with UUIDs {} to person {}", parentUuids, person.getUuid());
            personRepository.findAllByUuids(parentUuids)
                    .forEach(person::addParent);
        }
    }
}
