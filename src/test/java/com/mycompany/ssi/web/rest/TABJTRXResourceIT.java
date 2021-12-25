package com.mycompany.ssi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.ssi.IntegrationTest;
import com.mycompany.ssi.domain.TABJTRX;
import com.mycompany.ssi.repository.TABJTRXRepository;
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
 * Integration tests for the {@link TABJTRXResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TABJTRXResourceIT {

    private static final String DEFAULT_JTSTS = "A";
    private static final String UPDATED_JTSTS = "B";

    private static final String DEFAULT_JTDESC = "AAAAAAAAAA";
    private static final String UPDATED_JTDESC = "BBBBBBBBBB";

    private static final String DEFAULT_JTSDES = "AAAAAAAAAA";
    private static final String UPDATED_JTSDES = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_JTLMD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JTLMD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_JTOUID = "AAAAAAAAAA";
    private static final String UPDATED_JTOUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tabjtrxes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{jtjntx}";

    @Autowired
    private TABJTRXRepository tABJTRXRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTABJTRXMockMvc;

    private TABJTRX tABJTRX;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TABJTRX createEntity(EntityManager em) {
        TABJTRX tABJTRX = new TABJTRX()
            .jtsts(DEFAULT_JTSTS)
            .jtdesc(DEFAULT_JTDESC)
            .jtsdes(DEFAULT_JTSDES)
            .jtlmd(DEFAULT_JTLMD)
            .jtouid(DEFAULT_JTOUID);
        return tABJTRX;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TABJTRX createUpdatedEntity(EntityManager em) {
        TABJTRX tABJTRX = new TABJTRX()
            .jtsts(UPDATED_JTSTS)
            .jtdesc(UPDATED_JTDESC)
            .jtsdes(UPDATED_JTSDES)
            .jtlmd(UPDATED_JTLMD)
            .jtouid(UPDATED_JTOUID);
        return tABJTRX;
    }

    @BeforeEach
    public void initTest() {
        tABJTRX = createEntity(em);
    }

    @Test
    @Transactional
    void createTABJTRX() throws Exception {
        int databaseSizeBeforeCreate = tABJTRXRepository.findAll().size();
        // Create the TABJTRX
        restTABJTRXMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tABJTRX)))
            .andExpect(status().isCreated());

        // Validate the TABJTRX in the database
        List<TABJTRX> tABJTRXList = tABJTRXRepository.findAll();
        assertThat(tABJTRXList).hasSize(databaseSizeBeforeCreate + 1);
        TABJTRX testTABJTRX = tABJTRXList.get(tABJTRXList.size() - 1);
        assertThat(testTABJTRX.getJtsts()).isEqualTo(DEFAULT_JTSTS);
        assertThat(testTABJTRX.getJtdesc()).isEqualTo(DEFAULT_JTDESC);
        assertThat(testTABJTRX.getJtsdes()).isEqualTo(DEFAULT_JTSDES);
        assertThat(testTABJTRX.getJtlmd()).isEqualTo(DEFAULT_JTLMD);
        assertThat(testTABJTRX.getJtouid()).isEqualTo(DEFAULT_JTOUID);
    }

    @Test
    @Transactional
    void createTABJTRXWithExistingId() throws Exception {
        // Create the TABJTRX with an existing ID
        tABJTRX.setJtjntx("existing_id");

        int databaseSizeBeforeCreate = tABJTRXRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTABJTRXMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tABJTRX)))
            .andExpect(status().isBadRequest());

        // Validate the TABJTRX in the database
        List<TABJTRX> tABJTRXList = tABJTRXRepository.findAll();
        assertThat(tABJTRXList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkJtstsIsRequired() throws Exception {
        int databaseSizeBeforeTest = tABJTRXRepository.findAll().size();
        // set the field null
        tABJTRX.setJtsts(null);

        // Create the TABJTRX, which fails.

        restTABJTRXMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tABJTRX)))
            .andExpect(status().isBadRequest());

        List<TABJTRX> tABJTRXList = tABJTRXRepository.findAll();
        assertThat(tABJTRXList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkJtlmdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tABJTRXRepository.findAll().size();
        // set the field null
        tABJTRX.setJtlmd(null);

        // Create the TABJTRX, which fails.

        restTABJTRXMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tABJTRX)))
            .andExpect(status().isBadRequest());

        List<TABJTRX> tABJTRXList = tABJTRXRepository.findAll();
        assertThat(tABJTRXList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkJtouidIsRequired() throws Exception {
        int databaseSizeBeforeTest = tABJTRXRepository.findAll().size();
        // set the field null
        tABJTRX.setJtouid(null);

        // Create the TABJTRX, which fails.

        restTABJTRXMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tABJTRX)))
            .andExpect(status().isBadRequest());

        List<TABJTRX> tABJTRXList = tABJTRXRepository.findAll();
        assertThat(tABJTRXList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTABJTRXES() throws Exception {
        // Initialize the database
        tABJTRX.setJtjntx(UUID.randomUUID().toString());
        tABJTRXRepository.saveAndFlush(tABJTRX);

        // Get all the tABJTRXList
        restTABJTRXMockMvc
            .perform(get(ENTITY_API_URL + "?sort=jtjntx,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].jtjntx").value(hasItem(tABJTRX.getJtjntx())))
            .andExpect(jsonPath("$.[*].jtsts").value(hasItem(DEFAULT_JTSTS)))
            .andExpect(jsonPath("$.[*].jtdesc").value(hasItem(DEFAULT_JTDESC)))
            .andExpect(jsonPath("$.[*].jtsdes").value(hasItem(DEFAULT_JTSDES)))
            .andExpect(jsonPath("$.[*].jtlmd").value(hasItem(DEFAULT_JTLMD.toString())))
            .andExpect(jsonPath("$.[*].jtouid").value(hasItem(DEFAULT_JTOUID)));
    }

    @Test
    @Transactional
    void getTABJTRX() throws Exception {
        // Initialize the database
        tABJTRX.setJtjntx(UUID.randomUUID().toString());
        tABJTRXRepository.saveAndFlush(tABJTRX);

        // Get the tABJTRX
        restTABJTRXMockMvc
            .perform(get(ENTITY_API_URL_ID, tABJTRX.getJtjntx()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.jtjntx").value(tABJTRX.getJtjntx()))
            .andExpect(jsonPath("$.jtsts").value(DEFAULT_JTSTS))
            .andExpect(jsonPath("$.jtdesc").value(DEFAULT_JTDESC))
            .andExpect(jsonPath("$.jtsdes").value(DEFAULT_JTSDES))
            .andExpect(jsonPath("$.jtlmd").value(DEFAULT_JTLMD.toString()))
            .andExpect(jsonPath("$.jtouid").value(DEFAULT_JTOUID));
    }

    @Test
    @Transactional
    void getNonExistingTABJTRX() throws Exception {
        // Get the tABJTRX
        restTABJTRXMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTABJTRX() throws Exception {
        // Initialize the database
        tABJTRX.setJtjntx(UUID.randomUUID().toString());
        tABJTRXRepository.saveAndFlush(tABJTRX);

        int databaseSizeBeforeUpdate = tABJTRXRepository.findAll().size();

        // Update the tABJTRX
        TABJTRX updatedTABJTRX = tABJTRXRepository.findById(tABJTRX.getJtjntx()).get();
        // Disconnect from session so that the updates on updatedTABJTRX are not directly saved in db
        em.detach(updatedTABJTRX);
        updatedTABJTRX.jtsts(UPDATED_JTSTS).jtdesc(UPDATED_JTDESC).jtsdes(UPDATED_JTSDES).jtlmd(UPDATED_JTLMD).jtouid(UPDATED_JTOUID);

        restTABJTRXMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTABJTRX.getJtjntx())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTABJTRX))
            )
            .andExpect(status().isOk());

        // Validate the TABJTRX in the database
        List<TABJTRX> tABJTRXList = tABJTRXRepository.findAll();
        assertThat(tABJTRXList).hasSize(databaseSizeBeforeUpdate);
        TABJTRX testTABJTRX = tABJTRXList.get(tABJTRXList.size() - 1);
        assertThat(testTABJTRX.getJtsts()).isEqualTo(UPDATED_JTSTS);
        assertThat(testTABJTRX.getJtdesc()).isEqualTo(UPDATED_JTDESC);
        assertThat(testTABJTRX.getJtsdes()).isEqualTo(UPDATED_JTSDES);
        assertThat(testTABJTRX.getJtlmd()).isEqualTo(UPDATED_JTLMD);
        assertThat(testTABJTRX.getJtouid()).isEqualTo(UPDATED_JTOUID);
    }

    @Test
    @Transactional
    void putNonExistingTABJTRX() throws Exception {
        int databaseSizeBeforeUpdate = tABJTRXRepository.findAll().size();
        tABJTRX.setJtjntx(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTABJTRXMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tABJTRX.getJtjntx())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tABJTRX))
            )
            .andExpect(status().isBadRequest());

        // Validate the TABJTRX in the database
        List<TABJTRX> tABJTRXList = tABJTRXRepository.findAll();
        assertThat(tABJTRXList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTABJTRX() throws Exception {
        int databaseSizeBeforeUpdate = tABJTRXRepository.findAll().size();
        tABJTRX.setJtjntx(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTABJTRXMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tABJTRX))
            )
            .andExpect(status().isBadRequest());

        // Validate the TABJTRX in the database
        List<TABJTRX> tABJTRXList = tABJTRXRepository.findAll();
        assertThat(tABJTRXList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTABJTRX() throws Exception {
        int databaseSizeBeforeUpdate = tABJTRXRepository.findAll().size();
        tABJTRX.setJtjntx(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTABJTRXMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tABJTRX)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TABJTRX in the database
        List<TABJTRX> tABJTRXList = tABJTRXRepository.findAll();
        assertThat(tABJTRXList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTABJTRXWithPatch() throws Exception {
        // Initialize the database
        tABJTRX.setJtjntx(UUID.randomUUID().toString());
        tABJTRXRepository.saveAndFlush(tABJTRX);

        int databaseSizeBeforeUpdate = tABJTRXRepository.findAll().size();

        // Update the tABJTRX using partial update
        TABJTRX partialUpdatedTABJTRX = new TABJTRX();
        partialUpdatedTABJTRX.setJtjntx(tABJTRX.getJtjntx());

        partialUpdatedTABJTRX.jtsts(UPDATED_JTSTS).jtdesc(UPDATED_JTDESC).jtsdes(UPDATED_JTSDES);

        restTABJTRXMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTABJTRX.getJtjntx())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTABJTRX))
            )
            .andExpect(status().isOk());

        // Validate the TABJTRX in the database
        List<TABJTRX> tABJTRXList = tABJTRXRepository.findAll();
        assertThat(tABJTRXList).hasSize(databaseSizeBeforeUpdate);
        TABJTRX testTABJTRX = tABJTRXList.get(tABJTRXList.size() - 1);
        assertThat(testTABJTRX.getJtsts()).isEqualTo(UPDATED_JTSTS);
        assertThat(testTABJTRX.getJtdesc()).isEqualTo(UPDATED_JTDESC);
        assertThat(testTABJTRX.getJtsdes()).isEqualTo(UPDATED_JTSDES);
        assertThat(testTABJTRX.getJtlmd()).isEqualTo(DEFAULT_JTLMD);
        assertThat(testTABJTRX.getJtouid()).isEqualTo(DEFAULT_JTOUID);
    }

    @Test
    @Transactional
    void fullUpdateTABJTRXWithPatch() throws Exception {
        // Initialize the database
        tABJTRX.setJtjntx(UUID.randomUUID().toString());
        tABJTRXRepository.saveAndFlush(tABJTRX);

        int databaseSizeBeforeUpdate = tABJTRXRepository.findAll().size();

        // Update the tABJTRX using partial update
        TABJTRX partialUpdatedTABJTRX = new TABJTRX();
        partialUpdatedTABJTRX.setJtjntx(tABJTRX.getJtjntx());

        partialUpdatedTABJTRX
            .jtsts(UPDATED_JTSTS)
            .jtdesc(UPDATED_JTDESC)
            .jtsdes(UPDATED_JTSDES)
            .jtlmd(UPDATED_JTLMD)
            .jtouid(UPDATED_JTOUID);

        restTABJTRXMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTABJTRX.getJtjntx())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTABJTRX))
            )
            .andExpect(status().isOk());

        // Validate the TABJTRX in the database
        List<TABJTRX> tABJTRXList = tABJTRXRepository.findAll();
        assertThat(tABJTRXList).hasSize(databaseSizeBeforeUpdate);
        TABJTRX testTABJTRX = tABJTRXList.get(tABJTRXList.size() - 1);
        assertThat(testTABJTRX.getJtsts()).isEqualTo(UPDATED_JTSTS);
        assertThat(testTABJTRX.getJtdesc()).isEqualTo(UPDATED_JTDESC);
        assertThat(testTABJTRX.getJtsdes()).isEqualTo(UPDATED_JTSDES);
        assertThat(testTABJTRX.getJtlmd()).isEqualTo(UPDATED_JTLMD);
        assertThat(testTABJTRX.getJtouid()).isEqualTo(UPDATED_JTOUID);
    }

    @Test
    @Transactional
    void patchNonExistingTABJTRX() throws Exception {
        int databaseSizeBeforeUpdate = tABJTRXRepository.findAll().size();
        tABJTRX.setJtjntx(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTABJTRXMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tABJTRX.getJtjntx())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tABJTRX))
            )
            .andExpect(status().isBadRequest());

        // Validate the TABJTRX in the database
        List<TABJTRX> tABJTRXList = tABJTRXRepository.findAll();
        assertThat(tABJTRXList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTABJTRX() throws Exception {
        int databaseSizeBeforeUpdate = tABJTRXRepository.findAll().size();
        tABJTRX.setJtjntx(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTABJTRXMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tABJTRX))
            )
            .andExpect(status().isBadRequest());

        // Validate the TABJTRX in the database
        List<TABJTRX> tABJTRXList = tABJTRXRepository.findAll();
        assertThat(tABJTRXList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTABJTRX() throws Exception {
        int databaseSizeBeforeUpdate = tABJTRXRepository.findAll().size();
        tABJTRX.setJtjntx(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTABJTRXMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tABJTRX)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TABJTRX in the database
        List<TABJTRX> tABJTRXList = tABJTRXRepository.findAll();
        assertThat(tABJTRXList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTABJTRX() throws Exception {
        // Initialize the database
        tABJTRX.setJtjntx(UUID.randomUUID().toString());
        tABJTRXRepository.saveAndFlush(tABJTRX);

        int databaseSizeBeforeDelete = tABJTRXRepository.findAll().size();

        // Delete the tABJTRX
        restTABJTRXMockMvc
            .perform(delete(ENTITY_API_URL_ID, tABJTRX.getJtjntx()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TABJTRX> tABJTRXList = tABJTRXRepository.findAll();
        assertThat(tABJTRXList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
