package com.emard.repository;

import com.emard.domain.Fonction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Fonction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FonctionRepository extends JpaRepository<Fonction, Long> {

    Page<Fonction> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
