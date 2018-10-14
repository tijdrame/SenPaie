package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.Absence;
import com.emard.service.AbsenceService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Absence.
 */
@RestController
@RequestMapping("/api")
public class AbsenceResource {

    private final Logger log = LoggerFactory.getLogger(AbsenceResource.class);

    private static final String ENTITY_NAME = "absence";

    private final AbsenceService absenceService;

    public AbsenceResource(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    /**
     * POST  /absences : Create a new absence.
     *
     * @param absence the absence to create
     * @return the ResponseEntity with status 201 (Created) and with body the new absence, or with status 400 (Bad Request) if the absence has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/absences")
    @Timed
    public ResponseEntity<Absence> createAbsence(@Valid @RequestBody Absence absence) throws URISyntaxException {
        log.debug("REST request to save Absence : {}", absence);
        if (absence.getId() != null) {
            throw new BadRequestAlertException("A new absence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Absence result = absenceService.save(absence);
        return ResponseEntity.created(new URI("/api/absences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /absences : Updates an existing absence.
     *
     * @param absence the absence to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated absence,
     * or with status 400 (Bad Request) if the absence is not valid,
     * or with status 500 (Internal Server Error) if the absence couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/absences")
    @Timed
    public ResponseEntity<Absence> updateAbsence(@Valid @RequestBody Absence absence) throws URISyntaxException {
        log.debug("REST request to update Absence : {}", absence);
        if (absence.getId() == null) {
            return createAbsence(absence);
        }
        Absence result = absenceService.save(absence);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, absence.getId().toString()))
            .body(result);
    }

    /**
     * GET  /absences : get all the absences.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of absences in body
     */
    @GetMapping("/absences")
    @Timed
    public ResponseEntity<List<Absence>> getAllAbsences(Pageable pageable) {
        log.debug("REST request to get a page of Absences");
        Page<Absence> page = absenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/absences");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /absences/:id : get the "id" absence.
     *
     * @param id the id of the absence to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the absence, or with status 404 (Not Found)
     */
    @GetMapping("/absences/{id}")
    @Timed
    public ResponseEntity<Absence> getAbsence(@PathVariable Long id) {
        log.debug("REST request to get Absence : {}", id);
        Absence absence = absenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(absence));
    }

    /**
     * DELETE  /absences/:id : delete the "id" absence.
     *
     * @param id the id of the absence to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/absences/{id}")
    @Timed
    public ResponseEntity<Void> deleteAbsence(@PathVariable Long id) {
        log.debug("REST request to delete Absence : {}", id);
        absenceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
