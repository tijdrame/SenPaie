package com.emard.repository;

import com.emard.domain.Absence;
import com.emard.domain.Collaborateur;
import com.emard.domain.Exercice;
import com.emard.domain.Motif;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Absence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {

    @Query("select absence from Absence absence where absence.userCreated.login = ?#{principal.username}")
    List<Absence> findByUserCreatedIsCurrentUser();

    @Query("select absence from Absence absence where absence.userUpdated.login = ?#{principal.username}")
    List<Absence> findByUserUpdatedIsCurrentUser();

    @Query("select absence from Absence absence where absence.userDeleted.login = ?#{principal.username}")
    List<Absence> findByUserDeletedIsCurrentUser();

    Page<Absence> findByDeletedFalseOrderByDateAbsence(Pageable pageable);

    Page<Absence> findByCollaborateurAndExerciceAndMotifAndDeletedFalse(Collaborateur collaborateur, Exercice exo, Motif motif, Pageable pageable);
}
