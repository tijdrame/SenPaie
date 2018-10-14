package com.emard.repository;

import com.emard.domain.Remboursement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
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

}
