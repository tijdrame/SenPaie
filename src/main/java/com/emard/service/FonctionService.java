package com.emard.service;

import com.emard.domain.Fonction;
import com.emard.repository.FonctionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Fonction.
 */
@Service
@Transactional
public class FonctionService {

    private final Logger log = LoggerFactory.getLogger(FonctionService.class);

    private final FonctionRepository fonctionRepository;

    public FonctionService(FonctionRepository fonctionRepository) {
        this.fonctionRepository = fonctionRepository;
    }

    /**
     * Save a fonction.
     *
     * @param fonction the entity to save
     * @return the persisted entity
     */
    public Fonction save(Fonction fonction) {
        log.debug("Request to save Fonction : {}", fonction);
        fonction.deleted(false);
        return fonctionRepository.save(fonction);
    }

    /**
     * Get all the fonctions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Fonction> findAll(Pageable pageable) {
        log.debug("Request to get all Fonctions");
        return fonctionRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one fonction by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Fonction findOne(Long id) {
        log.debug("Request to get Fonction : {}", id);
        return fonctionRepository.findOne(id);
    }

    /**
     * Delete the fonction by id.
     *
     * @param fonction the id of the entity
     */
    public void delete(Fonction fonction) {
        log.debug("Request to delete Fonction : {}", fonction);
        fonction.deleted(true);
        fonctionRepository.save(fonction);
    }
}
