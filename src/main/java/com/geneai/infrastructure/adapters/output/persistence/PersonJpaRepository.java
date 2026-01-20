package com.geneai.infrastructure.adapters.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
interface PersonJpaRepository extends JpaRepository<PersonEntity, Long> {
    Optional<PersonEntity> findByUuid(UUID uuid);
    List<PersonEntity> findAllByUuidIn(Collection<UUID> uuids);
}
