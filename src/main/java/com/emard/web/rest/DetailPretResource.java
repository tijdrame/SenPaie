package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.DetailPret;
import com.emard.service.DetailPretService;
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
 * REST controller for managing DetailPret.
 */
@RestController
@RequestMapping("/api")
public class DetailPretResource {

    private final Logger log = LoggerFactory.getLogger(DetailPretResource.class);

    private static final String ENTITY_NAME = "detailPret";

    private final DetailPretService detailPretService;

    public DetailPretResource(DetailPretService detailPretService) {
        this.detailPretService = detailPretService;
    }

    /**
     * POST  /detail-prets : Create a new detailPret.
     *
     * @param detailPret the detailPret to create
     * @return the ResponseEntity with status 201 (Created) and with body the new detailPret, or with status 400 (Bad Request) if the detailPret has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/detail-prets")
    @Timed
    public ResponseEntity<DetailPret> createDetailPret(@Valid @RequestBody DetailPret detailPret) throws URISyntaxException {
        log.debug("REST request to save DetailPret : {}", detailPret);
        if (detailPret.getId() != null) {
            throw new BadRequestAlertException("A new detailPret cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetailPret result = detailPretService.save(detailPret);
        return ResponseEntity.created(new URI("/api/detail-prets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /detail-prets : Updates an existing detailPret.
     *
     * @param detailPret the detailPret to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated detailPret,
     * or with status 400 (Bad Request) if the detailPret is not valid,
     * or with status 500 (Internal Server Error) if the detailPret couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/detail-prets")
    @Timed
    public ResponseEntity<DetailPret> updateDetailPret(@Valid @RequestBody DetailPret detailPret) throws URISyntaxException {
        log.debug("REST request to update DetailPret : {}", detailPret);
        if (detailPret.getId() == null) {
            return createDetailPret(detailPret);
        }
        DetailPret result = detailPretService.save(detailPret);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, detailPret.getId().toString()))
            .body(result);
    }

    /**
     * GET  /detail-prets : get all the detailPrets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of detailPrets in body
     */
    @GetMapping("/detail-prets")
    @Timed
    public ResponseEntity<List<DetailPret>> getAllDetailPrets(Pageable pageable) {
        log.debug("REST request to get a page of DetailPrets");
        Page<DetailPret> page = detailPretService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/detail-prets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /detail-prets/:id : get the "id" detailPret.
     *
     * @param id the id of the detailPret to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the detailPret, or with status 404 (Not Found)
     */
    @GetMapping("/detail-prets/{id}")
    @Timed
    public ResponseEntity<DetailPret> getDetailPret(@PathVariable Long id) {
        log.debug("REST request to get DetailPret : {}", id);
        DetailPret detailPret = detailPretService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(detailPret));
    }

    /**
     * DELETE  /detail-prets/:id : delete the "id" detailPret.
     *
     * @param id the id of the detailPret to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/detail-prets/{id}")
    @Timed
    public ResponseEntity<Void> deleteDetailPret(@PathVariable Long id) {
        log.debug("REST request to delete DetailPret : {}", id);
        detailPretService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
