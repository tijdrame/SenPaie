package com.emard.service;

import com.emard.domain.*;
import com.emard.repository.BulletinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 * Service Implementation for managing Bulletin.
 */
@Service
@Transactional
public class BulletinService {

    private final Logger log = LoggerFactory.getLogger(BulletinService.class);

    private final BulletinRepository bulletinRepository;
    private final UserService userService;
    private final RemboursementService remboursementService;
    private final DetailPretService detailPretService;

    public BulletinService(BulletinRepository bulletinRepository, UserService userService, RemboursementService remboursementService,
                           DetailPretService detailPretService) {
        this.bulletinRepository = bulletinRepository;
        this.userService = userService;
        this.remboursementService = remboursementService;
        this.detailPretService = detailPretService;
    }

    /**
     * Save a bulletin.
     *
     * @param bulletin the entity to save
     * @return the persisted entity
     */
    public Bulletin save(Bulletin bulletin) {
        log.debug("Request to save Bulletin : {}", bulletin);
        if(bulletin.getRemboursements()!=null){
            for (Remboursement remboursement: bulletin.getRemboursements()) {
                remboursement.isRembourse(true).userUpdated(userService.getUserWithAuthorities().get());
                this.remboursementService.update(remboursement);
                if(!this.pretRembourse(remboursement.getDetailPret())){
                    remboursement.getDetailPret().isRembourse(true).userUpdated(userService.getUserWithAuthorities().get());
                    detailPretService.save(remboursement.getDetailPret());
                }

            }

        }
        bulletin.userCreated(userService.getUserWithAuthorities().get()).dateCreated(LocalDate.now()).deleted(false);
        return bulletinRepository.save(bulletin);
    }

    private Boolean pretRembourse(DetailPret detailPret){
        List<Remboursement> list = remboursementService.findByRemboursement(detailPret);
        Boolean flag = false;
        if (!list.isEmpty()){
            for (Remboursement remboursement: list){
                if(!remboursement.isIsRembourse()) return true;
            }
        }
        return false;
    }

    /**
     * Get all the bulletins.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Bulletin> findAll(Pageable pageable) {
        log.debug("Request to get all Bulletins");
        return bulletinRepository.findAll(pageable);
    }

    /**
     * Get one bulletin by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Bulletin findOne(Long id) {
        log.debug("Request to get Bulletin : {}", id);
        return bulletinRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the bulletin by id.
     *
     * @param bulletin the id of the entity
     */
    public void delete(Bulletin bulletin) {
        log.debug("Request to delete Bulletin : {}", bulletin);
        bulletin.deleted(true).userDeleted(userService.getUserWithAuthorities().get());
        if(bulletin.getRemboursements()!=null){
            for (Remboursement remboursement: bulletin.getRemboursements()) {
                remboursement.isRembourse(false).userUpdated(userService.getUserWithAuthorities().get());
                remboursement.getDetailPret().isRembourse(false).userUpdated(userService.getUserWithAuthorities().get());
                remboursementService.update(remboursement);
                detailPretService.Update(remboursement.getDetailPret());
            }
        }
        bulletinRepository.save(bulletin);
    }

    @Transactional(readOnly = true)
    public Page<Bulletin> findByCriteres(String prenom, String nom, String matricule,
                                         Boolean deleted, MoisConcerne moisConcerne, Exercice exercice, Pageable pageable) {
        log.debug("dans search bull service prenom="+prenom+" nom="+nom+" mat="+matricule+" del="+deleted);
        return bulletinRepository.findByCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndDeletedAndMoisConcerneAndExerciceOrderByDateCreatedDesc
            (prenom, nom, matricule, deleted, moisConcerne, exercice, pageable);
    }

    @Transactional(readOnly = true)
    public List<Bulletin> recapBulletin(Exercice exercice, Pageable pageable) {
        log.debug("dans recap bull service ="+exercice);
        return bulletinRepository.findByExerciceAndDeletedFalseAndCollaborateur_DeletedFalseOrderByCollaborateur_NomAscCollaborateur_PrenomAsc(exercice, pageable);
    }
}
