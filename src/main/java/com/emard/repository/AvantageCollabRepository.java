package com.emard.repository;

import com.emard.domain.AvantageCollab;
import com.emard.domain.Collaborateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the AvantageCollab entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvantageCollabRepository extends JpaRepository<AvantageCollab, Long> {
    Page<AvantageCollab> findByDeletedFalseOrderByCollaborateur_Nom(Pageable pageable);

    List<AvantageCollab> findByCollaborateurAndDeletedFalse(Collaborateur collaborateur);
}
