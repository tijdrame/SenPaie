package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.Regime;
import com.emard.security.AuthoritiesConstants;
import com.emard.service.RegimeService;
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
 * REST controller for managing Regime.
 */
@RestController
@RequestMapping("/api")
public class RegimeResource {

    private final Logger log = LoggerFactory.getLogger(RegimeResource.class);

    private static final String ENTITY_NAME = "regime";

    private final RegimeService regimeService;

    public RegimeResource(RegimeService regimeService) {
        this.regimeService = regimeService;
    }

    /**
     * POST  /regimes : Create a new regime.
     *
     * @param regime the regime to create
     * @return the ResponseEntity with status 201 (Created) and with body the new regime, or with status 400 (Bad Request) if the regime has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/regimes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Regime> createRegime(@Valid @RequestBody Regime regime) throws URISyntaxException {
        log.debug("REST request to save Regime : {}", regime);
        if (regime.getId() != null) {
            throw new BadRequestAlertException("A new regime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Regime result = regimeService.save(regime);
        return ResponseEntity.created(new URI("/api/regimes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /regimes : Updates an existing regime.
     *
     * @param regime the regime to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated regime,
     * or with status 400 (Bad Request) if the regime is not valid,
     * or with status 500 (Internal Server Error) if the regime couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/regimes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Regime> updateRegime(@Valid @RequestBody Regime regime) throws URISyntaxException {
        log.debug("REST request to update Regime : {}", regime);
        if (regime.getId() == null) {
            return createRegime(regime);
        }
        Regime result = regimeService.save(regime);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, regime.getId().toString()))
            .body(result);
    }

    /**
     * GET  /regimes : get all the regimes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of regimes in body
     */
    @GetMapping("/regimes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG, AuthoritiesConstants.USER})
    public ResponseEntity<List<Regime>> getAllRegimes(Pageable pageable) {
        log.debug("REST request to get a page of Regimes");
        Page<Regime> page = regimeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/regimes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /regimes/:id : get the "id" regime.
     *
     * @param id the id of the regime to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the regime, or with status 404 (Not Found)
     */
    @GetMapping("/regimes/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG, AuthoritiesConstants.USER})
    public ResponseEntity<Regime> getRegime(@PathVariable Long id) {
        log.debug("REST request to get Regime : {}", id);
        Regime regime = regimeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(regime));
    }

    /**
     * DELETE  /regimes/:id : delete the "id" regime.
     *
     * @param id the id of the regime to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/regimes/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Void> deleteRegime(@PathVariable Long id) {
        log.debug("REST request to delete Regime : {}", id);
        Regime regime = regimeService.findOne(id);
        regimeService.delete(regime);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
