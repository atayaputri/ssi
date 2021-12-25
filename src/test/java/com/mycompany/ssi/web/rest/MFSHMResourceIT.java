package com.mycompany.ssi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.ssi.IntegrationTest;
import com.mycompany.ssi.domain.MFSHM;
import com.mycompany.ssi.repository.MFSHMRepository;
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
 * Integration tests for the {@link MFSHMResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MFSHMResourceIT {

    private static final String DEFAULT_SHSTS = "A";
    private static final String UPDATED_SHSTS = "B";

    private static final Integer DEFAULT_SHFR = 1;
    private static final Integer UPDATED_SHFR = 2;

    private static final Integer DEFAULT_SHTO = 1;
    private static final Integer UPDATED_SHTO = 2;

    private static final Integer DEFAULT_SHJSHM = 1;
    private static final Integer UPDATED_SHJSHM = 2;

    private static final Integer DEFAULT_SHBAT = 1;
    private static final Integer UPDATED_SHBAT = 2;

    private static final Integer DEFAULT_SHSEQ = 1;
    private static final Integer UPDATED_SHSEQ = 2;

    private static final String DEFAULT_SHREF = "AAAAAAAAAA";
    private static final String UPDATED_SHREF = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SHDIS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SHDIS = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SHLMD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SHLMD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SHUID = "AAAAAAAAAA";
    private static final String UPDATED_SHUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mfshms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MFSHMRepository mFSHMRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMFSHMMockMvc;

    private MFSHM mFSHM;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MFSHM createEntity(EntityManager em) {
        MFSHM mFSHM = new MFSHM()
            .shsts(DEFAULT_SHSTS)
            .shfr(DEFAULT_SHFR)
            .shto(DEFAULT_SHTO)
            .shjshm(DEFAULT_SHJSHM)
            .shbat(DEFAULT_SHBAT)
            .shseq(DEFAULT_SHSEQ)
            .shref(DEFAULT_SHREF)
            .shdis(DEFAULT_SHDIS)
            .shlmd(DEFAULT_SHLMD)
            .shuid(DEFAULT_SHUID);
        return mFSHM;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MFSHM createUpdatedEntity(EntityManager em) {
        MFSHM mFSHM = new MFSHM()
            .shsts(UPDATED_SHSTS)
            .shfr(UPDATED_SHFR)
            .shto(UPDATED_SHTO)
            .shjshm(UPDATED_SHJSHM)
            .shbat(UPDATED_SHBAT)
            .shseq(UPDATED_SHSEQ)
            .shref(UPDATED_SHREF)
            .shdis(UPDATED_SHDIS)
            .shlmd(UPDATED_SHLMD)
            .shuid(UPDATED_SHUID);
        return mFSHM;
    }

    @BeforeEach
    public void initTest() {
        mFSHM = createEntity(em);
    }

    @Test
    @Transactional
    void createMFSHM() throws Exception {
        int databaseSizeBeforeCreate = mFSHMRepository.findAll().size();
        // Create the MFSHM
        restMFSHMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSHM)))
            .andExpect(status().isCreated());

        // Validate the MFSHM in the database
        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeCreate + 1);
        MFSHM testMFSHM = mFSHMList.get(mFSHMList.size() - 1);
        assertThat(testMFSHM.getShsts()).isEqualTo(DEFAULT_SHSTS);
        assertThat(testMFSHM.getShfr()).isEqualTo(DEFAULT_SHFR);
        assertThat(testMFSHM.getShto()).isEqualTo(DEFAULT_SHTO);
        assertThat(testMFSHM.getShjshm()).isEqualTo(DEFAULT_SHJSHM);
        assertThat(testMFSHM.getShbat()).isEqualTo(DEFAULT_SHBAT);
        assertThat(testMFSHM.getShseq()).isEqualTo(DEFAULT_SHSEQ);
        assertThat(testMFSHM.getShref()).isEqualTo(DEFAULT_SHREF);
        assertThat(testMFSHM.getShdis()).isEqualTo(DEFAULT_SHDIS);
        assertThat(testMFSHM.getShlmd()).isEqualTo(DEFAULT_SHLMD);
        assertThat(testMFSHM.getShuid()).isEqualTo(DEFAULT_SHUID);
    }

    @Test
    @Transactional
    void createMFSHMWithExistingId() throws Exception {
        // Create the MFSHM with an existing ID
        mFSHM.setId(1L);

        int databaseSizeBeforeCreate = mFSHMRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMFSHMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSHM)))
            .andExpect(status().isBadRequest());

        // Validate the MFSHM in the database
        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkShstsIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFSHMRepository.findAll().size();
        // set the field null
        mFSHM.setShsts(null);

        // Create the MFSHM, which fails.

        restMFSHMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSHM)))
            .andExpect(status().isBadRequest());

        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShfrIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFSHMRepository.findAll().size();
        // set the field null
        mFSHM.setShfr(null);

        // Create the MFSHM, which fails.

        restMFSHMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSHM)))
            .andExpect(status().isBadRequest());

        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShtoIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFSHMRepository.findAll().size();
        // set the field null
        mFSHM.setShto(null);

        // Create the MFSHM, which fails.

        restMFSHMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSHM)))
            .andExpect(status().isBadRequest());

        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShjshmIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFSHMRepository.findAll().size();
        // set the field null
        mFSHM.setShjshm(null);

        // Create the MFSHM, which fails.

        restMFSHMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSHM)))
            .andExpect(status().isBadRequest());

        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShbatIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFSHMRepository.findAll().size();
        // set the field null
        mFSHM.setShbat(null);

        // Create the MFSHM, which fails.

        restMFSHMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSHM)))
            .andExpect(status().isBadRequest());

        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShseqIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFSHMRepository.findAll().size();
        // set the field null
        mFSHM.setShseq(null);

        // Create the MFSHM, which fails.

        restMFSHMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSHM)))
            .andExpect(status().isBadRequest());

        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShrefIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFSHMRepository.findAll().size();
        // set the field null
        mFSHM.setShref(null);

        // Create the MFSHM, which fails.

        restMFSHMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSHM)))
            .andExpect(status().isBadRequest());

        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShdisIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFSHMRepository.findAll().size();
        // set the field null
        mFSHM.setShdis(null);

        // Create the MFSHM, which fails.

        restMFSHMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSHM)))
            .andExpect(status().isBadRequest());

        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShlmdIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFSHMRepository.findAll().size();
        // set the field null
        mFSHM.setShlmd(null);

        // Create the MFSHM, which fails.

        restMFSHMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSHM)))
            .andExpect(status().isBadRequest());

        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFSHMRepository.findAll().size();
        // set the field null
        mFSHM.setShuid(null);

        // Create the MFSHM, which fails.

        restMFSHMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSHM)))
            .andExpect(status().isBadRequest());

        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMFSHMS() throws Exception {
        // Initialize the database
        mFSHMRepository.saveAndFlush(mFSHM);

        // Get all the mFSHMList
        restMFSHMMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mFSHM.getId().intValue())))
            .andExpect(jsonPath("$.[*].shsts").value(hasItem(DEFAULT_SHSTS)))
            .andExpect(jsonPath("$.[*].shfr").value(hasItem(DEFAULT_SHFR)))
            .andExpect(jsonPath("$.[*].shto").value(hasItem(DEFAULT_SHTO)))
            .andExpect(jsonPath("$.[*].shjshm").value(hasItem(DEFAULT_SHJSHM)))
            .andExpect(jsonPath("$.[*].shbat").value(hasItem(DEFAULT_SHBAT)))
            .andExpect(jsonPath("$.[*].shseq").value(hasItem(DEFAULT_SHSEQ)))
            .andExpect(jsonPath("$.[*].shref").value(hasItem(DEFAULT_SHREF)))
            .andExpect(jsonPath("$.[*].shdis").value(hasItem(DEFAULT_SHDIS.toString())))
            .andExpect(jsonPath("$.[*].shlmd").value(hasItem(DEFAULT_SHLMD.toString())))
            .andExpect(jsonPath("$.[*].shuid").value(hasItem(DEFAULT_SHUID)));
    }

    @Test
    @Transactional
    void getMFSHM() throws Exception {
        // Initialize the database
        mFSHMRepository.saveAndFlush(mFSHM);

        // Get the mFSHM
        restMFSHMMockMvc
            .perform(get(ENTITY_API_URL_ID, mFSHM.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mFSHM.getId().intValue()))
            .andExpect(jsonPath("$.shsts").value(DEFAULT_SHSTS))
            .andExpect(jsonPath("$.shfr").value(DEFAULT_SHFR))
            .andExpect(jsonPath("$.shto").value(DEFAULT_SHTO))
            .andExpect(jsonPath("$.shjshm").value(DEFAULT_SHJSHM))
            .andExpect(jsonPath("$.shbat").value(DEFAULT_SHBAT))
            .andExpect(jsonPath("$.shseq").value(DEFAULT_SHSEQ))
            .andExpect(jsonPath("$.shref").value(DEFAULT_SHREF))
            .andExpect(jsonPath("$.shdis").value(DEFAULT_SHDIS.toString()))
            .andExpect(jsonPath("$.shlmd").value(DEFAULT_SHLMD.toString()))
            .andExpect(jsonPath("$.shuid").value(DEFAULT_SHUID));
    }

    @Test
    @Transactional
    void getNonExistingMFSHM() throws Exception {
        // Get the mFSHM
        restMFSHMMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMFSHM() throws Exception {
        // Initialize the database
        mFSHMRepository.saveAndFlush(mFSHM);

        int databaseSizeBeforeUpdate = mFSHMRepository.findAll().size();

        // Update the mFSHM
        MFSHM updatedMFSHM = mFSHMRepository.findById(mFSHM.getId()).get();
        // Disconnect from session so that the updates on updatedMFSHM are not directly saved in db
        em.detach(updatedMFSHM);
        updatedMFSHM
            .shsts(UPDATED_SHSTS)
            .shfr(UPDATED_SHFR)
            .shto(UPDATED_SHTO)
            .shjshm(UPDATED_SHJSHM)
            .shbat(UPDATED_SHBAT)
            .shseq(UPDATED_SHSEQ)
            .shref(UPDATED_SHREF)
            .shdis(UPDATED_SHDIS)
            .shlmd(UPDATED_SHLMD)
            .shuid(UPDATED_SHUID);

        restMFSHMMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMFSHM.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMFSHM))
            )
            .andExpect(status().isOk());

        // Validate the MFSHM in the database
        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeUpdate);
        MFSHM testMFSHM = mFSHMList.get(mFSHMList.size() - 1);
        assertThat(testMFSHM.getShsts()).isEqualTo(UPDATED_SHSTS);
        assertThat(testMFSHM.getShfr()).isEqualTo(UPDATED_SHFR);
        assertThat(testMFSHM.getShto()).isEqualTo(UPDATED_SHTO);
        assertThat(testMFSHM.getShjshm()).isEqualTo(UPDATED_SHJSHM);
        assertThat(testMFSHM.getShbat()).isEqualTo(UPDATED_SHBAT);
        assertThat(testMFSHM.getShseq()).isEqualTo(UPDATED_SHSEQ);
        assertThat(testMFSHM.getShref()).isEqualTo(UPDATED_SHREF);
        assertThat(testMFSHM.getShdis()).isEqualTo(UPDATED_SHDIS);
        assertThat(testMFSHM.getShlmd()).isEqualTo(UPDATED_SHLMD);
        assertThat(testMFSHM.getShuid()).isEqualTo(UPDATED_SHUID);
    }

    @Test
    @Transactional
    void putNonExistingMFSHM() throws Exception {
        int databaseSizeBeforeUpdate = mFSHMRepository.findAll().size();
        mFSHM.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMFSHMMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mFSHM.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mFSHM))
            )
            .andExpect(status().isBadRequest());

        // Validate the MFSHM in the database
        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMFSHM() throws Exception {
        int databaseSizeBeforeUpdate = mFSHMRepository.findAll().size();
        mFSHM.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMFSHMMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mFSHM))
            )
            .andExpect(status().isBadRequest());

        // Validate the MFSHM in the database
        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMFSHM() throws Exception {
        int databaseSizeBeforeUpdate = mFSHMRepository.findAll().size();
        mFSHM.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMFSHMMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFSHM)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MFSHM in the database
        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMFSHMWithPatch() throws Exception {
        // Initialize the database
        mFSHMRepository.saveAndFlush(mFSHM);

        int databaseSizeBeforeUpdate = mFSHMRepository.findAll().size();

        // Update the mFSHM using partial update
        MFSHM partialUpdatedMFSHM = new MFSHM();
        partialUpdatedMFSHM.setId(mFSHM.getId());

        partialUpdatedMFSHM
            .shsts(UPDATED_SHSTS)
            .shto(UPDATED_SHTO)
            .shjshm(UPDATED_SHJSHM)
            .shseq(UPDATED_SHSEQ)
            .shdis(UPDATED_SHDIS)
            .shlmd(UPDATED_SHLMD)
            .shuid(UPDATED_SHUID);

        restMFSHMMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMFSHM.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMFSHM))
            )
            .andExpect(status().isOk());

        // Validate the MFSHM in the database
        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeUpdate);
        MFSHM testMFSHM = mFSHMList.get(mFSHMList.size() - 1);
        assertThat(testMFSHM.getShsts()).isEqualTo(UPDATED_SHSTS);
        assertThat(testMFSHM.getShfr()).isEqualTo(DEFAULT_SHFR);
        assertThat(testMFSHM.getShto()).isEqualTo(UPDATED_SHTO);
        assertThat(testMFSHM.getShjshm()).isEqualTo(UPDATED_SHJSHM);
        assertThat(testMFSHM.getShbat()).isEqualTo(DEFAULT_SHBAT);
        assertThat(testMFSHM.getShseq()).isEqualTo(UPDATED_SHSEQ);
        assertThat(testMFSHM.getShref()).isEqualTo(DEFAULT_SHREF);
        assertThat(testMFSHM.getShdis()).isEqualTo(UPDATED_SHDIS);
        assertThat(testMFSHM.getShlmd()).isEqualTo(UPDATED_SHLMD);
        assertThat(testMFSHM.getShuid()).isEqualTo(UPDATED_SHUID);
    }

    @Test
    @Transactional
    void fullUpdateMFSHMWithPatch() throws Exception {
        // Initialize the database
        mFSHMRepository.saveAndFlush(mFSHM);

        int databaseSizeBeforeUpdate = mFSHMRepository.findAll().size();

        // Update the mFSHM using partial update
        MFSHM partialUpdatedMFSHM = new MFSHM();
        partialUpdatedMFSHM.setId(mFSHM.getId());

        partialUpdatedMFSHM
            .shsts(UPDATED_SHSTS)
            .shfr(UPDATED_SHFR)
            .shto(UPDATED_SHTO)
            .shjshm(UPDATED_SHJSHM)
            .shbat(UPDATED_SHBAT)
            .shseq(UPDATED_SHSEQ)
            .shref(UPDATED_SHREF)
            .shdis(UPDATED_SHDIS)
            .shlmd(UPDATED_SHLMD)
            .shuid(UPDATED_SHUID);

        restMFSHMMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMFSHM.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMFSHM))
            )
            .andExpect(status().isOk());

        // Validate the MFSHM in the database
        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeUpdate);
        MFSHM testMFSHM = mFSHMList.get(mFSHMList.size() - 1);
        assertThat(testMFSHM.getShsts()).isEqualTo(UPDATED_SHSTS);
        assertThat(testMFSHM.getShfr()).isEqualTo(UPDATED_SHFR);
        assertThat(testMFSHM.getShto()).isEqualTo(UPDATED_SHTO);
        assertThat(testMFSHM.getShjshm()).isEqualTo(UPDATED_SHJSHM);
        assertThat(testMFSHM.getShbat()).isEqualTo(UPDATED_SHBAT);
        assertThat(testMFSHM.getShseq()).isEqualTo(UPDATED_SHSEQ);
        assertThat(testMFSHM.getShref()).isEqualTo(UPDATED_SHREF);
        assertThat(testMFSHM.getShdis()).isEqualTo(UPDATED_SHDIS);
        assertThat(testMFSHM.getShlmd()).isEqualTo(UPDATED_SHLMD);
        assertThat(testMFSHM.getShuid()).isEqualTo(UPDATED_SHUID);
    }

    @Test
    @Transactional
    void patchNonExistingMFSHM() throws Exception {
        int databaseSizeBeforeUpdate = mFSHMRepository.findAll().size();
        mFSHM.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMFSHMMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mFSHM.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mFSHM))
            )
            .andExpect(status().isBadRequest());

        // Validate the MFSHM in the database
        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMFSHM() throws Exception {
        int databaseSizeBeforeUpdate = mFSHMRepository.findAll().size();
        mFSHM.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMFSHMMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mFSHM))
            )
            .andExpect(status().isBadRequest());

        // Validate the MFSHM in the database
        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMFSHM() throws Exception {
        int databaseSizeBeforeUpdate = mFSHMRepository.findAll().size();
        mFSHM.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMFSHMMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mFSHM)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MFSHM in the database
        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMFSHM() throws Exception {
        // Initialize the database
        mFSHMRepository.saveAndFlush(mFSHM);

        int databaseSizeBeforeDelete = mFSHMRepository.findAll().size();

        // Delete the mFSHM
        restMFSHMMockMvc
            .perform(delete(ENTITY_API_URL_ID, mFSHM.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MFSHM> mFSHMList = mFSHMRepository.findAll();
        assertThat(mFSHMList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
