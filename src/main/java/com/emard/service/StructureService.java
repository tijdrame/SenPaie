package com.emard.service;

import com.emard.domain.Structure;
import com.emard.repository.StructureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing Structure.
 */
@Service
@Transactional
public class StructureService {

    private final Logger log = LoggerFactory.getLogger(StructureService.class);

    private final StructureRepository structureRepository;

    public StructureService(StructureRepository structureRepository) {
        this.structureRepository = structureRepository;
    }

    /**
     * Save a structure.
     *
     * @param structure the entity to save
     * @return the persisted entity
     */
    public Structure save(Structure structure) {
        log.debug("Request to save Structure : {}", structure);
        structure.deleted(false);
        return structureRepository.save(structure);
    }

    /**
     * Get all the structures.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Structure> findAll(Pageable pageable) {
        log.debug("Request to get all Structures");
        return structureRepository.findByDeletedFalse(pageable);
    }

    /**
     * Get one structure by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Structure findOne(Long id) {
        log.debug("Request to get Structure : {}", id);
        return structureRepository.findOne(id);
    }

    /**
     * Delete the structure by id.
     *
     * @param id the id of the entity
     */
    public void delete(Structure id) {
        log.debug("Request to delete Structure : {}", id);
        id.deleted(true);
        structureRepository.save(id);
    }

    public Structure findByDenomination(String code) {
        List<Structure> list = structureRepository.findByDeletedFalse();
        return list.get(0);
    }
}
