package com.emard.repository;

import com.emard.domain.MoisConcerne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MoisConcerne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoisConcerneRepository extends JpaRepository<MoisConcerne, Long> {

    Page<MoisConcerne> findByDeletedFalseOrderByLibelle(Pageable pageable);

    Page<MoisConcerne> findByDeletedFalseOrderByCode(Pageable pageable);
}
