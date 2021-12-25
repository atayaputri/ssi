package com.mycompany.ssi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.ssi.IntegrationTest;
import com.mycompany.ssi.domain.TBCOMFO;
import com.mycompany.ssi.repository.TBCOMFORepository;
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
 * Integration tests for the {@link TBCOMFOResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TBCOMFOResourceIT {

    private static final String DEFAULT_COSTS = "AAAAAAAAAA";
    private static final String UPDATED_COSTS = "BBBBBBBBBB";

    private static final String DEFAULT_COCODE = "AAAAAAAAAA";
    private static final String UPDATED_COCODE = "BBBBBBBBBB";

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

    private static final String DEFAULT_COSERI = "AAAAAAAAAA";
    private static final String UPDATED_COSERI = "BBBBBBBBBB";

    private static final String DEFAULT_CODIR = "AAAAAAAAAA";
    private static final String UPDATED_CODIR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_COLMD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COLMD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COUID = "AAAAAAAAAA";
    private static final String UPDATED_COUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tbcomfos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TBCOMFORepository tBCOMFORepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTBCOMFOMockMvc;

    private TBCOMFO tBCOMFO;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBCOMFO createEntity(EntityManager em) {
        TBCOMFO tBCOMFO = new TBCOMFO()
            .costs(DEFAULT_COSTS)
            .cocode(DEFAULT_COCODE)
            .conam(DEFAULT_CONAM)
            .cocbei(DEFAULT_COCBEI)
            .conbei(DEFAULT_CONBEI)
            .cosat(DEFAULT_COSAT)
            .conom(DEFAULT_CONOM)
            .coseri(DEFAULT_COSERI)
            .codir(DEFAULT_CODIR)
            .colmd(DEFAULT_COLMD)
            .couid(DEFAULT_COUID);
        return tBCOMFO;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBCOMFO createUpdatedEntity(EntityManager em) {
        TBCOMFO tBCOMFO = new TBCOMFO()
            .costs(UPDATED_COSTS)
            .cocode(UPDATED_COCODE)
            .conam(UPDATED_CONAM)
            .cocbei(UPDATED_COCBEI)
            .conbei(UPDATED_CONBEI)
            .cosat(UPDATED_COSAT)
            .conom(UPDATED_CONOM)
            .coseri(UPDATED_COSERI)
            .codir(UPDATED_CODIR)
            .colmd(UPDATED_COLMD)
            .couid(UPDATED_COUID);
        return tBCOMFO;
    }

    @BeforeEach
    public void initTest() {
        tBCOMFO = createEntity(em);
    }

    @Test
    @Transactional
    void createTBCOMFO() throws Exception {
        int databaseSizeBeforeCreate = tBCOMFORepository.findAll().size();
        // Create the TBCOMFO
        restTBCOMFOMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMFO)))
            .andExpect(status().isCreated());

        // Validate the TBCOMFO in the database
        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeCreate + 1);
        TBCOMFO testTBCOMFO = tBCOMFOList.get(tBCOMFOList.size() - 1);
        assertThat(testTBCOMFO.getCosts()).isEqualTo(DEFAULT_COSTS);
        assertThat(testTBCOMFO.getCocode()).isEqualTo(DEFAULT_COCODE);
        assertThat(testTBCOMFO.getConam()).isEqualTo(DEFAULT_CONAM);
        assertThat(testTBCOMFO.getCocbei()).isEqualTo(DEFAULT_COCBEI);
        assertThat(testTBCOMFO.getConbei()).isEqualTo(DEFAULT_CONBEI);
        assertThat(testTBCOMFO.getCosat()).isEqualTo(DEFAULT_COSAT);
        assertThat(testTBCOMFO.getConom()).isEqualTo(DEFAULT_CONOM);
        assertThat(testTBCOMFO.getCoseri()).isEqualTo(DEFAULT_COSERI);
        assertThat(testTBCOMFO.getCodir()).isEqualTo(DEFAULT_CODIR);
        assertThat(testTBCOMFO.getColmd()).isEqualTo(DEFAULT_COLMD);
        assertThat(testTBCOMFO.getCouid()).isEqualTo(DEFAULT_COUID);
    }

    @Test
    @Transactional
    void createTBCOMFOWithExistingId() throws Exception {
        // Create the TBCOMFO with an existing ID
        tBCOMFO.setId(1L);

        int databaseSizeBeforeCreate = tBCOMFORepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTBCOMFOMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMFO)))
            .andExpect(status().isBadRequest());

        // Validate the TBCOMFO in the database
        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCostsIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMFORepository.findAll().size();
        // set the field null
        tBCOMFO.setCosts(null);

        // Create the TBCOMFO, which fails.

        restTBCOMFOMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMFO)))
            .andExpect(status().isBadRequest());

        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCocodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMFORepository.findAll().size();
        // set the field null
        tBCOMFO.setCocode(null);

        // Create the TBCOMFO, which fails.

        restTBCOMFOMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMFO)))
            .andExpect(status().isBadRequest());

        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConamIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMFORepository.findAll().size();
        // set the field null
        tBCOMFO.setConam(null);

        // Create the TBCOMFO, which fails.

        restTBCOMFOMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMFO)))
            .andExpect(status().isBadRequest());

        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCocbeiIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMFORepository.findAll().size();
        // set the field null
        tBCOMFO.setCocbei(null);

        // Create the TBCOMFO, which fails.

        restTBCOMFOMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMFO)))
            .andExpect(status().isBadRequest());

        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConbeiIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMFORepository.findAll().size();
        // set the field null
        tBCOMFO.setConbei(null);

        // Create the TBCOMFO, which fails.

        restTBCOMFOMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMFO)))
            .andExpect(status().isBadRequest());

        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCosatIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMFORepository.findAll().size();
        // set the field null
        tBCOMFO.setCosat(null);

        // Create the TBCOMFO, which fails.

        restTBCOMFOMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMFO)))
            .andExpect(status().isBadRequest());

        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConomIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMFORepository.findAll().size();
        // set the field null
        tBCOMFO.setConom(null);

        // Create the TBCOMFO, which fails.

        restTBCOMFOMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMFO)))
            .andExpect(status().isBadRequest());

        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCoseriIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMFORepository.findAll().size();
        // set the field null
        tBCOMFO.setCoseri(null);

        // Create the TBCOMFO, which fails.

        restTBCOMFOMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMFO)))
            .andExpect(status().isBadRequest());

        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodirIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMFORepository.findAll().size();
        // set the field null
        tBCOMFO.setCodir(null);

        // Create the TBCOMFO, which fails.

        restTBCOMFOMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMFO)))
            .andExpect(status().isBadRequest());

        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkColmdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMFORepository.findAll().size();
        // set the field null
        tBCOMFO.setColmd(null);

        // Create the TBCOMFO, which fails.

        restTBCOMFOMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMFO)))
            .andExpect(status().isBadRequest());

        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCouidIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBCOMFORepository.findAll().size();
        // set the field null
        tBCOMFO.setCouid(null);

        // Create the TBCOMFO, which fails.

        restTBCOMFOMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMFO)))
            .andExpect(status().isBadRequest());

        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTBCOMFOS() throws Exception {
        // Initialize the database
        tBCOMFORepository.saveAndFlush(tBCOMFO);

        // Get all the tBCOMFOList
        restTBCOMFOMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tBCOMFO.getId().intValue())))
            .andExpect(jsonPath("$.[*].costs").value(hasItem(DEFAULT_COSTS)))
            .andExpect(jsonPath("$.[*].cocode").value(hasItem(DEFAULT_COCODE)))
            .andExpect(jsonPath("$.[*].conam").value(hasItem(DEFAULT_CONAM)))
            .andExpect(jsonPath("$.[*].cocbei").value(hasItem(DEFAULT_COCBEI)))
            .andExpect(jsonPath("$.[*].conbei").value(hasItem(DEFAULT_CONBEI)))
            .andExpect(jsonPath("$.[*].cosat").value(hasItem(DEFAULT_COSAT)))
            .andExpect(jsonPath("$.[*].conom").value(hasItem(DEFAULT_CONOM)))
            .andExpect(jsonPath("$.[*].coseri").value(hasItem(DEFAULT_COSERI)))
            .andExpect(jsonPath("$.[*].codir").value(hasItem(DEFAULT_CODIR)))
            .andExpect(jsonPath("$.[*].colmd").value(hasItem(DEFAULT_COLMD.toString())))
            .andExpect(jsonPath("$.[*].couid").value(hasItem(DEFAULT_COUID)));
    }

    @Test
    @Transactional
    void getTBCOMFO() throws Exception {
        // Initialize the database
        tBCOMFORepository.saveAndFlush(tBCOMFO);

        // Get the tBCOMFO
        restTBCOMFOMockMvc
            .perform(get(ENTITY_API_URL_ID, tBCOMFO.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tBCOMFO.getId().intValue()))
            .andExpect(jsonPath("$.costs").value(DEFAULT_COSTS))
            .andExpect(jsonPath("$.cocode").value(DEFAULT_COCODE))
            .andExpect(jsonPath("$.conam").value(DEFAULT_CONAM))
            .andExpect(jsonPath("$.cocbei").value(DEFAULT_COCBEI))
            .andExpect(jsonPath("$.conbei").value(DEFAULT_CONBEI))
            .andExpect(jsonPath("$.cosat").value(DEFAULT_COSAT))
            .andExpect(jsonPath("$.conom").value(DEFAULT_CONOM))
            .andExpect(jsonPath("$.coseri").value(DEFAULT_COSERI))
            .andExpect(jsonPath("$.codir").value(DEFAULT_CODIR))
            .andExpect(jsonPath("$.colmd").value(DEFAULT_COLMD.toString()))
            .andExpect(jsonPath("$.couid").value(DEFAULT_COUID));
    }

    @Test
    @Transactional
    void getNonExistingTBCOMFO() throws Exception {
        // Get the tBCOMFO
        restTBCOMFOMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTBCOMFO() throws Exception {
        // Initialize the database
        tBCOMFORepository.saveAndFlush(tBCOMFO);

        int databaseSizeBeforeUpdate = tBCOMFORepository.findAll().size();

        // Update the tBCOMFO
        TBCOMFO updatedTBCOMFO = tBCOMFORepository.findById(tBCOMFO.getId()).get();
        // Disconnect from session so that the updates on updatedTBCOMFO are not directly saved in db
        em.detach(updatedTBCOMFO);
        updatedTBCOMFO
            .costs(UPDATED_COSTS)
            .cocode(UPDATED_COCODE)
            .conam(UPDATED_CONAM)
            .cocbei(UPDATED_COCBEI)
            .conbei(UPDATED_CONBEI)
            .cosat(UPDATED_COSAT)
            .conom(UPDATED_CONOM)
            .coseri(UPDATED_COSERI)
            .codir(UPDATED_CODIR)
            .colmd(UPDATED_COLMD)
            .couid(UPDATED_COUID);

        restTBCOMFOMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTBCOMFO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTBCOMFO))
            )
            .andExpect(status().isOk());

        // Validate the TBCOMFO in the database
        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeUpdate);
        TBCOMFO testTBCOMFO = tBCOMFOList.get(tBCOMFOList.size() - 1);
        assertThat(testTBCOMFO.getCosts()).isEqualTo(UPDATED_COSTS);
        assertThat(testTBCOMFO.getCocode()).isEqualTo(UPDATED_COCODE);
        assertThat(testTBCOMFO.getConam()).isEqualTo(UPDATED_CONAM);
        assertThat(testTBCOMFO.getCocbei()).isEqualTo(UPDATED_COCBEI);
        assertThat(testTBCOMFO.getConbei()).isEqualTo(UPDATED_CONBEI);
        assertThat(testTBCOMFO.getCosat()).isEqualTo(UPDATED_COSAT);
        assertThat(testTBCOMFO.getConom()).isEqualTo(UPDATED_CONOM);
        assertThat(testTBCOMFO.getCoseri()).isEqualTo(UPDATED_COSERI);
        assertThat(testTBCOMFO.getCodir()).isEqualTo(UPDATED_CODIR);
        assertThat(testTBCOMFO.getColmd()).isEqualTo(UPDATED_COLMD);
        assertThat(testTBCOMFO.getCouid()).isEqualTo(UPDATED_COUID);
    }

    @Test
    @Transactional
    void putNonExistingTBCOMFO() throws Exception {
        int databaseSizeBeforeUpdate = tBCOMFORepository.findAll().size();
        tBCOMFO.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBCOMFOMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tBCOMFO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBCOMFO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBCOMFO in the database
        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTBCOMFO() throws Exception {
        int databaseSizeBeforeUpdate = tBCOMFORepository.findAll().size();
        tBCOMFO.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBCOMFOMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBCOMFO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBCOMFO in the database
        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTBCOMFO() throws Exception {
        int databaseSizeBeforeUpdate = tBCOMFORepository.findAll().size();
        tBCOMFO.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBCOMFOMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBCOMFO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBCOMFO in the database
        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTBCOMFOWithPatch() throws Exception {
        // Initialize the database
        tBCOMFORepository.saveAndFlush(tBCOMFO);

        int databaseSizeBeforeUpdate = tBCOMFORepository.findAll().size();

        // Update the tBCOMFO using partial update
        TBCOMFO partialUpdatedTBCOMFO = new TBCOMFO();
        partialUpdatedTBCOMFO.setId(tBCOMFO.getId());

        partialUpdatedTBCOMFO.cocode(UPDATED_COCODE).cosat(UPDATED_COSAT).coseri(UPDATED_COSERI).colmd(UPDATED_COLMD).couid(UPDATED_COUID);

        restTBCOMFOMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBCOMFO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBCOMFO))
            )
            .andExpect(status().isOk());

        // Validate the TBCOMFO in the database
        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeUpdate);
        TBCOMFO testTBCOMFO = tBCOMFOList.get(tBCOMFOList.size() - 1);
        assertThat(testTBCOMFO.getCosts()).isEqualTo(DEFAULT_COSTS);
        assertThat(testTBCOMFO.getCocode()).isEqualTo(UPDATED_COCODE);
        assertThat(testTBCOMFO.getConam()).isEqualTo(DEFAULT_CONAM);
        assertThat(testTBCOMFO.getCocbei()).isEqualTo(DEFAULT_COCBEI);
        assertThat(testTBCOMFO.getConbei()).isEqualTo(DEFAULT_CONBEI);
        assertThat(testTBCOMFO.getCosat()).isEqualTo(UPDATED_COSAT);
        assertThat(testTBCOMFO.getConom()).isEqualTo(DEFAULT_CONOM);
        assertThat(testTBCOMFO.getCoseri()).isEqualTo(UPDATED_COSERI);
        assertThat(testTBCOMFO.getCodir()).isEqualTo(DEFAULT_CODIR);
        assertThat(testTBCOMFO.getColmd()).isEqualTo(UPDATED_COLMD);
        assertThat(testTBCOMFO.getCouid()).isEqualTo(UPDATED_COUID);
    }

    @Test
    @Transactional
    void fullUpdateTBCOMFOWithPatch() throws Exception {
        // Initialize the database
        tBCOMFORepository.saveAndFlush(tBCOMFO);

        int databaseSizeBeforeUpdate = tBCOMFORepository.findAll().size();

        // Update the tBCOMFO using partial update
        TBCOMFO partialUpdatedTBCOMFO = new TBCOMFO();
        partialUpdatedTBCOMFO.setId(tBCOMFO.getId());

        partialUpdatedTBCOMFO
            .costs(UPDATED_COSTS)
            .cocode(UPDATED_COCODE)
            .conam(UPDATED_CONAM)
            .cocbei(UPDATED_COCBEI)
            .conbei(UPDATED_CONBEI)
            .cosat(UPDATED_COSAT)
            .conom(UPDATED_CONOM)
            .coseri(UPDATED_COSERI)
            .codir(UPDATED_CODIR)
            .colmd(UPDATED_COLMD)
            .couid(UPDATED_COUID);

        restTBCOMFOMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBCOMFO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBCOMFO))
            )
            .andExpect(status().isOk());

        // Validate the TBCOMFO in the database
        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeUpdate);
        TBCOMFO testTBCOMFO = tBCOMFOList.get(tBCOMFOList.size() - 1);
        assertThat(testTBCOMFO.getCosts()).isEqualTo(UPDATED_COSTS);
        assertThat(testTBCOMFO.getCocode()).isEqualTo(UPDATED_COCODE);
        assertThat(testTBCOMFO.getConam()).isEqualTo(UPDATED_CONAM);
        assertThat(testTBCOMFO.getCocbei()).isEqualTo(UPDATED_COCBEI);
        assertThat(testTBCOMFO.getConbei()).isEqualTo(UPDATED_CONBEI);
        assertThat(testTBCOMFO.getCosat()).isEqualTo(UPDATED_COSAT);
        assertThat(testTBCOMFO.getConom()).isEqualTo(UPDATED_CONOM);
        assertThat(testTBCOMFO.getCoseri()).isEqualTo(UPDATED_COSERI);
        assertThat(testTBCOMFO.getCodir()).isEqualTo(UPDATED_CODIR);
        assertThat(testTBCOMFO.getColmd()).isEqualTo(UPDATED_COLMD);
        assertThat(testTBCOMFO.getCouid()).isEqualTo(UPDATED_COUID);
    }

    @Test
    @Transactional
    void patchNonExistingTBCOMFO() throws Exception {
        int databaseSizeBeforeUpdate = tBCOMFORepository.findAll().size();
        tBCOMFO.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBCOMFOMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tBCOMFO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBCOMFO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBCOMFO in the database
        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTBCOMFO() throws Exception {
        int databaseSizeBeforeUpdate = tBCOMFORepository.findAll().size();
        tBCOMFO.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBCOMFOMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBCOMFO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBCOMFO in the database
        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTBCOMFO() throws Exception {
        int databaseSizeBeforeUpdate = tBCOMFORepository.findAll().size();
        tBCOMFO.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBCOMFOMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tBCOMFO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBCOMFO in the database
        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTBCOMFO() throws Exception {
        // Initialize the database
        tBCOMFORepository.saveAndFlush(tBCOMFO);

        int databaseSizeBeforeDelete = tBCOMFORepository.findAll().size();

        // Delete the tBCOMFO
        restTBCOMFOMockMvc
            .perform(delete(ENTITY_API_URL_ID, tBCOMFO.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TBCOMFO> tBCOMFOList = tBCOMFORepository.findAll();
        assertThat(tBCOMFOList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
