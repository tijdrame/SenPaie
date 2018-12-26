package com.emard.service;

import com.emard.domain.*;
import com.emard.repository.AbsenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


/**
 * Service Implementation for managing Absence.
 */
@Service
@Transactional
public class  AbsenceService {

    private final Logger log = LoggerFactory.getLogger(AbsenceService.class);

    private final AbsenceRepository absenceRepository;
    private final UserService userService;
    private final ExerciceService exerciceService;
    private final CollaborateurService collaborateurService;
    private final MotifService motifService;


    public AbsenceService(AbsenceRepository absenceRepository, UserService userService, MotifService motifService,
                          ExerciceService exerciceService, CollaborateurService collaborateurService) {
        this.absenceRepository = absenceRepository;
        this.userService = userService;
        this.collaborateurService = collaborateurService;
        this.exerciceService = exerciceService;
        this.motifService = motifService;
    }

    /**
     * Save a absence.
     *
     * @param absence the entity to save
     * @return the persisted entity
     */
    public Absence save(Absence absence) {
        log.debug("Request to save Absence : {}", absence);
        absence.deleted(false).userCreated(userService.getUserWithAuthorities().get())
            .dateCreated(LocalDate.now());
        return absenceRepository.save(absence);
    }

    /**
     * Update a absence.
     *
     * @param absence the entity to update
     * @return the persisted entity
     */
    public Absence update(Absence absence) {
        log.debug("Request to save Absence : {}", absence);
        absence.userUpdated(userService.getUserWithAuthorities().get());
        return absenceRepository.save(absence);
    }

    /**
     * Get all the absences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Absence> findAll(Pageable pageable) {
        log.debug("Request to get all Absences");
        return absenceRepository.findByDeletedFalseOrderByDateAbsence(pageable);
    }

    /**
     * Get one absence by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Absence findOne(Long id) {
        log.debug("Request to get Absence : {}", id);
        return absenceRepository.findOne(id);
    }

    /**
     * Delete the absence by abscence.
     *
     * @param absence the id of the entity
     */
    public void delete(Absence absence) {
        log.debug("Request to delete Absence : {}", absence);
        absence.deleted(true).userDeleted(userService.getUserWithAuthorities().get());
        absenceRepository.save(absence);
    }


    public Page<Absence> search(Long idCollab, Long idExercice, Long idMotif, Pageable pageable) {
        Collaborateur collaborateur = collaborateurService.findOne(idCollab);
        Exercice exercice = exerciceService.findOne(idExercice);
        Motif motif = motifService.findOne(idMotif);
        return absenceRepository.findByCollaborateurAndExerciceAndMotifAndDeletedFalse(collaborateur, exercice, motif, pageable);
    }
}
