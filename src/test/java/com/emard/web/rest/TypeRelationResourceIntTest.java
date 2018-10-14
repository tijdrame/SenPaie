package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.TypeRelation;
import com.emard.repository.TypeRelationRepository;
import com.emard.service.TypeRelationService;
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
 * Test class for the TypeRelationResource REST controller.
 *
 * @see TypeRelationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class TypeRelationResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Double DEFAULT_NB_PARTS = 1D;
    private static final Double UPDATED_NB_PARTS = 2D;

    @Autowired
    private TypeRelationRepository typeRelationRepository;

    @Autowired
    private TypeRelationService typeRelationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeRelationMockMvc;

    private TypeRelation typeRelation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeRelationResource typeRelationResource = new TypeRelationResource(typeRelationService);
        this.restTypeRelationMockMvc = MockMvcBuilders.standaloneSetup(typeRelationResource)
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
    public static TypeRelation createEntity(EntityManager em) {
        TypeRelation typeRelation = new TypeRelation()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED)
            .nbParts(DEFAULT_NB_PARTS);
        return typeRelation;
    }

    @Before
    public void initTest() {
        typeRelation = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeRelation() throws Exception {
        int databaseSizeBeforeCreate = typeRelationRepository.findAll().size();

        // Create the TypeRelation
        restTypeRelationMockMvc.perform(post("/api/type-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeRelation)))
            .andExpect(status().isCreated());

        // Validate the TypeRelation in the database
        List<TypeRelation> typeRelationList = typeRelationRepository.findAll();
        assertThat(typeRelationList).hasSize(databaseSizeBeforeCreate + 1);
        TypeRelation testTypeRelation = typeRelationList.get(typeRelationList.size() - 1);
        assertThat(testTypeRelation.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeRelation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeRelation.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testTypeRelation.getNbParts()).isEqualTo(DEFAULT_NB_PARTS);
    }

    @Test
    @Transactional
    public void createTypeRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeRelationRepository.findAll().size();

        // Create the TypeRelation with an existing ID
        typeRelation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeRelationMockMvc.perform(post("/api/type-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeRelation)))
            .andExpect(status().isBadRequest());

        // Validate the TypeRelation in the database
        List<TypeRelation> typeRelationList = typeRelationRepository.findAll();
        assertThat(typeRelationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeRelationRepository.findAll().size();
        // set the field null
        typeRelation.setLibelle(null);

        // Create the TypeRelation, which fails.

        restTypeRelationMockMvc.perform(post("/api/type-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeRelation)))
            .andExpect(status().isBadRequest());

        List<TypeRelation> typeRelationList = typeRelationRepository.findAll();
        assertThat(typeRelationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeRelationRepository.findAll().size();
        // set the field null
        typeRelation.setCode(null);

        // Create the TypeRelation, which fails.

        restTypeRelationMockMvc.perform(post("/api/type-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeRelation)))
            .andExpect(status().isBadRequest());

        List<TypeRelation> typeRelationList = typeRelationRepository.findAll();
        assertThat(typeRelationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNbPartsIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeRelationRepository.findAll().size();
        // set the field null
        typeRelation.setNbParts(null);

        // Create the TypeRelation, which fails.

        restTypeRelationMockMvc.perform(post("/api/type-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeRelation)))
            .andExpect(status().isBadRequest());

        List<TypeRelation> typeRelationList = typeRelationRepository.findAll();
        assertThat(typeRelationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeRelations() throws Exception {
        // Initialize the database
        typeRelationRepository.saveAndFlush(typeRelation);

        // Get all the typeRelationList
        restTypeRelationMockMvc.perform(get("/api/type-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].nbParts").value(hasItem(DEFAULT_NB_PARTS.doubleValue())));
    }

    @Test
    @Transactional
    public void getTypeRelation() throws Exception {
        // Initialize the database
        typeRelationRepository.saveAndFlush(typeRelation);

        // Get the typeRelation
        restTypeRelationMockMvc.perform(get("/api/type-relations/{id}", typeRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeRelation.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.nbParts").value(DEFAULT_NB_PARTS.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeRelation() throws Exception {
        // Get the typeRelation
        restTypeRelationMockMvc.perform(get("/api/type-relations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeRelation() throws Exception {
        // Initialize the database
        typeRelationService.save(typeRelation);

        int databaseSizeBeforeUpdate = typeRelationRepository.findAll().size();

        // Update the typeRelation
        TypeRelation updatedTypeRelation = typeRelationRepository.findOne(typeRelation.getId());
        // Disconnect from session so that the updates on updatedTypeRelation are not directly saved in db
        em.detach(updatedTypeRelation);
        updatedTypeRelation
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED)
            .nbParts(UPDATED_NB_PARTS);

        restTypeRelationMockMvc.perform(put("/api/type-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeRelation)))
            .andExpect(status().isOk());

        // Validate the TypeRelation in the database
        List<TypeRelation> typeRelationList = typeRelationRepository.findAll();
        assertThat(typeRelationList).hasSize(databaseSizeBeforeUpdate);
        TypeRelation testTypeRelation = typeRelationList.get(typeRelationList.size() - 1);
        assertThat(testTypeRelation.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeRelation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeRelation.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testTypeRelation.getNbParts()).isEqualTo(UPDATED_NB_PARTS);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeRelation() throws Exception {
        int databaseSizeBeforeUpdate = typeRelationRepository.findAll().size();

        // Create the TypeRelation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypeRelationMockMvc.perform(put("/api/type-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeRelation)))
            .andExpect(status().isCreated());

        // Validate the TypeRelation in the database
        List<TypeRelation> typeRelationList = typeRelationRepository.findAll();
        assertThat(typeRelationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypeRelation() throws Exception {
        // Initialize the database
        typeRelationService.save(typeRelation);

        int databaseSizeBeforeDelete = typeRelationRepository.findAll().size();

        // Get the typeRelation
        restTypeRelationMockMvc.perform(delete("/api/type-relations/{id}", typeRelation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeRelation> typeRelationList = typeRelationRepository.findAll();
        assertThat(typeRelationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeRelation.class);
        TypeRelation typeRelation1 = new TypeRelation();
        typeRelation1.setId(1L);
        TypeRelation typeRelation2 = new TypeRelation();
        typeRelation2.setId(typeRelation1.getId());
        assertThat(typeRelation1).isEqualTo(typeRelation2);
        typeRelation2.setId(2L);
        assertThat(typeRelation1).isNotEqualTo(typeRelation2);
        typeRelation1.setId(null);
        assertThat(typeRelation1).isNotEqualTo(typeRelation2);
    }
}
