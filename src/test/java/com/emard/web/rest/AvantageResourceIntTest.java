package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.Avantage;
import com.emard.repository.AvantageRepository;
import com.emard.service.AvantageService;
import com.emard.web.rest.errors.ExceptionTranslator;
import com.emard.service.dto.AvantageCriteria;
import com.emard.service.AvantageQueryService;

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
 * Test class for the AvantageResource REST controller.
 *
 * @see AvantageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class AvantageResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private AvantageRepository avantageRepository;

    @Autowired
    private AvantageService avantageService;

    @Autowired
    private AvantageQueryService avantageQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAvantageMockMvc;

    private Avantage avantage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AvantageResource avantageResource = new AvantageResource(avantageService, avantageQueryService);
        this.restAvantageMockMvc = MockMvcBuilders.standaloneSetup(avantageResource)
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
    public static Avantage createEntity(EntityManager em) {
        Avantage avantage = new Avantage()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return avantage;
    }

    @Before
    public void initTest() {
        avantage = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvantage() throws Exception {
        int databaseSizeBeforeCreate = avantageRepository.findAll().size();

        // Create the Avantage
        restAvantageMockMvc.perform(post("/api/avantages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avantage)))
            .andExpect(status().isCreated());

        // Validate the Avantage in the database
        List<Avantage> avantageList = avantageRepository.findAll();
        assertThat(avantageList).hasSize(databaseSizeBeforeCreate + 1);
        Avantage testAvantage = avantageList.get(avantageList.size() - 1);
        assertThat(testAvantage.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testAvantage.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAvantage.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createAvantageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avantageRepository.findAll().size();

        // Create the Avantage with an existing ID
        avantage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvantageMockMvc.perform(post("/api/avantages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avantage)))
            .andExpect(status().isBadRequest());

        // Validate the Avantage in the database
        List<Avantage> avantageList = avantageRepository.findAll();
        assertThat(avantageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = avantageRepository.findAll().size();
        // set the field null
        avantage.setLibelle(null);

        // Create the Avantage, which fails.

        restAvantageMockMvc.perform(post("/api/avantages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avantage)))
            .andExpect(status().isBadRequest());

        List<Avantage> avantageList = avantageRepository.findAll();
        assertThat(avantageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = avantageRepository.findAll().size();
        // set the field null
        avantage.setCode(null);

        // Create the Avantage, which fails.

        restAvantageMockMvc.perform(post("/api/avantages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avantage)))
            .andExpect(status().isBadRequest());

        List<Avantage> avantageList = avantageRepository.findAll();
        assertThat(avantageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAvantages() throws Exception {
        // Initialize the database
        avantageRepository.saveAndFlush(avantage);

        // Get all the avantageList
        restAvantageMockMvc.perform(get("/api/avantages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avantage.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getAvantage() throws Exception {
        // Initialize the database
        avantageRepository.saveAndFlush(avantage);

        // Get the avantage
        restAvantageMockMvc.perform(get("/api/avantages/{id}", avantage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(avantage.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllAvantagesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        avantageRepository.saveAndFlush(avantage);

        // Get all the avantageList where libelle equals to DEFAULT_LIBELLE
        defaultAvantageShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the avantageList where libelle equals to UPDATED_LIBELLE
        defaultAvantageShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllAvantagesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        avantageRepository.saveAndFlush(avantage);

        // Get all the avantageList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultAvantageShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the avantageList where libelle equals to UPDATED_LIBELLE
        defaultAvantageShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllAvantagesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        avantageRepository.saveAndFlush(avantage);

        // Get all the avantageList where libelle is not null
        defaultAvantageShouldBeFound("libelle.specified=true");

        // Get all the avantageList where libelle is null
        defaultAvantageShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    public void getAllAvantagesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        avantageRepository.saveAndFlush(avantage);

        // Get all the avantageList where code equals to DEFAULT_CODE
        defaultAvantageShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the avantageList where code equals to UPDATED_CODE
        defaultAvantageShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAvantagesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        avantageRepository.saveAndFlush(avantage);

        // Get all the avantageList where code in DEFAULT_CODE or UPDATED_CODE
        defaultAvantageShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the avantageList where code equals to UPDATED_CODE
        defaultAvantageShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAvantagesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        avantageRepository.saveAndFlush(avantage);

        // Get all the avantageList where code is not null
        defaultAvantageShouldBeFound("code.specified=true");

        // Get all the avantageList where code is null
        defaultAvantageShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllAvantagesByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        avantageRepository.saveAndFlush(avantage);

        // Get all the avantageList where deleted equals to DEFAULT_DELETED
        defaultAvantageShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the avantageList where deleted equals to UPDATED_DELETED
        defaultAvantageShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllAvantagesByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        avantageRepository.saveAndFlush(avantage);

        // Get all the avantageList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultAvantageShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the avantageList where deleted equals to UPDATED_DELETED
        defaultAvantageShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllAvantagesByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        avantageRepository.saveAndFlush(avantage);

        // Get all the avantageList where deleted is not null
        defaultAvantageShouldBeFound("deleted.specified=true");

        // Get all the avantageList where deleted is null
        defaultAvantageShouldNotBeFound("deleted.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAvantageShouldBeFound(String filter) throws Exception {
        restAvantageMockMvc.perform(get("/api/avantages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avantage.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAvantageShouldNotBeFound(String filter) throws Exception {
        restAvantageMockMvc.perform(get("/api/avantages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingAvantage() throws Exception {
        // Get the avantage
        restAvantageMockMvc.perform(get("/api/avantages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvantage() throws Exception {
        // Initialize the database
        avantageService.save(avantage);

        int databaseSizeBeforeUpdate = avantageRepository.findAll().size();

        // Update the avantage
        Avantage updatedAvantage = avantageRepository.findOne(avantage.getId());
        // Disconnect from session so that the updates on updatedAvantage are not directly saved in db
        em.detach(updatedAvantage);
        updatedAvantage
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restAvantageMockMvc.perform(put("/api/avantages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvantage)))
            .andExpect(status().isOk());

        // Validate the Avantage in the database
        List<Avantage> avantageList = avantageRepository.findAll();
        assertThat(avantageList).hasSize(databaseSizeBeforeUpdate);
        Avantage testAvantage = avantageList.get(avantageList.size() - 1);
        assertThat(testAvantage.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testAvantage.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAvantage.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingAvantage() throws Exception {
        int databaseSizeBeforeUpdate = avantageRepository.findAll().size();

        // Create the Avantage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAvantageMockMvc.perform(put("/api/avantages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avantage)))
            .andExpect(status().isCreated());

        // Validate the Avantage in the database
        List<Avantage> avantageList = avantageRepository.findAll();
        assertThat(avantageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAvantage() throws Exception {
        // Initialize the database
        avantageService.save(avantage);

        int databaseSizeBeforeDelete = avantageRepository.findAll().size();

        // Get the avantage
        restAvantageMockMvc.perform(delete("/api/avantages/{id}", avantage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Avantage> avantageList = avantageRepository.findAll();
        assertThat(avantageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Avantage.class);
        Avantage avantage1 = new Avantage();
        avantage1.setId(1L);
        Avantage avantage2 = new Avantage();
        avantage2.setId(avantage1.getId());
        assertThat(avantage1).isEqualTo(avantage2);
        avantage2.setId(2L);
        assertThat(avantage1).isNotEqualTo(avantage2);
        avantage1.setId(null);
        assertThat(avantage1).isNotEqualTo(avantage2);
    }
}
