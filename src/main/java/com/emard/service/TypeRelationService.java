package com.emard.service;

import com.emard.domain.TypeRelation;
import com.emard.repository.TypeRelationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TypeRelation.
 */
@Service
@Transactional
public class TypeRelationService {

    private final Logger log = LoggerFactory.getLogger(TypeRelationService.class);

    private final TypeRelationRepository typeRelationRepository;

    public TypeRelationService(TypeRelationRepository typeRelationRepository) {
        this.typeRelationRepository = typeRelationRepository;
    }

    /**
     * Save a typeRelation.
     *
     * @param typeRelation the entity to save
     * @return the persisted entity
     */
    public TypeRelation save(TypeRelation typeRelation) {
        log.debug("Request to save TypeRelation : {}", typeRelation);
        typeRelation.deleted(false);
        return typeRelationRepository.save(typeRelation);
    }

    /**
     * Get all the typeRelations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeRelation> findAll(Pageable pageable) {
        log.debug("Request to get all TypeRelations");
        return typeRelationRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one typeRelation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TypeRelation findOne(Long id) {
        log.debug("Request to get TypeRelation : {}", id);
        return typeRelationRepository.findOne(id);
    }

    /**
     * Delete the typeRelation by id.
     *
     * @param typeRelation the id of the entity
     */
    public void delete(TypeRelation typeRelation) {
        log.debug("Request to delete TypeRelation : {}", typeRelation);
        typeRelation.deleted(true);
        typeRelationRepository.save(typeRelation);
    }
}
