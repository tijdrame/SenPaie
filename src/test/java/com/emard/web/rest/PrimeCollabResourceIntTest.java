package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.PrimeCollab;
import com.emard.domain.Collaborateur;
import com.emard.domain.Prime;
import com.emard.repository.PrimeCollabRepository;
import com.emard.service.PrimeCollabService;
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
 * Test class for the PrimeCollabResource REST controller.
 *
 * @see PrimeCollabResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class PrimeCollabResourceIntTest {

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private PrimeCollabRepository primeCollabRepository;

    @Autowired
    private PrimeCollabService primeCollabService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrimeCollabMockMvc;

    private PrimeCollab primeCollab;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrimeCollabResource primeCollabResource = new PrimeCollabResource(primeCollabService);
        this.restPrimeCollabMockMvc = MockMvcBuilders.standaloneSetup(primeCollabResource)
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
    public static PrimeCollab createEntity(EntityManager em) {
        PrimeCollab primeCollab = new PrimeCollab()
            .montant(DEFAULT_MONTANT)
            .deleted(DEFAULT_DELETED);
        // Add required entity
        Collaborateur collaborateur = CollaborateurResourceIntTest.createEntity(em);
        em.persist(collaborateur);
        em.flush();
        primeCollab.setCollaborateur(collaborateur);
        // Add required entity
        Prime prime = PrimeResourceIntTest.createEntity(em);
        em.persist(prime);
        em.flush();
        primeCollab.setPrime(prime);
        return primeCollab;
    }

    @Before
    public void initTest() {
        primeCollab = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrimeCollab() throws Exception {
        int databaseSizeBeforeCreate = primeCollabRepository.findAll().size();

        // Create the PrimeCollab
        restPrimeCollabMockMvc.perform(post("/api/prime-collabs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(primeCollab)))
            .andExpect(status().isCreated());

        // Validate the PrimeCollab in the database
        List<PrimeCollab> primeCollabList = primeCollabRepository.findAll();
        assertThat(primeCollabList).hasSize(databaseSizeBeforeCreate + 1);
        PrimeCollab testPrimeCollab = primeCollabList.get(primeCollabList.size() - 1);
        assertThat(testPrimeCollab.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testPrimeCollab.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createPrimeCollabWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = primeCollabRepository.findAll().size();

        // Create the PrimeCollab with an existing ID
        primeCollab.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrimeCollabMockMvc.perform(post("/api/prime-collabs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(primeCollab)))
            .andExpect(status().isBadRequest());

        // Validate the PrimeCollab in the database
        List<PrimeCollab> primeCollabList = primeCollabRepository.findAll();
        assertThat(primeCollabList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = primeCollabRepository.findAll().size();
        // set the field null
        primeCollab.setMontant(null);

        // Create the PrimeCollab, which fails.

        restPrimeCollabMockMvc.perform(post("/api/prime-collabs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(primeCollab)))
            .andExpect(status().isBadRequest());

        List<PrimeCollab> primeCollabList = primeCollabRepository.findAll();
        assertThat(primeCollabList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrimeCollabs() throws Exception {
        // Initialize the database
        primeCollabRepository.saveAndFlush(primeCollab);

        // Get all the primeCollabList
        restPrimeCollabMockMvc.perform(get("/api/prime-collabs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(primeCollab.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getPrimeCollab() throws Exception {
        // Initialize the database
        primeCollabRepository.saveAndFlush(primeCollab);

        // Get the primeCollab
        restPrimeCollabMockMvc.perform(get("/api/prime-collabs/{id}", primeCollab.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(primeCollab.getId().intValue()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrimeCollab() throws Exception {
        // Get the primeCollab
        restPrimeCollabMockMvc.perform(get("/api/prime-collabs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrimeCollab() throws Exception {
        // Initialize the database
        primeCollabService.save(primeCollab);

        int databaseSizeBeforeUpdate = primeCollabRepository.findAll().size();

        // Update the primeCollab
        PrimeCollab updatedPrimeCollab = primeCollabRepository.findOne(primeCollab.getId());
        // Disconnect from session so that the updates on updatedPrimeCollab are not directly saved in db
        em.detach(updatedPrimeCollab);
        updatedPrimeCollab
            .montant(UPDATED_MONTANT)
            .deleted(UPDATED_DELETED);

        restPrimeCollabMockMvc.perform(put("/api/prime-collabs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrimeCollab)))
            .andExpect(status().isOk());

        // Validate the PrimeCollab in the database
        List<PrimeCollab> primeCollabList = primeCollabRepository.findAll();
        assertThat(primeCollabList).hasSize(databaseSizeBeforeUpdate);
        PrimeCollab testPrimeCollab = primeCollabList.get(primeCollabList.size() - 1);
        assertThat(testPrimeCollab.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testPrimeCollab.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingPrimeCollab() throws Exception {
        int databaseSizeBeforeUpdate = primeCollabRepository.findAll().size();

        // Create the PrimeCollab

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrimeCollabMockMvc.perform(put("/api/prime-collabs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(primeCollab)))
            .andExpect(status().isCreated());

        // Validate the PrimeCollab in the database
        List<PrimeCollab> primeCollabList = primeCollabRepository.findAll();
        assertThat(primeCollabList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrimeCollab() throws Exception {
        // Initialize the database
        primeCollabService.save(primeCollab);

        int databaseSizeBeforeDelete = primeCollabRepository.findAll().size();

        // Get the primeCollab
        restPrimeCollabMockMvc.perform(delete("/api/prime-collabs/{id}", primeCollab.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrimeCollab> primeCollabList = primeCollabRepository.findAll();
        assertThat(primeCollabList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrimeCollab.class);
        PrimeCollab primeCollab1 = new PrimeCollab();
        primeCollab1.setId(1L);
        PrimeCollab primeCollab2 = new PrimeCollab();
        primeCollab2.setId(primeCollab1.getId());
        assertThat(primeCollab1).isEqualTo(primeCollab2);
        primeCollab2.setId(2L);
        assertThat(primeCollab1).isNotEqualTo(primeCollab2);
        primeCollab1.setId(null);
        assertThat(primeCollab1).isNotEqualTo(primeCollab2);
    }
}
