package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.Pret;
import com.emard.repository.PretRepository;
import com.emard.service.PretService;
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
 * Test class for the PretResource REST controller.
 *
 * @see PretResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class PretResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NB_REMBOURSEMENT = 1;
    private static final Integer UPDATED_NB_REMBOURSEMENT = 2;

    private static final LocalDate DEFAULT_DATE_PRET = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PRET = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DEBUT_REMBOURSEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT_REMBOURSEMENT = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private PretService pretService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPretMockMvc;

    private Pret pret;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PretResource pretResource = new PretResource(pretService);
        this.restPretMockMvc = MockMvcBuilders.standaloneSetup(pretResource)
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
    public static Pret createEntity(EntityManager em) {
        Pret pret = new Pret()
            .libelle(DEFAULT_LIBELLE)
            .nbRemboursement(DEFAULT_NB_REMBOURSEMENT)
            .datePret(DEFAULT_DATE_PRET)
            .dateDebutRemboursement(DEFAULT_DATE_DEBUT_REMBOURSEMENT)
            .deleted(DEFAULT_DELETED);
        return pret;
    }

    @Before
    public void initTest() {
        pret = createEntity(em);
    }

    @Test
    @Transactional
    public void createPret() throws Exception {
        int databaseSizeBeforeCreate = pretRepository.findAll().size();

        // Create the Pret
        restPretMockMvc.perform(post("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pret)))
            .andExpect(status().isCreated());

        // Validate the Pret in the database
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeCreate + 1);
        Pret testPret = pretList.get(pretList.size() - 1);
        assertThat(testPret.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testPret.getNbRemboursement()).isEqualTo(DEFAULT_NB_REMBOURSEMENT);
        assertThat(testPret.getDatePret()).isEqualTo(DEFAULT_DATE_PRET);
        assertThat(testPret.getDateDebutRemboursement()).isEqualTo(DEFAULT_DATE_DEBUT_REMBOURSEMENT);
        assertThat(testPret.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createPretWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pretRepository.findAll().size();

        // Create the Pret with an existing ID
        pret.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPretMockMvc.perform(post("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pret)))
            .andExpect(status().isBadRequest());

        // Validate the Pret in the database
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = pretRepository.findAll().size();
        // set the field null
        pret.setLibelle(null);

        // Create the Pret, which fails.

        restPretMockMvc.perform(post("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pret)))
            .andExpect(status().isBadRequest());

        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNbRemboursementIsRequired() throws Exception {
        int databaseSizeBeforeTest = pretRepository.findAll().size();
        // set the field null
        pret.setNbRemboursement(null);

        // Create the Pret, which fails.

        restPretMockMvc.perform(post("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pret)))
            .andExpect(status().isBadRequest());

        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatePretIsRequired() throws Exception {
        int databaseSizeBeforeTest = pretRepository.findAll().size();
        // set the field null
        pret.setDatePret(null);

        // Create the Pret, which fails.

        restPretMockMvc.perform(post("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pret)))
            .andExpect(status().isBadRequest());

        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDebutRemboursementIsRequired() throws Exception {
        int databaseSizeBeforeTest = pretRepository.findAll().size();
        // set the field null
        pret.setDateDebutRemboursement(null);

        // Create the Pret, which fails.

        restPretMockMvc.perform(post("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pret)))
            .andExpect(status().isBadRequest());

        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrets() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList
        restPretMockMvc.perform(get("/api/prets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pret.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].nbRemboursement").value(hasItem(DEFAULT_NB_REMBOURSEMENT)))
            .andExpect(jsonPath("$.[*].datePret").value(hasItem(DEFAULT_DATE_PRET.toString())))
            .andExpect(jsonPath("$.[*].dateDebutRemboursement").value(hasItem(DEFAULT_DATE_DEBUT_REMBOURSEMENT.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getPret() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get the pret
        restPretMockMvc.perform(get("/api/prets/{id}", pret.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pret.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.nbRemboursement").value(DEFAULT_NB_REMBOURSEMENT))
            .andExpect(jsonPath("$.datePret").value(DEFAULT_DATE_PRET.toString()))
            .andExpect(jsonPath("$.dateDebutRemboursement").value(DEFAULT_DATE_DEBUT_REMBOURSEMENT.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPret() throws Exception {
        // Get the pret
        restPretMockMvc.perform(get("/api/prets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePret() throws Exception {
        // Initialize the database
        pretService.save(pret);

        int databaseSizeBeforeUpdate = pretRepository.findAll().size();

        // Update the pret
        Pret updatedPret = pretRepository.findOne(pret.getId());
        // Disconnect from session so that the updates on updatedPret are not directly saved in db
        em.detach(updatedPret);
        updatedPret
            .libelle(UPDATED_LIBELLE)
            .nbRemboursement(UPDATED_NB_REMBOURSEMENT)
            .datePret(UPDATED_DATE_PRET)
            .dateDebutRemboursement(UPDATED_DATE_DEBUT_REMBOURSEMENT)
            .deleted(UPDATED_DELETED);

        restPretMockMvc.perform(put("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPret)))
            .andExpect(status().isOk());

        // Validate the Pret in the database
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeUpdate);
        Pret testPret = pretList.get(pretList.size() - 1);
        assertThat(testPret.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testPret.getNbRemboursement()).isEqualTo(UPDATED_NB_REMBOURSEMENT);
        assertThat(testPret.getDatePret()).isEqualTo(UPDATED_DATE_PRET);
        assertThat(testPret.getDateDebutRemboursement()).isEqualTo(UPDATED_DATE_DEBUT_REMBOURSEMENT);
        assertThat(testPret.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingPret() throws Exception {
        int databaseSizeBeforeUpdate = pretRepository.findAll().size();

        // Create the Pret

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPretMockMvc.perform(put("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pret)))
            .andExpect(status().isCreated());

        // Validate the Pret in the database
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePret() throws Exception {
        // Initialize the database
        pretService.save(pret);

        int databaseSizeBeforeDelete = pretRepository.findAll().size();

        // Get the pret
        restPretMockMvc.perform(delete("/api/prets/{id}", pret.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pret.class);
        Pret pret1 = new Pret();
        pret1.setId(1L);
        Pret pret2 = new Pret();
        pret2.setId(pret1.getId());
        assertThat(pret1).isEqualTo(pret2);
        pret2.setId(2L);
        assertThat(pret1).isNotEqualTo(pret2);
        pret1.setId(null);
        assertThat(pret1).isNotEqualTo(pret2);
    }
}
