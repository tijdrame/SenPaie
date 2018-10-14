package com.emard.repository;

import com.emard.domain.DetailPret;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the DetailPret entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailPretRepository extends JpaRepository<DetailPret, Long> {

    @Query("select detail_pret from DetailPret detail_pret where detail_pret.userCreated.login = ?#{principal.username}")
    List<DetailPret> findByUserCreatedIsCurrentUser();

    @Query("select detail_pret from DetailPret detail_pret where detail_pret.userUpdated.login = ?#{principal.username}")
    List<DetailPret> findByUserUpdatedIsCurrentUser();

    @Query("select detail_pret from DetailPret detail_pret where detail_pret.userDeleted.login = ?#{principal.username}")
    List<DetailPret> findByUserDeletedIsCurrentUser();

}
