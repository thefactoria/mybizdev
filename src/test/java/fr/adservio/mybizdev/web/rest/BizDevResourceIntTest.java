package fr.adservio.mybizdev.web.rest;

import fr.adservio.mybizdev.MybizdevApp;

import fr.adservio.mybizdev.domain.BizDev;
import fr.adservio.mybizdev.repository.BizDevRepository;
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
import java.util.List;

import static fr.adservio.mybizdev.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BizDevResource REST controller.
 *
 * @see BizDevResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybizdevApp.class)
public class BizDevResourceIntTest {

    private static final String DEFAULT_SURNOM = "AAAAAAAAAA";
    private static final String UPDATED_SURNOM = "BBBBBBBBBB";

    @Autowired
    private BizDevRepository bizDevRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBizDevMockMvc;

    private BizDev bizDev;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BizDevResource bizDevResource = new BizDevResource(bizDevRepository);
        this.restBizDevMockMvc = MockMvcBuilders.standaloneSetup(bizDevResource)
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
    public static BizDev createEntity(EntityManager em) {
        BizDev bizDev = new BizDev()
            .surnom(DEFAULT_SURNOM);
        return bizDev;
    }

    @Before
    public void initTest() {
        bizDev = createEntity(em);
    }

    @Test
    @Transactional
    public void createBizDev() throws Exception {
        int databaseSizeBeforeCreate = bizDevRepository.findAll().size();

        // Create the BizDev
        restBizDevMockMvc.perform(post("/api/biz-devs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bizDev)))
            .andExpect(status().isCreated());

        // Validate the BizDev in the database
        List<BizDev> bizDevList = bizDevRepository.findAll();
        assertThat(bizDevList).hasSize(databaseSizeBeforeCreate + 1);
        BizDev testBizDev = bizDevList.get(bizDevList.size() - 1);
        assertThat(testBizDev.getSurnom()).isEqualTo(DEFAULT_SURNOM);
    }

    @Test
    @Transactional
    public void createBizDevWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bizDevRepository.findAll().size();

        // Create the BizDev with an existing ID
        bizDev.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBizDevMockMvc.perform(post("/api/biz-devs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bizDev)))
            .andExpect(status().isBadRequest());

        // Validate the BizDev in the database
        List<BizDev> bizDevList = bizDevRepository.findAll();
        assertThat(bizDevList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSurnomIsRequired() throws Exception {
        int databaseSizeBeforeTest = bizDevRepository.findAll().size();
        // set the field null
        bizDev.setSurnom(null);

        // Create the BizDev, which fails.

        restBizDevMockMvc.perform(post("/api/biz-devs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bizDev)))
            .andExpect(status().isBadRequest());

        List<BizDev> bizDevList = bizDevRepository.findAll();
        assertThat(bizDevList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBizDevs() throws Exception {
        // Initialize the database
        bizDevRepository.saveAndFlush(bizDev);

        // Get all the bizDevList
        restBizDevMockMvc.perform(get("/api/biz-devs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bizDev.getId().intValue())))
            .andExpect(jsonPath("$.[*].surnom").value(hasItem(DEFAULT_SURNOM.toString())));
    }

    @Test
    @Transactional
    public void getBizDev() throws Exception {
        // Initialize the database
        bizDevRepository.saveAndFlush(bizDev);

        // Get the bizDev
        restBizDevMockMvc.perform(get("/api/biz-devs/{id}", bizDev.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bizDev.getId().intValue()))
            .andExpect(jsonPath("$.surnom").value(DEFAULT_SURNOM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBizDev() throws Exception {
        // Get the bizDev
        restBizDevMockMvc.perform(get("/api/biz-devs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBizDev() throws Exception {
        // Initialize the database
        bizDevRepository.saveAndFlush(bizDev);
        int databaseSizeBeforeUpdate = bizDevRepository.findAll().size();

        // Update the bizDev
        BizDev updatedBizDev = bizDevRepository.findOne(bizDev.getId());
        // Disconnect from session so that the updates on updatedBizDev are not directly saved in db
        em.detach(updatedBizDev);
        updatedBizDev
            .surnom(UPDATED_SURNOM);

        restBizDevMockMvc.perform(put("/api/biz-devs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBizDev)))
            .andExpect(status().isOk());

        // Validate the BizDev in the database
        List<BizDev> bizDevList = bizDevRepository.findAll();
        assertThat(bizDevList).hasSize(databaseSizeBeforeUpdate);
        BizDev testBizDev = bizDevList.get(bizDevList.size() - 1);
        assertThat(testBizDev.getSurnom()).isEqualTo(UPDATED_SURNOM);
    }

    @Test
    @Transactional
    public void updateNonExistingBizDev() throws Exception {
        int databaseSizeBeforeUpdate = bizDevRepository.findAll().size();

        // Create the BizDev

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBizDevMockMvc.perform(put("/api/biz-devs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bizDev)))
            .andExpect(status().isCreated());

        // Validate the BizDev in the database
        List<BizDev> bizDevList = bizDevRepository.findAll();
        assertThat(bizDevList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBizDev() throws Exception {
        // Initialize the database
        bizDevRepository.saveAndFlush(bizDev);
        int databaseSizeBeforeDelete = bizDevRepository.findAll().size();

        // Get the bizDev
        restBizDevMockMvc.perform(delete("/api/biz-devs/{id}", bizDev.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BizDev> bizDevList = bizDevRepository.findAll();
        assertThat(bizDevList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BizDev.class);
        BizDev bizDev1 = new BizDev();
        bizDev1.setId(1L);
        BizDev bizDev2 = new BizDev();
        bizDev2.setId(bizDev1.getId());
        assertThat(bizDev1).isEqualTo(bizDev2);
        bizDev2.setId(2L);
        assertThat(bizDev1).isNotEqualTo(bizDev2);
        bizDev1.setId(null);
        assertThat(bizDev1).isNotEqualTo(bizDev2);
    }
}
