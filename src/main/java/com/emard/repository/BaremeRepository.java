package com.emard.repository;

import com.emard.domain.Bareme;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Bareme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BaremeRepository extends JpaRepository<Bareme, Long> {

}
