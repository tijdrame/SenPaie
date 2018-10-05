package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.Nationalite;
import com.emard.repository.NationaliteRepository;
import com.emard.service.NationaliteService;
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
 * Test class for the NationaliteResource REST controller.
 *
 * @see NationaliteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class NationaliteResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private NationaliteRepository nationaliteRepository;

    @Autowired
    private NationaliteService nationaliteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNationaliteMockMvc;

    private Nationalite nationalite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NationaliteResource nationaliteResource = new NationaliteResource(nationaliteService);
        this.restNationaliteMockMvc = MockMvcBuilders.standaloneSetup(nationaliteResource)
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
    public static Nationalite createEntity(EntityManager em) {
        Nationalite nationalite = new Nationalite()
            .libelle(DEFAULT_LIBELLE)
            .deleted(DEFAULT_DELETED);
        return nationalite;
    }

    @Before
    public void initTest() {
        nationalite = createEntity(em);
    }

    @Test
    @Transactional
    public void createNationalite() throws Exception {
        int databaseSizeBeforeCreate = nationaliteRepository.findAll().size();

        // Create the Nationalite
        restNationaliteMockMvc.perform(post("/api/nationalites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nationalite)))
            .andExpect(status().isCreated());

        // Validate the Nationalite in the database
        List<Nationalite> nationaliteList = nationaliteRepository.findAll();
        assertThat(nationaliteList).hasSize(databaseSizeBeforeCreate + 1);
        Nationalite testNationalite = nationaliteList.get(nationaliteList.size() - 1);
        assertThat(testNationalite.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testNationalite.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createNationaliteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nationaliteRepository.findAll().size();

        // Create the Nationalite with an existing ID
        nationalite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNationaliteMockMvc.perform(post("/api/nationalites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nationalite)))
            .andExpect(status().isBadRequest());

        // Validate the Nationalite in the database
        List<Nationalite> nationaliteList = nationaliteRepository.findAll();
        assertThat(nationaliteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = nationaliteRepository.findAll().size();
        // set the field null
        nationalite.setLibelle(null);

        // Create the Nationalite, which fails.

        restNationaliteMockMvc.perform(post("/api/nationalites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nationalite)))
            .andExpect(status().isBadRequest());

        List<Nationalite> nationaliteList = nationaliteRepository.findAll();
        assertThat(nationaliteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNationalites() throws Exception {
        // Initialize the database
        nationaliteRepository.saveAndFlush(nationalite);

        // Get all the nationaliteList
        restNationaliteMockMvc.perform(get("/api/nationalites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nationalite.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getNationalite() throws Exception {
        // Initialize the database
        nationaliteRepository.saveAndFlush(nationalite);

        // Get the nationalite
        restNationaliteMockMvc.perform(get("/api/nationalites/{id}", nationalite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nationalite.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNationalite() throws Exception {
        // Get the nationalite
        restNationaliteMockMvc.perform(get("/api/nationalites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNationalite() throws Exception {
        // Initialize the database
        nationaliteService.save(nationalite);

        int databaseSizeBeforeUpdate = nationaliteRepository.findAll().size();

        // Update the nationalite
        Nationalite updatedNationalite = nationaliteRepository.findOne(nationalite.getId());
        // Disconnect from session so that the updates on updatedNationalite are not directly saved in db
        em.detach(updatedNationalite);
        updatedNationalite
            .libelle(UPDATED_LIBELLE)
            .deleted(UPDATED_DELETED);

        restNationaliteMockMvc.perform(put("/api/nationalites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNationalite)))
            .andExpect(status().isOk());

        // Validate the Nationalite in the database
        List<Nationalite> nationaliteList = nationaliteRepository.findAll();
        assertThat(nationaliteList).hasSize(databaseSizeBeforeUpdate);
        Nationalite testNationalite = nationaliteList.get(nationaliteList.size() - 1);
        assertThat(testNationalite.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testNationalite.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingNationalite() throws Exception {
        int databaseSizeBeforeUpdate = nationaliteRepository.findAll().size();

        // Create the Nationalite

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNationaliteMockMvc.perform(put("/api/nationalites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nationalite)))
            .andExpect(status().isCreated());

        // Validate the Nationalite in the database
        List<Nationalite> nationaliteList = nationaliteRepository.findAll();
        assertThat(nationaliteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNationalite() throws Exception {
        // Initialize the database
        nationaliteService.save(nationalite);

        int databaseSizeBeforeDelete = nationaliteRepository.findAll().size();

        // Get the nationalite
        restNationaliteMockMvc.perform(delete("/api/nationalites/{id}", nationalite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Nationalite> nationaliteList = nationaliteRepository.findAll();
        assertThat(nationaliteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nationalite.class);
        Nationalite nationalite1 = new Nationalite();
        nationalite1.setId(1L);
        Nationalite nationalite2 = new Nationalite();
        nationalite2.setId(nationalite1.getId());
        assertThat(nationalite1).isEqualTo(nationalite2);
        nationalite2.setId(2L);
        assertThat(nationalite1).isNotEqualTo(nationalite2);
        nationalite1.setId(null);
        assertThat(nationalite1).isNotEqualTo(nationalite2);
    }
}
