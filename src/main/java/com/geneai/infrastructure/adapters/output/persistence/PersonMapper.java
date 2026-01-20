package com.geneai.infrastructure.adapters.output.persistence;

import com.geneai.domain.model.Person;
import com.geneai.domain.model.Marriage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
interface PersonMapper {

    @Mapping(target = "parents", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "marriages", ignore = true)
    Person toDomain(PersonEntity entity);

    @Mapping(target = "parents", qualifiedByName = "toDomainWithoutRecursion")
    @Mapping(target = "children", qualifiedByName = "toDomainWithoutRecursion")
    @Mapping(target = "marriages", qualifiedByName = "toMarriageDomainWithParticipants")
    Person toDomainWithRelationships(PersonEntity entity);

    @Named("toDomainWithoutRecursion")
    @Mapping(target = "parents", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "marriages", ignore = true)
    Person toDomainWithoutRecursion(PersonEntity entity);

    @Named("toMarriageDomainWithParticipants")
    @Mapping(target = "participants", qualifiedByName = "toDomainWithoutRecursion")
    Marriage toMarriageDomainWithParticipants(MarriageEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parents", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "marriages", ignore = true)
    PersonEntity toEntity(Person domain);
}
