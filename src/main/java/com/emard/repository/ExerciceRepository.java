package com.emard.repository;

import com.emard.domain.Exercice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the Exercice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExerciceRepository extends JpaRepository<Exercice, Long> {

    Optional<Exercice> findByDebutExerciceAndDeletedFalse(Integer debutExercice);

    Page<Exercice> findByDeletedFalseOrderByDebutExercice(Pageable pageable);
}
