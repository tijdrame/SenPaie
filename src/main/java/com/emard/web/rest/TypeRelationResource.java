package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.TypeRelation;
import com.emard.security.AuthoritiesConstants;
import com.emard.service.TypeRelationService;
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
 * REST controller for managing TypeRelation.
 */
@RestController
@RequestMapping("/api")
public class TypeRelationResource {

    private final Logger log = LoggerFactory.getLogger(TypeRelationResource.class);

    private static final String ENTITY_NAME = "typeRelation";

    private final TypeRelationService typeRelationService;

    public TypeRelationResource(TypeRelationService typeRelationService) {
        this.typeRelationService = typeRelationService;
    }

    /**
     * POST  /type-relations : Create a new typeRelation.
     *
     * @param typeRelation the typeRelation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeRelation, or with status 400 (Bad Request) if the typeRelation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-relations")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<TypeRelation> createTypeRelation(@Valid @RequestBody TypeRelation typeRelation) throws URISyntaxException {
        log.debug("REST request to save TypeRelation : {}", typeRelation);
        if (typeRelation.getId() != null) {
            throw new BadRequestAlertException("A new typeRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeRelation result = typeRelationService.save(typeRelation);
        return ResponseEntity.created(new URI("/api/type-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-relations : Updates an existing typeRelation.
     *
     * @param typeRelation the typeRelation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeRelation,
     * or with status 400 (Bad Request) if the typeRelation is not valid,
     * or with status 500 (Internal Server Error) if the typeRelation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-relations")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<TypeRelation> updateTypeRelation(@Valid @RequestBody TypeRelation typeRelation) throws URISyntaxException {
        log.debug("REST request to update TypeRelation : {}", typeRelation);
        if (typeRelation.getId() == null) {
            return createTypeRelation(typeRelation);
        }
        TypeRelation result = typeRelationService.save(typeRelation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeRelation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-relations : get all the typeRelations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeRelations in body
     */
    @GetMapping("/type-relations")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG, AuthoritiesConstants.USER})
    public ResponseEntity<List<TypeRelation>> getAllTypeRelations(Pageable pageable) {
        log.debug("REST request to get a page of TypeRelations");
        Page<TypeRelation> page = typeRelationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-relations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /type-relations/:id : get the "id" typeRelation.
     *
     * @param id the id of the typeRelation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeRelation, or with status 404 (Not Found)
     */
    @GetMapping("/type-relations/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG, AuthoritiesConstants.USER})
    public ResponseEntity<TypeRelation> getTypeRelation(@PathVariable Long id) {
        log.debug("REST request to get TypeRelation : {}", id);
        TypeRelation typeRelation = typeRelationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeRelation));
    }

    /**
     * DELETE  /type-relations/:id : delete the "id" typeRelation.
     *
     * @param id the id of the typeRelation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-relations/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Void> deleteTypeRelation(@PathVariable Long id) {
        log.debug("REST request to delete TypeRelation : {}", id);
        TypeRelation typeRelation = typeRelationService.findOne(id);
        typeRelationService.delete(typeRelation);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
