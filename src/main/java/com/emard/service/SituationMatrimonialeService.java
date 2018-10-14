package com.emard.service;

import com.emard.domain.SituationMatrimoniale;
import com.emard.repository.SituationMatrimonialeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SituationMatrimoniale.
 */
@Service
@Transactional
public class SituationMatrimonialeService {

    private final Logger log = LoggerFactory.getLogger(SituationMatrimonialeService.class);

    private final SituationMatrimonialeRepository situationMatrimonialeRepository;

    public SituationMatrimonialeService(SituationMatrimonialeRepository situationMatrimonialeRepository) {
        this.situationMatrimonialeRepository = situationMatrimonialeRepository;
    }

    /**
     * Save a situationMatrimoniale.
     *
     * @param situationMatrimoniale the entity to save
     * @return the persisted entity
     */
    public SituationMatrimoniale save(SituationMatrimoniale situationMatrimoniale) {
        log.debug("Request to save SituationMatrimoniale : {}", situationMatrimoniale);
        return situationMatrimonialeRepository.save(situationMatrimoniale);
    }

    /**
     * Get all the situationMatrimoniales.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SituationMatrimoniale> findAll(Pageable pageable) {
        log.debug("Request to get all SituationMatrimoniales");
        return situationMatrimonialeRepository.findAll(pageable);
    }

    /**
     * Get one situationMatrimoniale by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SituationMatrimoniale findOne(Long id) {
        log.debug("Request to get SituationMatrimoniale : {}", id);
        return situationMatrimonialeRepository.findOne(id);
    }

    /**
     * Delete the situationMatrimoniale by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SituationMatrimoniale : {}", id);
        situationMatrimonialeRepository.delete(id);
    }
}
