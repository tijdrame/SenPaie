package com.emard.service;

import com.emard.domain.Nationalite;
import com.emard.repository.NationaliteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Nationalite.
 */
@Service
@Transactional
public class NationaliteService {

    private final Logger log = LoggerFactory.getLogger(NationaliteService.class);

    private final NationaliteRepository nationaliteRepository;

    public NationaliteService(NationaliteRepository nationaliteRepository) {
        this.nationaliteRepository = nationaliteRepository;
    }

    /**
     * Save a nationalite.
     *
     * @param nationalite the entity to save
     * @return the persisted entity
     */
    public Nationalite save(Nationalite nationalite) {
        log.debug("Request to save Nationalite : {}", nationalite);
        return nationaliteRepository.save(nationalite);
    }

    /**
     * Get all the nationalites.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Nationalite> findAll(Pageable pageable) {
        log.debug("Request to get all Nationalites");
        return nationaliteRepository.findAll(pageable);
    }

    /**
     * Get one nationalite by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Nationalite findOne(Long id) {
        log.debug("Request to get Nationalite : {}", id);
        return nationaliteRepository.findOne(id);
    }

    /**
     * Delete the nationalite by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Nationalite : {}", id);
        nationaliteRepository.delete(id);
    }
}
