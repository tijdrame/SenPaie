package com.emard.web.rest;

import com.emard.SenPaieApp;

import com.emard.domain.Pieces;
import com.emard.repository.PiecesRepository;
import com.emard.service.PiecesService;
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
 * Test class for the PiecesResource REST controller.
 *
 * @see PiecesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenPaieApp.class)
public class PiecesResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_EXPIRATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EXPIRATION = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final LocalDate DEFAULT_DATE_DELETED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DELETED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_UPDATED = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PiecesRepository piecesRepository;

    @Autowired
    private PiecesService piecesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPiecesMockMvc;

    private Pieces pieces;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PiecesResource piecesResource = new PiecesResource(piecesService);
        this.restPiecesMockMvc = MockMvcBuilders.standaloneSetup(piecesResource)
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
    public static Pieces createEntity(EntityManager em) {
        Pieces pieces = new Pieces()
            .libelle(DEFAULT_LIBELLE)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateExpiration(DEFAULT_DATE_EXPIRATION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .deleted(DEFAULT_DELETED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .dateUpdated(DEFAULT_DATE_UPDATED);
        return pieces;
    }

    @Before
    public void initTest() {
        pieces = createEntity(em);
    }

    @Test
    @Transactional
    public void createPieces() throws Exception {
        int databaseSizeBeforeCreate = piecesRepository.findAll().size();

        // Create the Pieces
        restPiecesMockMvc.perform(post("/api/pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieces)))
            .andExpect(status().isCreated());

        // Validate the Pieces in the database
        List<Pieces> piecesList = piecesRepository.findAll();
        assertThat(piecesList).hasSize(databaseSizeBeforeCreate + 1);
        Pieces testPieces = piecesList.get(piecesList.size() - 1);
        assertThat(testPieces.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testPieces.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testPieces.getDateExpiration()).isEqualTo(DEFAULT_DATE_EXPIRATION);
        assertThat(testPieces.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testPieces.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testPieces.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testPieces.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testPieces.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testPieces.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void createPiecesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = piecesRepository.findAll().size();

        // Create the Pieces with an existing ID
        pieces.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPiecesMockMvc.perform(post("/api/pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieces)))
            .andExpect(status().isBadRequest());

        // Validate the Pieces in the database
        List<Pieces> piecesList = piecesRepository.findAll();
        assertThat(piecesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = piecesRepository.findAll().size();
        // set the field null
        pieces.setLibelle(null);

        // Create the Pieces, which fails.

        restPiecesMockMvc.perform(post("/api/pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieces)))
            .andExpect(status().isBadRequest());

        List<Pieces> piecesList = piecesRepository.findAll();
        assertThat(piecesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = piecesRepository.findAll().size();
        // set the field null
        pieces.setDateDebut(null);

        // Create the Pieces, which fails.

        restPiecesMockMvc.perform(post("/api/pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieces)))
            .andExpect(status().isBadRequest());

        List<Pieces> piecesList = piecesRepository.findAll();
        assertThat(piecesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPieces() throws Exception {
        // Initialize the database
        piecesRepository.saveAndFlush(pieces);

        // Get all the piecesList
        restPiecesMockMvc.perform(get("/api/pieces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pieces.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateExpiration").value(hasItem(DEFAULT_DATE_EXPIRATION.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())));
    }

    @Test
    @Transactional
    public void getPieces() throws Exception {
        // Initialize the database
        piecesRepository.saveAndFlush(pieces);

        // Get the pieces
        restPiecesMockMvc.perform(get("/api/pieces/{id}", pieces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pieces.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateExpiration").value(DEFAULT_DATE_EXPIRATION.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPieces() throws Exception {
        // Get the pieces
        restPiecesMockMvc.perform(get("/api/pieces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePieces() throws Exception {
        // Initialize the database
        piecesService.save(pieces);

        int databaseSizeBeforeUpdate = piecesRepository.findAll().size();

        // Update the pieces
        Pieces updatedPieces = piecesRepository.findOne(pieces.getId());
        // Disconnect from session so that the updates on updatedPieces are not directly saved in db
        em.detach(updatedPieces);
        updatedPieces
            .libelle(UPDATED_LIBELLE)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateExpiration(UPDATED_DATE_EXPIRATION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .dateCreated(UPDATED_DATE_CREATED)
            .deleted(UPDATED_DELETED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .dateUpdated(UPDATED_DATE_UPDATED);

        restPiecesMockMvc.perform(put("/api/pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPieces)))
            .andExpect(status().isOk());

        // Validate the Pieces in the database
        List<Pieces> piecesList = piecesRepository.findAll();
        assertThat(piecesList).hasSize(databaseSizeBeforeUpdate);
        Pieces testPieces = piecesList.get(piecesList.size() - 1);
        assertThat(testPieces.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testPieces.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testPieces.getDateExpiration()).isEqualTo(UPDATED_DATE_EXPIRATION);
        assertThat(testPieces.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testPieces.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testPieces.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testPieces.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testPieces.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testPieces.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingPieces() throws Exception {
        int databaseSizeBeforeUpdate = piecesRepository.findAll().size();

        // Create the Pieces

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPiecesMockMvc.perform(put("/api/pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieces)))
            .andExpect(status().isCreated());

        // Validate the Pieces in the database
        List<Pieces> piecesList = piecesRepository.findAll();
        assertThat(piecesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePieces() throws Exception {
        // Initialize the database
        piecesService.save(pieces);

        int databaseSizeBeforeDelete = piecesRepository.findAll().size();

        // Get the pieces
        restPiecesMockMvc.perform(delete("/api/pieces/{id}", pieces.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pieces> piecesList = piecesRepository.findAll();
        assertThat(piecesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pieces.class);
        Pieces pieces1 = new Pieces();
        pieces1.setId(1L);
        Pieces pieces2 = new Pieces();
        pieces2.setId(pieces1.getId());
        assertThat(pieces1).isEqualTo(pieces2);
        pieces2.setId(2L);
        assertThat(pieces1).isNotEqualTo(pieces2);
        pieces1.setId(null);
        assertThat(pieces1).isNotEqualTo(pieces2);
    }
}
