package com.mycompany.ssi.web.rest;

import static com.mycompany.ssi.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.ssi.IntegrationTest;
import com.mycompany.ssi.domain.TBNEG;
import com.mycompany.ssi.repository.TBNEGRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link TBNEGResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TBNEGResourceIT {

    private static final String DEFAULT_NEGSTS = "AAAAAAAAAA";
    private static final String UPDATED_NEGSTS = "BBBBBBBBBB";

    private static final String DEFAULT_NEGNAM = "AAAAAAAAAA";
    private static final String UPDATED_NEGNAM = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_NEGTAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_NEGTAX = new BigDecimal(2);

    private static final LocalDate DEFAULT_NEGLMD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NEGLMD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NEGUID = "AAAAAAAAAA";
    private static final String UPDATED_NEGUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tbnegs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{negcod}";

    @Autowired
    private TBNEGRepository tBNEGRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTBNEGMockMvc;

    private TBNEG tBNEG;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBNEG createEntity(EntityManager em) {
        TBNEG tBNEG = new TBNEG()
            .negsts(DEFAULT_NEGSTS)
            .negnam(DEFAULT_NEGNAM)
            .negtax(DEFAULT_NEGTAX)
            .neglmd(DEFAULT_NEGLMD)
            .neguid(DEFAULT_NEGUID);
        return tBNEG;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBNEG createUpdatedEntity(EntityManager em) {
        TBNEG tBNEG = new TBNEG()
            .negsts(UPDATED_NEGSTS)
            .negnam(UPDATED_NEGNAM)
            .negtax(UPDATED_NEGTAX)
            .neglmd(UPDATED_NEGLMD)
            .neguid(UPDATED_NEGUID);
        return tBNEG;
    }

    @BeforeEach
    public void initTest() {
        tBNEG = createEntity(em);
    }

    @Test
    @Transactional
    void createTBNEG() throws Exception {
        int databaseSizeBeforeCreate = tBNEGRepository.findAll().size();
        // Create the TBNEG
        restTBNEGMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBNEG)))
            .andExpect(status().isCreated());

        // Validate the TBNEG in the database
        List<TBNEG> tBNEGList = tBNEGRepository.findAll();
        assertThat(tBNEGList).hasSize(databaseSizeBeforeCreate + 1);
        TBNEG testTBNEG = tBNEGList.get(tBNEGList.size() - 1);
        assertThat(testTBNEG.getNegsts()).isEqualTo(DEFAULT_NEGSTS);
        assertThat(testTBNEG.getNegnam()).isEqualTo(DEFAULT_NEGNAM);
        assertThat(testTBNEG.getNegtax()).isEqualByComparingTo(DEFAULT_NEGTAX);
        assertThat(testTBNEG.getNeglmd()).isEqualTo(DEFAULT_NEGLMD);
        assertThat(testTBNEG.getNeguid()).isEqualTo(DEFAULT_NEGUID);
    }

    @Test
    @Transactional
    void createTBNEGWithExistingId() throws Exception {
        // Create the TBNEG with an existing ID
        tBNEG.setNegcod("existing_id");

        int databaseSizeBeforeCreate = tBNEGRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTBNEGMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBNEG)))
            .andExpect(status().isBadRequest());

        // Validate the TBNEG in the database
        List<TBNEG> tBNEGList = tBNEGRepository.findAll();
        assertThat(tBNEGList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNegstsIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBNEGRepository.findAll().size();
        // set the field null
        tBNEG.setNegsts(null);

        // Create the TBNEG, which fails.

        restTBNEGMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBNEG)))
            .andExpect(status().isBadRequest());

        List<TBNEG> tBNEGList = tBNEGRepository.findAll();
        assertThat(tBNEGList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNegnamIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBNEGRepository.findAll().size();
        // set the field null
        tBNEG.setNegnam(null);

        // Create the TBNEG, which fails.

        restTBNEGMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBNEG)))
            .andExpect(status().isBadRequest());

        List<TBNEG> tBNEGList = tBNEGRepository.findAll();
        assertThat(tBNEGList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNegtaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBNEGRepository.findAll().size();
        // set the field null
        tBNEG.setNegtax(null);

        // Create the TBNEG, which fails.

        restTBNEGMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBNEG)))
            .andExpect(status().isBadRequest());

        List<TBNEG> tBNEGList = tBNEGRepository.findAll();
        assertThat(tBNEGList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNeglmdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBNEGRepository.findAll().size();
        // set the field null
        tBNEG.setNeglmd(null);

        // Create the TBNEG, which fails.

        restTBNEGMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBNEG)))
            .andExpect(status().isBadRequest());

        List<TBNEG> tBNEGList = tBNEGRepository.findAll();
        assertThat(tBNEGList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNeguidIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBNEGRepository.findAll().size();
        // set the field null
        tBNEG.setNeguid(null);

        // Create the TBNEG, which fails.

        restTBNEGMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBNEG)))
            .andExpect(status().isBadRequest());

        List<TBNEG> tBNEGList = tBNEGRepository.findAll();
        assertThat(tBNEGList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTBNEGS() throws Exception {
        // Initialize the database
        tBNEG.setNegcod(UUID.randomUUID().toString());
        tBNEGRepository.saveAndFlush(tBNEG);

        // Get all the tBNEGList
        restTBNEGMockMvc
            .perform(get(ENTITY_API_URL + "?sort=negcod,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].negcod").value(hasItem(tBNEG.getNegcod())))
            .andExpect(jsonPath("$.[*].negsts").value(hasItem(DEFAULT_NEGSTS)))
            .andExpect(jsonPath("$.[*].negnam").value(hasItem(DEFAULT_NEGNAM)))
            .andExpect(jsonPath("$.[*].negtax").value(hasItem(sameNumber(DEFAULT_NEGTAX))))
            .andExpect(jsonPath("$.[*].neglmd").value(hasItem(DEFAULT_NEGLMD.toString())))
            .andExpect(jsonPath("$.[*].neguid").value(hasItem(DEFAULT_NEGUID)));
    }

    @Test
    @Transactional
    void getTBNEG() throws Exception {
        // Initialize the database
        tBNEG.setNegcod(UUID.randomUUID().toString());
        tBNEGRepository.saveAndFlush(tBNEG);

        // Get the tBNEG
        restTBNEGMockMvc
            .perform(get(ENTITY_API_URL_ID, tBNEG.getNegcod()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.negcod").value(tBNEG.getNegcod()))
            .andExpect(jsonPath("$.negsts").value(DEFAULT_NEGSTS))
            .andExpect(jsonPath("$.negnam").value(DEFAULT_NEGNAM))
            .andExpect(jsonPath("$.negtax").value(sameNumber(DEFAULT_NEGTAX)))
            .andExpect(jsonPath("$.neglmd").value(DEFAULT_NEGLMD.toString()))
            .andExpect(jsonPath("$.neguid").value(DEFAULT_NEGUID));
    }

    @Test
    @Transactional
    void getNonExistingTBNEG() throws Exception {
        // Get the tBNEG
        restTBNEGMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTBNEG() throws Exception {
        // Initialize the database
        tBNEG.setNegcod(UUID.randomUUID().toString());
        tBNEGRepository.saveAndFlush(tBNEG);

        int databaseSizeBeforeUpdate = tBNEGRepository.findAll().size();

        // Update the tBNEG
        TBNEG updatedTBNEG = tBNEGRepository.findById(tBNEG.getNegcod()).get();
        // Disconnect from session so that the updates on updatedTBNEG are not directly saved in db
        em.detach(updatedTBNEG);
        updatedTBNEG.negsts(UPDATED_NEGSTS).negnam(UPDATED_NEGNAM).negtax(UPDATED_NEGTAX).neglmd(UPDATED_NEGLMD).neguid(UPDATED_NEGUID);

        restTBNEGMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTBNEG.getNegcod())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTBNEG))
            )
            .andExpect(status().isOk());

        // Validate the TBNEG in the database
        List<TBNEG> tBNEGList = tBNEGRepository.findAll();
        assertThat(tBNEGList).hasSize(databaseSizeBeforeUpdate);
        TBNEG testTBNEG = tBNEGList.get(tBNEGList.size() - 1);
        assertThat(testTBNEG.getNegsts()).isEqualTo(UPDATED_NEGSTS);
        assertThat(testTBNEG.getNegnam()).isEqualTo(UPDATED_NEGNAM);
        assertThat(testTBNEG.getNegtax()).isEqualTo(UPDATED_NEGTAX);
        assertThat(testTBNEG.getNeglmd()).isEqualTo(UPDATED_NEGLMD);
        assertThat(testTBNEG.getNeguid()).isEqualTo(UPDATED_NEGUID);
    }

    @Test
    @Transactional
    void putNonExistingTBNEG() throws Exception {
        int databaseSizeBeforeUpdate = tBNEGRepository.findAll().size();
        tBNEG.setNegcod(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBNEGMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tBNEG.getNegcod())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBNEG))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBNEG in the database
        List<TBNEG> tBNEGList = tBNEGRepository.findAll();
        assertThat(tBNEGList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTBNEG() throws Exception {
        int databaseSizeBeforeUpdate = tBNEGRepository.findAll().size();
        tBNEG.setNegcod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBNEGMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBNEG))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBNEG in the database
        List<TBNEG> tBNEGList = tBNEGRepository.findAll();
        assertThat(tBNEGList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTBNEG() throws Exception {
        int databaseSizeBeforeUpdate = tBNEGRepository.findAll().size();
        tBNEG.setNegcod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBNEGMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBNEG)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBNEG in the database
        List<TBNEG> tBNEGList = tBNEGRepository.findAll();
        assertThat(tBNEGList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTBNEGWithPatch() throws Exception {
        // Initialize the database
        tBNEG.setNegcod(UUID.randomUUID().toString());
        tBNEGRepository.saveAndFlush(tBNEG);

        int databaseSizeBeforeUpdate = tBNEGRepository.findAll().size();

        // Update the tBNEG using partial update
        TBNEG partialUpdatedTBNEG = new TBNEG();
        partialUpdatedTBNEG.setNegcod(tBNEG.getNegcod());

        partialUpdatedTBNEG.negnam(UPDATED_NEGNAM).neglmd(UPDATED_NEGLMD).neguid(UPDATED_NEGUID);

        restTBNEGMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBNEG.getNegcod())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBNEG))
            )
            .andExpect(status().isOk());

        // Validate the TBNEG in the database
        List<TBNEG> tBNEGList = tBNEGRepository.findAll();
        assertThat(tBNEGList).hasSize(databaseSizeBeforeUpdate);
        TBNEG testTBNEG = tBNEGList.get(tBNEGList.size() - 1);
        assertThat(testTBNEG.getNegsts()).isEqualTo(DEFAULT_NEGSTS);
        assertThat(testTBNEG.getNegnam()).isEqualTo(UPDATED_NEGNAM);
        assertThat(testTBNEG.getNegtax()).isEqualByComparingTo(DEFAULT_NEGTAX);
        assertThat(testTBNEG.getNeglmd()).isEqualTo(UPDATED_NEGLMD);
        assertThat(testTBNEG.getNeguid()).isEqualTo(UPDATED_NEGUID);
    }

    @Test
    @Transactional
    void fullUpdateTBNEGWithPatch() throws Exception {
        // Initialize the database
        tBNEG.setNegcod(UUID.randomUUID().toString());
        tBNEGRepository.saveAndFlush(tBNEG);

        int databaseSizeBeforeUpdate = tBNEGRepository.findAll().size();

        // Update the tBNEG using partial update
        TBNEG partialUpdatedTBNEG = new TBNEG();
        partialUpdatedTBNEG.setNegcod(tBNEG.getNegcod());

        partialUpdatedTBNEG
            .negsts(UPDATED_NEGSTS)
            .negnam(UPDATED_NEGNAM)
            .negtax(UPDATED_NEGTAX)
            .neglmd(UPDATED_NEGLMD)
            .neguid(UPDATED_NEGUID);

        restTBNEGMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBNEG.getNegcod())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBNEG))
            )
            .andExpect(status().isOk());

        // Validate the TBNEG in the database
        List<TBNEG> tBNEGList = tBNEGRepository.findAll();
        assertThat(tBNEGList).hasSize(databaseSizeBeforeUpdate);
        TBNEG testTBNEG = tBNEGList.get(tBNEGList.size() - 1);
        assertThat(testTBNEG.getNegsts()).isEqualTo(UPDATED_NEGSTS);
        assertThat(testTBNEG.getNegnam()).isEqualTo(UPDATED_NEGNAM);
        assertThat(testTBNEG.getNegtax()).isEqualByComparingTo(UPDATED_NEGTAX);
        assertThat(testTBNEG.getNeglmd()).isEqualTo(UPDATED_NEGLMD);
        assertThat(testTBNEG.getNeguid()).isEqualTo(UPDATED_NEGUID);
    }

    @Test
    @Transactional
    void patchNonExistingTBNEG() throws Exception {
        int databaseSizeBeforeUpdate = tBNEGRepository.findAll().size();
        tBNEG.setNegcod(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBNEGMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tBNEG.getNegcod())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBNEG))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBNEG in the database
        List<TBNEG> tBNEGList = tBNEGRepository.findAll();
        assertThat(tBNEGList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTBNEG() throws Exception {
        int databaseSizeBeforeUpdate = tBNEGRepository.findAll().size();
        tBNEG.setNegcod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBNEGMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBNEG))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBNEG in the database
        List<TBNEG> tBNEGList = tBNEGRepository.findAll();
        assertThat(tBNEGList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTBNEG() throws Exception {
        int databaseSizeBeforeUpdate = tBNEGRepository.findAll().size();
        tBNEG.setNegcod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBNEGMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tBNEG)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBNEG in the database
        List<TBNEG> tBNEGList = tBNEGRepository.findAll();
        assertThat(tBNEGList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTBNEG() throws Exception {
        // Initialize the database
        tBNEG.setNegcod(UUID.randomUUID().toString());
        tBNEGRepository.saveAndFlush(tBNEG);

        int databaseSizeBeforeDelete = tBNEGRepository.findAll().size();

        // Delete the tBNEG
        restTBNEGMockMvc
            .perform(delete(ENTITY_API_URL_ID, tBNEG.getNegcod()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TBNEG> tBNEGList = tBNEGRepository.findAll();
        assertThat(tBNEGList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
