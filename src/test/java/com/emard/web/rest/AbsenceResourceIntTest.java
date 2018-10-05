package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.Absence;
import com.emard.domain.Collaborateur;
import com.emard.domain.Motif;
import com.emard.domain.Exercice;
import com.emard.repository.AbsenceRepository;
import com.emard.service.AbsenceService;
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
 * Test class for the AbsenceResource REST controller.
 *
 * @see AbsenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class AbsenceResourceIntTest {

    private static final LocalDate DEFAULT_DATE_ABSENCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ABSENCE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private AbsenceService absenceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAbsenceMockMvc;

    private Absence absence;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AbsenceResource absenceResource = new AbsenceResource(absenceService);
        this.restAbsenceMockMvc = MockMvcBuilders.standaloneSetup(absenceResource)
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
    public static Absence createEntity(EntityManager em) {
        Absence absence = new Absence()
            .dateAbsence(DEFAULT_DATE_ABSENCE)
            .deleted(DEFAULT_DELETED)
            .dateCreated(DEFAULT_DATE_CREATED);
        // Add required entity
        Collaborateur collaborateur = CollaborateurResourceIntTest.createEntity(em);
        em.persist(collaborateur);
        em.flush();
        absence.setCollaborateur(collaborateur);
        // Add required entity
        Motif motif = MotifResourceIntTest.createEntity(em);
        em.persist(motif);
        em.flush();
        absence.setMotif(motif);
        // Add required entity
        Exercice exercice = ExerciceResourceIntTest.createEntity(em);
        em.persist(exercice);
        em.flush();
        absence.setExercice(exercice);
        return absence;
    }

    @Before
    public void initTest() {
        absence = createEntity(em);
    }

    @Test
    @Transactional
    public void createAbsence() throws Exception {
        int databaseSizeBeforeCreate = absenceRepository.findAll().size();

        // Create the Absence
        restAbsenceMockMvc.perform(post("/api/absences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(absence)))
            .andExpect(status().isCreated());

        // Validate the Absence in the database
        List<Absence> absenceList = absenceRepository.findAll();
        assertThat(absenceList).hasSize(databaseSizeBeforeCreate + 1);
        Absence testAbsence = absenceList.get(absenceList.size() - 1);
        assertThat(testAbsence.getDateAbsence()).isEqualTo(DEFAULT_DATE_ABSENCE);
        assertThat(testAbsence.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testAbsence.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
    }

    @Test
    @Transactional
    public void createAbsenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = absenceRepository.findAll().size();

        // Create the Absence with an existing ID
        absence.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAbsenceMockMvc.perform(post("/api/absences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(absence)))
            .andExpect(status().isBadRequest());

        // Validate the Absence in the database
        List<Absence> absenceList = absenceRepository.findAll();
        assertThat(absenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateAbsenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = absenceRepository.findAll().size();
        // set the field null
        absence.setDateAbsence(null);

        // Create the Absence, which fails.

        restAbsenceMockMvc.perform(post("/api/absences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(absence)))
            .andExpect(status().isBadRequest());

        List<Absence> absenceList = absenceRepository.findAll();
        assertThat(absenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAbsences() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        // Get all the absenceList
        restAbsenceMockMvc.perform(get("/api/absences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(absence.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateAbsence").value(hasItem(DEFAULT_DATE_ABSENCE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())));
    }

    @Test
    @Transactional
    public void getAbsence() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        // Get the absence
        restAbsenceMockMvc.perform(get("/api/absences/{id}", absence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(absence.getId().intValue()))
            .andExpect(jsonPath("$.dateAbsence").value(DEFAULT_DATE_ABSENCE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAbsence() throws Exception {
        // Get the absence
        restAbsenceMockMvc.perform(get("/api/absences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAbsence() throws Exception {
        // Initialize the database
        absenceService.save(absence);

        int databaseSizeBeforeUpdate = absenceRepository.findAll().size();

        // Update the absence
        Absence updatedAbsence = absenceRepository.findOne(absence.getId());
        // Disconnect from session so that the updates on updatedAbsence are not directly saved in db
        em.detach(updatedAbsence);
        updatedAbsence
            .dateAbsence(UPDATED_DATE_ABSENCE)
            .deleted(UPDATED_DELETED)
            .dateCreated(UPDATED_DATE_CREATED);

        restAbsenceMockMvc.perform(put("/api/absences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAbsence)))
            .andExpect(status().isOk());

        // Validate the Absence in the database
        List<Absence> absenceList = absenceRepository.findAll();
        assertThat(absenceList).hasSize(databaseSizeBeforeUpdate);
        Absence testAbsence = absenceList.get(absenceList.size() - 1);
        assertThat(testAbsence.getDateAbsence()).isEqualTo(UPDATED_DATE_ABSENCE);
        assertThat(testAbsence.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testAbsence.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingAbsence() throws Exception {
        int databaseSizeBeforeUpdate = absenceRepository.findAll().size();

        // Create the Absence

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAbsenceMockMvc.perform(put("/api/absences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(absence)))
            .andExpect(status().isCreated());

        // Validate the Absence in the database
        List<Absence> absenceList = absenceRepository.findAll();
        assertThat(absenceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAbsence() throws Exception {
        // Initialize the database
        absenceService.save(absence);

        int databaseSizeBeforeDelete = absenceRepository.findAll().size();

        // Get the absence
        restAbsenceMockMvc.perform(delete("/api/absences/{id}", absence.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Absence> absenceList = absenceRepository.findAll();
        assertThat(absenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Absence.class);
        Absence absence1 = new Absence();
        absence1.setId(1L);
        Absence absence2 = new Absence();
        absence2.setId(absence1.getId());
        assertThat(absence1).isEqualTo(absence2);
        absence2.setId(2L);
        assertThat(absence1).isNotEqualTo(absence2);
        absence1.setId(null);
        assertThat(absence1).isNotEqualTo(absence2);
    }
}
