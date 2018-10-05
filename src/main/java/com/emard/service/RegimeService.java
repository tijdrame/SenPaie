package com.emard.service;

import com.emard.domain.Regime;
import com.emard.repository.RegimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Regime.
 */
@Service
@Transactional
public class RegimeService {

    private final Logger log = LoggerFactory.getLogger(RegimeService.class);

    private final RegimeRepository regimeRepository;

    public RegimeService(RegimeRepository regimeRepository) {
        this.regimeRepository = regimeRepository;
    }

    /**
     * Save a regime.
     *
     * @param regime the entity to save
     * @return the persisted entity
     */
    public Regime save(Regime regime) {
        log.debug("Request to save Regime : {}", regime);
        regime.deleted(false);
        return regimeRepository.save(regime);
    }

    /**
     * Get all the regimes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Regime> findAll(Pageable pageable) {
        log.debug("Request to get all Regimes");
        return regimeRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one regime by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Regime findOne(Long id) {
        log.debug("Request to get Regime : {}", id);
        return regimeRepository.findOne(id);
    }

    /**
     * Delete the regime by id.
     *
     * @param regime the id of the entity
     */
    public void delete(Regime regime) {
        log.debug("Request to delete Regime : {}", regime);
        regime.deleted(true);
        regimeRepository.save(regime);
    }
}
