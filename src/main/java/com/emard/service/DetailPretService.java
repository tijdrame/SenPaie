package com.emard.service;

import com.emard.domain.DetailPret;
import com.emard.repository.DetailPretRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing DetailPret.
 */
@Service
@Transactional
public class DetailPretService {

    private final Logger log = LoggerFactory.getLogger(DetailPretService.class);

    private final DetailPretRepository detailPretRepository;

    public DetailPretService(DetailPretRepository detailPretRepository) {
        this.detailPretRepository = detailPretRepository;
    }

    /**
     * Save a detailPret.
     *
     * @param detailPret the entity to save
     * @return the persisted entity
     */
    public DetailPret save(DetailPret detailPret) {
        log.debug("Request to save DetailPret : {}", detailPret);
        return detailPretRepository.save(detailPret);
    }

    /**
     * Get all the detailPrets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DetailPret> findAll(Pageable pageable) {
        log.debug("Request to get all DetailPrets");
        return detailPretRepository.findAll(pageable);
    }

    /**
     * Get one detailPret by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DetailPret findOne(Long id) {
        log.debug("Request to get DetailPret : {}", id);
        return detailPretRepository.findOne(id);
    }

    /**
     * Delete the detailPret by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DetailPret : {}", id);
        detailPretRepository.delete(id);
    }
}
