package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.Collaborateur;
import com.emard.repository.CollaborateurRepository;
import com.emard.service.CollaborateurService;
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
 * Test class for the CollaborateurResource REST controller.
 *
 * @see CollaborateurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class CollaborateurResourceIntTest {

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final Double DEFAULT_TAUX_HORAIRE = 1D;
    private static final Double UPDATED_TAUX_HORAIRE = 2D;

    private static final Double DEFAULT_SALAIRE_DE_BASE = 1D;
    private static final Double UPDATED_SALAIRE_DE_BASE = 2D;

    private static final Double DEFAULT_SUR_SALAIRE = 1D;
    private static final Double UPDATED_SUR_SALAIRE = 2D;

    private static final Double DEFAULT_RETENUE_REPAS = 1D;
    private static final Double UPDATED_RETENUE_REPAS = 2D;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private CollaborateurRepository collaborateurRepository;

    @Autowired
    private CollaborateurService collaborateurService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCollaborateurMockMvc;

    private Collaborateur collaborateur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CollaborateurResource collaborateurResource = new CollaborateurResource(collaborateurService);
        this.restCollaborateurMockMvc = MockMvcBuilders.standaloneSetup(collaborateurResource)
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
    public static Collaborateur createEntity(EntityManager em) {
        Collaborateur collaborateur = new Collaborateur()
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .matricule(DEFAULT_MATRICULE)
            .adresse(DEFAULT_ADRESSE)
            .tauxHoraire(DEFAULT_TAUX_HORAIRE)
            .salaireDeBase(DEFAULT_SALAIRE_DE_BASE)
            .surSalaire(DEFAULT_SUR_SALAIRE)
            .retenueRepas(DEFAULT_RETENUE_REPAS)
            .deleted(DEFAULT_DELETED)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return collaborateur;
    }

    @Before
    public void initTest() {
        collaborateur = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollaborateur() throws Exception {
        int databaseSizeBeforeCreate = collaborateurRepository.findAll().size();

        // Create the Collaborateur
        restCollaborateurMockMvc.perform(post("/api/collaborateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaborateur)))
            .andExpect(status().isCreated());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeCreate + 1);
        Collaborateur testCollaborateur = collaborateurList.get(collaborateurList.size() - 1);
        assertThat(testCollaborateur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testCollaborateur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCollaborateur.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testCollaborateur.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testCollaborateur.getTauxHoraire()).isEqualTo(DEFAULT_TAUX_HORAIRE);
        assertThat(testCollaborateur.getSalaireDeBase()).isEqualTo(DEFAULT_SALAIRE_DE_BASE);
        assertThat(testCollaborateur.getSurSalaire()).isEqualTo(DEFAULT_SUR_SALAIRE);
        assertThat(testCollaborateur.getRetenueRepas()).isEqualTo(DEFAULT_RETENUE_REPAS);
        assertThat(testCollaborateur.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testCollaborateur.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testCollaborateur.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testCollaborateur.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createCollaborateurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collaborateurRepository.findAll().size();

        // Create the Collaborateur with an existing ID
        collaborateur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollaborateurMockMvc.perform(post("/api/collaborateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaborateur)))
            .andExpect(status().isBadRequest());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = collaborateurRepository.findAll().size();
        // set the field null
        collaborateur.setPrenom(null);

        // Create the Collaborateur, which fails.

        restCollaborateurMockMvc.perform(post("/api/collaborateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaborateur)))
            .andExpect(status().isBadRequest());

        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = collaborateurRepository.findAll().size();
        // set the field null
        collaborateur.setNom(null);

        // Create the Collaborateur, which fails.

        restCollaborateurMockMvc.perform(post("/api/collaborateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaborateur)))
            .andExpect(status().isBadRequest());

        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMatriculeIsRequired() throws Exception {
        int databaseSizeBeforeTest = collaborateurRepository.findAll().size();
        // set the field null
        collaborateur.setMatricule(null);

        // Create the Collaborateur, which fails.

        restCollaborateurMockMvc.perform(post("/api/collaborateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaborateur)))
            .andExpect(status().isBadRequest());

        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseIsRequired() throws Exception {
        int databaseSizeBeforeTest = collaborateurRepository.findAll().size();
        // set the field null
        collaborateur.setAdresse(null);

        // Create the Collaborateur, which fails.

        restCollaborateurMockMvc.perform(post("/api/collaborateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaborateur)))
            .andExpect(status().isBadRequest());

        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTauxHoraireIsRequired() throws Exception {
        int databaseSizeBeforeTest = collaborateurRepository.findAll().size();
        // set the field null
        collaborateur.setTauxHoraire(null);

        // Create the Collaborateur, which fails.

        restCollaborateurMockMvc.perform(post("/api/collaborateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaborateur)))
            .andExpect(status().isBadRequest());

        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalaireDeBaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = collaborateurRepository.findAll().size();
        // set the field null
        collaborateur.setSalaireDeBase(null);

        // Create the Collaborateur, which fails.

        restCollaborateurMockMvc.perform(post("/api/collaborateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaborateur)))
            .andExpect(status().isBadRequest());

        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSurSalaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = collaborateurRepository.findAll().size();
        // set the field null
        collaborateur.setSurSalaire(null);

        // Create the Collaborateur, which fails.

        restCollaborateurMockMvc.perform(post("/api/collaborateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaborateur)))
            .andExpect(status().isBadRequest());

        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRetenueRepasIsRequired() throws Exception {
        int databaseSizeBeforeTest = collaborateurRepository.findAll().size();
        // set the field null
        collaborateur.setRetenueRepas(null);

        // Create the Collaborateur, which fails.

        restCollaborateurMockMvc.perform(post("/api/collaborateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaborateur)))
            .andExpect(status().isBadRequest());

        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = collaborateurRepository.findAll().size();
        // set the field null
        collaborateur.setDateNaissance(null);

        // Create the Collaborateur, which fails.

        restCollaborateurMockMvc.perform(post("/api/collaborateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaborateur)))
            .andExpect(status().isBadRequest());

        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCollaborateurs() throws Exception {
        // Initialize the database
        collaborateurRepository.saveAndFlush(collaborateur);

        // Get all the collaborateurList
        restCollaborateurMockMvc.perform(get("/api/collaborateurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collaborateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].tauxHoraire").value(hasItem(DEFAULT_TAUX_HORAIRE.doubleValue())))
            .andExpect(jsonPath("$.[*].salaireDeBase").value(hasItem(DEFAULT_SALAIRE_DE_BASE.doubleValue())))
            .andExpect(jsonPath("$.[*].surSalaire").value(hasItem(DEFAULT_SUR_SALAIRE.doubleValue())))
            .andExpect(jsonPath("$.[*].retenueRepas").value(hasItem(DEFAULT_RETENUE_REPAS.doubleValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    public void getCollaborateur() throws Exception {
        // Initialize the database
        collaborateurRepository.saveAndFlush(collaborateur);

        // Get the collaborateur
        restCollaborateurMockMvc.perform(get("/api/collaborateurs/{id}", collaborateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(collaborateur.getId().intValue()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.tauxHoraire").value(DEFAULT_TAUX_HORAIRE.doubleValue()))
            .andExpect(jsonPath("$.salaireDeBase").value(DEFAULT_SALAIRE_DE_BASE.doubleValue()))
            .andExpect(jsonPath("$.surSalaire").value(DEFAULT_SUR_SALAIRE.doubleValue()))
            .andExpect(jsonPath("$.retenueRepas").value(DEFAULT_RETENUE_REPAS.doubleValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingCollaborateur() throws Exception {
        // Get the collaborateur
        restCollaborateurMockMvc.perform(get("/api/collaborateurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollaborateur() throws Exception {
        // Initialize the database
        collaborateurService.save(collaborateur);

        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();

        // Update the collaborateur
        Collaborateur updatedCollaborateur = collaborateurRepository.findOne(collaborateur.getId());
        // Disconnect from session so that the updates on updatedCollaborateur are not directly saved in db
        em.detach(updatedCollaborateur);
        updatedCollaborateur
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .matricule(UPDATED_MATRICULE)
            .adresse(UPDATED_ADRESSE)
            .tauxHoraire(UPDATED_TAUX_HORAIRE)
            .salaireDeBase(UPDATED_SALAIRE_DE_BASE)
            .surSalaire(UPDATED_SUR_SALAIRE)
            .retenueRepas(UPDATED_RETENUE_REPAS)
            .deleted(UPDATED_DELETED)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restCollaborateurMockMvc.perform(put("/api/collaborateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCollaborateur)))
            .andExpect(status().isOk());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
        Collaborateur testCollaborateur = collaborateurList.get(collaborateurList.size() - 1);
        assertThat(testCollaborateur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testCollaborateur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCollaborateur.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testCollaborateur.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testCollaborateur.getTauxHoraire()).isEqualTo(UPDATED_TAUX_HORAIRE);
        assertThat(testCollaborateur.getSalaireDeBase()).isEqualTo(UPDATED_SALAIRE_DE_BASE);
        assertThat(testCollaborateur.getSurSalaire()).isEqualTo(UPDATED_SUR_SALAIRE);
        assertThat(testCollaborateur.getRetenueRepas()).isEqualTo(UPDATED_RETENUE_REPAS);
        assertThat(testCollaborateur.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testCollaborateur.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testCollaborateur.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testCollaborateur.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCollaborateur() throws Exception {
        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();

        // Create the Collaborateur

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCollaborateurMockMvc.perform(put("/api/collaborateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaborateur)))
            .andExpect(status().isCreated());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCollaborateur() throws Exception {
        // Initialize the database
        collaborateurService.save(collaborateur);

        int databaseSizeBeforeDelete = collaborateurRepository.findAll().size();

        // Get the collaborateur
        restCollaborateurMockMvc.perform(delete("/api/collaborateurs/{id}", collaborateur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Collaborateur.class);
        Collaborateur collaborateur1 = new Collaborateur();
        collaborateur1.setId(1L);
        Collaborateur collaborateur2 = new Collaborateur();
        collaborateur2.setId(collaborateur1.getId());
        assertThat(collaborateur1).isEqualTo(collaborateur2);
        collaborateur2.setId(2L);
        assertThat(collaborateur1).isNotEqualTo(collaborateur2);
        collaborateur1.setId(null);
        assertThat(collaborateur1).isNotEqualTo(collaborateur2);
    }
}
