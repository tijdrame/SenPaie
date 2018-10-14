package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.SituationMatrimoniale;
import com.emard.service.SituationMatrimonialeService;
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
 * REST controller for managing SituationMatrimoniale.
 */
@RestController
@RequestMapping("/api")
public class SituationMatrimonialeResource {

    private final Logger log = LoggerFactory.getLogger(SituationMatrimonialeResource.class);

    private static final String ENTITY_NAME = "situationMatrimoniale";

    private final SituationMatrimonialeService situationMatrimonialeService;

    public SituationMatrimonialeResource(SituationMatrimonialeService situationMatrimonialeService) {
        this.situationMatrimonialeService = situationMatrimonialeService;
    }

    /**
     * POST  /situation-matrimoniales : Create a new situationMatrimoniale.
     *
     * @param situationMatrimoniale the situationMatrimoniale to create
     * @return the ResponseEntity with status 201 (Created) and with body the new situationMatrimoniale, or with status 400 (Bad Request) if the situationMatrimoniale has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/situation-matrimoniales")
    @Timed
    public ResponseEntity<SituationMatrimoniale> createSituationMatrimoniale(@Valid @RequestBody SituationMatrimoniale situationMatrimoniale) throws URISyntaxException {
        log.debug("REST request to save SituationMatrimoniale : {}", situationMatrimoniale);
        if (situationMatrimoniale.getId() != null) {
            throw new BadRequestAlertException("A new situationMatrimoniale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SituationMatrimoniale result = situationMatrimonialeService.save(situationMatrimoniale);
        return ResponseEntity.created(new URI("/api/situation-matrimoniales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /situation-matrimoniales : Updates an existing situationMatrimoniale.
     *
     * @param situationMatrimoniale the situationMatrimoniale to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated situationMatrimoniale,
     * or with status 400 (Bad Request) if the situationMatrimoniale is not valid,
     * or with status 500 (Internal Server Error) if the situationMatrimoniale couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/situation-matrimoniales")
    @Timed
    public ResponseEntity<SituationMatrimoniale> updateSituationMatrimoniale(@Valid @RequestBody SituationMatrimoniale situationMatrimoniale) throws URISyntaxException {
        log.debug("REST request to update SituationMatrimoniale : {}", situationMatrimoniale);
        if (situationMatrimoniale.getId() == null) {
            return createSituationMatrimoniale(situationMatrimoniale);
        }
        SituationMatrimoniale result = situationMatrimonialeService.save(situationMatrimoniale);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, situationMatrimoniale.getId().toString()))
            .body(result);
    }

    /**
     * GET  /situation-matrimoniales : get all the situationMatrimoniales.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of situationMatrimoniales in body
     */
    @GetMapping("/situation-matrimoniales")
    @Timed
    public ResponseEntity<List<SituationMatrimoniale>> getAllSituationMatrimoniales(Pageable pageable) {
        log.debug("REST request to get a page of SituationMatrimoniales");
        Page<SituationMatrimoniale> page = situationMatrimonialeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/situation-matrimoniales");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /situation-matrimoniales/:id : get the "id" situationMatrimoniale.
     *
     * @param id the id of the situationMatrimoniale to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the situationMatrimoniale, or with status 404 (Not Found)
     */
    @GetMapping("/situation-matrimoniales/{id}")
    @Timed
    public ResponseEntity<SituationMatrimoniale> getSituationMatrimoniale(@PathVariable Long id) {
        log.debug("REST request to get SituationMatrimoniale : {}", id);
        SituationMatrimoniale situationMatrimoniale = situationMatrimonialeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(situationMatrimoniale));
    }

    /**
     * DELETE  /situation-matrimoniales/:id : delete the "id" situationMatrimoniale.
     *
     * @param id the id of the situationMatrimoniale to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/situation-matrimoniales/{id}")
    @Timed
    public ResponseEntity<Void> deleteSituationMatrimoniale(@PathVariable Long id) {
        log.debug("REST request to delete SituationMatrimoniale : {}", id);
        situationMatrimonialeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
