package com.mycompany.ssi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.ssi.IntegrationTest;
import com.mycompany.ssi.domain.MAPSKS;
import com.mycompany.ssi.domain.enumeration.StatusSKS;
import com.mycompany.ssi.repository.MAPSKSRepository;
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
 * Integration tests for the {@link MAPSKSResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MAPSKSResourceIT {

    private static final StatusSKS DEFAULT_MSKSTS = StatusSKS.A;
    private static final StatusSKS UPDATED_MSKSTS = StatusSKS.U;

    private static final String ENTITY_API_URL = "/api/mapsks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MAPSKSRepository mAPSKSRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMAPSKSMockMvc;

    private MAPSKS mAPSKS;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAPSKS createEntity(EntityManager em) {
        MAPSKS mAPSKS = new MAPSKS().msksts(DEFAULT_MSKSTS);
        return mAPSKS;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAPSKS createUpdatedEntity(EntityManager em) {
        MAPSKS mAPSKS = new MAPSKS().msksts(UPDATED_MSKSTS);
        return mAPSKS;
    }

    @BeforeEach
    public void initTest() {
        mAPSKS = createEntity(em);
    }

    @Test
    @Transactional
    void createMAPSKS() throws Exception {
        int databaseSizeBeforeCreate = mAPSKSRepository.findAll().size();
        // Create the MAPSKS
        restMAPSKSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mAPSKS)))
            .andExpect(status().isCreated());

        // Validate the MAPSKS in the database
        List<MAPSKS> mAPSKSList = mAPSKSRepository.findAll();
        assertThat(mAPSKSList).hasSize(databaseSizeBeforeCreate + 1);
        MAPSKS testMAPSKS = mAPSKSList.get(mAPSKSList.size() - 1);
        assertThat(testMAPSKS.getMsksts()).isEqualTo(DEFAULT_MSKSTS);
    }

    @Test
    @Transactional
    void createMAPSKSWithExistingId() throws Exception {
        // Create the MAPSKS with an existing ID
        mAPSKS.setId(1L);

        int databaseSizeBeforeCreate = mAPSKSRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMAPSKSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mAPSKS)))
            .andExpect(status().isBadRequest());

        // Validate the MAPSKS in the database
        List<MAPSKS> mAPSKSList = mAPSKSRepository.findAll();
        assertThat(mAPSKSList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMskstsIsRequired() throws Exception {
        int databaseSizeBeforeTest = mAPSKSRepository.findAll().size();
        // set the field null
        mAPSKS.setMsksts(null);

        // Create the MAPSKS, which fails.

        restMAPSKSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mAPSKS)))
            .andExpect(status().isBadRequest());

        List<MAPSKS> mAPSKSList = mAPSKSRepository.findAll();
        assertThat(mAPSKSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMAPSKS() throws Exception {
        // Initialize the database
        mAPSKSRepository.saveAndFlush(mAPSKS);

        // Get all the mAPSKSList
        restMAPSKSMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAPSKS.getId().intValue())))
            .andExpect(jsonPath("$.[*].msksts").value(hasItem(DEFAULT_MSKSTS.toString())));
    }

    @Test
    @Transactional
    void getMAPSKS() throws Exception {
        // Initialize the database
        mAPSKSRepository.saveAndFlush(mAPSKS);

        // Get the mAPSKS
        restMAPSKSMockMvc
            .perform(get(ENTITY_API_URL_ID, mAPSKS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mAPSKS.getId().intValue()))
            .andExpect(jsonPath("$.msksts").value(DEFAULT_MSKSTS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMAPSKS() throws Exception {
        // Get the mAPSKS
        restMAPSKSMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMAPSKS() throws Exception {
        // Initialize the database
        mAPSKSRepository.saveAndFlush(mAPSKS);

        int databaseSizeBeforeUpdate = mAPSKSRepository.findAll().size();

        // Update the mAPSKS
        MAPSKS updatedMAPSKS = mAPSKSRepository.findById(mAPSKS.getId()).get();
        // Disconnect from session so that the updates on updatedMAPSKS are not directly saved in db
        em.detach(updatedMAPSKS);
        updatedMAPSKS.msksts(UPDATED_MSKSTS);

        restMAPSKSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMAPSKS.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMAPSKS))
            )
            .andExpect(status().isOk());

        // Validate the MAPSKS in the database
        List<MAPSKS> mAPSKSList = mAPSKSRepository.findAll();
        assertThat(mAPSKSList).hasSize(databaseSizeBeforeUpdate);
        MAPSKS testMAPSKS = mAPSKSList.get(mAPSKSList.size() - 1);
        assertThat(testMAPSKS.getMsksts()).isEqualTo(UPDATED_MSKSTS);
    }

    @Test
    @Transactional
    void putNonExistingMAPSKS() throws Exception {
        int databaseSizeBeforeUpdate = mAPSKSRepository.findAll().size();
        mAPSKS.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMAPSKSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mAPSKS.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mAPSKS))
            )
            .andExpect(status().isBadRequest());

        // Validate the MAPSKS in the database
        List<MAPSKS> mAPSKSList = mAPSKSRepository.findAll();
        assertThat(mAPSKSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMAPSKS() throws Exception {
        int databaseSizeBeforeUpdate = mAPSKSRepository.findAll().size();
        mAPSKS.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMAPSKSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mAPSKS))
            )
            .andExpect(status().isBadRequest());

        // Validate the MAPSKS in the database
        List<MAPSKS> mAPSKSList = mAPSKSRepository.findAll();
        assertThat(mAPSKSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMAPSKS() throws Exception {
        int databaseSizeBeforeUpdate = mAPSKSRepository.findAll().size();
        mAPSKS.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMAPSKSMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mAPSKS)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MAPSKS in the database
        List<MAPSKS> mAPSKSList = mAPSKSRepository.findAll();
        assertThat(mAPSKSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMAPSKSWithPatch() throws Exception {
        // Initialize the database
        mAPSKSRepository.saveAndFlush(mAPSKS);

        int databaseSizeBeforeUpdate = mAPSKSRepository.findAll().size();

        // Update the mAPSKS using partial update
        MAPSKS partialUpdatedMAPSKS = new MAPSKS();
        partialUpdatedMAPSKS.setId(mAPSKS.getId());

        restMAPSKSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMAPSKS.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMAPSKS))
            )
            .andExpect(status().isOk());

        // Validate the MAPSKS in the database
        List<MAPSKS> mAPSKSList = mAPSKSRepository.findAll();
        assertThat(mAPSKSList).hasSize(databaseSizeBeforeUpdate);
        MAPSKS testMAPSKS = mAPSKSList.get(mAPSKSList.size() - 1);
        assertThat(testMAPSKS.getMsksts()).isEqualTo(DEFAULT_MSKSTS);
    }

    @Test
    @Transactional
    void fullUpdateMAPSKSWithPatch() throws Exception {
        // Initialize the database
        mAPSKSRepository.saveAndFlush(mAPSKS);

        int databaseSizeBeforeUpdate = mAPSKSRepository.findAll().size();

        // Update the mAPSKS using partial update
        MAPSKS partialUpdatedMAPSKS = new MAPSKS();
        partialUpdatedMAPSKS.setId(mAPSKS.getId());

        partialUpdatedMAPSKS.msksts(UPDATED_MSKSTS);

        restMAPSKSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMAPSKS.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMAPSKS))
            )
            .andExpect(status().isOk());

        // Validate the MAPSKS in the database
        List<MAPSKS> mAPSKSList = mAPSKSRepository.findAll();
        assertThat(mAPSKSList).hasSize(databaseSizeBeforeUpdate);
        MAPSKS testMAPSKS = mAPSKSList.get(mAPSKSList.size() - 1);
        assertThat(testMAPSKS.getMsksts()).isEqualTo(UPDATED_MSKSTS);
    }

    @Test
    @Transactional
    void patchNonExistingMAPSKS() throws Exception {
        int databaseSizeBeforeUpdate = mAPSKSRepository.findAll().size();
        mAPSKS.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMAPSKSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mAPSKS.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mAPSKS))
            )
            .andExpect(status().isBadRequest());

        // Validate the MAPSKS in the database
        List<MAPSKS> mAPSKSList = mAPSKSRepository.findAll();
        assertThat(mAPSKSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMAPSKS() throws Exception {
        int databaseSizeBeforeUpdate = mAPSKSRepository.findAll().size();
        mAPSKS.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMAPSKSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mAPSKS))
            )
            .andExpect(status().isBadRequest());

        // Validate the MAPSKS in the database
        List<MAPSKS> mAPSKSList = mAPSKSRepository.findAll();
        assertThat(mAPSKSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMAPSKS() throws Exception {
        int databaseSizeBeforeUpdate = mAPSKSRepository.findAll().size();
        mAPSKS.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMAPSKSMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mAPSKS)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MAPSKS in the database
        List<MAPSKS> mAPSKSList = mAPSKSRepository.findAll();
        assertThat(mAPSKSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMAPSKS() throws Exception {
        // Initialize the database
        mAPSKSRepository.saveAndFlush(mAPSKS);

        int databaseSizeBeforeDelete = mAPSKSRepository.findAll().size();

        // Delete the mAPSKS
        restMAPSKSMockMvc
            .perform(delete(ENTITY_API_URL_ID, mAPSKS.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MAPSKS> mAPSKSList = mAPSKSRepository.findAll();
        assertThat(mAPSKSList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
