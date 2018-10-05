package com.emard.repository;

import com.emard.domain.Statut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Statut entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatutRepository extends JpaRepository<Statut, Long> {

    Page<Statut> findByDeletedFalseOrderByLibelle(Pageable pageable);

    Statut findByCodeAndAndDeletedFalse(String code);
}
