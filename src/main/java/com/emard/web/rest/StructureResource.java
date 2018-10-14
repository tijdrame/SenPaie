package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.Structure;
import com.emard.service.StructureService;
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
 * REST controller for managing Structure.
 */
@RestController
@RequestMapping("/api")
public class StructureResource {

    private final Logger log = LoggerFactory.getLogger(StructureResource.class);

    private static final String ENTITY_NAME = "structure";

    private final StructureService structureService;

    public StructureResource(StructureService structureService) {
        this.structureService = structureService;
    }

    /**
     * POST  /structures : Create a new structure.
     *
     * @param structure the structure to create
     * @return the ResponseEntity with status 201 (Created) and with body the new structure, or with status 400 (Bad Request) if the structure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/structures")
    @Timed
    public ResponseEntity<Structure> createStructure(@Valid @RequestBody Structure structure) throws URISyntaxException {
        log.debug("REST request to save Structure : {}", structure);
        if (structure.getId() != null) {
            throw new BadRequestAlertException("A new structure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Structure result = structureService.save(structure);
        return ResponseEntity.created(new URI("/api/structures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /structures : Updates an existing structure.
     *
     * @param structure the structure to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated structure,
     * or with status 400 (Bad Request) if the structure is not valid,
     * or with status 500 (Internal Server Error) if the structure couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/structures")
    @Timed
    public ResponseEntity<Structure> updateStructure(@Valid @RequestBody Structure structure) throws URISyntaxException {
        log.debug("REST request to update Structure : {}", structure);
        if (structure.getId() == null) {
            return createStructure(structure);
        }
        Structure result = structureService.save(structure);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, structure.getId().toString()))
            .body(result);
    }

    /**
     * GET  /structures : get all the structures.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of structures in body
     */
    @GetMapping("/structures")
    @Timed
    public ResponseEntity<List<Structure>> getAllStructures(Pageable pageable) {
        log.debug("REST request to get a page of Structures");
        Page<Structure> page = structureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/structures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /structures/:id : get the "id" structure.
     *
     * @param id the id of the structure to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the structure, or with status 404 (Not Found)
     */
    @GetMapping("/structures/{id}")
    @Timed
    public ResponseEntity<Structure> getStructure(@PathVariable Long id) {
        log.debug("REST request to get Structure : {}", id);
        Structure structure = structureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(structure));
    }

    /**
     * DELETE  /structures/:id : delete the "id" structure.
     *
     * @param id the id of the structure to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/structures/{id}")
    @Timed
    public ResponseEntity<Void> deleteStructure(@PathVariable Long id) {
        log.debug("REST request to delete Structure : {}", id);
        structureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
