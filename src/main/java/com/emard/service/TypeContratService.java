package com.emard.service;

import com.emard.domain.TypeContrat;
import com.emard.repository.TypeContratRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TypeContrat.
 */
@Service
@Transactional
public class TypeContratService {

    private final Logger log = LoggerFactory.getLogger(TypeContratService.class);

    private final TypeContratRepository typeContratRepository;

    public TypeContratService(TypeContratRepository typeContratRepository) {
        this.typeContratRepository = typeContratRepository;
    }

    /**
     * Save a typeContrat.
     *
     * @param typeContrat the entity to save
     * @return the persisted entity
     */
    public TypeContrat save(TypeContrat typeContrat) {
        log.debug("Request to save TypeContrat : {}", typeContrat);
        return typeContratRepository.save(typeContrat);
    }

    /**
     * Get all the typeContrats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeContrat> findAll(Pageable pageable) {
        log.debug("Request to get all TypeContrats");
        return typeContratRepository.findAll(pageable);
    }

    /**
     * Get one typeContrat by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TypeContrat findOne(Long id) {
        log.debug("Request to get TypeContrat : {}", id);
        return typeContratRepository.findOne(id);
    }

    /**
     * Delete the typeContrat by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeContrat : {}", id);
        typeContratRepository.delete(id);
    }
}
