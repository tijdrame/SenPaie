package com.emard.service;

import com.emard.domain.MembreFamille;
import com.emard.repository.MembreFamilleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MembreFamille.
 */
@Service
@Transactional
public class MembreFamilleService {

    private final Logger log = LoggerFactory.getLogger(MembreFamilleService.class);

    private final MembreFamilleRepository membreFamilleRepository;

    public MembreFamilleService(MembreFamilleRepository membreFamilleRepository) {
        this.membreFamilleRepository = membreFamilleRepository;
    }

    /**
     * Save a membreFamille.
     *
     * @param membreFamille the entity to save
     * @return the persisted entity
     */
    public MembreFamille save(MembreFamille membreFamille) {
        log.debug("Request to save MembreFamille : {}", membreFamille);
        return membreFamilleRepository.save(membreFamille);
    }

    /**
     * Get all the membreFamilles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MembreFamille> findAll(Pageable pageable) {
        log.debug("Request to get all MembreFamilles");
        return membreFamilleRepository.findAll(pageable);
    }

    /**
     * Get one membreFamille by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MembreFamille findOne(Long id) {
        log.debug("Request to get MembreFamille : {}", id);
        return membreFamilleRepository.findOne(id);
    }

    /**
     * Delete the membreFamille by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MembreFamille : {}", id);
        membreFamilleRepository.delete(id);
    }
}
