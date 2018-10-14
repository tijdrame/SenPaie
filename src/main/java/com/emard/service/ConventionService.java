package com.emard.service;

import com.emard.domain.Convention;
import com.emard.repository.ConventionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Convention.
 */
@Service
@Transactional
public class ConventionService {

    private final Logger log = LoggerFactory.getLogger(ConventionService.class);

    private final ConventionRepository conventionRepository;

    public ConventionService(ConventionRepository conventionRepository) {
        this.conventionRepository = conventionRepository;
    }

    /**
     * Save a convention.
     *
     * @param convention the entity to save
     * @return the persisted entity
     */
    public Convention save(Convention convention) {
        log.debug("Request to save Convention : {}", convention);
        return conventionRepository.save(convention);
    }

    /**
     * Get all the conventions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Convention> findAll(Pageable pageable) {
        log.debug("Request to get all Conventions");
        return conventionRepository.findAll(pageable);
    }

    /**
     * Get one convention by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Convention findOne(Long id) {
        log.debug("Request to get Convention : {}", id);
        return conventionRepository.findOne(id);
    }

    /**
     * Delete the convention by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Convention : {}", id);
        conventionRepository.delete(id);
    }
}
