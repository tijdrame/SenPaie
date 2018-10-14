package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.TypeContrat;
import com.emard.service.TypeContratService;
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
 * REST controller for managing TypeContrat.
 */
@RestController
@RequestMapping("/api")
public class TypeContratResource {

    private final Logger log = LoggerFactory.getLogger(TypeContratResource.class);

    private static final String ENTITY_NAME = "typeContrat";

    private final TypeContratService typeContratService;

    public TypeContratResource(TypeContratService typeContratService) {
        this.typeContratService = typeContratService;
    }

    /**
     * POST  /type-contrats : Create a new typeContrat.
     *
     * @param typeContrat the typeContrat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeContrat, or with status 400 (Bad Request) if the typeContrat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-contrats")
    @Timed
    public ResponseEntity<TypeContrat> createTypeContrat(@Valid @RequestBody TypeContrat typeContrat) throws URISyntaxException {
        log.debug("REST request to save TypeContrat : {}", typeContrat);
        if (typeContrat.getId() != null) {
            throw new BadRequestAlertException("A new typeContrat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeContrat result = typeContratService.save(typeContrat);
        return ResponseEntity.created(new URI("/api/type-contrats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-contrats : Updates an existing typeContrat.
     *
     * @param typeContrat the typeContrat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeContrat,
     * or with status 400 (Bad Request) if the typeContrat is not valid,
     * or with status 500 (Internal Server Error) if the typeContrat couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-contrats")
    @Timed
    public ResponseEntity<TypeContrat> updateTypeContrat(@Valid @RequestBody TypeContrat typeContrat) throws URISyntaxException {
        log.debug("REST request to update TypeContrat : {}", typeContrat);
        if (typeContrat.getId() == null) {
            return createTypeContrat(typeContrat);
        }
        TypeContrat result = typeContratService.save(typeContrat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeContrat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-contrats : get all the typeContrats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeContrats in body
     */
    @GetMapping("/type-contrats")
    @Timed
    public ResponseEntity<List<TypeContrat>> getAllTypeContrats(Pageable pageable) {
        log.debug("REST request to get a page of TypeContrats");
        Page<TypeContrat> page = typeContratService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-contrats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /type-contrats/:id : get the "id" typeContrat.
     *
     * @param id the id of the typeContrat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeContrat, or with status 404 (Not Found)
     */
    @GetMapping("/type-contrats/{id}")
    @Timed
    public ResponseEntity<TypeContrat> getTypeContrat(@PathVariable Long id) {
        log.debug("REST request to get TypeContrat : {}", id);
        TypeContrat typeContrat = typeContratService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeContrat));
    }

    /**
     * DELETE  /type-contrats/:id : delete the "id" typeContrat.
     *
     * @param id the id of the typeContrat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-contrats/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeContrat(@PathVariable Long id) {
        log.debug("REST request to delete TypeContrat : {}", id);
        typeContratService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
