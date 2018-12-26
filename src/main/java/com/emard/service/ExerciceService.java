package com.emard.service;

import com.emard.domain.Exercice;
import com.emard.repository.ExerciceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * Service Implementation for managing Exercice.
 */
@Service
@Transactional
public class ExerciceService {

    private final Logger log = LoggerFactory.getLogger(ExerciceService.class);

    private final ExerciceRepository exerciceRepository;

    public ExerciceService(ExerciceRepository exerciceRepository) {
        this.exerciceRepository = exerciceRepository;
    }

    /**
     * Save a exercice.
     *
     * @param exercice the entity to save
     * @return the persisted entity
     */
    public Exercice save(Exercice exercice) {
        log.debug("Request to save Exercice : {}", exercice);
        exercice
            .actif(false)
            .deleted(false);
        return exerciceRepository.save(exercice);
    }

    /**
     * Get all the exercices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Exercice> findAll(Pageable pageable) {
        log.debug("Request to get all Exercices");
        return exerciceRepository.findByDeletedFalseOrderByDebutExerciceDesc(pageable);
    }

    /**
     * Get one exercice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Exercice findOne(Long id) {
        log.debug("Request to get Exercice : {}", id);
        return exerciceRepository.findOne(id);
    }

    /**
     * Delete the exercice by id.
     *
     * @param exercice the id of the entity
     */
    public void delete(Exercice exercice) {
        log.debug("Request to delete Exercice : {}", exercice);
        exercice.deleted(true);
        exerciceRepository.save(exercice);
    }

    public Optional<Exercice> findByDebutExercice(Integer debutExercice) {
        return exerciceRepository.findByDebutExerciceAndDeletedFalse(debutExercice);
    }
}
