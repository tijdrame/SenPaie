package com.emard.repository;

import com.emard.domain.Structure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Structure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StructureRepository extends JpaRepository<Structure, Long> {

    //Structure findByDenomination(String code);
    List<Structure> findByDeletedFalse();
    Page<Structure> findByDeletedFalse(Pageable pageable);
}
