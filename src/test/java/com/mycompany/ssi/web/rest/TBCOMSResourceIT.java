package com.mycompany.ssi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.ssi.IntegrationTest;
import com.mycompany.ssi.domain.TBCOMS;
import com.mycompany.ssi.repository.TBCOMSRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
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
 * Integration tests for the {@link TBCOMSResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TBCOMSResourceIT {

    private static final String DEFAULT_COSTS = "AAAAAAAAAA";
    private static final String UPDATED_COSTS = "BBBBBBBBBB";

    private static final String DEFAULT_CONAM = "AAAAAAAAAA";
    private static final String UPDATED_CONAM = "BBBBBBBBBB";

    private static final String DEFAULT_COCBEI = "AAAAAA";
    private static final String UPDATED_COCBEI = "BBBBBB";

    private static final String DEFAULT_CONBEI = "AAAAAAAAAA";
    private static final String UPDATED_CONBEI = "BBBBBBBBBB";

    private static final String DEFAULT_COSAT = "AAAAAAAAAA";
    private static final String UPDATED_COSAT = "BBBBBBBBBB";

    private static final Integer DEFAULT_CONOM = 1;
    private static final Integer UPDATED_CONOM = 2;

    private static final String DEFAULT_COISIN = "AAAAAAAAAA";
    private static final String UPDATED_COISIN = "BBBBBBBBBB";

    private static final String DEFAULT_CONPWP = "AAAAAAAAAA";
    private static final String UPDATED_CONPWP = "BBBBBBBBBB";

    private static final String DEFAULT_COSERI = "AAAAAAAAAA";
    private static final String UPDATED_COSERI = "BBBBBBBBBB";

    private static final Integer DEFAULT_COLSHM = 1;
    private static final Integer UPDATED_COLSHM = 2;

    private static final Integer DEFAULT_COLSKS = 1;
    private static final Integer UPDATED_COLSKS = 2;

    private static final Integer DEFAULT_COTSHM = 1;
    private static final Integer UPDATED_COTSHM = 2;

    private static final Integer DEFAULT_CODSHM = 1;
    private static final Integer UPDATED_CODSHM = 2;

    private static final String DEFAULT_CONOTE_1 = "AAAAAAAAAA";
    private static final String UPDATED_CONOTE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CONOTE_2 = "AAAAAAAAAA";
    private static final String UPDATED_CONOTE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CONOTE_3 = "AAAAAAAAAA";
    private static final String UPDATED_CONOTE_3 = "BBBBBBBBBB";

    private static final Integer DEFAULT_COSKPS = 1;
    private static final Integer UPDATED_COSKPS = 2;

    private static final Integer DEFAULT_COTHLD = 1;
    private static final Integer UPDATED_COTHLD = 2;

    private static final String DEFAULT_CODIR_1 = "AAAAAAAAAA";
    private static final String UPDATED_CODIR_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CODIR_2 = "AAAAAAAAAA";
    private static final String UPDATED_CODIR_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CODIR_3 = "AAAAAAAAAA";
    private static final String UPDATED_CODIR_3 = "BBBBBBBBBB";

    private static final String DEFAULT_CODIR_4 = "AAAAAAAAAA";
    private static final String UPDATED_CODIR_4 = "BBBBBBBBBB";

    private static final String DEFAULT_CODIR_5 = "AAAAAAAAAA";
    private static final String UPDATED_CODIR_5 = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_COLMD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COLMD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COUID = "AAAAAAAAAA";
    private static final String UPDATED_COUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tbcoms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{cocode}";

    @Autowired
    private TBCOMSRepository tBCOMSRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTBCOMSMockMvc;

    private TBCOMS tBCOMS;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBCOMS createEntity(EntityManager em) {
        TBCOMS tBCOMS = new TBCOMS()
            .costs(DEFAULT_COSTS)
            .conam(DEFAULT_CONAM)
            .cocbei(DEFAULT_COCBEI)
            .conbei(DEFAULT_CONBEI)
            .cosat(DEFAULT_COSAT)
            .conom(DEFAULT_CONOM)
            .coisin(DEFAULT_COISIN)
            .conpwp(DEFAULT_CONPWP)
            .coseri(DEFAULT_COSERI)
            .colshm(DEFAULT_COLSHM)
            .colsks(DEFAULT_COLSKS)
            .cotshm(DEFAULT_COTSHM)
            .codshm(DEFAULT_CODSHM)
            .conote1(DEFAULT_CONOTE_1)
            .conote2(DEFAULT_CONOTE_2)
            .conote3(DEFAULT_CONOTE_3)
            .coskps(DEFAULT_COSKPS)
            .cothld(DEFAULT_COTHLD)
            .codir1(DEFAULT_CODIR_1)
            .codir2(DEFAULT_CODIR_2)
            .codir3(DEFAULT_CODIR_3)
            .codir4(DEFAULT_CODIR_4)
            .codir5(DEFAULT_CODIR_5)
            .colmd(DEFAULT_COLMD)
            .couid(DEFAULT_COUID);
        return tBCOMS;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBCOMS createUpdatedEntity(EntityManager em) {
        TBCOMS tBCOMS = new TBCOMS()
            .costs(UPDATED_COSTS)
            .conam(UPDATED_CONAM)
            .cocbei(UPDATED_COCBEI)
            .conbei(UPDATED_CONBEI)
            .cosat(UPDATED_COSAT)
            .conom(UPDATED_CONOM)
            .coisin(UPDATED_COISIN)
            .conpwp(UPDATED_CONPWP)
            .coseri(UPDATED_COSERI)
            .colshm(UPDATED_COLSHM)
            .colsks(UPDATED_COLSKS)
            .cotshm(UPDATED_COTSHM)
            .codshm(UPDATED_CODSHM)
            .conote1(UPDATED_CONOTE_1)
            .conote2(UPDATED_CONOTE_2)
            .conote3(UPDATED_CONOTE_3)
            .coskps(UPDATED_COSKPS)
            .cothld(UPDATED_COTHLD)
            .codir1(UPDATED_CODIR_1)
            .codir2(UPDATED_CODIR_2)
            .codir3(UPDATED_CODIR_3)
            .codir4(UPDATED_CODIR_4)
            .codir5(UPDATED_CODIR_5)
            .colmd(UPDATED_COLMD)
            .couid(UPDATED_COUID);
        return tBCOMS;
    }

    @BeforeEach
    public void initTest() {
        tBCOMS = createEntity(em);
    }

    @Test
    @Transactional
    void createTBCOMS() throws Exception {
        int databaseSizeBeforeCreate = tBCOMSRepository.findAll().size();
        // Create the TBCOMS
        restTBCOMSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isCreated());

        // Validate the TBCOMS in the database
        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeCreate + 1);
        TBCOMS testTBCOMS = tBCOMSList.get(tBCOMSList.size() - 1);
        assertThat(testTBCOMS.getCosts()).isEqualTo(DEFAULT_COSTS);
        assertThat(testTBCOMS.getConam()).isEqualTo(DEFAULT_CONAM);
        assertThat(testTBCOMS.getCocbei()).isEqualTo(DEFAULT_COCBEI);
        assertThat(testTBCOMS.getConbei()).isEqualTo(DEFAULT_CONBEI);
        assertThat(testTBCOMS.getCosat()).isEqualTo(DEFAULT_COSAT);
        assertThat(testTBCOMS.getConom()).isEqualTo(DEFAULT_CONOM);
        assertThat(testTBCOMS.getCoisin()).isEqualTo(DEFAULT_COISIN);
        assertThat(testTBCOMS.getConpwp()).isEqualTo(DEFAULT_CONPWP);
        assertThat(testTBCOMS.getCoseri()).isEqualTo(DEFAULT_COSERI);
        assertThat(testTBCOMS.getColshm()).isEqualTo(DEFAULT_COLSHM);
        assertThat(testTBCOMS.getColsks()).isEqualTo(DEFAULT_COLSKS);
        assertThat(testTBCOMS.getCotshm()).isEqualTo(DEFAULT_COTSHM);
        assertThat(testTBCOMS.getCodshm()).isEqualTo(DEFAULT_CODSHM);
        assertThat(testTBCOMS.getConote1()).isEqualTo(DEFAULT_CONOTE_1);
        assertThat(testTBCOMS.getConote2()).isEqualTo(DEFAULT_CONOTE_2);
        assertThat(testTBCOMS.getConote3()).isEqualTo(DEFAULT_CONOTE_3);
        assertThat(testTBCOMS.getCoskps()).isEqualTo(DEFAULT_COSKPS);
        assertThat(testTBCOMS.getCothld()).isEqualTo(DEFAULT_COTHLD);
        assertThat(testTBCOMS.getCodir1()).isEqualTo(DEFAULT_CODIR_1);
        assertThat(testTBCOMS.getCodir2()).isEqualTo(DEFAULT_CODIR_2);
        assertThat(testTBCOMS.getCodir3()).isEqualTo(DEFAULT_CODIR_3);
        assertThat(testTBCOMS.getCodir4()).isEqualTo(DEFAULT_CODIR_4);
        assertThat(testTBCOMS.getCodir5()).isEqualTo(DEFAULT_CODIR_5);
        assertThat(testTBCOMS.getColmd()).isEqualTo(DEFAULT_COLMD);
        assertThat(testTBCOMS.getCouid()).isEqualTo(DEFAULT_COUID);
    }

    @Test
    @Transactional
    void createTBCOMSWithExistingId() throws Exception {
        // Create the TBCOMS with an existing ID
        tBCOMS.setCocode("existing_id");

        int databaseSizeBeforeCreate = tBCOMSRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTBCOMSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isBadRequest());

        // Validate the TBCOMS in the database
        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCostsIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMSRepository.findAll().size();
        // set the field null
        tBCOMS.setCosts(null);

        // Create the TBCOMS, which fails.

        restTBCOMSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isBadRequest());

        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConamIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMSRepository.findAll().size();
        // set the field null
        tBCOMS.setConam(null);

        // Create the TBCOMS, which fails.

        restTBCOMSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isBadRequest());

        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCocbeiIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMSRepository.findAll().size();
        // set the field null
        tBCOMS.setCocbei(null);

        // Create the TBCOMS, which fails.

        restTBCOMSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isBadRequest());

        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConbeiIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMSRepository.findAll().size();
        // set the field null
        tBCOMS.setConbei(null);

        // Create the TBCOMS, which fails.

        restTBCOMSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isBadRequest());

        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCosatIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMSRepository.findAll().size();
        // set the field null
        tBCOMS.setCosat(null);

        // Create the TBCOMS, which fails.

        restTBCOMSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isBadRequest());

        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConomIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMSRepository.findAll().size();
        // set the field null
        tBCOMS.setConom(null);

        // Create the TBCOMS, which fails.

        restTBCOMSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isBadRequest());

        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCoisinIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMSRepository.findAll().size();
        // set the field null
        tBCOMS.setCoisin(null);

        // Create the TBCOMS, which fails.

        restTBCOMSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isBadRequest());

        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConpwpIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMSRepository.findAll().size();
        // set the field null
        tBCOMS.setConpwp(null);

        // Create the TBCOMS, which fails.

        restTBCOMSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isBadRequest());

        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCoseriIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMSRepository.findAll().size();
        // set the field null
        tBCOMS.setCoseri(null);

        // Create the TBCOMS, which fails.

        restTBCOMSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isBadRequest());

        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkColshmIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMSRepository.findAll().size();
        // set the field null
        tBCOMS.setColshm(null);

        // Create the TBCOMS, which fails.

        restTBCOMSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isBadRequest());

        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkColsksIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMSRepository.findAll().size();
        // set the field null
        tBCOMS.setColsks(null);

        // Create the TBCOMS, which fails.

        restTBCOMSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isBadRequest());

        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCotshmIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMSRepository.findAll().size();
        // set the field null
        tBCOMS.setCotshm(null);

        // Create the TBCOMS, which fails.

        restTBCOMSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isBadRequest());

        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodshmIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMSRepository.findAll().size();
        // set the field null
        tBCOMS.setCodshm(null);

        // Create the TBCOMS, which fails.

        restTBCOMSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isBadRequest());

        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCoskpsIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMSRepository.findAll().size();
        // set the field null
        tBCOMS.setCoskps(null);

        // Create the TBCOMS, which fails.

        restTBCOMSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isBadRequest());

        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCothldIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMSRepository.findAll().size();
        // set the field null
        tBCOMS.setCothld(null);

        // Create the TBCOMS, which fails.

        restTBCOMSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isBadRequest());

        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkColmdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMSRepository.findAll().size();
        // set the field null
        tBCOMS.setColmd(null);

        // Create the TBCOMS, which fails.

        restTBCOMSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isBadRequest());

        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCouidIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMSRepository.findAll().size();
        // set the field null
        tBCOMS.setCouid(null);

        // Create the TBCOMS, which fails.

        restTBCOMSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isBadRequest());

        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTBCOMS() throws Exception {
        // Initialize the database
        tBCOMS.setCocode(UUID.randomUUID().toString());
        tBCOMSRepository.saveAndFlush(tBCOMS);

        // Get all the tBCOMSList
        restTBCOMSMockMvc
            .perform(get(ENTITY_API_URL + "?sort=cocode,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].cocode").value(hasItem(tBCOMS.getCocode())))
            .andExpect(jsonPath("$.[*].costs").value(hasItem(DEFAULT_COSTS)))
            .andExpect(jsonPath("$.[*].conam").value(hasItem(DEFAULT_CONAM)))
            .andExpect(jsonPath("$.[*].cocbei").value(hasItem(DEFAULT_COCBEI)))
            .andExpect(jsonPath("$.[*].conbei").value(hasItem(DEFAULT_CONBEI)))
            .andExpect(jsonPath("$.[*].cosat").value(hasItem(DEFAULT_COSAT)))
            .andExpect(jsonPath("$.[*].conom").value(hasItem(DEFAULT_CONOM)))
            .andExpect(jsonPath("$.[*].coisin").value(hasItem(DEFAULT_COISIN)))
            .andExpect(jsonPath("$.[*].conpwp").value(hasItem(DEFAULT_CONPWP)))
            .andExpect(jsonPath("$.[*].coseri").value(hasItem(DEFAULT_COSERI)))
            .andExpect(jsonPath("$.[*].colshm").value(hasItem(DEFAULT_COLSHM)))
            .andExpect(jsonPath("$.[*].colsks").value(hasItem(DEFAULT_COLSKS)))
            .andExpect(jsonPath("$.[*].cotshm").value(hasItem(DEFAULT_COTSHM)))
            .andExpect(jsonPath("$.[*].codshm").value(hasItem(DEFAULT_CODSHM)))
            .andExpect(jsonPath("$.[*].conote1").value(hasItem(DEFAULT_CONOTE_1)))
            .andExpect(jsonPath("$.[*].conote2").value(hasItem(DEFAULT_CONOTE_2)))
            .andExpect(jsonPath("$.[*].conote3").value(hasItem(DEFAULT_CONOTE_3)))
            .andExpect(jsonPath("$.[*].coskps").value(hasItem(DEFAULT_COSKPS)))
            .andExpect(jsonPath("$.[*].cothld").value(hasItem(DEFAULT_COTHLD)))
            .andExpect(jsonPath("$.[*].codir1").value(hasItem(DEFAULT_CODIR_1)))
            .andExpect(jsonPath("$.[*].codir2").value(hasItem(DEFAULT_CODIR_2)))
            .andExpect(jsonPath("$.[*].codir3").value(hasItem(DEFAULT_CODIR_3)))
            .andExpect(jsonPath("$.[*].codir4").value(hasItem(DEFAULT_CODIR_4)))
            .andExpect(jsonPath("$.[*].codir5").value(hasItem(DEFAULT_CODIR_5)))
            .andExpect(jsonPath("$.[*].colmd").value(hasItem(DEFAULT_COLMD.toString())))
            .andExpect(jsonPath("$.[*].couid").value(hasItem(DEFAULT_COUID)));
    }

    @Test
    @Transactional
    void getTBCOMS() throws Exception {
        // Initialize the database
        tBCOMS.setCocode(UUID.randomUUID().toString());
        tBCOMSRepository.saveAndFlush(tBCOMS);

        // Get the tBCOMS
        restTBCOMSMockMvc
            .perform(get(ENTITY_API_URL_ID, tBCOMS.getCocode()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.cocode").value(tBCOMS.getCocode()))
            .andExpect(jsonPath("$.costs").value(DEFAULT_COSTS))
            .andExpect(jsonPath("$.conam").value(DEFAULT_CONAM))
            .andExpect(jsonPath("$.cocbei").value(DEFAULT_COCBEI))
            .andExpect(jsonPath("$.conbei").value(DEFAULT_CONBEI))
            .andExpect(jsonPath("$.cosat").value(DEFAULT_COSAT))
            .andExpect(jsonPath("$.conom").value(DEFAULT_CONOM))
            .andExpect(jsonPath("$.coisin").value(DEFAULT_COISIN))
            .andExpect(jsonPath("$.conpwp").value(DEFAULT_CONPWP))
            .andExpect(jsonPath("$.coseri").value(DEFAULT_COSERI))
            .andExpect(jsonPath("$.colshm").value(DEFAULT_COLSHM))
            .andExpect(jsonPath("$.colsks").value(DEFAULT_COLSKS))
            .andExpect(jsonPath("$.cotshm").value(DEFAULT_COTSHM))
            .andExpect(jsonPath("$.codshm").value(DEFAULT_CODSHM))
            .andExpect(jsonPath("$.conote1").value(DEFAULT_CONOTE_1))
            .andExpect(jsonPath("$.conote2").value(DEFAULT_CONOTE_2))
            .andExpect(jsonPath("$.conote3").value(DEFAULT_CONOTE_3))
            .andExpect(jsonPath("$.coskps").value(DEFAULT_COSKPS))
            .andExpect(jsonPath("$.cothld").value(DEFAULT_COTHLD))
            .andExpect(jsonPath("$.codir1").value(DEFAULT_CODIR_1))
            .andExpect(jsonPath("$.codir2").value(DEFAULT_CODIR_2))
            .andExpect(jsonPath("$.codir3").value(DEFAULT_CODIR_3))
            .andExpect(jsonPath("$.codir4").value(DEFAULT_CODIR_4))
            .andExpect(jsonPath("$.codir5").value(DEFAULT_CODIR_5))
            .andExpect(jsonPath("$.colmd").value(DEFAULT_COLMD.toString()))
            .andExpect(jsonPath("$.couid").value(DEFAULT_COUID));
    }

    @Test
    @Transactional
    void getNonExistingTBCOMS() throws Exception {
        // Get the tBCOMS
        restTBCOMSMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTBCOMS() throws Exception {
        // Initialize the database
        tBCOMS.setCocode(UUID.randomUUID().toString());
        tBCOMSRepository.saveAndFlush(tBCOMS);

        int databaseSizeBeforeUpdate = tBCOMSRepository.findAll().size();

        // Update the tBCOMS
        TBCOMS updatedTBCOMS = tBCOMSRepository.findById(tBCOMS.getCocode()).get();
        // Disconnect from session so that the updates on updatedTBCOMS are not directly saved in db
        em.detach(updatedTBCOMS);
        updatedTBCOMS
            .costs(UPDATED_COSTS)
            .conam(UPDATED_CONAM)
            .cocbei(UPDATED_COCBEI)
            .conbei(UPDATED_CONBEI)
            .cosat(UPDATED_COSAT)
            .conom(UPDATED_CONOM)
            .coisin(UPDATED_COISIN)
            .conpwp(UPDATED_CONPWP)
            .coseri(UPDATED_COSERI)
            .colshm(UPDATED_COLSHM)
            .colsks(UPDATED_COLSKS)
            .cotshm(UPDATED_COTSHM)
            .codshm(UPDATED_CODSHM)
            .conote1(UPDATED_CONOTE_1)
            .conote2(UPDATED_CONOTE_2)
            .conote3(UPDATED_CONOTE_3)
            .coskps(UPDATED_COSKPS)
            .cothld(UPDATED_COTHLD)
            .codir1(UPDATED_CODIR_1)
            .codir2(UPDATED_CODIR_2)
            .codir3(UPDATED_CODIR_3)
            .codir4(UPDATED_CODIR_4)
            .codir5(UPDATED_CODIR_5)
            .colmd(UPDATED_COLMD)
            .couid(UPDATED_COUID);

        restTBCOMSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTBCOMS.getCocode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTBCOMS))
            )
            .andExpect(status().isOk());

        // Validate the TBCOMS in the database
        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeUpdate);
        TBCOMS testTBCOMS = tBCOMSList.get(tBCOMSList.size() - 1);
        assertThat(testTBCOMS.getCosts()).isEqualTo(UPDATED_COSTS);
        assertThat(testTBCOMS.getConam()).isEqualTo(UPDATED_CONAM);
        assertThat(testTBCOMS.getCocbei()).isEqualTo(UPDATED_COCBEI);
        assertThat(testTBCOMS.getConbei()).isEqualTo(UPDATED_CONBEI);
        assertThat(testTBCOMS.getCosat()).isEqualTo(UPDATED_COSAT);
        assertThat(testTBCOMS.getConom()).isEqualTo(UPDATED_CONOM);
        assertThat(testTBCOMS.getCoisin()).isEqualTo(UPDATED_COISIN);
        assertThat(testTBCOMS.getConpwp()).isEqualTo(UPDATED_CONPWP);
        assertThat(testTBCOMS.getCoseri()).isEqualTo(UPDATED_COSERI);
        assertThat(testTBCOMS.getColshm()).isEqualTo(UPDATED_COLSHM);
        assertThat(testTBCOMS.getColsks()).isEqualTo(UPDATED_COLSKS);
        assertThat(testTBCOMS.getCotshm()).isEqualTo(UPDATED_COTSHM);
        assertThat(testTBCOMS.getCodshm()).isEqualTo(UPDATED_CODSHM);
        assertThat(testTBCOMS.getConote1()).isEqualTo(UPDATED_CONOTE_1);
        assertThat(testTBCOMS.getConote2()).isEqualTo(UPDATED_CONOTE_2);
        assertThat(testTBCOMS.getConote3()).isEqualTo(UPDATED_CONOTE_3);
        assertThat(testTBCOMS.getCoskps()).isEqualTo(UPDATED_COSKPS);
        assertThat(testTBCOMS.getCothld()).isEqualTo(UPDATED_COTHLD);
        assertThat(testTBCOMS.getCodir1()).isEqualTo(UPDATED_CODIR_1);
        assertThat(testTBCOMS.getCodir2()).isEqualTo(UPDATED_CODIR_2);
        assertThat(testTBCOMS.getCodir3()).isEqualTo(UPDATED_CODIR_3);
        assertThat(testTBCOMS.getCodir4()).isEqualTo(UPDATED_CODIR_4);
        assertThat(testTBCOMS.getCodir5()).isEqualTo(UPDATED_CODIR_5);
        assertThat(testTBCOMS.getColmd()).isEqualTo(UPDATED_COLMD);
        assertThat(testTBCOMS.getCouid()).isEqualTo(UPDATED_COUID);
    }

    @Test
    @Transactional
    void putNonExistingTBCOMS() throws Exception {
        int databaseSizeBeforeUpdate = tBCOMSRepository.findAll().size();
        tBCOMS.setCocode(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBCOMSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tBCOMS.getCocode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBCOMS))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBCOMS in the database
        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTBCOMS() throws Exception {
        int databaseSizeBeforeUpdate = tBCOMSRepository.findAll().size();
        tBCOMS.setCocode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBCOMSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBCOMS))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBCOMS in the database
        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTBCOMS() throws Exception {
        int databaseSizeBeforeUpdate = tBCOMSRepository.findAll().size();
        tBCOMS.setCocode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBCOMSMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBCOMS in the database
        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTBCOMSWithPatch() throws Exception {
        // Initialize the database
        tBCOMS.setCocode(UUID.randomUUID().toString());
        tBCOMSRepository.saveAndFlush(tBCOMS);

        int databaseSizeBeforeUpdate = tBCOMSRepository.findAll().size();

        // Update the tBCOMS using partial update
        TBCOMS partialUpdatedTBCOMS = new TBCOMS();
        partialUpdatedTBCOMS.setCocode(tBCOMS.getCocode());

        partialUpdatedTBCOMS
            .costs(UPDATED_COSTS)
            .cocbei(UPDATED_COCBEI)
            .cosat(UPDATED_COSAT)
            .colshm(UPDATED_COLSHM)
            .codshm(UPDATED_CODSHM)
            .conote2(UPDATED_CONOTE_2)
            .coskps(UPDATED_COSKPS)
            .codir1(UPDATED_CODIR_1)
            .codir3(UPDATED_CODIR_3)
            .codir4(UPDATED_CODIR_4)
            .codir5(UPDATED_CODIR_5);

        restTBCOMSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBCOMS.getCocode())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBCOMS))
            )
            .andExpect(status().isOk());

        // Validate the TBCOMS in the database
        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeUpdate);
        TBCOMS testTBCOMS = tBCOMSList.get(tBCOMSList.size() - 1);
        assertThat(testTBCOMS.getCosts()).isEqualTo(UPDATED_COSTS);
        assertThat(testTBCOMS.getConam()).isEqualTo(DEFAULT_CONAM);
        assertThat(testTBCOMS.getCocbei()).isEqualTo(UPDATED_COCBEI);
        assertThat(testTBCOMS.getConbei()).isEqualTo(DEFAULT_CONBEI);
        assertThat(testTBCOMS.getCosat()).isEqualTo(UPDATED_COSAT);
        assertThat(testTBCOMS.getConom()).isEqualTo(DEFAULT_CONOM);
        assertThat(testTBCOMS.getCoisin()).isEqualTo(DEFAULT_COISIN);
        assertThat(testTBCOMS.getConpwp()).isEqualTo(DEFAULT_CONPWP);
        assertThat(testTBCOMS.getCoseri()).isEqualTo(DEFAULT_COSERI);
        assertThat(testTBCOMS.getColshm()).isEqualTo(UPDATED_COLSHM);
        assertThat(testTBCOMS.getColsks()).isEqualTo(DEFAULT_COLSKS);
        assertThat(testTBCOMS.getCotshm()).isEqualTo(DEFAULT_COTSHM);
        assertThat(testTBCOMS.getCodshm()).isEqualTo(UPDATED_CODSHM);
        assertThat(testTBCOMS.getConote1()).isEqualTo(DEFAULT_CONOTE_1);
        assertThat(testTBCOMS.getConote2()).isEqualTo(UPDATED_CONOTE_2);
        assertThat(testTBCOMS.getConote3()).isEqualTo(DEFAULT_CONOTE_3);
        assertThat(testTBCOMS.getCoskps()).isEqualTo(UPDATED_COSKPS);
        assertThat(testTBCOMS.getCothld()).isEqualTo(DEFAULT_COTHLD);
        assertThat(testTBCOMS.getCodir1()).isEqualTo(UPDATED_CODIR_1);
        assertThat(testTBCOMS.getCodir2()).isEqualTo(DEFAULT_CODIR_2);
        assertThat(testTBCOMS.getCodir3()).isEqualTo(UPDATED_CODIR_3);
        assertThat(testTBCOMS.getCodir4()).isEqualTo(UPDATED_CODIR_4);
        assertThat(testTBCOMS.getCodir5()).isEqualTo(UPDATED_CODIR_5);
        assertThat(testTBCOMS.getColmd()).isEqualTo(DEFAULT_COLMD);
        assertThat(testTBCOMS.getCouid()).isEqualTo(DEFAULT_COUID);
    }

    @Test
    @Transactional
    void fullUpdateTBCOMSWithPatch() throws Exception {
        // Initialize the database
        tBCOMS.setCocode(UUID.randomUUID().toString());
        tBCOMSRepository.saveAndFlush(tBCOMS);

        int databaseSizeBeforeUpdate = tBCOMSRepository.findAll().size();

        // Update the tBCOMS using partial update
        TBCOMS partialUpdatedTBCOMS = new TBCOMS();
        partialUpdatedTBCOMS.setCocode(tBCOMS.getCocode());

        partialUpdatedTBCOMS
            .costs(UPDATED_COSTS)
            .conam(UPDATED_CONAM)
            .cocbei(UPDATED_COCBEI)
            .conbei(UPDATED_CONBEI)
            .cosat(UPDATED_COSAT)
            .conom(UPDATED_CONOM)
            .coisin(UPDATED_COISIN)
            .conpwp(UPDATED_CONPWP)
            .coseri(UPDATED_COSERI)
            .colshm(UPDATED_COLSHM)
            .colsks(UPDATED_COLSKS)
            .cotshm(UPDATED_COTSHM)
            .codshm(UPDATED_CODSHM)
            .conote1(UPDATED_CONOTE_1)
            .conote2(UPDATED_CONOTE_2)
            .conote3(UPDATED_CONOTE_3)
            .coskps(UPDATED_COSKPS)
            .cothld(UPDATED_COTHLD)
            .codir1(UPDATED_CODIR_1)
            .codir2(UPDATED_CODIR_2)
            .codir3(UPDATED_CODIR_3)
            .codir4(UPDATED_CODIR_4)
            .codir5(UPDATED_CODIR_5)
            .colmd(UPDATED_COLMD)
            .couid(UPDATED_COUID);

        restTBCOMSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBCOMS.getCocode())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBCOMS))
            )
            .andExpect(status().isOk());

        // Validate the TBCOMS in the database
        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeUpdate);
        TBCOMS testTBCOMS = tBCOMSList.get(tBCOMSList.size() - 1);
        assertThat(testTBCOMS.getCosts()).isEqualTo(UPDATED_COSTS);
        assertThat(testTBCOMS.getConam()).isEqualTo(UPDATED_CONAM);
        assertThat(testTBCOMS.getCocbei()).isEqualTo(UPDATED_COCBEI);
        assertThat(testTBCOMS.getConbei()).isEqualTo(UPDATED_CONBEI);
        assertThat(testTBCOMS.getCosat()).isEqualTo(UPDATED_COSAT);
        assertThat(testTBCOMS.getConom()).isEqualTo(UPDATED_CONOM);
        assertThat(testTBCOMS.getCoisin()).isEqualTo(UPDATED_COISIN);
        assertThat(testTBCOMS.getConpwp()).isEqualTo(UPDATED_CONPWP);
        assertThat(testTBCOMS.getCoseri()).isEqualTo(UPDATED_COSERI);
        assertThat(testTBCOMS.getColshm()).isEqualTo(UPDATED_COLSHM);
        assertThat(testTBCOMS.getColsks()).isEqualTo(UPDATED_COLSKS);
        assertThat(testTBCOMS.getCotshm()).isEqualTo(UPDATED_COTSHM);
        assertThat(testTBCOMS.getCodshm()).isEqualTo(UPDATED_CODSHM);
        assertThat(testTBCOMS.getConote1()).isEqualTo(UPDATED_CONOTE_1);
        assertThat(testTBCOMS.getConote2()).isEqualTo(UPDATED_CONOTE_2);
        assertThat(testTBCOMS.getConote3()).isEqualTo(UPDATED_CONOTE_3);
        assertThat(testTBCOMS.getCoskps()).isEqualTo(UPDATED_COSKPS);
        assertThat(testTBCOMS.getCothld()).isEqualTo(UPDATED_COTHLD);
        assertThat(testTBCOMS.getCodir1()).isEqualTo(UPDATED_CODIR_1);
        assertThat(testTBCOMS.getCodir2()).isEqualTo(UPDATED_CODIR_2);
        assertThat(testTBCOMS.getCodir3()).isEqualTo(UPDATED_CODIR_3);
        assertThat(testTBCOMS.getCodir4()).isEqualTo(UPDATED_CODIR_4);
        assertThat(testTBCOMS.getCodir5()).isEqualTo(UPDATED_CODIR_5);
        assertThat(testTBCOMS.getColmd()).isEqualTo(UPDATED_COLMD);
        assertThat(testTBCOMS.getCouid()).isEqualTo(UPDATED_COUID);
    }

    @Test
    @Transactional
    void patchNonExistingTBCOMS() throws Exception {
        int databaseSizeBeforeUpdate = tBCOMSRepository.findAll().size();
        tBCOMS.setCocode(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBCOMSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tBCOMS.getCocode())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBCOMS))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBCOMS in the database
        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTBCOMS() throws Exception {
        int databaseSizeBeforeUpdate = tBCOMSRepository.findAll().size();
        tBCOMS.setCocode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBCOMSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBCOMS))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBCOMS in the database
        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTBCOMS() throws Exception {
        int databaseSizeBeforeUpdate = tBCOMSRepository.findAll().size();
        tBCOMS.setCocode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBCOMSMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tBCOMS)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBCOMS in the database
        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTBCOMS() throws Exception {
        // Initialize the database
        tBCOMS.setCocode(UUID.randomUUID().toString());
        tBCOMSRepository.saveAndFlush(tBCOMS);

        int databaseSizeBeforeDelete = tBCOMSRepository.findAll().size();

        // Delete the tBCOMS
        restTBCOMSMockMvc
            .perform(delete(ENTITY_API_URL_ID, tBCOMS.getCocode()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TBCOMS> tBCOMSList = tBCOMSRepository.findAll();
        assertThat(tBCOMSList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
