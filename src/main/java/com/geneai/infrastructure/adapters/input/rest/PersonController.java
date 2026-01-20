package com.geneai.infrastructure.adapters.input.rest;

import com.geneai.application.ports.input.PersonUseCase;
import com.geneai.infrastructure.adapters.input.rest.dto.PersonDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/people")
@RequiredArgsConstructor
@Slf4j
public class PersonController {

    private final PersonUseCase personUseCase;

    @GetMapping
    public List<PersonDTO> getAllPeople() {
        log.info("REST request to get all people");
        return personUseCase.getAllPeople();
    }

    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO) {
        log.info("REST request to save person: {}", personDTO.getFirstName() + " " + personDTO.getLastName());
        return new ResponseEntity<>(personUseCase.savePerson(personDTO), HttpStatus.CREATED);
    }
}
