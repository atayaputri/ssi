package com.mycompany.ssi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.ssi.IntegrationTest;
import com.mycompany.ssi.domain.TBKAB;
import com.mycompany.ssi.repository.TBKABRepository;
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
 * Integration tests for the {@link TBKABResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TBKABResourceIT {

    private static final String DEFAULT_KABSTS = "AAAAAAAAAA";
    private static final String UPDATED_KABSTS = "BBBBBBBBBB";

    private static final String DEFAULT_KABNAM = "AAAAAAAAAA";
    private static final String UPDATED_KABNAM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_KABLMD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_KABLMD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_KABUID = "AAAAAAAAAA";
    private static final String UPDATED_KABUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tbkabs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{kabcod}";

    @Autowired
    private TBKABRepository tBKABRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTBKABMockMvc;

    private TBKAB tBKAB;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBKAB createEntity(EntityManager em) {
        TBKAB tBKAB = new TBKAB().kabsts(DEFAULT_KABSTS).kabnam(DEFAULT_KABNAM).kablmd(DEFAULT_KABLMD).kabuid(DEFAULT_KABUID);
        return tBKAB;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBKAB createUpdatedEntity(EntityManager em) {
        TBKAB tBKAB = new TBKAB().kabsts(UPDATED_KABSTS).kabnam(UPDATED_KABNAM).kablmd(UPDATED_KABLMD).kabuid(UPDATED_KABUID);
        return tBKAB;
    }

    @BeforeEach
    public void initTest() {
        tBKAB = createEntity(em);
    }

    @Test
    @Transactional
    void createTBKAB() throws Exception {
        int databaseSizeBeforeCreate = tBKABRepository.findAll().size();
        // Create the TBKAB
        restTBKABMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBKAB)))
            .andExpect(status().isCreated());

        // Validate the TBKAB in the database
        List<TBKAB> tBKABList = tBKABRepository.findAll();
        assertThat(tBKABList).hasSize(databaseSizeBeforeCreate + 1);
        TBKAB testTBKAB = tBKABList.get(tBKABList.size() - 1);
        assertThat(testTBKAB.getKabsts()).isEqualTo(DEFAULT_KABSTS);
        assertThat(testTBKAB.getKabnam()).isEqualTo(DEFAULT_KABNAM);
        assertThat(testTBKAB.getKablmd()).isEqualTo(DEFAULT_KABLMD);
        assertThat(testTBKAB.getKabuid()).isEqualTo(DEFAULT_KABUID);
    }

    @Test
    @Transactional
    void createTBKABWithExistingId() throws Exception {
        // Create the TBKAB with an existing ID
        tBKAB.setKabcod("existing_id");

        int databaseSizeBeforeCreate = tBKABRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTBKABMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBKAB)))
            .andExpect(status().isBadRequest());

        // Validate the TBKAB in the database
        List<TBKAB> tBKABList = tBKABRepository.findAll();
        assertThat(tBKABList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkKabstsIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBKABRepository.findAll().size();
        // set the field null
        tBKAB.setKabsts(null);

        // Create the TBKAB, which fails.

        restTBKABMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBKAB)))
            .andExpect(status().isBadRequest());

        List<TBKAB> tBKABList = tBKABRepository.findAll();
        assertThat(tBKABList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkKabnamIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBKABRepository.findAll().size();
        // set the field null
        tBKAB.setKabnam(null);

        // Create the TBKAB, which fails.

        restTBKABMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBKAB)))
            .andExpect(status().isBadRequest());

        List<TBKAB> tBKABList = tBKABRepository.findAll();
        assertThat(tBKABList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkKablmdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBKABRepository.findAll().size();
        // set the field null
        tBKAB.setKablmd(null);

        // Create the TBKAB, which fails.

        restTBKABMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBKAB)))
            .andExpect(status().isBadRequest());

        List<TBKAB> tBKABList = tBKABRepository.findAll();
        assertThat(tBKABList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkKabuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBKABRepository.findAll().size();
        // set the field null
        tBKAB.setKabuid(null);

        // Create the TBKAB, which fails.

        restTBKABMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBKAB)))
            .andExpect(status().isBadRequest());

        List<TBKAB> tBKABList = tBKABRepository.findAll();
        assertThat(tBKABList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTBKABS() throws Exception {
        // Initialize the database
        tBKAB.setKabcod(UUID.randomUUID().toString());
        tBKABRepository.saveAndFlush(tBKAB);

        // Get all the tBKABList
        restTBKABMockMvc
            .perform(get(ENTITY_API_URL + "?sort=kabcod,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].kabcod").value(hasItem(tBKAB.getKabcod())))
            .andExpect(jsonPath("$.[*].kabsts").value(hasItem(DEFAULT_KABSTS)))
            .andExpect(jsonPath("$.[*].kabnam").value(hasItem(DEFAULT_KABNAM)))
            .andExpect(jsonPath("$.[*].kablmd").value(hasItem(DEFAULT_KABLMD.toString())))
            .andExpect(jsonPath("$.[*].kabuid").value(hasItem(DEFAULT_KABUID)));
    }

    @Test
    @Transactional
    void getTBKAB() throws Exception {
        // Initialize the database
        tBKAB.setKabcod(UUID.randomUUID().toString());
        tBKABRepository.saveAndFlush(tBKAB);

        // Get the tBKAB
        restTBKABMockMvc
            .perform(get(ENTITY_API_URL_ID, tBKAB.getKabcod()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.kabcod").value(tBKAB.getKabcod()))
            .andExpect(jsonPath("$.kabsts").value(DEFAULT_KABSTS))
            .andExpect(jsonPath("$.kabnam").value(DEFAULT_KABNAM))
            .andExpect(jsonPath("$.kablmd").value(DEFAULT_KABLMD.toString()))
            .andExpect(jsonPath("$.kabuid").value(DEFAULT_KABUID));
    }

    @Test
    @Transactional
    void getNonExistingTBKAB() throws Exception {
        // Get the tBKAB
        restTBKABMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTBKAB() throws Exception {
        // Initialize the database
        tBKAB.setKabcod(UUID.randomUUID().toString());
        tBKABRepository.saveAndFlush(tBKAB);

        int databaseSizeBeforeUpdate = tBKABRepository.findAll().size();

        // Update the tBKAB
        TBKAB updatedTBKAB = tBKABRepository.findById(tBKAB.getKabcod()).get();
        // Disconnect from session so that the updates on updatedTBKAB are not directly saved in db
        em.detach(updatedTBKAB);
        updatedTBKAB.kabsts(UPDATED_KABSTS).kabnam(UPDATED_KABNAM).kablmd(UPDATED_KABLMD).kabuid(UPDATED_KABUID);

        restTBKABMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTBKAB.getKabcod())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTBKAB))
            )
            .andExpect(status().isOk());

        // Validate the TBKAB in the database
        List<TBKAB> tBKABList = tBKABRepository.findAll();
        assertThat(tBKABList).hasSize(databaseSizeBeforeUpdate);
        TBKAB testTBKAB = tBKABList.get(tBKABList.size() - 1);
        assertThat(testTBKAB.getKabsts()).isEqualTo(UPDATED_KABSTS);
        assertThat(testTBKAB.getKabnam()).isEqualTo(UPDATED_KABNAM);
        assertThat(testTBKAB.getKablmd()).isEqualTo(UPDATED_KABLMD);
        assertThat(testTBKAB.getKabuid()).isEqualTo(UPDATED_KABUID);
    }

    @Test
    @Transactional
    void putNonExistingTBKAB() throws Exception {
        int databaseSizeBeforeUpdate = tBKABRepository.findAll().size();
        tBKAB.setKabcod(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBKABMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tBKAB.getKabcod())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBKAB))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBKAB in the database
        List<TBKAB> tBKABList = tBKABRepository.findAll();
        assertThat(tBKABList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTBKAB() throws Exception {
        int databaseSizeBeforeUpdate = tBKABRepository.findAll().size();
        tBKAB.setKabcod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBKABMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBKAB))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBKAB in the database
        List<TBKAB> tBKABList = tBKABRepository.findAll();
        assertThat(tBKABList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTBKAB() throws Exception {
        int databaseSizeBeforeUpdate = tBKABRepository.findAll().size();
        tBKAB.setKabcod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBKABMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBKAB)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBKAB in the database
        List<TBKAB> tBKABList = tBKABRepository.findAll();
        assertThat(tBKABList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTBKABWithPatch() throws Exception {
        // Initialize the database
        tBKAB.setKabcod(UUID.randomUUID().toString());
        tBKABRepository.saveAndFlush(tBKAB);

        int databaseSizeBeforeUpdate = tBKABRepository.findAll().size();

        // Update the tBKAB using partial update
        TBKAB partialUpdatedTBKAB = new TBKAB();
        partialUpdatedTBKAB.setKabcod(tBKAB.getKabcod());

        partialUpdatedTBKAB.kablmd(UPDATED_KABLMD);

        restTBKABMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBKAB.getKabcod())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBKAB))
            )
            .andExpect(status().isOk());

        // Validate the TBKAB in the database
        List<TBKAB> tBKABList = tBKABRepository.findAll();
        assertThat(tBKABList).hasSize(databaseSizeBeforeUpdate);
        TBKAB testTBKAB = tBKABList.get(tBKABList.size() - 1);
        assertThat(testTBKAB.getKabsts()).isEqualTo(DEFAULT_KABSTS);
        assertThat(testTBKAB.getKabnam()).isEqualTo(DEFAULT_KABNAM);
        assertThat(testTBKAB.getKablmd()).isEqualTo(UPDATED_KABLMD);
        assertThat(testTBKAB.getKabuid()).isEqualTo(DEFAULT_KABUID);
    }

    @Test
    @Transactional
    void fullUpdateTBKABWithPatch() throws Exception {
        // Initialize the database
        tBKAB.setKabcod(UUID.randomUUID().toString());
        tBKABRepository.saveAndFlush(tBKAB);

        int databaseSizeBeforeUpdate = tBKABRepository.findAll().size();

        // Update the tBKAB using partial update
        TBKAB partialUpdatedTBKAB = new TBKAB();
        partialUpdatedTBKAB.setKabcod(tBKAB.getKabcod());

        partialUpdatedTBKAB.kabsts(UPDATED_KABSTS).kabnam(UPDATED_KABNAM).kablmd(UPDATED_KABLMD).kabuid(UPDATED_KABUID);

        restTBKABMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBKAB.getKabcod())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBKAB))
            )
            .andExpect(status().isOk());

        // Validate the TBKAB in the database
        List<TBKAB> tBKABList = tBKABRepository.findAll();
        assertThat(tBKABList).hasSize(databaseSizeBeforeUpdate);
        TBKAB testTBKAB = tBKABList.get(tBKABList.size() - 1);
        assertThat(testTBKAB.getKabsts()).isEqualTo(UPDATED_KABSTS);
        assertThat(testTBKAB.getKabnam()).isEqualTo(UPDATED_KABNAM);
        assertThat(testTBKAB.getKablmd()).isEqualTo(UPDATED_KABLMD);
        assertThat(testTBKAB.getKabuid()).isEqualTo(UPDATED_KABUID);
    }

    @Test
    @Transactional
    void patchNonExistingTBKAB() throws Exception {
        int databaseSizeBeforeUpdate = tBKABRepository.findAll().size();
        tBKAB.setKabcod(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBKABMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tBKAB.getKabcod())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBKAB))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBKAB in the database
        List<TBKAB> tBKABList = tBKABRepository.findAll();
        assertThat(tBKABList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTBKAB() throws Exception {
        int databaseSizeBeforeUpdate = tBKABRepository.findAll().size();
        tBKAB.setKabcod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBKABMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBKAB))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBKAB in the database
        List<TBKAB> tBKABList = tBKABRepository.findAll();
        assertThat(tBKABList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTBKAB() throws Exception {
        int databaseSizeBeforeUpdate = tBKABRepository.findAll().size();
        tBKAB.setKabcod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBKABMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tBKAB)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBKAB in the database
        List<TBKAB> tBKABList = tBKABRepository.findAll();
        assertThat(tBKABList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTBKAB() throws Exception {
        // Initialize the database
        tBKAB.setKabcod(UUID.randomUUID().toString());
        tBKABRepository.saveAndFlush(tBKAB);

        int databaseSizeBeforeDelete = tBKABRepository.findAll().size();

        // Delete the tBKAB
        restTBKABMockMvc
            .perform(delete(ENTITY_API_URL_ID, tBKAB.getKabcod()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TBKAB> tBKABList = tBKABRepository.findAll();
        assertThat(tBKABList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
