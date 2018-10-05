package com.emard.repository;

import com.emard.domain.Collaborateur;
import com.emard.domain.DemandeConge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the DemandeConge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeCongeRepository extends JpaRepository<DemandeConge, Long> {

    @Query("select demande_conge from DemandeConge demande_conge where demande_conge.userCreated.login = ?#{principal.username}")
    List<DemandeConge> findByUserCreatedIsCurrentUser();

    @Query("select demande_conge from DemandeConge demande_conge where demande_conge.userUpdated.login = ?#{principal.username}")
    List<DemandeConge> findByUserUpdatedIsCurrentUser();

    @Query("select demande_conge from DemandeConge demande_conge where demande_conge.userDeleted.login = ?#{principal.username}")
    List<DemandeConge> findByUserDeletedIsCurrentUser();

    Page<DemandeConge> findByDeletedFalseOrderByDateCreated(Pageable pageable);

    Page<DemandeConge> findByCollaborateurAndDeletedFalseOrderByDateCreated(Collaborateur collaborateur, Pageable pageable);

    Page<DemandeConge> findByCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_TelephoneLikeIgnoreCaseOrderByDateCreatedDesc
        (String prenom, String nom, String tel, Pageable pageable);
}
