package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.Pieces;
import com.emard.service.PiecesService;
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
 * REST controller for managing Pieces.
 */
@RestController
@RequestMapping("/api")
public class PiecesResource {

    private final Logger log = LoggerFactory.getLogger(PiecesResource.class);

    private static final String ENTITY_NAME = "pieces";

    private final PiecesService piecesService;

    public PiecesResource(PiecesService piecesService) {
        this.piecesService = piecesService;
    }

    /**
     * POST  /pieces : Create a new pieces.
     *
     * @param pieces the pieces to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pieces, or with status 400 (Bad Request) if the pieces has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pieces")
    @Timed
    public ResponseEntity<Pieces> createPieces(@Valid @RequestBody Pieces pieces) throws URISyntaxException {
        log.debug("REST request to save Pieces : {}", pieces);
        if (pieces.getId() != null) {
            throw new BadRequestAlertException("A new pieces cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pieces result = piecesService.save(pieces);
        return ResponseEntity.created(new URI("/api/pieces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pieces : Updates an existing pieces.
     *
     * @param pieces the pieces to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pieces,
     * or with status 400 (Bad Request) if the pieces is not valid,
     * or with status 500 (Internal Server Error) if the pieces couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pieces")
    @Timed
    public ResponseEntity<Pieces> updatePieces(@Valid @RequestBody Pieces pieces) throws URISyntaxException {
        log.debug("REST request to update Pieces : {}", pieces);
        if (pieces.getId() == null) {
            return createPieces(pieces);
        }
        Pieces result = piecesService.save(pieces);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pieces.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pieces : get all the pieces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pieces in body
     */
    @GetMapping("/pieces")
    @Timed
    public ResponseEntity<List<Pieces>> getAllPieces(Pageable pageable) {
        log.debug("REST request to get a page of Pieces");
        Page<Pieces> page = piecesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pieces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pieces/:id : get the "id" pieces.
     *
     * @param id the id of the pieces to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pieces, or with status 404 (Not Found)
     */
    @GetMapping("/pieces/{id}")
    @Timed
    public ResponseEntity<Pieces> getPieces(@PathVariable Long id) {
        log.debug("REST request to get Pieces : {}", id);
        Pieces pieces = piecesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pieces));
    }

    /**
     * DELETE  /pieces/:id : delete the "id" pieces.
     *
     * @param id the id of the pieces to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pieces/{id}")
    @Timed
    public ResponseEntity<Void> deletePieces(@PathVariable Long id) {
        log.debug("REST request to delete Pieces : {}", id);
        piecesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
