package com.emard.service;

import com.emard.domain.Collaborateur;
import com.emard.domain.PrimeCollab;
import com.emard.repository.PrimeCollabRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing PrimeCollab.
 */
@Service
@Transactional
public class PrimeCollabService {

    private final Logger log = LoggerFactory.getLogger(PrimeCollabService.class);

    private final PrimeCollabRepository primeCollabRepository;

    public PrimeCollabService(PrimeCollabRepository primeCollabRepository) {
        this.primeCollabRepository = primeCollabRepository;
    }

    /**
     * Save a primeCollab.
     *
     * @param primeCollab the entity to save
     * @return the persisted entity
     */
    public PrimeCollab save(PrimeCollab primeCollab) {
        log.debug("Request to save PrimeCollab : {}", primeCollab);
        primeCollab.deleted(false);
        return primeCollabRepository.save(primeCollab);
    }

    /**
     * Get all the primeCollabs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PrimeCollab> findAll(Pageable pageable) {
        log.debug("Request to get all PrimeCollabs");
        return primeCollabRepository.findByDeletedFalseOrderByCollaborateur_Nom(pageable);
    }

    /**
     * Get one primeCollab by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PrimeCollab findOne(Long id) {
        log.debug("Request to get PrimeCollab : {}", id);
        return primeCollabRepository.findOne(id);
    }

    /**
     * Delete the primeCollab by id.
     *
     * @param id the id of the entity
     */
    public void delete(PrimeCollab id) {
        log.debug("Request to delete PrimeCollab : {}", id);
        id.deleted(true);
        primeCollabRepository.save(id);
    }

    @Transactional
    public List<PrimeCollab> findByCollaborateur(Collaborateur collaborateur) {
        return  primeCollabRepository.findByCollaborateurAndDeletedFalse(collaborateur);
    }
}
