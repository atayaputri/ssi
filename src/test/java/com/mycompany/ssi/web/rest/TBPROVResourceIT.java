package com.mycompany.ssi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.ssi.IntegrationTest;
import com.mycompany.ssi.domain.TBPROV;
import com.mycompany.ssi.repository.TBPROVRepository;
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
 * Integration tests for the {@link TBPROVResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TBPROVResourceIT {

    private static final String DEFAULT_PROVSTS = "AAAAAAAAAA";
    private static final String UPDATED_PROVSTS = "BBBBBBBBBB";

    private static final String DEFAULT_PROVNAM = "AAAAAAAAAA";
    private static final String UPDATED_PROVNAM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PROVLMD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PROVLMD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PROVUID = "AAAAAAAAAA";
    private static final String UPDATED_PROVUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tbprovs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{provcod}";

    @Autowired
    private TBPROVRepository tBPROVRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTBPROVMockMvc;

    private TBPROV tBPROV;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBPROV createEntity(EntityManager em) {
        TBPROV tBPROV = new TBPROV().provsts(DEFAULT_PROVSTS).provnam(DEFAULT_PROVNAM).provlmd(DEFAULT_PROVLMD).provuid(DEFAULT_PROVUID);
        return tBPROV;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBPROV createUpdatedEntity(EntityManager em) {
        TBPROV tBPROV = new TBPROV().provsts(UPDATED_PROVSTS).provnam(UPDATED_PROVNAM).provlmd(UPDATED_PROVLMD).provuid(UPDATED_PROVUID);
        return tBPROV;
    }

    @BeforeEach
    public void initTest() {
        tBPROV = createEntity(em);
    }

    @Test
    @Transactional
    void createTBPROV() throws Exception {
        int databaseSizeBeforeCreate = tBPROVRepository.findAll().size();
        // Create the TBPROV
        restTBPROVMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBPROV)))
            .andExpect(status().isCreated());

        // Validate the TBPROV in the database
        List<TBPROV> tBPROVList = tBPROVRepository.findAll();
        assertThat(tBPROVList).hasSize(databaseSizeBeforeCreate + 1);
        TBPROV testTBPROV = tBPROVList.get(tBPROVList.size() - 1);
        assertThat(testTBPROV.getProvsts()).isEqualTo(DEFAULT_PROVSTS);
        assertThat(testTBPROV.getProvnam()).isEqualTo(DEFAULT_PROVNAM);
        assertThat(testTBPROV.getProvlmd()).isEqualTo(DEFAULT_PROVLMD);
        assertThat(testTBPROV.getProvuid()).isEqualTo(DEFAULT_PROVUID);
    }

    @Test
    @Transactional
    void createTBPROVWithExistingId() throws Exception {
        // Create the TBPROV with an existing ID
        tBPROV.setProvcod("existing_id");

        int databaseSizeBeforeCreate = tBPROVRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTBPROVMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBPROV)))
            .andExpect(status().isBadRequest());

        // Validate the TBPROV in the database
        List<TBPROV> tBPROVList = tBPROVRepository.findAll();
        assertThat(tBPROVList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkProvstsIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBPROVRepository.findAll().size();
        // set the field null
        tBPROV.setProvsts(null);

        // Create the TBPROV, which fails.

        restTBPROVMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBPROV)))
            .andExpect(status().isBadRequest());

        List<TBPROV> tBPROVList = tBPROVRepository.findAll();
        assertThat(tBPROVList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProvnamIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBPROVRepository.findAll().size();
        // set the field null
        tBPROV.setProvnam(null);

        // Create the TBPROV, which fails.

        restTBPROVMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBPROV)))
            .andExpect(status().isBadRequest());

        List<TBPROV> tBPROVList = tBPROVRepository.findAll();
        assertThat(tBPROVList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProvlmdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBPROVRepository.findAll().size();
        // set the field null
        tBPROV.setProvlmd(null);

        // Create the TBPROV, which fails.

        restTBPROVMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBPROV)))
            .andExpect(status().isBadRequest());

        List<TBPROV> tBPROVList = tBPROVRepository.findAll();
        assertThat(tBPROVList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProvuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBPROVRepository.findAll().size();
        // set the field null
        tBPROV.setProvuid(null);

        // Create the TBPROV, which fails.

        restTBPROVMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBPROV)))
            .andExpect(status().isBadRequest());

        List<TBPROV> tBPROVList = tBPROVRepository.findAll();
        assertThat(tBPROVList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTBPROVS() throws Exception {
        // Initialize the database
        tBPROV.setProvcod(UUID.randomUUID().toString());
        tBPROVRepository.saveAndFlush(tBPROV);

        // Get all the tBPROVList
        restTBPROVMockMvc
            .perform(get(ENTITY_API_URL + "?sort=provcod,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].provcod").value(hasItem(tBPROV.getProvcod())))
            .andExpect(jsonPath("$.[*].provsts").value(hasItem(DEFAULT_PROVSTS)))
            .andExpect(jsonPath("$.[*].provnam").value(hasItem(DEFAULT_PROVNAM)))
            .andExpect(jsonPath("$.[*].provlmd").value(hasItem(DEFAULT_PROVLMD.toString())))
            .andExpect(jsonPath("$.[*].provuid").value(hasItem(DEFAULT_PROVUID)));
    }

    @Test
    @Transactional
    void getTBPROV() throws Exception {
        // Initialize the database
        tBPROV.setProvcod(UUID.randomUUID().toString());
        tBPROVRepository.saveAndFlush(tBPROV);

        // Get the tBPROV
        restTBPROVMockMvc
            .perform(get(ENTITY_API_URL_ID, tBPROV.getProvcod()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.provcod").value(tBPROV.getProvcod()))
            .andExpect(jsonPath("$.provsts").value(DEFAULT_PROVSTS))
            .andExpect(jsonPath("$.provnam").value(DEFAULT_PROVNAM))
            .andExpect(jsonPath("$.provlmd").value(DEFAULT_PROVLMD.toString()))
            .andExpect(jsonPath("$.provuid").value(DEFAULT_PROVUID));
    }

    @Test
    @Transactional
    void getNonExistingTBPROV() throws Exception {
        // Get the tBPROV
        restTBPROVMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTBPROV() throws Exception {
        // Initialize the database
        tBPROV.setProvcod(UUID.randomUUID().toString());
        tBPROVRepository.saveAndFlush(tBPROV);

        int databaseSizeBeforeUpdate = tBPROVRepository.findAll().size();

        // Update the tBPROV
        TBPROV updatedTBPROV = tBPROVRepository.findById(tBPROV.getProvcod()).get();
        // Disconnect from session so that the updates on updatedTBPROV are not directly saved in db
        em.detach(updatedTBPROV);
        updatedTBPROV.provsts(UPDATED_PROVSTS).provnam(UPDATED_PROVNAM).provlmd(UPDATED_PROVLMD).provuid(UPDATED_PROVUID);

        restTBPROVMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTBPROV.getProvcod())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTBPROV))
            )
            .andExpect(status().isOk());

        // Validate the TBPROV in the database
        List<TBPROV> tBPROVList = tBPROVRepository.findAll();
        assertThat(tBPROVList).hasSize(databaseSizeBeforeUpdate);
        TBPROV testTBPROV = tBPROVList.get(tBPROVList.size() - 1);
        assertThat(testTBPROV.getProvsts()).isEqualTo(UPDATED_PROVSTS);
        assertThat(testTBPROV.getProvnam()).isEqualTo(UPDATED_PROVNAM);
        assertThat(testTBPROV.getProvlmd()).isEqualTo(UPDATED_PROVLMD);
        assertThat(testTBPROV.getProvuid()).isEqualTo(UPDATED_PROVUID);
    }

    @Test
    @Transactional
    void putNonExistingTBPROV() throws Exception {
        int databaseSizeBeforeUpdate = tBPROVRepository.findAll().size();
        tBPROV.setProvcod(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBPROVMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tBPROV.getProvcod())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBPROV))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBPROV in the database
        List<TBPROV> tBPROVList = tBPROVRepository.findAll();
        assertThat(tBPROVList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTBPROV() throws Exception {
        int databaseSizeBeforeUpdate = tBPROVRepository.findAll().size();
        tBPROV.setProvcod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBPROVMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBPROV))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBPROV in the database
        List<TBPROV> tBPROVList = tBPROVRepository.findAll();
        assertThat(tBPROVList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTBPROV() throws Exception {
        int databaseSizeBeforeUpdate = tBPROVRepository.findAll().size();
        tBPROV.setProvcod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBPROVMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBPROV)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBPROV in the database
        List<TBPROV> tBPROVList = tBPROVRepository.findAll();
        assertThat(tBPROVList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTBPROVWithPatch() throws Exception {
        // Initialize the database
        tBPROV.setProvcod(UUID.randomUUID().toString());
        tBPROVRepository.saveAndFlush(tBPROV);

        int databaseSizeBeforeUpdate = tBPROVRepository.findAll().size();

        // Update the tBPROV using partial update
        TBPROV partialUpdatedTBPROV = new TBPROV();
        partialUpdatedTBPROV.setProvcod(tBPROV.getProvcod());

        partialUpdatedTBPROV.provsts(UPDATED_PROVSTS).provnam(UPDATED_PROVNAM);

        restTBPROVMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBPROV.getProvcod())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBPROV))
            )
            .andExpect(status().isOk());

        // Validate the TBPROV in the database
        List<TBPROV> tBPROVList = tBPROVRepository.findAll();
        assertThat(tBPROVList).hasSize(databaseSizeBeforeUpdate);
        TBPROV testTBPROV = tBPROVList.get(tBPROVList.size() - 1);
        assertThat(testTBPROV.getProvsts()).isEqualTo(UPDATED_PROVSTS);
        assertThat(testTBPROV.getProvnam()).isEqualTo(UPDATED_PROVNAM);
        assertThat(testTBPROV.getProvlmd()).isEqualTo(DEFAULT_PROVLMD);
        assertThat(testTBPROV.getProvuid()).isEqualTo(DEFAULT_PROVUID);
    }

    @Test
    @Transactional
    void fullUpdateTBPROVWithPatch() throws Exception {
        // Initialize the database
        tBPROV.setProvcod(UUID.randomUUID().toString());
        tBPROVRepository.saveAndFlush(tBPROV);

        int databaseSizeBeforeUpdate = tBPROVRepository.findAll().size();

        // Update the tBPROV using partial update
        TBPROV partialUpdatedTBPROV = new TBPROV();
        partialUpdatedTBPROV.setProvcod(tBPROV.getProvcod());

        partialUpdatedTBPROV.provsts(UPDATED_PROVSTS).provnam(UPDATED_PROVNAM).provlmd(UPDATED_PROVLMD).provuid(UPDATED_PROVUID);

        restTBPROVMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBPROV.getProvcod())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBPROV))
            )
            .andExpect(status().isOk());

        // Validate the TBPROV in the database
        List<TBPROV> tBPROVList = tBPROVRepository.findAll();
        assertThat(tBPROVList).hasSize(databaseSizeBeforeUpdate);
        TBPROV testTBPROV = tBPROVList.get(tBPROVList.size() - 1);
        assertThat(testTBPROV.getProvsts()).isEqualTo(UPDATED_PROVSTS);
        assertThat(testTBPROV.getProvnam()).isEqualTo(UPDATED_PROVNAM);
        assertThat(testTBPROV.getProvlmd()).isEqualTo(UPDATED_PROVLMD);
        assertThat(testTBPROV.getProvuid()).isEqualTo(UPDATED_PROVUID);
    }

    @Test
    @Transactional
    void patchNonExistingTBPROV() throws Exception {
        int databaseSizeBeforeUpdate = tBPROVRepository.findAll().size();
        tBPROV.setProvcod(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBPROVMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tBPROV.getProvcod())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBPROV))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBPROV in the database
        List<TBPROV> tBPROVList = tBPROVRepository.findAll();
        assertThat(tBPROVList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTBPROV() throws Exception {
        int databaseSizeBeforeUpdate = tBPROVRepository.findAll().size();
        tBPROV.setProvcod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBPROVMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBPROV))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBPROV in the database
        List<TBPROV> tBPROVList = tBPROVRepository.findAll();
        assertThat(tBPROVList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTBPROV() throws Exception {
        int databaseSizeBeforeUpdate = tBPROVRepository.findAll().size();
        tBPROV.setProvcod(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBPROVMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tBPROV)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBPROV in the database
        List<TBPROV> tBPROVList = tBPROVRepository.findAll();
        assertThat(tBPROVList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTBPROV() throws Exception {
        // Initialize the database
        tBPROV.setProvcod(UUID.randomUUID().toString());
        tBPROVRepository.saveAndFlush(tBPROV);

        int databaseSizeBeforeDelete = tBPROVRepository.findAll().size();

        // Delete the tBPROV
        restTBPROVMockMvc
            .perform(delete(ENTITY_API_URL_ID, tBPROV.getProvcod()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TBPROV> tBPROVList = tBPROVRepository.findAll();
        assertThat(tBPROVList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
