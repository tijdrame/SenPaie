package com.emard.repository;

import com.emard.domain.Bareme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Bareme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BaremeRepository extends JpaRepository<Bareme, Long> {

    Page<Bareme> findByDeletedFalse(Pageable pageable);

    Bareme findByRevenuBrut(Double revenuBrut);
}
