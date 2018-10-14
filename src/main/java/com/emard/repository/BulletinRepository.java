package com.emard.repository;

import com.emard.domain.Bulletin;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

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

}
