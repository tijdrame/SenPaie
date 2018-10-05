package com.emard.repository;

import com.emard.domain.Pret;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Pret entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PretRepository extends JpaRepository<Pret, Long> {

    @Query("select pret from Pret pret where pret.userCreate.login = ?#{principal.username}")
    List<Pret> findByUserCreateIsCurrentUser();

    @Query("select pret from Pret pret where pret.userUpdate.login = ?#{principal.username}")
    List<Pret> findByUserUpdateIsCurrentUser();

    @Query("select pret from Pret pret where pret.userDeleted.login = ?#{principal.username}")
    List<Pret> findByUserDeletedIsCurrentUser();

    Page<Pret> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
