package com.emard.repository;

import com.emard.domain.Sexe;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Sexe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SexeRepository extends JpaRepository<Sexe, Long> {

}
