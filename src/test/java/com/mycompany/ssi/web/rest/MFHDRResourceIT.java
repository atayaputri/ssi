package com.mycompany.ssi.web.rest;

import static com.mycompany.ssi.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.ssi.IntegrationTest;
import com.mycompany.ssi.domain.MFHDR;
import com.mycompany.ssi.domain.enumeration.Citizenship;
import com.mycompany.ssi.domain.enumeration.HolderGroup;
import com.mycompany.ssi.repository.MFHDRRepository;
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
 * Integration tests for the {@link MFHDRResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MFHDRResourceIT {

    private static final String DEFAULT_HDSTS = "AAAAAAAAAA";
    private static final String UPDATED_HDSTS = "BBBBBBBBBB";

    private static final String DEFAULT_HDSID = "AAAAAAAAAA";
    private static final String UPDATED_HDSID = "BBBBBBBBBB";

    private static final String DEFAULT_HDNM_1 = "AAAAAAAAAA";
    private static final String UPDATED_HDNM_1 = "BBBBBBBBBB";

    private static final String DEFAULT_HDNM_2 = "AAAAAAAAAA";
    private static final String UPDATED_HDNM_2 = "BBBBBBBBBB";

    private static final String DEFAULT_HDAL_1 = "AAAAAAAAAA";
    private static final String UPDATED_HDAL_1 = "BBBBBBBBBB";

    private static final String DEFAULT_HDAL_2 = "AAAAAAAAAA";
    private static final String UPDATED_HDAL_2 = "BBBBBBBBBB";

    private static final Integer DEFAULT_HDJSH = 1;
    private static final Integer UPDATED_HDJSH = 2;

    private static final HolderGroup DEFAULT_HDINCO = HolderGroup.I;
    private static final HolderGroup UPDATED_HDINCO = HolderGroup.C;

    private static final Citizenship DEFAULT_HDKWN = Citizenship.I;
    private static final Citizenship UPDATED_HDKWN = Citizenship.A;

    private static final String DEFAULT_HDKTP = "AAAAAAAAAA";
    private static final String UPDATED_HDKTP = "BBBBBBBBBB";

    private static final String DEFAULT_HDNPWP = "AAAAAAAAAA";
    private static final String UPDATED_HDNPWP = "BBBBBBBBBB";

    private static final String DEFAULT_HDSIUP = "AAAAAAAAAA";
    private static final String UPDATED_HDSIUP = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_HDTAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_HDTAX = new BigDecimal(2);

    private static final LocalDate DEFAULT_HDDIS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HDDIS = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_HDLMD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HDLMD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HDUID = "AAAAAAAAAA";
    private static final String UPDATED_HDUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mfhdrs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{hdno}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MFHDRRepository mFHDRRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMFHDRMockMvc;

    private MFHDR mFHDR;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MFHDR createEntity(EntityManager em) {
        MFHDR mFHDR = new MFHDR()
            .hdsts(DEFAULT_HDSTS)
            .hdsid(DEFAULT_HDSID)
            .hdnm1(DEFAULT_HDNM_1)
            .hdnm2(DEFAULT_HDNM_2)
            .hdal1(DEFAULT_HDAL_1)
            .hdal2(DEFAULT_HDAL_2)
            .hdjsh(DEFAULT_HDJSH)
            .hdinco(DEFAULT_HDINCO)
            .hdkwn(DEFAULT_HDKWN)
            .hdktp(DEFAULT_HDKTP)
            .hdnpwp(DEFAULT_HDNPWP)
            .hdsiup(DEFAULT_HDSIUP)
            .hdtax(DEFAULT_HDTAX)
            .hddis(DEFAULT_HDDIS)
            .hdlmd(DEFAULT_HDLMD)
            .hduid(DEFAULT_HDUID);
        return mFHDR;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MFHDR createUpdatedEntity(EntityManager em) {
        MFHDR mFHDR = new MFHDR()
            .hdsts(UPDATED_HDSTS)
            .hdsid(UPDATED_HDSID)
            .hdnm1(UPDATED_HDNM_1)
            .hdnm2(UPDATED_HDNM_2)
            .hdal1(UPDATED_HDAL_1)
            .hdal2(UPDATED_HDAL_2)
            .hdjsh(UPDATED_HDJSH)
            .hdinco(UPDATED_HDINCO)
            .hdkwn(UPDATED_HDKWN)
            .hdktp(UPDATED_HDKTP)
            .hdnpwp(UPDATED_HDNPWP)
            .hdsiup(UPDATED_HDSIUP)
            .hdtax(UPDATED_HDTAX)
            .hddis(UPDATED_HDDIS)
            .hdlmd(UPDATED_HDLMD)
            .hduid(UPDATED_HDUID);
        return mFHDR;
    }

    @BeforeEach
    public void initTest() {
        mFHDR = createEntity(em);
    }

    @Test
    @Transactional
    void createMFHDR() throws Exception {
        int databaseSizeBeforeCreate = mFHDRRepository.findAll().size();
        // Create the MFHDR
        restMFHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isCreated());

        // Validate the MFHDR in the database
        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeCreate + 1);
        MFHDR testMFHDR = mFHDRList.get(mFHDRList.size() - 1);
        assertThat(testMFHDR.getHdsts()).isEqualTo(DEFAULT_HDSTS);
        assertThat(testMFHDR.getHdsid()).isEqualTo(DEFAULT_HDSID);
        assertThat(testMFHDR.getHdnm1()).isEqualTo(DEFAULT_HDNM_1);
        assertThat(testMFHDR.getHdnm2()).isEqualTo(DEFAULT_HDNM_2);
        assertThat(testMFHDR.getHdal1()).isEqualTo(DEFAULT_HDAL_1);
        assertThat(testMFHDR.getHdal2()).isEqualTo(DEFAULT_HDAL_2);
        assertThat(testMFHDR.getHdjsh()).isEqualTo(DEFAULT_HDJSH);
        assertThat(testMFHDR.getHdinco()).isEqualTo(DEFAULT_HDINCO);
        assertThat(testMFHDR.getHdkwn()).isEqualTo(DEFAULT_HDKWN);
        assertThat(testMFHDR.getHdktp()).isEqualTo(DEFAULT_HDKTP);
        assertThat(testMFHDR.getHdnpwp()).isEqualTo(DEFAULT_HDNPWP);
        assertThat(testMFHDR.getHdsiup()).isEqualTo(DEFAULT_HDSIUP);
        assertThat(testMFHDR.getHdtax()).isEqualByComparingTo(DEFAULT_HDTAX);
        assertThat(testMFHDR.getHddis()).isEqualTo(DEFAULT_HDDIS);
        assertThat(testMFHDR.getHdlmd()).isEqualTo(DEFAULT_HDLMD);
        assertThat(testMFHDR.getHduid()).isEqualTo(DEFAULT_HDUID);
    }

    @Test
    @Transactional
    void createMFHDRWithExistingId() throws Exception {
        // Create the MFHDR with an existing ID
        mFHDR.setHdno(1L);

        int databaseSizeBeforeCreate = mFHDRRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMFHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isBadRequest());

        // Validate the MFHDR in the database
        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkHdstsIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFHDRRepository.findAll().size();
        // set the field null
        mFHDR.setHdsts(null);

        // Create the MFHDR, which fails.

        restMFHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isBadRequest());

        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHdsidIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFHDRRepository.findAll().size();
        // set the field null
        mFHDR.setHdsid(null);

        // Create the MFHDR, which fails.

        restMFHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isBadRequest());

        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHdnm1IsRequired() throws Exception {
        int databaseSizeBeforeTest = mFHDRRepository.findAll().size();
        // set the field null
        mFHDR.setHdnm1(null);

        // Create the MFHDR, which fails.

        restMFHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isBadRequest());

        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHdnm2IsRequired() throws Exception {
        int databaseSizeBeforeTest = mFHDRRepository.findAll().size();
        // set the field null
        mFHDR.setHdnm2(null);

        // Create the MFHDR, which fails.

        restMFHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isBadRequest());

        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHdal1IsRequired() throws Exception {
        int databaseSizeBeforeTest = mFHDRRepository.findAll().size();
        // set the field null
        mFHDR.setHdal1(null);

        // Create the MFHDR, which fails.

        restMFHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isBadRequest());

        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHdal2IsRequired() throws Exception {
        int databaseSizeBeforeTest = mFHDRRepository.findAll().size();
        // set the field null
        mFHDR.setHdal2(null);

        // Create the MFHDR, which fails.

        restMFHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isBadRequest());

        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHdjshIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFHDRRepository.findAll().size();
        // set the field null
        mFHDR.setHdjsh(null);

        // Create the MFHDR, which fails.

        restMFHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isBadRequest());

        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHdincoIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFHDRRepository.findAll().size();
        // set the field null
        mFHDR.setHdinco(null);

        // Create the MFHDR, which fails.

        restMFHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isBadRequest());

        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHdkwnIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFHDRRepository.findAll().size();
        // set the field null
        mFHDR.setHdkwn(null);

        // Create the MFHDR, which fails.

        restMFHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isBadRequest());

        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHdktpIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFHDRRepository.findAll().size();
        // set the field null
        mFHDR.setHdktp(null);

        // Create the MFHDR, which fails.

        restMFHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isBadRequest());

        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHdnpwpIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFHDRRepository.findAll().size();
        // set the field null
        mFHDR.setHdnpwp(null);

        // Create the MFHDR, which fails.

        restMFHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isBadRequest());

        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHdsiupIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFHDRRepository.findAll().size();
        // set the field null
        mFHDR.setHdsiup(null);

        // Create the MFHDR, which fails.

        restMFHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isBadRequest());

        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHdtaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFHDRRepository.findAll().size();
        // set the field null
        mFHDR.setHdtax(null);

        // Create the MFHDR, which fails.

        restMFHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isBadRequest());

        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHddisIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFHDRRepository.findAll().size();
        // set the field null
        mFHDR.setHddis(null);

        // Create the MFHDR, which fails.

        restMFHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isBadRequest());

        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHdlmdIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFHDRRepository.findAll().size();
        // set the field null
        mFHDR.setHdlmd(null);

        // Create the MFHDR, which fails.

        restMFHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isBadRequest());

        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHduidIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFHDRRepository.findAll().size();
        // set the field null
        mFHDR.setHduid(null);

        // Create the MFHDR, which fails.

        restMFHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isBadRequest());

        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMFHDRS() throws Exception {
        // Initialize the database
        mFHDRRepository.saveAndFlush(mFHDR);

        // Get all the mFHDRList
        restMFHDRMockMvc
            .perform(get(ENTITY_API_URL + "?sort=hdno,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].hdno").value(hasItem(mFHDR.getHdno().intValue())))
            .andExpect(jsonPath("$.[*].hdsts").value(hasItem(DEFAULT_HDSTS)))
            .andExpect(jsonPath("$.[*].hdsid").value(hasItem(DEFAULT_HDSID)))
            .andExpect(jsonPath("$.[*].hdnm1").value(hasItem(DEFAULT_HDNM_1)))
            .andExpect(jsonPath("$.[*].hdnm2").value(hasItem(DEFAULT_HDNM_2)))
            .andExpect(jsonPath("$.[*].hdal1").value(hasItem(DEFAULT_HDAL_1)))
            .andExpect(jsonPath("$.[*].hdal2").value(hasItem(DEFAULT_HDAL_2)))
            .andExpect(jsonPath("$.[*].hdjsh").value(hasItem(DEFAULT_HDJSH)))
            .andExpect(jsonPath("$.[*].hdinco").value(hasItem(DEFAULT_HDINCO.toString())))
            .andExpect(jsonPath("$.[*].hdkwn").value(hasItem(DEFAULT_HDKWN.toString())))
            .andExpect(jsonPath("$.[*].hdktp").value(hasItem(DEFAULT_HDKTP)))
            .andExpect(jsonPath("$.[*].hdnpwp").value(hasItem(DEFAULT_HDNPWP)))
            .andExpect(jsonPath("$.[*].hdsiup").value(hasItem(DEFAULT_HDSIUP)))
            .andExpect(jsonPath("$.[*].hdtax").value(hasItem(sameNumber(DEFAULT_HDTAX))))
            .andExpect(jsonPath("$.[*].hddis").value(hasItem(DEFAULT_HDDIS.toString())))
            .andExpect(jsonPath("$.[*].hdlmd").value(hasItem(DEFAULT_HDLMD.toString())))
            .andExpect(jsonPath("$.[*].hduid").value(hasItem(DEFAULT_HDUID)));
    }

    @Test
    @Transactional
    void getMFHDR() throws Exception {
        // Initialize the database
        mFHDRRepository.saveAndFlush(mFHDR);

        // Get the mFHDR
        restMFHDRMockMvc
            .perform(get(ENTITY_API_URL_ID, mFHDR.getHdno()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.hdno").value(mFHDR.getHdno().intValue()))
            .andExpect(jsonPath("$.hdsts").value(DEFAULT_HDSTS))
            .andExpect(jsonPath("$.hdsid").value(DEFAULT_HDSID))
            .andExpect(jsonPath("$.hdnm1").value(DEFAULT_HDNM_1))
            .andExpect(jsonPath("$.hdnm2").value(DEFAULT_HDNM_2))
            .andExpect(jsonPath("$.hdal1").value(DEFAULT_HDAL_1))
            .andExpect(jsonPath("$.hdal2").value(DEFAULT_HDAL_2))
            .andExpect(jsonPath("$.hdjsh").value(DEFAULT_HDJSH))
            .andExpect(jsonPath("$.hdinco").value(DEFAULT_HDINCO.toString()))
            .andExpect(jsonPath("$.hdkwn").value(DEFAULT_HDKWN.toString()))
            .andExpect(jsonPath("$.hdktp").value(DEFAULT_HDKTP))
            .andExpect(jsonPath("$.hdnpwp").value(DEFAULT_HDNPWP))
            .andExpect(jsonPath("$.hdsiup").value(DEFAULT_HDSIUP))
            .andExpect(jsonPath("$.hdtax").value(sameNumber(DEFAULT_HDTAX)))
            .andExpect(jsonPath("$.hddis").value(DEFAULT_HDDIS.toString()))
            .andExpect(jsonPath("$.hdlmd").value(DEFAULT_HDLMD.toString()))
            .andExpect(jsonPath("$.hduid").value(DEFAULT_HDUID));
    }

    @Test
    @Transactional
    void getNonExistingMFHDR() throws Exception {
        // Get the mFHDR
        restMFHDRMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMFHDR() throws Exception {
        // Initialize the database
        mFHDRRepository.saveAndFlush(mFHDR);

        int databaseSizeBeforeUpdate = mFHDRRepository.findAll().size();

        // Update the mFHDR
        MFHDR updatedMFHDR = mFHDRRepository.findById(mFHDR.getHdno()).get();
        // Disconnect from session so that the updates on updatedMFHDR are not directly saved in db
        em.detach(updatedMFHDR);
        updatedMFHDR
            .hdsts(UPDATED_HDSTS)
            .hdsid(UPDATED_HDSID)
            .hdnm1(UPDATED_HDNM_1)
            .hdnm2(UPDATED_HDNM_2)
            .hdal1(UPDATED_HDAL_1)
            .hdal2(UPDATED_HDAL_2)
            .hdjsh(UPDATED_HDJSH)
            .hdinco(UPDATED_HDINCO)
            .hdkwn(UPDATED_HDKWN)
            .hdktp(UPDATED_HDKTP)
            .hdnpwp(UPDATED_HDNPWP)
            .hdsiup(UPDATED_HDSIUP)
            .hdtax(UPDATED_HDTAX)
            .hddis(UPDATED_HDDIS)
            .hdlmd(UPDATED_HDLMD)
            .hduid(UPDATED_HDUID);

        restMFHDRMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMFHDR.getHdno())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMFHDR))
            )
            .andExpect(status().isOk());

        // Validate the MFHDR in the database
        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeUpdate);
        MFHDR testMFHDR = mFHDRList.get(mFHDRList.size() - 1);
        assertThat(testMFHDR.getHdsts()).isEqualTo(UPDATED_HDSTS);
        assertThat(testMFHDR.getHdsid()).isEqualTo(UPDATED_HDSID);
        assertThat(testMFHDR.getHdnm1()).isEqualTo(UPDATED_HDNM_1);
        assertThat(testMFHDR.getHdnm2()).isEqualTo(UPDATED_HDNM_2);
        assertThat(testMFHDR.getHdal1()).isEqualTo(UPDATED_HDAL_1);
        assertThat(testMFHDR.getHdal2()).isEqualTo(UPDATED_HDAL_2);
        assertThat(testMFHDR.getHdjsh()).isEqualTo(UPDATED_HDJSH);
        assertThat(testMFHDR.getHdinco()).isEqualTo(UPDATED_HDINCO);
        assertThat(testMFHDR.getHdkwn()).isEqualTo(UPDATED_HDKWN);
        assertThat(testMFHDR.getHdktp()).isEqualTo(UPDATED_HDKTP);
        assertThat(testMFHDR.getHdnpwp()).isEqualTo(UPDATED_HDNPWP);
        assertThat(testMFHDR.getHdsiup()).isEqualTo(UPDATED_HDSIUP);
        assertThat(testMFHDR.getHdtax()).isEqualTo(UPDATED_HDTAX);
        assertThat(testMFHDR.getHddis()).isEqualTo(UPDATED_HDDIS);
        assertThat(testMFHDR.getHdlmd()).isEqualTo(UPDATED_HDLMD);
        assertThat(testMFHDR.getHduid()).isEqualTo(UPDATED_HDUID);
    }

    @Test
    @Transactional
    void putNonExistingMFHDR() throws Exception {
        int databaseSizeBeforeUpdate = mFHDRRepository.findAll().size();
        mFHDR.setHdno(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMFHDRMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mFHDR.getHdno())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mFHDR))
            )
            .andExpect(status().isBadRequest());

        // Validate the MFHDR in the database
        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMFHDR() throws Exception {
        int databaseSizeBeforeUpdate = mFHDRRepository.findAll().size();
        mFHDR.setHdno(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMFHDRMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mFHDR))
            )
            .andExpect(status().isBadRequest());

        // Validate the MFHDR in the database
        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMFHDR() throws Exception {
        int databaseSizeBeforeUpdate = mFHDRRepository.findAll().size();
        mFHDR.setHdno(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMFHDRMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MFHDR in the database
        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMFHDRWithPatch() throws Exception {
        // Initialize the database
        mFHDRRepository.saveAndFlush(mFHDR);

        int databaseSizeBeforeUpdate = mFHDRRepository.findAll().size();

        // Update the mFHDR using partial update
        MFHDR partialUpdatedMFHDR = new MFHDR();
        partialUpdatedMFHDR.setHdno(mFHDR.getHdno());

        partialUpdatedMFHDR
            .hdsts(UPDATED_HDSTS)
            .hdal1(UPDATED_HDAL_1)
            .hdjsh(UPDATED_HDJSH)
            .hdinco(UPDATED_HDINCO)
            .hdkwn(UPDATED_HDKWN)
            .hdktp(UPDATED_HDKTP)
            .hdnpwp(UPDATED_HDNPWP)
            .hdtax(UPDATED_HDTAX)
            .hddis(UPDATED_HDDIS)
            .hdlmd(UPDATED_HDLMD)
            .hduid(UPDATED_HDUID);

        restMFHDRMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMFHDR.getHdno())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMFHDR))
            )
            .andExpect(status().isOk());

        // Validate the MFHDR in the database
        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeUpdate);
        MFHDR testMFHDR = mFHDRList.get(mFHDRList.size() - 1);
        assertThat(testMFHDR.getHdsts()).isEqualTo(UPDATED_HDSTS);
        assertThat(testMFHDR.getHdsid()).isEqualTo(DEFAULT_HDSID);
        assertThat(testMFHDR.getHdnm1()).isEqualTo(DEFAULT_HDNM_1);
        assertThat(testMFHDR.getHdnm2()).isEqualTo(DEFAULT_HDNM_2);
        assertThat(testMFHDR.getHdal1()).isEqualTo(UPDATED_HDAL_1);
        assertThat(testMFHDR.getHdal2()).isEqualTo(DEFAULT_HDAL_2);
        assertThat(testMFHDR.getHdjsh()).isEqualTo(UPDATED_HDJSH);
        assertThat(testMFHDR.getHdinco()).isEqualTo(UPDATED_HDINCO);
        assertThat(testMFHDR.getHdkwn()).isEqualTo(UPDATED_HDKWN);
        assertThat(testMFHDR.getHdktp()).isEqualTo(UPDATED_HDKTP);
        assertThat(testMFHDR.getHdnpwp()).isEqualTo(UPDATED_HDNPWP);
        assertThat(testMFHDR.getHdsiup()).isEqualTo(DEFAULT_HDSIUP);
        assertThat(testMFHDR.getHdtax()).isEqualByComparingTo(UPDATED_HDTAX);
        assertThat(testMFHDR.getHddis()).isEqualTo(UPDATED_HDDIS);
        assertThat(testMFHDR.getHdlmd()).isEqualTo(UPDATED_HDLMD);
        assertThat(testMFHDR.getHduid()).isEqualTo(UPDATED_HDUID);
    }

    @Test
    @Transactional
    void fullUpdateMFHDRWithPatch() throws Exception {
        // Initialize the database
        mFHDRRepository.saveAndFlush(mFHDR);

        int databaseSizeBeforeUpdate = mFHDRRepository.findAll().size();

        // Update the mFHDR using partial update
        MFHDR partialUpdatedMFHDR = new MFHDR();
        partialUpdatedMFHDR.setHdno(mFHDR.getHdno());

        partialUpdatedMFHDR
            .hdsts(UPDATED_HDSTS)
            .hdsid(UPDATED_HDSID)
            .hdnm1(UPDATED_HDNM_1)
            .hdnm2(UPDATED_HDNM_2)
            .hdal1(UPDATED_HDAL_1)
            .hdal2(UPDATED_HDAL_2)
            .hdjsh(UPDATED_HDJSH)
            .hdinco(UPDATED_HDINCO)
            .hdkwn(UPDATED_HDKWN)
            .hdktp(UPDATED_HDKTP)
            .hdnpwp(UPDATED_HDNPWP)
            .hdsiup(UPDATED_HDSIUP)
            .hdtax(UPDATED_HDTAX)
            .hddis(UPDATED_HDDIS)
            .hdlmd(UPDATED_HDLMD)
            .hduid(UPDATED_HDUID);

        restMFHDRMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMFHDR.getHdno())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMFHDR))
            )
            .andExpect(status().isOk());

        // Validate the MFHDR in the database
        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeUpdate);
        MFHDR testMFHDR = mFHDRList.get(mFHDRList.size() - 1);
        assertThat(testMFHDR.getHdsts()).isEqualTo(UPDATED_HDSTS);
        assertThat(testMFHDR.getHdsid()).isEqualTo(UPDATED_HDSID);
        assertThat(testMFHDR.getHdnm1()).isEqualTo(UPDATED_HDNM_1);
        assertThat(testMFHDR.getHdnm2()).isEqualTo(UPDATED_HDNM_2);
        assertThat(testMFHDR.getHdal1()).isEqualTo(UPDATED_HDAL_1);
        assertThat(testMFHDR.getHdal2()).isEqualTo(UPDATED_HDAL_2);
        assertThat(testMFHDR.getHdjsh()).isEqualTo(UPDATED_HDJSH);
        assertThat(testMFHDR.getHdinco()).isEqualTo(UPDATED_HDINCO);
        assertThat(testMFHDR.getHdkwn()).isEqualTo(UPDATED_HDKWN);
        assertThat(testMFHDR.getHdktp()).isEqualTo(UPDATED_HDKTP);
        assertThat(testMFHDR.getHdnpwp()).isEqualTo(UPDATED_HDNPWP);
        assertThat(testMFHDR.getHdsiup()).isEqualTo(UPDATED_HDSIUP);
        assertThat(testMFHDR.getHdtax()).isEqualByComparingTo(UPDATED_HDTAX);
        assertThat(testMFHDR.getHddis()).isEqualTo(UPDATED_HDDIS);
        assertThat(testMFHDR.getHdlmd()).isEqualTo(UPDATED_HDLMD);
        assertThat(testMFHDR.getHduid()).isEqualTo(UPDATED_HDUID);
    }

    @Test
    @Transactional
    void patchNonExistingMFHDR() throws Exception {
        int databaseSizeBeforeUpdate = mFHDRRepository.findAll().size();
        mFHDR.setHdno(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMFHDRMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mFHDR.getHdno())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mFHDR))
            )
            .andExpect(status().isBadRequest());

        // Validate the MFHDR in the database
        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMFHDR() throws Exception {
        int databaseSizeBeforeUpdate = mFHDRRepository.findAll().size();
        mFHDR.setHdno(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMFHDRMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mFHDR))
            )
            .andExpect(status().isBadRequest());

        // Validate the MFHDR in the database
        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMFHDR() throws Exception {
        int databaseSizeBeforeUpdate = mFHDRRepository.findAll().size();
        mFHDR.setHdno(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMFHDRMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mFHDR)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MFHDR in the database
        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMFHDR() throws Exception {
        // Initialize the database
        mFHDRRepository.saveAndFlush(mFHDR);

        int databaseSizeBeforeDelete = mFHDRRepository.findAll().size();

        // Delete the mFHDR
        restMFHDRMockMvc
            .perform(delete(ENTITY_API_URL_ID, mFHDR.getHdno()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MFHDR> mFHDRList = mFHDRRepository.findAll();
        assertThat(mFHDRList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
