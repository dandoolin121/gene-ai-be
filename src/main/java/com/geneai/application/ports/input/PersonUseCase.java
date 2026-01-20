package com.geneai.application.ports.input;

import com.geneai.infrastructure.adapters.input.rest.dto.PersonDTO;
import java.util.List;

public interface PersonUseCase {
    List<PersonDTO> getAllPeople();
    PersonDTO savePerson(PersonDTO personDTO);
}
