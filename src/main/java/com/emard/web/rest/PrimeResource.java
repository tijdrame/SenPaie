package com.emard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emard.domain.Prime;
import com.emard.security.AuthoritiesConstants;
import com.emard.service.PrimeService;
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
 * REST controller for managing Prime.
 */
@RestController
@RequestMapping("/api")
public class PrimeResource {

    private final Logger log = LoggerFactory.getLogger(PrimeResource.class);

    private static final String ENTITY_NAME = "prime";

    private final PrimeService primeService;

    public PrimeResource(PrimeService primeService) {
        this.primeService = primeService;
    }

    /**
     * POST  /primes : Create a new prime.
     *
     * @param prime the prime to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prime, or with status 400 (Bad Request) if the prime has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/primes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Prime> createPrime(@Valid @RequestBody Prime prime) throws URISyntaxException {
        log.debug("REST request to save Prime : {}", prime);
        if (prime.getId() != null) {
            throw new BadRequestAlertException("A new prime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Prime result = primeService.save(prime);
        return ResponseEntity.created(new URI("/api/primes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /primes : Updates an existing prime.
     *
     * @param prime the prime to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prime,
     * or with status 400 (Bad Request) if the prime is not valid,
     * or with status 500 (Internal Server Error) if the prime couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/primes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Prime> updatePrime(@Valid @RequestBody Prime prime) throws URISyntaxException {
        log.debug("REST request to update Prime : {}", prime);
        if (prime.getId() == null) {
            return createPrime(prime);
        }
        Prime result = primeService.save(prime);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prime.getId().toString()))
            .body(result);
    }

    /**
     * GET  /primes : get all the primes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of primes in body
     */
    @GetMapping("/primes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<List<Prime>> getAllPrimes(Pageable pageable) {
        log.debug("REST request to get a page of Primes");
        Page<Prime> page = primeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/primes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /primes/:id : get the "id" prime.
     *
     * @param id the id of the prime to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prime, or with status 404 (Not Found)
     */
    @GetMapping("/primes/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Prime> getPrime(@PathVariable Long id) {
        log.debug("REST request to get Prime : {}", id);
        Prime prime = primeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(prime));
    }

    /**
     * DELETE  /primes/:id : delete the "id" prime.
     *
     * @param id the id of the prime to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/primes/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.RH})
    public ResponseEntity<Void> deletePrime(@PathVariable Long id) {
        log.debug("REST request to delete Prime : {}", id);
        Prime prime = primeService.findOne(id);
        primeService.delete(prime);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
