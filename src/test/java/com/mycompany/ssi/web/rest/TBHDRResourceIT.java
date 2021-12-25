package com.mycompany.ssi.web.rest;

import static com.mycompany.ssi.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.ssi.IntegrationTest;
import com.mycompany.ssi.domain.TBHDR;
import com.mycompany.ssi.repository.TBHDRRepository;
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
 * Integration tests for the {@link TBHDRResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TBHDRResourceIT {

    private static final String DEFAULT_THSTS = "AAAAAAAAAA";
    private static final String UPDATED_THSTS = "BBBBBBBBBB";

    private static final String DEFAULT_THSID = "AAAAAAAAAA";
    private static final String UPDATED_THSID = "BBBBBBBBBB";

    private static final String DEFAULT_THNM_1 = "AAAAAAAAAA";
    private static final String UPDATED_THNM_1 = "BBBBBBBBBB";

    private static final Integer DEFAULT_THJSH = 1;
    private static final Integer UPDATED_THJSH = 2;

    private static final BigDecimal DEFAULT_THTAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_THTAX = new BigDecimal(2);

    private static final LocalDate DEFAULT_THDIS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THDIS = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_THLMD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THLMD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_THUID = "AAAAAAAAAA";
    private static final String UPDATED_THUID = "BBBBBBBBBB";

    private static final Integer DEFAULT_THFIL_1 = 1;
    private static final Integer UPDATED_THFIL_1 = 2;

    private static final Integer DEFAULT_THFIL_2 = 1;
    private static final Integer UPDATED_THFIL_2 = 2;

    private static final String DEFAULT_THFIL_3 = "AAAAAAAAAA";
    private static final String UPDATED_THFIL_3 = "BBBBBBBBBB";

    private static final String DEFAULT_THFIL_4 = "AAAAAAAAAA";
    private static final String UPDATED_THFIL_4 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tbhdrs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{thno}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TBHDRRepository tBHDRRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTBHDRMockMvc;

    private TBHDR tBHDR;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBHDR createEntity(EntityManager em) {
        TBHDR tBHDR = new TBHDR()
            .thsts(DEFAULT_THSTS)
            .thsid(DEFAULT_THSID)
            .thnm1(DEFAULT_THNM_1)
            .thjsh(DEFAULT_THJSH)
            .thtax(DEFAULT_THTAX)
            .thdis(DEFAULT_THDIS)
            .thlmd(DEFAULT_THLMD)
            .thuid(DEFAULT_THUID)
            .thfil1(DEFAULT_THFIL_1)
            .thfil2(DEFAULT_THFIL_2)
            .thfil3(DEFAULT_THFIL_3)
            .thfil4(DEFAULT_THFIL_4);
        return tBHDR;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBHDR createUpdatedEntity(EntityManager em) {
        TBHDR tBHDR = new TBHDR()
            .thsts(UPDATED_THSTS)
            .thsid(UPDATED_THSID)
            .thnm1(UPDATED_THNM_1)
            .thjsh(UPDATED_THJSH)
            .thtax(UPDATED_THTAX)
            .thdis(UPDATED_THDIS)
            .thlmd(UPDATED_THLMD)
            .thuid(UPDATED_THUID)
            .thfil1(UPDATED_THFIL_1)
            .thfil2(UPDATED_THFIL_2)
            .thfil3(UPDATED_THFIL_3)
            .thfil4(UPDATED_THFIL_4);
        return tBHDR;
    }

    @BeforeEach
    public void initTest() {
        tBHDR = createEntity(em);
    }

    @Test
    @Transactional
    void createTBHDR() throws Exception {
        int databaseSizeBeforeCreate = tBHDRRepository.findAll().size();
        // Create the TBHDR
        restTBHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBHDR)))
            .andExpect(status().isCreated());

        // Validate the TBHDR in the database
        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeCreate + 1);
        TBHDR testTBHDR = tBHDRList.get(tBHDRList.size() - 1);
        assertThat(testTBHDR.getThsts()).isEqualTo(DEFAULT_THSTS);
        assertThat(testTBHDR.getThsid()).isEqualTo(DEFAULT_THSID);
        assertThat(testTBHDR.getThnm1()).isEqualTo(DEFAULT_THNM_1);
        assertThat(testTBHDR.getThjsh()).isEqualTo(DEFAULT_THJSH);
        assertThat(testTBHDR.getThtax()).isEqualByComparingTo(DEFAULT_THTAX);
        assertThat(testTBHDR.getThdis()).isEqualTo(DEFAULT_THDIS);
        assertThat(testTBHDR.getThlmd()).isEqualTo(DEFAULT_THLMD);
        assertThat(testTBHDR.getThuid()).isEqualTo(DEFAULT_THUID);
        assertThat(testTBHDR.getThfil1()).isEqualTo(DEFAULT_THFIL_1);
        assertThat(testTBHDR.getThfil2()).isEqualTo(DEFAULT_THFIL_2);
        assertThat(testTBHDR.getThfil3()).isEqualTo(DEFAULT_THFIL_3);
        assertThat(testTBHDR.getThfil4()).isEqualTo(DEFAULT_THFIL_4);
    }

    @Test
    @Transactional
    void createTBHDRWithExistingId() throws Exception {
        // Create the TBHDR with an existing ID
        tBHDR.setThno(1L);

        int databaseSizeBeforeCreate = tBHDRRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTBHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBHDR)))
            .andExpect(status().isBadRequest());

        // Validate the TBHDR in the database
        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkThstsIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBHDRRepository.findAll().size();
        // set the field null
        tBHDR.setThsts(null);

        // Create the TBHDR, which fails.

        restTBHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBHDR)))
            .andExpect(status().isBadRequest());

        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkThsidIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBHDRRepository.findAll().size();
        // set the field null
        tBHDR.setThsid(null);

        // Create the TBHDR, which fails.

        restTBHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBHDR)))
            .andExpect(status().isBadRequest());

        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkThnm1IsRequired() throws Exception {
        int databaseSizeBeforeTest = tBHDRRepository.findAll().size();
        // set the field null
        tBHDR.setThnm1(null);

        // Create the TBHDR, which fails.

        restTBHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBHDR)))
            .andExpect(status().isBadRequest());

        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkThjshIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBHDRRepository.findAll().size();
        // set the field null
        tBHDR.setThjsh(null);

        // Create the TBHDR, which fails.

        restTBHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBHDR)))
            .andExpect(status().isBadRequest());

        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkThtaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBHDRRepository.findAll().size();
        // set the field null
        tBHDR.setThtax(null);

        // Create the TBHDR, which fails.

        restTBHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBHDR)))
            .andExpect(status().isBadRequest());

        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkThdisIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBHDRRepository.findAll().size();
        // set the field null
        tBHDR.setThdis(null);

        // Create the TBHDR, which fails.

        restTBHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBHDR)))
            .andExpect(status().isBadRequest());

        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkThlmdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBHDRRepository.findAll().size();
        // set the field null
        tBHDR.setThlmd(null);

        // Create the TBHDR, which fails.

        restTBHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBHDR)))
            .andExpect(status().isBadRequest());

        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkThuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBHDRRepository.findAll().size();
        // set the field null
        tBHDR.setThuid(null);

        // Create the TBHDR, which fails.

        restTBHDRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBHDR)))
            .andExpect(status().isBadRequest());

        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTBHDRS() throws Exception {
        // Initialize the database
        tBHDRRepository.saveAndFlush(tBHDR);

        // Get all the tBHDRList
        restTBHDRMockMvc
            .perform(get(ENTITY_API_URL + "?sort=thno,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].thno").value(hasItem(tBHDR.getThno().intValue())))
            .andExpect(jsonPath("$.[*].thsts").value(hasItem(DEFAULT_THSTS)))
            .andExpect(jsonPath("$.[*].thsid").value(hasItem(DEFAULT_THSID)))
            .andExpect(jsonPath("$.[*].thnm1").value(hasItem(DEFAULT_THNM_1)))
            .andExpect(jsonPath("$.[*].thjsh").value(hasItem(DEFAULT_THJSH)))
            .andExpect(jsonPath("$.[*].thtax").value(hasItem(sameNumber(DEFAULT_THTAX))))
            .andExpect(jsonPath("$.[*].thdis").value(hasItem(DEFAULT_THDIS.toString())))
            .andExpect(jsonPath("$.[*].thlmd").value(hasItem(DEFAULT_THLMD.toString())))
            .andExpect(jsonPath("$.[*].thuid").value(hasItem(DEFAULT_THUID)))
            .andExpect(jsonPath("$.[*].thfil1").value(hasItem(DEFAULT_THFIL_1)))
            .andExpect(jsonPath("$.[*].thfil2").value(hasItem(DEFAULT_THFIL_2)))
            .andExpect(jsonPath("$.[*].thfil3").value(hasItem(DEFAULT_THFIL_3)))
            .andExpect(jsonPath("$.[*].thfil4").value(hasItem(DEFAULT_THFIL_4)));
    }

    @Test
    @Transactional
    void getTBHDR() throws Exception {
        // Initialize the database
        tBHDRRepository.saveAndFlush(tBHDR);

        // Get the tBHDR
        restTBHDRMockMvc
            .perform(get(ENTITY_API_URL_ID, tBHDR.getThno()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.thno").value(tBHDR.getThno().intValue()))
            .andExpect(jsonPath("$.thsts").value(DEFAULT_THSTS))
            .andExpect(jsonPath("$.thsid").value(DEFAULT_THSID))
            .andExpect(jsonPath("$.thnm1").value(DEFAULT_THNM_1))
            .andExpect(jsonPath("$.thjsh").value(DEFAULT_THJSH))
            .andExpect(jsonPath("$.thtax").value(sameNumber(DEFAULT_THTAX)))
            .andExpect(jsonPath("$.thdis").value(DEFAULT_THDIS.toString()))
            .andExpect(jsonPath("$.thlmd").value(DEFAULT_THLMD.toString()))
            .andExpect(jsonPath("$.thuid").value(DEFAULT_THUID))
            .andExpect(jsonPath("$.thfil1").value(DEFAULT_THFIL_1))
            .andExpect(jsonPath("$.thfil2").value(DEFAULT_THFIL_2))
            .andExpect(jsonPath("$.thfil3").value(DEFAULT_THFIL_3))
            .andExpect(jsonPath("$.thfil4").value(DEFAULT_THFIL_4));
    }

    @Test
    @Transactional
    void getNonExistingTBHDR() throws Exception {
        // Get the tBHDR
        restTBHDRMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTBHDR() throws Exception {
        // Initialize the database
        tBHDRRepository.saveAndFlush(tBHDR);

        int databaseSizeBeforeUpdate = tBHDRRepository.findAll().size();

        // Update the tBHDR
        TBHDR updatedTBHDR = tBHDRRepository.findById(tBHDR.getThno()).get();
        // Disconnect from session so that the updates on updatedTBHDR are not directly saved in db
        em.detach(updatedTBHDR);
        updatedTBHDR
            .thsts(UPDATED_THSTS)
            .thsid(UPDATED_THSID)
            .thnm1(UPDATED_THNM_1)
            .thjsh(UPDATED_THJSH)
            .thtax(UPDATED_THTAX)
            .thdis(UPDATED_THDIS)
            .thlmd(UPDATED_THLMD)
            .thuid(UPDATED_THUID)
            .thfil1(UPDATED_THFIL_1)
            .thfil2(UPDATED_THFIL_2)
            .thfil3(UPDATED_THFIL_3)
            .thfil4(UPDATED_THFIL_4);

        restTBHDRMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTBHDR.getThno())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTBHDR))
            )
            .andExpect(status().isOk());

        // Validate the TBHDR in the database
        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeUpdate);
        TBHDR testTBHDR = tBHDRList.get(tBHDRList.size() - 1);
        assertThat(testTBHDR.getThsts()).isEqualTo(UPDATED_THSTS);
        assertThat(testTBHDR.getThsid()).isEqualTo(UPDATED_THSID);
        assertThat(testTBHDR.getThnm1()).isEqualTo(UPDATED_THNM_1);
        assertThat(testTBHDR.getThjsh()).isEqualTo(UPDATED_THJSH);
        assertThat(testTBHDR.getThtax()).isEqualTo(UPDATED_THTAX);
        assertThat(testTBHDR.getThdis()).isEqualTo(UPDATED_THDIS);
        assertThat(testTBHDR.getThlmd()).isEqualTo(UPDATED_THLMD);
        assertThat(testTBHDR.getThuid()).isEqualTo(UPDATED_THUID);
        assertThat(testTBHDR.getThfil1()).isEqualTo(UPDATED_THFIL_1);
        assertThat(testTBHDR.getThfil2()).isEqualTo(UPDATED_THFIL_2);
        assertThat(testTBHDR.getThfil3()).isEqualTo(UPDATED_THFIL_3);
        assertThat(testTBHDR.getThfil4()).isEqualTo(UPDATED_THFIL_4);
    }

    @Test
    @Transactional
    void putNonExistingTBHDR() throws Exception {
        int databaseSizeBeforeUpdate = tBHDRRepository.findAll().size();
        tBHDR.setThno(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBHDRMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tBHDR.getThno())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBHDR))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBHDR in the database
        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTBHDR() throws Exception {
        int databaseSizeBeforeUpdate = tBHDRRepository.findAll().size();
        tBHDR.setThno(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBHDRMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBHDR))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBHDR in the database
        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTBHDR() throws Exception {
        int databaseSizeBeforeUpdate = tBHDRRepository.findAll().size();
        tBHDR.setThno(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBHDRMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBHDR)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBHDR in the database
        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTBHDRWithPatch() throws Exception {
        // Initialize the database
        tBHDRRepository.saveAndFlush(tBHDR);

        int databaseSizeBeforeUpdate = tBHDRRepository.findAll().size();

        // Update the tBHDR using partial update
        TBHDR partialUpdatedTBHDR = new TBHDR();
        partialUpdatedTBHDR.setThno(tBHDR.getThno());

        partialUpdatedTBHDR.thsts(UPDATED_THSTS).thjsh(UPDATED_THJSH).thdis(UPDATED_THDIS).thfil2(UPDATED_THFIL_2).thfil4(UPDATED_THFIL_4);

        restTBHDRMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBHDR.getThno())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBHDR))
            )
            .andExpect(status().isOk());

        // Validate the TBHDR in the database
        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeUpdate);
        TBHDR testTBHDR = tBHDRList.get(tBHDRList.size() - 1);
        assertThat(testTBHDR.getThsts()).isEqualTo(UPDATED_THSTS);
        assertThat(testTBHDR.getThsid()).isEqualTo(DEFAULT_THSID);
        assertThat(testTBHDR.getThnm1()).isEqualTo(DEFAULT_THNM_1);
        assertThat(testTBHDR.getThjsh()).isEqualTo(UPDATED_THJSH);
        assertThat(testTBHDR.getThtax()).isEqualByComparingTo(DEFAULT_THTAX);
        assertThat(testTBHDR.getThdis()).isEqualTo(UPDATED_THDIS);
        assertThat(testTBHDR.getThlmd()).isEqualTo(DEFAULT_THLMD);
        assertThat(testTBHDR.getThuid()).isEqualTo(DEFAULT_THUID);
        assertThat(testTBHDR.getThfil1()).isEqualTo(DEFAULT_THFIL_1);
        assertThat(testTBHDR.getThfil2()).isEqualTo(UPDATED_THFIL_2);
        assertThat(testTBHDR.getThfil3()).isEqualTo(DEFAULT_THFIL_3);
        assertThat(testTBHDR.getThfil4()).isEqualTo(UPDATED_THFIL_4);
    }

    @Test
    @Transactional
    void fullUpdateTBHDRWithPatch() throws Exception {
        // Initialize the database
        tBHDRRepository.saveAndFlush(tBHDR);

        int databaseSizeBeforeUpdate = tBHDRRepository.findAll().size();

        // Update the tBHDR using partial update
        TBHDR partialUpdatedTBHDR = new TBHDR();
        partialUpdatedTBHDR.setThno(tBHDR.getThno());

        partialUpdatedTBHDR
            .thsts(UPDATED_THSTS)
            .thsid(UPDATED_THSID)
            .thnm1(UPDATED_THNM_1)
            .thjsh(UPDATED_THJSH)
            .thtax(UPDATED_THTAX)
            .thdis(UPDATED_THDIS)
            .thlmd(UPDATED_THLMD)
            .thuid(UPDATED_THUID)
            .thfil1(UPDATED_THFIL_1)
            .thfil2(UPDATED_THFIL_2)
            .thfil3(UPDATED_THFIL_3)
            .thfil4(UPDATED_THFIL_4);

        restTBHDRMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBHDR.getThno())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBHDR))
            )
            .andExpect(status().isOk());

        // Validate the TBHDR in the database
        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeUpdate);
        TBHDR testTBHDR = tBHDRList.get(tBHDRList.size() - 1);
        assertThat(testTBHDR.getThsts()).isEqualTo(UPDATED_THSTS);
        assertThat(testTBHDR.getThsid()).isEqualTo(UPDATED_THSID);
        assertThat(testTBHDR.getThnm1()).isEqualTo(UPDATED_THNM_1);
        assertThat(testTBHDR.getThjsh()).isEqualTo(UPDATED_THJSH);
        assertThat(testTBHDR.getThtax()).isEqualByComparingTo(UPDATED_THTAX);
        assertThat(testTBHDR.getThdis()).isEqualTo(UPDATED_THDIS);
        assertThat(testTBHDR.getThlmd()).isEqualTo(UPDATED_THLMD);
        assertThat(testTBHDR.getThuid()).isEqualTo(UPDATED_THUID);
        assertThat(testTBHDR.getThfil1()).isEqualTo(UPDATED_THFIL_1);
        assertThat(testTBHDR.getThfil2()).isEqualTo(UPDATED_THFIL_2);
        assertThat(testTBHDR.getThfil3()).isEqualTo(UPDATED_THFIL_3);
        assertThat(testTBHDR.getThfil4()).isEqualTo(UPDATED_THFIL_4);
    }

    @Test
    @Transactional
    void patchNonExistingTBHDR() throws Exception {
        int databaseSizeBeforeUpdate = tBHDRRepository.findAll().size();
        tBHDR.setThno(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBHDRMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tBHDR.getThno())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBHDR))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBHDR in the database
        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTBHDR() throws Exception {
        int databaseSizeBeforeUpdate = tBHDRRepository.findAll().size();
        tBHDR.setThno(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBHDRMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBHDR))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBHDR in the database
        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTBHDR() throws Exception {
        int databaseSizeBeforeUpdate = tBHDRRepository.findAll().size();
        tBHDR.setThno(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBHDRMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tBHDR)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBHDR in the database
        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTBHDR() throws Exception {
        // Initialize the database
        tBHDRRepository.saveAndFlush(tBHDR);

        int databaseSizeBeforeDelete = tBHDRRepository.findAll().size();

        // Delete the tBHDR
        restTBHDRMockMvc
            .perform(delete(ENTITY_API_URL_ID, tBHDR.getThno()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TBHDR> tBHDRList = tBHDRRepository.findAll();
        assertThat(tBHDRList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
