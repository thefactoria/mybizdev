package fr.adservio.mybizdev.web.rest;

import fr.adservio.mybizdev.MybizdevApp;

import fr.adservio.mybizdev.domain.Consultant;
import fr.adservio.mybizdev.repository.ConsultantRepository;
import fr.adservio.mybizdev.service.ConsultantService;
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

/**
 * Test class for the ConsultantResource REST controller.
 *
 * @see ConsultantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybizdevApp.class)
public class ConsultantResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_CJM = 1;
    private static final Integer UPDATED_CJM = 2;

    private static final Integer DEFAULT_TJM = 1;
    private static final Integer UPDATED_TJM = 2;

    private static final Instant DEFAULT_DATE_INTEGRATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_INTEGRATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ConsultantRepository consultantRepository;

    @Autowired
    private ConsultantService consultantService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConsultantMockMvc;

    private Consultant consultant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsultantResource consultantResource = new ConsultantResource(consultantService);
        this.restConsultantMockMvc = MockMvcBuilders.standaloneSetup(consultantResource)
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
    public static Consultant createEntity(EntityManager em) {
        Consultant consultant = new Consultant()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .cjm(DEFAULT_CJM)
            .tjm(DEFAULT_TJM)
            .dateIntegration(DEFAULT_DATE_INTEGRATION);
        return consultant;
    }

    @Before
    public void initTest() {
        consultant = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsultant() throws Exception {
        int databaseSizeBeforeCreate = consultantRepository.findAll().size();

        // Create the Consultant
        restConsultantMockMvc.perform(post("/api/consultants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultant)))
            .andExpect(status().isCreated());

        // Validate the Consultant in the database
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeCreate + 1);
        Consultant testConsultant = consultantList.get(consultantList.size() - 1);
        assertThat(testConsultant.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testConsultant.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testConsultant.getCjm()).isEqualTo(DEFAULT_CJM);
        assertThat(testConsultant.getTjm()).isEqualTo(DEFAULT_TJM);
        assertThat(testConsultant.getDateIntegration()).isEqualTo(DEFAULT_DATE_INTEGRATION);
    }

    @Test
    @Transactional
    public void createConsultantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultantRepository.findAll().size();

        // Create the Consultant with an existing ID
        consultant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultantMockMvc.perform(post("/api/consultants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultant)))
            .andExpect(status().isBadRequest());

        // Validate the Consultant in the database
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultantRepository.findAll().size();
        // set the field null
        consultant.setNom(null);

        // Create the Consultant, which fails.

        restConsultantMockMvc.perform(post("/api/consultants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultant)))
            .andExpect(status().isBadRequest());

        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultantRepository.findAll().size();
        // set the field null
        consultant.setPrenom(null);

        // Create the Consultant, which fails.

        restConsultantMockMvc.perform(post("/api/consultants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultant)))
            .andExpect(status().isBadRequest());

        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCjmIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultantRepository.findAll().size();
        // set the field null
        consultant.setCjm(null);

        // Create the Consultant, which fails.

        restConsultantMockMvc.perform(post("/api/consultants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultant)))
            .andExpect(status().isBadRequest());

        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTjmIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultantRepository.findAll().size();
        // set the field null
        consultant.setTjm(null);

        // Create the Consultant, which fails.

        restConsultantMockMvc.perform(post("/api/consultants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultant)))
            .andExpect(status().isBadRequest());

        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIntegrationIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultantRepository.findAll().size();
        // set the field null
        consultant.setDateIntegration(null);

        // Create the Consultant, which fails.

        restConsultantMockMvc.perform(post("/api/consultants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultant)))
            .andExpect(status().isBadRequest());

        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConsultants() throws Exception {
        // Initialize the database
        consultantRepository.saveAndFlush(consultant);

        // Get all the consultantList
        restConsultantMockMvc.perform(get("/api/consultants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultant.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].cjm").value(hasItem(DEFAULT_CJM)))
            .andExpect(jsonPath("$.[*].tjm").value(hasItem(DEFAULT_TJM)))
            .andExpect(jsonPath("$.[*].dateIntegration").value(hasItem(DEFAULT_DATE_INTEGRATION.toString())));
    }

    @Test
    @Transactional
    public void getConsultant() throws Exception {
        // Initialize the database
        consultantRepository.saveAndFlush(consultant);

        // Get the consultant
        restConsultantMockMvc.perform(get("/api/consultants/{id}", consultant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consultant.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.cjm").value(DEFAULT_CJM))
            .andExpect(jsonPath("$.tjm").value(DEFAULT_TJM))
            .andExpect(jsonPath("$.dateIntegration").value(DEFAULT_DATE_INTEGRATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConsultant() throws Exception {
        // Get the consultant
        restConsultantMockMvc.perform(get("/api/consultants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsultant() throws Exception {
        // Initialize the database
        consultantService.save(consultant);

        int databaseSizeBeforeUpdate = consultantRepository.findAll().size();

        // Update the consultant
        Consultant updatedConsultant = consultantRepository.findOne(consultant.getId());
        // Disconnect from session so that the updates on updatedConsultant are not directly saved in db
        em.detach(updatedConsultant);
        updatedConsultant
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .cjm(UPDATED_CJM)
            .tjm(UPDATED_TJM)
            .dateIntegration(UPDATED_DATE_INTEGRATION);

        restConsultantMockMvc.perform(put("/api/consultants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConsultant)))
            .andExpect(status().isOk());

        // Validate the Consultant in the database
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeUpdate);
        Consultant testConsultant = consultantList.get(consultantList.size() - 1);
        assertThat(testConsultant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testConsultant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testConsultant.getCjm()).isEqualTo(UPDATED_CJM);
        assertThat(testConsultant.getTjm()).isEqualTo(UPDATED_TJM);
        assertThat(testConsultant.getDateIntegration()).isEqualTo(UPDATED_DATE_INTEGRATION);
    }

    @Test
    @Transactional
    public void updateNonExistingConsultant() throws Exception {
        int databaseSizeBeforeUpdate = consultantRepository.findAll().size();

        // Create the Consultant

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConsultantMockMvc.perform(put("/api/consultants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultant)))
            .andExpect(status().isCreated());

        // Validate the Consultant in the database
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConsultant() throws Exception {
        // Initialize the database
        consultantService.save(consultant);

        int databaseSizeBeforeDelete = consultantRepository.findAll().size();

        // Get the consultant
        restConsultantMockMvc.perform(delete("/api/consultants/{id}", consultant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Consultant.class);
        Consultant consultant1 = new Consultant();
        consultant1.setId(1L);
        Consultant consultant2 = new Consultant();
        consultant2.setId(consultant1.getId());
        assertThat(consultant1).isEqualTo(consultant2);
        consultant2.setId(2L);
        assertThat(consultant1).isNotEqualTo(consultant2);
        consultant1.setId(null);
        assertThat(consultant1).isNotEqualTo(consultant2);
    }
}
