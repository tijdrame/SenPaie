package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.TypePaiement;
import com.emard.service.TypePaiementService;
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
 * REST controller for managing TypePaiement.
 */
@RestController
@RequestMapping("/api")
public class TypePaiementResource {

    private final Logger log = LoggerFactory.getLogger(TypePaiementResource.class);

    private static final String ENTITY_NAME = "typePaiement";

    private final TypePaiementService typePaiementService;

    public TypePaiementResource(TypePaiementService typePaiementService) {
        this.typePaiementService = typePaiementService;
    }

    /**
     * POST  /type-paiements : Create a new typePaiement.
     *
     * @param typePaiement the typePaiement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typePaiement, or with status 400 (Bad Request) if the typePaiement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-paiements")
    @Timed
    public ResponseEntity<TypePaiement> createTypePaiement(@Valid @RequestBody TypePaiement typePaiement) throws URISyntaxException {
        log.debug("REST request to save TypePaiement : {}", typePaiement);
        if (typePaiement.getId() != null) {
            throw new BadRequestAlertException("A new typePaiement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypePaiement result = typePaiementService.save(typePaiement);
        return ResponseEntity.created(new URI("/api/type-paiements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-paiements : Updates an existing typePaiement.
     *
     * @param typePaiement the typePaiement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typePaiement,
     * or with status 400 (Bad Request) if the typePaiement is not valid,
     * or with status 500 (Internal Server Error) if the typePaiement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-paiements")
    @Timed
    public ResponseEntity<TypePaiement> updateTypePaiement(@Valid @RequestBody TypePaiement typePaiement) throws URISyntaxException {
        log.debug("REST request to update TypePaiement : {}", typePaiement);
        if (typePaiement.getId() == null) {
            return createTypePaiement(typePaiement);
        }
        TypePaiement result = typePaiementService.save(typePaiement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typePaiement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-paiements : get all the typePaiements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typePaiements in body
     */
    @GetMapping("/type-paiements")
    @Timed
    public ResponseEntity<List<TypePaiement>> getAllTypePaiements(Pageable pageable) {
        log.debug("REST request to get a page of TypePaiements");
        Page<TypePaiement> page = typePaiementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-paiements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /type-paiements/:id : get the "id" typePaiement.
     *
     * @param id the id of the typePaiement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typePaiement, or with status 404 (Not Found)
     */
    @GetMapping("/type-paiements/{id}")
    @Timed
    public ResponseEntity<TypePaiement> getTypePaiement(@PathVariable Long id) {
        log.debug("REST request to get TypePaiement : {}", id);
        TypePaiement typePaiement = typePaiementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typePaiement));
    }

    /**
     * DELETE  /type-paiements/:id : delete the "id" typePaiement.
     *
     * @param id the id of the typePaiement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-paiements/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypePaiement(@PathVariable Long id) {
        log.debug("REST request to delete TypePaiement : {}", id);
        typePaiementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
