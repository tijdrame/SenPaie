package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.DemandeConge;
import com.emard.security.AuthoritiesConstants;
import com.emard.service.DemandeCongeService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DemandeConge.
 */
@RestController
@RequestMapping("/api")
public class DemandeCongeResource {

    private final Logger log = LoggerFactory.getLogger(DemandeCongeResource.class);

    private static final String ENTITY_NAME = "demandeConge";

    private final DemandeCongeService demandeCongeService;

    public DemandeCongeResource(DemandeCongeService demandeCongeService) {
        this.demandeCongeService = demandeCongeService;
    }

    /**
     * POST  /demande-conges : Create a new demandeConge.
     *
     * @param demandeConge the demandeConge to create
     * @return the ResponseEntity with status 201 (Created) and with body the new demandeConge, or with status 400 (Bad Request) if the demandeConge has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/demande-conges")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG, AuthoritiesConstants.USER})
    public ResponseEntity<DemandeConge> createDemandeConge(@Valid @RequestBody DemandeConge demandeConge) throws URISyntaxException {
        log.debug("REST request to save DemandeConge : {}", demandeConge);
        if (demandeConge.getId() != null) {
            throw new BadRequestAlertException("A new demandeConge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandeConge result = demandeCongeService.save(demandeConge);
        return ResponseEntity.created(new URI("/api/demande-conges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /demande-conges : Updates an existing demandeConge.
     *
     * @param demandeConge the demandeConge to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated demandeConge,
     * or with status 400 (Bad Request) if the demandeConge is not valid,
     * or with status 500 (Internal Server Error) if the demandeConge couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/demande-conges")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG, AuthoritiesConstants.USER})
    public ResponseEntity<DemandeConge> updateDemandeConge(@Valid @RequestBody DemandeConge demandeConge) throws URISyntaxException {
        log.debug("REST request to update DemandeConge : {}", demandeConge);
        if (demandeConge.getId() == null) {
            return createDemandeConge(demandeConge);
        }
        DemandeConge result = demandeCongeService.update(demandeConge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, demandeConge.getId().toString()))
            .body(result);
    }

    /**
     * GET  /demande-conges : get all the demandeConges.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of demandeConges in body
     */
    @GetMapping("/demande-conges")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG, AuthoritiesConstants.USER})
    public ResponseEntity<List<DemandeConge>> getAllDemandeConges(Pageable pageable) {
        log.debug("REST request to get a page of DemandeConges");
        Page<DemandeConge> page = demandeCongeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/demande-conges");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /demande-conges/:id : get the "id" demandeConge.
     *
     * @param id the id of the demandeConge to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the demandeConge, or with status 404 (Not Found)
     */
    @GetMapping("/demande-conges/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG, AuthoritiesConstants.USER})
    public ResponseEntity<DemandeConge> getDemandeConge(@PathVariable Long id) {
        log.debug("REST request to get DemandeConge : {}", id);
        DemandeConge demandeConge = demandeCongeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(demandeConge));
    }

    /**
     * DELETE  /demande-conges/:id : delete the "id" demandeConge.
     *
     * @param id the id of the demandeConge to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/demande-conges/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG, AuthoritiesConstants.USER})
    public ResponseEntity<Void> deleteDemandeConge(@PathVariable Long id) {
        log.debug("REST request to delete DemandeConge : {}", id);
        DemandeConge demandeConge = demandeCongeService.findOne(id);
        demandeCongeService.delete(demandeConge);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/demande-congesTer/{prenom}/{nom}/{tel}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public ResponseEntity<List<DemandeConge>> search(@PathVariable Optional<String> prenom  , @PathVariable Optional<String> nom,
                                                     @PathVariable Optional<String> tel, Pageable pageable) {
        log.debug("REST request to get a page of Collab====>: "+prenom.isPresent()+" val"+prenom.get());
        //log.debug("date deb"+dateDebut.get());
        Page<DemandeConge> page = demandeCongeService.findByCriters(prenom.isPresent()?"%"+prenom.get().trim()+"%":"",
            nom.isPresent()?"%"+nom.get().trim()+"%":"", tel.isPresent()?"%"+tel.get().trim()+"%":"",pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/demande-conges");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
