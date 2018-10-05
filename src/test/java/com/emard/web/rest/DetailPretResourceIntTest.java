package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.DetailPret;
import com.emard.domain.Collaborateur;
import com.emard.domain.Pret;
import com.emard.repository.DetailPretRepository;
import com.emard.service.DetailPretService;
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
import java.util.List;

import static com.emard.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DetailPretResource REST controller.
 *
 * @see DetailPretResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class DetailPretResourceIntTest {

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final Boolean DEFAULT_IS_REMBOURSE = false;
    private static final Boolean UPDATED_IS_REMBOURSE = true;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private DetailPretRepository detailPretRepository;

    @Autowired
    private DetailPretService detailPretService;
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

    private MockMvc restDetailPretMockMvc;

    private DetailPret detailPret;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetailPretResource detailPretResource = new DetailPretResource(detailPretService, remboursementService);
        this.restDetailPretMockMvc = MockMvcBuilders.standaloneSetup(detailPretResource)
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
    public static DetailPret createEntity(EntityManager em) {
        DetailPret detailPret = new DetailPret()
            .montant(DEFAULT_MONTANT)
            .isRembourse(DEFAULT_IS_REMBOURSE)
            .deleted(DEFAULT_DELETED);
        // Add required entity
        Collaborateur collaborateur = CollaborateurResourceIntTest.createEntity(em);
        em.persist(collaborateur);
        em.flush();
        detailPret.setCollaborateur(collaborateur);
        // Add required entity
        Pret pret = PretResourceIntTest.createEntity(em);
        em.persist(pret);
        em.flush();
        detailPret.setPret(pret);
        return detailPret;
    }

    @Before
    public void initTest() {
        detailPret = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetailPret() throws Exception {
        int databaseSizeBeforeCreate = detailPretRepository.findAll().size();

        // Create the DetailPret
        restDetailPretMockMvc.perform(post("/api/detail-prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailPret)))
            .andExpect(status().isCreated());

        // Validate the DetailPret in the database
        List<DetailPret> detailPretList = detailPretRepository.findAll();
        assertThat(detailPretList).hasSize(databaseSizeBeforeCreate + 1);
        DetailPret testDetailPret = detailPretList.get(detailPretList.size() - 1);
        assertThat(testDetailPret.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testDetailPret.isIsRembourse()).isEqualTo(DEFAULT_IS_REMBOURSE);
        assertThat(testDetailPret.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createDetailPretWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detailPretRepository.findAll().size();

        // Create the DetailPret with an existing ID
        detailPret.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailPretMockMvc.perform(post("/api/detail-prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailPret)))
            .andExpect(status().isBadRequest());

        // Validate the DetailPret in the database
        List<DetailPret> detailPretList = detailPretRepository.findAll();
        assertThat(detailPretList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailPretRepository.findAll().size();
        // set the field null
        detailPret.setMontant(null);

        // Create the DetailPret, which fails.

        restDetailPretMockMvc.perform(post("/api/detail-prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailPret)))
            .andExpect(status().isBadRequest());

        List<DetailPret> detailPretList = detailPretRepository.findAll();
        assertThat(detailPretList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDetailPrets() throws Exception {
        // Initialize the database
        detailPretRepository.saveAndFlush(detailPret);

        // Get all the detailPretList
        restDetailPretMockMvc.perform(get("/api/detail-prets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailPret.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].isRembourse").value(hasItem(DEFAULT_IS_REMBOURSE.booleanValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getDetailPret() throws Exception {
        // Initialize the database
        detailPretRepository.saveAndFlush(detailPret);

        // Get the detailPret
        restDetailPretMockMvc.perform(get("/api/detail-prets/{id}", detailPret.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detailPret.getId().intValue()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.isRembourse").value(DEFAULT_IS_REMBOURSE.booleanValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDetailPret() throws Exception {
        // Get the detailPret
        restDetailPretMockMvc.perform(get("/api/detail-prets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetailPret() throws Exception {
        // Initialize the database
        detailPretService.save(detailPret);

        int databaseSizeBeforeUpdate = detailPretRepository.findAll().size();

        // Update the detailPret
        DetailPret updatedDetailPret = detailPretRepository.findOne(detailPret.getId());
        // Disconnect from session so that the updates on updatedDetailPret are not directly saved in db
        em.detach(updatedDetailPret);
        updatedDetailPret
            .montant(UPDATED_MONTANT)
            .isRembourse(UPDATED_IS_REMBOURSE)
            .deleted(UPDATED_DELETED);

        restDetailPretMockMvc.perform(put("/api/detail-prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDetailPret)))
            .andExpect(status().isOk());

        // Validate the DetailPret in the database
        List<DetailPret> detailPretList = detailPretRepository.findAll();
        assertThat(detailPretList).hasSize(databaseSizeBeforeUpdate);
        DetailPret testDetailPret = detailPretList.get(detailPretList.size() - 1);
        assertThat(testDetailPret.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testDetailPret.isIsRembourse()).isEqualTo(UPDATED_IS_REMBOURSE);
        assertThat(testDetailPret.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingDetailPret() throws Exception {
        int databaseSizeBeforeUpdate = detailPretRepository.findAll().size();

        // Create the DetailPret

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDetailPretMockMvc.perform(put("/api/detail-prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailPret)))
            .andExpect(status().isCreated());

        // Validate the DetailPret in the database
        List<DetailPret> detailPretList = detailPretRepository.findAll();
        assertThat(detailPretList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDetailPret() throws Exception {
        // Initialize the database
        detailPretService.save(detailPret);

        int databaseSizeBeforeDelete = detailPretRepository.findAll().size();

        // Get the detailPret
        restDetailPretMockMvc.perform(delete("/api/detail-prets/{id}", detailPret.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DetailPret> detailPretList = detailPretRepository.findAll();
        assertThat(detailPretList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailPret.class);
        DetailPret detailPret1 = new DetailPret();
        detailPret1.setId(1L);
        DetailPret detailPret2 = new DetailPret();
        detailPret2.setId(detailPret1.getId());
        assertThat(detailPret1).isEqualTo(detailPret2);
        detailPret2.setId(2L);
        assertThat(detailPret1).isNotEqualTo(detailPret2);
        detailPret1.setId(null);
        assertThat(detailPret1).isNotEqualTo(detailPret2);
    }
}
