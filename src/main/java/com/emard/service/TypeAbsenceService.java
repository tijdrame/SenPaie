package com.emard.service;

import com.emard.domain.TypeAbsence;
import com.emard.repository.TypeAbsenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TypeAbsence.
 */
@Service
@Transactional
public class TypeAbsenceService {

    private final Logger log = LoggerFactory.getLogger(TypeAbsenceService.class);

    private final TypeAbsenceRepository typeAbsenceRepository;

    public TypeAbsenceService(TypeAbsenceRepository typeAbsenceRepository) {
        this.typeAbsenceRepository = typeAbsenceRepository;
    }

    /**
     * Save a typeAbsence.
     *
     * @param typeAbsence the entity to save
     * @return the persisted entity
     */
    public TypeAbsence save(TypeAbsence typeAbsence) {
        log.debug("Request to save TypeAbsence : {}", typeAbsence);
        typeAbsence.deleted(false);
        return typeAbsenceRepository.save(typeAbsence);
    }

    /**
     * Get all the typeAbsences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeAbsence> findAll(Pageable pageable) {
        log.debug("Request to get all TypeAbsences");
        return typeAbsenceRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one typeAbsence by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TypeAbsence findOne(Long id) {
        log.debug("Request to get TypeAbsence : {}", id);
        return typeAbsenceRepository.findOne(id);
    }

    /**
     * Delete the typeAbsence by id.
     *
     * @param id the id of the entity
     */
    public void delete(TypeAbsence id) {
        log.debug("Request to delete TypeAbsence : {}", id);
        id.deleted(true);
        typeAbsenceRepository.save(id);
    }
}
