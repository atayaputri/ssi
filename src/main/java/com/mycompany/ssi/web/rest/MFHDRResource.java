package com.mycompany.ssi.web.rest;

import com.mycompany.ssi.domain.MFHDR;
import com.mycompany.ssi.repository.MFHDRRepository;
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
 * REST controller for managing {@link com.mycompany.ssi.domain.MFHDR}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MFHDRResource {

    private final Logger log = LoggerFactory.getLogger(MFHDRResource.class);

    private static final String ENTITY_NAME = "mFHDR";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MFHDRRepository mFHDRRepository;

    public MFHDRResource(MFHDRRepository mFHDRRepository) {
        this.mFHDRRepository = mFHDRRepository;
    }

    /**
     * {@code POST  /mfhdrs} : Create a new mFHDR.
     *
     * @param mFHDR the mFHDR to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mFHDR, or with status {@code 400 (Bad Request)} if the mFHDR has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mfhdrs")
    public ResponseEntity<MFHDR> createMFHDR(@Valid @RequestBody MFHDR mFHDR) throws URISyntaxException {
        log.debug("REST request to save MFHDR : {}", mFHDR);
        if (mFHDR.getHdno() != null) {
            throw new BadRequestAlertException("A new mFHDR cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MFHDR result = mFHDRRepository.save(mFHDR);
        return ResponseEntity
            .created(new URI("/api/mfhdrs/" + result.getHdno()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getHdno().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mfhdrs/:hdno} : Updates an existing mFHDR.
     *
     * @param hdno the id of the mFHDR to save.
     * @param mFHDR the mFHDR to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mFHDR,
     * or with status {@code 400 (Bad Request)} if the mFHDR is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mFHDR couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mfhdrs/{hdno}")
    public ResponseEntity<MFHDR> updateMFHDR(
        @PathVariable(value = "hdno", required = false) final Long hdno,
        @Valid @RequestBody MFHDR mFHDR
    ) throws URISyntaxException {
        log.debug("REST request to update MFHDR : {}, {}", hdno, mFHDR);
        if (mFHDR.getHdno() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(hdno, mFHDR.getHdno())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mFHDRRepository.existsById(hdno)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MFHDR result = mFHDRRepository.save(mFHDR);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mFHDR.getHdno().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mfhdrs/:hdno} : Partial updates given fields of an existing mFHDR, field will ignore if it is null
     *
     * @param hdno the id of the mFHDR to save.
     * @param mFHDR the mFHDR to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mFHDR,
     * or with status {@code 400 (Bad Request)} if the mFHDR is not valid,
     * or with status {@code 404 (Not Found)} if the mFHDR is not found,
     * or with status {@code 500 (Internal Server Error)} if the mFHDR couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mfhdrs/{hdno}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MFHDR> partialUpdateMFHDR(
        @PathVariable(value = "hdno", required = false) final Long hdno,
        @NotNull @RequestBody MFHDR mFHDR
    ) throws URISyntaxException {
        log.debug("REST request to partial update MFHDR partially : {}, {}", hdno, mFHDR);
        if (mFHDR.getHdno() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(hdno, mFHDR.getHdno())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mFHDRRepository.existsById(hdno)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MFHDR> result = mFHDRRepository
            .findById(mFHDR.getHdno())
            .map(existingMFHDR -> {
                if (mFHDR.getHdsts() != null) {
                    existingMFHDR.setHdsts(mFHDR.getHdsts());
                }
                if (mFHDR.getHdsid() != null) {
                    existingMFHDR.setHdsid(mFHDR.getHdsid());
                }
                if (mFHDR.getHdnm1() != null) {
                    existingMFHDR.setHdnm1(mFHDR.getHdnm1());
                }
                if (mFHDR.getHdnm2() != null) {
                    existingMFHDR.setHdnm2(mFHDR.getHdnm2());
                }
                if (mFHDR.getHdal1() != null) {
                    existingMFHDR.setHdal1(mFHDR.getHdal1());
                }
                if (mFHDR.getHdal2() != null) {
                    existingMFHDR.setHdal2(mFHDR.getHdal2());
                }
                if (mFHDR.getHdjsh() != null) {
                    existingMFHDR.setHdjsh(mFHDR.getHdjsh());
                }
                if (mFHDR.getHdinco() != null) {
                    existingMFHDR.setHdinco(mFHDR.getHdinco());
                }
                if (mFHDR.getHdkwn() != null) {
                    existingMFHDR.setHdkwn(mFHDR.getHdkwn());
                }
                if (mFHDR.getHdktp() != null) {
                    existingMFHDR.setHdktp(mFHDR.getHdktp());
                }
                if (mFHDR.getHdnpwp() != null) {
                    existingMFHDR.setHdnpwp(mFHDR.getHdnpwp());
                }
                if (mFHDR.getHdsiup() != null) {
                    existingMFHDR.setHdsiup(mFHDR.getHdsiup());
                }
                if (mFHDR.getHdtax() != null) {
                    existingMFHDR.setHdtax(mFHDR.getHdtax());
                }
                if (mFHDR.getHddis() != null) {
                    existingMFHDR.setHddis(mFHDR.getHddis());
                }
                if (mFHDR.getHdlmd() != null) {
                    existingMFHDR.setHdlmd(mFHDR.getHdlmd());
                }
                if (mFHDR.getHduid() != null) {
                    existingMFHDR.setHduid(mFHDR.getHduid());
                }

                return existingMFHDR;
            })
            .map(mFHDRRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mFHDR.getHdno().toString())
        );
    }

    /**
     * {@code GET  /mfhdrs} : get all the mFHDRS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mFHDRS in body.
     */
    @GetMapping("/mfhdrs")
    public List<MFHDR> getAllMFHDRS() {
        log.debug("REST request to get all MFHDRS");
        return mFHDRRepository.findAll();
    }

    /**
     * {@code GET  /mfhdrs/:id} : get the "id" mFHDR.
     *
     * @param id the id of the mFHDR to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mFHDR, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mfhdrs/{id}")
    public ResponseEntity<MFHDR> getMFHDR(@PathVariable Long id) {
        log.debug("REST request to get MFHDR : {}", id);
        Optional<MFHDR> mFHDR = mFHDRRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mFHDR);
    }

    /**
     * {@code DELETE  /mfhdrs/:id} : delete the "id" mFHDR.
     *
     * @param id the id of the mFHDR to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mfhdrs/{id}")
    public ResponseEntity<Void> deleteMFHDR(@PathVariable Long id) {
        log.debug("REST request to delete MFHDR : {}", id);
        mFHDRRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
