package com.emard.service;

import com.emard.domain.Prime;
import com.emard.repository.PrimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Prime.
 */
@Service
@Transactional
public class PrimeService {

    private final Logger log = LoggerFactory.getLogger(PrimeService.class);

    private final PrimeRepository primeRepository;

    public PrimeService(PrimeRepository primeRepository) {
        this.primeRepository = primeRepository;
    }

    /**
     * Save a prime.
     *
     * @param prime the entity to save
     * @return the persisted entity
     */
    public Prime save(Prime prime) {
        log.debug("Request to save Prime : {}", prime);
        prime.deleted(false);
        return primeRepository.save(prime);
    }

    /**
     * Get all the primes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Prime> findAll(Pageable pageable) {
        log.debug("Request to get all Primes");
        return primeRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one prime by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Prime findOne(Long id) {
        log.debug("Request to get Prime : {}", id);
        return primeRepository.findOne(id);
    }

    /**
     * Delete the prime by id.
     *
     * @param id the id of the entity
     */
    public void delete(Prime id) {
        log.debug("Request to delete Prime : {}", id);
        id.deleted(true);
        primeRepository.save(id);
    }
}
