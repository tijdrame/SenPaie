package com.emard.service;

import com.emard.domain.MoisConcerne;
import com.emard.repository.MoisConcerneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MoisConcerne.
 */
@Service
@Transactional
public class MoisConcerneService {

    private final Logger log = LoggerFactory.getLogger(MoisConcerneService.class);

    private final MoisConcerneRepository moisConcerneRepository;

    public MoisConcerneService(MoisConcerneRepository moisConcerneRepository) {
        this.moisConcerneRepository = moisConcerneRepository;
    }

    /**
     * Save a moisConcerne.
     *
     * @param moisConcerne the entity to save
     * @return the persisted entity
     */
    public MoisConcerne save(MoisConcerne moisConcerne) {
        log.debug("Request to save MoisConcerne : {}", moisConcerne);
        moisConcerne.deleted(false);
        return moisConcerneRepository.save(moisConcerne);
    }

    /**
     * Get all the moisConcernes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MoisConcerne> findAll(Pageable pageable) {
        log.debug("Request to get all MoisConcernes");
        return moisConcerneRepository.findByDeletedFalseOrderByCode(pageable);
    }

    /**
     * Get one moisConcerne by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MoisConcerne findOne(Long id) {
        log.debug("Request to get MoisConcerne : {}", id);
        return moisConcerneRepository.findOne(id);
    }

    /**
     * Delete the moisConcerne by id.
     *
     * @param id the id of the entity
     */
    public void delete(MoisConcerne id) {
        log.debug("Request to delete MoisConcerne : {}", id);
        id.deleted(true);
        moisConcerneRepository.save(id);
    }
}
