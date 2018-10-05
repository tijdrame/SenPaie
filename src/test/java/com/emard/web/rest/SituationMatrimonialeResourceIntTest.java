package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.SituationMatrimoniale;
import com.emard.repository.SituationMatrimonialeRepository;
import com.emard.service.SituationMatrimonialeService;
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
 * Test class for the SituationMatrimonialeResource REST controller.
 *
 * @see SituationMatrimonialeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class SituationMatrimonialeResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private SituationMatrimonialeRepository situationMatrimonialeRepository;

    @Autowired
    private SituationMatrimonialeService situationMatrimonialeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSituationMatrimonialeMockMvc;

    private SituationMatrimoniale situationMatrimoniale;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SituationMatrimonialeResource situationMatrimonialeResource = new SituationMatrimonialeResource(situationMatrimonialeService);
        this.restSituationMatrimonialeMockMvc = MockMvcBuilders.standaloneSetup(situationMatrimonialeResource)
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
    public static SituationMatrimoniale createEntity(EntityManager em) {
        SituationMatrimoniale situationMatrimoniale = new SituationMatrimoniale()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return situationMatrimoniale;
    }

    @Before
    public void initTest() {
        situationMatrimoniale = createEntity(em);
    }

    @Test
    @Transactional
    public void createSituationMatrimoniale() throws Exception {
        int databaseSizeBeforeCreate = situationMatrimonialeRepository.findAll().size();

        // Create the SituationMatrimoniale
        restSituationMatrimonialeMockMvc.perform(post("/api/situation-matrimoniales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situationMatrimoniale)))
            .andExpect(status().isCreated());

        // Validate the SituationMatrimoniale in the database
        List<SituationMatrimoniale> situationMatrimonialeList = situationMatrimonialeRepository.findAll();
        assertThat(situationMatrimonialeList).hasSize(databaseSizeBeforeCreate + 1);
        SituationMatrimoniale testSituationMatrimoniale = situationMatrimonialeList.get(situationMatrimonialeList.size() - 1);
        assertThat(testSituationMatrimoniale.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testSituationMatrimoniale.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSituationMatrimoniale.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createSituationMatrimonialeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = situationMatrimonialeRepository.findAll().size();

        // Create the SituationMatrimoniale with an existing ID
        situationMatrimoniale.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSituationMatrimonialeMockMvc.perform(post("/api/situation-matrimoniales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situationMatrimoniale)))
            .andExpect(status().isBadRequest());

        // Validate the SituationMatrimoniale in the database
        List<SituationMatrimoniale> situationMatrimonialeList = situationMatrimonialeRepository.findAll();
        assertThat(situationMatrimonialeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = situationMatrimonialeRepository.findAll().size();
        // set the field null
        situationMatrimoniale.setLibelle(null);

        // Create the SituationMatrimoniale, which fails.

        restSituationMatrimonialeMockMvc.perform(post("/api/situation-matrimoniales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situationMatrimoniale)))
            .andExpect(status().isBadRequest());

        List<SituationMatrimoniale> situationMatrimonialeList = situationMatrimonialeRepository.findAll();
        assertThat(situationMatrimonialeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = situationMatrimonialeRepository.findAll().size();
        // set the field null
        situationMatrimoniale.setCode(null);

        // Create the SituationMatrimoniale, which fails.

        restSituationMatrimonialeMockMvc.perform(post("/api/situation-matrimoniales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situationMatrimoniale)))
            .andExpect(status().isBadRequest());

        List<SituationMatrimoniale> situationMatrimonialeList = situationMatrimonialeRepository.findAll();
        assertThat(situationMatrimonialeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSituationMatrimoniales() throws Exception {
        // Initialize the database
        situationMatrimonialeRepository.saveAndFlush(situationMatrimoniale);

        // Get all the situationMatrimonialeList
        restSituationMatrimonialeMockMvc.perform(get("/api/situation-matrimoniales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(situationMatrimoniale.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getSituationMatrimoniale() throws Exception {
        // Initialize the database
        situationMatrimonialeRepository.saveAndFlush(situationMatrimoniale);

        // Get the situationMatrimoniale
        restSituationMatrimonialeMockMvc.perform(get("/api/situation-matrimoniales/{id}", situationMatrimoniale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(situationMatrimoniale.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSituationMatrimoniale() throws Exception {
        // Get the situationMatrimoniale
        restSituationMatrimonialeMockMvc.perform(get("/api/situation-matrimoniales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSituationMatrimoniale() throws Exception {
        // Initialize the database
        situationMatrimonialeService.save(situationMatrimoniale);

        int databaseSizeBeforeUpdate = situationMatrimonialeRepository.findAll().size();

        // Update the situationMatrimoniale
        SituationMatrimoniale updatedSituationMatrimoniale = situationMatrimonialeRepository.findOne(situationMatrimoniale.getId());
        // Disconnect from session so that the updates on updatedSituationMatrimoniale are not directly saved in db
        em.detach(updatedSituationMatrimoniale);
        updatedSituationMatrimoniale
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restSituationMatrimonialeMockMvc.perform(put("/api/situation-matrimoniales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSituationMatrimoniale)))
            .andExpect(status().isOk());

        // Validate the SituationMatrimoniale in the database
        List<SituationMatrimoniale> situationMatrimonialeList = situationMatrimonialeRepository.findAll();
        assertThat(situationMatrimonialeList).hasSize(databaseSizeBeforeUpdate);
        SituationMatrimoniale testSituationMatrimoniale = situationMatrimonialeList.get(situationMatrimonialeList.size() - 1);
        assertThat(testSituationMatrimoniale.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testSituationMatrimoniale.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSituationMatrimoniale.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingSituationMatrimoniale() throws Exception {
        int databaseSizeBeforeUpdate = situationMatrimonialeRepository.findAll().size();

        // Create the SituationMatrimoniale

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSituationMatrimonialeMockMvc.perform(put("/api/situation-matrimoniales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situationMatrimoniale)))
            .andExpect(status().isCreated());

        // Validate the SituationMatrimoniale in the database
        List<SituationMatrimoniale> situationMatrimonialeList = situationMatrimonialeRepository.findAll();
        assertThat(situationMatrimonialeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSituationMatrimoniale() throws Exception {
        // Initialize the database
        situationMatrimonialeService.save(situationMatrimoniale);

        int databaseSizeBeforeDelete = situationMatrimonialeRepository.findAll().size();

        // Get the situationMatrimoniale
        restSituationMatrimonialeMockMvc.perform(delete("/api/situation-matrimoniales/{id}", situationMatrimoniale.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SituationMatrimoniale> situationMatrimonialeList = situationMatrimonialeRepository.findAll();
        assertThat(situationMatrimonialeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SituationMatrimoniale.class);
        SituationMatrimoniale situationMatrimoniale1 = new SituationMatrimoniale();
        situationMatrimoniale1.setId(1L);
        SituationMatrimoniale situationMatrimoniale2 = new SituationMatrimoniale();
        situationMatrimoniale2.setId(situationMatrimoniale1.getId());
        assertThat(situationMatrimoniale1).isEqualTo(situationMatrimoniale2);
        situationMatrimoniale2.setId(2L);
        assertThat(situationMatrimoniale1).isNotEqualTo(situationMatrimoniale2);
        situationMatrimoniale1.setId(null);
        assertThat(situationMatrimoniale1).isNotEqualTo(situationMatrimoniale2);
    }
}
