package com.emard.service;

import com.emard.domain.AvantageCollab;
import com.emard.domain.Collaborateur;
import com.emard.repository.AvantageCollabRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing AvantageCollab.
 */
@Service
@Transactional
public class AvantageCollabService {

    private final Logger log = LoggerFactory.getLogger(AvantageCollabService.class);

    private final AvantageCollabRepository avantageCollabRepository;

    public AvantageCollabService(AvantageCollabRepository avantageCollabRepository) {
        this.avantageCollabRepository = avantageCollabRepository;
    }

    /**
     * Save a avantageCollab.
     *
     * @param avantageCollab the entity to save
     * @return the persisted entity
     */
    public AvantageCollab save(AvantageCollab avantageCollab) {
        log.debug("Request to save AvantageCollab : {}", avantageCollab);
        avantageCollab.deleted(false);
        return avantageCollabRepository.save(avantageCollab);
    }

    /**
     * Get all the avantageCollabs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AvantageCollab> findAll(Pageable pageable) {
        log.debug("Request to get all AvantageCollabs");
        return avantageCollabRepository.findByDeletedFalseOrderByCollaborateur_Nom(pageable);
    }

    /**
     * Get one avantageCollab by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AvantageCollab findOne(Long id) {
        log.debug("Request to get AvantageCollab : {}", id);
        return avantageCollabRepository.findOne(id);
    }

    /**
     * Delete the avantageCollab by id.
     *
     * @param id the id of the entity
     */
    public void delete(AvantageCollab id) {
        log.debug("Request to delete AvantageCollab : {}", id);
        id.deleted(true);
        avantageCollabRepository.save(id);
    }

    @Transactional
    public List<AvantageCollab> findByCollaborateur(Collaborateur collaborateur) {
        return  avantageCollabRepository.findByCollaborateurAndDeletedFalse(collaborateur);
    }
}
