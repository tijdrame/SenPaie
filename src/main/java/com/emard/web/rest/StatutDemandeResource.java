package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.StatutDemande;
import com.emard.service.StatutDemandeService;
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
 * REST controller for managing StatutDemande.
 */
@RestController
@RequestMapping("/api")
public class StatutDemandeResource {

    private final Logger log = LoggerFactory.getLogger(StatutDemandeResource.class);

    private static final String ENTITY_NAME = "statutDemande";

    private final StatutDemandeService statutDemandeService;

    public StatutDemandeResource(StatutDemandeService statutDemandeService) {
        this.statutDemandeService = statutDemandeService;
    }

    /**
     * POST  /statut-demandes : Create a new statutDemande.
     *
     * @param statutDemande the statutDemande to create
     * @return the ResponseEntity with status 201 (Created) and with body the new statutDemande, or with status 400 (Bad Request) if the statutDemande has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/statut-demandes")
    @Timed
    public ResponseEntity<StatutDemande> createStatutDemande(@Valid @RequestBody StatutDemande statutDemande) throws URISyntaxException {
        log.debug("REST request to save StatutDemande : {}", statutDemande);
        if (statutDemande.getId() != null) {
            throw new BadRequestAlertException("A new statutDemande cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatutDemande result = statutDemandeService.save(statutDemande);
        return ResponseEntity.created(new URI("/api/statut-demandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /statut-demandes : Updates an existing statutDemande.
     *
     * @param statutDemande the statutDemande to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated statutDemande,
     * or with status 400 (Bad Request) if the statutDemande is not valid,
     * or with status 500 (Internal Server Error) if the statutDemande couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/statut-demandes")
    @Timed
    public ResponseEntity<StatutDemande> updateStatutDemande(@Valid @RequestBody StatutDemande statutDemande) throws URISyntaxException {
        log.debug("REST request to update StatutDemande : {}", statutDemande);
        if (statutDemande.getId() == null) {
            return createStatutDemande(statutDemande);
        }
        StatutDemande result = statutDemandeService.save(statutDemande);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, statutDemande.getId().toString()))
            .body(result);
    }

    /**
     * GET  /statut-demandes : get all the statutDemandes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of statutDemandes in body
     */
    @GetMapping("/statut-demandes")
    @Timed
    public ResponseEntity<List<StatutDemande>> getAllStatutDemandes(Pageable pageable) {
        log.debug("REST request to get a page of StatutDemandes");
        Page<StatutDemande> page = statutDemandeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/statut-demandes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /statut-demandes/:id : get the "id" statutDemande.
     *
     * @param id the id of the statutDemande to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the statutDemande, or with status 404 (Not Found)
     */
    @GetMapping("/statut-demandes/{id}")
    @Timed
    public ResponseEntity<StatutDemande> getStatutDemande(@PathVariable Long id) {
        log.debug("REST request to get StatutDemande : {}", id);
        StatutDemande statutDemande = statutDemandeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(statutDemande));
    }

    /**
     * DELETE  /statut-demandes/:id : delete the "id" statutDemande.
     *
     * @param id the id of the statutDemande to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/statut-demandes/{id}")
    @Timed
    public ResponseEntity<Void> deleteStatutDemande(@PathVariable Long id) {
        log.debug("REST request to delete StatutDemande : {}", id);
        statutDemandeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
