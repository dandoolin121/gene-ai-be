package com.geneai.infrastructure.adapters.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MarriageRepository extends JpaRepository<MarriageEntity, Long> {
}
