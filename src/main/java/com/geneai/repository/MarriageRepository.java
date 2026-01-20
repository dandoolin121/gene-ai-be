package com.geneai.repository;

import com.geneai.model.Marriage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarriageRepository extends JpaRepository<Marriage, Long> {
}
