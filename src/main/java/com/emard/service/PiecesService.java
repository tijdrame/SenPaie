package com.emard.service;

import com.emard.domain.Pieces;
import com.emard.repository.PiecesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Pieces.
 */
@Service
@Transactional
public class PiecesService {

    private final Logger log = LoggerFactory.getLogger(PiecesService.class);

    private final PiecesRepository piecesRepository;

    public PiecesService(PiecesRepository piecesRepository) {
        this.piecesRepository = piecesRepository;
    }

    /**
     * Save a pieces.
     *
     * @param pieces the entity to save
     * @return the persisted entity
     */
    public Pieces save(Pieces pieces) {
        log.debug("Request to save Pieces : {}", pieces);
        return piecesRepository.save(pieces);
    }

    /**
     * Get all the pieces.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Pieces> findAll(Pageable pageable) {
        log.debug("Request to get all Pieces");
        return piecesRepository.findAll(pageable);
    }

    /**
     * Get one pieces by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Pieces findOne(Long id) {
        log.debug("Request to get Pieces : {}", id);
        return piecesRepository.findOne(id);
    }

    /**
     * Delete the pieces by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Pieces : {}", id);
        piecesRepository.delete(id);
    }
}
