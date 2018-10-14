package com.emard.repository;

import com.emard.domain.Absence;
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

}
