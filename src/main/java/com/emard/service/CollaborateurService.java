package com.emard.service;

import com.emard.domain.Collaborateur;
import com.emard.repository.CollaborateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Collaborateur.
 */
@Service
@Transactional
public class CollaborateurService {

    private final Logger log = LoggerFactory.getLogger(CollaborateurService.class);

    private final CollaborateurRepository collaborateurRepository;

    public CollaborateurService(CollaborateurRepository collaborateurRepository) {
        this.collaborateurRepository = collaborateurRepository;
    }

    /**
     * Save a collaborateur.
     *
     * @param collaborateur the entity to save
     * @return the persisted entity
     */
    public Collaborateur save(Collaborateur collaborateur) {
        log.debug("Request to save Collaborateur : {}", collaborateur);
        return collaborateurRepository.save(collaborateur);
    }

    /**
     * Get all the collaborateurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Collaborateur> findAll(Pageable pageable) {
        log.debug("Request to get all Collaborateurs");
        return collaborateurRepository.findAll(pageable);
    }

    /**
     * Get one collaborateur by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Collaborateur findOne(Long id) {
        log.debug("Request to get Collaborateur : {}", id);
        return collaborateurRepository.findOne(id);
    }

    /**
     * Delete the collaborateur by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Collaborateur : {}", id);
        collaborateurRepository.delete(id);
    }
}
