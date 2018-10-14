package com.emard.repository;

import com.emard.domain.Collaborateur;
import com.emard.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Collaborateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollaborateurRepository extends JpaRepository<Collaborateur, Long> {

    @Query("select collaborateur from Collaborateur collaborateur where collaborateur.userCreated.login = ?#{principal.username}")
    List<Collaborateur> findByUserCreatedIsCurrentUser();

    @Query("select collaborateur from Collaborateur collaborateur where collaborateur.userUpdated.login = ?#{principal.username}")
    List<Collaborateur> findByUserUpdatedIsCurrentUser();

    @Query("select collaborateur from Collaborateur collaborateur where collaborateur.userDeleted.login = ?#{principal.username}")
    List<Collaborateur> findByUserDeletedIsCurrentUser();

    Page<Collaborateur> findByDeletedFalseOrderByNomAscPrenomAsc(Pageable pageable);

    Page<Collaborateur> findByPrenomLikeIgnoreCaseAndNomLikeIgnoreCaseAndTelephoneLikeIgnoreCaseAndDeleted(
        String prenom, String nom, String tel, Boolean deleted, Pageable pageable);

    List<Collaborateur> findByDeletedFalseOrderByNomAscPrenomAsc();

    Collaborateur findByUserCollab(User user);


}
