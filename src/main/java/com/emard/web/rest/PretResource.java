package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.Pret;
import com.emard.service.PretService;
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
 * REST controller for managing Pret.
 */
@RestController
@RequestMapping("/api")
public class PretResource {

    private final Logger log = LoggerFactory.getLogger(PretResource.class);

    private static final String ENTITY_NAME = "pret";

    private final PretService pretService;

    public PretResource(PretService pretService) {
        this.pretService = pretService;
    }

    /**
     * POST  /prets : Create a new pret.
     *
     * @param pret the pret to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pret, or with status 400 (Bad Request) if the pret has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prets")
    @Timed
    public ResponseEntity<Pret> createPret(@Valid @RequestBody Pret pret) throws URISyntaxException {
        log.debug("REST request to save Pret : {}", pret);
        if (pret.getId() != null) {
            throw new BadRequestAlertException("A new pret cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pret result = pretService.save(pret);
        return ResponseEntity.created(new URI("/api/prets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prets : Updates an existing pret.
     *
     * @param pret the pret to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pret,
     * or with status 400 (Bad Request) if the pret is not valid,
     * or with status 500 (Internal Server Error) if the pret couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prets")
    @Timed
    public ResponseEntity<Pret> updatePret(@Valid @RequestBody Pret pret) throws URISyntaxException {
        log.debug("REST request to update Pret : {}", pret);
        if (pret.getId() == null) {
            return createPret(pret);
        }
        Pret result = pretService.save(pret);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pret.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prets : get all the prets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of prets in body
     */
    @GetMapping("/prets")
    @Timed
    public ResponseEntity<List<Pret>> getAllPrets(Pageable pageable) {
        log.debug("REST request to get a page of Prets");
        Page<Pret> page = pretService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prets/:id : get the "id" pret.
     *
     * @param id the id of the pret to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pret, or with status 404 (Not Found)
     */
    @GetMapping("/prets/{id}")
    @Timed
    public ResponseEntity<Pret> getPret(@PathVariable Long id) {
        log.debug("REST request to get Pret : {}", id);
        Pret pret = pretService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pret));
    }

    /**
     * DELETE  /prets/:id : delete the "id" pret.
     *
     * @param id the id of the pret to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prets/{id}")
    @Timed
    public ResponseEntity<Void> deletePret(@PathVariable Long id) {
        log.debug("REST request to delete Pret : {}", id);
        pretService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
