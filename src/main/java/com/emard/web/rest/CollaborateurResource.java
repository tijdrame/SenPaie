package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.Collaborateur;
import com.emard.security.AuthoritiesConstants;
import com.emard.service.CollaborateurService;
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
 * REST controller for managing Collaborateur.
 */
@RestController
@RequestMapping("/api")
public class CollaborateurResource {

    private final Logger log = LoggerFactory.getLogger(CollaborateurResource.class);

    private static final String ENTITY_NAME = "collaborateur";

    private final CollaborateurService collaborateurService;

    public CollaborateurResource(CollaborateurService collaborateurService) {
        this.collaborateurService = collaborateurService;
    }

    /**
     * POST  /collaborateurs : Create a new collaborateur.
     *
     * @param collaborateur the collaborateur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new collaborateur, or with status 400 (Bad Request) if the collaborateur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/collaborateurs")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Collaborateur> createCollaborateur(@Valid @RequestBody Collaborateur collaborateur) throws URISyntaxException {
        log.debug("REST request to save Collaborateur : {}", collaborateur);
        if (collaborateur.getId() != null) {
            throw new BadRequestAlertException("A new collaborateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Collaborateur result = collaborateurService.save(collaborateur);
        return ResponseEntity.created(new URI("/api/collaborateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /collaborateurs : Updates an existing collaborateur.
     *
     * @param collaborateur the collaborateur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated collaborateur,
     * or with status 400 (Bad Request) if the collaborateur is not valid,
     * or with status 500 (Internal Server Error) if the collaborateur couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/collaborateurs")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Collaborateur> updateCollaborateur(@Valid @RequestBody Collaborateur collaborateur) throws URISyntaxException {
        log.debug("REST request to update Collaborateur : {}", collaborateur);
        if (collaborateur.getId() == null) {
            return createCollaborateur(collaborateur);
        }
        Collaborateur result = collaborateurService.update(collaborateur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, collaborateur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /collaborateurs : get all the collaborateurs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of collaborateurs in body
     */
    @GetMapping("/collaborateurs")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG, AuthoritiesConstants.USER})
    public ResponseEntity<List<Collaborateur>> getAllCollaborateurs(Pageable pageable) {
        log.debug("REST request to get a page of Collaborateurs");
        Page<Collaborateur> page = collaborateurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/collaborateurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /collaborateurs : get all the collaborateurs.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and the list of collaborateurs in body
     */
    @GetMapping("/collaborateursBis")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG, AuthoritiesConstants.USER})
    public ResponseEntity<List<Collaborateur>> getAllCollaborateursBis() {
        log.debug("REST request to get a page of Collaborateurs");
        List<Collaborateur> list = collaborateurService.getTheCollabo();
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/collaborateurs");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * GET  /collaborateurs/:id : get the "id" collaborateur.
     *
     * @param id the id of the collaborateur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the collaborateur, or with status 404 (Not Found)
     */
    @GetMapping("/collaborateurs/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG, AuthoritiesConstants.USER})
    public ResponseEntity<Collaborateur> getCollaborateur(@PathVariable Long id) {
        log.debug("REST request to get Collaborateur : {}", id);
        Collaborateur collaborateur = collaborateurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(collaborateur));
    }

    /**
     * DELETE  /collaborateurs/:id : delete the "id" collaborateur.
     *
     * @param id the id of the collaborateur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/collaborateurs/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Void> deleteCollaborateur(@PathVariable Long id) {
        log.debug("REST request to delete Collaborateur : {}", id);
        Collaborateur collaborateur = collaborateurService.findOne(id);
        collaborateurService.delete(collaborateur);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/collaborateursTer/{prenom}/{nom}/{tel}/{deleted}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH, AuthoritiesConstants.DG})
    public ResponseEntity<List<Collaborateur>> search(@PathVariable Optional<String> prenom  , @PathVariable Optional<String> nom,
                                                      @PathVariable Optional<String> tel, @PathVariable Boolean deleted, Pageable pageable) {
        log.debug("REST request to get a page of Collab====>: "+prenom.isPresent()+" val"+prenom.get());
        Page<Collaborateur> page = collaborateurService.findByCriteres(prenom.isPresent()?"%"+prenom.get().trim()+"%":"",
            nom.isPresent()?"%"+nom.get().trim()+"%":"", tel.isPresent()?"%"+tel.get().trim()+"%":"", deleted, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/collaborateurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
