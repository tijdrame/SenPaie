package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.Bareme;
import com.emard.repository.BaremeRepository;
import com.emard.service.BaremeService;
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
 * Test class for the BaremeResource REST controller.
 *
 * @see BaremeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class BaremeResourceIntTest {

    private static final Double DEFAULT_REVENU_BRUT = 1D;
    private static final Double UPDATED_REVENU_BRUT = 2D;

    private static final Double DEFAULT_TRIM_F = 1D;
    private static final Double UPDATED_TRIM_F = 2D;

    private static final Double DEFAULT_UNE_PART = 1D;
    private static final Double UPDATED_UNE_PART = 2D;

    private static final Double DEFAULT_UNE_PART_ET_DEMI = 1D;
    private static final Double UPDATED_UNE_PART_ET_DEMI = 2D;

    private static final Double DEFAULT_DEUX_PARTS = 1D;
    private static final Double UPDATED_DEUX_PARTS = 2D;

    private static final Double DEFAULT_DEUX_PARTS_ET_DEMI = 1D;
    private static final Double UPDATED_DEUX_PARTS_ET_DEMI = 2D;

    private static final Double DEFAULT_TROIS_PARTS = 1D;
    private static final Double UPDATED_TROIS_PARTS = 2D;

    private static final Double DEFAULT_TROIS_PARTS_ET_DEMI = 1D;
    private static final Double UPDATED_TROIS_PARTS_ET_DEMI = 2D;

    private static final Double DEFAULT_QUATRE_PARTS = 1D;
    private static final Double UPDATED_QUATRE_PARTS = 2D;

    private static final Double DEFAULT_QUATRE_PARTS_ET_DEMI = 1D;
    private static final Double UPDATED_QUATRE_PARTS_ET_DEMI = 2D;

    private static final Double DEFAULT_CINQ_PARTS = 1D;
    private static final Double UPDATED_CINQ_PARTS = 2D;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private BaremeRepository baremeRepository;

    @Autowired
    private BaremeService baremeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBaremeMockMvc;

    private Bareme bareme;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BaremeResource baremeResource = new BaremeResource(baremeService);
        this.restBaremeMockMvc = MockMvcBuilders.standaloneSetup(baremeResource)
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
    public static Bareme createEntity(EntityManager em) {
        Bareme bareme = new Bareme()
            .revenuBrut(DEFAULT_REVENU_BRUT)
            .trimF(DEFAULT_TRIM_F)
            .unePart(DEFAULT_UNE_PART)
            .unePartEtDemi(DEFAULT_UNE_PART_ET_DEMI)
            .deuxParts(DEFAULT_DEUX_PARTS)
            .deuxPartsEtDemi(DEFAULT_DEUX_PARTS_ET_DEMI)
            .troisParts(DEFAULT_TROIS_PARTS)
            .troisPartsEtDemi(DEFAULT_TROIS_PARTS_ET_DEMI)
            .quatreParts(DEFAULT_QUATRE_PARTS)
            .quatrePartsEtDemi(DEFAULT_QUATRE_PARTS_ET_DEMI)
            .cinqParts(DEFAULT_CINQ_PARTS)
            .deleted(DEFAULT_DELETED);
        return bareme;
    }

    @Before
    public void initTest() {
        bareme = createEntity(em);
    }

    @Test
    @Transactional
    public void createBareme() throws Exception {
        int databaseSizeBeforeCreate = baremeRepository.findAll().size();

        // Create the Bareme
        restBaremeMockMvc.perform(post("/api/baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bareme)))
            .andExpect(status().isCreated());

        // Validate the Bareme in the database
        List<Bareme> baremeList = baremeRepository.findAll();
        assertThat(baremeList).hasSize(databaseSizeBeforeCreate + 1);
        Bareme testBareme = baremeList.get(baremeList.size() - 1);
        assertThat(testBareme.getRevenuBrut()).isEqualTo(DEFAULT_REVENU_BRUT);
        assertThat(testBareme.getTrimF()).isEqualTo(DEFAULT_TRIM_F);
        assertThat(testBareme.getUnePart()).isEqualTo(DEFAULT_UNE_PART);
        assertThat(testBareme.getUnePartEtDemi()).isEqualTo(DEFAULT_UNE_PART_ET_DEMI);
        assertThat(testBareme.getDeuxParts()).isEqualTo(DEFAULT_DEUX_PARTS);
        assertThat(testBareme.getDeuxPartsEtDemi()).isEqualTo(DEFAULT_DEUX_PARTS_ET_DEMI);
        assertThat(testBareme.getTroisParts()).isEqualTo(DEFAULT_TROIS_PARTS);
        assertThat(testBareme.getTroisPartsEtDemi()).isEqualTo(DEFAULT_TROIS_PARTS_ET_DEMI);
        assertThat(testBareme.getQuatreParts()).isEqualTo(DEFAULT_QUATRE_PARTS);
        assertThat(testBareme.getQuatrePartsEtDemi()).isEqualTo(DEFAULT_QUATRE_PARTS_ET_DEMI);
        assertThat(testBareme.getCinqParts()).isEqualTo(DEFAULT_CINQ_PARTS);
        assertThat(testBareme.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createBaremeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = baremeRepository.findAll().size();

        // Create the Bareme with an existing ID
        bareme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBaremeMockMvc.perform(post("/api/baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bareme)))
            .andExpect(status().isBadRequest());

        // Validate the Bareme in the database
        List<Bareme> baremeList = baremeRepository.findAll();
        assertThat(baremeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRevenuBrutIsRequired() throws Exception {
        int databaseSizeBeforeTest = baremeRepository.findAll().size();
        // set the field null
        bareme.setRevenuBrut(null);

        // Create the Bareme, which fails.

        restBaremeMockMvc.perform(post("/api/baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bareme)))
            .andExpect(status().isBadRequest());

        List<Bareme> baremeList = baremeRepository.findAll();
        assertThat(baremeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrimFIsRequired() throws Exception {
        int databaseSizeBeforeTest = baremeRepository.findAll().size();
        // set the field null
        bareme.setTrimF(null);

        // Create the Bareme, which fails.

        restBaremeMockMvc.perform(post("/api/baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bareme)))
            .andExpect(status().isBadRequest());

        List<Bareme> baremeList = baremeRepository.findAll();
        assertThat(baremeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnePartIsRequired() throws Exception {
        int databaseSizeBeforeTest = baremeRepository.findAll().size();
        // set the field null
        bareme.setUnePart(null);

        // Create the Bareme, which fails.

        restBaremeMockMvc.perform(post("/api/baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bareme)))
            .andExpect(status().isBadRequest());

        List<Bareme> baremeList = baremeRepository.findAll();
        assertThat(baremeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnePartEtDemiIsRequired() throws Exception {
        int databaseSizeBeforeTest = baremeRepository.findAll().size();
        // set the field null
        bareme.setUnePartEtDemi(null);

        // Create the Bareme, which fails.

        restBaremeMockMvc.perform(post("/api/baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bareme)))
            .andExpect(status().isBadRequest());

        List<Bareme> baremeList = baremeRepository.findAll();
        assertThat(baremeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeuxPartsIsRequired() throws Exception {
        int databaseSizeBeforeTest = baremeRepository.findAll().size();
        // set the field null
        bareme.setDeuxParts(null);

        // Create the Bareme, which fails.

        restBaremeMockMvc.perform(post("/api/baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bareme)))
            .andExpect(status().isBadRequest());

        List<Bareme> baremeList = baremeRepository.findAll();
        assertThat(baremeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeuxPartsEtDemiIsRequired() throws Exception {
        int databaseSizeBeforeTest = baremeRepository.findAll().size();
        // set the field null
        bareme.setDeuxPartsEtDemi(null);

        // Create the Bareme, which fails.

        restBaremeMockMvc.perform(post("/api/baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bareme)))
            .andExpect(status().isBadRequest());

        List<Bareme> baremeList = baremeRepository.findAll();
        assertThat(baremeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTroisPartsIsRequired() throws Exception {
        int databaseSizeBeforeTest = baremeRepository.findAll().size();
        // set the field null
        bareme.setTroisParts(null);

        // Create the Bareme, which fails.

        restBaremeMockMvc.perform(post("/api/baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bareme)))
            .andExpect(status().isBadRequest());

        List<Bareme> baremeList = baremeRepository.findAll();
        assertThat(baremeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTroisPartsEtDemiIsRequired() throws Exception {
        int databaseSizeBeforeTest = baremeRepository.findAll().size();
        // set the field null
        bareme.setTroisPartsEtDemi(null);

        // Create the Bareme, which fails.

        restBaremeMockMvc.perform(post("/api/baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bareme)))
            .andExpect(status().isBadRequest());

        List<Bareme> baremeList = baremeRepository.findAll();
        assertThat(baremeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuatrePartsIsRequired() throws Exception {
        int databaseSizeBeforeTest = baremeRepository.findAll().size();
        // set the field null
        bareme.setQuatreParts(null);

        // Create the Bareme, which fails.

        restBaremeMockMvc.perform(post("/api/baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bareme)))
            .andExpect(status().isBadRequest());

        List<Bareme> baremeList = baremeRepository.findAll();
        assertThat(baremeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuatrePartsEtDemiIsRequired() throws Exception {
        int databaseSizeBeforeTest = baremeRepository.findAll().size();
        // set the field null
        bareme.setQuatrePartsEtDemi(null);

        // Create the Bareme, which fails.

        restBaremeMockMvc.perform(post("/api/baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bareme)))
            .andExpect(status().isBadRequest());

        List<Bareme> baremeList = baremeRepository.findAll();
        assertThat(baremeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCinqPartsIsRequired() throws Exception {
        int databaseSizeBeforeTest = baremeRepository.findAll().size();
        // set the field null
        bareme.setCinqParts(null);

        // Create the Bareme, which fails.

        restBaremeMockMvc.perform(post("/api/baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bareme)))
            .andExpect(status().isBadRequest());

        List<Bareme> baremeList = baremeRepository.findAll();
        assertThat(baremeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBaremes() throws Exception {
        // Initialize the database
        baremeRepository.saveAndFlush(bareme);

        // Get all the baremeList
        restBaremeMockMvc.perform(get("/api/baremes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bareme.getId().intValue())))
            .andExpect(jsonPath("$.[*].revenuBrut").value(hasItem(DEFAULT_REVENU_BRUT.doubleValue())))
            .andExpect(jsonPath("$.[*].trimF").value(hasItem(DEFAULT_TRIM_F.doubleValue())))
            .andExpect(jsonPath("$.[*].unePart").value(hasItem(DEFAULT_UNE_PART.doubleValue())))
            .andExpect(jsonPath("$.[*].unePartEtDemi").value(hasItem(DEFAULT_UNE_PART_ET_DEMI.doubleValue())))
            .andExpect(jsonPath("$.[*].deuxParts").value(hasItem(DEFAULT_DEUX_PARTS.doubleValue())))
            .andExpect(jsonPath("$.[*].deuxPartsEtDemi").value(hasItem(DEFAULT_DEUX_PARTS_ET_DEMI.doubleValue())))
            .andExpect(jsonPath("$.[*].troisParts").value(hasItem(DEFAULT_TROIS_PARTS.doubleValue())))
            .andExpect(jsonPath("$.[*].troisPartsEtDemi").value(hasItem(DEFAULT_TROIS_PARTS_ET_DEMI.doubleValue())))
            .andExpect(jsonPath("$.[*].quatreParts").value(hasItem(DEFAULT_QUATRE_PARTS.doubleValue())))
            .andExpect(jsonPath("$.[*].quatrePartsEtDemi").value(hasItem(DEFAULT_QUATRE_PARTS_ET_DEMI.doubleValue())))
            .andExpect(jsonPath("$.[*].cinqParts").value(hasItem(DEFAULT_CINQ_PARTS.doubleValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getBareme() throws Exception {
        // Initialize the database
        baremeRepository.saveAndFlush(bareme);

        // Get the bareme
        restBaremeMockMvc.perform(get("/api/baremes/{id}", bareme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bareme.getId().intValue()))
            .andExpect(jsonPath("$.revenuBrut").value(DEFAULT_REVENU_BRUT.doubleValue()))
            .andExpect(jsonPath("$.trimF").value(DEFAULT_TRIM_F.doubleValue()))
            .andExpect(jsonPath("$.unePart").value(DEFAULT_UNE_PART.doubleValue()))
            .andExpect(jsonPath("$.unePartEtDemi").value(DEFAULT_UNE_PART_ET_DEMI.doubleValue()))
            .andExpect(jsonPath("$.deuxParts").value(DEFAULT_DEUX_PARTS.doubleValue()))
            .andExpect(jsonPath("$.deuxPartsEtDemi").value(DEFAULT_DEUX_PARTS_ET_DEMI.doubleValue()))
            .andExpect(jsonPath("$.troisParts").value(DEFAULT_TROIS_PARTS.doubleValue()))
            .andExpect(jsonPath("$.troisPartsEtDemi").value(DEFAULT_TROIS_PARTS_ET_DEMI.doubleValue()))
            .andExpect(jsonPath("$.quatreParts").value(DEFAULT_QUATRE_PARTS.doubleValue()))
            .andExpect(jsonPath("$.quatrePartsEtDemi").value(DEFAULT_QUATRE_PARTS_ET_DEMI.doubleValue()))
            .andExpect(jsonPath("$.cinqParts").value(DEFAULT_CINQ_PARTS.doubleValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBareme() throws Exception {
        // Get the bareme
        restBaremeMockMvc.perform(get("/api/baremes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBareme() throws Exception {
        // Initialize the database
        baremeService.save(bareme);

        int databaseSizeBeforeUpdate = baremeRepository.findAll().size();

        // Update the bareme
        Bareme updatedBareme = baremeRepository.findOne(bareme.getId());
        // Disconnect from session so that the updates on updatedBareme are not directly saved in db
        em.detach(updatedBareme);
        updatedBareme
            .revenuBrut(UPDATED_REVENU_BRUT)
            .trimF(UPDATED_TRIM_F)
            .unePart(UPDATED_UNE_PART)
            .unePartEtDemi(UPDATED_UNE_PART_ET_DEMI)
            .deuxParts(UPDATED_DEUX_PARTS)
            .deuxPartsEtDemi(UPDATED_DEUX_PARTS_ET_DEMI)
            .troisParts(UPDATED_TROIS_PARTS)
            .troisPartsEtDemi(UPDATED_TROIS_PARTS_ET_DEMI)
            .quatreParts(UPDATED_QUATRE_PARTS)
            .quatrePartsEtDemi(UPDATED_QUATRE_PARTS_ET_DEMI)
            .cinqParts(UPDATED_CINQ_PARTS)
            .deleted(UPDATED_DELETED);

        restBaremeMockMvc.perform(put("/api/baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBareme)))
            .andExpect(status().isOk());

        // Validate the Bareme in the database
        List<Bareme> baremeList = baremeRepository.findAll();
        assertThat(baremeList).hasSize(databaseSizeBeforeUpdate);
        Bareme testBareme = baremeList.get(baremeList.size() - 1);
        assertThat(testBareme.getRevenuBrut()).isEqualTo(UPDATED_REVENU_BRUT);
        assertThat(testBareme.getTrimF()).isEqualTo(UPDATED_TRIM_F);
        assertThat(testBareme.getUnePart()).isEqualTo(UPDATED_UNE_PART);
        assertThat(testBareme.getUnePartEtDemi()).isEqualTo(UPDATED_UNE_PART_ET_DEMI);
        assertThat(testBareme.getDeuxParts()).isEqualTo(UPDATED_DEUX_PARTS);
        assertThat(testBareme.getDeuxPartsEtDemi()).isEqualTo(UPDATED_DEUX_PARTS_ET_DEMI);
        assertThat(testBareme.getTroisParts()).isEqualTo(UPDATED_TROIS_PARTS);
        assertThat(testBareme.getTroisPartsEtDemi()).isEqualTo(UPDATED_TROIS_PARTS_ET_DEMI);
        assertThat(testBareme.getQuatreParts()).isEqualTo(UPDATED_QUATRE_PARTS);
        assertThat(testBareme.getQuatrePartsEtDemi()).isEqualTo(UPDATED_QUATRE_PARTS_ET_DEMI);
        assertThat(testBareme.getCinqParts()).isEqualTo(UPDATED_CINQ_PARTS);
        assertThat(testBareme.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingBareme() throws Exception {
        int databaseSizeBeforeUpdate = baremeRepository.findAll().size();

        // Create the Bareme

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBaremeMockMvc.perform(put("/api/baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bareme)))
            .andExpect(status().isCreated());

        // Validate the Bareme in the database
        List<Bareme> baremeList = baremeRepository.findAll();
        assertThat(baremeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBareme() throws Exception {
        // Initialize the database
        baremeService.save(bareme);

        int databaseSizeBeforeDelete = baremeRepository.findAll().size();

        // Get the bareme
        restBaremeMockMvc.perform(delete("/api/baremes/{id}", bareme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bareme> baremeList = baremeRepository.findAll();
        assertThat(baremeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bareme.class);
        Bareme bareme1 = new Bareme();
        bareme1.setId(1L);
        Bareme bareme2 = new Bareme();
        bareme2.setId(bareme1.getId());
        assertThat(bareme1).isEqualTo(bareme2);
        bareme2.setId(2L);
        assertThat(bareme1).isNotEqualTo(bareme2);
        bareme1.setId(null);
        assertThat(bareme1).isNotEqualTo(bareme2);
    }
}
