package com.emard.repository;

import com.emard.domain.Motif;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Motif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MotifRepository extends JpaRepository<Motif, Long> {

}
