package com.geneai.application.services

import com.geneai.application.ports.output.PersonRepository
import com.geneai.domain.model.Person

import java.util.concurrent.ConcurrentHashMap

class InMemoryPersonRepository implements PersonRepository {
    private final Map<UUID, Person> storage = new ConcurrentHashMap<>()

    @Override
    List<Person> findAll() {
        return new ArrayList<>(storage.values())
    }

    @Override
    Optional<Person> findByUuid(UUID uuid) {
        return Optional.ofNullable(storage.get(uuid))
    }

    @Override
    List<Person> findAllByUuids(List<UUID> uuids) {
        return storage.values().findAll { uuids.contains(it.uuid) }
    }

    @Override
    Person save(Person person) {
        storage.put(person.uuid, person)
        return person
    }
}
