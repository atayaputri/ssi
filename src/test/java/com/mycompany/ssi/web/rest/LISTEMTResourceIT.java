package com.mycompany.ssi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.ssi.IntegrationTest;
import com.mycompany.ssi.domain.LISTEMT;
import com.mycompany.ssi.repository.LISTEMTRepository;
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
 * Integration tests for the {@link LISTEMTResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LISTEMTResourceIT {

    private static final String DEFAULT_LISCODE = "AAAA";
    private static final String UPDATED_LISCODE = "BBBB";

    private static final String DEFAULT_LISNAM = "AAAAAAAAAA";
    private static final String UPDATED_LISNAM = "BBBBBBBBBB";

    private static final String DEFAULT_LISDIR = "AAAAAAAAAA";
    private static final String UPDATED_LISDIR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/listemts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LISTEMTRepository lISTEMTRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLISTEMTMockMvc;

    private LISTEMT lISTEMT;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LISTEMT createEntity(EntityManager em) {
        LISTEMT lISTEMT = new LISTEMT().liscode(DEFAULT_LISCODE).lisnam(DEFAULT_LISNAM).lisdir(DEFAULT_LISDIR);
        return lISTEMT;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LISTEMT createUpdatedEntity(EntityManager em) {
        LISTEMT lISTEMT = new LISTEMT().liscode(UPDATED_LISCODE).lisnam(UPDATED_LISNAM).lisdir(UPDATED_LISDIR);
        return lISTEMT;
    }

    @BeforeEach
    public void initTest() {
        lISTEMT = createEntity(em);
    }

    @Test
    @Transactional
    void createLISTEMT() throws Exception {
        int databaseSizeBeforeCreate = lISTEMTRepository.findAll().size();
        // Create the LISTEMT
        restLISTEMTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lISTEMT)))
            .andExpect(status().isCreated());

        // Validate the LISTEMT in the database
        List<LISTEMT> lISTEMTList = lISTEMTRepository.findAll();
        assertThat(lISTEMTList).hasSize(databaseSizeBeforeCreate + 1);
        LISTEMT testLISTEMT = lISTEMTList.get(lISTEMTList.size() - 1);
        assertThat(testLISTEMT.getLiscode()).isEqualTo(DEFAULT_LISCODE);
        assertThat(testLISTEMT.getLisnam()).isEqualTo(DEFAULT_LISNAM);
        assertThat(testLISTEMT.getLisdir()).isEqualTo(DEFAULT_LISDIR);
    }

    @Test
    @Transactional
    void createLISTEMTWithExistingId() throws Exception {
        // Create the LISTEMT with an existing ID
        lISTEMT.setId(1L);

        int databaseSizeBeforeCreate = lISTEMTRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLISTEMTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lISTEMT)))
            .andExpect(status().isBadRequest());

        // Validate the LISTEMT in the database
        List<LISTEMT> lISTEMTList = lISTEMTRepository.findAll();
        assertThat(lISTEMTList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLiscodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lISTEMTRepository.findAll().size();
        // set the field null
        lISTEMT.setLiscode(null);

        // Create the LISTEMT, which fails.

        restLISTEMTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lISTEMT)))
            .andExpect(status().isBadRequest());

        List<LISTEMT> lISTEMTList = lISTEMTRepository.findAll();
        assertThat(lISTEMTList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLisnamIsRequired() throws Exception {
        int databaseSizeBeforeTest = lISTEMTRepository.findAll().size();
        // set the field null
        lISTEMT.setLisnam(null);

        // Create the LISTEMT, which fails.

        restLISTEMTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lISTEMT)))
            .andExpect(status().isBadRequest());

        List<LISTEMT> lISTEMTList = lISTEMTRepository.findAll();
        assertThat(lISTEMTList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLisdirIsRequired() throws Exception {
        int databaseSizeBeforeTest = lISTEMTRepository.findAll().size();
        // set the field null
        lISTEMT.setLisdir(null);

        // Create the LISTEMT, which fails.

        restLISTEMTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lISTEMT)))
            .andExpect(status().isBadRequest());

        List<LISTEMT> lISTEMTList = lISTEMTRepository.findAll();
        assertThat(lISTEMTList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLISTEMTS() throws Exception {
        // Initialize the database
        lISTEMTRepository.saveAndFlush(lISTEMT);

        // Get all the lISTEMTList
        restLISTEMTMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lISTEMT.getId().intValue())))
            .andExpect(jsonPath("$.[*].liscode").value(hasItem(DEFAULT_LISCODE)))
            .andExpect(jsonPath("$.[*].lisnam").value(hasItem(DEFAULT_LISNAM)))
            .andExpect(jsonPath("$.[*].lisdir").value(hasItem(DEFAULT_LISDIR)));
    }

    @Test
    @Transactional
    void getLISTEMT() throws Exception {
        // Initialize the database
        lISTEMTRepository.saveAndFlush(lISTEMT);

        // Get the lISTEMT
        restLISTEMTMockMvc
            .perform(get(ENTITY_API_URL_ID, lISTEMT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lISTEMT.getId().intValue()))
            .andExpect(jsonPath("$.liscode").value(DEFAULT_LISCODE))
            .andExpect(jsonPath("$.lisnam").value(DEFAULT_LISNAM))
            .andExpect(jsonPath("$.lisdir").value(DEFAULT_LISDIR));
    }

    @Test
    @Transactional
    void getNonExistingLISTEMT() throws Exception {
        // Get the lISTEMT
        restLISTEMTMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLISTEMT() throws Exception {
        // Initialize the database
        lISTEMTRepository.saveAndFlush(lISTEMT);

        int databaseSizeBeforeUpdate = lISTEMTRepository.findAll().size();

        // Update the lISTEMT
        LISTEMT updatedLISTEMT = lISTEMTRepository.findById(lISTEMT.getId()).get();
        // Disconnect from session so that the updates on updatedLISTEMT are not directly saved in db
        em.detach(updatedLISTEMT);
        updatedLISTEMT.liscode(UPDATED_LISCODE).lisnam(UPDATED_LISNAM).lisdir(UPDATED_LISDIR);

        restLISTEMTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLISTEMT.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLISTEMT))
            )
            .andExpect(status().isOk());

        // Validate the LISTEMT in the database
        List<LISTEMT> lISTEMTList = lISTEMTRepository.findAll();
        assertThat(lISTEMTList).hasSize(databaseSizeBeforeUpdate);
        LISTEMT testLISTEMT = lISTEMTList.get(lISTEMTList.size() - 1);
        assertThat(testLISTEMT.getLiscode()).isEqualTo(UPDATED_LISCODE);
        assertThat(testLISTEMT.getLisnam()).isEqualTo(UPDATED_LISNAM);
        assertThat(testLISTEMT.getLisdir()).isEqualTo(UPDATED_LISDIR);
    }

    @Test
    @Transactional
    void putNonExistingLISTEMT() throws Exception {
        int databaseSizeBeforeUpdate = lISTEMTRepository.findAll().size();
        lISTEMT.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLISTEMTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lISTEMT.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lISTEMT))
            )
            .andExpect(status().isBadRequest());

        // Validate the LISTEMT in the database
        List<LISTEMT> lISTEMTList = lISTEMTRepository.findAll();
        assertThat(lISTEMTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLISTEMT() throws Exception {
        int databaseSizeBeforeUpdate = lISTEMTRepository.findAll().size();
        lISTEMT.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLISTEMTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lISTEMT))
            )
            .andExpect(status().isBadRequest());

        // Validate the LISTEMT in the database
        List<LISTEMT> lISTEMTList = lISTEMTRepository.findAll();
        assertThat(lISTEMTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLISTEMT() throws Exception {
        int databaseSizeBeforeUpdate = lISTEMTRepository.findAll().size();
        lISTEMT.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLISTEMTMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lISTEMT)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LISTEMT in the database
        List<LISTEMT> lISTEMTList = lISTEMTRepository.findAll();
        assertThat(lISTEMTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLISTEMTWithPatch() throws Exception {
        // Initialize the database
        lISTEMTRepository.saveAndFlush(lISTEMT);

        int databaseSizeBeforeUpdate = lISTEMTRepository.findAll().size();

        // Update the lISTEMT using partial update
        LISTEMT partialUpdatedLISTEMT = new LISTEMT();
        partialUpdatedLISTEMT.setId(lISTEMT.getId());

        partialUpdatedLISTEMT.liscode(UPDATED_LISCODE).lisdir(UPDATED_LISDIR);

        restLISTEMTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLISTEMT.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLISTEMT))
            )
            .andExpect(status().isOk());

        // Validate the LISTEMT in the database
        List<LISTEMT> lISTEMTList = lISTEMTRepository.findAll();
        assertThat(lISTEMTList).hasSize(databaseSizeBeforeUpdate);
        LISTEMT testLISTEMT = lISTEMTList.get(lISTEMTList.size() - 1);
        assertThat(testLISTEMT.getLiscode()).isEqualTo(UPDATED_LISCODE);
        assertThat(testLISTEMT.getLisnam()).isEqualTo(DEFAULT_LISNAM);
        assertThat(testLISTEMT.getLisdir()).isEqualTo(UPDATED_LISDIR);
    }

    @Test
    @Transactional
    void fullUpdateLISTEMTWithPatch() throws Exception {
        // Initialize the database
        lISTEMTRepository.saveAndFlush(lISTEMT);

        int databaseSizeBeforeUpdate = lISTEMTRepository.findAll().size();

        // Update the lISTEMT using partial update
        LISTEMT partialUpdatedLISTEMT = new LISTEMT();
        partialUpdatedLISTEMT.setId(lISTEMT.getId());

        partialUpdatedLISTEMT.liscode(UPDATED_LISCODE).lisnam(UPDATED_LISNAM).lisdir(UPDATED_LISDIR);

        restLISTEMTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLISTEMT.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLISTEMT))
            )
            .andExpect(status().isOk());

        // Validate the LISTEMT in the database
        List<LISTEMT> lISTEMTList = lISTEMTRepository.findAll();
        assertThat(lISTEMTList).hasSize(databaseSizeBeforeUpdate);
        LISTEMT testLISTEMT = lISTEMTList.get(lISTEMTList.size() - 1);
        assertThat(testLISTEMT.getLiscode()).isEqualTo(UPDATED_LISCODE);
        assertThat(testLISTEMT.getLisnam()).isEqualTo(UPDATED_LISNAM);
        assertThat(testLISTEMT.getLisdir()).isEqualTo(UPDATED_LISDIR);
    }

    @Test
    @Transactional
    void patchNonExistingLISTEMT() throws Exception {
        int databaseSizeBeforeUpdate = lISTEMTRepository.findAll().size();
        lISTEMT.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLISTEMTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lISTEMT.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lISTEMT))
            )
            .andExpect(status().isBadRequest());

        // Validate the LISTEMT in the database
        List<LISTEMT> lISTEMTList = lISTEMTRepository.findAll();
        assertThat(lISTEMTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLISTEMT() throws Exception {
        int databaseSizeBeforeUpdate = lISTEMTRepository.findAll().size();
        lISTEMT.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLISTEMTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lISTEMT))
            )
            .andExpect(status().isBadRequest());

        // Validate the LISTEMT in the database
        List<LISTEMT> lISTEMTList = lISTEMTRepository.findAll();
        assertThat(lISTEMTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLISTEMT() throws Exception {
        int databaseSizeBeforeUpdate = lISTEMTRepository.findAll().size();
        lISTEMT.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLISTEMTMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(lISTEMT)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LISTEMT in the database
        List<LISTEMT> lISTEMTList = lISTEMTRepository.findAll();
        assertThat(lISTEMTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLISTEMT() throws Exception {
        // Initialize the database
        lISTEMTRepository.saveAndFlush(lISTEMT);

        int databaseSizeBeforeDelete = lISTEMTRepository.findAll().size();

        // Delete the lISTEMT
        restLISTEMTMockMvc
            .perform(delete(ENTITY_API_URL_ID, lISTEMT.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LISTEMT> lISTEMTList = lISTEMTRepository.findAll();
        assertThat(lISTEMTList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
