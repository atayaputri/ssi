package com.mycompany.ssi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.ssi.IntegrationTest;
import com.mycompany.ssi.domain.TBJNPS;
import com.mycompany.ssi.repository.TBJNPSRepository;
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
 * Integration tests for the {@link TBJNPSResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TBJNPSResourceIT {

    private static final String DEFAULT_JPSSTS = "AAAAAAAAAA";
    private static final String UPDATED_JPSSTS = "BBBBBBBBBB";

    private static final String DEFAULT_JPSNAM = "AAAAAAAAAA";
    private static final String UPDATED_JPSNAM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_JPSLMD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JPSLMD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_JPSUID = "AAAAAAAAAA";
    private static final String UPDATED_JPSUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tbjnps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{jpscod}";

    @Autowired
    private TBJNPSRepository tBJNPSRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTBJNPSMockMvc;

    private TBJNPS tBJNPS;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBJNPS createEntity(EntityManager em) {
        TBJNPS tBJNPS = new TBJNPS().jpssts(DEFAULT_JPSSTS).jpsnam(DEFAULT_JPSNAM).jpslmd(DEFAULT_JPSLMD).jpsuid(DEFAULT_JPSUID);
        return tBJNPS;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBJNPS createUpdatedEntity(EntityManager em) {
        TBJNPS tBJNPS = new TBJNPS().jpssts(UPDATED_JPSSTS).jpsnam(UPDATED_JPSNAM).jpslmd(UPDATED_JPSLMD).jpsuid(UPDATED_JPSUID);
        return tBJNPS;
    }

    @BeforeEach
    public void initTest() {
        tBJNPS = createEntity(em);
    }

    @Test
    @Transactional
    void createTBJNPS() throws Exception {
        int databaseSizeBeforeCreate = tBJNPSRepository.findAll().size();
        // Create the TBJNPS
        restTBJNPSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBJNPS)))
            .andExpect(status().isCreated());

        // Validate the TBJNPS in the database
        List<TBJNPS> tBJNPSList = tBJNPSRepository.findAll();
        assertThat(tBJNPSList).hasSize(databaseSizeBeforeCreate + 1);
        TBJNPS testTBJNPS = tBJNPSList.get(tBJNPSList.size() - 1);
        assertThat(testTBJNPS.getJpssts()).isEqualTo(DEFAULT_JPSSTS);
        assertThat(testTBJNPS.getJpsnam()).isEqualTo(DEFAULT_JPSNAM);
        assertThat(testTBJNPS.getJpslmd()).isEqualTo(DEFAULT_JPSLMD);
        assertThat(testTBJNPS.getJpsuid()).isEqualTo(DEFAULT_JPSUID);
    }

    @Test
    @Transactional
    void createTBJNPSWithExistingId() throws Exception {
        // Create the TBJNPS with an existing ID
        tBJNPS.setJpscod("existing_id");

        int databaseSizeBeforeCreate = tBJNPSRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTBJNPSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBJNPS)))
            .andExpect(status().isBadRequest());

        // Validate the TBJNPS in the database
        List<TBJNPS> tBJNPSList = tBJNPSRepository.findAll();
        assertThat(tBJNPSList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkJpsstsIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBJNPSRepository.findAll().size();
        // set the field null
        tBJNPS.setJpssts(null);

        // Create the TBJNPS, which fails.

        restTBJNPSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBJNPS)))
            .andExpect(status().isBadRequest());

        List<TBJNPS> tBJNPSList = tBJNPSRepository.findAll();
        assertThat(tBJNPSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkJpsnamIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBJNPSRepository.findAll().size();
        // set the field null
        tBJNPS.setJpsnam(null);

        // Create the TBJNPS, which fails.

        restTBJNPSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBJNPS)))
            .andExpect(status().isBadRequest());

        List<TBJNPS> tBJNPSList = tBJNPSRepository.findAll();
        assertThat(tBJNPSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkJpslmdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBJNPSRepository.findAll().size();
        // set the field null
        tBJNPS.setJpslmd(null);

        // Create the TBJNPS, which fails.

        restTBJNPSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBJNPS)))
            .andExpect(status().isBadRequest());

        List<TBJNPS> tBJNPSList = tBJNPSRepository.findAll();
        assertThat(tBJNPSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkJpsuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBJNPSRepository.findAll().size();
        // set the field null
        tBJNPS.setJpsuid(null);

        // Create the TBJNPS, which fails.

        restTBJNPSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBJNPS)))
            .andExpect(status().isBadRequest());

        List<TBJNPS> tBJNPSList = tBJNPSRepository.findAll();
        assertThat(tBJNPSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTBJNPS() throws Exception {
        // Initialize the database
        tBJNPS.setJpscod(UUID.randomUUID().toString());
        tBJNPSRepository.saveAndFlush(tBJNPS);

        // Get all the tBJNPSList
        restTBJNPSMockMvc
            .perform(get(ENTITY_API_URL + "?sort=jpscod,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].jpscod").value(hasItem(tBJNPS.getJpscod())))
            .andExpect(jsonPath("$.[*].jpssts").value(hasItem(DEFAULT_JPSSTS)))
            .andExpect(jsonPath("$.[*].jpsnam").value(hasItem(DEFAULT_JPSNAM)))
            .andExpect(jsonPath("$.[*].jpslmd").value(hasItem(DEFAULT_JPSLMD.toString())))
            .andExpect(jsonPath("$.[*].jpsuid").value(hasItem(DEFAULT_JPSUID)));
    }

    @Test
    @Transactional
    void getTBJNPS() throws Exception {
        // Initialize the database
        tBJNPS.setJpscod(UUID.randomUUID().toString());
        tBJNPSRepository.saveAndFlush(tBJNPS);

        // Get the tBJNPS
        restTBJNPSMockMvc
            .perform(get(ENTITY_API_URL_ID, tBJNPS.getJpscod()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.jpscod").value(tBJNPS.getJpscod()))
            .andExpect(jsonPath("$.jpssts").value(DEFAULT_JPSSTS))
            .andExpect(jsonPath("$.jpsnam").value(DEFAULT_JPSNAM))
            .andExpect(jsonPath("$.jpslmd").value(DEFAULT_JPSLMD.toString()))
            .andExpect(jsonPath("$.jpsuid").value(DEFAULT_JPSUID));
    }

    @Test
    @Transactional
    void getNonExistingTBJNPS() throws Exception {
        // Get the tBJNPS
        restTBJNPSMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTBJNPS() throws Exception {
        // Initialize the database
        tBJNPS.setJpscod(UUID.randomUUID().toString());
        tBJNPSRepository.saveAndFlush(tBJNPS);

        int databaseSizeBeforeUpdate = tBJNPSRepository.findAll().size();

        // Update the tBJNPS
        TBJNPS updatedTBJNPS = tBJNPSRepository.findById(tBJNPS.getJpscod()).get();
        // Disconnect from session so that the updates on updatedTBJNPS are not directly saved in db
        em.detach(updatedTBJNPS);
        updatedTBJNPS.jpssts(UPDATED_JPSSTS).jpsnam(UPDATED_JPSNAM).jpslmd(UPDATED_JPSLMD).jpsuid(UPDATED_JPSUID);

        restTBJNPSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTBJNPS.getJpscod())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTBJNPS))
            )
            .andExpect(status().isOk());

        // Validate the TBJNPS in the database
        List<TBJNPS> tBJNPSList = tBJNPSRepository.findAll();
        assertThat(tBJNPSList).hasSize(databaseSizeBeforeUpdate);
        TBJNPS testTBJNPS = tBJNPSList.get(tBJNPSList.size() - 1);
        assertThat(testTBJNPS.getJpssts()).isEqualTo(UPDATED_JPSSTS);
        assertThat(testTBJNPS.getJpsnam()).isEqualTo(UPDATED_JPSNAM);
        assertThat(testTBJNPS.getJpslmd()).isEqualTo(UPDATED_JPSLMD);
        assertThat(testTBJNPS.getJpsuid()).isEqualTo(UPDATED_JPSUID);
    }

    @Test
    @Transactional
    void putNonExistingTBJNPS() throws Exception {
        int databaseSizeBeforeUpdate = tBJNPSRepository.findAll().size();
        tBJNPS.setJpscod(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBJNPSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tBJNPS.getJpscod())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBJNPS))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBJNPS in the database
        List<TBJNPS> tBJNPSList = tBJNPSRepository.findAll();
        assertThat(tBJNPSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTBJNPS() throws Exception {
        int databaseSizeBeforeUpdate = tBJNPSRepository.findAll().size();
        tBJNPS.setJpscod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBJNPSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBJNPS))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBJNPS in the database
        List<TBJNPS> tBJNPSList = tBJNPSRepository.findAll();
        assertThat(tBJNPSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTBJNPS() throws Exception {
        int databaseSizeBeforeUpdate = tBJNPSRepository.findAll().size();
        tBJNPS.setJpscod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBJNPSMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBJNPS)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBJNPS in the database
        List<TBJNPS> tBJNPSList = tBJNPSRepository.findAll();
        assertThat(tBJNPSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTBJNPSWithPatch() throws Exception {
        // Initialize the database
        tBJNPS.setJpscod(UUID.randomUUID().toString());
        tBJNPSRepository.saveAndFlush(tBJNPS);

        int databaseSizeBeforeUpdate = tBJNPSRepository.findAll().size();

        // Update the tBJNPS using partial update
        TBJNPS partialUpdatedTBJNPS = new TBJNPS();
        partialUpdatedTBJNPS.setJpscod(tBJNPS.getJpscod());

        partialUpdatedTBJNPS.jpssts(UPDATED_JPSSTS).jpsnam(UPDATED_JPSNAM).jpslmd(UPDATED_JPSLMD).jpsuid(UPDATED_JPSUID);

        restTBJNPSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBJNPS.getJpscod())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBJNPS))
            )
            .andExpect(status().isOk());

        // Validate the TBJNPS in the database
        List<TBJNPS> tBJNPSList = tBJNPSRepository.findAll();
        assertThat(tBJNPSList).hasSize(databaseSizeBeforeUpdate);
        TBJNPS testTBJNPS = tBJNPSList.get(tBJNPSList.size() - 1);
        assertThat(testTBJNPS.getJpssts()).isEqualTo(UPDATED_JPSSTS);
        assertThat(testTBJNPS.getJpsnam()).isEqualTo(UPDATED_JPSNAM);
        assertThat(testTBJNPS.getJpslmd()).isEqualTo(UPDATED_JPSLMD);
        assertThat(testTBJNPS.getJpsuid()).isEqualTo(UPDATED_JPSUID);
    }

    @Test
    @Transactional
    void fullUpdateTBJNPSWithPatch() throws Exception {
        // Initialize the database
        tBJNPS.setJpscod(UUID.randomUUID().toString());
        tBJNPSRepository.saveAndFlush(tBJNPS);

        int databaseSizeBeforeUpdate = tBJNPSRepository.findAll().size();

        // Update the tBJNPS using partial update
        TBJNPS partialUpdatedTBJNPS = new TBJNPS();
        partialUpdatedTBJNPS.setJpscod(tBJNPS.getJpscod());

        partialUpdatedTBJNPS.jpssts(UPDATED_JPSSTS).jpsnam(UPDATED_JPSNAM).jpslmd(UPDATED_JPSLMD).jpsuid(UPDATED_JPSUID);

        restTBJNPSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBJNPS.getJpscod())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBJNPS))
            )
            .andExpect(status().isOk());

        // Validate the TBJNPS in the database
        List<TBJNPS> tBJNPSList = tBJNPSRepository.findAll();
        assertThat(tBJNPSList).hasSize(databaseSizeBeforeUpdate);
        TBJNPS testTBJNPS = tBJNPSList.get(tBJNPSList.size() - 1);
        assertThat(testTBJNPS.getJpssts()).isEqualTo(UPDATED_JPSSTS);
        assertThat(testTBJNPS.getJpsnam()).isEqualTo(UPDATED_JPSNAM);
        assertThat(testTBJNPS.getJpslmd()).isEqualTo(UPDATED_JPSLMD);
        assertThat(testTBJNPS.getJpsuid()).isEqualTo(UPDATED_JPSUID);
    }

    @Test
    @Transactional
    void patchNonExistingTBJNPS() throws Exception {
        int databaseSizeBeforeUpdate = tBJNPSRepository.findAll().size();
        tBJNPS.setJpscod(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBJNPSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tBJNPS.getJpscod())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBJNPS))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBJNPS in the database
        List<TBJNPS> tBJNPSList = tBJNPSRepository.findAll();
        assertThat(tBJNPSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTBJNPS() throws Exception {
        int databaseSizeBeforeUpdate = tBJNPSRepository.findAll().size();
        tBJNPS.setJpscod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBJNPSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBJNPS))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBJNPS in the database
        List<TBJNPS> tBJNPSList = tBJNPSRepository.findAll();
        assertThat(tBJNPSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTBJNPS() throws Exception {
        int databaseSizeBeforeUpdate = tBJNPSRepository.findAll().size();
        tBJNPS.setJpscod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBJNPSMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tBJNPS)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBJNPS in the database
        List<TBJNPS> tBJNPSList = tBJNPSRepository.findAll();
        assertThat(tBJNPSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTBJNPS() throws Exception {
        // Initialize the database
        tBJNPS.setJpscod(UUID.randomUUID().toString());
        tBJNPSRepository.saveAndFlush(tBJNPS);

        int databaseSizeBeforeDelete = tBJNPSRepository.findAll().size();

        // Delete the tBJNPS
        restTBJNPSMockMvc
            .perform(delete(ENTITY_API_URL_ID, tBJNPS.getJpscod()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TBJNPS> tBJNPSList = tBJNPSRepository.findAll();
        assertThat(tBJNPSList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
