package com.geneai.samples

import com.geneai.infrastructure.adapters.input.rest.dto.PersonDTO
import java.time.LocalDate
import java.util.UUID

trait PersonSample {

    PersonDTO createPersonDTO(Map props = [:]) {
        def defaultProps = [
                firstName : "John",
                lastName  : "Doe",
                birthDate : LocalDate.of(1990, 1, 1),
                birthPlace: "New York"
        ]
        def finalProps = defaultProps + props

        PersonDTO.builder()
                .uuid(finalProps.uuid as UUID)
                .firstName(finalProps.firstName as String)
                .lastName(finalProps.lastName as String)
                .birthDate(finalProps.birthDate as LocalDate)
                .birthPlace(finalProps.birthPlace as String)
                .deathDate(finalProps.deathDate as LocalDate)
                .deathPlace(finalProps.deathPlace as String)
                .fatherUuid(finalProps.fatherUuid as UUID)
                .motherUuid(finalProps.motherUuid as UUID)
                .childrenUuids(finalProps.childrenUuids as List<UUID>)
                .marriages(finalProps.marriages as List<PersonDTO.MarriageDTO>)
                .build()
    }
}
