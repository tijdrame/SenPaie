package com.emard.service;

import com.emard.domain.Motif;
import com.emard.repository.MotifRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Motif.
 */
@Service
@Transactional
public class MotifService {

    private final Logger log = LoggerFactory.getLogger(MotifService.class);

    private final MotifRepository motifRepository;

    public MotifService(MotifRepository motifRepository) {
        this.motifRepository = motifRepository;
    }

    /**
     * Save a motif.
     *
     * @param motif the entity to save
     * @return the persisted entity
     */
    public Motif save(Motif motif) {
        log.debug("Request to save Motif : {}", motif);
        return motifRepository.save(motif);
    }

    /**
     * Get all the motifs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Motif> findAll(Pageable pageable) {
        log.debug("Request to get all Motifs");
        return motifRepository.findAll(pageable);
    }

    /**
     * Get one motif by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Motif findOne(Long id) {
        log.debug("Request to get Motif : {}", id);
        return motifRepository.findOne(id);
    }

    /**
     * Delete the motif by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Motif : {}", id);
        motifRepository.delete(id);
    }
}
