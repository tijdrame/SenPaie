package com.emard.repository;

import com.emard.domain.Regime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Regime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegimeRepository extends JpaRepository<Regime, Long> {

    Page<Regime> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
