package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.Convention;
import com.emard.repository.ConventionRepository;
import com.emard.service.ConventionService;
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
 * Test class for the ConventionResource REST controller.
 *
 * @see ConventionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class ConventionResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private ConventionRepository conventionRepository;

    @Autowired
    private ConventionService conventionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConventionMockMvc;

    private Convention convention;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConventionResource conventionResource = new ConventionResource(conventionService);
        this.restConventionMockMvc = MockMvcBuilders.standaloneSetup(conventionResource)
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
    public static Convention createEntity(EntityManager em) {
        Convention convention = new Convention()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return convention;
    }

    @Before
    public void initTest() {
        convention = createEntity(em);
    }

    @Test
    @Transactional
    public void createConvention() throws Exception {
        int databaseSizeBeforeCreate = conventionRepository.findAll().size();

        // Create the Convention
        restConventionMockMvc.perform(post("/api/conventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(convention)))
            .andExpect(status().isCreated());

        // Validate the Convention in the database
        List<Convention> conventionList = conventionRepository.findAll();
        assertThat(conventionList).hasSize(databaseSizeBeforeCreate + 1);
        Convention testConvention = conventionList.get(conventionList.size() - 1);
        assertThat(testConvention.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testConvention.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testConvention.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createConventionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conventionRepository.findAll().size();

        // Create the Convention with an existing ID
        convention.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConventionMockMvc.perform(post("/api/conventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(convention)))
            .andExpect(status().isBadRequest());

        // Validate the Convention in the database
        List<Convention> conventionList = conventionRepository.findAll();
        assertThat(conventionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = conventionRepository.findAll().size();
        // set the field null
        convention.setLibelle(null);

        // Create the Convention, which fails.

        restConventionMockMvc.perform(post("/api/conventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(convention)))
            .andExpect(status().isBadRequest());

        List<Convention> conventionList = conventionRepository.findAll();
        assertThat(conventionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = conventionRepository.findAll().size();
        // set the field null
        convention.setCode(null);

        // Create the Convention, which fails.

        restConventionMockMvc.perform(post("/api/conventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(convention)))
            .andExpect(status().isBadRequest());

        List<Convention> conventionList = conventionRepository.findAll();
        assertThat(conventionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConventions() throws Exception {
        // Initialize the database
        conventionRepository.saveAndFlush(convention);

        // Get all the conventionList
        restConventionMockMvc.perform(get("/api/conventions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(convention.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getConvention() throws Exception {
        // Initialize the database
        conventionRepository.saveAndFlush(convention);

        // Get the convention
        restConventionMockMvc.perform(get("/api/conventions/{id}", convention.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(convention.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingConvention() throws Exception {
        // Get the convention
        restConventionMockMvc.perform(get("/api/conventions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConvention() throws Exception {
        // Initialize the database
        conventionService.save(convention);

        int databaseSizeBeforeUpdate = conventionRepository.findAll().size();

        // Update the convention
        Convention updatedConvention = conventionRepository.findOne(convention.getId());
        // Disconnect from session so that the updates on updatedConvention are not directly saved in db
        em.detach(updatedConvention);
        updatedConvention
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restConventionMockMvc.perform(put("/api/conventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConvention)))
            .andExpect(status().isOk());

        // Validate the Convention in the database
        List<Convention> conventionList = conventionRepository.findAll();
        assertThat(conventionList).hasSize(databaseSizeBeforeUpdate);
        Convention testConvention = conventionList.get(conventionList.size() - 1);
        assertThat(testConvention.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testConvention.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testConvention.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingConvention() throws Exception {
        int databaseSizeBeforeUpdate = conventionRepository.findAll().size();

        // Create the Convention

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConventionMockMvc.perform(put("/api/conventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(convention)))
            .andExpect(status().isCreated());

        // Validate the Convention in the database
        List<Convention> conventionList = conventionRepository.findAll();
        assertThat(conventionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConvention() throws Exception {
        // Initialize the database
        conventionService.save(convention);

        int databaseSizeBeforeDelete = conventionRepository.findAll().size();

        // Get the convention
        restConventionMockMvc.perform(delete("/api/conventions/{id}", convention.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Convention> conventionList = conventionRepository.findAll();
        assertThat(conventionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Convention.class);
        Convention convention1 = new Convention();
        convention1.setId(1L);
        Convention convention2 = new Convention();
        convention2.setId(convention1.getId());
        assertThat(convention1).isEqualTo(convention2);
        convention2.setId(2L);
        assertThat(convention1).isNotEqualTo(convention2);
        convention1.setId(null);
        assertThat(convention1).isNotEqualTo(convention2);
    }
}
