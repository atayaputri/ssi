package com.mycompany.ssi.web.rest;

import com.mycompany.ssi.domain.TBPROV;
import com.mycompany.ssi.repository.TBPROVRepository;
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
 * REST controller for managing {@link com.mycompany.ssi.domain.TBPROV}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TBPROVResource {

    private final Logger log = LoggerFactory.getLogger(TBPROVResource.class);

    private static final String ENTITY_NAME = "tBPROV";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TBPROVRepository tBPROVRepository;

    public TBPROVResource(TBPROVRepository tBPROVRepository) {
        this.tBPROVRepository = tBPROVRepository;
    }

    /**
     * {@code POST  /tbprovs} : Create a new tBPROV.
     *
     * @param tBPROV the tBPROV to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tBPROV, or with status {@code 400 (Bad Request)} if the tBPROV has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tbprovs")
    public ResponseEntity<TBPROV> createTBPROV(@Valid @RequestBody TBPROV tBPROV) throws URISyntaxException {
        log.debug("REST request to save TBPROV : {}", tBPROV);
        if (tBPROV.getProvcod() != null) {
            throw new BadRequestAlertException("A new tBPROV cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TBPROV result = tBPROVRepository.save(tBPROV);
        return ResponseEntity
            .created(new URI("/api/tbprovs/" + result.getProvcod()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getProvcod()))
            .body(result);
    }

    /**
     * {@code PUT  /tbprovs/:provcod} : Updates an existing tBPROV.
     *
     * @param provcod the id of the tBPROV to save.
     * @param tBPROV the tBPROV to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBPROV,
     * or with status {@code 400 (Bad Request)} if the tBPROV is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tBPROV couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tbprovs/{provcod}")
    public ResponseEntity<TBPROV> updateTBPROV(
        @PathVariable(value = "provcod", required = false) final String provcod,
        @Valid @RequestBody TBPROV tBPROV
    ) throws URISyntaxException {
        log.debug("REST request to update TBPROV : {}, {}", provcod, tBPROV);
        if (tBPROV.getProvcod() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(provcod, tBPROV.getProvcod())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBPROVRepository.existsById(provcod)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TBPROV result = tBPROVRepository.save(tBPROV);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBPROV.getProvcod()))
            .body(result);
    }

    /**
     * {@code PATCH  /tbprovs/:provcod} : Partial updates given fields of an existing tBPROV, field will ignore if it is null
     *
     * @param provcod the id of the tBPROV to save.
     * @param tBPROV the tBPROV to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBPROV,
     * or with status {@code 400 (Bad Request)} if the tBPROV is not valid,
     * or with status {@code 404 (Not Found)} if the tBPROV is not found,
     * or with status {@code 500 (Internal Server Error)} if the tBPROV couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tbprovs/{provcod}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TBPROV> partialUpdateTBPROV(
        @PathVariable(value = "provcod", required = false) final String provcod,
        @NotNull @RequestBody TBPROV tBPROV
    ) throws URISyntaxException {
        log.debug("REST request to partial update TBPROV partially : {}, {}", provcod, tBPROV);
        if (tBPROV.getProvcod() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(provcod, tBPROV.getProvcod())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBPROVRepository.existsById(provcod)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TBPROV> result = tBPROVRepository
            .findById(tBPROV.getProvcod())
            .map(existingTBPROV -> {
                if (tBPROV.getProvsts() != null) {
                    existingTBPROV.setProvsts(tBPROV.getProvsts());
                }
                if (tBPROV.getProvnam() != null) {
                    existingTBPROV.setProvnam(tBPROV.getProvnam());
                }
                if (tBPROV.getProvlmd() != null) {
                    existingTBPROV.setProvlmd(tBPROV.getProvlmd());
                }
                if (tBPROV.getProvuid() != null) {
                    existingTBPROV.setProvuid(tBPROV.getProvuid());
                }

                return existingTBPROV;
            })
            .map(tBPROVRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBPROV.getProvcod())
        );
    }

    /**
     * {@code GET  /tbprovs} : get all the tBPROVS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tBPROVS in body.
     */
    @GetMapping("/tbprovs")
    public List<TBPROV> getAllTBPROVS() {
        log.debug("REST request to get all TBPROVS");
        return tBPROVRepository.findAll();
    }

    /**
     * {@code GET  /tbprovs/:id} : get the "id" tBPROV.
     *
     * @param id the id of the tBPROV to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tBPROV, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tbprovs/{id}")
    public ResponseEntity<TBPROV> getTBPROV(@PathVariable String id) {
        log.debug("REST request to get TBPROV : {}", id);
        Optional<TBPROV> tBPROV = tBPROVRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tBPROV);
    }

    /**
     * {@code DELETE  /tbprovs/:id} : delete the "id" tBPROV.
     *
     * @param id the id of the tBPROV to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tbprovs/{id}")
    public ResponseEntity<Void> deleteTBPROV(@PathVariable String id) {
        log.debug("REST request to delete TBPROV : {}", id);
        tBPROVRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
