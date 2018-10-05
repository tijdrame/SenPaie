package com.emard.repository;

import com.emard.domain.TypeRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TypeRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeRelationRepository extends JpaRepository<TypeRelation, Long> {

    Page<TypeRelation> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
