package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.MoisConcerne;
import com.emard.service.MoisConcerneService;
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
 * REST controller for managing MoisConcerne.
 */
@RestController
@RequestMapping("/api")
public class MoisConcerneResource {

    private final Logger log = LoggerFactory.getLogger(MoisConcerneResource.class);

    private static final String ENTITY_NAME = "moisConcerne";

    private final MoisConcerneService moisConcerneService;

    public MoisConcerneResource(MoisConcerneService moisConcerneService) {
        this.moisConcerneService = moisConcerneService;
    }

    /**
     * POST  /mois-concernes : Create a new moisConcerne.
     *
     * @param moisConcerne the moisConcerne to create
     * @return the ResponseEntity with status 201 (Created) and with body the new moisConcerne, or with status 400 (Bad Request) if the moisConcerne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mois-concernes")
    @Timed
    public ResponseEntity<MoisConcerne> createMoisConcerne(@Valid @RequestBody MoisConcerne moisConcerne) throws URISyntaxException {
        log.debug("REST request to save MoisConcerne : {}", moisConcerne);
        if (moisConcerne.getId() != null) {
            throw new BadRequestAlertException("A new moisConcerne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MoisConcerne result = moisConcerneService.save(moisConcerne);
        return ResponseEntity.created(new URI("/api/mois-concernes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mois-concernes : Updates an existing moisConcerne.
     *
     * @param moisConcerne the moisConcerne to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated moisConcerne,
     * or with status 400 (Bad Request) if the moisConcerne is not valid,
     * or with status 500 (Internal Server Error) if the moisConcerne couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mois-concernes")
    @Timed
    public ResponseEntity<MoisConcerne> updateMoisConcerne(@Valid @RequestBody MoisConcerne moisConcerne) throws URISyntaxException {
        log.debug("REST request to update MoisConcerne : {}", moisConcerne);
        if (moisConcerne.getId() == null) {
            return createMoisConcerne(moisConcerne);
        }
        MoisConcerne result = moisConcerneService.save(moisConcerne);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, moisConcerne.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mois-concernes : get all the moisConcernes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of moisConcernes in body
     */
    @GetMapping("/mois-concernes")
    @Timed
    public ResponseEntity<List<MoisConcerne>> getAllMoisConcernes(Pageable pageable) {
        log.debug("REST request to get a page of MoisConcernes");
        Page<MoisConcerne> page = moisConcerneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mois-concernes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mois-concernes/:id : get the "id" moisConcerne.
     *
     * @param id the id of the moisConcerne to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the moisConcerne, or with status 404 (Not Found)
     */
    @GetMapping("/mois-concernes/{id}")
    @Timed
    public ResponseEntity<MoisConcerne> getMoisConcerne(@PathVariable Long id) {
        log.debug("REST request to get MoisConcerne : {}", id);
        MoisConcerne moisConcerne = moisConcerneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(moisConcerne));
    }

    /**
     * DELETE  /mois-concernes/:id : delete the "id" moisConcerne.
     *
     * @param id the id of the moisConcerne to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mois-concernes/{id}")
    @Timed
    public ResponseEntity<Void> deleteMoisConcerne(@PathVariable Long id) {
        log.debug("REST request to delete MoisConcerne : {}", id);
        MoisConcerne moisConcerne = moisConcerneService.findOne(id);
        moisConcerneService.delete(moisConcerne);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
