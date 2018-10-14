package com.emard.repository;

import com.emard.domain.Nationalite;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Nationalite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NationaliteRepository extends JpaRepository<Nationalite, Long> {

}
