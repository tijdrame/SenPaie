package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.Bulletin;
import com.emard.domain.Collaborateur;
import com.emard.domain.TypePaiement;
import com.emard.repository.BulletinRepository;
import com.emard.service.BulletinService;
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
 * Test class for the BulletinResource REST controller.
 *
 * @see BulletinResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class BulletinResourceIntTest {

    private static final Double DEFAULT_RETENUE_IPM = 1D;
    private static final Double UPDATED_RETENUE_IPM = 2D;

    private static final Double DEFAULT_RETENUE_PHARMACIE = 1D;
    private static final Double UPDATED_RETENUE_PHARMACIE = 2D;

    private static final Double DEFAULT_AUTRE_RETENUE = 1D;
    private static final Double UPDATED_AUTRE_RETENUE = 2D;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DELETED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DELETED = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_BRUT_FISCAL = 1D;
    private static final Double UPDATED_BRUT_FISCAL = 2D;

    private static final Double DEFAULT_NET_A_PAYER = 1D;
    private static final Double UPDATED_NET_A_PAYER = 2D;

    private static final Double DEFAULT_SALAIRE_BRUT_MENSUEL = 1D;
    private static final Double UPDATED_SALAIRE_BRUT_MENSUEL = 2D;

    private static final Double DEFAULT_IMPOT_SUR_REVENU = 1D;
    private static final Double UPDATED_IMPOT_SUR_REVENU = 2D;

    private static final Double DEFAULT_TRIMF = 1D;
    private static final Double UPDATED_TRIMF = 2D;

    private static final Double DEFAULT_IPRES_PART_SALARIALE = 1D;
    private static final Double UPDATED_IPRES_PART_SALARIALE = 2D;

    private static final Double DEFAULT_TOT_RETENUE = 1D;
    private static final Double UPDATED_TOT_RETENUE = 2D;

    private static final Double DEFAULT_IPRES_PART_PATRONALES = 1D;
    private static final Double UPDATED_IPRES_PART_PATRONALES = 2D;

    private static final Double DEFAULT_CSS_ACCIDENT_DE_TRAVAIL = 1D;
    private static final Double UPDATED_CSS_ACCIDENT_DE_TRAVAIL = 2D;

    private static final Double DEFAULT_CSS_PRESTATION_FAMILIALE = 1D;
    private static final Double UPDATED_CSS_PRESTATION_FAMILIALE = 2D;

    private static final Double DEFAULT_IPM_PATRONALE = 1D;
    private static final Double UPDATED_IPM_PATRONALE = 2D;

    private static final Double DEFAULT_CONTRIBUTION_FORFAITAIRE = 1D;
    private static final Double UPDATED_CONTRIBUTION_FORFAITAIRE = 2D;

    private static final Float DEFAULT_NB_PART = 1F;
    private static final Float UPDATED_NB_PART = 2F;

    private static final Integer DEFAULT_NB_FEMMES = 1;
    private static final Integer UPDATED_NB_FEMMES = 2;

    private static final Integer DEFAULT_NB_ENFANTS = 1;
    private static final Integer UPDATED_NB_ENFANTS = 2;

    @Autowired
    private BulletinRepository bulletinRepository;

    @Autowired
    private BulletinService bulletinService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBulletinMockMvc;

    private Bulletin bulletin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BulletinResource bulletinResource = new BulletinResource(bulletinService);
        this.restBulletinMockMvc = MockMvcBuilders.standaloneSetup(bulletinResource)
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
    public static Bulletin createEntity(EntityManager em) {
        Bulletin bulletin = new Bulletin()
            .retenueIpm(DEFAULT_RETENUE_IPM)
            .retenuePharmacie(DEFAULT_RETENUE_PHARMACIE)
            .autreRetenue(DEFAULT_AUTRE_RETENUE)
            .deleted(DEFAULT_DELETED)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .brutFiscal(DEFAULT_BRUT_FISCAL)
            .netAPayer(DEFAULT_NET_A_PAYER)
            .salaireBrutMensuel(DEFAULT_SALAIRE_BRUT_MENSUEL)
            .impotSurRevenu(DEFAULT_IMPOT_SUR_REVENU)
            .trimf(DEFAULT_TRIMF)
            .ipresPartSalariale(DEFAULT_IPRES_PART_SALARIALE)
            .totRetenue(DEFAULT_TOT_RETENUE)
            .ipresPartPatronales(DEFAULT_IPRES_PART_PATRONALES)
            .cssAccidentDeTravail(DEFAULT_CSS_ACCIDENT_DE_TRAVAIL)
            .cssPrestationFamiliale(DEFAULT_CSS_PRESTATION_FAMILIALE)
            .ipmPatronale(DEFAULT_IPM_PATRONALE)
            .contributionForfaitaire(DEFAULT_CONTRIBUTION_FORFAITAIRE)
            .nbPart(DEFAULT_NB_PART)
            .nbFemmes(DEFAULT_NB_FEMMES)
            .nbEnfants(DEFAULT_NB_ENFANTS);
        // Add required entity
        Collaborateur collaborateur = CollaborateurResourceIntTest.createEntity(em);
        em.persist(collaborateur);
        em.flush();
        bulletin.setCollaborateur(collaborateur);
        // Add required entity
        TypePaiement typePaiement = TypePaiementResourceIntTest.createEntity(em);
        em.persist(typePaiement);
        em.flush();
        bulletin.setTypePaiement(typePaiement);
        return bulletin;
    }

    @Before
    public void initTest() {
        bulletin = createEntity(em);
    }

    @Test
    @Transactional
    public void createBulletin() throws Exception {
        int databaseSizeBeforeCreate = bulletinRepository.findAll().size();

        // Create the Bulletin
        restBulletinMockMvc.perform(post("/api/bulletins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bulletin)))
            .andExpect(status().isCreated());

        // Validate the Bulletin in the database
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeCreate + 1);
        Bulletin testBulletin = bulletinList.get(bulletinList.size() - 1);
        assertThat(testBulletin.getRetenueIpm()).isEqualTo(DEFAULT_RETENUE_IPM);
        assertThat(testBulletin.getRetenuePharmacie()).isEqualTo(DEFAULT_RETENUE_PHARMACIE);
        assertThat(testBulletin.getAutreRetenue()).isEqualTo(DEFAULT_AUTRE_RETENUE);
        assertThat(testBulletin.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testBulletin.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testBulletin.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testBulletin.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testBulletin.getBrutFiscal()).isEqualTo(DEFAULT_BRUT_FISCAL);
        assertThat(testBulletin.getNetAPayer()).isEqualTo(DEFAULT_NET_A_PAYER);
        assertThat(testBulletin.getSalaireBrutMensuel()).isEqualTo(DEFAULT_SALAIRE_BRUT_MENSUEL);
        assertThat(testBulletin.getImpotSurRevenu()).isEqualTo(DEFAULT_IMPOT_SUR_REVENU);
        assertThat(testBulletin.getTrimf()).isEqualTo(DEFAULT_TRIMF);
        assertThat(testBulletin.getIpresPartSalariale()).isEqualTo(DEFAULT_IPRES_PART_SALARIALE);
        assertThat(testBulletin.getTotRetenue()).isEqualTo(DEFAULT_TOT_RETENUE);
        assertThat(testBulletin.getIpresPartPatronales()).isEqualTo(DEFAULT_IPRES_PART_PATRONALES);
        assertThat(testBulletin.getCssAccidentDeTravail()).isEqualTo(DEFAULT_CSS_ACCIDENT_DE_TRAVAIL);
        assertThat(testBulletin.getCssPrestationFamiliale()).isEqualTo(DEFAULT_CSS_PRESTATION_FAMILIALE);
        assertThat(testBulletin.getIpmPatronale()).isEqualTo(DEFAULT_IPM_PATRONALE);
        assertThat(testBulletin.getContributionForfaitaire()).isEqualTo(DEFAULT_CONTRIBUTION_FORFAITAIRE);
        assertThat(testBulletin.getNbPart()).isEqualTo(DEFAULT_NB_PART);
        assertThat(testBulletin.getNbFemmes()).isEqualTo(DEFAULT_NB_FEMMES);
        assertThat(testBulletin.getNbEnfants()).isEqualTo(DEFAULT_NB_ENFANTS);
    }

    @Test
    @Transactional
    public void createBulletinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bulletinRepository.findAll().size();

        // Create the Bulletin with an existing ID
        bulletin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBulletinMockMvc.perform(post("/api/bulletins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bulletin)))
            .andExpect(status().isBadRequest());

        // Validate the Bulletin in the database
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBulletins() throws Exception {
        // Initialize the database
        bulletinRepository.saveAndFlush(bulletin);

        // Get all the bulletinList
        restBulletinMockMvc.perform(get("/api/bulletins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bulletin.getId().intValue())))
            .andExpect(jsonPath("$.[*].retenueIpm").value(hasItem(DEFAULT_RETENUE_IPM.doubleValue())))
            .andExpect(jsonPath("$.[*].retenuePharmacie").value(hasItem(DEFAULT_RETENUE_PHARMACIE.doubleValue())))
            .andExpect(jsonPath("$.[*].autreRetenue").value(hasItem(DEFAULT_AUTRE_RETENUE.doubleValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].brutFiscal").value(hasItem(DEFAULT_BRUT_FISCAL.doubleValue())))
            .andExpect(jsonPath("$.[*].netAPayer").value(hasItem(DEFAULT_NET_A_PAYER.doubleValue())))
            .andExpect(jsonPath("$.[*].salaireBrutMensuel").value(hasItem(DEFAULT_SALAIRE_BRUT_MENSUEL.doubleValue())))
            .andExpect(jsonPath("$.[*].impotSurRevenu").value(hasItem(DEFAULT_IMPOT_SUR_REVENU.doubleValue())))
            .andExpect(jsonPath("$.[*].trimf").value(hasItem(DEFAULT_TRIMF.doubleValue())))
            .andExpect(jsonPath("$.[*].ipresPartSalariale").value(hasItem(DEFAULT_IPRES_PART_SALARIALE.doubleValue())))
            .andExpect(jsonPath("$.[*].totRetenue").value(hasItem(DEFAULT_TOT_RETENUE.doubleValue())))
            .andExpect(jsonPath("$.[*].ipresPartPatronales").value(hasItem(DEFAULT_IPRES_PART_PATRONALES.doubleValue())))
            .andExpect(jsonPath("$.[*].cssAccidentDeTravail").value(hasItem(DEFAULT_CSS_ACCIDENT_DE_TRAVAIL.doubleValue())))
            .andExpect(jsonPath("$.[*].cssPrestationFamiliale").value(hasItem(DEFAULT_CSS_PRESTATION_FAMILIALE.doubleValue())))
            .andExpect(jsonPath("$.[*].ipmPatronale").value(hasItem(DEFAULT_IPM_PATRONALE.doubleValue())))
            .andExpect(jsonPath("$.[*].contributionForfaitaire").value(hasItem(DEFAULT_CONTRIBUTION_FORFAITAIRE.doubleValue())))
            .andExpect(jsonPath("$.[*].nbPart").value(hasItem(DEFAULT_NB_PART.doubleValue())))
            .andExpect(jsonPath("$.[*].nbFemmes").value(hasItem(DEFAULT_NB_FEMMES)))
            .andExpect(jsonPath("$.[*].nbEnfants").value(hasItem(DEFAULT_NB_ENFANTS)));
    }

    @Test
    @Transactional
    public void getBulletin() throws Exception {
        // Initialize the database
        bulletinRepository.saveAndFlush(bulletin);

        // Get the bulletin
        restBulletinMockMvc.perform(get("/api/bulletins/{id}", bulletin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bulletin.getId().intValue()))
            .andExpect(jsonPath("$.retenueIpm").value(DEFAULT_RETENUE_IPM.doubleValue()))
            .andExpect(jsonPath("$.retenuePharmacie").value(DEFAULT_RETENUE_PHARMACIE.doubleValue()))
            .andExpect(jsonPath("$.autreRetenue").value(DEFAULT_AUTRE_RETENUE.doubleValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.brutFiscal").value(DEFAULT_BRUT_FISCAL.doubleValue()))
            .andExpect(jsonPath("$.netAPayer").value(DEFAULT_NET_A_PAYER.doubleValue()))
            .andExpect(jsonPath("$.salaireBrutMensuel").value(DEFAULT_SALAIRE_BRUT_MENSUEL.doubleValue()))
            .andExpect(jsonPath("$.impotSurRevenu").value(DEFAULT_IMPOT_SUR_REVENU.doubleValue()))
            .andExpect(jsonPath("$.trimf").value(DEFAULT_TRIMF.doubleValue()))
            .andExpect(jsonPath("$.ipresPartSalariale").value(DEFAULT_IPRES_PART_SALARIALE.doubleValue()))
            .andExpect(jsonPath("$.totRetenue").value(DEFAULT_TOT_RETENUE.doubleValue()))
            .andExpect(jsonPath("$.ipresPartPatronales").value(DEFAULT_IPRES_PART_PATRONALES.doubleValue()))
            .andExpect(jsonPath("$.cssAccidentDeTravail").value(DEFAULT_CSS_ACCIDENT_DE_TRAVAIL.doubleValue()))
            .andExpect(jsonPath("$.cssPrestationFamiliale").value(DEFAULT_CSS_PRESTATION_FAMILIALE.doubleValue()))
            .andExpect(jsonPath("$.ipmPatronale").value(DEFAULT_IPM_PATRONALE.doubleValue()))
            .andExpect(jsonPath("$.contributionForfaitaire").value(DEFAULT_CONTRIBUTION_FORFAITAIRE.doubleValue()))
            .andExpect(jsonPath("$.nbPart").value(DEFAULT_NB_PART.doubleValue()))
            .andExpect(jsonPath("$.nbFemmes").value(DEFAULT_NB_FEMMES))
            .andExpect(jsonPath("$.nbEnfants").value(DEFAULT_NB_ENFANTS));
    }

    @Test
    @Transactional
    public void getNonExistingBulletin() throws Exception {
        // Get the bulletin
        restBulletinMockMvc.perform(get("/api/bulletins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBulletin() throws Exception {
        // Initialize the database
        bulletinService.save(bulletin);

        int databaseSizeBeforeUpdate = bulletinRepository.findAll().size();

        // Update the bulletin
        Bulletin updatedBulletin = bulletinRepository.findOne(bulletin.getId());
        // Disconnect from session so that the updates on updatedBulletin are not directly saved in db
        em.detach(updatedBulletin);
        updatedBulletin
            .retenueIpm(UPDATED_RETENUE_IPM)
            .retenuePharmacie(UPDATED_RETENUE_PHARMACIE)
            .autreRetenue(UPDATED_AUTRE_RETENUE)
            .deleted(UPDATED_DELETED)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .brutFiscal(UPDATED_BRUT_FISCAL)
            .netAPayer(UPDATED_NET_A_PAYER)
            .salaireBrutMensuel(UPDATED_SALAIRE_BRUT_MENSUEL)
            .impotSurRevenu(UPDATED_IMPOT_SUR_REVENU)
            .trimf(UPDATED_TRIMF)
            .ipresPartSalariale(UPDATED_IPRES_PART_SALARIALE)
            .totRetenue(UPDATED_TOT_RETENUE)
            .ipresPartPatronales(UPDATED_IPRES_PART_PATRONALES)
            .cssAccidentDeTravail(UPDATED_CSS_ACCIDENT_DE_TRAVAIL)
            .cssPrestationFamiliale(UPDATED_CSS_PRESTATION_FAMILIALE)
            .ipmPatronale(UPDATED_IPM_PATRONALE)
            .contributionForfaitaire(UPDATED_CONTRIBUTION_FORFAITAIRE)
            .nbPart(UPDATED_NB_PART)
            .nbFemmes(UPDATED_NB_FEMMES)
            .nbEnfants(UPDATED_NB_ENFANTS);

        restBulletinMockMvc.perform(put("/api/bulletins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBulletin)))
            .andExpect(status().isOk());

        // Validate the Bulletin in the database
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeUpdate);
        Bulletin testBulletin = bulletinList.get(bulletinList.size() - 1);
        assertThat(testBulletin.getRetenueIpm()).isEqualTo(UPDATED_RETENUE_IPM);
        assertThat(testBulletin.getRetenuePharmacie()).isEqualTo(UPDATED_RETENUE_PHARMACIE);
        assertThat(testBulletin.getAutreRetenue()).isEqualTo(UPDATED_AUTRE_RETENUE);
        assertThat(testBulletin.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testBulletin.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testBulletin.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testBulletin.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testBulletin.getBrutFiscal()).isEqualTo(UPDATED_BRUT_FISCAL);
        assertThat(testBulletin.getNetAPayer()).isEqualTo(UPDATED_NET_A_PAYER);
        assertThat(testBulletin.getSalaireBrutMensuel()).isEqualTo(UPDATED_SALAIRE_BRUT_MENSUEL);
        assertThat(testBulletin.getImpotSurRevenu()).isEqualTo(UPDATED_IMPOT_SUR_REVENU);
        assertThat(testBulletin.getTrimf()).isEqualTo(UPDATED_TRIMF);
        assertThat(testBulletin.getIpresPartSalariale()).isEqualTo(UPDATED_IPRES_PART_SALARIALE);
        assertThat(testBulletin.getTotRetenue()).isEqualTo(UPDATED_TOT_RETENUE);
        assertThat(testBulletin.getIpresPartPatronales()).isEqualTo(UPDATED_IPRES_PART_PATRONALES);
        assertThat(testBulletin.getCssAccidentDeTravail()).isEqualTo(UPDATED_CSS_ACCIDENT_DE_TRAVAIL);
        assertThat(testBulletin.getCssPrestationFamiliale()).isEqualTo(UPDATED_CSS_PRESTATION_FAMILIALE);
        assertThat(testBulletin.getIpmPatronale()).isEqualTo(UPDATED_IPM_PATRONALE);
        assertThat(testBulletin.getContributionForfaitaire()).isEqualTo(UPDATED_CONTRIBUTION_FORFAITAIRE);
        assertThat(testBulletin.getNbPart()).isEqualTo(UPDATED_NB_PART);
        assertThat(testBulletin.getNbFemmes()).isEqualTo(UPDATED_NB_FEMMES);
        assertThat(testBulletin.getNbEnfants()).isEqualTo(UPDATED_NB_ENFANTS);
    }

    @Test
    @Transactional
    public void updateNonExistingBulletin() throws Exception {
        int databaseSizeBeforeUpdate = bulletinRepository.findAll().size();

        // Create the Bulletin

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBulletinMockMvc.perform(put("/api/bulletins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bulletin)))
            .andExpect(status().isCreated());

        // Validate the Bulletin in the database
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBulletin() throws Exception {
        // Initialize the database
        bulletinService.save(bulletin);

        int databaseSizeBeforeDelete = bulletinRepository.findAll().size();

        // Get the bulletin
        restBulletinMockMvc.perform(delete("/api/bulletins/{id}", bulletin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bulletin.class);
        Bulletin bulletin1 = new Bulletin();
        bulletin1.setId(1L);
        Bulletin bulletin2 = new Bulletin();
        bulletin2.setId(bulletin1.getId());
        assertThat(bulletin1).isEqualTo(bulletin2);
        bulletin2.setId(2L);
        assertThat(bulletin1).isNotEqualTo(bulletin2);
        bulletin1.setId(null);
        assertThat(bulletin1).isNotEqualTo(bulletin2);
    }
}
