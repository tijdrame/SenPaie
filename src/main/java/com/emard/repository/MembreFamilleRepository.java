package com.emard.repository;

import com.emard.domain.Collaborateur;
import com.emard.domain.MembreFamille;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the MembreFamille entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembreFamilleRepository extends JpaRepository<MembreFamille, Long> {

    @Query("select membre_famille from MembreFamille membre_famille where membre_famille.user.login = ?#{principal.username}")
    List<MembreFamille> findByUserIsCurrentUser();

    @Query("select membre_famille from MembreFamille membre_famille where membre_famille.userUpdate.login = ?#{principal.username}")
    List<MembreFamille> findByUserUpdateIsCurrentUser();

    @Query("select membre_famille from MembreFamille membre_famille where membre_famille.userDeleted.login = ?#{principal.username}")
    List<MembreFamille> findByUserDeletedIsCurrentUser();

    Page<MembreFamille> findByDeletedFalseOrderByNomAscPrenomAsc(Pageable pageable);

    List<MembreFamille> findByCollaborateur(Collaborateur collaborateur);

    //Page<MembreFamille> findByPrenomLikeIgnoreCaseAndNomLikeIgnoreCaseAndTelephoneLikeIgnoreCaseAndDeleted(String prenom, String nom, String tel, Boolean deleted, Pageable pageable);
    Page<MembreFamille> findByPrenomLikeIgnoreCaseAndNomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndDeleted(String prenom, String nom, String tel, Boolean deleted, Pageable pageable);
}
