package com.emard.repository;

import com.emard.domain.MembreFamille;
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

}
