package fr.adservio.mybizdev.web.rest;

import fr.adservio.mybizdev.MybizdevApp;

import fr.adservio.mybizdev.domain.Placement;
import fr.adservio.mybizdev.repository.PlacementRepository;
import fr.adservio.mybizdev.service.PlacementService;
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
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static fr.adservio.mybizdev.web.rest.TestUtil.sameInstant;
import static fr.adservio.mybizdev.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.adservio.mybizdev.domain.enumeration.Statut;
/**
 * Test class for the PlacementResource REST controller.
 *
 * @see PlacementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybizdevApp.class)
public class PlacementResourceIntTest {

    private static final String DEFAULT_NOM_CLIENT_FINAL = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CLIENT_FINAL = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_SSII = "AAAAAAAAAA";
    private static final String UPDATED_NOM_SSII = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_SSII = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_SSII = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_CLIENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_DEMARRAGE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_DEMARRAGE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Statut DEFAULT_ETAT = Statut.GO;
    private static final Statut UPDATED_ETAT = Statut.NOGO;

    @Autowired
    private PlacementRepository placementRepository;

    @Autowired
    private PlacementService placementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlacementMockMvc;

    private Placement placement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlacementResource placementResource = new PlacementResource(placementService);
        this.restPlacementMockMvc = MockMvcBuilders.standaloneSetup(placementResource)
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
    public static Placement createEntity(EntityManager em) {
        Placement placement = new Placement()
            .nomClientFinal(DEFAULT_NOM_CLIENT_FINAL)
            .nomSSII(DEFAULT_NOM_SSII)
            .contactSSII(DEFAULT_CONTACT_SSII)
            .contactClient(DEFAULT_CONTACT_CLIENT)
            .dateDemarrage(DEFAULT_DATE_DEMARRAGE)
            .etat(DEFAULT_ETAT);
        return placement;
    }

    @Before
    public void initTest() {
        placement = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlacement() throws Exception {
        int databaseSizeBeforeCreate = placementRepository.findAll().size();

        // Create the Placement
        restPlacementMockMvc.perform(post("/api/placements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(placement)))
            .andExpect(status().isCreated());

        // Validate the Placement in the database
        List<Placement> placementList = placementRepository.findAll();
        assertThat(placementList).hasSize(databaseSizeBeforeCreate + 1);
        Placement testPlacement = placementList.get(placementList.size() - 1);
        assertThat(testPlacement.getNomClientFinal()).isEqualTo(DEFAULT_NOM_CLIENT_FINAL);
        assertThat(testPlacement.getNomSSII()).isEqualTo(DEFAULT_NOM_SSII);
        assertThat(testPlacement.getContactSSII()).isEqualTo(DEFAULT_CONTACT_SSII);
        assertThat(testPlacement.getContactClient()).isEqualTo(DEFAULT_CONTACT_CLIENT);
        assertThat(testPlacement.getDateDemarrage()).isEqualTo(DEFAULT_DATE_DEMARRAGE);
        assertThat(testPlacement.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    public void createPlacementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = placementRepository.findAll().size();

        // Create the Placement with an existing ID
        placement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlacementMockMvc.perform(post("/api/placements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(placement)))
            .andExpect(status().isBadRequest());

        // Validate the Placement in the database
        List<Placement> placementList = placementRepository.findAll();
        assertThat(placementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomClientFinalIsRequired() throws Exception {
        int databaseSizeBeforeTest = placementRepository.findAll().size();
        // set the field null
        placement.setNomClientFinal(null);

        // Create the Placement, which fails.

        restPlacementMockMvc.perform(post("/api/placements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(placement)))
            .andExpect(status().isBadRequest());

        List<Placement> placementList = placementRepository.findAll();
        assertThat(placementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlacements() throws Exception {
        // Initialize the database
        placementRepository.saveAndFlush(placement);

        // Get all the placementList
        restPlacementMockMvc.perform(get("/api/placements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(placement.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomClientFinal").value(hasItem(DEFAULT_NOM_CLIENT_FINAL.toString())))
            .andExpect(jsonPath("$.[*].nomSSII").value(hasItem(DEFAULT_NOM_SSII.toString())))
            .andExpect(jsonPath("$.[*].contactSSII").value(hasItem(DEFAULT_CONTACT_SSII.toString())))
            .andExpect(jsonPath("$.[*].contactClient").value(hasItem(DEFAULT_CONTACT_CLIENT.toString())))
            .andExpect(jsonPath("$.[*].dateDemarrage").value(hasItem(sameInstant(DEFAULT_DATE_DEMARRAGE))))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }

    @Test
    @Transactional
    public void getPlacement() throws Exception {
        // Initialize the database
        placementRepository.saveAndFlush(placement);

        // Get the placement
        restPlacementMockMvc.perform(get("/api/placements/{id}", placement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(placement.getId().intValue()))
            .andExpect(jsonPath("$.nomClientFinal").value(DEFAULT_NOM_CLIENT_FINAL.toString()))
            .andExpect(jsonPath("$.nomSSII").value(DEFAULT_NOM_SSII.toString()))
            .andExpect(jsonPath("$.contactSSII").value(DEFAULT_CONTACT_SSII.toString()))
            .andExpect(jsonPath("$.contactClient").value(DEFAULT_CONTACT_CLIENT.toString()))
            .andExpect(jsonPath("$.dateDemarrage").value(sameInstant(DEFAULT_DATE_DEMARRAGE)))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlacement() throws Exception {
        // Get the placement
        restPlacementMockMvc.perform(get("/api/placements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlacement() throws Exception {
        // Initialize the database
        placementService.save(placement);

        int databaseSizeBeforeUpdate = placementRepository.findAll().size();

        // Update the placement
        Placement updatedPlacement = placementRepository.findOne(placement.getId());
        // Disconnect from session so that the updates on updatedPlacement are not directly saved in db
        em.detach(updatedPlacement);
        updatedPlacement
            .nomClientFinal(UPDATED_NOM_CLIENT_FINAL)
            .nomSSII(UPDATED_NOM_SSII)
            .contactSSII(UPDATED_CONTACT_SSII)
            .contactClient(UPDATED_CONTACT_CLIENT)
            .dateDemarrage(UPDATED_DATE_DEMARRAGE)
            .etat(UPDATED_ETAT);

        restPlacementMockMvc.perform(put("/api/placements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlacement)))
            .andExpect(status().isOk());

        // Validate the Placement in the database
        List<Placement> placementList = placementRepository.findAll();
        assertThat(placementList).hasSize(databaseSizeBeforeUpdate);
        Placement testPlacement = placementList.get(placementList.size() - 1);
        assertThat(testPlacement.getNomClientFinal()).isEqualTo(UPDATED_NOM_CLIENT_FINAL);
        assertThat(testPlacement.getNomSSII()).isEqualTo(UPDATED_NOM_SSII);
        assertThat(testPlacement.getContactSSII()).isEqualTo(UPDATED_CONTACT_SSII);
        assertThat(testPlacement.getContactClient()).isEqualTo(UPDATED_CONTACT_CLIENT);
        assertThat(testPlacement.getDateDemarrage()).isEqualTo(UPDATED_DATE_DEMARRAGE);
        assertThat(testPlacement.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void updateNonExistingPlacement() throws Exception {
        int databaseSizeBeforeUpdate = placementRepository.findAll().size();

        // Create the Placement

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlacementMockMvc.perform(put("/api/placements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(placement)))
            .andExpect(status().isCreated());

        // Validate the Placement in the database
        List<Placement> placementList = placementRepository.findAll();
        assertThat(placementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlacement() throws Exception {
        // Initialize the database
        placementService.save(placement);

        int databaseSizeBeforeDelete = placementRepository.findAll().size();

        // Get the placement
        restPlacementMockMvc.perform(delete("/api/placements/{id}", placement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Placement> placementList = placementRepository.findAll();
        assertThat(placementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Placement.class);
        Placement placement1 = new Placement();
        placement1.setId(1L);
        Placement placement2 = new Placement();
        placement2.setId(placement1.getId());
        assertThat(placement1).isEqualTo(placement2);
        placement2.setId(2L);
        assertThat(placement1).isNotEqualTo(placement2);
        placement1.setId(null);
        assertThat(placement1).isNotEqualTo(placement2);
    }
}
