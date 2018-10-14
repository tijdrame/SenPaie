package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.Remboursement;
import com.emard.domain.DetailPret;
import com.emard.repository.RemboursementRepository;
import com.emard.service.RemboursementService;
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
 * Test class for the RemboursementResource REST controller.
 *
 * @see RemboursementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class RemboursementResourceIntTest {

    private static final LocalDate DEFAULT_DATE_REMBOURSEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REMBOURSEMENT = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Boolean DEFAULT_IS_REMBOURSE = false;
    private static final Boolean UPDATED_IS_REMBOURSE = true;

    @Autowired
    private RemboursementRepository remboursementRepository;

    @Autowired
    private RemboursementService remboursementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRemboursementMockMvc;

    private Remboursement remboursement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RemboursementResource remboursementResource = new RemboursementResource(remboursementService);
        this.restRemboursementMockMvc = MockMvcBuilders.standaloneSetup(remboursementResource)
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
    public static Remboursement createEntity(EntityManager em) {
        Remboursement remboursement = new Remboursement()
            .dateRemboursement(DEFAULT_DATE_REMBOURSEMENT)
            .montant(DEFAULT_MONTANT)
            .deleted(DEFAULT_DELETED)
            .isRembourse(DEFAULT_IS_REMBOURSE);
        // Add required entity
        DetailPret detailPret = DetailPretResourceIntTest.createEntity(em);
        em.persist(detailPret);
        em.flush();
        remboursement.setDetailPret(detailPret);
        return remboursement;
    }

    @Before
    public void initTest() {
        remboursement = createEntity(em);
    }

    @Test
    @Transactional
    public void createRemboursement() throws Exception {
        int databaseSizeBeforeCreate = remboursementRepository.findAll().size();

        // Create the Remboursement
        restRemboursementMockMvc.perform(post("/api/remboursements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remboursement)))
            .andExpect(status().isCreated());

        // Validate the Remboursement in the database
        List<Remboursement> remboursementList = remboursementRepository.findAll();
        assertThat(remboursementList).hasSize(databaseSizeBeforeCreate + 1);
        Remboursement testRemboursement = remboursementList.get(remboursementList.size() - 1);
        assertThat(testRemboursement.getDateRemboursement()).isEqualTo(DEFAULT_DATE_REMBOURSEMENT);
        assertThat(testRemboursement.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testRemboursement.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testRemboursement.isIsRembourse()).isEqualTo(DEFAULT_IS_REMBOURSE);
    }

    @Test
    @Transactional
    public void createRemboursementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = remboursementRepository.findAll().size();

        // Create the Remboursement with an existing ID
        remboursement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRemboursementMockMvc.perform(post("/api/remboursements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remboursement)))
            .andExpect(status().isBadRequest());

        // Validate the Remboursement in the database
        List<Remboursement> remboursementList = remboursementRepository.findAll();
        assertThat(remboursementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateRemboursementIsRequired() throws Exception {
        int databaseSizeBeforeTest = remboursementRepository.findAll().size();
        // set the field null
        remboursement.setDateRemboursement(null);

        // Create the Remboursement, which fails.

        restRemboursementMockMvc.perform(post("/api/remboursements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remboursement)))
            .andExpect(status().isBadRequest());

        List<Remboursement> remboursementList = remboursementRepository.findAll();
        assertThat(remboursementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRemboursements() throws Exception {
        // Initialize the database
        remboursementRepository.saveAndFlush(remboursement);

        // Get all the remboursementList
        restRemboursementMockMvc.perform(get("/api/remboursements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remboursement.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateRemboursement").value(hasItem(DEFAULT_DATE_REMBOURSEMENT.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isRembourse").value(hasItem(DEFAULT_IS_REMBOURSE.booleanValue())));
    }

    @Test
    @Transactional
    public void getRemboursement() throws Exception {
        // Initialize the database
        remboursementRepository.saveAndFlush(remboursement);

        // Get the remboursement
        restRemboursementMockMvc.perform(get("/api/remboursements/{id}", remboursement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(remboursement.getId().intValue()))
            .andExpect(jsonPath("$.dateRemboursement").value(DEFAULT_DATE_REMBOURSEMENT.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.isRembourse").value(DEFAULT_IS_REMBOURSE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRemboursement() throws Exception {
        // Get the remboursement
        restRemboursementMockMvc.perform(get("/api/remboursements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRemboursement() throws Exception {
        // Initialize the database
        remboursementService.save(remboursement);

        int databaseSizeBeforeUpdate = remboursementRepository.findAll().size();

        // Update the remboursement
        Remboursement updatedRemboursement = remboursementRepository.findOne(remboursement.getId());
        // Disconnect from session so that the updates on updatedRemboursement are not directly saved in db
        em.detach(updatedRemboursement);
        updatedRemboursement
            .dateRemboursement(UPDATED_DATE_REMBOURSEMENT)
            .montant(UPDATED_MONTANT)
            .deleted(UPDATED_DELETED)
            .isRembourse(UPDATED_IS_REMBOURSE);

        restRemboursementMockMvc.perform(put("/api/remboursements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRemboursement)))
            .andExpect(status().isOk());

        // Validate the Remboursement in the database
        List<Remboursement> remboursementList = remboursementRepository.findAll();
        assertThat(remboursementList).hasSize(databaseSizeBeforeUpdate);
        Remboursement testRemboursement = remboursementList.get(remboursementList.size() - 1);
        assertThat(testRemboursement.getDateRemboursement()).isEqualTo(UPDATED_DATE_REMBOURSEMENT);
        assertThat(testRemboursement.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testRemboursement.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testRemboursement.isIsRembourse()).isEqualTo(UPDATED_IS_REMBOURSE);
    }

    @Test
    @Transactional
    public void updateNonExistingRemboursement() throws Exception {
        int databaseSizeBeforeUpdate = remboursementRepository.findAll().size();

        // Create the Remboursement

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRemboursementMockMvc.perform(put("/api/remboursements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remboursement)))
            .andExpect(status().isCreated());

        // Validate the Remboursement in the database
        List<Remboursement> remboursementList = remboursementRepository.findAll();
        assertThat(remboursementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRemboursement() throws Exception {
        // Initialize the database
        remboursementService.save(remboursement);

        int databaseSizeBeforeDelete = remboursementRepository.findAll().size();

        // Get the remboursement
        restRemboursementMockMvc.perform(delete("/api/remboursements/{id}", remboursement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Remboursement> remboursementList = remboursementRepository.findAll();
        assertThat(remboursementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Remboursement.class);
        Remboursement remboursement1 = new Remboursement();
        remboursement1.setId(1L);
        Remboursement remboursement2 = new Remboursement();
        remboursement2.setId(remboursement1.getId());
        assertThat(remboursement1).isEqualTo(remboursement2);
        remboursement2.setId(2L);
        assertThat(remboursement1).isNotEqualTo(remboursement2);
        remboursement1.setId(null);
        assertThat(remboursement1).isNotEqualTo(remboursement2);
    }
}
