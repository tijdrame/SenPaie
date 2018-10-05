package com.emard.repository;

import com.emard.domain.Nationalite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Nationalite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NationaliteRepository extends JpaRepository<Nationalite, Long> {

    Page<Nationalite> findByDeletedFalseOrderByLibelle(Pageable pageable);

    List<Nationalite> findByDeletedFalseOrderByLibelle();
}
