package com.emard.service;

import com.emard.domain.StatutDemande;
import com.emard.repository.StatutDemandeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing StatutDemande.
 */
@Service
@Transactional
public class StatutDemandeService {

    private final Logger log = LoggerFactory.getLogger(StatutDemandeService.class);

    private final StatutDemandeRepository statutDemandeRepository;

    public StatutDemandeService(StatutDemandeRepository statutDemandeRepository) {
        this.statutDemandeRepository = statutDemandeRepository;
    }

    /**
     * Save a statutDemande.
     *
     * @param statutDemande the entity to save
     * @return the persisted entity
     */
    public StatutDemande save(StatutDemande statutDemande) {
        log.debug("Request to save StatutDemande : {}", statutDemande);
        statutDemande.deleted(false);
        return statutDemandeRepository.save(statutDemande);
    }

    /**
     * Get all the statutDemandes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StatutDemande> findAll(Pageable pageable) {
        log.debug("Request to get all StatutDemandes");
        return statutDemandeRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one statutDemande by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public StatutDemande findOne(Long id) {
        log.debug("Request to get StatutDemande : {}", id);
        return statutDemandeRepository.findOne(id);
    }

    /**
     * Delete the statutDemande by id.
     *
     * @param statutDemande the id of the entity
     */
    public void delete(StatutDemande statutDemande) {
        log.debug("Request to delete StatutDemande : {}", statutDemande);
        statutDemande.deleted(true);
        statutDemandeRepository.save(statutDemande);
    }

    public StatutDemande findByCode(String code){
        return statutDemandeRepository.findByCodeAndAndDeletedFalse(code);
    }
}
