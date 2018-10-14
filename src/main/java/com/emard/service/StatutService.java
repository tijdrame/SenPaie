package com.emard.service;

import com.emard.domain.Statut;
import com.emard.repository.StatutRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Statut.
 */
@Service
@Transactional
public class StatutService {

    private final Logger log = LoggerFactory.getLogger(StatutService.class);

    private final StatutRepository statutRepository;

    public StatutService(StatutRepository statutRepository) {
        this.statutRepository = statutRepository;
    }

    /**
     * Save a statut.
     *
     * @param statut the entity to save
     * @return the persisted entity
     */
    public Statut save(Statut statut) {
        log.debug("Request to save Statut : {}", statut);
        return statutRepository.save(statut);
    }

    /**
     * Get all the statuts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Statut> findAll(Pageable pageable) {
        log.debug("Request to get all Statuts");
        return statutRepository.findAll(pageable);
    }

    /**
     * Get one statut by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Statut findOne(Long id) {
        log.debug("Request to get Statut : {}", id);
        return statutRepository.findOne(id);
    }

    /**
     * Delete the statut by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Statut : {}", id);
        statutRepository.delete(id);
    }
}
