package com.emard.service;

import com.emard.domain.Collaborateur;
import com.emard.domain.MembreFamille;
import com.emard.repository.MembreFamilleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing MembreFamille.
 */
@Service
@Transactional
public class MembreFamilleService {

    private final Logger log = LoggerFactory.getLogger(MembreFamilleService.class);

    private final MembreFamilleRepository membreFamilleRepository;

    private final UserService userService;
    public MembreFamilleService(MembreFamilleRepository membreFamilleRepository, UserService userService) {
        this.membreFamilleRepository = membreFamilleRepository;
        this.userService = userService;
    }

    /**
     * Save a membreFamille.
     *
     * @param membreFamille the entity to save
     * @return the persisted entity
     */
    public MembreFamille save(MembreFamille membreFamille) {
        log.debug("Request to save MembreFamille : {}", membreFamille);
        membreFamille.deleted(false).user(userService.getUserWithAuthorities().get());
        return membreFamilleRepository.save(membreFamille);
    }

    /**
     * Update a membreFamille.
     *
     * @param membreFamille the entity to save
     * @return the persisted entity
     */
    public MembreFamille update(MembreFamille membreFamille) {
        log.debug("Request to save MembreFamille : {}", membreFamille);
        membreFamille.deleted(false).userUpdate(userService.getUserWithAuthorities().get());
        return membreFamilleRepository.save(membreFamille);
    }

    /**
     * Get all the membreFamilles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MembreFamille> findAll(Pageable pageable) {
        log.debug("Request to get all MembreFamilles");
        return membreFamilleRepository.findByDeletedFalseOrderByNomAscPrenomAsc(pageable);
    }

    /**
     * Get one membreFamille by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MembreFamille findOne(Long id) {
        log.debug("Request to get MembreFamille : {}", id);
        return membreFamilleRepository.findOne(id);
    }

    /**
     * Delete the membreFamille by id.
     *
     * @param membreFamille the id of the entity
     */
    public void delete(MembreFamille membreFamille) {
        log.debug("Request to delete MembreFamille : {}", membreFamille);
        membreFamille.deleted(true).userDeleted(this.userService.getUserWithAuthorities().get());
        membreFamilleRepository.save(membreFamille);
    }

    public List<MembreFamille> findByCollaborateur(Collaborateur collaborateur) {
        return membreFamilleRepository.findByCollaborateur(collaborateur);
    }

    @Transactional(readOnly = true)
    public Page<MembreFamille> findByCriteres(String prenom, String nom, String tel, Boolean deleted, Pageable pageable) {
        log.debug("Request to get all Collaborateurs");
        return membreFamilleRepository.findByPrenomLikeIgnoreCaseAndNomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndDeleted
            (prenom, nom, tel, deleted, pageable);
    }
}
