package com.emard.service;

import com.emard.domain.Remboursement;
import com.emard.repository.RemboursementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Remboursement.
 */
@Service
@Transactional
public class RemboursementService {

    private final Logger log = LoggerFactory.getLogger(RemboursementService.class);

    private final RemboursementRepository remboursementRepository;

    public RemboursementService(RemboursementRepository remboursementRepository) {
        this.remboursementRepository = remboursementRepository;
    }

    /**
     * Save a remboursement.
     *
     * @param remboursement the entity to save
     * @return the persisted entity
     */
    public Remboursement save(Remboursement remboursement) {
        log.debug("Request to save Remboursement : {}", remboursement);
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
    public void delete(Long id) {
        log.debug("Request to delete Remboursement : {}", id);
        remboursementRepository.delete(id);
    }
}
