package fr.adservio.mybizdev.web.rest;

import fr.adservio.mybizdev.MybizdevApp;

import fr.adservio.mybizdev.domain.Etat;
import fr.adservio.mybizdev.repository.EtatRepository;
import fr.adservio.mybizdev.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static fr.adservio.mybizdev.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.adservio.mybizdev.domain.enumeration.Statut;
/**
 * Test class for the EtatResource REST controller.
 *
 * @see EtatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybizdevApp.class)
public class EtatResourceIntTest {

    private static final Instant DEFAULT_DATE_DEMARRAGE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DEMARRAGE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_DEBUT_INTERCO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DEBUT_INTERCO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Statut DEFAULT_LIBELLE_STATUT = Statut.GO;
    private static final Statut UPDATED_LIBELLE_STATUT = Statut.NOGO;

    @Autowired
    private EtatRepository etatRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEtatMockMvc;

    private Etat etat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtatResource etatResource = new EtatResource(etatRepository);
        this.restEtatMockMvc = MockMvcBuilders.standaloneSetup(etatResource)
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
    public static Etat createEntity(EntityManager em) {
        Etat etat = new Etat()
            .dateDemarrage(DEFAULT_DATE_DEMARRAGE)
            .dateDebutInterco(DEFAULT_DATE_DEBUT_INTERCO)
            .libelleStatut(DEFAULT_LIBELLE_STATUT);
        return etat;
    }

    @Before
    public void initTest() {
        etat = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtat() throws Exception {
        int databaseSizeBeforeCreate = etatRepository.findAll().size();

        // Create the Etat
        restEtatMockMvc.perform(post("/api/etats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etat)))
            .andExpect(status().isCreated());

        // Validate the Etat in the database
        List<Etat> etatList = etatRepository.findAll();
        assertThat(etatList).hasSize(databaseSizeBeforeCreate + 1);
        Etat testEtat = etatList.get(etatList.size() - 1);
        assertThat(testEtat.getDateDemarrage()).isEqualTo(DEFAULT_DATE_DEMARRAGE);
        assertThat(testEtat.getDateDebutInterco()).isEqualTo(DEFAULT_DATE_DEBUT_INTERCO);
        assertThat(testEtat.getLibelleStatut()).isEqualTo(DEFAULT_LIBELLE_STATUT);
    }

    @Test
    @Transactional
    public void createEtatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etatRepository.findAll().size();

        // Create the Etat with an existing ID
        etat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtatMockMvc.perform(post("/api/etats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etat)))
            .andExpect(status().isBadRequest());

        // Validate the Etat in the database
        List<Etat> etatList = etatRepository.findAll();
        assertThat(etatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateDebutIntercoIsRequired() throws Exception {
        int databaseSizeBeforeTest = etatRepository.findAll().size();
        // set the field null
        etat.setDateDebutInterco(null);

        // Create the Etat, which fails.

        restEtatMockMvc.perform(post("/api/etats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etat)))
            .andExpect(status().isBadRequest());

        List<Etat> etatList = etatRepository.findAll();
        assertThat(etatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEtats() throws Exception {
        // Initialize the database
        etatRepository.saveAndFlush(etat);

        // Get all the etatList
        restEtatMockMvc.perform(get("/api/etats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etat.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDemarrage").value(hasItem(DEFAULT_DATE_DEMARRAGE.toString())))
            .andExpect(jsonPath("$.[*].dateDebutInterco").value(hasItem(DEFAULT_DATE_DEBUT_INTERCO.toString())))
            .andExpect(jsonPath("$.[*].libelleStatut").value(hasItem(DEFAULT_LIBELLE_STATUT.toString())));
    }

    @Test
    @Transactional
    public void getEtat() throws Exception {
        // Initialize the database
        etatRepository.saveAndFlush(etat);

        // Get the etat
        restEtatMockMvc.perform(get("/api/etats/{id}", etat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(etat.getId().intValue()))
            .andExpect(jsonPath("$.dateDemarrage").value(DEFAULT_DATE_DEMARRAGE.toString()))
            .andExpect(jsonPath("$.dateDebutInterco").value(DEFAULT_DATE_DEBUT_INTERCO.toString()))
            .andExpect(jsonPath("$.libelleStatut").value(DEFAULT_LIBELLE_STATUT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEtat() throws Exception {
        // Get the etat
        restEtatMockMvc.perform(get("/api/etats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtat() throws Exception {
        // Initialize the database
        etatRepository.saveAndFlush(etat);
        int databaseSizeBeforeUpdate = etatRepository.findAll().size();

        // Update the etat
        Etat updatedEtat = etatRepository.findOne(etat.getId());
        // Disconnect from session so that the updates on updatedEtat are not directly saved in db
        em.detach(updatedEtat);
        updatedEtat
            .dateDemarrage(UPDATED_DATE_DEMARRAGE)
            .dateDebutInterco(UPDATED_DATE_DEBUT_INTERCO)
            .libelleStatut(UPDATED_LIBELLE_STATUT);

        restEtatMockMvc.perform(put("/api/etats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtat)))
            .andExpect(status().isOk());

        // Validate the Etat in the database
        List<Etat> etatList = etatRepository.findAll();
        assertThat(etatList).hasSize(databaseSizeBeforeUpdate);
        Etat testEtat = etatList.get(etatList.size() - 1);
        assertThat(testEtat.getDateDemarrage()).isEqualTo(UPDATED_DATE_DEMARRAGE);
        assertThat(testEtat.getDateDebutInterco()).isEqualTo(UPDATED_DATE_DEBUT_INTERCO);
        assertThat(testEtat.getLibelleStatut()).isEqualTo(UPDATED_LIBELLE_STATUT);
    }

    @Test
    @Transactional
    public void updateNonExistingEtat() throws Exception {
        int databaseSizeBeforeUpdate = etatRepository.findAll().size();

        // Create the Etat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEtatMockMvc.perform(put("/api/etats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etat)))
            .andExpect(status().isCreated());

        // Validate the Etat in the database
        List<Etat> etatList = etatRepository.findAll();
        assertThat(etatList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEtat() throws Exception {
        // Initialize the database
        etatRepository.saveAndFlush(etat);
        int databaseSizeBeforeDelete = etatRepository.findAll().size();

        // Get the etat
        restEtatMockMvc.perform(delete("/api/etats/{id}", etat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Etat> etatList = etatRepository.findAll();
        assertThat(etatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Etat.class);
        Etat etat1 = new Etat();
        etat1.setId(1L);
        Etat etat2 = new Etat();
        etat2.setId(etat1.getId());
        assertThat(etat1).isEqualTo(etat2);
        etat2.setId(2L);
        assertThat(etat1).isNotEqualTo(etat2);
        etat1.setId(null);
        assertThat(etat1).isNotEqualTo(etat2);
    }
}
