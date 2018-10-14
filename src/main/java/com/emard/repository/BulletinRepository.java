package com.emard.repository;

import com.emard.domain.Bulletin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Bulletin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BulletinRepository extends JpaRepository<Bulletin, Long> {

    @Query("select bulletin from Bulletin bulletin where bulletin.userCreated.login = ?#{principal.username}")
    List<Bulletin> findByUserCreatedIsCurrentUser();

    @Query("select bulletin from Bulletin bulletin where bulletin.userUpdated.login = ?#{principal.username}")
    List<Bulletin> findByUserUpdatedIsCurrentUser();

    @Query("select bulletin from Bulletin bulletin where bulletin.userDeleted.login = ?#{principal.username}")
    List<Bulletin> findByUserDeletedIsCurrentUser();
    @Query("select distinct bulletin from Bulletin bulletin left join fetch bulletin.remboursements")
    List<Bulletin> findAllWithEagerRelationships();

    @Query("select bulletin from Bulletin bulletin left join fetch bulletin.remboursements where bulletin.id =:id")
    Bulletin findOneWithEagerRelationships(@Param("id") Long id);

    /*@Query("select b from Bulletin b where ((:prenom is null or b.collaborateur.prenom like:prenom) " +
        " and (:nom is null or b.collaborateur.nom like:nom)" +
        " and (:matricule is null or b.collaborateur.matricule like:matricule) and (:theDate is null or b.dateCreated =:theDate) and b.deleted =:deleted)")
    Page<Bulletin> findByCriteres(@Param("prenom") String prenom, @Param("nom") String nom,
                                  @Param("matricule") String matricule, @Param("theDate") LocalDate theDate,
                                  @Param("deleted") Boolean deleted, Pageable pageable);*/


    Page<Bulletin> findByCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndDeletedOrderByDateCreatedDesc(
        String prenom, String nom, String matricule, Boolean deleted, Pageable pageable
    );
}
