package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.Sexe;
import com.emard.service.SexeService;
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
 * REST controller for managing Sexe.
 */
@RestController
@RequestMapping("/api")
public class SexeResource {

    private final Logger log = LoggerFactory.getLogger(SexeResource.class);

    private static final String ENTITY_NAME = "sexe";

    private final SexeService sexeService;

    public SexeResource(SexeService sexeService) {
        this.sexeService = sexeService;
    }

    /**
     * POST  /sexes : Create a new sexe.
     *
     * @param sexe the sexe to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sexe, or with status 400 (Bad Request) if the sexe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sexes")
    @Timed
    public ResponseEntity<Sexe> createSexe(@Valid @RequestBody Sexe sexe) throws URISyntaxException {
        log.debug("REST request to save Sexe : {}", sexe);
        if (sexe.getId() != null) {
            throw new BadRequestAlertException("A new sexe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sexe result = sexeService.save(sexe);
        return ResponseEntity.created(new URI("/api/sexes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sexes : Updates an existing sexe.
     *
     * @param sexe the sexe to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sexe,
     * or with status 400 (Bad Request) if the sexe is not valid,
     * or with status 500 (Internal Server Error) if the sexe couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sexes")
    @Timed
    public ResponseEntity<Sexe> updateSexe(@Valid @RequestBody Sexe sexe) throws URISyntaxException {
        log.debug("REST request to update Sexe : {}", sexe);
        if (sexe.getId() == null) {
            return createSexe(sexe);
        }
        Sexe result = sexeService.save(sexe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sexe.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sexes : get all the sexes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sexes in body
     */
    @GetMapping("/sexes")
    @Timed
    public ResponseEntity<List<Sexe>> getAllSexes(Pageable pageable) {
        log.debug("REST request to get a page of Sexes");
        Page<Sexe> page = sexeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sexes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sexes/:id : get the "id" sexe.
     *
     * @param id the id of the sexe to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sexe, or with status 404 (Not Found)
     */
    @GetMapping("/sexes/{id}")
    @Timed
    public ResponseEntity<Sexe> getSexe(@PathVariable Long id) {
        log.debug("REST request to get Sexe : {}", id);
        Sexe sexe = sexeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sexe));
    }

    /**
     * DELETE  /sexes/:id : delete the "id" sexe.
     *
     * @param id the id of the sexe to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sexes/{id}")
    @Timed
    public ResponseEntity<Void> deleteSexe(@PathVariable Long id) {
        log.debug("REST request to delete Sexe : {}", id);
        sexeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
