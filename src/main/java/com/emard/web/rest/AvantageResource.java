package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.Avantage;
import com.emard.security.AuthoritiesConstants;
import com.emard.service.AvantageService;
import com.emard.web.rest.errors.BadRequestAlertException;
import com.emard.web.rest.util.HeaderUtil;
import com.emard.web.rest.util.PaginationUtil;
import com.emard.service.dto.AvantageCriteria;
import com.emard.service.AvantageQueryService;
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
 * REST controller for managing Avantage.
 */
@RestController
@RequestMapping("/api")
public class AvantageResource {

    private final Logger log = LoggerFactory.getLogger(AvantageResource.class);

    private static final String ENTITY_NAME = "avantage";

    private final AvantageService avantageService;

    private final AvantageQueryService avantageQueryService;

    public AvantageResource(AvantageService avantageService, AvantageQueryService avantageQueryService) {
        this.avantageService = avantageService;
        this.avantageQueryService = avantageQueryService;
    }

    /**
     * POST  /avantages : Create a new avantage.
     *
     * @param avantage the avantage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new avantage, or with status 400 (Bad Request) if the avantage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/avantages")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Avantage> createAvantage(@Valid @RequestBody Avantage avantage) throws URISyntaxException {
        log.debug("REST request to save Avantage : {}", avantage);
        if (avantage.getId() != null) {
            throw new BadRequestAlertException("A new avantage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Avantage result = avantageService.save(avantage);
        return ResponseEntity.created(new URI("/api/avantages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /avantages : Updates an existing avantage.
     *
     * @param avantage the avantage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated avantage,
     * or with status 400 (Bad Request) if the avantage is not valid,
     * or with status 500 (Internal Server Error) if the avantage couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/avantages")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Avantage> updateAvantage(@Valid @RequestBody Avantage avantage) throws URISyntaxException {
        log.debug("REST request to update Avantage : {}", avantage);
        if (avantage.getId() == null) {
            return createAvantage(avantage);
        }
        Avantage result = avantageService.save(avantage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, avantage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /avantages : get all the avantages.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of avantages in body
     */
    @GetMapping("/avantages")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<List<Avantage>> getAllAvantages(AvantageCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Avantages by criteria: {}", criteria);
        Page<Avantage> page = avantageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/avantages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /avantages/:id : get the "id" avantage.
     *
     * @param id the id of the avantage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the avantage, or with status 404 (Not Found)
     */
    @GetMapping("/avantages/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Avantage> getAvantage(@PathVariable Long id) {
        log.debug("REST request to get Avantage : {}", id);
        Avantage avantage = avantageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(avantage));
    }

    /**
     * DELETE  /avantages/:id : delete the "id" avantage.
     *
     * @param id the id of the avantage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/avantages/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Void> deleteAvantage(@PathVariable Long id) {
        log.debug("REST request to delete Avantage : {}", id);
        Avantage avantage = avantageService.findOne(id);
        avantageService.delete(avantage);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
