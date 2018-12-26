package com.emard.repository;

import com.emard.domain.Collaborateur;
import com.emard.domain.PrimeCollab;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the PrimeCollab entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrimeCollabRepository extends JpaRepository<PrimeCollab, Long> {
    Page<PrimeCollab> findByDeletedFalseOrderByCollaborateur_Nom(Pageable pageable);

    List<PrimeCollab> findByCollaborateurAndDeletedFalse(Collaborateur collaborateur);
}
