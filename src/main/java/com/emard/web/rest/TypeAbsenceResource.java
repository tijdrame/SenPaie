package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.TypeAbsence;
import com.emard.security.AuthoritiesConstants;
import com.emard.service.TypeAbsenceService;
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
 * REST controller for managing TypeAbsence.
 */
@RestController
@RequestMapping("/api")
public class TypeAbsenceResource {

    private final Logger log = LoggerFactory.getLogger(TypeAbsenceResource.class);

    private static final String ENTITY_NAME = "typeAbsence";

    private final TypeAbsenceService typeAbsenceService;

    public TypeAbsenceResource(TypeAbsenceService typeAbsenceService) {
        this.typeAbsenceService = typeAbsenceService;
    }

    /**
     * POST  /type-absences : Create a new typeAbsence.
     *
     * @param typeAbsence the typeAbsence to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeAbsence, or with status 400 (Bad Request) if the typeAbsence has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-absences")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<TypeAbsence> createTypeAbsence(@Valid @RequestBody TypeAbsence typeAbsence) throws URISyntaxException {
        log.debug("REST request to save TypeAbsence : {}", typeAbsence);
        if (typeAbsence.getId() != null) {
            throw new BadRequestAlertException("A new typeAbsence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeAbsence result = typeAbsenceService.save(typeAbsence);
        return ResponseEntity.created(new URI("/api/type-absences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-absences : Updates an existing typeAbsence.
     *
     * @param typeAbsence the typeAbsence to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeAbsence,
     * or with status 400 (Bad Request) if the typeAbsence is not valid,
     * or with status 500 (Internal Server Error) if the typeAbsence couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-absences")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<TypeAbsence> updateTypeAbsence(@Valid @RequestBody TypeAbsence typeAbsence) throws URISyntaxException {
        log.debug("REST request to update TypeAbsence : {}", typeAbsence);
        if (typeAbsence.getId() == null) {
            return createTypeAbsence(typeAbsence);
        }
        TypeAbsence result = typeAbsenceService.save(typeAbsence);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeAbsence.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-absences : get all the typeAbsences.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeAbsences in body
     */
    @GetMapping("/type-absences")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.USER, AuthoritiesConstants.DG})
    public ResponseEntity<List<TypeAbsence>> getAllTypeAbsences(Pageable pageable) {
        log.debug("REST request to get a page of TypeAbsences");
        Page<TypeAbsence> page = typeAbsenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-absences");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /type-absences/:id : get the "id" typeAbsence.
     *
     * @param id the id of the typeAbsence to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeAbsence, or with status 404 (Not Found)
     */
    @GetMapping("/type-absences/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.USER, AuthoritiesConstants.DG})
    public ResponseEntity<TypeAbsence> getTypeAbsence(@PathVariable Long id) {
        log.debug("REST request to get TypeAbsence : {}", id);
        TypeAbsence typeAbsence = typeAbsenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeAbsence));
    }

    /**
     * DELETE  /type-absences/:id : delete the "id" typeAbsence.
     *
     * @param id the id of the typeAbsence to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-absences/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Void> deleteTypeAbsence(@PathVariable Long id) {
        log.debug("REST request to delete TypeAbsence : {}", id);
        TypeAbsence typeAbsence = typeAbsenceService.findOne(id);
        typeAbsenceService.delete(typeAbsence);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
