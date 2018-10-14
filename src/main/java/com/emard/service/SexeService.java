package com.emard.service;

import com.emard.domain.Sexe;
import com.emard.repository.SexeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Sexe.
 */
@Service
@Transactional
public class SexeService {

    private final Logger log = LoggerFactory.getLogger(SexeService.class);

    private final SexeRepository sexeRepository;

    public SexeService(SexeRepository sexeRepository) {
        this.sexeRepository = sexeRepository;
    }

    /**
     * Save a sexe.
     *
     * @param sexe the entity to save
     * @return the persisted entity
     */
    public Sexe save(Sexe sexe) {
        log.debug("Request to save Sexe : {}", sexe);
        return sexeRepository.save(sexe);
    }

    /**
     * Get all the sexes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Sexe> findAll(Pageable pageable) {
        log.debug("Request to get all Sexes");
        return sexeRepository.findAll(pageable);
    }

    /**
     * Get one sexe by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Sexe findOne(Long id) {
        log.debug("Request to get Sexe : {}", id);
        return sexeRepository.findOne(id);
    }

    /**
     * Delete the sexe by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Sexe : {}", id);
        sexeRepository.delete(id);
    }
}
