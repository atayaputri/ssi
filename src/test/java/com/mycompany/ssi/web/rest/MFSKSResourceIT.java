package com.mycompany.ssi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.ssi.IntegrationTest;
import com.mycompany.ssi.domain.MFSKS;
import com.mycompany.ssi.repository.MFSKSRepository;
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
 * Integration tests for the {@link MFSKSResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MFSKSResourceIT {

    private static final String DEFAULT_SKSTS = "A";
    private static final String UPDATED_SKSTS = "B";

    private static final Integer DEFAULT_SKJSH = 1;
    private static final Integer UPDATED_SKJSH = 2;

    private static final Integer DEFAULT_SKBAT = 1;
    private static final Integer UPDATED_SKBAT = 2;

    private static final Integer DEFAULT_SKSEQ = 1;
    private static final Integer UPDATED_SKSEQ = 2;

    private static final String DEFAULT_SKREF = "AAAAAAAAAA";
    private static final String UPDATED_SKREF = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SKDIS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SKDIS = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SKLMD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SKLMD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SKUID = "AAAAAAAAAA";
    private static final String UPDATED_SKUID = "BBBBBBBBBB";

    private static final Integer DEFAULT_SKFIL_1 = 1;
    private static final Integer UPDATED_SKFIL_1 = 2;

    private static final Integer DEFAULT_SKFIL_2 = 1;
    private static final Integer UPDATED_SKFIL_2 = 2;

    private static final String DEFAULT_SKFIL_3 = "AAAAAAAAAA";
    private static final String UPDATED_SKFIL_3 = "BBBBBBBBBB";

    private static final String DEFAULT_SKFIL_4 = "AAAAAAAAAA";
    private static final String UPDATED_SKFIL_4 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mfsks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{skno}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MFSKSRepository mFSKSRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMFSKSMockMvc;

    private MFSKS mFSKS;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MFSKS createEntity(EntityManager em) {
        MFSKS mFSKS = new MFSKS()
            .sksts(DEFAULT_SKSTS)
            .skjsh(DEFAULT_SKJSH)
            .skbat(DEFAULT_SKBAT)
            .skseq(DEFAULT_SKSEQ)
            .skref(DEFAULT_SKREF)
            .skdis(DEFAULT_SKDIS)
            .sklmd(DEFAULT_SKLMD)
            .skuid(DEFAULT_SKUID)
            .skfil1(DEFAULT_SKFIL_1)
            .skfil2(DEFAULT_SKFIL_2)
            .skfil3(DEFAULT_SKFIL_3)
            .skfil4(DEFAULT_SKFIL_4);
        return mFSKS;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MFSKS createUpdatedEntity(EntityManager em) {
        MFSKS mFSKS = new MFSKS()
            .sksts(UPDATED_SKSTS)
            .skjsh(UPDATED_SKJSH)
            .skbat(UPDATED_SKBAT)
            .skseq(UPDATED_SKSEQ)
            .skref(UPDATED_SKREF)
            .skdis(UPDATED_SKDIS)
            .sklmd(UPDATED_SKLMD)
            .skuid(UPDATED_SKUID)
            .skfil1(UPDATED_SKFIL_1)
            .skfil2(UPDATED_SKFIL_2)
            .skfil3(UPDATED_SKFIL_3)
            .skfil4(UPDATED_SKFIL_4);
        return mFSKS;
    }

    @BeforeEach
    public void initTest() {
        mFSKS = createEntity(em);
    }

    @Test
    @Transactional
    void createMFSKS() throws Exception {
        int databaseSizeBeforeCreate = mFSKSRepository.findAll().size();
        // Create the MFSKS
        restMFSKSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSKS)))
            .andExpect(status().isCreated());

        // Validate the MFSKS in the database
        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeCreate + 1);
        MFSKS testMFSKS = mFSKSList.get(mFSKSList.size() - 1);
        assertThat(testMFSKS.getSksts()).isEqualTo(DEFAULT_SKSTS);
        assertThat(testMFSKS.getSkjsh()).isEqualTo(DEFAULT_SKJSH);
        assertThat(testMFSKS.getSkbat()).isEqualTo(DEFAULT_SKBAT);
        assertThat(testMFSKS.getSkseq()).isEqualTo(DEFAULT_SKSEQ);
        assertThat(testMFSKS.getSkref()).isEqualTo(DEFAULT_SKREF);
        assertThat(testMFSKS.getSkdis()).isEqualTo(DEFAULT_SKDIS);
        assertThat(testMFSKS.getSklmd()).isEqualTo(DEFAULT_SKLMD);
        assertThat(testMFSKS.getSkuid()).isEqualTo(DEFAULT_SKUID);
        assertThat(testMFSKS.getSkfil1()).isEqualTo(DEFAULT_SKFIL_1);
        assertThat(testMFSKS.getSkfil2()).isEqualTo(DEFAULT_SKFIL_2);
        assertThat(testMFSKS.getSkfil3()).isEqualTo(DEFAULT_SKFIL_3);
        assertThat(testMFSKS.getSkfil4()).isEqualTo(DEFAULT_SKFIL_4);
    }

    @Test
    @Transactional
    void createMFSKSWithExistingId() throws Exception {
        // Create the MFSKS with an existing ID
        mFSKS.setSkno(1L);

        int databaseSizeBeforeCreate = mFSKSRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMFSKSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSKS)))
            .andExpect(status().isBadRequest());

        // Validate the MFSKS in the database
        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSkstsIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFSKSRepository.findAll().size();
        // set the field null
        mFSKS.setSksts(null);

        // Create the MFSKS, which fails.

        restMFSKSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSKS)))
            .andExpect(status().isBadRequest());

        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSkjshIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFSKSRepository.findAll().size();
        // set the field null
        mFSKS.setSkjsh(null);

        // Create the MFSKS, which fails.

        restMFSKSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSKS)))
            .andExpect(status().isBadRequest());

        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSkbatIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFSKSRepository.findAll().size();
        // set the field null
        mFSKS.setSkbat(null);

        // Create the MFSKS, which fails.

        restMFSKSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSKS)))
            .andExpect(status().isBadRequest());

        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSkseqIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFSKSRepository.findAll().size();
        // set the field null
        mFSKS.setSkseq(null);

        // Create the MFSKS, which fails.

        restMFSKSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSKS)))
            .andExpect(status().isBadRequest());

        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSkrefIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFSKSRepository.findAll().size();
        // set the field null
        mFSKS.setSkref(null);

        // Create the MFSKS, which fails.

        restMFSKSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSKS)))
            .andExpect(status().isBadRequest());

        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSkdisIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFSKSRepository.findAll().size();
        // set the field null
        mFSKS.setSkdis(null);

        // Create the MFSKS, which fails.

        restMFSKSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSKS)))
            .andExpect(status().isBadRequest());

        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSklmdIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFSKSRepository.findAll().size();
        // set the field null
        mFSKS.setSklmd(null);

        // Create the MFSKS, which fails.

        restMFSKSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSKS)))
            .andExpect(status().isBadRequest());

        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSkuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFSKSRepository.findAll().size();
        // set the field null
        mFSKS.setSkuid(null);

        // Create the MFSKS, which fails.

        restMFSKSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSKS)))
            .andExpect(status().isBadRequest());

        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMFSKS() throws Exception {
        // Initialize the database
        mFSKSRepository.saveAndFlush(mFSKS);

        // Get all the mFSKSList
        restMFSKSMockMvc
            .perform(get(ENTITY_API_URL + "?sort=skno,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].skno").value(hasItem(mFSKS.getSkno().intValue())))
            .andExpect(jsonPath("$.[*].sksts").value(hasItem(DEFAULT_SKSTS)))
            .andExpect(jsonPath("$.[*].skjsh").value(hasItem(DEFAULT_SKJSH)))
            .andExpect(jsonPath("$.[*].skbat").value(hasItem(DEFAULT_SKBAT)))
            .andExpect(jsonPath("$.[*].skseq").value(hasItem(DEFAULT_SKSEQ)))
            .andExpect(jsonPath("$.[*].skref").value(hasItem(DEFAULT_SKREF)))
            .andExpect(jsonPath("$.[*].skdis").value(hasItem(DEFAULT_SKDIS.toString())))
            .andExpect(jsonPath("$.[*].sklmd").value(hasItem(DEFAULT_SKLMD.toString())))
            .andExpect(jsonPath("$.[*].skuid").value(hasItem(DEFAULT_SKUID)))
            .andExpect(jsonPath("$.[*].skfil1").value(hasItem(DEFAULT_SKFIL_1)))
            .andExpect(jsonPath("$.[*].skfil2").value(hasItem(DEFAULT_SKFIL_2)))
            .andExpect(jsonPath("$.[*].skfil3").value(hasItem(DEFAULT_SKFIL_3)))
            .andExpect(jsonPath("$.[*].skfil4").value(hasItem(DEFAULT_SKFIL_4)));
    }

    @Test
    @Transactional
    void getMFSKS() throws Exception {
        // Initialize the database
        mFSKSRepository.saveAndFlush(mFSKS);

        // Get the mFSKS
        restMFSKSMockMvc
            .perform(get(ENTITY_API_URL_ID, mFSKS.getSkno()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.skno").value(mFSKS.getSkno().intValue()))
            .andExpect(jsonPath("$.sksts").value(DEFAULT_SKSTS))
            .andExpect(jsonPath("$.skjsh").value(DEFAULT_SKJSH))
            .andExpect(jsonPath("$.skbat").value(DEFAULT_SKBAT))
            .andExpect(jsonPath("$.skseq").value(DEFAULT_SKSEQ))
            .andExpect(jsonPath("$.skref").value(DEFAULT_SKREF))
            .andExpect(jsonPath("$.skdis").value(DEFAULT_SKDIS.toString()))
            .andExpect(jsonPath("$.sklmd").value(DEFAULT_SKLMD.toString()))
            .andExpect(jsonPath("$.skuid").value(DEFAULT_SKUID))
            .andExpect(jsonPath("$.skfil1").value(DEFAULT_SKFIL_1))
            .andExpect(jsonPath("$.skfil2").value(DEFAULT_SKFIL_2))
            .andExpect(jsonPath("$.skfil3").value(DEFAULT_SKFIL_3))
            .andExpect(jsonPath("$.skfil4").value(DEFAULT_SKFIL_4));
    }

    @Test
    @Transactional
    void getNonExistingMFSKS() throws Exception {
        // Get the mFSKS
        restMFSKSMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMFSKS() throws Exception {
        // Initialize the database
        mFSKSRepository.saveAndFlush(mFSKS);

        int databaseSizeBeforeUpdate = mFSKSRepository.findAll().size();

        // Update the mFSKS
        MFSKS updatedMFSKS = mFSKSRepository.findById(mFSKS.getSkno()).get();
        // Disconnect from session so that the updates on updatedMFSKS are not directly saved in db
        em.detach(updatedMFSKS);
        updatedMFSKS
            .sksts(UPDATED_SKSTS)
            .skjsh(UPDATED_SKJSH)
            .skbat(UPDATED_SKBAT)
            .skseq(UPDATED_SKSEQ)
            .skref(UPDATED_SKREF)
            .skdis(UPDATED_SKDIS)
            .sklmd(UPDATED_SKLMD)
            .skuid(UPDATED_SKUID)
            .skfil1(UPDATED_SKFIL_1)
            .skfil2(UPDATED_SKFIL_2)
            .skfil3(UPDATED_SKFIL_3)
            .skfil4(UPDATED_SKFIL_4);

        restMFSKSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMFSKS.getSkno())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMFSKS))
            )
            .andExpect(status().isOk());

        // Validate the MFSKS in the database
        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeUpdate);
        MFSKS testMFSKS = mFSKSList.get(mFSKSList.size() - 1);
        assertThat(testMFSKS.getSksts()).isEqualTo(UPDATED_SKSTS);
        assertThat(testMFSKS.getSkjsh()).isEqualTo(UPDATED_SKJSH);
        assertThat(testMFSKS.getSkbat()).isEqualTo(UPDATED_SKBAT);
        assertThat(testMFSKS.getSkseq()).isEqualTo(UPDATED_SKSEQ);
        assertThat(testMFSKS.getSkref()).isEqualTo(UPDATED_SKREF);
        assertThat(testMFSKS.getSkdis()).isEqualTo(UPDATED_SKDIS);
        assertThat(testMFSKS.getSklmd()).isEqualTo(UPDATED_SKLMD);
        assertThat(testMFSKS.getSkuid()).isEqualTo(UPDATED_SKUID);
        assertThat(testMFSKS.getSkfil1()).isEqualTo(UPDATED_SKFIL_1);
        assertThat(testMFSKS.getSkfil2()).isEqualTo(UPDATED_SKFIL_2);
        assertThat(testMFSKS.getSkfil3()).isEqualTo(UPDATED_SKFIL_3);
        assertThat(testMFSKS.getSkfil4()).isEqualTo(UPDATED_SKFIL_4);
    }

    @Test
    @Transactional
    void putNonExistingMFSKS() throws Exception {
        int databaseSizeBeforeUpdate = mFSKSRepository.findAll().size();
        mFSKS.setSkno(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMFSKSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mFSKS.getSkno())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mFSKS))
            )
            .andExpect(status().isBadRequest());

        // Validate the MFSKS in the database
        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMFSKS() throws Exception {
        int databaseSizeBeforeUpdate = mFSKSRepository.findAll().size();
        mFSKS.setSkno(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMFSKSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mFSKS))
            )
            .andExpect(status().isBadRequest());

        // Validate the MFSKS in the database
        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMFSKS() throws Exception {
        int databaseSizeBeforeUpdate = mFSKSRepository.findAll().size();
        mFSKS.setSkno(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMFSKSMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSKS)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MFSKS in the database
        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMFSKSWithPatch() throws Exception {
        // Initialize the database
        mFSKSRepository.saveAndFlush(mFSKS);

        int databaseSizeBeforeUpdate = mFSKSRepository.findAll().size();

        // Update the mFSKS using partial update
        MFSKS partialUpdatedMFSKS = new MFSKS();
        partialUpdatedMFSKS.setSkno(mFSKS.getSkno());

        partialUpdatedMFSKS.skjsh(UPDATED_SKJSH).skbat(UPDATED_SKBAT).skdis(UPDATED_SKDIS).skfil3(UPDATED_SKFIL_3).skfil4(UPDATED_SKFIL_4);

        restMFSKSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMFSKS.getSkno())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMFSKS))
            )
            .andExpect(status().isOk());

        // Validate the MFSKS in the database
        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeUpdate);
        MFSKS testMFSKS = mFSKSList.get(mFSKSList.size() - 1);
        assertThat(testMFSKS.getSksts()).isEqualTo(DEFAULT_SKSTS);
        assertThat(testMFSKS.getSkjsh()).isEqualTo(UPDATED_SKJSH);
        assertThat(testMFSKS.getSkbat()).isEqualTo(UPDATED_SKBAT);
        assertThat(testMFSKS.getSkseq()).isEqualTo(DEFAULT_SKSEQ);
        assertThat(testMFSKS.getSkref()).isEqualTo(DEFAULT_SKREF);
        assertThat(testMFSKS.getSkdis()).isEqualTo(UPDATED_SKDIS);
        assertThat(testMFSKS.getSklmd()).isEqualTo(DEFAULT_SKLMD);
        assertThat(testMFSKS.getSkuid()).isEqualTo(DEFAULT_SKUID);
        assertThat(testMFSKS.getSkfil1()).isEqualTo(DEFAULT_SKFIL_1);
        assertThat(testMFSKS.getSkfil2()).isEqualTo(DEFAULT_SKFIL_2);
        assertThat(testMFSKS.getSkfil3()).isEqualTo(UPDATED_SKFIL_3);
        assertThat(testMFSKS.getSkfil4()).isEqualTo(UPDATED_SKFIL_4);
    }

    @Test
    @Transactional
    void fullUpdateMFSKSWithPatch() throws Exception {
        // Initialize the database
        mFSKSRepository.saveAndFlush(mFSKS);

        int databaseSizeBeforeUpdate = mFSKSRepository.findAll().size();

        // Update the mFSKS using partial update
        MFSKS partialUpdatedMFSKS = new MFSKS();
        partialUpdatedMFSKS.setSkno(mFSKS.getSkno());

        partialUpdatedMFSKS
            .sksts(UPDATED_SKSTS)
            .skjsh(UPDATED_SKJSH)
            .skbat(UPDATED_SKBAT)
            .skseq(UPDATED_SKSEQ)
            .skref(UPDATED_SKREF)
            .skdis(UPDATED_SKDIS)
            .sklmd(UPDATED_SKLMD)
            .skuid(UPDATED_SKUID)
            .skfil1(UPDATED_SKFIL_1)
            .skfil2(UPDATED_SKFIL_2)
            .skfil3(UPDATED_SKFIL_3)
            .skfil4(UPDATED_SKFIL_4);

        restMFSKSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMFSKS.getSkno())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMFSKS))
            )
            .andExpect(status().isOk());

        // Validate the MFSKS in the database
        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeUpdate);
        MFSKS testMFSKS = mFSKSList.get(mFSKSList.size() - 1);
        assertThat(testMFSKS.getSksts()).isEqualTo(UPDATED_SKSTS);
        assertThat(testMFSKS.getSkjsh()).isEqualTo(UPDATED_SKJSH);
        assertThat(testMFSKS.getSkbat()).isEqualTo(UPDATED_SKBAT);
        assertThat(testMFSKS.getSkseq()).isEqualTo(UPDATED_SKSEQ);
        assertThat(testMFSKS.getSkref()).isEqualTo(UPDATED_SKREF);
        assertThat(testMFSKS.getSkdis()).isEqualTo(UPDATED_SKDIS);
        assertThat(testMFSKS.getSklmd()).isEqualTo(UPDATED_SKLMD);
        assertThat(testMFSKS.getSkuid()).isEqualTo(UPDATED_SKUID);
        assertThat(testMFSKS.getSkfil1()).isEqualTo(UPDATED_SKFIL_1);
        assertThat(testMFSKS.getSkfil2()).isEqualTo(UPDATED_SKFIL_2);
        assertThat(testMFSKS.getSkfil3()).isEqualTo(UPDATED_SKFIL_3);
        assertThat(testMFSKS.getSkfil4()).isEqualTo(UPDATED_SKFIL_4);
    }

    @Test
    @Transactional
    void patchNonExistingMFSKS() throws Exception {
        int databaseSizeBeforeUpdate = mFSKSRepository.findAll().size();
        mFSKS.setSkno(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMFSKSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mFSKS.getSkno())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mFSKS))
            )
            .andExpect(status().isBadRequest());

        // Validate the MFSKS in the database
        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMFSKS() throws Exception {
        int databaseSizeBeforeUpdate = mFSKSRepository.findAll().size();
        mFSKS.setSkno(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMFSKSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mFSKS))
            )
            .andExpect(status().isBadRequest());

        // Validate the MFSKS in the database
        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMFSKS() throws Exception {
        int databaseSizeBeforeUpdate = mFSKSRepository.findAll().size();
        mFSKS.setSkno(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMFSKSMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mFSKS)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MFSKS in the database
        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMFSKS() throws Exception {
        // Initialize the database
        mFSKSRepository.saveAndFlush(mFSKS);

        int databaseSizeBeforeDelete = mFSKSRepository.findAll().size();

        // Delete the mFSKS
        restMFSKSMockMvc
            .perform(delete(ENTITY_API_URL_ID, mFSKS.getSkno()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MFSKS> mFSKSList = mFSKSRepository.findAll();
        assertThat(mFSKSList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
