package com.emard.service;

import com.emard.domain.Pret;
import com.emard.repository.PretRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Pret.
 */
@Service
@Transactional
public class PretService {

    private final Logger log = LoggerFactory.getLogger(PretService.class);

    private final PretRepository pretRepository;
    private final UserService userService;

    public PretService(PretRepository pretRepository, UserService userService) {
        this.pretRepository = pretRepository;
        this.userService = userService;
    }

    /**
     * Save a pret.
     *
     * @param pret the entity to save
     * @return the persisted entity
     */
    public Pret save(Pret pret) {
        pret.deleted(false).userCreate(userService.getUserWithAuthorities().get());
        log.debug("Request to save Pret : {}", pret);
        return pretRepository.save(pret);
    }

    /**
     * Save a pret.
     *
     * @param pret the entity to save
     * @return the persisted entity
     */
    public Pret update(Pret pret) {
        pret.deleted(false).userUpdate(userService.getUserWithAuthorities().get());
        log.debug("Request to save Pret : {}", pret);
        return pretRepository.save(pret);
    }

    /**
     * Get all the prets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Pret> findAll(Pageable pageable) {
        log.debug("Request to get all Prets");
        return pretRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one pret by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Pret findOne(Long id) {
        log.debug("Request to get Pret : {}", id);
        return pretRepository.findOne(id);
    }

    /**
     * Delete the pret by id.
     *
     * @param pret the id of the entity
     */
    public void delete(Pret pret) {
        log.debug("Request to delete Pret : {}", pret);
        pret.deleted(true).userDeleted(this.userService.getUserWithAuthorities().get());
        pretRepository.save(pret);
    }
}
