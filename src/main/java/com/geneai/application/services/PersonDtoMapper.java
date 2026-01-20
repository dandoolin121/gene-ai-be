package com.geneai.application.services;

import com.geneai.domain.model.Marriage;
import com.geneai.domain.model.Person;
import com.geneai.infrastructure.adapters.input.rest.dto.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
interface PersonDtoMapper {

    @Mapping(target = "birthPlace", source = "residence")
    @Mapping(target = "deathPlace", source = "burialPlace")
    @Mapping(target = "fatherUuid", source = "person", qualifiedByName = "mapFatherUuid")
    @Mapping(target = "motherUuid", source = "person", qualifiedByName = "mapMotherUuid")
    @Mapping(target = "childrenUuids", source = "children", qualifiedByName = "mapChildrenUuids")
    @Mapping(target = "marriages", source = "marriages")
    PersonDTO toDTO(Person person);

    @Mapping(target = "spouseUuid", source = "marriage", qualifiedByName = "mapSpouseUuid")
    PersonDTO.MarriageDTO toMarriageDTO(Marriage marriage, @org.mapstruct.Context Person person);

    default List<PersonDTO.MarriageDTO> mapMarriages(java.util.Set<Marriage> marriages, @org.mapstruct.Context Person person) {
        if (marriages == null) {
            return null;
        }
        return marriages.stream()
                .map(m -> toMarriageDTO(m, person))
                .toList();
    }

    @Mapping(target = "residence", source = "birthPlace")
    @Mapping(target = "burialPlace", source = "deathPlace")
    @Mapping(target = "parents", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "marriages", ignore = true)
    Person toDomain(PersonDTO dto);

    @Named("mapFatherUuid")
    default UUID mapFatherUuid(Person person) {
        List<UUID> parentUuids = person.getParents().stream()
                .map(Person::getUuid)
                .toList();
        return !parentUuids.isEmpty() ? parentUuids.get(0) : null;
    }

    @Named("mapMotherUuid")
    default UUID mapMotherUuid(Person person) {
        List<UUID> parentUuids = person.getParents().stream()
                .map(Person::getUuid)
                .toList();
        return parentUuids.size() > 1 ? parentUuids.get(1) : null;
    }

    @Named("mapChildrenUuids")
    default List<UUID> mapChildrenUuids(java.util.Set<Person> children) {
        return children.stream()
                .map(Person::getUuid)
                .toList();
    }

    @Named("mapSpouseUuid")
    default UUID mapSpouseUuid(Marriage marriage, @org.mapstruct.Context Person person) {
        Person spouse = marriage.getSpouseOf(person);
        return spouse != null ? spouse.getUuid() : null;
    }
}
