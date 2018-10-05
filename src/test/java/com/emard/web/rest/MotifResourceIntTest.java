package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.Motif;
import com.emard.repository.MotifRepository;
import com.emard.service.MotifService;
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
 * Test class for the MotifResource REST controller.
 *
 * @see MotifResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class MotifResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private MotifRepository motifRepository;

    @Autowired
    private MotifService motifService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMotifMockMvc;

    private Motif motif;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MotifResource motifResource = new MotifResource(motifService);
        this.restMotifMockMvc = MockMvcBuilders.standaloneSetup(motifResource)
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
    public static Motif createEntity(EntityManager em) {
        Motif motif = new Motif()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return motif;
    }

    @Before
    public void initTest() {
        motif = createEntity(em);
    }

    @Test
    @Transactional
    public void createMotif() throws Exception {
        int databaseSizeBeforeCreate = motifRepository.findAll().size();

        // Create the Motif
        restMotifMockMvc.perform(post("/api/motifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motif)))
            .andExpect(status().isCreated());

        // Validate the Motif in the database
        List<Motif> motifList = motifRepository.findAll();
        assertThat(motifList).hasSize(databaseSizeBeforeCreate + 1);
        Motif testMotif = motifList.get(motifList.size() - 1);
        assertThat(testMotif.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testMotif.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMotif.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createMotifWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = motifRepository.findAll().size();

        // Create the Motif with an existing ID
        motif.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMotifMockMvc.perform(post("/api/motifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motif)))
            .andExpect(status().isBadRequest());

        // Validate the Motif in the database
        List<Motif> motifList = motifRepository.findAll();
        assertThat(motifList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = motifRepository.findAll().size();
        // set the field null
        motif.setLibelle(null);

        // Create the Motif, which fails.

        restMotifMockMvc.perform(post("/api/motifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motif)))
            .andExpect(status().isBadRequest());

        List<Motif> motifList = motifRepository.findAll();
        assertThat(motifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = motifRepository.findAll().size();
        // set the field null
        motif.setCode(null);

        // Create the Motif, which fails.

        restMotifMockMvc.perform(post("/api/motifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motif)))
            .andExpect(status().isBadRequest());

        List<Motif> motifList = motifRepository.findAll();
        assertThat(motifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMotifs() throws Exception {
        // Initialize the database
        motifRepository.saveAndFlush(motif);

        // Get all the motifList
        restMotifMockMvc.perform(get("/api/motifs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motif.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getMotif() throws Exception {
        // Initialize the database
        motifRepository.saveAndFlush(motif);

        // Get the motif
        restMotifMockMvc.perform(get("/api/motifs/{id}", motif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(motif.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMotif() throws Exception {
        // Get the motif
        restMotifMockMvc.perform(get("/api/motifs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMotif() throws Exception {
        // Initialize the database
        motifService.save(motif);

        int databaseSizeBeforeUpdate = motifRepository.findAll().size();

        // Update the motif
        Motif updatedMotif = motifRepository.findOne(motif.getId());
        // Disconnect from session so that the updates on updatedMotif are not directly saved in db
        em.detach(updatedMotif);
        updatedMotif
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restMotifMockMvc.perform(put("/api/motifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMotif)))
            .andExpect(status().isOk());

        // Validate the Motif in the database
        List<Motif> motifList = motifRepository.findAll();
        assertThat(motifList).hasSize(databaseSizeBeforeUpdate);
        Motif testMotif = motifList.get(motifList.size() - 1);
        assertThat(testMotif.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testMotif.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMotif.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingMotif() throws Exception {
        int databaseSizeBeforeUpdate = motifRepository.findAll().size();

        // Create the Motif

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMotifMockMvc.perform(put("/api/motifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motif)))
            .andExpect(status().isCreated());

        // Validate the Motif in the database
        List<Motif> motifList = motifRepository.findAll();
        assertThat(motifList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMotif() throws Exception {
        // Initialize the database
        motifService.save(motif);

        int databaseSizeBeforeDelete = motifRepository.findAll().size();

        // Get the motif
        restMotifMockMvc.perform(delete("/api/motifs/{id}", motif.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Motif> motifList = motifRepository.findAll();
        assertThat(motifList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Motif.class);
        Motif motif1 = new Motif();
        motif1.setId(1L);
        Motif motif2 = new Motif();
        motif2.setId(motif1.getId());
        assertThat(motif1).isEqualTo(motif2);
        motif2.setId(2L);
        assertThat(motif1).isNotEqualTo(motif2);
        motif1.setId(null);
        assertThat(motif1).isNotEqualTo(motif2);
    }
}
