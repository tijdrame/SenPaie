package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.MembreFamille;
import com.emard.service.MembreFamilleService;
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
 * REST controller for managing MembreFamille.
 */
@RestController
@RequestMapping("/api")
public class MembreFamilleResource {

    private final Logger log = LoggerFactory.getLogger(MembreFamilleResource.class);

    private static final String ENTITY_NAME = "membreFamille";

    private final MembreFamilleService membreFamilleService;

    public MembreFamilleResource(MembreFamilleService membreFamilleService) {
        this.membreFamilleService = membreFamilleService;
    }

    /**
     * POST  /membre-familles : Create a new membreFamille.
     *
     * @param membreFamille the membreFamille to create
     * @return the ResponseEntity with status 201 (Created) and with body the new membreFamille, or with status 400 (Bad Request) if the membreFamille has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/membre-familles")
    @Timed
    public ResponseEntity<MembreFamille> createMembreFamille(@Valid @RequestBody MembreFamille membreFamille) throws URISyntaxException {
        log.debug("REST request to save MembreFamille : {}", membreFamille);
        if (membreFamille.getId() != null) {
            throw new BadRequestAlertException("A new membreFamille cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MembreFamille result = membreFamilleService.save(membreFamille);
        return ResponseEntity.created(new URI("/api/membre-familles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /membre-familles : Updates an existing membreFamille.
     *
     * @param membreFamille the membreFamille to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated membreFamille,
     * or with status 400 (Bad Request) if the membreFamille is not valid,
     * or with status 500 (Internal Server Error) if the membreFamille couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/membre-familles")
    @Timed
    public ResponseEntity<MembreFamille> updateMembreFamille(@Valid @RequestBody MembreFamille membreFamille) throws URISyntaxException {
        log.debug("REST request to update MembreFamille : {}", membreFamille);
        if (membreFamille.getId() == null) {
            return createMembreFamille(membreFamille);
        }
        MembreFamille result = membreFamilleService.save(membreFamille);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, membreFamille.getId().toString()))
            .body(result);
    }

    /**
     * GET  /membre-familles : get all the membreFamilles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of membreFamilles in body
     */
    @GetMapping("/membre-familles")
    @Timed
    public ResponseEntity<List<MembreFamille>> getAllMembreFamilles(Pageable pageable) {
        log.debug("REST request to get a page of MembreFamilles");
        Page<MembreFamille> page = membreFamilleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/membre-familles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /membre-familles/:id : get the "id" membreFamille.
     *
     * @param id the id of the membreFamille to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the membreFamille, or with status 404 (Not Found)
     */
    @GetMapping("/membre-familles/{id}")
    @Timed
    public ResponseEntity<MembreFamille> getMembreFamille(@PathVariable Long id) {
        log.debug("REST request to get MembreFamille : {}", id);
        MembreFamille membreFamille = membreFamilleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(membreFamille));
    }

    /**
     * DELETE  /membre-familles/:id : delete the "id" membreFamille.
     *
     * @param id the id of the membreFamille to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/membre-familles/{id}")
    @Timed
    public ResponseEntity<Void> deleteMembreFamille(@PathVariable Long id) {
        log.debug("REST request to delete MembreFamille : {}", id);
        membreFamilleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
