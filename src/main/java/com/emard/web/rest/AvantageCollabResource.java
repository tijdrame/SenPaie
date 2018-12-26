package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.AvantageCollab;
import com.emard.domain.Collaborateur;
import com.emard.security.AuthoritiesConstants;
import com.emard.service.AvantageCollabService;
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
 * REST controller for managing AvantageCollab.
 */
@RestController
@RequestMapping("/api")
public class AvantageCollabResource {

    private final Logger log = LoggerFactory.getLogger(AvantageCollabResource.class);

    private static final String ENTITY_NAME = "avantageCollab";

    private final AvantageCollabService avantageCollabService;

    public AvantageCollabResource(AvantageCollabService avantageCollabService) {
        this.avantageCollabService = avantageCollabService;
    }

    /**
     * POST  /avantage-collabs : Create a new avantageCollab.
     *
     * @param avantageCollab the avantageCollab to create
     * @return the ResponseEntity with status 201 (Created) and with body the new avantageCollab, or with status 400 (Bad Request) if the avantageCollab has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/avantage-collabs")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public ResponseEntity<AvantageCollab> createAvantageCollab(@Valid @RequestBody AvantageCollab avantageCollab) throws URISyntaxException {
        log.debug("REST request to save AvantageCollab : {}", avantageCollab);
        if (avantageCollab.getId() != null) {
            throw new BadRequestAlertException("A new avantageCollab cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvantageCollab result = avantageCollabService.save(avantageCollab);
        return ResponseEntity.created(new URI("/api/avantage-collabs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /avantage-collabs : Updates an existing avantageCollab.
     *
     * @param avantageCollab the avantageCollab to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated avantageCollab,
     * or with status 400 (Bad Request) if the avantageCollab is not valid,
     * or with status 500 (Internal Server Error) if the avantageCollab couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/avantage-collabs")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public ResponseEntity<AvantageCollab> updateAvantageCollab(@Valid @RequestBody AvantageCollab avantageCollab) throws URISyntaxException {
        log.debug("REST request to update AvantageCollab : {}", avantageCollab);
        if (avantageCollab.getId() == null) {
            return createAvantageCollab(avantageCollab);
        }
        AvantageCollab result = avantageCollabService.save(avantageCollab);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, avantageCollab.getId().toString()))
            .body(result);
    }

    /**
     * GET  /avantage-collabs : get all the avantageCollabs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of avantageCollabs in body
     */
    @GetMapping("/avantage-collabs")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public ResponseEntity<List<AvantageCollab>> getAllAvantageCollabs(Pageable pageable) {
        log.debug("REST request to get a page of AvantageCollabs");
        Page<AvantageCollab> page = avantageCollabService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/avantage-collabs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /avantage-collabs/:id : get the "id" avantageCollab.
     *
     * @param id the id of the avantageCollab to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the avantageCollab, or with status 404 (Not Found)
     */
    @GetMapping("/avantage-collabs/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public ResponseEntity<AvantageCollab> getAvantageCollab(@PathVariable Long id) {
        log.debug("REST request to get AvantageCollab : {}", id);
        AvantageCollab avantageCollab = avantageCollabService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(avantageCollab));
    }

    /**
     * DELETE  /avantage-collabs/:id : delete the "id" avantageCollab.
     *
     * @param id the id of the avantageCollab to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/avantage-collabs/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public ResponseEntity<Void> deleteAvantageCollab(@PathVariable Long id) {
        log.debug("REST request to delete AvantageCollab : {}", id);
        AvantageCollab avantageCollab = avantageCollabService.findOne(id);
        avantageCollabService.delete(avantageCollab);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * Get all the Avantages.
     *
     * @return the list of entities
     */
    @GetMapping("/avantage-collabsTer/{id}")
    @Transactional(readOnly = true)
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public List<AvantageCollab> findByCollab(@PathVariable Collaborateur id) {
        log.debug("Request to get all Avantages by Collab");
        return avantageCollabService.findByCollaborateur(id);
    }
}
