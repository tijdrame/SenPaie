package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.Remboursement;
import com.emard.service.RemboursementService;
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
 * REST controller for managing Remboursement.
 */
@RestController
@RequestMapping("/api")
public class RemboursementResource {

    private final Logger log = LoggerFactory.getLogger(RemboursementResource.class);

    private static final String ENTITY_NAME = "remboursement";

    private final RemboursementService remboursementService;

    public RemboursementResource(RemboursementService remboursementService) {
        this.remboursementService = remboursementService;
    }

    /**
     * POST  /remboursements : Create a new remboursement.
     *
     * @param remboursement the remboursement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new remboursement, or with status 400 (Bad Request) if the remboursement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/remboursements")
    @Timed
    public ResponseEntity<Remboursement> createRemboursement(@Valid @RequestBody Remboursement remboursement) throws URISyntaxException {
        log.debug("REST request to save Remboursement : {}", remboursement);
        if (remboursement.getId() != null) {
            throw new BadRequestAlertException("A new remboursement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Remboursement result = remboursementService.save(remboursement);
        return ResponseEntity.created(new URI("/api/remboursements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /remboursements : Updates an existing remboursement.
     *
     * @param remboursement the remboursement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated remboursement,
     * or with status 400 (Bad Request) if the remboursement is not valid,
     * or with status 500 (Internal Server Error) if the remboursement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/remboursements")
    @Timed
    public ResponseEntity<Remboursement> updateRemboursement(@Valid @RequestBody Remboursement remboursement) throws URISyntaxException {
        log.debug("REST request to update Remboursement : {}", remboursement);
        if (remboursement.getId() == null) {
            return createRemboursement(remboursement);
        }
        Remboursement result = remboursementService.save(remboursement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, remboursement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /remboursements : get all the remboursements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of remboursements in body
     */
    @GetMapping("/remboursements")
    @Timed
    public ResponseEntity<List<Remboursement>> getAllRemboursements(Pageable pageable) {
        log.debug("REST request to get a page of Remboursements");
        Page<Remboursement> page = remboursementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/remboursements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /remboursements/:id : get the "id" remboursement.
     *
     * @param id the id of the remboursement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the remboursement, or with status 404 (Not Found)
     */
    @GetMapping("/remboursements/{id}")
    @Timed
    public ResponseEntity<Remboursement> getRemboursement(@PathVariable Long id) {
        log.debug("REST request to get Remboursement : {}", id);
        Remboursement remboursement = remboursementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(remboursement));
    }

    /**
     * DELETE  /remboursements/:id : delete the "id" remboursement.
     *
     * @param id the id of the remboursement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/remboursements/{id}")
    @Timed
    public ResponseEntity<Void> deleteRemboursement(@PathVariable Long id) {
        log.debug("REST request to delete Remboursement : {}", id);
        remboursementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
