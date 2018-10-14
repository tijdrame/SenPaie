package com.emard.repository;

import com.emard.domain.Pieces;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Pieces entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PiecesRepository extends JpaRepository<Pieces, Long> {

    @Query("select pieces from Pieces pieces where pieces.user.login = ?#{principal.username}")
    List<Pieces> findByUserIsCurrentUser();

    @Query("select pieces from Pieces pieces where pieces.userUpdated.login = ?#{principal.username}")
    List<Pieces> findByUserUpdatedIsCurrentUser();

    @Query("select pieces from Pieces pieces where pieces.userDeleted.login = ?#{principal.username}")
    List<Pieces> findByUserDeletedIsCurrentUser();

    Page<Pieces> findByDeletedFalseOrderByLibelle(Pageable pageable);

    Page<Pieces> findByCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndDeleted
        (String prenom, String nom, String matricile, Boolean deleted, Pageable pageable);
}
