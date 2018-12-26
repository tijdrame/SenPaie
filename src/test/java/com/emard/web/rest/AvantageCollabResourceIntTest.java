package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.AvantageCollab;
import com.emard.domain.Collaborateur;
import com.emard.domain.Avantage;
import com.emard.repository.AvantageCollabRepository;
import com.emard.service.AvantageCollabService;
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
 * Test class for the AvantageCollabResource REST controller.
 *
 * @see AvantageCollabResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class AvantageCollabResourceIntTest {

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private AvantageCollabRepository avantageCollabRepository;

    @Autowired
    private AvantageCollabService avantageCollabService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAvantageCollabMockMvc;

    private AvantageCollab avantageCollab;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AvantageCollabResource avantageCollabResource = new AvantageCollabResource(avantageCollabService);
        this.restAvantageCollabMockMvc = MockMvcBuilders.standaloneSetup(avantageCollabResource)
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
    public static AvantageCollab createEntity(EntityManager em) {
        AvantageCollab avantageCollab = new AvantageCollab()
            .montant(DEFAULT_MONTANT)
            .deleted(DEFAULT_DELETED);
        // Add required entity
        Collaborateur collaborateur = CollaborateurResourceIntTest.createEntity(em);
        em.persist(collaborateur);
        em.flush();
        avantageCollab.setCollaborateur(collaborateur);
        // Add required entity
        Avantage avantage = AvantageResourceIntTest.createEntity(em);
        em.persist(avantage);
        em.flush();
        avantageCollab.setAvantage(avantage);
        return avantageCollab;
    }

    @Before
    public void initTest() {
        avantageCollab = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvantageCollab() throws Exception {
        int databaseSizeBeforeCreate = avantageCollabRepository.findAll().size();

        // Create the AvantageCollab
        restAvantageCollabMockMvc.perform(post("/api/avantage-collabs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avantageCollab)))
            .andExpect(status().isCreated());

        // Validate the AvantageCollab in the database
        List<AvantageCollab> avantageCollabList = avantageCollabRepository.findAll();
        assertThat(avantageCollabList).hasSize(databaseSizeBeforeCreate + 1);
        AvantageCollab testAvantageCollab = avantageCollabList.get(avantageCollabList.size() - 1);
        assertThat(testAvantageCollab.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testAvantageCollab.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createAvantageCollabWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avantageCollabRepository.findAll().size();

        // Create the AvantageCollab with an existing ID
        avantageCollab.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvantageCollabMockMvc.perform(post("/api/avantage-collabs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avantageCollab)))
            .andExpect(status().isBadRequest());

        // Validate the AvantageCollab in the database
        List<AvantageCollab> avantageCollabList = avantageCollabRepository.findAll();
        assertThat(avantageCollabList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = avantageCollabRepository.findAll().size();
        // set the field null
        avantageCollab.setMontant(null);

        // Create the AvantageCollab, which fails.

        restAvantageCollabMockMvc.perform(post("/api/avantage-collabs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avantageCollab)))
            .andExpect(status().isBadRequest());

        List<AvantageCollab> avantageCollabList = avantageCollabRepository.findAll();
        assertThat(avantageCollabList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAvantageCollabs() throws Exception {
        // Initialize the database
        avantageCollabRepository.saveAndFlush(avantageCollab);

        // Get all the avantageCollabList
        restAvantageCollabMockMvc.perform(get("/api/avantage-collabs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avantageCollab.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getAvantageCollab() throws Exception {
        // Initialize the database
        avantageCollabRepository.saveAndFlush(avantageCollab);

        // Get the avantageCollab
        restAvantageCollabMockMvc.perform(get("/api/avantage-collabs/{id}", avantageCollab.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(avantageCollab.getId().intValue()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAvantageCollab() throws Exception {
        // Get the avantageCollab
        restAvantageCollabMockMvc.perform(get("/api/avantage-collabs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvantageCollab() throws Exception {
        // Initialize the database
        avantageCollabService.save(avantageCollab);

        int databaseSizeBeforeUpdate = avantageCollabRepository.findAll().size();

        // Update the avantageCollab
        AvantageCollab updatedAvantageCollab = avantageCollabRepository.findOne(avantageCollab.getId());
        // Disconnect from session so that the updates on updatedAvantageCollab are not directly saved in db
        em.detach(updatedAvantageCollab);
        updatedAvantageCollab
            .montant(UPDATED_MONTANT)
            .deleted(UPDATED_DELETED);

        restAvantageCollabMockMvc.perform(put("/api/avantage-collabs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvantageCollab)))
            .andExpect(status().isOk());

        // Validate the AvantageCollab in the database
        List<AvantageCollab> avantageCollabList = avantageCollabRepository.findAll();
        assertThat(avantageCollabList).hasSize(databaseSizeBeforeUpdate);
        AvantageCollab testAvantageCollab = avantageCollabList.get(avantageCollabList.size() - 1);
        assertThat(testAvantageCollab.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testAvantageCollab.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingAvantageCollab() throws Exception {
        int databaseSizeBeforeUpdate = avantageCollabRepository.findAll().size();

        // Create the AvantageCollab

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAvantageCollabMockMvc.perform(put("/api/avantage-collabs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avantageCollab)))
            .andExpect(status().isCreated());

        // Validate the AvantageCollab in the database
        List<AvantageCollab> avantageCollabList = avantageCollabRepository.findAll();
        assertThat(avantageCollabList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAvantageCollab() throws Exception {
        // Initialize the database
        avantageCollabService.save(avantageCollab);

        int databaseSizeBeforeDelete = avantageCollabRepository.findAll().size();

        // Get the avantageCollab
        restAvantageCollabMockMvc.perform(delete("/api/avantage-collabs/{id}", avantageCollab.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AvantageCollab> avantageCollabList = avantageCollabRepository.findAll();
        assertThat(avantageCollabList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvantageCollab.class);
        AvantageCollab avantageCollab1 = new AvantageCollab();
        avantageCollab1.setId(1L);
        AvantageCollab avantageCollab2 = new AvantageCollab();
        avantageCollab2.setId(avantageCollab1.getId());
        assertThat(avantageCollab1).isEqualTo(avantageCollab2);
        avantageCollab2.setId(2L);
        assertThat(avantageCollab1).isNotEqualTo(avantageCollab2);
        avantageCollab1.setId(null);
        assertThat(avantageCollab1).isNotEqualTo(avantageCollab2);
    }
}
