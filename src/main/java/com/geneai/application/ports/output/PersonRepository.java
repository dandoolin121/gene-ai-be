package com.geneai.application.ports.output;

import com.geneai.domain.model.Person;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepository {
    List<Person> findAll();
    Optional<Person> findByUuid(UUID uuid);
    List<Person> findAllByUuids(List<UUID> uuids);
    Person save(Person person);
}
