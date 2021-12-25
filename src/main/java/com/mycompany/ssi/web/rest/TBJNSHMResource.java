package com.mycompany.ssi.web.rest;

import com.mycompany.ssi.domain.TBJNSHM;
import com.mycompany.ssi.repository.TBJNSHMRepository;
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
 * REST controller for managing {@link com.mycompany.ssi.domain.TBJNSHM}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TBJNSHMResource {

    private final Logger log = LoggerFactory.getLogger(TBJNSHMResource.class);

    private static final String ENTITY_NAME = "tBJNSHM";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TBJNSHMRepository tBJNSHMRepository;

    public TBJNSHMResource(TBJNSHMRepository tBJNSHMRepository) {
        this.tBJNSHMRepository = tBJNSHMRepository;
    }

    /**
     * {@code POST  /tbjnshms} : Create a new tBJNSHM.
     *
     * @param tBJNSHM the tBJNSHM to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tBJNSHM, or with status {@code 400 (Bad Request)} if the tBJNSHM has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tbjnshms")
    public ResponseEntity<TBJNSHM> createTBJNSHM(@Valid @RequestBody TBJNSHM tBJNSHM) throws URISyntaxException {
        log.debug("REST request to save TBJNSHM : {}", tBJNSHM);
        if (tBJNSHM.getJshcod() != null) {
            throw new BadRequestAlertException("A new tBJNSHM cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TBJNSHM result = tBJNSHMRepository.save(tBJNSHM);
        return ResponseEntity
            .created(new URI("/api/tbjnshms/" + result.getJshcod()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getJshcod()))
            .body(result);
    }

    /**
     * {@code PUT  /tbjnshms/:jshcod} : Updates an existing tBJNSHM.
     *
     * @param jshcod the id of the tBJNSHM to save.
     * @param tBJNSHM the tBJNSHM to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBJNSHM,
     * or with status {@code 400 (Bad Request)} if the tBJNSHM is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tBJNSHM couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tbjnshms/{jshcod}")
    public ResponseEntity<TBJNSHM> updateTBJNSHM(
        @PathVariable(value = "jshcod", required = false) final String jshcod,
        @Valid @RequestBody TBJNSHM tBJNSHM
    ) throws URISyntaxException {
        log.debug("REST request to update TBJNSHM : {}, {}", jshcod, tBJNSHM);
        if (tBJNSHM.getJshcod() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(jshcod, tBJNSHM.getJshcod())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBJNSHMRepository.existsById(jshcod)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TBJNSHM result = tBJNSHMRepository.save(tBJNSHM);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBJNSHM.getJshcod()))
            .body(result);
    }

    /**
     * {@code PATCH  /tbjnshms/:jshcod} : Partial updates given fields of an existing tBJNSHM, field will ignore if it is null
     *
     * @param jshcod the id of the tBJNSHM to save.
     * @param tBJNSHM the tBJNSHM to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBJNSHM,
     * or with status {@code 400 (Bad Request)} if the tBJNSHM is not valid,
     * or with status {@code 404 (Not Found)} if the tBJNSHM is not found,
     * or with status {@code 500 (Internal Server Error)} if the tBJNSHM couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tbjnshms/{jshcod}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TBJNSHM> partialUpdateTBJNSHM(
        @PathVariable(value = "jshcod", required = false) final String jshcod,
        @NotNull @RequestBody TBJNSHM tBJNSHM
    ) throws URISyntaxException {
        log.debug("REST request to partial update TBJNSHM partially : {}, {}", jshcod, tBJNSHM);
        if (tBJNSHM.getJshcod() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(jshcod, tBJNSHM.getJshcod())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBJNSHMRepository.existsById(jshcod)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TBJNSHM> result = tBJNSHMRepository
            .findById(tBJNSHM.getJshcod())
            .map(existingTBJNSHM -> {
                if (tBJNSHM.getJshsts() != null) {
                    existingTBJNSHM.setJshsts(tBJNSHM.getJshsts());
                }
                if (tBJNSHM.getJshnam() != null) {
                    existingTBJNSHM.setJshnam(tBJNSHM.getJshnam());
                }
                if (tBJNSHM.getJshlmd() != null) {
                    existingTBJNSHM.setJshlmd(tBJNSHM.getJshlmd());
                }
                if (tBJNSHM.getJshuid() != null) {
                    existingTBJNSHM.setJshuid(tBJNSHM.getJshuid());
                }

                return existingTBJNSHM;
            })
            .map(tBJNSHMRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBJNSHM.getJshcod())
        );
    }

    /**
     * {@code GET  /tbjnshms} : get all the tBJNSHMS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tBJNSHMS in body.
     */
    @GetMapping("/tbjnshms")
    public List<TBJNSHM> getAllTBJNSHMS() {
        log.debug("REST request to get all TBJNSHMS");
        return tBJNSHMRepository.findAll();
    }

    /**
     * {@code GET  /tbjnshms/:id} : get the "id" tBJNSHM.
     *
     * @param id the id of the tBJNSHM to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tBJNSHM, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tbjnshms/{id}")
    public ResponseEntity<TBJNSHM> getTBJNSHM(@PathVariable String id) {
        log.debug("REST request to get TBJNSHM : {}", id);
        Optional<TBJNSHM> tBJNSHM = tBJNSHMRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tBJNSHM);
    }

    /**
     * {@code DELETE  /tbjnshms/:id} : delete the "id" tBJNSHM.
     *
     * @param id the id of the tBJNSHM to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tbjnshms/{id}")
    public ResponseEntity<Void> deleteTBJNSHM(@PathVariable String id) {
        log.debug("REST request to delete TBJNSHM : {}", id);
        tBJNSHMRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
