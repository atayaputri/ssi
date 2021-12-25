package com.mycompany.ssi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.ssi.IntegrationTest;
import com.mycompany.ssi.domain.TBTYPS;
import com.mycompany.ssi.repository.TBTYPSRepository;
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
 * Integration tests for the {@link TBTYPSResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TBTYPSResourceIT {

    private static final String DEFAULT_TPSSTS = "AAAAAAAAAA";
    private static final String UPDATED_TPSSTS = "BBBBBBBBBB";

    private static final String DEFAULT_TPSNAM = "AAAAAAAAAA";
    private static final String UPDATED_TPSNAM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TPSLMD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TPSLMD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TPSUID = "AAAAAAAAAA";
    private static final String UPDATED_TPSUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tbtyps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{tpscod}";

    @Autowired
    private TBTYPSRepository tBTYPSRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTBTYPSMockMvc;

    private TBTYPS tBTYPS;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBTYPS createEntity(EntityManager em) {
        TBTYPS tBTYPS = new TBTYPS().tpssts(DEFAULT_TPSSTS).tpsnam(DEFAULT_TPSNAM).tpslmd(DEFAULT_TPSLMD).tpsuid(DEFAULT_TPSUID);
        return tBTYPS;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBTYPS createUpdatedEntity(EntityManager em) {
        TBTYPS tBTYPS = new TBTYPS().tpssts(UPDATED_TPSSTS).tpsnam(UPDATED_TPSNAM).tpslmd(UPDATED_TPSLMD).tpsuid(UPDATED_TPSUID);
        return tBTYPS;
    }

    @BeforeEach
    public void initTest() {
        tBTYPS = createEntity(em);
    }

    @Test
    @Transactional
    void createTBTYPS() throws Exception {
        int databaseSizeBeforeCreate = tBTYPSRepository.findAll().size();
        // Create the TBTYPS
        restTBTYPSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBTYPS)))
            .andExpect(status().isCreated());

        // Validate the TBTYPS in the database
        List<TBTYPS> tBTYPSList = tBTYPSRepository.findAll();
        assertThat(tBTYPSList).hasSize(databaseSizeBeforeCreate + 1);
        TBTYPS testTBTYPS = tBTYPSList.get(tBTYPSList.size() - 1);
        assertThat(testTBTYPS.getTpssts()).isEqualTo(DEFAULT_TPSSTS);
        assertThat(testTBTYPS.getTpsnam()).isEqualTo(DEFAULT_TPSNAM);
        assertThat(testTBTYPS.getTpslmd()).isEqualTo(DEFAULT_TPSLMD);
        assertThat(testTBTYPS.getTpsuid()).isEqualTo(DEFAULT_TPSUID);
    }

    @Test
    @Transactional
    void createTBTYPSWithExistingId() throws Exception {
        // Create the TBTYPS with an existing ID
        tBTYPS.setTpscod("existing_id");

        int databaseSizeBeforeCreate = tBTYPSRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTBTYPSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBTYPS)))
            .andExpect(status().isBadRequest());

        // Validate the TBTYPS in the database
        List<TBTYPS> tBTYPSList = tBTYPSRepository.findAll();
        assertThat(tBTYPSList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTpsstsIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBTYPSRepository.findAll().size();
        // set the field null
        tBTYPS.setTpssts(null);

        // Create the TBTYPS, which fails.

        restTBTYPSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBTYPS)))
            .andExpect(status().isBadRequest());

        List<TBTYPS> tBTYPSList = tBTYPSRepository.findAll();
        assertThat(tBTYPSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTpsnamIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBTYPSRepository.findAll().size();
        // set the field null
        tBTYPS.setTpsnam(null);

        // Create the TBTYPS, which fails.

        restTBTYPSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBTYPS)))
            .andExpect(status().isBadRequest());

        List<TBTYPS> tBTYPSList = tBTYPSRepository.findAll();
        assertThat(tBTYPSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTpslmdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBTYPSRepository.findAll().size();
        // set the field null
        tBTYPS.setTpslmd(null);

        // Create the TBTYPS, which fails.

        restTBTYPSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBTYPS)))
            .andExpect(status().isBadRequest());

        List<TBTYPS> tBTYPSList = tBTYPSRepository.findAll();
        assertThat(tBTYPSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTpsuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBTYPSRepository.findAll().size();
        // set the field null
        tBTYPS.setTpsuid(null);

        // Create the TBTYPS, which fails.

        restTBTYPSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBTYPS)))
            .andExpect(status().isBadRequest());

        List<TBTYPS> tBTYPSList = tBTYPSRepository.findAll();
        assertThat(tBTYPSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTBTYPS() throws Exception {
        // Initialize the database
        tBTYPS.setTpscod(UUID.randomUUID().toString());
        tBTYPSRepository.saveAndFlush(tBTYPS);

        // Get all the tBTYPSList
        restTBTYPSMockMvc
            .perform(get(ENTITY_API_URL + "?sort=tpscod,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].tpscod").value(hasItem(tBTYPS.getTpscod())))
            .andExpect(jsonPath("$.[*].tpssts").value(hasItem(DEFAULT_TPSSTS)))
            .andExpect(jsonPath("$.[*].tpsnam").value(hasItem(DEFAULT_TPSNAM)))
            .andExpect(jsonPath("$.[*].tpslmd").value(hasItem(DEFAULT_TPSLMD.toString())))
            .andExpect(jsonPath("$.[*].tpsuid").value(hasItem(DEFAULT_TPSUID)));
    }

    @Test
    @Transactional
    void getTBTYPS() throws Exception {
        // Initialize the database
        tBTYPS.setTpscod(UUID.randomUUID().toString());
        tBTYPSRepository.saveAndFlush(tBTYPS);

        // Get the tBTYPS
        restTBTYPSMockMvc
            .perform(get(ENTITY_API_URL_ID, tBTYPS.getTpscod()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.tpscod").value(tBTYPS.getTpscod()))
            .andExpect(jsonPath("$.tpssts").value(DEFAULT_TPSSTS))
            .andExpect(jsonPath("$.tpsnam").value(DEFAULT_TPSNAM))
            .andExpect(jsonPath("$.tpslmd").value(DEFAULT_TPSLMD.toString()))
            .andExpect(jsonPath("$.tpsuid").value(DEFAULT_TPSUID));
    }

    @Test
    @Transactional
    void getNonExistingTBTYPS() throws Exception {
        // Get the tBTYPS
        restTBTYPSMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTBTYPS() throws Exception {
        // Initialize the database
        tBTYPS.setTpscod(UUID.randomUUID().toString());
        tBTYPSRepository.saveAndFlush(tBTYPS);

        int databaseSizeBeforeUpdate = tBTYPSRepository.findAll().size();

        // Update the tBTYPS
        TBTYPS updatedTBTYPS = tBTYPSRepository.findById(tBTYPS.getTpscod()).get();
        // Disconnect from session so that the updates on updatedTBTYPS are not directly saved in db
        em.detach(updatedTBTYPS);
        updatedTBTYPS.tpssts(UPDATED_TPSSTS).tpsnam(UPDATED_TPSNAM).tpslmd(UPDATED_TPSLMD).tpsuid(UPDATED_TPSUID);

        restTBTYPSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTBTYPS.getTpscod())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTBTYPS))
            )
            .andExpect(status().isOk());

        // Validate the TBTYPS in the database
        List<TBTYPS> tBTYPSList = tBTYPSRepository.findAll();
        assertThat(tBTYPSList).hasSize(databaseSizeBeforeUpdate);
        TBTYPS testTBTYPS = tBTYPSList.get(tBTYPSList.size() - 1);
        assertThat(testTBTYPS.getTpssts()).isEqualTo(UPDATED_TPSSTS);
        assertThat(testTBTYPS.getTpsnam()).isEqualTo(UPDATED_TPSNAM);
        assertThat(testTBTYPS.getTpslmd()).isEqualTo(UPDATED_TPSLMD);
        assertThat(testTBTYPS.getTpsuid()).isEqualTo(UPDATED_TPSUID);
    }

    @Test
    @Transactional
    void putNonExistingTBTYPS() throws Exception {
        int databaseSizeBeforeUpdate = tBTYPSRepository.findAll().size();
        tBTYPS.setTpscod(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBTYPSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tBTYPS.getTpscod())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBTYPS))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBTYPS in the database
        List<TBTYPS> tBTYPSList = tBTYPSRepository.findAll();
        assertThat(tBTYPSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTBTYPS() throws Exception {
        int databaseSizeBeforeUpdate = tBTYPSRepository.findAll().size();
        tBTYPS.setTpscod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBTYPSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBTYPS))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBTYPS in the database
        List<TBTYPS> tBTYPSList = tBTYPSRepository.findAll();
        assertThat(tBTYPSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTBTYPS() throws Exception {
        int databaseSizeBeforeUpdate = tBTYPSRepository.findAll().size();
        tBTYPS.setTpscod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBTYPSMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBTYPS)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBTYPS in the database
        List<TBTYPS> tBTYPSList = tBTYPSRepository.findAll();
        assertThat(tBTYPSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTBTYPSWithPatch() throws Exception {
        // Initialize the database
        tBTYPS.setTpscod(UUID.randomUUID().toString());
        tBTYPSRepository.saveAndFlush(tBTYPS);

        int databaseSizeBeforeUpdate = tBTYPSRepository.findAll().size();

        // Update the tBTYPS using partial update
        TBTYPS partialUpdatedTBTYPS = new TBTYPS();
        partialUpdatedTBTYPS.setTpscod(tBTYPS.getTpscod());

        restTBTYPSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBTYPS.getTpscod())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBTYPS))
            )
            .andExpect(status().isOk());

        // Validate the TBTYPS in the database
        List<TBTYPS> tBTYPSList = tBTYPSRepository.findAll();
        assertThat(tBTYPSList).hasSize(databaseSizeBeforeUpdate);
        TBTYPS testTBTYPS = tBTYPSList.get(tBTYPSList.size() - 1);
        assertThat(testTBTYPS.getTpssts()).isEqualTo(DEFAULT_TPSSTS);
        assertThat(testTBTYPS.getTpsnam()).isEqualTo(DEFAULT_TPSNAM);
        assertThat(testTBTYPS.getTpslmd()).isEqualTo(DEFAULT_TPSLMD);
        assertThat(testTBTYPS.getTpsuid()).isEqualTo(DEFAULT_TPSUID);
    }

    @Test
    @Transactional
    void fullUpdateTBTYPSWithPatch() throws Exception {
        // Initialize the database
        tBTYPS.setTpscod(UUID.randomUUID().toString());
        tBTYPSRepository.saveAndFlush(tBTYPS);

        int databaseSizeBeforeUpdate = tBTYPSRepository.findAll().size();

        // Update the tBTYPS using partial update
        TBTYPS partialUpdatedTBTYPS = new TBTYPS();
        partialUpdatedTBTYPS.setTpscod(tBTYPS.getTpscod());

        partialUpdatedTBTYPS.tpssts(UPDATED_TPSSTS).tpsnam(UPDATED_TPSNAM).tpslmd(UPDATED_TPSLMD).tpsuid(UPDATED_TPSUID);

        restTBTYPSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBTYPS.getTpscod())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBTYPS))
            )
            .andExpect(status().isOk());

        // Validate the TBTYPS in the database
        List<TBTYPS> tBTYPSList = tBTYPSRepository.findAll();
        assertThat(tBTYPSList).hasSize(databaseSizeBeforeUpdate);
        TBTYPS testTBTYPS = tBTYPSList.get(tBTYPSList.size() - 1);
        assertThat(testTBTYPS.getTpssts()).isEqualTo(UPDATED_TPSSTS);
        assertThat(testTBTYPS.getTpsnam()).isEqualTo(UPDATED_TPSNAM);
        assertThat(testTBTYPS.getTpslmd()).isEqualTo(UPDATED_TPSLMD);
        assertThat(testTBTYPS.getTpsuid()).isEqualTo(UPDATED_TPSUID);
    }

    @Test
    @Transactional
    void patchNonExistingTBTYPS() throws Exception {
        int databaseSizeBeforeUpdate = tBTYPSRepository.findAll().size();
        tBTYPS.setTpscod(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBTYPSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tBTYPS.getTpscod())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBTYPS))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBTYPS in the database
        List<TBTYPS> tBTYPSList = tBTYPSRepository.findAll();
        assertThat(tBTYPSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTBTYPS() throws Exception {
        int databaseSizeBeforeUpdate = tBTYPSRepository.findAll().size();
        tBTYPS.setTpscod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBTYPSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBTYPS))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBTYPS in the database
        List<TBTYPS> tBTYPSList = tBTYPSRepository.findAll();
        assertThat(tBTYPSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTBTYPS() throws Exception {
        int databaseSizeBeforeUpdate = tBTYPSRepository.findAll().size();
        tBTYPS.setTpscod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBTYPSMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tBTYPS)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBTYPS in the database
        List<TBTYPS> tBTYPSList = tBTYPSRepository.findAll();
        assertThat(tBTYPSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTBTYPS() throws Exception {
        // Initialize the database
        tBTYPS.setTpscod(UUID.randomUUID().toString());
        tBTYPSRepository.saveAndFlush(tBTYPS);

        int databaseSizeBeforeDelete = tBTYPSRepository.findAll().size();

        // Delete the tBTYPS
        restTBTYPSMockMvc
            .perform(delete(ENTITY_API_URL_ID, tBTYPS.getTpscod()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TBTYPS> tBTYPSList = tBTYPSRepository.findAll();
        assertThat(tBTYPSList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
