package com.emard.repository;

import com.emard.domain.Convention;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Convention entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConventionRepository extends JpaRepository<Convention, Long> {

    Page<Convention> findByDeletedFalse(Pageable pageable);
}
