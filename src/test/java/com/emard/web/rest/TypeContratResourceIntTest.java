package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.TypeContrat;
import com.emard.repository.TypeContratRepository;
import com.emard.service.TypeContratService;
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
 * Test class for the TypeContratResource REST controller.
 *
 * @see TypeContratResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class TypeContratResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private TypeContratRepository typeContratRepository;

    @Autowired
    private TypeContratService typeContratService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeContratMockMvc;

    private TypeContrat typeContrat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeContratResource typeContratResource = new TypeContratResource(typeContratService);
        this.restTypeContratMockMvc = MockMvcBuilders.standaloneSetup(typeContratResource)
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
    public static TypeContrat createEntity(EntityManager em) {
        TypeContrat typeContrat = new TypeContrat()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return typeContrat;
    }

    @Before
    public void initTest() {
        typeContrat = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeContrat() throws Exception {
        int databaseSizeBeforeCreate = typeContratRepository.findAll().size();

        // Create the TypeContrat
        restTypeContratMockMvc.perform(post("/api/type-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeContrat)))
            .andExpect(status().isCreated());

        // Validate the TypeContrat in the database
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeCreate + 1);
        TypeContrat testTypeContrat = typeContratList.get(typeContratList.size() - 1);
        assertThat(testTypeContrat.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeContrat.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeContrat.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createTypeContratWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeContratRepository.findAll().size();

        // Create the TypeContrat with an existing ID
        typeContrat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeContratMockMvc.perform(post("/api/type-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeContrat)))
            .andExpect(status().isBadRequest());

        // Validate the TypeContrat in the database
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeContratRepository.findAll().size();
        // set the field null
        typeContrat.setLibelle(null);

        // Create the TypeContrat, which fails.

        restTypeContratMockMvc.perform(post("/api/type-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeContrat)))
            .andExpect(status().isBadRequest());

        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeContratRepository.findAll().size();
        // set the field null
        typeContrat.setCode(null);

        // Create the TypeContrat, which fails.

        restTypeContratMockMvc.perform(post("/api/type-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeContrat)))
            .andExpect(status().isBadRequest());

        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeContrats() throws Exception {
        // Initialize the database
        typeContratRepository.saveAndFlush(typeContrat);

        // Get all the typeContratList
        restTypeContratMockMvc.perform(get("/api/type-contrats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeContrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getTypeContrat() throws Exception {
        // Initialize the database
        typeContratRepository.saveAndFlush(typeContrat);

        // Get the typeContrat
        restTypeContratMockMvc.perform(get("/api/type-contrats/{id}", typeContrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeContrat.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeContrat() throws Exception {
        // Get the typeContrat
        restTypeContratMockMvc.perform(get("/api/type-contrats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeContrat() throws Exception {
        // Initialize the database
        typeContratService.save(typeContrat);

        int databaseSizeBeforeUpdate = typeContratRepository.findAll().size();

        // Update the typeContrat
        TypeContrat updatedTypeContrat = typeContratRepository.findOne(typeContrat.getId());
        // Disconnect from session so that the updates on updatedTypeContrat are not directly saved in db
        em.detach(updatedTypeContrat);
        updatedTypeContrat
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restTypeContratMockMvc.perform(put("/api/type-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeContrat)))
            .andExpect(status().isOk());

        // Validate the TypeContrat in the database
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeUpdate);
        TypeContrat testTypeContrat = typeContratList.get(typeContratList.size() - 1);
        assertThat(testTypeContrat.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeContrat.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeContrat.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeContrat() throws Exception {
        int databaseSizeBeforeUpdate = typeContratRepository.findAll().size();

        // Create the TypeContrat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypeContratMockMvc.perform(put("/api/type-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeContrat)))
            .andExpect(status().isCreated());

        // Validate the TypeContrat in the database
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypeContrat() throws Exception {
        // Initialize the database
        typeContratService.save(typeContrat);

        int databaseSizeBeforeDelete = typeContratRepository.findAll().size();

        // Get the typeContrat
        restTypeContratMockMvc.perform(delete("/api/type-contrats/{id}", typeContrat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeContrat.class);
        TypeContrat typeContrat1 = new TypeContrat();
        typeContrat1.setId(1L);
        TypeContrat typeContrat2 = new TypeContrat();
        typeContrat2.setId(typeContrat1.getId());
        assertThat(typeContrat1).isEqualTo(typeContrat2);
        typeContrat2.setId(2L);
        assertThat(typeContrat1).isNotEqualTo(typeContrat2);
        typeContrat1.setId(null);
        assertThat(typeContrat1).isNotEqualTo(typeContrat2);
    }
}
