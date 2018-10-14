package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.Sexe;
import com.emard.repository.SexeRepository;
import com.emard.service.SexeService;
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
 * Test class for the SexeResource REST controller.
 *
 * @see SexeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class SexeResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private SexeRepository sexeRepository;

    @Autowired
    private SexeService sexeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSexeMockMvc;

    private Sexe sexe;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SexeResource sexeResource = new SexeResource(sexeService);
        this.restSexeMockMvc = MockMvcBuilders.standaloneSetup(sexeResource)
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
    public static Sexe createEntity(EntityManager em) {
        Sexe sexe = new Sexe()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return sexe;
    }

    @Before
    public void initTest() {
        sexe = createEntity(em);
    }

    @Test
    @Transactional
    public void createSexe() throws Exception {
        int databaseSizeBeforeCreate = sexeRepository.findAll().size();

        // Create the Sexe
        restSexeMockMvc.perform(post("/api/sexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sexe)))
            .andExpect(status().isCreated());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeCreate + 1);
        Sexe testSexe = sexeList.get(sexeList.size() - 1);
        assertThat(testSexe.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testSexe.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSexe.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createSexeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sexeRepository.findAll().size();

        // Create the Sexe with an existing ID
        sexe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSexeMockMvc.perform(post("/api/sexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sexe)))
            .andExpect(status().isBadRequest());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = sexeRepository.findAll().size();
        // set the field null
        sexe.setLibelle(null);

        // Create the Sexe, which fails.

        restSexeMockMvc.perform(post("/api/sexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sexe)))
            .andExpect(status().isBadRequest());

        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sexeRepository.findAll().size();
        // set the field null
        sexe.setCode(null);

        // Create the Sexe, which fails.

        restSexeMockMvc.perform(post("/api/sexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sexe)))
            .andExpect(status().isBadRequest());

        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSexes() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList
        restSexeMockMvc.perform(get("/api/sexes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sexe.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getSexe() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get the sexe
        restSexeMockMvc.perform(get("/api/sexes/{id}", sexe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sexe.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSexe() throws Exception {
        // Get the sexe
        restSexeMockMvc.perform(get("/api/sexes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSexe() throws Exception {
        // Initialize the database
        sexeService.save(sexe);

        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();

        // Update the sexe
        Sexe updatedSexe = sexeRepository.findOne(sexe.getId());
        // Disconnect from session so that the updates on updatedSexe are not directly saved in db
        em.detach(updatedSexe);
        updatedSexe
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restSexeMockMvc.perform(put("/api/sexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSexe)))
            .andExpect(status().isOk());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
        Sexe testSexe = sexeList.get(sexeList.size() - 1);
        assertThat(testSexe.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testSexe.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSexe.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();

        // Create the Sexe

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSexeMockMvc.perform(put("/api/sexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sexe)))
            .andExpect(status().isCreated());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSexe() throws Exception {
        // Initialize the database
        sexeService.save(sexe);

        int databaseSizeBeforeDelete = sexeRepository.findAll().size();

        // Get the sexe
        restSexeMockMvc.perform(delete("/api/sexes/{id}", sexe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sexe.class);
        Sexe sexe1 = new Sexe();
        sexe1.setId(1L);
        Sexe sexe2 = new Sexe();
        sexe2.setId(sexe1.getId());
        assertThat(sexe1).isEqualTo(sexe2);
        sexe2.setId(2L);
        assertThat(sexe1).isNotEqualTo(sexe2);
        sexe1.setId(null);
        assertThat(sexe1).isNotEqualTo(sexe2);
    }
}
