package com.geneai.infrastructure.adapters.output.persistence;

import com.geneai.application.ports.output.PersonRepository;
import com.geneai.domain.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
class PersonPersistenceAdapter implements PersonRepository {

    private final PersonJpaRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public List<Person> findAll() {
        log.trace("Finding all Person entities");
        return personRepository.findAll().stream()
                .map(personMapper::toDomainWithRelationships)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Person> findByUuid(UUID uuid) {
        log.trace("Finding Person entity by UUID: {}", uuid);
        return personRepository.findByUuid(uuid)
                .map(personMapper::toDomainWithRelationships);
    }

    @Override
    public List<Person> findAllByUuids(List<UUID> uuids) {
        log.trace("Finding Person entities by UUIDs: {}", uuids);
        return personRepository.findAllByUuidIn(uuids).stream()
                .map(personMapper::toDomainWithRelationships)
                .collect(Collectors.toList());
    }

    @Override
    public Person save(Person person) {
        log.debug("Persisting Person with UUID: {}", person.getUuid());
        PersonEntity entity = personMapper.toEntity(person);
        addParentsToEntity(person, entity);
        PersonEntity savedEntity = personRepository.save(entity);
        return personMapper.toDomainWithRelationships(savedEntity);
    }

    private void addParentsToEntity(Person person, PersonEntity entity) {
        if (person.getParents() == null || person.getParents().isEmpty()) {
            return;
        }

        List<UUID> parentUuids = person.getParents().stream()
                .map(Person::getUuid)
                .collect(Collectors.toList());

        log.trace("Linking parents {} to entity {}", parentUuids, person.getUuid());
        personRepository.findAllByUuidIn(parentUuids)
                .forEach(parentEntity -> entity.getParents().add(parentEntity));
    }
}
