package com.emard.service;

import com.emard.domain.Bulletin;
import com.emard.repository.BulletinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Bulletin.
 */
@Service
@Transactional
public class BulletinService {

    private final Logger log = LoggerFactory.getLogger(BulletinService.class);

    private final BulletinRepository bulletinRepository;

    public BulletinService(BulletinRepository bulletinRepository) {
        this.bulletinRepository = bulletinRepository;
    }

    /**
     * Save a bulletin.
     *
     * @param bulletin the entity to save
     * @return the persisted entity
     */
    public Bulletin save(Bulletin bulletin) {
        log.debug("Request to save Bulletin : {}", bulletin);
        return bulletinRepository.save(bulletin);
    }

    /**
     * Get all the bulletins.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Bulletin> findAll(Pageable pageable) {
        log.debug("Request to get all Bulletins");
        return bulletinRepository.findAll(pageable);
    }

    /**
     * Get one bulletin by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Bulletin findOne(Long id) {
        log.debug("Request to get Bulletin : {}", id);
        return bulletinRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the bulletin by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Bulletin : {}", id);
        bulletinRepository.delete(id);
    }
}
