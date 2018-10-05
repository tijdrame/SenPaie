package com.emard.repository;

import com.emard.domain.Motif;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Motif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MotifRepository extends JpaRepository<Motif, Long> {

    Page<Motif> findByDeletedFalseOrderByLibelle(Pageable pageable);

    Motif findByCodeAndDeletedFalse(String code);
}
