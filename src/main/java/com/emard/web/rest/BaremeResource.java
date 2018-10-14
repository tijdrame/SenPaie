package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.Bareme;
import com.emard.service.BaremeService;
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
 * REST controller for managing Bareme.
 */
@RestController
@RequestMapping("/api")
public class BaremeResource {

    private final Logger log = LoggerFactory.getLogger(BaremeResource.class);

    private static final String ENTITY_NAME = "bareme";

    private final BaremeService baremeService;

    public BaremeResource(BaremeService baremeService) {
        this.baremeService = baremeService;
    }

    /**
     * POST  /baremes : Create a new bareme.
     *
     * @param bareme the bareme to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bareme, or with status 400 (Bad Request) if the bareme has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/baremes")
    @Timed
    public ResponseEntity<Bareme> createBareme(@Valid @RequestBody Bareme bareme) throws URISyntaxException {
        log.debug("REST request to save Bareme : {}", bareme);
        if (bareme.getId() != null) {
            throw new BadRequestAlertException("A new bareme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bareme result = baremeService.save(bareme);
        return ResponseEntity.created(new URI("/api/baremes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /baremes : Updates an existing bareme.
     *
     * @param bareme the bareme to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bareme,
     * or with status 400 (Bad Request) if the bareme is not valid,
     * or with status 500 (Internal Server Error) if the bareme couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/baremes")
    @Timed
    public ResponseEntity<Bareme> updateBareme(@Valid @RequestBody Bareme bareme) throws URISyntaxException {
        log.debug("REST request to update Bareme : {}", bareme);
        if (bareme.getId() == null) {
            return createBareme(bareme);
        }
        Bareme result = baremeService.save(bareme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bareme.getId().toString()))
            .body(result);
    }

    /**
     * GET  /baremes : get all the baremes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of baremes in body
     */
    @GetMapping("/baremes")
    @Timed
    public ResponseEntity<List<Bareme>> getAllBaremes(Pageable pageable) {
        log.debug("REST request to get a page of Baremes");
        Page<Bareme> page = baremeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/baremes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /baremes/:id : get the "id" bareme.
     *
     * @param id the id of the bareme to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bareme, or with status 404 (Not Found)
     */
    @GetMapping("/baremes/{id}")
    @Timed
    public ResponseEntity<Bareme> getBareme(@PathVariable Long id) {
        log.debug("REST request to get Bareme : {}", id);
        Bareme bareme = baremeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bareme));
    }

    /**
     * DELETE  /baremes/:id : delete the "id" bareme.
     *
     * @param id the id of the bareme to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/baremes/{id}")
    @Timed
    public ResponseEntity<Void> deleteBareme(@PathVariable Long id) {
        log.debug("REST request to delete Bareme : {}", id);
        baremeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
