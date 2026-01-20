package com.geneai.infrastructure.adapters.input.rest

import com.geneai.BaseIT
import com.geneai.samples.PersonSample
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import com.geneai.infrastructure.adapters.input.rest.dto.PersonDTO

class PersonIntegrationSpec extends BaseIT implements PersonSample {

    @Autowired
    TestRestTemplate restTemplate

    def "should save and retrieve person via REST"() {
        given: "a person DTO"
        def dto = createPersonDTO(firstName: "Integration", lastName: "Test")

        when: "POST /api/people"
        def response = restTemplate.postForEntity("/api/people", dto, PersonDTO)

        then: "response is CREATED"
        response.statusCode == HttpStatus.CREATED
        response.body.uuid != null
        response.body.firstName == "Integration"
        response.body.lastName == "Test"

        when: "GET /api/people"
        def listResponse = restTemplate.getForEntity("/api/people", List)

        then: "response is OK and contains the saved person"
        listResponse.statusCode == HttpStatus.OK
        listResponse.body.size() >= 1
    }

    def "should fetch multiple people via REST"() {
        given: "some people saved in system"
        restTemplate.postForEntity("/api/people", createPersonDTO(firstName: "User1"), PersonDTO)
        restTemplate.postForEntity("/api/people", createPersonDTO(firstName: "User2"), PersonDTO)

        when: "GET /api/people"
        def response = restTemplate.getForEntity("/api/people", PersonDTO[])

        then: "response is OK and contains at least these two people"
        response.statusCode == HttpStatus.OK
        response.body.size() >= 2
        response.body.any { it.firstName == "User1" }
        response.body.any { it.firstName == "User2" }
    }
}
