package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.Exercice;
import com.emard.security.AuthoritiesConstants;
import com.emard.service.ExerciceService;
import com.emard.web.rest.errors.BadRequestAlertException;
import com.emard.web.rest.util.HeaderUtil;
import com.emard.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Exercice.
 */
@RestController
@RequestMapping("/api")
public class ExerciceResource {

    private final Logger log = LoggerFactory.getLogger(ExerciceResource.class);

    private static final String ENTITY_NAME = "exercice";

    private final ExerciceService exerciceService;

    public ExerciceResource(ExerciceService exerciceService) {
        this.exerciceService = exerciceService;
    }

    /**
     * POST  /exercices : Create a new exercice.
     *
     * @param exercice the exercice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new exercice, or with status 400 (Bad Request)
     * if the exercice has already an ID
     *
     */
    @PostMapping("/exercices")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Exercice> createExercice(@Valid @RequestBody Exercice exercice) throws URISyntaxException {
        //exercice.setFinExercice(exercice.getDebutExercice()+1);
        log.debug("REST request to save Exercice : {}", exercice);
        if(this.controleExo(exercice)){
            throw new BadRequestAlertException("L'exercice existe déjà", ENTITY_NAME, " "+ exercice.getDebutExercice());
        }
        if (exercice.getId() != null) {
            throw new BadRequestAlertException("A new exercice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Exercice result = exerciceService.save(exercice);
        return ResponseEntity.created(new URI("/api/exercices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exercices : Updates an existing exercice.
     *
     * @param exercice the exercice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated exercice,
     * or with status 400 (Bad Request) if the exercice is not valid,
     * or with status 500 (Internal Server Error) if the exercice couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exercices")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Exercice> updateExercice(@Valid @RequestBody Exercice exercice) throws URISyntaxException {
        log.debug("REST request to update Exercice : {}", exercice);
        if (exercice.getId() == null) {
            return createExercice(exercice);
        }
        if(this.controleExo(exercice)){
            throw new BadRequestAlertException("A new exercice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Exercice result = exerciceService.save(exercice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, exercice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exercices : get all the exercices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of exercices in body
     */
    @GetMapping("/exercices")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public ResponseEntity<List<Exercice>> getAllExercices(Pageable pageable) {
        log.debug("REST request to get a page of Exercices");
        Page<Exercice> page = exerciceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exercices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /exercices/:id : get the "id" exercice.
     *
     * @param id the id of the exercice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exercice, or with status 404 (Not Found)
     */
    @GetMapping("/exercices/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public ResponseEntity<Exercice> getExercice(@PathVariable Long id) {
        log.debug("REST request to get Exercice : {}", id);
        Exercice exercice = exerciceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(exercice));
    }

    /**
     * DELETE  /exercices/:id : delete the "id" exercice.
     *
     * @param id the id of the exercice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exercices/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Void> deleteExercice(@PathVariable Long id) {
        log.debug("REST request to delete Exercice : {}", id);
        Exercice exercice = exerciceService.findOne(id);
        exerciceService.delete(exercice);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    private Boolean controleExo(Exercice exercice){
        Optional<Exercice> exo = exerciceService.findByDebutExercice(exercice.getDebutExercice());
        if(exo.isPresent())return true;
        return false;
    }
}
