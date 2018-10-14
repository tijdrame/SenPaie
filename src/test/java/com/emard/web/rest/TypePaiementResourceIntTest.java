package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.TypePaiement;
import com.emard.repository.TypePaiementRepository;
import com.emard.service.TypePaiementService;
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
 * Test class for the TypePaiementResource REST controller.
 *
 * @see TypePaiementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class TypePaiementResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private TypePaiementRepository typePaiementRepository;

    @Autowired
    private TypePaiementService typePaiementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypePaiementMockMvc;

    private TypePaiement typePaiement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypePaiementResource typePaiementResource = new TypePaiementResource(typePaiementService);
        this.restTypePaiementMockMvc = MockMvcBuilders.standaloneSetup(typePaiementResource)
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
    public static TypePaiement createEntity(EntityManager em) {
        TypePaiement typePaiement = new TypePaiement()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return typePaiement;
    }

    @Before
    public void initTest() {
        typePaiement = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypePaiement() throws Exception {
        int databaseSizeBeforeCreate = typePaiementRepository.findAll().size();

        // Create the TypePaiement
        restTypePaiementMockMvc.perform(post("/api/type-paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typePaiement)))
            .andExpect(status().isCreated());

        // Validate the TypePaiement in the database
        List<TypePaiement> typePaiementList = typePaiementRepository.findAll();
        assertThat(typePaiementList).hasSize(databaseSizeBeforeCreate + 1);
        TypePaiement testTypePaiement = typePaiementList.get(typePaiementList.size() - 1);
        assertThat(testTypePaiement.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypePaiement.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypePaiement.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createTypePaiementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typePaiementRepository.findAll().size();

        // Create the TypePaiement with an existing ID
        typePaiement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypePaiementMockMvc.perform(post("/api/type-paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typePaiement)))
            .andExpect(status().isBadRequest());

        // Validate the TypePaiement in the database
        List<TypePaiement> typePaiementList = typePaiementRepository.findAll();
        assertThat(typePaiementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typePaiementRepository.findAll().size();
        // set the field null
        typePaiement.setLibelle(null);

        // Create the TypePaiement, which fails.

        restTypePaiementMockMvc.perform(post("/api/type-paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typePaiement)))
            .andExpect(status().isBadRequest());

        List<TypePaiement> typePaiementList = typePaiementRepository.findAll();
        assertThat(typePaiementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typePaiementRepository.findAll().size();
        // set the field null
        typePaiement.setCode(null);

        // Create the TypePaiement, which fails.

        restTypePaiementMockMvc.perform(post("/api/type-paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typePaiement)))
            .andExpect(status().isBadRequest());

        List<TypePaiement> typePaiementList = typePaiementRepository.findAll();
        assertThat(typePaiementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypePaiements() throws Exception {
        // Initialize the database
        typePaiementRepository.saveAndFlush(typePaiement);

        // Get all the typePaiementList
        restTypePaiementMockMvc.perform(get("/api/type-paiements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typePaiement.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getTypePaiement() throws Exception {
        // Initialize the database
        typePaiementRepository.saveAndFlush(typePaiement);

        // Get the typePaiement
        restTypePaiementMockMvc.perform(get("/api/type-paiements/{id}", typePaiement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typePaiement.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypePaiement() throws Exception {
        // Get the typePaiement
        restTypePaiementMockMvc.perform(get("/api/type-paiements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypePaiement() throws Exception {
        // Initialize the database
        typePaiementService.save(typePaiement);

        int databaseSizeBeforeUpdate = typePaiementRepository.findAll().size();

        // Update the typePaiement
        TypePaiement updatedTypePaiement = typePaiementRepository.findOne(typePaiement.getId());
        // Disconnect from session so that the updates on updatedTypePaiement are not directly saved in db
        em.detach(updatedTypePaiement);
        updatedTypePaiement
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restTypePaiementMockMvc.perform(put("/api/type-paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypePaiement)))
            .andExpect(status().isOk());

        // Validate the TypePaiement in the database
        List<TypePaiement> typePaiementList = typePaiementRepository.findAll();
        assertThat(typePaiementList).hasSize(databaseSizeBeforeUpdate);
        TypePaiement testTypePaiement = typePaiementList.get(typePaiementList.size() - 1);
        assertThat(testTypePaiement.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypePaiement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypePaiement.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypePaiement() throws Exception {
        int databaseSizeBeforeUpdate = typePaiementRepository.findAll().size();

        // Create the TypePaiement

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypePaiementMockMvc.perform(put("/api/type-paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typePaiement)))
            .andExpect(status().isCreated());

        // Validate the TypePaiement in the database
        List<TypePaiement> typePaiementList = typePaiementRepository.findAll();
        assertThat(typePaiementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypePaiement() throws Exception {
        // Initialize the database
        typePaiementService.save(typePaiement);

        int databaseSizeBeforeDelete = typePaiementRepository.findAll().size();

        // Get the typePaiement
        restTypePaiementMockMvc.perform(delete("/api/type-paiements/{id}", typePaiement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypePaiement> typePaiementList = typePaiementRepository.findAll();
        assertThat(typePaiementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypePaiement.class);
        TypePaiement typePaiement1 = new TypePaiement();
        typePaiement1.setId(1L);
        TypePaiement typePaiement2 = new TypePaiement();
        typePaiement2.setId(typePaiement1.getId());
        assertThat(typePaiement1).isEqualTo(typePaiement2);
        typePaiement2.setId(2L);
        assertThat(typePaiement1).isNotEqualTo(typePaiement2);
        typePaiement1.setId(null);
        assertThat(typePaiement1).isNotEqualTo(typePaiement2);
    }
}
