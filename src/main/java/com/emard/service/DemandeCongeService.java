package com.emard.service;

import com.emard.domain.DemandeConge;
import com.emard.repository.DemandeCongeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing DemandeConge.
 */
@Service
@Transactional
public class DemandeCongeService {

    private final Logger log = LoggerFactory.getLogger(DemandeCongeService.class);

    private final DemandeCongeRepository demandeCongeRepository;

    public DemandeCongeService(DemandeCongeRepository demandeCongeRepository) {
        this.demandeCongeRepository = demandeCongeRepository;
    }

    /**
     * Save a demandeConge.
     *
     * @param demandeConge the entity to save
     * @return the persisted entity
     */
    public DemandeConge save(DemandeConge demandeConge) {
        log.debug("Request to save DemandeConge : {}", demandeConge);
        return demandeCongeRepository.save(demandeConge);
    }

    /**
     * Get all the demandeConges.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DemandeConge> findAll(Pageable pageable) {
        log.debug("Request to get all DemandeConges");
        return demandeCongeRepository.findAll(pageable);
    }

    /**
     * Get one demandeConge by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DemandeConge findOne(Long id) {
        log.debug("Request to get DemandeConge : {}", id);
        return demandeCongeRepository.findOne(id);
    }

    /**
     * Delete the demandeConge by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DemandeConge : {}", id);
        demandeCongeRepository.delete(id);
    }
}
