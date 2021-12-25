package com.mycompany.ssi.web.rest;

import com.mycompany.ssi.domain.MFSKS;
import com.mycompany.ssi.repository.MFSKSRepository;
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
 * REST controller for managing {@link com.mycompany.ssi.domain.MFSKS}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MFSKSResource {

    private final Logger log = LoggerFactory.getLogger(MFSKSResource.class);

    private static final String ENTITY_NAME = "mFSKS";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MFSKSRepository mFSKSRepository;

    public MFSKSResource(MFSKSRepository mFSKSRepository) {
        this.mFSKSRepository = mFSKSRepository;
    }

    /**
     * {@code POST  /mfsks} : Create a new mFSKS.
     *
     * @param mFSKS the mFSKS to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mFSKS, or with status {@code 400 (Bad Request)} if the mFSKS has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mfsks")
    public ResponseEntity<MFSKS> createMFSKS(@Valid @RequestBody MFSKS mFSKS) throws URISyntaxException {
        log.debug("REST request to save MFSKS : {}", mFSKS);
        if (mFSKS.getSkno() != null) {
            throw new BadRequestAlertException("A new mFSKS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MFSKS result = mFSKSRepository.save(mFSKS);
        return ResponseEntity
            .created(new URI("/api/mfsks/" + result.getSkno()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getSkno().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mfsks/:skno} : Updates an existing mFSKS.
     *
     * @param skno the id of the mFSKS to save.
     * @param mFSKS the mFSKS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mFSKS,
     * or with status {@code 400 (Bad Request)} if the mFSKS is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mFSKS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mfsks/{skno}")
    public ResponseEntity<MFSKS> updateMFSKS(
        @PathVariable(value = "skno", required = false) final Long skno,
        @Valid @RequestBody MFSKS mFSKS
    ) throws URISyntaxException {
        log.debug("REST request to update MFSKS : {}, {}", skno, mFSKS);
        if (mFSKS.getSkno() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(skno, mFSKS.getSkno())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mFSKSRepository.existsById(skno)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MFSKS result = mFSKSRepository.save(mFSKS);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mFSKS.getSkno().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mfsks/:skno} : Partial updates given fields of an existing mFSKS, field will ignore if it is null
     *
     * @param skno the id of the mFSKS to save.
     * @param mFSKS the mFSKS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mFSKS,
     * or with status {@code 400 (Bad Request)} if the mFSKS is not valid,
     * or with status {@code 404 (Not Found)} if the mFSKS is not found,
     * or with status {@code 500 (Internal Server Error)} if the mFSKS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mfsks/{skno}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MFSKS> partialUpdateMFSKS(
        @PathVariable(value = "skno", required = false) final Long skno,
        @NotNull @RequestBody MFSKS mFSKS
    ) throws URISyntaxException {
        log.debug("REST request to partial update MFSKS partially : {}, {}", skno, mFSKS);
        if (mFSKS.getSkno() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(skno, mFSKS.getSkno())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mFSKSRepository.existsById(skno)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MFSKS> result = mFSKSRepository
            .findById(mFSKS.getSkno())
            .map(existingMFSKS -> {
                if (mFSKS.getSksts() != null) {
                    existingMFSKS.setSksts(mFSKS.getSksts());
                }
                if (mFSKS.getSkjsh() != null) {
                    existingMFSKS.setSkjsh(mFSKS.getSkjsh());
                }
                if (mFSKS.getSkbat() != null) {
                    existingMFSKS.setSkbat(mFSKS.getSkbat());
                }
                if (mFSKS.getSkseq() != null) {
                    existingMFSKS.setSkseq(mFSKS.getSkseq());
                }
                if (mFSKS.getSkref() != null) {
                    existingMFSKS.setSkref(mFSKS.getSkref());
                }
                if (mFSKS.getSkdis() != null) {
                    existingMFSKS.setSkdis(mFSKS.getSkdis());
                }
                if (mFSKS.getSklmd() != null) {
                    existingMFSKS.setSklmd(mFSKS.getSklmd());
                }
                if (mFSKS.getSkuid() != null) {
                    existingMFSKS.setSkuid(mFSKS.getSkuid());
                }
                if (mFSKS.getSkfil1() != null) {
                    existingMFSKS.setSkfil1(mFSKS.getSkfil1());
                }
                if (mFSKS.getSkfil2() != null) {
                    existingMFSKS.setSkfil2(mFSKS.getSkfil2());
                }
                if (mFSKS.getSkfil3() != null) {
                    existingMFSKS.setSkfil3(mFSKS.getSkfil3());
                }
                if (mFSKS.getSkfil4() != null) {
                    existingMFSKS.setSkfil4(mFSKS.getSkfil4());
                }

                return existingMFSKS;
            })
            .map(mFSKSRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mFSKS.getSkno().toString())
        );
    }

    /**
     * {@code GET  /mfsks} : get all the mFSKS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mFSKS in body.
     */
    @GetMapping("/mfsks")
    public List<MFSKS> getAllMFSKS() {
        log.debug("REST request to get all MFSKS");
        return mFSKSRepository.findAll();
    }

    /**
     * {@code GET  /mfsks/:id} : get the "id" mFSKS.
     *
     * @param id the id of the mFSKS to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mFSKS, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mfsks/{id}")
    public ResponseEntity<MFSKS> getMFSKS(@PathVariable Long id) {
        log.debug("REST request to get MFSKS : {}", id);
        Optional<MFSKS> mFSKS = mFSKSRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mFSKS);
    }

    /**
     * {@code DELETE  /mfsks/:id} : delete the "id" mFSKS.
     *
     * @param id the id of the mFSKS to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mfsks/{id}")
    public ResponseEntity<Void> deleteMFSKS(@PathVariable Long id) {
        log.debug("REST request to delete MFSKS : {}", id);
        mFSKSRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
