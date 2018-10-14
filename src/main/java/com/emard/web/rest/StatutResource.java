package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.Statut;
import com.emard.service.StatutService;
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
 * REST controller for managing Statut.
 */
@RestController
@RequestMapping("/api")
public class StatutResource {

    private final Logger log = LoggerFactory.getLogger(StatutResource.class);

    private static final String ENTITY_NAME = "statut";

    private final StatutService statutService;

    public StatutResource(StatutService statutService) {
        this.statutService = statutService;
    }

    /**
     * POST  /statuts : Create a new statut.
     *
     * @param statut the statut to create
     * @return the ResponseEntity with status 201 (Created) and with body the new statut, or with status 400 (Bad Request) if the statut has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/statuts")
    @Timed
    public ResponseEntity<Statut> createStatut(@Valid @RequestBody Statut statut) throws URISyntaxException {
        log.debug("REST request to save Statut : {}", statut);
        if (statut.getId() != null) {
            throw new BadRequestAlertException("A new statut cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Statut result = statutService.save(statut);
        return ResponseEntity.created(new URI("/api/statuts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /statuts : Updates an existing statut.
     *
     * @param statut the statut to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated statut,
     * or with status 400 (Bad Request) if the statut is not valid,
     * or with status 500 (Internal Server Error) if the statut couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/statuts")
    @Timed
    public ResponseEntity<Statut> updateStatut(@Valid @RequestBody Statut statut) throws URISyntaxException {
        log.debug("REST request to update Statut : {}", statut);
        if (statut.getId() == null) {
            return createStatut(statut);
        }
        Statut result = statutService.save(statut);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, statut.getId().toString()))
            .body(result);
    }

    /**
     * GET  /statuts : get all the statuts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of statuts in body
     */
    @GetMapping("/statuts")
    @Timed
    public ResponseEntity<List<Statut>> getAllStatuts(Pageable pageable) {
        log.debug("REST request to get a page of Statuts");
        Page<Statut> page = statutService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/statuts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /statuts/:id : get the "id" statut.
     *
     * @param id the id of the statut to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the statut, or with status 404 (Not Found)
     */
    @GetMapping("/statuts/{id}")
    @Timed
    public ResponseEntity<Statut> getStatut(@PathVariable Long id) {
        log.debug("REST request to get Statut : {}", id);
        Statut statut = statutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(statut));
    }

    /**
     * DELETE  /statuts/:id : delete the "id" statut.
     *
     * @param id the id of the statut to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/statuts/{id}")
    @Timed
    public ResponseEntity<Void> deleteStatut(@PathVariable Long id) {
        log.debug("REST request to delete Statut : {}", id);
        statutService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
