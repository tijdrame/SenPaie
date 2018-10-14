package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.Exercice;
import com.emard.repository.ExerciceRepository;
import com.emard.service.ExerciceService;
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
 * Test class for the ExerciceResource REST controller.
 *
 * @see ExerciceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class ExerciceResourceIntTest {

    private static final Integer DEFAULT_DEBUT_EXERCICE = 1;
    private static final Integer UPDATED_DEBUT_EXERCICE = 2;

    private static final Integer DEFAULT_FIN_EXERCICE = 1;
    private static final Integer UPDATED_FIN_EXERCICE = 2;

    private static final Boolean DEFAULT_ACTIF = false;
    private static final Boolean UPDATED_ACTIF = true;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private ExerciceRepository exerciceRepository;

    @Autowired
    private ExerciceService exerciceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExerciceMockMvc;

    private Exercice exercice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExerciceResource exerciceResource = new ExerciceResource(exerciceService);
        this.restExerciceMockMvc = MockMvcBuilders.standaloneSetup(exerciceResource)
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
    public static Exercice createEntity(EntityManager em) {
        Exercice exercice = new Exercice()
            .debutExercice(DEFAULT_DEBUT_EXERCICE)
            .finExercice(DEFAULT_FIN_EXERCICE)
            .actif(DEFAULT_ACTIF)
            .deleted(DEFAULT_DELETED);
        return exercice;
    }

    @Before
    public void initTest() {
        exercice = createEntity(em);
    }

    @Test
    @Transactional
    public void createExercice() throws Exception {
        int databaseSizeBeforeCreate = exerciceRepository.findAll().size();

        // Create the Exercice
        restExerciceMockMvc.perform(post("/api/exercices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercice)))
            .andExpect(status().isCreated());

        // Validate the Exercice in the database
        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeCreate + 1);
        Exercice testExercice = exerciceList.get(exerciceList.size() - 1);
        assertThat(testExercice.getDebutExercice()).isEqualTo(DEFAULT_DEBUT_EXERCICE);
        assertThat(testExercice.getFinExercice()).isEqualTo(DEFAULT_FIN_EXERCICE);
        assertThat(testExercice.isActif()).isEqualTo(DEFAULT_ACTIF);
        assertThat(testExercice.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createExerciceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exerciceRepository.findAll().size();

        // Create the Exercice with an existing ID
        exercice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExerciceMockMvc.perform(post("/api/exercices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercice)))
            .andExpect(status().isBadRequest());

        // Validate the Exercice in the database
        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDebutExerciceIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciceRepository.findAll().size();
        // set the field null
        exercice.setDebutExercice(null);

        // Create the Exercice, which fails.

        restExerciceMockMvc.perform(post("/api/exercices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercice)))
            .andExpect(status().isBadRequest());

        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFinExerciceIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciceRepository.findAll().size();
        // set the field null
        exercice.setFinExercice(null);

        // Create the Exercice, which fails.

        restExerciceMockMvc.perform(post("/api/exercices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercice)))
            .andExpect(status().isBadRequest());

        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExercices() throws Exception {
        // Initialize the database
        exerciceRepository.saveAndFlush(exercice);

        // Get all the exerciceList
        restExerciceMockMvc.perform(get("/api/exercices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exercice.getId().intValue())))
            .andExpect(jsonPath("$.[*].debutExercice").value(hasItem(DEFAULT_DEBUT_EXERCICE)))
            .andExpect(jsonPath("$.[*].finExercice").value(hasItem(DEFAULT_FIN_EXERCICE)))
            .andExpect(jsonPath("$.[*].actif").value(hasItem(DEFAULT_ACTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getExercice() throws Exception {
        // Initialize the database
        exerciceRepository.saveAndFlush(exercice);

        // Get the exercice
        restExerciceMockMvc.perform(get("/api/exercices/{id}", exercice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(exercice.getId().intValue()))
            .andExpect(jsonPath("$.debutExercice").value(DEFAULT_DEBUT_EXERCICE))
            .andExpect(jsonPath("$.finExercice").value(DEFAULT_FIN_EXERCICE))
            .andExpect(jsonPath("$.actif").value(DEFAULT_ACTIF.booleanValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingExercice() throws Exception {
        // Get the exercice
        restExerciceMockMvc.perform(get("/api/exercices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExercice() throws Exception {
        // Initialize the database
        exerciceService.save(exercice);

        int databaseSizeBeforeUpdate = exerciceRepository.findAll().size();

        // Update the exercice
        Exercice updatedExercice = exerciceRepository.findOne(exercice.getId());
        // Disconnect from session so that the updates on updatedExercice are not directly saved in db
        em.detach(updatedExercice);
        updatedExercice
            .debutExercice(UPDATED_DEBUT_EXERCICE)
            .finExercice(UPDATED_FIN_EXERCICE)
            .actif(UPDATED_ACTIF)
            .deleted(UPDATED_DELETED);

        restExerciceMockMvc.perform(put("/api/exercices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExercice)))
            .andExpect(status().isOk());

        // Validate the Exercice in the database
        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeUpdate);
        Exercice testExercice = exerciceList.get(exerciceList.size() - 1);
        assertThat(testExercice.getDebutExercice()).isEqualTo(UPDATED_DEBUT_EXERCICE);
        assertThat(testExercice.getFinExercice()).isEqualTo(UPDATED_FIN_EXERCICE);
        assertThat(testExercice.isActif()).isEqualTo(UPDATED_ACTIF);
        assertThat(testExercice.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingExercice() throws Exception {
        int databaseSizeBeforeUpdate = exerciceRepository.findAll().size();

        // Create the Exercice

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExerciceMockMvc.perform(put("/api/exercices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercice)))
            .andExpect(status().isCreated());

        // Validate the Exercice in the database
        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExercice() throws Exception {
        // Initialize the database
        exerciceService.save(exercice);

        int databaseSizeBeforeDelete = exerciceRepository.findAll().size();

        // Get the exercice
        restExerciceMockMvc.perform(delete("/api/exercices/{id}", exercice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Exercice.class);
        Exercice exercice1 = new Exercice();
        exercice1.setId(1L);
        Exercice exercice2 = new Exercice();
        exercice2.setId(exercice1.getId());
        assertThat(exercice1).isEqualTo(exercice2);
        exercice2.setId(2L);
        assertThat(exercice1).isNotEqualTo(exercice2);
        exercice1.setId(null);
        assertThat(exercice1).isNotEqualTo(exercice2);
    }
}
