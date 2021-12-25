package com.mycompany.ssi.web.rest;

import static com.mycompany.ssi.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.ssi.IntegrationTest;
import com.mycompany.ssi.domain.TABFEE;
import com.mycompany.ssi.repository.TABFEERepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TABFEEResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TABFEEResourceIT {

    private static final String DEFAULT_FESTS = "AAAAAAAAAA";
    private static final String UPDATED_FESTS = "BBBBBBBBBB";

    private static final String DEFAULT_FEEMT = "AAAAAAAAAA";
    private static final String UPDATED_FEEMT = "BBBBBBBBBB";

    private static final Integer DEFAULT_FEMIN = 1;
    private static final Integer UPDATED_FEMIN = 2;

    private static final Integer DEFAULT_FEMAX = 1;
    private static final Integer UPDATED_FEMAX = 2;

    private static final Integer DEFAULT_FEFEE = 1;
    private static final Integer UPDATED_FEFEE = 2;

    private static final BigDecimal DEFAULT_FEDISCP = new BigDecimal(1);
    private static final BigDecimal UPDATED_FEDISCP = new BigDecimal(2);

    private static final Integer DEFAULT_FEDISC = 1;
    private static final Integer UPDATED_FEDISC = 2;

    private static final BigDecimal DEFAULT_FETAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_FETAX = new BigDecimal(2);

    private static final LocalDate DEFAULT_FELMD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FELMD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FEUID = "AAAAAAAAAA";
    private static final String UPDATED_FEUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tabfees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TABFEERepository tABFEERepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTABFEEMockMvc;

    private TABFEE tABFEE;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TABFEE createEntity(EntityManager em) {
        TABFEE tABFEE = new TABFEE()
            .fests(DEFAULT_FESTS)
            .feemt(DEFAULT_FEEMT)
            .femin(DEFAULT_FEMIN)
            .femax(DEFAULT_FEMAX)
            .fefee(DEFAULT_FEFEE)
            .fediscp(DEFAULT_FEDISCP)
            .fedisc(DEFAULT_FEDISC)
            .fetax(DEFAULT_FETAX)
            .felmd(DEFAULT_FELMD)
            .feuid(DEFAULT_FEUID);
        return tABFEE;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TABFEE createUpdatedEntity(EntityManager em) {
        TABFEE tABFEE = new TABFEE()
            .fests(UPDATED_FESTS)
            .feemt(UPDATED_FEEMT)
            .femin(UPDATED_FEMIN)
            .femax(UPDATED_FEMAX)
            .fefee(UPDATED_FEFEE)
            .fediscp(UPDATED_FEDISCP)
            .fedisc(UPDATED_FEDISC)
            .fetax(UPDATED_FETAX)
            .felmd(UPDATED_FELMD)
            .feuid(UPDATED_FEUID);
        return tABFEE;
    }

    @BeforeEach
    public void initTest() {
        tABFEE = createEntity(em);
    }

    @Test
    @Transactional
    void createTABFEE() throws Exception {
        int databaseSizeBeforeCreate = tABFEERepository.findAll().size();
        // Create the TABFEE
        restTABFEEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tABFEE)))
            .andExpect(status().isCreated());

        // Validate the TABFEE in the database
        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeCreate + 1);
        TABFEE testTABFEE = tABFEEList.get(tABFEEList.size() - 1);
        assertThat(testTABFEE.getFests()).isEqualTo(DEFAULT_FESTS);
        assertThat(testTABFEE.getFeemt()).isEqualTo(DEFAULT_FEEMT);
        assertThat(testTABFEE.getFemin()).isEqualTo(DEFAULT_FEMIN);
        assertThat(testTABFEE.getFemax()).isEqualTo(DEFAULT_FEMAX);
        assertThat(testTABFEE.getFefee()).isEqualTo(DEFAULT_FEFEE);
        assertThat(testTABFEE.getFediscp()).isEqualByComparingTo(DEFAULT_FEDISCP);
        assertThat(testTABFEE.getFedisc()).isEqualTo(DEFAULT_FEDISC);
        assertThat(testTABFEE.getFetax()).isEqualByComparingTo(DEFAULT_FETAX);
        assertThat(testTABFEE.getFelmd()).isEqualTo(DEFAULT_FELMD);
        assertThat(testTABFEE.getFeuid()).isEqualTo(DEFAULT_FEUID);
    }

    @Test
    @Transactional
    void createTABFEEWithExistingId() throws Exception {
        // Create the TABFEE with an existing ID
        tABFEE.setId(1L);

        int databaseSizeBeforeCreate = tABFEERepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTABFEEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tABFEE)))
            .andExpect(status().isBadRequest());

        // Validate the TABFEE in the database
        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFestsIsRequired() throws Exception {
        int databaseSizeBeforeTest = tABFEERepository.findAll().size();
        // set the field null
        tABFEE.setFests(null);

        // Create the TABFEE, which fails.

        restTABFEEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tABFEE)))
            .andExpect(status().isBadRequest());

        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFeemtIsRequired() throws Exception {
        int databaseSizeBeforeTest = tABFEERepository.findAll().size();
        // set the field null
        tABFEE.setFeemt(null);

        // Create the TABFEE, which fails.

        restTABFEEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tABFEE)))
            .andExpect(status().isBadRequest());

        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFeminIsRequired() throws Exception {
        int databaseSizeBeforeTest = tABFEERepository.findAll().size();
        // set the field null
        tABFEE.setFemin(null);

        // Create the TABFEE, which fails.

        restTABFEEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tABFEE)))
            .andExpect(status().isBadRequest());

        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFemaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = tABFEERepository.findAll().size();
        // set the field null
        tABFEE.setFemax(null);

        // Create the TABFEE, which fails.

        restTABFEEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tABFEE)))
            .andExpect(status().isBadRequest());

        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFefeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tABFEERepository.findAll().size();
        // set the field null
        tABFEE.setFefee(null);

        // Create the TABFEE, which fails.

        restTABFEEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tABFEE)))
            .andExpect(status().isBadRequest());

        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFediscpIsRequired() throws Exception {
        int databaseSizeBeforeTest = tABFEERepository.findAll().size();
        // set the field null
        tABFEE.setFediscp(null);

        // Create the TABFEE, which fails.

        restTABFEEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tABFEE)))
            .andExpect(status().isBadRequest());

        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFediscIsRequired() throws Exception {
        int databaseSizeBeforeTest = tABFEERepository.findAll().size();
        // set the field null
        tABFEE.setFedisc(null);

        // Create the TABFEE, which fails.

        restTABFEEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tABFEE)))
            .andExpect(status().isBadRequest());

        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFetaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = tABFEERepository.findAll().size();
        // set the field null
        tABFEE.setFetax(null);

        // Create the TABFEE, which fails.

        restTABFEEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tABFEE)))
            .andExpect(status().isBadRequest());

        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFelmdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tABFEERepository.findAll().size();
        // set the field null
        tABFEE.setFelmd(null);

        // Create the TABFEE, which fails.

        restTABFEEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tABFEE)))
            .andExpect(status().isBadRequest());

        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFeuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = tABFEERepository.findAll().size();
        // set the field null
        tABFEE.setFeuid(null);

        // Create the TABFEE, which fails.

        restTABFEEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tABFEE)))
            .andExpect(status().isBadRequest());

        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTABFEES() throws Exception {
        // Initialize the database
        tABFEERepository.saveAndFlush(tABFEE);

        // Get all the tABFEEList
        restTABFEEMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tABFEE.getId().intValue())))
            .andExpect(jsonPath("$.[*].fests").value(hasItem(DEFAULT_FESTS)))
            .andExpect(jsonPath("$.[*].feemt").value(hasItem(DEFAULT_FEEMT)))
            .andExpect(jsonPath("$.[*].femin").value(hasItem(DEFAULT_FEMIN)))
            .andExpect(jsonPath("$.[*].femax").value(hasItem(DEFAULT_FEMAX)))
            .andExpect(jsonPath("$.[*].fefee").value(hasItem(DEFAULT_FEFEE)))
            .andExpect(jsonPath("$.[*].fediscp").value(hasItem(sameNumber(DEFAULT_FEDISCP))))
            .andExpect(jsonPath("$.[*].fedisc").value(hasItem(DEFAULT_FEDISC)))
            .andExpect(jsonPath("$.[*].fetax").value(hasItem(sameNumber(DEFAULT_FETAX))))
            .andExpect(jsonPath("$.[*].felmd").value(hasItem(DEFAULT_FELMD.toString())))
            .andExpect(jsonPath("$.[*].feuid").value(hasItem(DEFAULT_FEUID)));
    }

    @Test
    @Transactional
    void getTABFEE() throws Exception {
        // Initialize the database
        tABFEERepository.saveAndFlush(tABFEE);

        // Get the tABFEE
        restTABFEEMockMvc
            .perform(get(ENTITY_API_URL_ID, tABFEE.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tABFEE.getId().intValue()))
            .andExpect(jsonPath("$.fests").value(DEFAULT_FESTS))
            .andExpect(jsonPath("$.feemt").value(DEFAULT_FEEMT))
            .andExpect(jsonPath("$.femin").value(DEFAULT_FEMIN))
            .andExpect(jsonPath("$.femax").value(DEFAULT_FEMAX))
            .andExpect(jsonPath("$.fefee").value(DEFAULT_FEFEE))
            .andExpect(jsonPath("$.fediscp").value(sameNumber(DEFAULT_FEDISCP)))
            .andExpect(jsonPath("$.fedisc").value(DEFAULT_FEDISC))
            .andExpect(jsonPath("$.fetax").value(sameNumber(DEFAULT_FETAX)))
            .andExpect(jsonPath("$.felmd").value(DEFAULT_FELMD.toString()))
            .andExpect(jsonPath("$.feuid").value(DEFAULT_FEUID));
    }

    @Test
    @Transactional
    void getNonExistingTABFEE() throws Exception {
        // Get the tABFEE
        restTABFEEMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTABFEE() throws Exception {
        // Initialize the database
        tABFEERepository.saveAndFlush(tABFEE);

        int databaseSizeBeforeUpdate = tABFEERepository.findAll().size();

        // Update the tABFEE
        TABFEE updatedTABFEE = tABFEERepository.findById(tABFEE.getId()).get();
        // Disconnect from session so that the updates on updatedTABFEE are not directly saved in db
        em.detach(updatedTABFEE);
        updatedTABFEE
            .fests(UPDATED_FESTS)
            .feemt(UPDATED_FEEMT)
            .femin(UPDATED_FEMIN)
            .femax(UPDATED_FEMAX)
            .fefee(UPDATED_FEFEE)
            .fediscp(UPDATED_FEDISCP)
            .fedisc(UPDATED_FEDISC)
            .fetax(UPDATED_FETAX)
            .felmd(UPDATED_FELMD)
            .feuid(UPDATED_FEUID);

        restTABFEEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTABFEE.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTABFEE))
            )
            .andExpect(status().isOk());

        // Validate the TABFEE in the database
        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeUpdate);
        TABFEE testTABFEE = tABFEEList.get(tABFEEList.size() - 1);
        assertThat(testTABFEE.getFests()).isEqualTo(UPDATED_FESTS);
        assertThat(testTABFEE.getFeemt()).isEqualTo(UPDATED_FEEMT);
        assertThat(testTABFEE.getFemin()).isEqualTo(UPDATED_FEMIN);
        assertThat(testTABFEE.getFemax()).isEqualTo(UPDATED_FEMAX);
        assertThat(testTABFEE.getFefee()).isEqualTo(UPDATED_FEFEE);
        assertThat(testTABFEE.getFediscp()).isEqualTo(UPDATED_FEDISCP);
        assertThat(testTABFEE.getFedisc()).isEqualTo(UPDATED_FEDISC);
        assertThat(testTABFEE.getFetax()).isEqualTo(UPDATED_FETAX);
        assertThat(testTABFEE.getFelmd()).isEqualTo(UPDATED_FELMD);
        assertThat(testTABFEE.getFeuid()).isEqualTo(UPDATED_FEUID);
    }

    @Test
    @Transactional
    void putNonExistingTABFEE() throws Exception {
        int databaseSizeBeforeUpdate = tABFEERepository.findAll().size();
        tABFEE.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTABFEEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tABFEE.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tABFEE))
            )
            .andExpect(status().isBadRequest());

        // Validate the TABFEE in the database
        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTABFEE() throws Exception {
        int databaseSizeBeforeUpdate = tABFEERepository.findAll().size();
        tABFEE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTABFEEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tABFEE))
            )
            .andExpect(status().isBadRequest());

        // Validate the TABFEE in the database
        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTABFEE() throws Exception {
        int databaseSizeBeforeUpdate = tABFEERepository.findAll().size();
        tABFEE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTABFEEMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tABFEE)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TABFEE in the database
        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTABFEEWithPatch() throws Exception {
        // Initialize the database
        tABFEERepository.saveAndFlush(tABFEE);

        int databaseSizeBeforeUpdate = tABFEERepository.findAll().size();

        // Update the tABFEE using partial update
        TABFEE partialUpdatedTABFEE = new TABFEE();
        partialUpdatedTABFEE.setId(tABFEE.getId());

        partialUpdatedTABFEE.fests(UPDATED_FESTS).femin(UPDATED_FEMIN).fefee(UPDATED_FEFEE).fetax(UPDATED_FETAX).feuid(UPDATED_FEUID);

        restTABFEEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTABFEE.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTABFEE))
            )
            .andExpect(status().isOk());

        // Validate the TABFEE in the database
        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeUpdate);
        TABFEE testTABFEE = tABFEEList.get(tABFEEList.size() - 1);
        assertThat(testTABFEE.getFests()).isEqualTo(UPDATED_FESTS);
        assertThat(testTABFEE.getFeemt()).isEqualTo(DEFAULT_FEEMT);
        assertThat(testTABFEE.getFemin()).isEqualTo(UPDATED_FEMIN);
        assertThat(testTABFEE.getFemax()).isEqualTo(DEFAULT_FEMAX);
        assertThat(testTABFEE.getFefee()).isEqualTo(UPDATED_FEFEE);
        assertThat(testTABFEE.getFediscp()).isEqualByComparingTo(DEFAULT_FEDISCP);
        assertThat(testTABFEE.getFedisc()).isEqualTo(DEFAULT_FEDISC);
        assertThat(testTABFEE.getFetax()).isEqualByComparingTo(UPDATED_FETAX);
        assertThat(testTABFEE.getFelmd()).isEqualTo(DEFAULT_FELMD);
        assertThat(testTABFEE.getFeuid()).isEqualTo(UPDATED_FEUID);
    }

    @Test
    @Transactional
    void fullUpdateTABFEEWithPatch() throws Exception {
        // Initialize the database
        tABFEERepository.saveAndFlush(tABFEE);

        int databaseSizeBeforeUpdate = tABFEERepository.findAll().size();

        // Update the tABFEE using partial update
        TABFEE partialUpdatedTABFEE = new TABFEE();
        partialUpdatedTABFEE.setId(tABFEE.getId());

        partialUpdatedTABFEE
            .fests(UPDATED_FESTS)
            .feemt(UPDATED_FEEMT)
            .femin(UPDATED_FEMIN)
            .femax(UPDATED_FEMAX)
            .fefee(UPDATED_FEFEE)
            .fediscp(UPDATED_FEDISCP)
            .fedisc(UPDATED_FEDISC)
            .fetax(UPDATED_FETAX)
            .felmd(UPDATED_FELMD)
            .feuid(UPDATED_FEUID);

        restTABFEEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTABFEE.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTABFEE))
            )
            .andExpect(status().isOk());

        // Validate the TABFEE in the database
        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeUpdate);
        TABFEE testTABFEE = tABFEEList.get(tABFEEList.size() - 1);
        assertThat(testTABFEE.getFests()).isEqualTo(UPDATED_FESTS);
        assertThat(testTABFEE.getFeemt()).isEqualTo(UPDATED_FEEMT);
        assertThat(testTABFEE.getFemin()).isEqualTo(UPDATED_FEMIN);
        assertThat(testTABFEE.getFemax()).isEqualTo(UPDATED_FEMAX);
        assertThat(testTABFEE.getFefee()).isEqualTo(UPDATED_FEFEE);
        assertThat(testTABFEE.getFediscp()).isEqualByComparingTo(UPDATED_FEDISCP);
        assertThat(testTABFEE.getFedisc()).isEqualTo(UPDATED_FEDISC);
        assertThat(testTABFEE.getFetax()).isEqualByComparingTo(UPDATED_FETAX);
        assertThat(testTABFEE.getFelmd()).isEqualTo(UPDATED_FELMD);
        assertThat(testTABFEE.getFeuid()).isEqualTo(UPDATED_FEUID);
    }

    @Test
    @Transactional
    void patchNonExistingTABFEE() throws Exception {
        int databaseSizeBeforeUpdate = tABFEERepository.findAll().size();
        tABFEE.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTABFEEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tABFEE.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tABFEE))
            )
            .andExpect(status().isBadRequest());

        // Validate the TABFEE in the database
        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTABFEE() throws Exception {
        int databaseSizeBeforeUpdate = tABFEERepository.findAll().size();
        tABFEE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTABFEEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tABFEE))
            )
            .andExpect(status().isBadRequest());

        // Validate the TABFEE in the database
        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTABFEE() throws Exception {
        int databaseSizeBeforeUpdate = tABFEERepository.findAll().size();
        tABFEE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTABFEEMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tABFEE)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TABFEE in the database
        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTABFEE() throws Exception {
        // Initialize the database
        tABFEERepository.saveAndFlush(tABFEE);

        int databaseSizeBeforeDelete = tABFEERepository.findAll().size();

        // Delete the tABFEE
        restTABFEEMockMvc
            .perform(delete(ENTITY_API_URL_ID, tABFEE.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TABFEE> tABFEEList = tABFEERepository.findAll();
        assertThat(tABFEEList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
