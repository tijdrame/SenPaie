package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.Regime;
import com.emard.repository.RegimeRepository;
import com.emard.service.RegimeService;
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
 * Test class for the RegimeResource REST controller.
 *
 * @see RegimeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class RegimeResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_TAUX_PATRONAL = 1D;
    private static final Double UPDATED_TAUX_PATRONAL = 2D;

    private static final Double DEFAULT_TAUX_SALARIAL = 1D;
    private static final Double UPDATED_TAUX_SALARIAL = 2D;

    private static final Double DEFAULT_PLAFOND = 1D;
    private static final Double UPDATED_PLAFOND = 2D;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private RegimeRepository regimeRepository;

    @Autowired
    private RegimeService regimeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRegimeMockMvc;

    private Regime regime;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegimeResource regimeResource = new RegimeResource(regimeService);
        this.restRegimeMockMvc = MockMvcBuilders.standaloneSetup(regimeResource)
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
    public static Regime createEntity(EntityManager em) {
        Regime regime = new Regime()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .tauxPatronal(DEFAULT_TAUX_PATRONAL)
            .tauxSalarial(DEFAULT_TAUX_SALARIAL)
            .plafond(DEFAULT_PLAFOND)
            .deleted(DEFAULT_DELETED);
        return regime;
    }

    @Before
    public void initTest() {
        regime = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegime() throws Exception {
        int databaseSizeBeforeCreate = regimeRepository.findAll().size();

        // Create the Regime
        restRegimeMockMvc.perform(post("/api/regimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regime)))
            .andExpect(status().isCreated());

        // Validate the Regime in the database
        List<Regime> regimeList = regimeRepository.findAll();
        assertThat(regimeList).hasSize(databaseSizeBeforeCreate + 1);
        Regime testRegime = regimeList.get(regimeList.size() - 1);
        assertThat(testRegime.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testRegime.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRegime.getTauxPatronal()).isEqualTo(DEFAULT_TAUX_PATRONAL);
        assertThat(testRegime.getTauxSalarial()).isEqualTo(DEFAULT_TAUX_SALARIAL);
        assertThat(testRegime.getPlafond()).isEqualTo(DEFAULT_PLAFOND);
        assertThat(testRegime.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createRegimeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regimeRepository.findAll().size();

        // Create the Regime with an existing ID
        regime.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegimeMockMvc.perform(post("/api/regimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regime)))
            .andExpect(status().isBadRequest());

        // Validate the Regime in the database
        List<Regime> regimeList = regimeRepository.findAll();
        assertThat(regimeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = regimeRepository.findAll().size();
        // set the field null
        regime.setLibelle(null);

        // Create the Regime, which fails.

        restRegimeMockMvc.perform(post("/api/regimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regime)))
            .andExpect(status().isBadRequest());

        List<Regime> regimeList = regimeRepository.findAll();
        assertThat(regimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = regimeRepository.findAll().size();
        // set the field null
        regime.setCode(null);

        // Create the Regime, which fails.

        restRegimeMockMvc.perform(post("/api/regimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regime)))
            .andExpect(status().isBadRequest());

        List<Regime> regimeList = regimeRepository.findAll();
        assertThat(regimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTauxPatronalIsRequired() throws Exception {
        int databaseSizeBeforeTest = regimeRepository.findAll().size();
        // set the field null
        regime.setTauxPatronal(null);

        // Create the Regime, which fails.

        restRegimeMockMvc.perform(post("/api/regimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regime)))
            .andExpect(status().isBadRequest());

        List<Regime> regimeList = regimeRepository.findAll();
        assertThat(regimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTauxSalarialIsRequired() throws Exception {
        int databaseSizeBeforeTest = regimeRepository.findAll().size();
        // set the field null
        regime.setTauxSalarial(null);

        // Create the Regime, which fails.

        restRegimeMockMvc.perform(post("/api/regimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regime)))
            .andExpect(status().isBadRequest());

        List<Regime> regimeList = regimeRepository.findAll();
        assertThat(regimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlafondIsRequired() throws Exception {
        int databaseSizeBeforeTest = regimeRepository.findAll().size();
        // set the field null
        regime.setPlafond(null);

        // Create the Regime, which fails.

        restRegimeMockMvc.perform(post("/api/regimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regime)))
            .andExpect(status().isBadRequest());

        List<Regime> regimeList = regimeRepository.findAll();
        assertThat(regimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegimes() throws Exception {
        // Initialize the database
        regimeRepository.saveAndFlush(regime);

        // Get all the regimeList
        restRegimeMockMvc.perform(get("/api/regimes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regime.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].tauxPatronal").value(hasItem(DEFAULT_TAUX_PATRONAL.doubleValue())))
            .andExpect(jsonPath("$.[*].tauxSalarial").value(hasItem(DEFAULT_TAUX_SALARIAL.doubleValue())))
            .andExpect(jsonPath("$.[*].plafond").value(hasItem(DEFAULT_PLAFOND.doubleValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getRegime() throws Exception {
        // Initialize the database
        regimeRepository.saveAndFlush(regime);

        // Get the regime
        restRegimeMockMvc.perform(get("/api/regimes/{id}", regime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(regime.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.tauxPatronal").value(DEFAULT_TAUX_PATRONAL.doubleValue()))
            .andExpect(jsonPath("$.tauxSalarial").value(DEFAULT_TAUX_SALARIAL.doubleValue()))
            .andExpect(jsonPath("$.plafond").value(DEFAULT_PLAFOND.doubleValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRegime() throws Exception {
        // Get the regime
        restRegimeMockMvc.perform(get("/api/regimes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegime() throws Exception {
        // Initialize the database
        regimeService.save(regime);

        int databaseSizeBeforeUpdate = regimeRepository.findAll().size();

        // Update the regime
        Regime updatedRegime = regimeRepository.findOne(regime.getId());
        // Disconnect from session so that the updates on updatedRegime are not directly saved in db
        em.detach(updatedRegime);
        updatedRegime
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .tauxPatronal(UPDATED_TAUX_PATRONAL)
            .tauxSalarial(UPDATED_TAUX_SALARIAL)
            .plafond(UPDATED_PLAFOND)
            .deleted(UPDATED_DELETED);

        restRegimeMockMvc.perform(put("/api/regimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegime)))
            .andExpect(status().isOk());

        // Validate the Regime in the database
        List<Regime> regimeList = regimeRepository.findAll();
        assertThat(regimeList).hasSize(databaseSizeBeforeUpdate);
        Regime testRegime = regimeList.get(regimeList.size() - 1);
        assertThat(testRegime.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testRegime.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRegime.getTauxPatronal()).isEqualTo(UPDATED_TAUX_PATRONAL);
        assertThat(testRegime.getTauxSalarial()).isEqualTo(UPDATED_TAUX_SALARIAL);
        assertThat(testRegime.getPlafond()).isEqualTo(UPDATED_PLAFOND);
        assertThat(testRegime.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingRegime() throws Exception {
        int databaseSizeBeforeUpdate = regimeRepository.findAll().size();

        // Create the Regime

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRegimeMockMvc.perform(put("/api/regimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regime)))
            .andExpect(status().isCreated());

        // Validate the Regime in the database
        List<Regime> regimeList = regimeRepository.findAll();
        assertThat(regimeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRegime() throws Exception {
        // Initialize the database
        regimeService.save(regime);

        int databaseSizeBeforeDelete = regimeRepository.findAll().size();

        // Get the regime
        restRegimeMockMvc.perform(delete("/api/regimes/{id}", regime.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Regime> regimeList = regimeRepository.findAll();
        assertThat(regimeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Regime.class);
        Regime regime1 = new Regime();
        regime1.setId(1L);
        Regime regime2 = new Regime();
        regime2.setId(regime1.getId());
        assertThat(regime1).isEqualTo(regime2);
        regime2.setId(2L);
        assertThat(regime1).isNotEqualTo(regime2);
        regime1.setId(null);
        assertThat(regime1).isNotEqualTo(regime2);
    }
}
