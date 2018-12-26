package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.Collaborateur;
import com.emard.domain.PrimeCollab;
import com.emard.security.AuthoritiesConstants;
import com.emard.service.PrimeCollabService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PrimeCollab.
 */
@RestController
@RequestMapping("/api")
public class PrimeCollabResource {

    private final Logger log = LoggerFactory.getLogger(PrimeCollabResource.class);

    private static final String ENTITY_NAME = "primeCollab";

    private final PrimeCollabService primeCollabService;

    public PrimeCollabResource(PrimeCollabService primeCollabService) {
        this.primeCollabService = primeCollabService;
    }

    /**
     * POST  /prime-collabs : Create a new primeCollab.
     *
     * @param primeCollab the primeCollab to create
     * @return the ResponseEntity with status 201 (Created) and with body the new primeCollab, or with status 400 (Bad Request) if the primeCollab has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prime-collabs")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public ResponseEntity<PrimeCollab> createPrimeCollab(@Valid @RequestBody PrimeCollab primeCollab) throws URISyntaxException {
        log.debug("REST request to save PrimeCollab : {}", primeCollab);
        if (primeCollab.getId() != null) {
            throw new BadRequestAlertException("A new primeCollab cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrimeCollab result = primeCollabService.save(primeCollab);
        return ResponseEntity.created(new URI("/api/prime-collabs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prime-collabs : Updates an existing primeCollab.
     *
     * @param primeCollab the primeCollab to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated primeCollab,
     * or with status 400 (Bad Request) if the primeCollab is not valid,
     * or with status 500 (Internal Server Error) if the primeCollab couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prime-collabs")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public ResponseEntity<PrimeCollab> updatePrimeCollab(@Valid @RequestBody PrimeCollab primeCollab) throws URISyntaxException {
        log.debug("REST request to update PrimeCollab : {}", primeCollab);
        if (primeCollab.getId() == null) {
            return createPrimeCollab(primeCollab);
        }
        PrimeCollab result = primeCollabService.save(primeCollab);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, primeCollab.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prime-collabs : get all the primeCollabs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of primeCollabs in body
     */
    @GetMapping("/prime-collabs")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public ResponseEntity<List<PrimeCollab>> getAllPrimeCollabs(Pageable pageable) {
        log.debug("REST request to get a page of PrimeCollabs");
        Page<PrimeCollab> page = primeCollabService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prime-collabs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prime-collabs/:id : get the "id" primeCollab.
     *
     * @param id the id of the primeCollab to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the primeCollab, or with status 404 (Not Found)
     */
    @GetMapping("/prime-collabs/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public ResponseEntity<PrimeCollab> getPrimeCollab(@PathVariable Long id) {
        log.debug("REST request to get PrimeCollab : {}", id);
        PrimeCollab primeCollab = primeCollabService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(primeCollab));
    }

    /**
     * DELETE  /prime-collabs/:id : delete the "id" primeCollab.
     *
     * @param id the id of the primeCollab to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prime-collabs/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public ResponseEntity<Void> deletePrimeCollab(@PathVariable Long id) {
        log.debug("REST request to delete PrimeCollab : {}", id);
        PrimeCollab primeCollab = primeCollabService.findOne(id);
        primeCollabService.delete(primeCollab);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * Get all the primes.
     *
     * @return the list of entities
     */
    @GetMapping("/prime-collabsTer/{id}")
    @Transactional(readOnly = true)
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public List<PrimeCollab> findByCollab(@PathVariable Collaborateur id) {
        log.debug("Request to get all Primes");
        return primeCollabService.findByCollaborateur(id);
    }
}
