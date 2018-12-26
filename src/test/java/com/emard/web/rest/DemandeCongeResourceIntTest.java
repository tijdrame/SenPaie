package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.DemandeConge;
import com.emard.repository.DemandeCongeRepository;
import com.emard.service.DemandeCongeService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.emard.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DemandeCongeResource REST controller.
 *
 * @see DemandeCongeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class DemandeCongeResourceIntTest {

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MOTIF_REJET = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF_REJET = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private DemandeCongeRepository demandeCongeRepository;

    @Autowired
    private DemandeCongeService demandeCongeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDemandeCongeMockMvc;

    private DemandeConge demandeConge;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DemandeCongeResource demandeCongeResource = new DemandeCongeResource(demandeCongeService);
        this.restDemandeCongeMockMvc = MockMvcBuilders.standaloneSetup(demandeCongeResource)
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
    public static DemandeConge createEntity(EntityManager em) {
        DemandeConge demandeConge = new DemandeConge()
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .motifRejet(DEFAULT_MOTIF_REJET)
            .deleted(DEFAULT_DELETED)
            .libelle(DEFAULT_LIBELLE);
        return demandeConge;
    }

    @Before
    public void initTest() {
        demandeConge = createEntity(em);
    }

    @Test
    @Transactional
    public void createDemandeConge() throws Exception {
        int databaseSizeBeforeCreate = demandeCongeRepository.findAll().size();

        // Create the DemandeConge
        restDemandeCongeMockMvc.perform(post("/api/demande-conges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandeConge)))
            .andExpect(status().isCreated());

        // Validate the DemandeConge in the database
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeCreate + 1);
        DemandeConge testDemandeConge = demandeCongeList.get(demandeCongeList.size() - 1);
        assertThat(testDemandeConge.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testDemandeConge.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testDemandeConge.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testDemandeConge.getMotifRejet()).isEqualTo(DEFAULT_MOTIF_REJET);
        assertThat(testDemandeConge.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testDemandeConge.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createDemandeCongeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = demandeCongeRepository.findAll().size();

        // Create the DemandeConge with an existing ID
        demandeConge.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandeCongeMockMvc.perform(post("/api/demande-conges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandeConge)))
            .andExpect(status().isBadRequest());

        // Validate the DemandeConge in the database
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeCongeRepository.findAll().size();
        // set the field null
        demandeConge.setDateDebut(null);

        // Create the DemandeConge, which fails.

        restDemandeCongeMockMvc.perform(post("/api/demande-conges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandeConge)))
            .andExpect(status().isBadRequest());

        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeCongeRepository.findAll().size();
        // set the field null
        demandeConge.setDateFin(null);

        // Create the DemandeConge, which fails.

        restDemandeCongeMockMvc.perform(post("/api/demande-conges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandeConge)))
            .andExpect(status().isBadRequest());

        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDemandeConges() throws Exception {
        // Initialize the database
        demandeCongeRepository.saveAndFlush(demandeConge);

        // Get all the demandeCongeList
        restDemandeCongeMockMvc.perform(get("/api/demande-conges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandeConge.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].motifRejet").value(hasItem(DEFAULT_MOTIF_REJET.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getDemandeConge() throws Exception {
        // Initialize the database
        demandeCongeRepository.saveAndFlush(demandeConge);

        // Get the demandeConge
        restDemandeCongeMockMvc.perform(get("/api/demande-conges/{id}", demandeConge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(demandeConge.getId().intValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.motifRejet").value(DEFAULT_MOTIF_REJET.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDemandeConge() throws Exception {
        // Get the demandeConge
        restDemandeCongeMockMvc.perform(get("/api/demande-conges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDemandeConge() throws Exception {
        // Initialize the database
        demandeCongeService.save(demandeConge);

        int databaseSizeBeforeUpdate = demandeCongeRepository.findAll().size();

        // Update the demandeConge
        DemandeConge updatedDemandeConge = demandeCongeRepository.findOne(demandeConge.getId());
        // Disconnect from session so that the updates on updatedDemandeConge are not directly saved in db
        em.detach(updatedDemandeConge);
        updatedDemandeConge
            .dateCreated(UPDATED_DATE_CREATED)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .motifRejet(UPDATED_MOTIF_REJET)
            .deleted(UPDATED_DELETED)
            .libelle(UPDATED_LIBELLE);

        restDemandeCongeMockMvc.perform(put("/api/demande-conges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDemandeConge)))
            .andExpect(status().isOk());

        // Validate the DemandeConge in the database
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeUpdate);
        DemandeConge testDemandeConge = demandeCongeList.get(demandeCongeList.size() - 1);
        assertThat(testDemandeConge.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testDemandeConge.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testDemandeConge.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testDemandeConge.getMotifRejet()).isEqualTo(UPDATED_MOTIF_REJET);
        assertThat(testDemandeConge.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testDemandeConge.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingDemandeConge() throws Exception {
        int databaseSizeBeforeUpdate = demandeCongeRepository.findAll().size();

        // Create the DemandeConge

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDemandeCongeMockMvc.perform(put("/api/demande-conges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandeConge)))
            .andExpect(status().isCreated());

        // Validate the DemandeConge in the database
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDemandeConge() throws Exception {
        // Initialize the database
        demandeCongeService.save(demandeConge);

        int databaseSizeBeforeDelete = demandeCongeRepository.findAll().size();

        // Get the demandeConge
        restDemandeCongeMockMvc.perform(delete("/api/demande-conges/{id}", demandeConge.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeConge.class);
        DemandeConge demandeConge1 = new DemandeConge();
        demandeConge1.setId(1L);
        DemandeConge demandeConge2 = new DemandeConge();
        demandeConge2.setId(demandeConge1.getId());
        assertThat(demandeConge1).isEqualTo(demandeConge2);
        demandeConge2.setId(2L);
        assertThat(demandeConge1).isNotEqualTo(demandeConge2);
        demandeConge1.setId(null);
        assertThat(demandeConge1).isNotEqualTo(demandeConge2);
    }
}
