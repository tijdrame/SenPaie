package com.emard.service;

import com.emard.domain.TypePaiement;
import com.emard.repository.TypePaiementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TypePaiement.
 */
@Service
@Transactional
public class TypePaiementService {

    private final Logger log = LoggerFactory.getLogger(TypePaiementService.class);

    private final TypePaiementRepository typePaiementRepository;

    public TypePaiementService(TypePaiementRepository typePaiementRepository) {
        this.typePaiementRepository = typePaiementRepository;
    }

    /**
     * Save a typePaiement.
     *
     * @param typePaiement the entity to save
     * @return the persisted entity
     */
    public TypePaiement save(TypePaiement typePaiement) {
        log.debug("Request to save TypePaiement : {}", typePaiement);
        typePaiement.deleted(false);
        return typePaiementRepository.save(typePaiement);
    }

    /**
     * Get all the typePaiements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypePaiement> findAll(Pageable pageable) {
        log.debug("Request to get all TypePaiements");
        return typePaiementRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one typePaiement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TypePaiement findOne(Long id) {
        log.debug("Request to get TypePaiement : {}", id);
        return typePaiementRepository.findOne(id);
    }

    /**
     * Delete the typePaiement by id.
     *
     * @param typePaiement the id of the entity
     */
    public void delete(TypePaiement typePaiement) {
        log.debug("Request to delete TypePaiement : {}", typePaiement);
        typePaiement.deleted(true);
        typePaiementRepository.save(typePaiement);
    }
}
