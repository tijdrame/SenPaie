package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.Prime;
import com.emard.repository.PrimeRepository;
import com.emard.service.PrimeService;
import com.emard.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.emard.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PrimeResource REST controller.
 *
 * @see PrimeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class PrimeResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IMPOSABLE = false;
    private static final Boolean UPDATED_IMPOSABLE = true;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private PrimeRepository primeRepository;

    @Autowired
    private PrimeService primeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrimeMockMvc;

    private Prime prime;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrimeResource primeResource = new PrimeResource(primeService);
        this.restPrimeMockMvc = MockMvcBuilders.standaloneSetup(primeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prime createEntity(EntityManager em) {
        Prime prime = new Prime()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .imposable(DEFAULT_IMPOSABLE)
            .deleted(DEFAULT_DELETED);
        return prime;
    }

    @Before
    public void initTest() {
        prime = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrime() throws Exception {
        int databaseSizeBeforeCreate = primeRepository.findAll().size();

        // Create the Prime
        restPrimeMockMvc.perform(post("/api/primes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prime)))
            .andExpect(status().isCreated());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeCreate + 1);
        Prime testPrime = primeList.get(primeList.size() - 1);
        assertThat(testPrime.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testPrime.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPrime.isImposable()).isEqualTo(DEFAULT_IMPOSABLE);
        assertThat(testPrime.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createPrimeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = primeRepository.findAll().size();

        // Create the Prime with an existing ID
        prime.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrimeMockMvc.perform(post("/api/primes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prime)))
            .andExpect(status().isBadRequest());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = primeRepository.findAll().size();
        // set the field null
        prime.setLibelle(null);

        // Create the Prime, which fails.

        restPrimeMockMvc.perform(post("/api/primes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prime)))
            .andExpect(status().isBadRequest());

        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = primeRepository.findAll().size();
        // set the field null
        prime.setCode(null);

        // Create the Prime, which fails.

        restPrimeMockMvc.perform(post("/api/primes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prime)))
            .andExpect(status().isBadRequest());

        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrimes() throws Exception {
        // Initialize the database
        primeRepository.saveAndFlush(prime);

        // Get all the primeList
        restPrimeMockMvc.perform(get("/api/primes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prime.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].imposable").value(hasItem(DEFAULT_IMPOSABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getPrime() throws Exception {
        // Initialize the database
        primeRepository.saveAndFlush(prime);

        // Get the prime
        restPrimeMockMvc.perform(get("/api/primes/{id}", prime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prime.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.imposable").value(DEFAULT_IMPOSABLE.booleanValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrime() throws Exception {
        // Get the prime
        restPrimeMockMvc.perform(get("/api/primes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrime() throws Exception {
        // Initialize the database
        primeService.save(prime);

        int databaseSizeBeforeUpdate = primeRepository.findAll().size();

        // Update the prime
        Prime updatedPrime = primeRepository.findOne(prime.getId());
        // Disconnect from session so that the updates on updatedPrime are not directly saved in db
        em.detach(updatedPrime);
        updatedPrime
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .imposable(UPDATED_IMPOSABLE)
            .deleted(UPDATED_DELETED);

        restPrimeMockMvc.perform(put("/api/primes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrime)))
            .andExpect(status().isOk());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeUpdate);
        Prime testPrime = primeList.get(primeList.size() - 1);
        assertThat(testPrime.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testPrime.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPrime.isImposable()).isEqualTo(UPDATED_IMPOSABLE);
        assertThat(testPrime.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingPrime() throws Exception {
        int databaseSizeBeforeUpdate = primeRepository.findAll().size();

        // Create the Prime

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrimeMockMvc.perform(put("/api/primes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prime)))
            .andExpect(status().isCreated());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrime() throws Exception {
        // Initialize the database
        primeService.save(prime);

        int databaseSizeBeforeDelete = primeRepository.findAll().size();

        // Get the prime
        restPrimeMockMvc.perform(delete("/api/primes/{id}", prime.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prime.class);
        Prime prime1 = new Prime();
        prime1.setId(1L);
        Prime prime2 = new Prime();
        prime2.setId(prime1.getId());
        assertThat(prime1).isEqualTo(prime2);
        prime2.setId(2L);
        assertThat(prime1).isNotEqualTo(prime2);
        prime1.setId(null);
        assertThat(prime1).isNotEqualTo(prime2);
    }
}
