package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.MoisConcerne;
import com.emard.repository.MoisConcerneRepository;
import com.emard.service.MoisConcerneService;
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
 * Test class for the MoisConcerneResource REST controller.
 *
 * @see MoisConcerneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class MoisConcerneResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private MoisConcerneRepository moisConcerneRepository;

    @Autowired
    private MoisConcerneService moisConcerneService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMoisConcerneMockMvc;

    private MoisConcerne moisConcerne;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MoisConcerneResource moisConcerneResource = new MoisConcerneResource(moisConcerneService);
        this.restMoisConcerneMockMvc = MockMvcBuilders.standaloneSetup(moisConcerneResource)
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
    public static MoisConcerne createEntity(EntityManager em) {
        MoisConcerne moisConcerne = new MoisConcerne()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return moisConcerne;
    }

    @Before
    public void initTest() {
        moisConcerne = createEntity(em);
    }

    @Test
    @Transactional
    public void createMoisConcerne() throws Exception {
        int databaseSizeBeforeCreate = moisConcerneRepository.findAll().size();

        // Create the MoisConcerne
        restMoisConcerneMockMvc.perform(post("/api/mois-concernes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moisConcerne)))
            .andExpect(status().isCreated());

        // Validate the MoisConcerne in the database
        List<MoisConcerne> moisConcerneList = moisConcerneRepository.findAll();
        assertThat(moisConcerneList).hasSize(databaseSizeBeforeCreate + 1);
        MoisConcerne testMoisConcerne = moisConcerneList.get(moisConcerneList.size() - 1);
        assertThat(testMoisConcerne.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testMoisConcerne.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMoisConcerne.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createMoisConcerneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moisConcerneRepository.findAll().size();

        // Create the MoisConcerne with an existing ID
        moisConcerne.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoisConcerneMockMvc.perform(post("/api/mois-concernes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moisConcerne)))
            .andExpect(status().isBadRequest());

        // Validate the MoisConcerne in the database
        List<MoisConcerne> moisConcerneList = moisConcerneRepository.findAll();
        assertThat(moisConcerneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = moisConcerneRepository.findAll().size();
        // set the field null
        moisConcerne.setLibelle(null);

        // Create the MoisConcerne, which fails.

        restMoisConcerneMockMvc.perform(post("/api/mois-concernes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moisConcerne)))
            .andExpect(status().isBadRequest());

        List<MoisConcerne> moisConcerneList = moisConcerneRepository.findAll();
        assertThat(moisConcerneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = moisConcerneRepository.findAll().size();
        // set the field null
        moisConcerne.setCode(null);

        // Create the MoisConcerne, which fails.

        restMoisConcerneMockMvc.perform(post("/api/mois-concernes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moisConcerne)))
            .andExpect(status().isBadRequest());

        List<MoisConcerne> moisConcerneList = moisConcerneRepository.findAll();
        assertThat(moisConcerneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMoisConcernes() throws Exception {
        // Initialize the database
        moisConcerneRepository.saveAndFlush(moisConcerne);

        // Get all the moisConcerneList
        restMoisConcerneMockMvc.perform(get("/api/mois-concernes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moisConcerne.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getMoisConcerne() throws Exception {
        // Initialize the database
        moisConcerneRepository.saveAndFlush(moisConcerne);

        // Get the moisConcerne
        restMoisConcerneMockMvc.perform(get("/api/mois-concernes/{id}", moisConcerne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(moisConcerne.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMoisConcerne() throws Exception {
        // Get the moisConcerne
        restMoisConcerneMockMvc.perform(get("/api/mois-concernes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMoisConcerne() throws Exception {
        // Initialize the database
        moisConcerneService.save(moisConcerne);

        int databaseSizeBeforeUpdate = moisConcerneRepository.findAll().size();

        // Update the moisConcerne
        MoisConcerne updatedMoisConcerne = moisConcerneRepository.findOne(moisConcerne.getId());
        // Disconnect from session so that the updates on updatedMoisConcerne are not directly saved in db
        em.detach(updatedMoisConcerne);
        updatedMoisConcerne
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restMoisConcerneMockMvc.perform(put("/api/mois-concernes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMoisConcerne)))
            .andExpect(status().isOk());

        // Validate the MoisConcerne in the database
        List<MoisConcerne> moisConcerneList = moisConcerneRepository.findAll();
        assertThat(moisConcerneList).hasSize(databaseSizeBeforeUpdate);
        MoisConcerne testMoisConcerne = moisConcerneList.get(moisConcerneList.size() - 1);
        assertThat(testMoisConcerne.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testMoisConcerne.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMoisConcerne.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingMoisConcerne() throws Exception {
        int databaseSizeBeforeUpdate = moisConcerneRepository.findAll().size();

        // Create the MoisConcerne

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMoisConcerneMockMvc.perform(put("/api/mois-concernes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moisConcerne)))
            .andExpect(status().isCreated());

        // Validate the MoisConcerne in the database
        List<MoisConcerne> moisConcerneList = moisConcerneRepository.findAll();
        assertThat(moisConcerneList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMoisConcerne() throws Exception {
        // Initialize the database
        moisConcerneService.save(moisConcerne);

        int databaseSizeBeforeDelete = moisConcerneRepository.findAll().size();

        // Get the moisConcerne
        restMoisConcerneMockMvc.perform(delete("/api/mois-concernes/{id}", moisConcerne.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MoisConcerne> moisConcerneList = moisConcerneRepository.findAll();
        assertThat(moisConcerneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoisConcerne.class);
        MoisConcerne moisConcerne1 = new MoisConcerne();
        moisConcerne1.setId(1L);
        MoisConcerne moisConcerne2 = new MoisConcerne();
        moisConcerne2.setId(moisConcerne1.getId());
        assertThat(moisConcerne1).isEqualTo(moisConcerne2);
        moisConcerne2.setId(2L);
        assertThat(moisConcerne1).isNotEqualTo(moisConcerne2);
        moisConcerne1.setId(null);
        assertThat(moisConcerne1).isNotEqualTo(moisConcerne2);
    }
}
