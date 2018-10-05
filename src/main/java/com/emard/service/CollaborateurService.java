package com.emard.service;

import com.emard.domain.Authority;
import com.emard.domain.Collaborateur;
import com.emard.domain.User;
import com.emard.repository.CollaborateurRepository;
import com.emard.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;


/**
 * Service Implementation for managing Collaborateur.
 */
@Service
@Transactional
public class CollaborateurService {

    private final Logger log = LoggerFactory.getLogger(CollaborateurService.class);

    private final CollaborateurRepository collaborateurRepository;
    private final UserService userService;
    private final MailService mailService;

    public CollaborateurService(CollaborateurRepository collaborateurRepository, UserService userService,
                                MailService mailService) {
        this.collaborateurRepository = collaborateurRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    /**
     * Save a collaborateur.
     *
     * @param collaborateur the entity to save
     * @return the persisted entity
     */
    public Collaborateur save(Collaborateur collaborateur) {
        UserDTO user = new UserDTO();
        user.setLogin(collaborateur.getLogin());
        user.setFirstName(collaborateur.getPrenom());
        user.setLastName(collaborateur.getNom());
        user.setEmail(collaborateur.getEmail());
        User us = userService.createUser(user);
        Authority authority = new Authority();
        authority.setName("ROLE_USER");
        us.getAuthorities().add(authority);
        collaborateur.userCollab(us);
        mailService.sendCreationEmail(us);
        log.debug("Request to save Collaborateur : {}", collaborateur);
        collaborateur.deleted(false).userCreated(userService.getUserWithAuthorities().get());
        return collaborateurRepository.save(collaborateur);
    }

    /**
     * Update a collaborateur.
     *
     * @param collaborateur the entity to save
     * @return the persisted entity
     */
    public Collaborateur update(Collaborateur collaborateur) {
        log.debug("Request to save Collaborateur : {}", collaborateur);
        collaborateur.deleted(false).userUpdated(userService.getUserWithAuthorities().get());
        return collaborateurRepository.save(collaborateur);
    }

    /**
     * Get all the collaborateurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Collaborateur> findAll(Pageable pageable) {
        log.debug("Request to get all Collaborateurs");
        return collaborateurRepository.findByDeletedFalseOrderByNomAscPrenomAsc(pageable);
    }

    /**
     * Get one collaborateur by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Collaborateur findOne(Long id) {
        log.debug("Request to get Collaborateur : {}", id);
        return collaborateurRepository.findOne(id);
    }

    /**
     * Delete the collaborateur by id.
     *
     * @param collaborateur the id of the entity
     */
    public void delete(Collaborateur collaborateur) {
        log.debug("Request to delete Collaborateur : {}", collaborateur);

        collaborateur.deleted(true).userDeleted(userService.getUserWithAuthorities().get());
        User user = collaborateur.getUserCollab();
        user.setActivated(false);
        user.setLastModifiedDate(Instant.now());
        user.setLastModifiedBy(userService.getUserWithAuthorities().get().getLogin());
        userService.updateUs(user);
        collaborateurRepository.save(collaborateur);
    }

    public List<Collaborateur> getTheCollabo(){
        return collaborateurRepository.findByDeletedFalseOrderByNomAscPrenomAsc();
    }

    public Collaborateur finbByUserCollab(User user){
        return collaborateurRepository.findByUserCollab(user);
    }

    @Transactional(readOnly = true)
    public Page<Collaborateur> findByCriteres(String prenom, String nom, String tel, Boolean deleted, Pageable pageable) {
        log.debug("Request to get all Collaborateurs");
        return collaborateurRepository.findByPrenomLikeIgnoreCaseAndNomLikeIgnoreCaseAndTelephoneLikeIgnoreCaseAndDeleted(prenom, nom, tel, deleted, pageable);
    }
}
