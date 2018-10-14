package com.emard.repository;

import com.emard.domain.Collaborateur;
import com.emard.domain.DetailPret;
import com.emard.domain.Remboursement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the Remboursement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RemboursementRepository extends JpaRepository<Remboursement, Long> {

    @Query("select remboursement from Remboursement remboursement where remboursement.userCreated.login = ?#{principal.username}")
    List<Remboursement> findByUserCreatedIsCurrentUser();

    @Query("select remboursement from Remboursement remboursement where remboursement.userUpdated.login = ?#{principal.username}")
    List<Remboursement> findByUserUpdatedIsCurrentUser();

    //@Query("select r from Remboursement r inner join r.detailPret.collaborateur c where c= :collaborateur")
    List<Remboursement> findByDetailPret_CollaborateurAndDeletedFalseAndIsRembourseFalse(Collaborateur collaborateur);

    List<Remboursement> findByDetailPret(DetailPret detailPret);

    Page<Remboursement> findByDetailPret_Collaborateur_PrenomLikeIgnoreCaseAndDetailPret_Collaborateur_NomLikeIgnoreCaseAndDetailPret_Collaborateur_MatriculeLikeIgnoreCase
        (String prenom, String nom, String matricule, Pageable pageable);
}
