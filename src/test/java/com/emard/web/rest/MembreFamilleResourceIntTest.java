package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.MembreFamille;
import com.emard.repository.MembreFamilleRepository;
import com.emard.service.MembreFamilleService;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the MembreFamilleResource REST controller.
 *
 * @see MembreFamilleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class MembreFamilleResourceIntTest {

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MARIAGE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MARIAGE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private MembreFamilleRepository membreFamilleRepository;

    @Autowired
    private MembreFamilleService membreFamilleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMembreFamilleMockMvc;

    private MembreFamille membreFamille;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MembreFamilleResource membreFamilleResource = new MembreFamilleResource(membreFamilleService);
        this.restMembreFamilleMockMvc = MockMvcBuilders.standaloneSetup(membreFamilleResource)
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
    public static MembreFamille createEntity(EntityManager em) {
        MembreFamille membreFamille = new MembreFamille()
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .adresse(DEFAULT_ADRESSE)
            .isActif(DEFAULT_IS_ACTIF)
            .deleted(DEFAULT_DELETED)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .dateMariage(DEFAULT_DATE_MARIAGE)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return membreFamille;
    }

    @Before
    public void initTest() {
        membreFamille = createEntity(em);
    }

    @Test
    @Transactional
    public void createMembreFamille() throws Exception {
        int databaseSizeBeforeCreate = membreFamilleRepository.findAll().size();

        // Create the MembreFamille
        restMembreFamilleMockMvc.perform(post("/api/membre-familles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isCreated());

        // Validate the MembreFamille in the database
        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeCreate + 1);
        MembreFamille testMembreFamille = membreFamilleList.get(membreFamilleList.size() - 1);
        assertThat(testMembreFamille.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testMembreFamille.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMembreFamille.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testMembreFamille.isIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testMembreFamille.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testMembreFamille.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testMembreFamille.getDateMariage()).isEqualTo(DEFAULT_DATE_MARIAGE);
        assertThat(testMembreFamille.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testMembreFamille.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createMembreFamilleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = membreFamilleRepository.findAll().size();

        // Create the MembreFamille with an existing ID
        membreFamille.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembreFamilleMockMvc.perform(post("/api/membre-familles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isBadRequest());

        // Validate the MembreFamille in the database
        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = membreFamilleRepository.findAll().size();
        // set the field null
        membreFamille.setPrenom(null);

        // Create the MembreFamille, which fails.

        restMembreFamilleMockMvc.perform(post("/api/membre-familles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isBadRequest());

        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = membreFamilleRepository.findAll().size();
        // set the field null
        membreFamille.setNom(null);

        // Create the MembreFamille, which fails.

        restMembreFamilleMockMvc.perform(post("/api/membre-familles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isBadRequest());

        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseIsRequired() throws Exception {
        int databaseSizeBeforeTest = membreFamilleRepository.findAll().size();
        // set the field null
        membreFamille.setAdresse(null);

        // Create the MembreFamille, which fails.

        restMembreFamilleMockMvc.perform(post("/api/membre-familles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isBadRequest());

        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMembreFamilles() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList
        restMembreFamilleMockMvc.perform(get("/api/membre-familles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membreFamille.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].dateMariage").value(hasItem(DEFAULT_DATE_MARIAGE.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    public void getMembreFamille() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get the membreFamille
        restMembreFamilleMockMvc.perform(get("/api/membre-familles/{id}", membreFamille.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(membreFamille.getId().intValue()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.isActif").value(DEFAULT_IS_ACTIF.booleanValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.dateMariage").value(DEFAULT_DATE_MARIAGE.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingMembreFamille() throws Exception {
        // Get the membreFamille
        restMembreFamilleMockMvc.perform(get("/api/membre-familles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMembreFamille() throws Exception {
        // Initialize the database
        membreFamilleService.save(membreFamille);

        int databaseSizeBeforeUpdate = membreFamilleRepository.findAll().size();

        // Update the membreFamille
        MembreFamille updatedMembreFamille = membreFamilleRepository.findOne(membreFamille.getId());
        // Disconnect from session so that the updates on updatedMembreFamille are not directly saved in db
        em.detach(updatedMembreFamille);
        updatedMembreFamille
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .isActif(UPDATED_IS_ACTIF)
            .deleted(UPDATED_DELETED)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .dateMariage(UPDATED_DATE_MARIAGE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restMembreFamilleMockMvc.perform(put("/api/membre-familles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMembreFamille)))
            .andExpect(status().isOk());

        // Validate the MembreFamille in the database
        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeUpdate);
        MembreFamille testMembreFamille = membreFamilleList.get(membreFamilleList.size() - 1);
        assertThat(testMembreFamille.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testMembreFamille.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMembreFamille.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testMembreFamille.isIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testMembreFamille.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testMembreFamille.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testMembreFamille.getDateMariage()).isEqualTo(UPDATED_DATE_MARIAGE);
        assertThat(testMembreFamille.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testMembreFamille.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMembreFamille() throws Exception {
        int databaseSizeBeforeUpdate = membreFamilleRepository.findAll().size();

        // Create the MembreFamille

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMembreFamilleMockMvc.perform(put("/api/membre-familles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isCreated());

        // Validate the MembreFamille in the database
        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMembreFamille() throws Exception {
        // Initialize the database
        membreFamilleService.save(membreFamille);

        int databaseSizeBeforeDelete = membreFamilleRepository.findAll().size();

        // Get the membreFamille
        restMembreFamilleMockMvc.perform(delete("/api/membre-familles/{id}", membreFamille.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MembreFamille.class);
        MembreFamille membreFamille1 = new MembreFamille();
        membreFamille1.setId(1L);
        MembreFamille membreFamille2 = new MembreFamille();
        membreFamille2.setId(membreFamille1.getId());
        assertThat(membreFamille1).isEqualTo(membreFamille2);
        membreFamille2.setId(2L);
        assertThat(membreFamille1).isNotEqualTo(membreFamille2);
        membreFamille1.setId(null);
        assertThat(membreFamille1).isNotEqualTo(membreFamille2);
    }
}
