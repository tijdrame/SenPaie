package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.Bulletin;
import com.emard.security.AuthoritiesConstants;
import com.emard.service.BulletinService;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Bulletin.
 */
@RestController
@RequestMapping("/api")
public class BulletinResource {

    private final Logger log = LoggerFactory.getLogger(BulletinResource.class);

    private static final String ENTITY_NAME = "bulletin";

    private final BulletinService bulletinService;

    public BulletinResource(BulletinService bulletinService) {
        this.bulletinService = bulletinService;
    }

    /**
     * POST  /bulletins : Create a new bulletin.
     *
     * @param bulletin the bulletin to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bulletin, or with status 400 (Bad Request) if the bulletin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bulletins")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Bulletin> createBulletin(@Valid @RequestBody Bulletin bulletin) throws URISyntaxException {
        log.debug("REST request to save Bulletin : {}", bulletin);
        if (bulletin.getId() != null) {
            throw new BadRequestAlertException("A new bulletin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bulletin result = bulletinService.save(bulletin);
        return ResponseEntity.created(new URI("/api/bulletins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bulletins : Updates an existing bulletin.
     *
     * @param bulletin the bulletin to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bulletin,
     * or with status 400 (Bad Request) if the bulletin is not valid,
     * or with status 500 (Internal Server Error) if the bulletin couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bulletins")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Bulletin> updateBulletin(@Valid @RequestBody Bulletin bulletin) throws URISyntaxException {
        log.debug("REST request to update Bulletin : {}", bulletin);
        if (bulletin.getId() == null) {
            return createBulletin(bulletin);
        }
        Bulletin result = bulletinService.save(bulletin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bulletin.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bulletins : get all the bulletins.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bulletins in body
     */
    @GetMapping("/bulletins")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public ResponseEntity<List<Bulletin>> getAllBulletins(Pageable pageable) {
        log.debug("REST request to get a page of Bulletins");
        Page<Bulletin> page = bulletinService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bulletins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bulletins/:id : get the "id" bulletin.
     *
     * @param id the id of the bulletin to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bulletin, or with status 404 (Not Found)
     */
    @GetMapping("/bulletins/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public ResponseEntity<Bulletin> getBulletin(@PathVariable Long id) {
        log.debug("REST request to get Bulletin : {}", id);
        Bulletin bulletin = bulletinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bulletin));
    }

    /**
     * DELETE  /bulletins/:id : delete the "id" bulletin.
     *
     * @param id the id of the bulletin to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bulletins/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Void> deleteBulletin(@PathVariable Long id) {
        log.debug("REST request to delete Bulletin : {}", id);
        Bulletin bulletin = bulletinService.findOne(id);
        bulletinService.delete(bulletin);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/bulletinsTer/{prenom}/{nom}/{matricule}/{deleted}")
//  @GetMapping("/collaborateursTer/{prenom}/{nom}/{tel}/{deleted}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public ResponseEntity<List<Bulletin>> search(@PathVariable Optional<String> prenom  , @PathVariable Optional<String> nom,
                                                 @PathVariable Optional<String> matricule,
                                                 @PathVariable Boolean deleted, Pageable pageable) {
        //log.debug("REST request to get a page of Collab====>: "+prenom.isPresent()+" val"+prenom.get());
        log.debug("dans serch bull ressource");
        //SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        //LocalDate ld = format.parse(theDate);
        //Next parse the date from the @RequestParam, specifying the TO type as a TemporalQuery:
        //LocalDateTime date = dateTimeFormat.parse(start, LocalDateTime::from);

        //LocalDate dateBi = new LocalDate(theDate);
        Page<Bulletin> page = bulletinService.findByCriteres(prenom.isPresent()?"%"+prenom.get().trim()+"%":"",
            nom.isPresent()?"%"+nom.get().trim()+"%":"", matricule.isPresent()?"%"+matricule.get().trim()+"%":"",
             deleted, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bulletins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
