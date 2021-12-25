package com.mycompany.ssi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.ssi.IntegrationTest;
import com.mycompany.ssi.domain.TBBROKER;
import com.mycompany.ssi.repository.TBBROKERRepository;
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
 * Integration tests for the {@link TBBROKERResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TBBROKERResourceIT {

    private static final String DEFAULT_BRSTS = "AAAAAAAAAA";
    private static final String UPDATED_BRSTS = "BBBBBBBBBB";

    private static final String DEFAULT_BRNAM = "AAAAAAAAAA";
    private static final String UPDATED_BRNAM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BRLMD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BRLMD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_BRUID = "AAAAAAAAAA";
    private static final String UPDATED_BRUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tbbrokers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{brcode}";

    @Autowired
    private TBBROKERRepository tBBROKERRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTBBROKERMockMvc;

    private TBBROKER tBBROKER;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBBROKER createEntity(EntityManager em) {
        TBBROKER tBBROKER = new TBBROKER().brsts(DEFAULT_BRSTS).brnam(DEFAULT_BRNAM).brlmd(DEFAULT_BRLMD).bruid(DEFAULT_BRUID);
        return tBBROKER;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBBROKER createUpdatedEntity(EntityManager em) {
        TBBROKER tBBROKER = new TBBROKER().brsts(UPDATED_BRSTS).brnam(UPDATED_BRNAM).brlmd(UPDATED_BRLMD).bruid(UPDATED_BRUID);
        return tBBROKER;
    }

    @BeforeEach
    public void initTest() {
        tBBROKER = createEntity(em);
    }

    @Test
    @Transactional
    void createTBBROKER() throws Exception {
        int databaseSizeBeforeCreate = tBBROKERRepository.findAll().size();
        // Create the TBBROKER
        restTBBROKERMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBBROKER)))
            .andExpect(status().isCreated());

        // Validate the TBBROKER in the database
        List<TBBROKER> tBBROKERList = tBBROKERRepository.findAll();
        assertThat(tBBROKERList).hasSize(databaseSizeBeforeCreate + 1);
        TBBROKER testTBBROKER = tBBROKERList.get(tBBROKERList.size() - 1);
        assertThat(testTBBROKER.getBrsts()).isEqualTo(DEFAULT_BRSTS);
        assertThat(testTBBROKER.getBrnam()).isEqualTo(DEFAULT_BRNAM);
        assertThat(testTBBROKER.getBrlmd()).isEqualTo(DEFAULT_BRLMD);
        assertThat(testTBBROKER.getBruid()).isEqualTo(DEFAULT_BRUID);
    }

    @Test
    @Transactional
    void createTBBROKERWithExistingId() throws Exception {
        // Create the TBBROKER with an existing ID
        tBBROKER.setBrcode("existing_id");

        int databaseSizeBeforeCreate = tBBROKERRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTBBROKERMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBBROKER)))
            .andExpect(status().isBadRequest());

        // Validate the TBBROKER in the database
        List<TBBROKER> tBBROKERList = tBBROKERRepository.findAll();
        assertThat(tBBROKERList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBrstsIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBBROKERRepository.findAll().size();
        // set the field null
        tBBROKER.setBrsts(null);

        // Create the TBBROKER, which fails.

        restTBBROKERMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBBROKER)))
            .andExpect(status().isBadRequest());

        List<TBBROKER> tBBROKERList = tBBROKERRepository.findAll();
        assertThat(tBBROKERList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBrnamIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBBROKERRepository.findAll().size();
        // set the field null
        tBBROKER.setBrnam(null);

        // Create the TBBROKER, which fails.

        restTBBROKERMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBBROKER)))
            .andExpect(status().isBadRequest());

        List<TBBROKER> tBBROKERList = tBBROKERRepository.findAll();
        assertThat(tBBROKERList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBrlmdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBBROKERRepository.findAll().size();
        // set the field null
        tBBROKER.setBrlmd(null);

        // Create the TBBROKER, which fails.

        restTBBROKERMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBBROKER)))
            .andExpect(status().isBadRequest());

        List<TBBROKER> tBBROKERList = tBBROKERRepository.findAll();
        assertThat(tBBROKERList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBruidIsRequired() throws Exception {
        int databaseSizeBeforeTest = tBBROKERRepository.findAll().size();
        // set the field null
        tBBROKER.setBruid(null);

        // Create the TBBROKER, which fails.

        restTBBROKERMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBBROKER)))
            .andExpect(status().isBadRequest());

        List<TBBROKER> tBBROKERList = tBBROKERRepository.findAll();
        assertThat(tBBROKERList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTBBROKERS() throws Exception {
        // Initialize the database
        tBBROKER.setBrcode(UUID.randomUUID().toString());
        tBBROKERRepository.saveAndFlush(tBBROKER);

        // Get all the tBBROKERList
        restTBBROKERMockMvc
            .perform(get(ENTITY_API_URL + "?sort=brcode,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].brcode").value(hasItem(tBBROKER.getBrcode())))
            .andExpect(jsonPath("$.[*].brsts").value(hasItem(DEFAULT_BRSTS)))
            .andExpect(jsonPath("$.[*].brnam").value(hasItem(DEFAULT_BRNAM)))
            .andExpect(jsonPath("$.[*].brlmd").value(hasItem(DEFAULT_BRLMD.toString())))
            .andExpect(jsonPath("$.[*].bruid").value(hasItem(DEFAULT_BRUID)));
    }

    @Test
    @Transactional
    void getTBBROKER() throws Exception {
        // Initialize the database
        tBBROKER.setBrcode(UUID.randomUUID().toString());
        tBBROKERRepository.saveAndFlush(tBBROKER);

        // Get the tBBROKER
        restTBBROKERMockMvc
            .perform(get(ENTITY_API_URL_ID, tBBROKER.getBrcode()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.brcode").value(tBBROKER.getBrcode()))
            .andExpect(jsonPath("$.brsts").value(DEFAULT_BRSTS))
            .andExpect(jsonPath("$.brnam").value(DEFAULT_BRNAM))
            .andExpect(jsonPath("$.brlmd").value(DEFAULT_BRLMD.toString()))
            .andExpect(jsonPath("$.bruid").value(DEFAULT_BRUID));
    }

    @Test
    @Transactional
    void getNonExistingTBBROKER() throws Exception {
        // Get the tBBROKER
        restTBBROKERMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTBBROKER() throws Exception {
        // Initialize the database
        tBBROKER.setBrcode(UUID.randomUUID().toString());
        tBBROKERRepository.saveAndFlush(tBBROKER);

        int databaseSizeBeforeUpdate = tBBROKERRepository.findAll().size();

        // Update the tBBROKER
        TBBROKER updatedTBBROKER = tBBROKERRepository.findById(tBBROKER.getBrcode()).get();
        // Disconnect from session so that the updates on updatedTBBROKER are not directly saved in db
        em.detach(updatedTBBROKER);
        updatedTBBROKER.brsts(UPDATED_BRSTS).brnam(UPDATED_BRNAM).brlmd(UPDATED_BRLMD).bruid(UPDATED_BRUID);

        restTBBROKERMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTBBROKER.getBrcode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTBBROKER))
            )
            .andExpect(status().isOk());

        // Validate the TBBROKER in the database
        List<TBBROKER> tBBROKERList = tBBROKERRepository.findAll();
        assertThat(tBBROKERList).hasSize(databaseSizeBeforeUpdate);
        TBBROKER testTBBROKER = tBBROKERList.get(tBBROKERList.size() - 1);
        assertThat(testTBBROKER.getBrsts()).isEqualTo(UPDATED_BRSTS);
        assertThat(testTBBROKER.getBrnam()).isEqualTo(UPDATED_BRNAM);
        assertThat(testTBBROKER.getBrlmd()).isEqualTo(UPDATED_BRLMD);
        assertThat(testTBBROKER.getBruid()).isEqualTo(UPDATED_BRUID);
    }

    @Test
    @Transactional
    void putNonExistingTBBROKER() throws Exception {
        int databaseSizeBeforeUpdate = tBBROKERRepository.findAll().size();
        tBBROKER.setBrcode(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBBROKERMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tBBROKER.getBrcode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBBROKER))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBBROKER in the database
        List<TBBROKER> tBBROKERList = tBBROKERRepository.findAll();
        assertThat(tBBROKERList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTBBROKER() throws Exception {
        int databaseSizeBeforeUpdate = tBBROKERRepository.findAll().size();
        tBBROKER.setBrcode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBBROKERMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tBBROKER))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBBROKER in the database
        List<TBBROKER> tBBROKERList = tBBROKERRepository.findAll();
        assertThat(tBBROKERList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTBBROKER() throws Exception {
        int databaseSizeBeforeUpdate = tBBROKERRepository.findAll().size();
        tBBROKER.setBrcode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBBROKERMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tBBROKER)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBBROKER in the database
        List<TBBROKER> tBBROKERList = tBBROKERRepository.findAll();
        assertThat(tBBROKERList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTBBROKERWithPatch() throws Exception {
        // Initialize the database
        tBBROKER.setBrcode(UUID.randomUUID().toString());
        tBBROKERRepository.saveAndFlush(tBBROKER);

        int databaseSizeBeforeUpdate = tBBROKERRepository.findAll().size();

        // Update the tBBROKER using partial update
        TBBROKER partialUpdatedTBBROKER = new TBBROKER();
        partialUpdatedTBBROKER.setBrcode(tBBROKER.getBrcode());

        partialUpdatedTBBROKER.brsts(UPDATED_BRSTS).brnam(UPDATED_BRNAM).brlmd(UPDATED_BRLMD).bruid(UPDATED_BRUID);

        restTBBROKERMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBBROKER.getBrcode())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBBROKER))
            )
            .andExpect(status().isOk());

        // Validate the TBBROKER in the database
        List<TBBROKER> tBBROKERList = tBBROKERRepository.findAll();
        assertThat(tBBROKERList).hasSize(databaseSizeBeforeUpdate);
        TBBROKER testTBBROKER = tBBROKERList.get(tBBROKERList.size() - 1);
        assertThat(testTBBROKER.getBrsts()).isEqualTo(UPDATED_BRSTS);
        assertThat(testTBBROKER.getBrnam()).isEqualTo(UPDATED_BRNAM);
        assertThat(testTBBROKER.getBrlmd()).isEqualTo(UPDATED_BRLMD);
        assertThat(testTBBROKER.getBruid()).isEqualTo(UPDATED_BRUID);
    }

    @Test
    @Transactional
    void fullUpdateTBBROKERWithPatch() throws Exception {
        // Initialize the database
        tBBROKER.setBrcode(UUID.randomUUID().toString());
        tBBROKERRepository.saveAndFlush(tBBROKER);

        int databaseSizeBeforeUpdate = tBBROKERRepository.findAll().size();

        // Update the tBBROKER using partial update
        TBBROKER partialUpdatedTBBROKER = new TBBROKER();
        partialUpdatedTBBROKER.setBrcode(tBBROKER.getBrcode());

        partialUpdatedTBBROKER.brsts(UPDATED_BRSTS).brnam(UPDATED_BRNAM).brlmd(UPDATED_BRLMD).bruid(UPDATED_BRUID);

        restTBBROKERMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTBBROKER.getBrcode())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTBBROKER))
            )
            .andExpect(status().isOk());

        // Validate the TBBROKER in the database
        List<TBBROKER> tBBROKERList = tBBROKERRepository.findAll();
        assertThat(tBBROKERList).hasSize(databaseSizeBeforeUpdate);
        TBBROKER testTBBROKER = tBBROKERList.get(tBBROKERList.size() - 1);
        assertThat(testTBBROKER.getBrsts()).isEqualTo(UPDATED_BRSTS);
        assertThat(testTBBROKER.getBrnam()).isEqualTo(UPDATED_BRNAM);
        assertThat(testTBBROKER.getBrlmd()).isEqualTo(UPDATED_BRLMD);
        assertThat(testTBBROKER.getBruid()).isEqualTo(UPDATED_BRUID);
    }

    @Test
    @Transactional
    void patchNonExistingTBBROKER() throws Exception {
        int databaseSizeBeforeUpdate = tBBROKERRepository.findAll().size();
        tBBROKER.setBrcode(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBBROKERMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tBBROKER.getBrcode())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBBROKER))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBBROKER in the database
        List<TBBROKER> tBBROKERList = tBBROKERRepository.findAll();
        assertThat(tBBROKERList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTBBROKER() throws Exception {
        int databaseSizeBeforeUpdate = tBBROKERRepository.findAll().size();
        tBBROKER.setBrcode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBBROKERMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tBBROKER))
            )
            .andExpect(status().isBadRequest());

        // Validate the TBBROKER in the database
        List<TBBROKER> tBBROKERList = tBBROKERRepository.findAll();
        assertThat(tBBROKERList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTBBROKER() throws Exception {
        int databaseSizeBeforeUpdate = tBBROKERRepository.findAll().size();
        tBBROKER.setBrcode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTBBROKERMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tBBROKER)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TBBROKER in the database
        List<TBBROKER> tBBROKERList = tBBROKERRepository.findAll();
        assertThat(tBBROKERList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTBBROKER() throws Exception {
        // Initialize the database
        tBBROKER.setBrcode(UUID.randomUUID().toString());
        tBBROKERRepository.saveAndFlush(tBBROKER);

        int databaseSizeBeforeDelete = tBBROKERRepository.findAll().size();

        // Delete the tBBROKER
        restTBBROKERMockMvc
            .perform(delete(ENTITY_API_URL_ID, tBBROKER.getBrcode()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TBBROKER> tBBROKERList = tBBROKERRepository.findAll();
        assertThat(tBBROKERList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
