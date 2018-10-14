package com.emard.service;

import com.emard.domain.Absence;
import com.emard.repository.AbsenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Absence.
 */
@Service
@Transactional
public class AbsenceService {

    private final Logger log = LoggerFactory.getLogger(AbsenceService.class);

    private final AbsenceRepository absenceRepository;

    public AbsenceService(AbsenceRepository absenceRepository) {
        this.absenceRepository = absenceRepository;
    }

    /**
     * Save a absence.
     *
     * @param absence the entity to save
     * @return the persisted entity
     */
    public Absence save(Absence absence) {
        log.debug("Request to save Absence : {}", absence);
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
        return absenceRepository.findAll(pageable);
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
     * Delete the absence by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Absence : {}", id);
        absenceRepository.delete(id);
    }
}
