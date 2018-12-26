package com.emard.service;

import com.emard.domain.Avantage;
import com.emard.repository.AvantageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Avantage.
 */
@Service
@Transactional
public class AvantageService {

    private final Logger log = LoggerFactory.getLogger(AvantageService.class);

    private final AvantageRepository avantageRepository;

    public AvantageService(AvantageRepository avantageRepository) {
        this.avantageRepository = avantageRepository;
    }

    /**
     * Save a avantage.
     *
     * @param avantage the entity to save
     * @return the persisted entity
     */
    public Avantage save(Avantage avantage) {
        log.debug("Request to save Avantage : {}", avantage);
        avantage.deleted(false);
        return avantageRepository.save(avantage);
    }

    /**
     * Get all the avantages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Avantage> findAll(Pageable pageable) {
        log.debug("Request to get all Avantages");
        return avantageRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one avantage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Avantage findOne(Long id) {
        log.debug("Request to get Avantage : {}", id);
        return avantageRepository.findOne(id);
    }

    /**
     * Delete the avantage by id.
     *
     * @param id the id of the entity
     */
    public void delete(Avantage id) {
        log.debug("Request to delete Avantage : {}", id);
        id.deleted(true);
        avantageRepository.save(id);
    }
}
