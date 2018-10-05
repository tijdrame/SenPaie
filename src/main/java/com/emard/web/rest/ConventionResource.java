package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.Convention;
import com.emard.security.AuthoritiesConstants;
import com.emard.service.ConventionService;
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
 * REST controller for managing Convention.
 */
@RestController
@RequestMapping("/api")
public class ConventionResource {

    private final Logger log = LoggerFactory.getLogger(ConventionResource.class);

    private static final String ENTITY_NAME = "convention";

    private final ConventionService conventionService;

    public ConventionResource(ConventionService conventionService) {
        this.conventionService = conventionService;
    }

    /**
     * POST  /conventions : Create a new convention.
     *
     * @param convention the convention to create
     * @return the ResponseEntity with status 201 (Created) and with body the new convention, or with status 400 (Bad Request) if the convention has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/conventions")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Convention> createConvention(@Valid @RequestBody Convention convention) throws URISyntaxException {
        log.debug("REST request to save Convention : {}", convention);
        if (convention.getId() != null) {
            throw new BadRequestAlertException("A new convention cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Convention result = conventionService.save(convention);
        return ResponseEntity.created(new URI("/api/conventions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /conventions : Updates an existing convention.
     *
     * @param convention the convention to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated convention,
     * or with status 400 (Bad Request) if the convention is not valid,
     * or with status 500 (Internal Server Error) if the convention couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/conventions")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Convention> updateConvention(@Valid @RequestBody Convention convention) throws URISyntaxException {
        log.debug("REST request to update Convention : {}", convention);
        if (convention.getId() == null) {
            return createConvention(convention);
        }
        Convention result = conventionService.save(convention);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, convention.getId().toString()))
            .body(result);
    }

    /**
     * GET  /conventions : get all the conventions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of conventions in body
     */
    @GetMapping("/conventions")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public ResponseEntity<List<Convention>> getAllConventions(Pageable pageable) {
        log.debug("REST request to get a page of Conventions");
        Page<Convention> page = conventionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/conventions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /conventions/:id : get the "id" convention.
     *
     * @param id the id of the convention to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the convention, or with status 404 (Not Found)
     */
    @GetMapping("/conventions/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public ResponseEntity<Convention> getConvention(@PathVariable Long id) {
        log.debug("REST request to get Convention : {}", id);
        Convention convention = conventionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(convention));
    }

    /**
     * DELETE  /conventions/:id : delete the "id" convention.
     *
     * @param id the id of the convention to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/conventions/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Void> deleteConvention(@PathVariable Long id) {
        log.debug("REST request to delete Convention : {}", id);
        Convention convention = conventionService.findOne(id);
        conventionService.delete(convention);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
