package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.StatutDemande;
import com.emard.repository.StatutDemandeRepository;
import com.emard.service.StatutDemandeService;
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
 * Test class for the StatutDemandeResource REST controller.
 *
 * @see StatutDemandeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class StatutDemandeResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private StatutDemandeRepository statutDemandeRepository;

    @Autowired
    private StatutDemandeService statutDemandeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStatutDemandeMockMvc;

    private StatutDemande statutDemande;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StatutDemandeResource statutDemandeResource = new StatutDemandeResource(statutDemandeService);
        this.restStatutDemandeMockMvc = MockMvcBuilders.standaloneSetup(statutDemandeResource)
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
    public static StatutDemande createEntity(EntityManager em) {
        StatutDemande statutDemande = new StatutDemande()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return statutDemande;
    }

    @Before
    public void initTest() {
        statutDemande = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatutDemande() throws Exception {
        int databaseSizeBeforeCreate = statutDemandeRepository.findAll().size();

        // Create the StatutDemande
        restStatutDemandeMockMvc.perform(post("/api/statut-demandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statutDemande)))
            .andExpect(status().isCreated());

        // Validate the StatutDemande in the database
        List<StatutDemande> statutDemandeList = statutDemandeRepository.findAll();
        assertThat(statutDemandeList).hasSize(databaseSizeBeforeCreate + 1);
        StatutDemande testStatutDemande = statutDemandeList.get(statutDemandeList.size() - 1);
        assertThat(testStatutDemande.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testStatutDemande.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testStatutDemande.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createStatutDemandeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statutDemandeRepository.findAll().size();

        // Create the StatutDemande with an existing ID
        statutDemande.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatutDemandeMockMvc.perform(post("/api/statut-demandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statutDemande)))
            .andExpect(status().isBadRequest());

        // Validate the StatutDemande in the database
        List<StatutDemande> statutDemandeList = statutDemandeRepository.findAll();
        assertThat(statutDemandeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = statutDemandeRepository.findAll().size();
        // set the field null
        statutDemande.setLibelle(null);

        // Create the StatutDemande, which fails.

        restStatutDemandeMockMvc.perform(post("/api/statut-demandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statutDemande)))
            .andExpect(status().isBadRequest());

        List<StatutDemande> statutDemandeList = statutDemandeRepository.findAll();
        assertThat(statutDemandeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = statutDemandeRepository.findAll().size();
        // set the field null
        statutDemande.setCode(null);

        // Create the StatutDemande, which fails.

        restStatutDemandeMockMvc.perform(post("/api/statut-demandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statutDemande)))
            .andExpect(status().isBadRequest());

        List<StatutDemande> statutDemandeList = statutDemandeRepository.findAll();
        assertThat(statutDemandeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStatutDemandes() throws Exception {
        // Initialize the database
        statutDemandeRepository.saveAndFlush(statutDemande);

        // Get all the statutDemandeList
        restStatutDemandeMockMvc.perform(get("/api/statut-demandes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statutDemande.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getStatutDemande() throws Exception {
        // Initialize the database
        statutDemandeRepository.saveAndFlush(statutDemande);

        // Get the statutDemande
        restStatutDemandeMockMvc.perform(get("/api/statut-demandes/{id}", statutDemande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(statutDemande.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStatutDemande() throws Exception {
        // Get the statutDemande
        restStatutDemandeMockMvc.perform(get("/api/statut-demandes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatutDemande() throws Exception {
        // Initialize the database
        statutDemandeService.save(statutDemande);

        int databaseSizeBeforeUpdate = statutDemandeRepository.findAll().size();

        // Update the statutDemande
        StatutDemande updatedStatutDemande = statutDemandeRepository.findOne(statutDemande.getId());
        // Disconnect from session so that the updates on updatedStatutDemande are not directly saved in db
        em.detach(updatedStatutDemande);
        updatedStatutDemande
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restStatutDemandeMockMvc.perform(put("/api/statut-demandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStatutDemande)))
            .andExpect(status().isOk());

        // Validate the StatutDemande in the database
        List<StatutDemande> statutDemandeList = statutDemandeRepository.findAll();
        assertThat(statutDemandeList).hasSize(databaseSizeBeforeUpdate);
        StatutDemande testStatutDemande = statutDemandeList.get(statutDemandeList.size() - 1);
        assertThat(testStatutDemande.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testStatutDemande.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStatutDemande.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingStatutDemande() throws Exception {
        int databaseSizeBeforeUpdate = statutDemandeRepository.findAll().size();

        // Create the StatutDemande

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStatutDemandeMockMvc.perform(put("/api/statut-demandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statutDemande)))
            .andExpect(status().isCreated());

        // Validate the StatutDemande in the database
        List<StatutDemande> statutDemandeList = statutDemandeRepository.findAll();
        assertThat(statutDemandeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStatutDemande() throws Exception {
        // Initialize the database
        statutDemandeService.save(statutDemande);

        int databaseSizeBeforeDelete = statutDemandeRepository.findAll().size();

        // Get the statutDemande
        restStatutDemandeMockMvc.perform(delete("/api/statut-demandes/{id}", statutDemande.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StatutDemande> statutDemandeList = statutDemandeRepository.findAll();
        assertThat(statutDemandeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatutDemande.class);
        StatutDemande statutDemande1 = new StatutDemande();
        statutDemande1.setId(1L);
        StatutDemande statutDemande2 = new StatutDemande();
        statutDemande2.setId(statutDemande1.getId());
        assertThat(statutDemande1).isEqualTo(statutDemande2);
        statutDemande2.setId(2L);
        assertThat(statutDemande1).isNotEqualTo(statutDemande2);
        statutDemande1.setId(null);
        assertThat(statutDemande1).isNotEqualTo(statutDemande2);
    }
}
