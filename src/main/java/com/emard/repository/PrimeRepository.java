package com.emard.repository;

import com.emard.domain.Prime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Prime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrimeRepository extends JpaRepository<Prime, Long> {

    Page<Prime> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
