package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.Statut;
import com.emard.repository.StatutRepository;
import com.emard.service.StatutService;
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
 * Test class for the StatutResource REST controller.
 *
 * @see StatutResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class StatutResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private StatutRepository statutRepository;

    @Autowired
    private StatutService statutService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStatutMockMvc;

    private Statut statut;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StatutResource statutResource = new StatutResource(statutService);
        this.restStatutMockMvc = MockMvcBuilders.standaloneSetup(statutResource)
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
    public static Statut createEntity(EntityManager em) {
        Statut statut = new Statut()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return statut;
    }

    @Before
    public void initTest() {
        statut = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatut() throws Exception {
        int databaseSizeBeforeCreate = statutRepository.findAll().size();

        // Create the Statut
        restStatutMockMvc.perform(post("/api/statuts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statut)))
            .andExpect(status().isCreated());

        // Validate the Statut in the database
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeCreate + 1);
        Statut testStatut = statutList.get(statutList.size() - 1);
        assertThat(testStatut.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testStatut.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testStatut.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createStatutWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statutRepository.findAll().size();

        // Create the Statut with an existing ID
        statut.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatutMockMvc.perform(post("/api/statuts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statut)))
            .andExpect(status().isBadRequest());

        // Validate the Statut in the database
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = statutRepository.findAll().size();
        // set the field null
        statut.setLibelle(null);

        // Create the Statut, which fails.

        restStatutMockMvc.perform(post("/api/statuts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statut)))
            .andExpect(status().isBadRequest());

        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = statutRepository.findAll().size();
        // set the field null
        statut.setCode(null);

        // Create the Statut, which fails.

        restStatutMockMvc.perform(post("/api/statuts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statut)))
            .andExpect(status().isBadRequest());

        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStatuts() throws Exception {
        // Initialize the database
        statutRepository.saveAndFlush(statut);

        // Get all the statutList
        restStatutMockMvc.perform(get("/api/statuts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statut.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getStatut() throws Exception {
        // Initialize the database
        statutRepository.saveAndFlush(statut);

        // Get the statut
        restStatutMockMvc.perform(get("/api/statuts/{id}", statut.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(statut.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStatut() throws Exception {
        // Get the statut
        restStatutMockMvc.perform(get("/api/statuts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatut() throws Exception {
        // Initialize the database
        statutService.save(statut);

        int databaseSizeBeforeUpdate = statutRepository.findAll().size();

        // Update the statut
        Statut updatedStatut = statutRepository.findOne(statut.getId());
        // Disconnect from session so that the updates on updatedStatut are not directly saved in db
        em.detach(updatedStatut);
        updatedStatut
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restStatutMockMvc.perform(put("/api/statuts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStatut)))
            .andExpect(status().isOk());

        // Validate the Statut in the database
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeUpdate);
        Statut testStatut = statutList.get(statutList.size() - 1);
        assertThat(testStatut.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testStatut.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStatut.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingStatut() throws Exception {
        int databaseSizeBeforeUpdate = statutRepository.findAll().size();

        // Create the Statut

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStatutMockMvc.perform(put("/api/statuts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statut)))
            .andExpect(status().isCreated());

        // Validate the Statut in the database
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStatut() throws Exception {
        // Initialize the database
        statutService.save(statut);

        int databaseSizeBeforeDelete = statutRepository.findAll().size();

        // Get the statut
        restStatutMockMvc.perform(delete("/api/statuts/{id}", statut.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Statut.class);
        Statut statut1 = new Statut();
        statut1.setId(1L);
        Statut statut2 = new Statut();
        statut2.setId(statut1.getId());
        assertThat(statut1).isEqualTo(statut2);
        statut2.setId(2L);
        assertThat(statut1).isNotEqualTo(statut2);
        statut1.setId(null);
        assertThat(statut1).isNotEqualTo(statut2);
    }
}
