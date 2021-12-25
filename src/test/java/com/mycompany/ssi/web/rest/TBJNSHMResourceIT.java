package com.mycompany.ssi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.ssi.IntegrationTest;
import com.mycompany.ssi.domain.TBJNSHM;
import com.mycompany.ssi.repository.TBJNSHMRepository;
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
 * Integration tests for the {@link TBJNSHMResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TBJNSHMResourceIT {

    private static final String DEFAULT_JSHSTS = "AAAAAAAAAA";
    private static final String UPDATED_JSHSTS = "BBBBBBBBBB";

    private static final String DEFAULT_JSHNAM = "AAAAAAAAAA";
    private static final String UPDATED_JSHNAM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_JSHLMD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JSHLMD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_JSHUID = "AAAAAAAAAA";
    private static final String UPDATED_JSHUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tbjnshms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{jshcod}";

    @Autowired
    private TBJNSHMRepository tBJNSHMRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTBJNSHMMockMvc;

    private TBJNSHM tBJNSHM;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBJNSHM createEntity(EntityManager em) {
        TBJNSHM tBJNSHM = new TBJNSHM().jshsts(DEFAULT_JSHSTS).jshnam(DEFAULT_JSHNAM).jshlmd(DEFAULT_JSHLMD).jshuid(DEFAULT_JSHUID);
        return tBJNSHM;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBJNSHM createUpdatedEntity(EntityManager em) {
        TBJNSHM tBJNSHM = new TBJNSHM().jshsts(UPDATED_JSHSTS).jshnam(UPDATED_JSHNAM).jshlmd(UPDATED_JSHLMD).jshuid(UPDATED_JSHUID);
        return tBJNSHM;
    }

    @BeforeEach
    public void initTest() {
        tBJNSHM = createEntity(em);
    }

    @Test
    @Transactional
    void createTBJNSHM() throws Exception {
        int databaseSizeBeforeCreate = tBJNSHMRepository.findAll().size();
        // Create the TBJNSHM
        restTBJNSHMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBJNSHM)))
            .andExpect(status().isCreated());

        // Validate the TBJNSHM in the database
        List<TBJNSHM> tBJNSHMList = tBJNSHMRepository.findAll();
        assertThat(tBJNSHMList).hasSize(databaseSizeBeforeCreate + 1);
        TBJNSHM testTBJNSHM = tBJNSHMList.get(tBJNSHMList.size() - 1);
        assertThat(testTBJNSHM.getJshsts()).isEqualTo(DEFAULT_JSHSTS);
        assertThat(testTBJNSHM.getJshnam()).isEqualTo(DEFAULT_JSHNAM);
        assertThat(testTBJNSHM.getJshlmd()).isEqualTo(DEFAULT_JSHLMD);
        assertThat(testTBJNSHM.getJshuid()).isEqualTo(DEFAULT_JSHUID);
    }

    @Test
    @Transactional
    void createTBJNSHMWithExistingId() throws Exception {
        // Create the TBJNSHM with an existing ID
        tBJNSHM.setJshcod("existing_id");

        int databaseSizeBeforeCreate = tBJNSHMRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTBJNSHMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBJNSHM)))
            .andExpect(status().isBadRequest());

        // Validate the TBJNSHM in the database
        List<TBJNSHM> tBJNSHMList = tBJNSHMRepository.findAll();
        assertThat(tBJNSHMList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkJshstsIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBJNSHMRepository.findAll().size();
        // set the field null
        tBJNSHM.setJshsts(null);

        // Create the TBJNSHM, which fails.

        restTBJNSHMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBJNSHM)))
            .andExpect(status().isBadRequest());

        List<TBJNSHM> tBJNSHMList = tBJNSHMRepository.findAll();
        assertThat(tBJNSHMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkJshnamIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBJNSHMRepository.findAll().size();
        // set the field null
        tBJNSHM.setJshnam(null);

        // Create the TBJNSHM, which fails.

        restTBJNSHMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBJNSHM)))
            .andExpect(status().isBadRequest());

        List<TBJNSHM> tBJNSHMList = tBJNSHMRepository.findAll();
        assertThat(tBJNSHMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkJshlmdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBJNSHMRepository.findAll().size();
        // set the field null
        tBJNSHM.setJshlmd(null);

        // Create the TBJNSHM, which fails.

        restTBJNSHMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBJNSHM)))
            .andExpect(status().isBadRequest());

        List<TBJNSHM> tBJNSHMList = tBJNSHMRepository.findAll();
        assertThat(tBJNSHMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkJshuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBJNSHMRepository.findAll().size();
        // set the field null
        tBJNSHM.setJshuid(null);

        // Create the TBJNSHM, which fails.

        restTBJNSHMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBJNSHM)))
            .andExpect(status().isBadRequest());

        List<TBJNSHM> tBJNSHMList = tBJNSHMRepository.findAll();
        assertThat(tBJNSHMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTBJNSHMS() throws Exception {
        // Initialize the database
        tBJNSHM.setJshcod(UUID.randomUUID().toString());
        tBJNSHMRepository.saveAndFlush(tBJNSHM);

        // Get all the tBJNSHMList
        restTBJNSHMMockMvc
            .perform(get(ENTITY_API_URL + "?sort=jshcod,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].jshcod").value(hasItem(tBJNSHM.getJshcod())))
            .andExpect(jsonPath("$.[*].jshsts").value(hasItem(DEFAULT_JSHSTS)))
            .andExpect(jsonPath("$.[*].jshnam").value(hasItem(DEFAULT_JSHNAM)))
            .andExpect(jsonPath("$.[*].jshlmd").value(hasItem(DEFAULT_JSHLMD.toString())))
            .andExpect(jsonPath("$.[*].jshuid").value(hasItem(DEFAULT_JSHUID)));
    }

    @Test
    @Transactional
    void getTBJNSHM() throws Exception {
        // Initialize the database
        tBJNSHM.setJshcod(UUID.randomUUID().toString());
        tBJNSHMRepository.saveAndFlush(tBJNSHM);

        // Get the tBJNSHM
        restTBJNSHMMockMvc
            .perform(get(ENTITY_API_URL_ID, tBJNSHM.getJshcod()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.jshcod").value(tBJNSHM.getJshcod()))
            .andExpect(jsonPath("$.jshsts").value(DEFAULT_JSHSTS))
            .andExpect(jsonPath("$.jshnam").value(DEFAULT_JSHNAM))
            .andExpect(jsonPath("$.jshlmd").value(DEFAULT_JSHLMD.toString()))
            .andExpect(jsonPath("$.jshuid").value(DEFAULT_JSHUID));
    }

    @Test
    @Transactional
    void getNonExistingTBJNSHM() throws Exception {
        // Get the tBJNSHM
        restTBJNSHMMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTBJNSHM() throws Exception {
        // Initialize the database
        tBJNSHM.setJshcod(UUID.randomUUID().toString());
        tBJNSHMRepository.saveAndFlush(tBJNSHM);

        int databaseSizeBeforeUpdate = tBJNSHMRepository.findAll().size();

        // Update the tBJNSHM
        TBJNSHM updatedTBJNSHM = tBJNSHMRepository.findById(tBJNSHM.getJshcod()).get();
        // Disconnect from session so that the updates on updatedTBJNSHM are not directly saved in db
        em.detach(updatedTBJNSHM);
        updatedTBJNSHM.jshsts(UPDATED_JSHSTS).jshnam(UPDATED_JSHNAM).jshlmd(UPDATED_JSHLMD).jshuid(UPDATED_JSHUID);

        restTBJNSHMMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTBJNSHM.getJshcod())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTBJNSHM))
            )
            .andExpect(status().isOk());

        // Validate the TBJNSHM in the database
        List<TBJNSHM> tBJNSHMList = tBJNSHMRepository.findAll();
        assertThat(tBJNSHMList).hasSize(databaseSizeBeforeUpdate);
        TBJNSHM testTBJNSHM = tBJNSHMList.get(tBJNSHMList.size() - 1);
        assertThat(testTBJNSHM.getJshsts()).isEqualTo(UPDATED_JSHSTS);
        assertThat(testTBJNSHM.getJshnam()).isEqualTo(UPDATED_JSHNAM);
        assertThat(testTBJNSHM.getJshlmd()).isEqualTo(UPDATED_JSHLMD);
        assertThat(testTBJNSHM.getJshuid()).isEqualTo(UPDATED_JSHUID);
    }

    @Test
    @Transactional
    void putNonExistingTBJNSHM() throws Exception {
        int databaseSizeBeforeUpdate = tBJNSHMRepository.findAll().size();
        tBJNSHM.setJshcod(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBJNSHMMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tBJNSHM.getJshcod())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBJNSHM))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBJNSHM in the database
        List<TBJNSHM> tBJNSHMList = tBJNSHMRepository.findAll();
        assertThat(tBJNSHMList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTBJNSHM() throws Exception {
        int databaseSizeBeforeUpdate = tBJNSHMRepository.findAll().size();
        tBJNSHM.setJshcod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBJNSHMMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBJNSHM))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBJNSHM in the database
        List<TBJNSHM> tBJNSHMList = tBJNSHMRepository.findAll();
        assertThat(tBJNSHMList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTBJNSHM() throws Exception {
        int databaseSizeBeforeUpdate = tBJNSHMRepository.findAll().size();
        tBJNSHM.setJshcod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBJNSHMMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBJNSHM)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBJNSHM in the database
        List<TBJNSHM> tBJNSHMList = tBJNSHMRepository.findAll();
        assertThat(tBJNSHMList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTBJNSHMWithPatch() throws Exception {
        // Initialize the database
        tBJNSHM.setJshcod(UUID.randomUUID().toString());
        tBJNSHMRepository.saveAndFlush(tBJNSHM);

        int databaseSizeBeforeUpdate = tBJNSHMRepository.findAll().size();

        // Update the tBJNSHM using partial update
        TBJNSHM partialUpdatedTBJNSHM = new TBJNSHM();
        partialUpdatedTBJNSHM.setJshcod(tBJNSHM.getJshcod());

        partialUpdatedTBJNSHM.jshsts(UPDATED_JSHSTS).jshnam(UPDATED_JSHNAM);

        restTBJNSHMMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBJNSHM.getJshcod())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBJNSHM))
            )
            .andExpect(status().isOk());

        // Validate the TBJNSHM in the database
        List<TBJNSHM> tBJNSHMList = tBJNSHMRepository.findAll();
        assertThat(tBJNSHMList).hasSize(databaseSizeBeforeUpdate);
        TBJNSHM testTBJNSHM = tBJNSHMList.get(tBJNSHMList.size() - 1);
        assertThat(testTBJNSHM.getJshsts()).isEqualTo(UPDATED_JSHSTS);
        assertThat(testTBJNSHM.getJshnam()).isEqualTo(UPDATED_JSHNAM);
        assertThat(testTBJNSHM.getJshlmd()).isEqualTo(DEFAULT_JSHLMD);
        assertThat(testTBJNSHM.getJshuid()).isEqualTo(DEFAULT_JSHUID);
    }

    @Test
    @Transactional
    void fullUpdateTBJNSHMWithPatch() throws Exception {
        // Initialize the database
        tBJNSHM.setJshcod(UUID.randomUUID().toString());
        tBJNSHMRepository.saveAndFlush(tBJNSHM);

        int databaseSizeBeforeUpdate = tBJNSHMRepository.findAll().size();

        // Update the tBJNSHM using partial update
        TBJNSHM partialUpdatedTBJNSHM = new TBJNSHM();
        partialUpdatedTBJNSHM.setJshcod(tBJNSHM.getJshcod());

        partialUpdatedTBJNSHM.jshsts(UPDATED_JSHSTS).jshnam(UPDATED_JSHNAM).jshlmd(UPDATED_JSHLMD).jshuid(UPDATED_JSHUID);

        restTBJNSHMMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBJNSHM.getJshcod())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBJNSHM))
            )
            .andExpect(status().isOk());

        // Validate the TBJNSHM in the database
        List<TBJNSHM> tBJNSHMList = tBJNSHMRepository.findAll();
        assertThat(tBJNSHMList).hasSize(databaseSizeBeforeUpdate);
        TBJNSHM testTBJNSHM = tBJNSHMList.get(tBJNSHMList.size() - 1);
        assertThat(testTBJNSHM.getJshsts()).isEqualTo(UPDATED_JSHSTS);
        assertThat(testTBJNSHM.getJshnam()).isEqualTo(UPDATED_JSHNAM);
        assertThat(testTBJNSHM.getJshlmd()).isEqualTo(UPDATED_JSHLMD);
        assertThat(testTBJNSHM.getJshuid()).isEqualTo(UPDATED_JSHUID);
    }

    @Test
    @Transactional
    void patchNonExistingTBJNSHM() throws Exception {
        int databaseSizeBeforeUpdate = tBJNSHMRepository.findAll().size();
        tBJNSHM.setJshcod(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBJNSHMMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tBJNSHM.getJshcod())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBJNSHM))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBJNSHM in the database
        List<TBJNSHM> tBJNSHMList = tBJNSHMRepository.findAll();
        assertThat(tBJNSHMList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTBJNSHM() throws Exception {
        int databaseSizeBeforeUpdate = tBJNSHMRepository.findAll().size();
        tBJNSHM.setJshcod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBJNSHMMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBJNSHM))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBJNSHM in the database
        List<TBJNSHM> tBJNSHMList = tBJNSHMRepository.findAll();
        assertThat(tBJNSHMList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTBJNSHM() throws Exception {
        int databaseSizeBeforeUpdate = tBJNSHMRepository.findAll().size();
        tBJNSHM.setJshcod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBJNSHMMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tBJNSHM)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBJNSHM in the database
        List<TBJNSHM> tBJNSHMList = tBJNSHMRepository.findAll();
        assertThat(tBJNSHMList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTBJNSHM() throws Exception {
        // Initialize the database
        tBJNSHM.setJshcod(UUID.randomUUID().toString());
        tBJNSHMRepository.saveAndFlush(tBJNSHM);

        int databaseSizeBeforeDelete = tBJNSHMRepository.findAll().size();

        // Delete the tBJNSHM
        restTBJNSHMMockMvc
            .perform(delete(ENTITY_API_URL_ID, tBJNSHM.getJshcod()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TBJNSHM> tBJNSHMList = tBJNSHMRepository.findAll();
        assertThat(tBJNSHMList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
