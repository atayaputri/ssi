package com.mycompany.ssi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.ssi.IntegrationTest;
import com.mycompany.ssi.domain.TBPART;
import com.mycompany.ssi.repository.TBPARTRepository;
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
 * Integration tests for the {@link TBPARTResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TBPARTResourceIT {

    private static final String DEFAULT_TPASTS = "AAAAAAAAAA";
    private static final String UPDATED_TPASTS = "BBBBBBBBBB";

    private static final String DEFAULT_TPANAM = "AAAAAAAAAA";
    private static final String UPDATED_TPANAM = "BBBBBBBBBB";

    private static final String DEFAULT_TPAREK = "AAAAAAAAAA";
    private static final String UPDATED_TPAREK = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TPADIS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TPADIS = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TPALMD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TPALMD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TPAUID = "AAAAAAAAAA";
    private static final String UPDATED_TPAUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tbparts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{tpacode}";

    @Autowired
    private TBPARTRepository tBPARTRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTBPARTMockMvc;

    private TBPART tBPART;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBPART createEntity(EntityManager em) {
        TBPART tBPART = new TBPART()
            .tpasts(DEFAULT_TPASTS)
            .tpanam(DEFAULT_TPANAM)
            .tparek(DEFAULT_TPAREK)
            .tpadis(DEFAULT_TPADIS)
            .tpalmd(DEFAULT_TPALMD)
            .tpauid(DEFAULT_TPAUID);
        return tBPART;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBPART createUpdatedEntity(EntityManager em) {
        TBPART tBPART = new TBPART()
            .tpasts(UPDATED_TPASTS)
            .tpanam(UPDATED_TPANAM)
            .tparek(UPDATED_TPAREK)
            .tpadis(UPDATED_TPADIS)
            .tpalmd(UPDATED_TPALMD)
            .tpauid(UPDATED_TPAUID);
        return tBPART;
    }

    @BeforeEach
    public void initTest() {
        tBPART = createEntity(em);
    }

    @Test
    @Transactional
    void createTBPART() throws Exception {
        int databaseSizeBeforeCreate = tBPARTRepository.findAll().size();
        // Create the TBPART
        restTBPARTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBPART)))
            .andExpect(status().isCreated());

        // Validate the TBPART in the database
        List<TBPART> tBPARTList = tBPARTRepository.findAll();
        assertThat(tBPARTList).hasSize(databaseSizeBeforeCreate + 1);
        TBPART testTBPART = tBPARTList.get(tBPARTList.size() - 1);
        assertThat(testTBPART.getTpasts()).isEqualTo(DEFAULT_TPASTS);
        assertThat(testTBPART.getTpanam()).isEqualTo(DEFAULT_TPANAM);
        assertThat(testTBPART.getTparek()).isEqualTo(DEFAULT_TPAREK);
        assertThat(testTBPART.getTpadis()).isEqualTo(DEFAULT_TPADIS);
        assertThat(testTBPART.getTpalmd()).isEqualTo(DEFAULT_TPALMD);
        assertThat(testTBPART.getTpauid()).isEqualTo(DEFAULT_TPAUID);
    }

    @Test
    @Transactional
    void createTBPARTWithExistingId() throws Exception {
        // Create the TBPART with an existing ID
        tBPART.setTpacode("existing_id");

        int databaseSizeBeforeCreate = tBPARTRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTBPARTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBPART)))
            .andExpect(status().isBadRequest());

        // Validate the TBPART in the database
        List<TBPART> tBPARTList = tBPARTRepository.findAll();
        assertThat(tBPARTList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTpastsIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBPARTRepository.findAll().size();
        // set the field null
        tBPART.setTpasts(null);

        // Create the TBPART, which fails.

        restTBPARTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBPART)))
            .andExpect(status().isBadRequest());

        List<TBPART> tBPARTList = tBPARTRepository.findAll();
        assertThat(tBPARTList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTpanamIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBPARTRepository.findAll().size();
        // set the field null
        tBPART.setTpanam(null);

        // Create the TBPART, which fails.

        restTBPARTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBPART)))
            .andExpect(status().isBadRequest());

        List<TBPART> tBPARTList = tBPARTRepository.findAll();
        assertThat(tBPARTList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTparekIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBPARTRepository.findAll().size();
        // set the field null
        tBPART.setTparek(null);

        // Create the TBPART, which fails.

        restTBPARTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBPART)))
            .andExpect(status().isBadRequest());

        List<TBPART> tBPARTList = tBPARTRepository.findAll();
        assertThat(tBPARTList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTpadisIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBPARTRepository.findAll().size();
        // set the field null
        tBPART.setTpadis(null);

        // Create the TBPART, which fails.

        restTBPARTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBPART)))
            .andExpect(status().isBadRequest());

        List<TBPART> tBPARTList = tBPARTRepository.findAll();
        assertThat(tBPARTList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTpalmdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBPARTRepository.findAll().size();
        // set the field null
        tBPART.setTpalmd(null);

        // Create the TBPART, which fails.

        restTBPARTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBPART)))
            .andExpect(status().isBadRequest());

        List<TBPART> tBPARTList = tBPARTRepository.findAll();
        assertThat(tBPARTList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTpauidIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBPARTRepository.findAll().size();
        // set the field null
        tBPART.setTpauid(null);

        // Create the TBPART, which fails.

        restTBPARTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBPART)))
            .andExpect(status().isBadRequest());

        List<TBPART> tBPARTList = tBPARTRepository.findAll();
        assertThat(tBPARTList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTBPARTS() throws Exception {
        // Initialize the database
        tBPART.setTpacode(UUID.randomUUID().toString());
        tBPARTRepository.saveAndFlush(tBPART);

        // Get all the tBPARTList
        restTBPARTMockMvc
            .perform(get(ENTITY_API_URL + "?sort=tpacode,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].tpacode").value(hasItem(tBPART.getTpacode())))
            .andExpect(jsonPath("$.[*].tpasts").value(hasItem(DEFAULT_TPASTS)))
            .andExpect(jsonPath("$.[*].tpanam").value(hasItem(DEFAULT_TPANAM)))
            .andExpect(jsonPath("$.[*].tparek").value(hasItem(DEFAULT_TPAREK)))
            .andExpect(jsonPath("$.[*].tpadis").value(hasItem(DEFAULT_TPADIS.toString())))
            .andExpect(jsonPath("$.[*].tpalmd").value(hasItem(DEFAULT_TPALMD.toString())))
            .andExpect(jsonPath("$.[*].tpauid").value(hasItem(DEFAULT_TPAUID)));
    }

    @Test
    @Transactional
    void getTBPART() throws Exception {
        // Initialize the database
        tBPART.setTpacode(UUID.randomUUID().toString());
        tBPARTRepository.saveAndFlush(tBPART);

        // Get the tBPART
        restTBPARTMockMvc
            .perform(get(ENTITY_API_URL_ID, tBPART.getTpacode()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.tpacode").value(tBPART.getTpacode()))
            .andExpect(jsonPath("$.tpasts").value(DEFAULT_TPASTS))
            .andExpect(jsonPath("$.tpanam").value(DEFAULT_TPANAM))
            .andExpect(jsonPath("$.tparek").value(DEFAULT_TPAREK))
            .andExpect(jsonPath("$.tpadis").value(DEFAULT_TPADIS.toString()))
            .andExpect(jsonPath("$.tpalmd").value(DEFAULT_TPALMD.toString()))
            .andExpect(jsonPath("$.tpauid").value(DEFAULT_TPAUID));
    }

    @Test
    @Transactional
    void getNonExistingTBPART() throws Exception {
        // Get the tBPART
        restTBPARTMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTBPART() throws Exception {
        // Initialize the database
        tBPART.setTpacode(UUID.randomUUID().toString());
        tBPARTRepository.saveAndFlush(tBPART);

        int databaseSizeBeforeUpdate = tBPARTRepository.findAll().size();

        // Update the tBPART
        TBPART updatedTBPART = tBPARTRepository.findById(tBPART.getTpacode()).get();
        // Disconnect from session so that the updates on updatedTBPART are not directly saved in db
        em.detach(updatedTBPART);
        updatedTBPART
            .tpasts(UPDATED_TPASTS)
            .tpanam(UPDATED_TPANAM)
            .tparek(UPDATED_TPAREK)
            .tpadis(UPDATED_TPADIS)
            .tpalmd(UPDATED_TPALMD)
            .tpauid(UPDATED_TPAUID);

        restTBPARTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTBPART.getTpacode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTBPART))
            )
            .andExpect(status().isOk());

        // Validate the TBPART in the database
        List<TBPART> tBPARTList = tBPARTRepository.findAll();
        assertThat(tBPARTList).hasSize(databaseSizeBeforeUpdate);
        TBPART testTBPART = tBPARTList.get(tBPARTList.size() - 1);
        assertThat(testTBPART.getTpasts()).isEqualTo(UPDATED_TPASTS);
        assertThat(testTBPART.getTpanam()).isEqualTo(UPDATED_TPANAM);
        assertThat(testTBPART.getTparek()).isEqualTo(UPDATED_TPAREK);
        assertThat(testTBPART.getTpadis()).isEqualTo(UPDATED_TPADIS);
        assertThat(testTBPART.getTpalmd()).isEqualTo(UPDATED_TPALMD);
        assertThat(testTBPART.getTpauid()).isEqualTo(UPDATED_TPAUID);
    }

    @Test
    @Transactional
    void putNonExistingTBPART() throws Exception {
        int databaseSizeBeforeUpdate = tBPARTRepository.findAll().size();
        tBPART.setTpacode(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBPARTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tBPART.getTpacode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBPART))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBPART in the database
        List<TBPART> tBPARTList = tBPARTRepository.findAll();
        assertThat(tBPARTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTBPART() throws Exception {
        int databaseSizeBeforeUpdate = tBPARTRepository.findAll().size();
        tBPART.setTpacode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBPARTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBPART))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBPART in the database
        List<TBPART> tBPARTList = tBPARTRepository.findAll();
        assertThat(tBPARTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTBPART() throws Exception {
        int databaseSizeBeforeUpdate = tBPARTRepository.findAll().size();
        tBPART.setTpacode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBPARTMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBPART)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBPART in the database
        List<TBPART> tBPARTList = tBPARTRepository.findAll();
        assertThat(tBPARTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTBPARTWithPatch() throws Exception {
        // Initialize the database
        tBPART.setTpacode(UUID.randomUUID().toString());
        tBPARTRepository.saveAndFlush(tBPART);

        int databaseSizeBeforeUpdate = tBPARTRepository.findAll().size();

        // Update the tBPART using partial update
        TBPART partialUpdatedTBPART = new TBPART();
        partialUpdatedTBPART.setTpacode(tBPART.getTpacode());

        partialUpdatedTBPART
            .tpasts(UPDATED_TPASTS)
            .tpanam(UPDATED_TPANAM)
            .tparek(UPDATED_TPAREK)
            .tpadis(UPDATED_TPADIS)
            .tpalmd(UPDATED_TPALMD)
            .tpauid(UPDATED_TPAUID);

        restTBPARTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBPART.getTpacode())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBPART))
            )
            .andExpect(status().isOk());

        // Validate the TBPART in the database
        List<TBPART> tBPARTList = tBPARTRepository.findAll();
        assertThat(tBPARTList).hasSize(databaseSizeBeforeUpdate);
        TBPART testTBPART = tBPARTList.get(tBPARTList.size() - 1);
        assertThat(testTBPART.getTpasts()).isEqualTo(UPDATED_TPASTS);
        assertThat(testTBPART.getTpanam()).isEqualTo(UPDATED_TPANAM);
        assertThat(testTBPART.getTparek()).isEqualTo(UPDATED_TPAREK);
        assertThat(testTBPART.getTpadis()).isEqualTo(UPDATED_TPADIS);
        assertThat(testTBPART.getTpalmd()).isEqualTo(UPDATED_TPALMD);
        assertThat(testTBPART.getTpauid()).isEqualTo(UPDATED_TPAUID);
    }

    @Test
    @Transactional
    void fullUpdateTBPARTWithPatch() throws Exception {
        // Initialize the database
        tBPART.setTpacode(UUID.randomUUID().toString());
        tBPARTRepository.saveAndFlush(tBPART);

        int databaseSizeBeforeUpdate = tBPARTRepository.findAll().size();

        // Update the tBPART using partial update
        TBPART partialUpdatedTBPART = new TBPART();
        partialUpdatedTBPART.setTpacode(tBPART.getTpacode());

        partialUpdatedTBPART
            .tpasts(UPDATED_TPASTS)
            .tpanam(UPDATED_TPANAM)
            .tparek(UPDATED_TPAREK)
            .tpadis(UPDATED_TPADIS)
            .tpalmd(UPDATED_TPALMD)
            .tpauid(UPDATED_TPAUID);

        restTBPARTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBPART.getTpacode())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBPART))
            )
            .andExpect(status().isOk());

        // Validate the TBPART in the database
        List<TBPART> tBPARTList = tBPARTRepository.findAll();
        assertThat(tBPARTList).hasSize(databaseSizeBeforeUpdate);
        TBPART testTBPART = tBPARTList.get(tBPARTList.size() - 1);
        assertThat(testTBPART.getTpasts()).isEqualTo(UPDATED_TPASTS);
        assertThat(testTBPART.getTpanam()).isEqualTo(UPDATED_TPANAM);
        assertThat(testTBPART.getTparek()).isEqualTo(UPDATED_TPAREK);
        assertThat(testTBPART.getTpadis()).isEqualTo(UPDATED_TPADIS);
        assertThat(testTBPART.getTpalmd()).isEqualTo(UPDATED_TPALMD);
        assertThat(testTBPART.getTpauid()).isEqualTo(UPDATED_TPAUID);
    }

    @Test
    @Transactional
    void patchNonExistingTBPART() throws Exception {
        int databaseSizeBeforeUpdate = tBPARTRepository.findAll().size();
        tBPART.setTpacode(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBPARTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tBPART.getTpacode())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBPART))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBPART in the database
        List<TBPART> tBPARTList = tBPARTRepository.findAll();
        assertThat(tBPARTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTBPART() throws Exception {
        int databaseSizeBeforeUpdate = tBPARTRepository.findAll().size();
        tBPART.setTpacode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBPARTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBPART))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBPART in the database
        List<TBPART> tBPARTList = tBPARTRepository.findAll();
        assertThat(tBPARTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTBPART() throws Exception {
        int databaseSizeBeforeUpdate = tBPARTRepository.findAll().size();
        tBPART.setTpacode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBPARTMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tBPART)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBPART in the database
        List<TBPART> tBPARTList = tBPARTRepository.findAll();
        assertThat(tBPARTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTBPART() throws Exception {
        // Initialize the database
        tBPART.setTpacode(UUID.randomUUID().toString());
        tBPARTRepository.saveAndFlush(tBPART);

        int databaseSizeBeforeDelete = tBPARTRepository.findAll().size();

        // Delete the tBPART
        restTBPARTMockMvc
            .perform(delete(ENTITY_API_URL_ID, tBPART.getTpacode()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TBPART> tBPARTList = tBPARTRepository.findAll();
        assertThat(tBPARTList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
