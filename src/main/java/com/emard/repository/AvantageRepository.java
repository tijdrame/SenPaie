package com.emard.repository;

import com.emard.domain.Avantage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Avantage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvantageRepository extends JpaRepository<Avantage, Long>, JpaSpecificationExecutor<Avantage> {

    Page<Avantage> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
