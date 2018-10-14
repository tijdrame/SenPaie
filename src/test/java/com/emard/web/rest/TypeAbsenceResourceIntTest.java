package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.TypeAbsence;
import com.emard.repository.TypeAbsenceRepository;
import com.emard.service.TypeAbsenceService;
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
 * Test class for the TypeAbsenceResource REST controller.
 *
 * @see TypeAbsenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class TypeAbsenceResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private TypeAbsenceRepository typeAbsenceRepository;

    @Autowired
    private TypeAbsenceService typeAbsenceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeAbsenceMockMvc;

    private TypeAbsence typeAbsence;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeAbsenceResource typeAbsenceResource = new TypeAbsenceResource(typeAbsenceService);
        this.restTypeAbsenceMockMvc = MockMvcBuilders.standaloneSetup(typeAbsenceResource)
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
    public static TypeAbsence createEntity(EntityManager em) {
        TypeAbsence typeAbsence = new TypeAbsence()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return typeAbsence;
    }

    @Before
    public void initTest() {
        typeAbsence = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeAbsence() throws Exception {
        int databaseSizeBeforeCreate = typeAbsenceRepository.findAll().size();

        // Create the TypeAbsence
        restTypeAbsenceMockMvc.perform(post("/api/type-absences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeAbsence)))
            .andExpect(status().isCreated());

        // Validate the TypeAbsence in the database
        List<TypeAbsence> typeAbsenceList = typeAbsenceRepository.findAll();
        assertThat(typeAbsenceList).hasSize(databaseSizeBeforeCreate + 1);
        TypeAbsence testTypeAbsence = typeAbsenceList.get(typeAbsenceList.size() - 1);
        assertThat(testTypeAbsence.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeAbsence.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeAbsence.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createTypeAbsenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeAbsenceRepository.findAll().size();

        // Create the TypeAbsence with an existing ID
        typeAbsence.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeAbsenceMockMvc.perform(post("/api/type-absences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeAbsence)))
            .andExpect(status().isBadRequest());

        // Validate the TypeAbsence in the database
        List<TypeAbsence> typeAbsenceList = typeAbsenceRepository.findAll();
        assertThat(typeAbsenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeAbsenceRepository.findAll().size();
        // set the field null
        typeAbsence.setLibelle(null);

        // Create the TypeAbsence, which fails.

        restTypeAbsenceMockMvc.perform(post("/api/type-absences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeAbsence)))
            .andExpect(status().isBadRequest());

        List<TypeAbsence> typeAbsenceList = typeAbsenceRepository.findAll();
        assertThat(typeAbsenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeAbsenceRepository.findAll().size();
        // set the field null
        typeAbsence.setCode(null);

        // Create the TypeAbsence, which fails.

        restTypeAbsenceMockMvc.perform(post("/api/type-absences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeAbsence)))
            .andExpect(status().isBadRequest());

        List<TypeAbsence> typeAbsenceList = typeAbsenceRepository.findAll();
        assertThat(typeAbsenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeAbsences() throws Exception {
        // Initialize the database
        typeAbsenceRepository.saveAndFlush(typeAbsence);

        // Get all the typeAbsenceList
        restTypeAbsenceMockMvc.perform(get("/api/type-absences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeAbsence.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getTypeAbsence() throws Exception {
        // Initialize the database
        typeAbsenceRepository.saveAndFlush(typeAbsence);

        // Get the typeAbsence
        restTypeAbsenceMockMvc.perform(get("/api/type-absences/{id}", typeAbsence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeAbsence.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeAbsence() throws Exception {
        // Get the typeAbsence
        restTypeAbsenceMockMvc.perform(get("/api/type-absences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeAbsence() throws Exception {
        // Initialize the database
        typeAbsenceService.save(typeAbsence);

        int databaseSizeBeforeUpdate = typeAbsenceRepository.findAll().size();

        // Update the typeAbsence
        TypeAbsence updatedTypeAbsence = typeAbsenceRepository.findOne(typeAbsence.getId());
        // Disconnect from session so that the updates on updatedTypeAbsence are not directly saved in db
        em.detach(updatedTypeAbsence);
        updatedTypeAbsence
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restTypeAbsenceMockMvc.perform(put("/api/type-absences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeAbsence)))
            .andExpect(status().isOk());

        // Validate the TypeAbsence in the database
        List<TypeAbsence> typeAbsenceList = typeAbsenceRepository.findAll();
        assertThat(typeAbsenceList).hasSize(databaseSizeBeforeUpdate);
        TypeAbsence testTypeAbsence = typeAbsenceList.get(typeAbsenceList.size() - 1);
        assertThat(testTypeAbsence.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeAbsence.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeAbsence.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeAbsence() throws Exception {
        int databaseSizeBeforeUpdate = typeAbsenceRepository.findAll().size();

        // Create the TypeAbsence

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypeAbsenceMockMvc.perform(put("/api/type-absences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeAbsence)))
            .andExpect(status().isCreated());

        // Validate the TypeAbsence in the database
        List<TypeAbsence> typeAbsenceList = typeAbsenceRepository.findAll();
        assertThat(typeAbsenceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypeAbsence() throws Exception {
        // Initialize the database
        typeAbsenceService.save(typeAbsence);

        int databaseSizeBeforeDelete = typeAbsenceRepository.findAll().size();

        // Get the typeAbsence
        restTypeAbsenceMockMvc.perform(delete("/api/type-absences/{id}", typeAbsence.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeAbsence> typeAbsenceList = typeAbsenceRepository.findAll();
        assertThat(typeAbsenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeAbsence.class);
        TypeAbsence typeAbsence1 = new TypeAbsence();
        typeAbsence1.setId(1L);
        TypeAbsence typeAbsence2 = new TypeAbsence();
        typeAbsence2.setId(typeAbsence1.getId());
        assertThat(typeAbsence1).isEqualTo(typeAbsence2);
        typeAbsence2.setId(2L);
        assertThat(typeAbsence1).isNotEqualTo(typeAbsence2);
        typeAbsence1.setId(null);
        assertThat(typeAbsence1).isNotEqualTo(typeAbsence2);
    }
}
