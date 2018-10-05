package com.emard.service;

import com.emard.domain.Collaborateur;
import com.emard.domain.Remboursement;
import com.emard.repository.RemboursementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


/**
 * Service Implementation for managing Remboursement.
 */
@Service
@Transactional
public class RemboursementService {

    private final Logger log = LoggerFactory.getLogger(RemboursementService.class);

    private final RemboursementRepository remboursementRepository;

    private final UserService userService;
    private final EntityManager em;

    public RemboursementService(RemboursementRepository remboursementRepository, UserService userService, EntityManager em) {
        this.remboursementRepository = remboursementRepository;
        this.userService = userService;
        this.em = em;
    }

    /**
     * Save a remboursement.
     *
     * @param remboursement the entity to save
     * @return the persisted entity
     */
    public Remboursement save(Remboursement remboursement) {
        log.debug("Request to save Remboursement : {}", remboursement);
        remboursement.userCreated(userService.getUserWithAuthorities().get()).deleted(false);
        return remboursementRepository.save(remboursement);
    }

    /**
     * Save a remboursement.
     *
     * @param remboursement the entity to save
     * @return the persisted entity
     */
    public Remboursement update(Remboursement remboursement) {
        log.debug("Request to save Remboursement : {}", remboursement);
        remboursement.userUpdated(userService.getUserWithAuthorities().get()).deleted(false);
        return remboursementRepository.save(remboursement);
    }

    /**
     * Get all the remboursements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Remboursement> findAll(Pageable pageable) {
        log.debug("Request to get all Remboursements");
        return remboursementRepository.findAll(pageable);
    }

    /**
     * Get one remboursement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Remboursement findOne(Long id) {
        log.debug("Request to get Remboursement : {}", id);
        return remboursementRepository.findOne(id);
    }

    /**
     * Delete the remboursement by id.
     *
     * @param id the id of the entity
     */
    public void delete(Remboursement id) {
        log.debug("Request to delete Remboursement : {}", id);
        id.deleted(true);
        remboursementRepository.save(id);
    }

    public List<Remboursement> getRemboursementByCollaborateur(Collaborateur collaborateur){
        log.debug("Request get remboursement by collab : {}", collaborateur);
        //List<Remboursement> list = new ArrayList<>();
        /*StringBuilder hql = new StringBuilder();
        hql.append("from Remboursement r");
        hql.append(" inner join fetch r.detailPret d");
        hql.append(" inner join fetch d.collaborateur c");
        hql.append(" c =:paramCollab");

        List<Remboursement> list = em.createQuery(hql.toString()).setParameter("paramCollab", collaborateur).getResultList();*/
        //log.debug(list+"list remb");
        //return list;
        return remboursementRepository.findByDetailPret_CollaborateurAndDeletedFalseAndIsRembourseFalse(collaborateur);
    }
}
