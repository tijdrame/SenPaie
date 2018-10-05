package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.Motif;
import com.emard.security.AuthoritiesConstants;
import com.emard.service.MotifService;
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
 * REST controller for managing Motif.
 */
@RestController
@RequestMapping("/api")
public class MotifResource {

    private final Logger log = LoggerFactory.getLogger(MotifResource.class);

    private static final String ENTITY_NAME = "motif";

    private final MotifService motifService;

    public MotifResource(MotifService motifService) {
        this.motifService = motifService;
    }

    /**
     * POST  /motifs : Create a new motif.
     *
     * @param motif the motif to create
     * @return the ResponseEntity with status 201 (Created) and with body the new motif, or with status 400 (Bad Request) if the motif has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/motifs")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Motif> createMotif(@Valid @RequestBody Motif motif) throws URISyntaxException {
        log.debug("REST request to save Motif : {}", motif);
        if (motif.getId() != null) {
            throw new BadRequestAlertException("A new motif cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Motif result = motifService.save(motif);
        return ResponseEntity.created(new URI("/api/motifs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /motifs : Updates an existing motif.
     *
     * @param motif the motif to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated motif,
     * or with status 400 (Bad Request) if the motif is not valid,
     * or with status 500 (Internal Server Error) if the motif couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/motifs")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Motif> updateMotif(@Valid @RequestBody Motif motif) throws URISyntaxException {
        log.debug("REST request to update Motif : {}", motif);
        if (motif.getId() == null) {
            return createMotif(motif);
        }
        Motif result = motifService.save(motif);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, motif.getId().toString()))
            .body(result);
    }

    /**
     * GET  /motifs : get all the motifs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of motifs in body
     */
    @GetMapping("/motifs")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG, AuthoritiesConstants.USER})
    public ResponseEntity<List<Motif>> getAllMotifs(Pageable pageable) {
        log.debug("REST request to get a page of Motifs");
        Page<Motif> page = motifService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/motifs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /motifs/:id : get the "id" motif.
     *
     * @param id the id of the motif to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the motif, or with status 404 (Not Found)
     */
    @GetMapping("/motifs/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG, AuthoritiesConstants.USER})
    public ResponseEntity<Motif> getMotif(@PathVariable Long id) {
        log.debug("REST request to get Motif : {}", id);
        Motif motif = motifService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(motif));
    }

    /**
     * DELETE  /motifs/:id : delete the "id" motif.
     *
     * @param id the id of the motif to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/motifs/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Void> deleteMotif(@PathVariable Long id) {
        log.debug("REST request to delete Motif : {}", id);
        Motif motif = motifService.findOne(id);
        motifService.delete(motif);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
