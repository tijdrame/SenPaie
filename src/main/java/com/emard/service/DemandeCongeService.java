package com.emard.service;

import com.emard.domain.*;
import com.emard.repository.DemandeCongeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;


/**
 * Service Implementation for managing DemandeConge.
 */
@Service
@Transactional
public class DemandeCongeService {

    private final Logger log = LoggerFactory.getLogger(DemandeCongeService.class);

    private final DemandeCongeRepository demandeCongeRepository;
    private final UserService userService;
    private final CollaborateurService collaborateurService;
    private final StatutDemandeService statutService;
    private final AbsenceService absenceService;
    private final ExerciceService exerciceService;
    private final MotifService motifService;

    public DemandeCongeService(DemandeCongeRepository demandeCongeRepository, UserService userService,
                               CollaborateurService collaborateurService, StatutDemandeService statutService,
                               AbsenceService absenceService, ExerciceService exerciceService,
                               MotifService motifService) {
        this.demandeCongeRepository = demandeCongeRepository;
        this.userService = userService;
        this.collaborateurService = collaborateurService;
        this.statutService = statutService;
        this.absenceService = absenceService;
        this.exerciceService = exerciceService;
        this.motifService = motifService;
    }

    /**
     * Save a demandeConge.
     *
     * @param demandeConge the entity to save
     * @return the persisted entity
     */
    public DemandeConge save(DemandeConge demandeConge) {
        log.debug("Request to save DemandeConge : {}", demandeConge);
        //statut en cours
        StatutDemande statut = statutService.findByCode("ENCOURS");
        demandeConge.deleted(false).userCreated(userService.getUserWithAuthorities().get()).dateCreated(LocalDate.now())
            .collaborateur(collaborateurService.finbByUserCollab(userService.getUserWithAuthorities().get()))
            .statutDG(statut).statutRH(statut);
        return demandeCongeRepository.save(demandeConge);
    }

    /**
     * Update a demandeConge.
     *
     * @param demandeConge the entity to update
     * @return the persisted entity
     */
    public DemandeConge update(DemandeConge demandeConge) {
        log.debug("Request to save DemandeConge : {}", demandeConge);
        demandeConge.userUpdated(userService.getUserWithAuthorities().get());
        if(demandeConge.getStatutDG()!=null && demandeConge.getStatutDG().getCode().equals("VALIDE")&&
            demandeConge.getStatutRH()!=null && demandeConge.getStatutRH().getCode().equals("VALIDE")){
            Motif motif = null;
            if(demandeConge.getTypeAbsence().getCode().equals("CONGE"))
                motif = motifService.findByCode("C");
            else motif = motifService.findByCode("P");
            log.debug("dans if isert ABS<===>"+demandeConge.getDateDebut()+" "+demandeConge.getDateFin()+" "+
                demandeConge.getDateDebut().isEqual(demandeConge.getDateFin()));
            Optional<Exercice> exo = exerciceService.findByDebutExercice(demandeConge.getDateDebut().getYear());
            long numOfDaysBetween = ChronoUnit.DAYS.between(demandeConge.getDateDebut(), demandeConge.getDateFin());
            LocalDate date = demandeConge.getDateDebut();
            for (int i=0; i<=numOfDaysBetween; i++)
            {
                log.debug("dans for isert ABS===>");
                Absence absence = new Absence();
                absence.deleted(false).userCreated(userService.getUserWithAuthorities().get()).dateCreated(LocalDate.now())
                    .collaborateur(demandeConge.getCollaborateur()).dateAbsence(date).exercice(exo.get()).motif(motif);
                absenceService.save(absence);
                date = date.plusDays(1);
            }
        }
        return demandeCongeRepository.save(demandeConge);
    }

    /**
     * Get all the demandeConges.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DemandeConge> findAll(Pageable pageable) {
        log.debug("Request to get all DemandeConges");
        User user = userService.getUserWithAuthorities().get();
        log.debug("DROIT DU USER EN COURS== "+user.getAuthorities());
        Boolean flag = false;

        for(Authority authority: user.getAuthorities()){
            if(authority.getName().equals("ROLE_DG")||authority.getName().equals("ROLE_RH"))
                flag = true;
        }
        if(flag)
            return demandeCongeRepository.findAll(pageable);
        else {
            Collaborateur collaborateur = collaborateurService.finbByUserCollab(userService.getUserWithAuthorities().get());
            return demandeCongeRepository.findByCollaborateurAndDeletedFalseOrderByDateCreated(collaborateur,pageable);
        }
    }

    /**
     * Get one demandeConge by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DemandeConge findOne(Long id) {
        log.debug("Request to get DemandeConge : {}", id);
        return demandeCongeRepository.findOne(id);
    }

    /**
     * Delete the demandeConge by id.
     *
     * @param demandeConge the id of the entity
     */
    public void delete(DemandeConge demandeConge) {
        log.debug("Request to delete DemandeConge : {}", demandeConge);
        demandeConge.deleted(true).userDeleted(userService.getUserWithAuthorities().get());
        demandeCongeRepository.save(demandeConge);
    }

    @Transactional(readOnly = true)
    public Page<DemandeConge> findByCriters(String prenom,  String nom, String tel, Pageable pageable) {

        return demandeCongeRepository.findByCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_TelephoneLikeIgnoreCaseOrderByDateCreatedDesc
            (prenom, nom, tel, pageable);
    }
}
