package com.mycompany.ssi.web.rest;

import com.mycompany.ssi.domain.MFSHM;
import com.mycompany.ssi.repository.MFSHMRepository;
import com.mycompany.ssi.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.ssi.domain.MFSHM}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MFSHMResource {

    private final Logger log = LoggerFactory.getLogger(MFSHMResource.class);

    private static final String ENTITY_NAME = "mFSHM";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MFSHMRepository mFSHMRepository;

    public MFSHMResource(MFSHMRepository mFSHMRepository) {
        this.mFSHMRepository = mFSHMRepository;
    }

    /**
     * {@code POST  /mfshms} : Create a new mFSHM.
     *
     * @param mFSHM the mFSHM to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mFSHM, or with status {@code 400 (Bad Request)} if the mFSHM has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mfshms")
    public ResponseEntity<MFSHM> createMFSHM(@Valid @RequestBody MFSHM mFSHM) throws URISyntaxException {
        log.debug("REST request to save MFSHM : {}", mFSHM);
        if (mFSHM.getId() != null) {
            throw new BadRequestAlertException("A new mFSHM cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MFSHM result = mFSHMRepository.save(mFSHM);
        return ResponseEntity
            .created(new URI("/api/mfshms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mfshms/:id} : Updates an existing mFSHM.
     *
     * @param id the id of the mFSHM to save.
     * @param mFSHM the mFSHM to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mFSHM,
     * or with status {@code 400 (Bad Request)} if the mFSHM is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mFSHM couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mfshms/{id}")
    public ResponseEntity<MFSHM> updateMFSHM(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody MFSHM mFSHM)
        throws URISyntaxException {
        log.debug("REST request to update MFSHM : {}, {}", id, mFSHM);
        if (mFSHM.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mFSHM.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mFSHMRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MFSHM result = mFSHMRepository.save(mFSHM);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mFSHM.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mfshms/:id} : Partial updates given fields of an existing mFSHM, field will ignore if it is null
     *
     * @param id the id of the mFSHM to save.
     * @param mFSHM the mFSHM to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mFSHM,
     * or with status {@code 400 (Bad Request)} if the mFSHM is not valid,
     * or with status {@code 404 (Not Found)} if the mFSHM is not found,
     * or with status {@code 500 (Internal Server Error)} if the mFSHM couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mfshms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MFSHM> partialUpdateMFSHM(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MFSHM mFSHM
    ) throws URISyntaxException {
        log.debug("REST request to partial update MFSHM partially : {}, {}", id, mFSHM);
        if (mFSHM.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mFSHM.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mFSHMRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MFSHM> result = mFSHMRepository
            .findById(mFSHM.getId())
            .map(existingMFSHM -> {
                if (mFSHM.getShsts() != null) {
                    existingMFSHM.setShsts(mFSHM.getShsts());
                }
                if (mFSHM.getShfr() != null) {
                    existingMFSHM.setShfr(mFSHM.getShfr());
                }
                if (mFSHM.getShto() != null) {
                    existingMFSHM.setShto(mFSHM.getShto());
                }
                if (mFSHM.getShjshm() != null) {
                    existingMFSHM.setShjshm(mFSHM.getShjshm());
                }
                if (mFSHM.getShbat() != null) {
                    existingMFSHM.setShbat(mFSHM.getShbat());
                }
                if (mFSHM.getShseq() != null) {
                    existingMFSHM.setShseq(mFSHM.getShseq());
                }
                if (mFSHM.getShref() != null) {
                    existingMFSHM.setShref(mFSHM.getShref());
                }
                if (mFSHM.getShdis() != null) {
                    existingMFSHM.setShdis(mFSHM.getShdis());
                }
                if (mFSHM.getShlmd() != null) {
                    existingMFSHM.setShlmd(mFSHM.getShlmd());
                }
                if (mFSHM.getShuid() != null) {
                    existingMFSHM.setShuid(mFSHM.getShuid());
                }

                return existingMFSHM;
            })
            .map(mFSHMRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mFSHM.getId().toString())
        );
    }

    /**
     * {@code GET  /mfshms} : get all the mFSHMS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mFSHMS in body.
     */
    @GetMapping("/mfshms")
    public List<MFSHM> getAllMFSHMS() {
        log.debug("REST request to get all MFSHMS");
        return mFSHMRepository.findAll();
    }

    /**
     * {@code GET  /mfshms/:id} : get the "id" mFSHM.
     *
     * @param id the id of the mFSHM to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mFSHM, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mfshms/{id}")
    public ResponseEntity<MFSHM> getMFSHM(@PathVariable Long id) {
        log.debug("REST request to get MFSHM : {}", id);
        Optional<MFSHM> mFSHM = mFSHMRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mFSHM);
    }

    /**
     * {@code DELETE  /mfshms/:id} : delete the "id" mFSHM.
     *
     * @param id the id of the mFSHM to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mfshms/{id}")
    public ResponseEntity<Void> deleteMFSHM(@PathVariable Long id) {
        log.debug("REST request to delete MFSHM : {}", id);
        mFSHMRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
