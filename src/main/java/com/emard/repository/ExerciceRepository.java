package com.emard.repository;

import com.emard.domain.Exercice;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Exercice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExerciceRepository extends JpaRepository<Exercice, Long> {

}
