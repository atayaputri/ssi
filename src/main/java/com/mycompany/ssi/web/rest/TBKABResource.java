package com.mycompany.ssi.web.rest;

import com.mycompany.ssi.domain.TBKAB;
import com.mycompany.ssi.repository.TBKABRepository;
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
 * REST controller for managing {@link com.mycompany.ssi.domain.TBKAB}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TBKABResource {

    private final Logger log = LoggerFactory.getLogger(TBKABResource.class);

    private static final String ENTITY_NAME = "tBKAB";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TBKABRepository tBKABRepository;

    public TBKABResource(TBKABRepository tBKABRepository) {
        this.tBKABRepository = tBKABRepository;
    }

    /**
     * {@code POST  /tbkabs} : Create a new tBKAB.
     *
     * @param tBKAB the tBKAB to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tBKAB, or with status {@code 400 (Bad Request)} if the tBKAB has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tbkabs")
    public ResponseEntity<TBKAB> createTBKAB(@Valid @RequestBody TBKAB tBKAB) throws URISyntaxException {
        log.debug("REST request to save TBKAB : {}", tBKAB);
        if (tBKAB.getKabcod() != null) {
            throw new BadRequestAlertException("A new tBKAB cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TBKAB result = tBKABRepository.save(tBKAB);
        return ResponseEntity
            .created(new URI("/api/tbkabs/" + result.getKabcod()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getKabcod()))
            .body(result);
    }

    /**
     * {@code PUT  /tbkabs/:kabcod} : Updates an existing tBKAB.
     *
     * @param kabcod the id of the tBKAB to save.
     * @param tBKAB the tBKAB to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBKAB,
     * or with status {@code 400 (Bad Request)} if the tBKAB is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tBKAB couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tbkabs/{kabcod}")
    public ResponseEntity<TBKAB> updateTBKAB(
        @PathVariable(value = "kabcod", required = false) final String kabcod,
        @Valid @RequestBody TBKAB tBKAB
    ) throws URISyntaxException {
        log.debug("REST request to update TBKAB : {}, {}", kabcod, tBKAB);
        if (tBKAB.getKabcod() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(kabcod, tBKAB.getKabcod())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBKABRepository.existsById(kabcod)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TBKAB result = tBKABRepository.save(tBKAB);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBKAB.getKabcod()))
            .body(result);
    }

    /**
     * {@code PATCH  /tbkabs/:kabcod} : Partial updates given fields of an existing tBKAB, field will ignore if it is null
     *
     * @param kabcod the id of the tBKAB to save.
     * @param tBKAB the tBKAB to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBKAB,
     * or with status {@code 400 (Bad Request)} if the tBKAB is not valid,
     * or with status {@code 404 (Not Found)} if the tBKAB is not found,
     * or with status {@code 500 (Internal Server Error)} if the tBKAB couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tbkabs/{kabcod}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TBKAB> partialUpdateTBKAB(
        @PathVariable(value = "kabcod", required = false) final String kabcod,
        @NotNull @RequestBody TBKAB tBKAB
    ) throws URISyntaxException {
        log.debug("REST request to partial update TBKAB partially : {}, {}", kabcod, tBKAB);
        if (tBKAB.getKabcod() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(kabcod, tBKAB.getKabcod())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBKABRepository.existsById(kabcod)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TBKAB> result = tBKABRepository
            .findById(tBKAB.getKabcod())
            .map(existingTBKAB -> {
                if (tBKAB.getKabsts() != null) {
                    existingTBKAB.setKabsts(tBKAB.getKabsts());
                }
                if (tBKAB.getKabnam() != null) {
                    existingTBKAB.setKabnam(tBKAB.getKabnam());
                }
                if (tBKAB.getKablmd() != null) {
                    existingTBKAB.setKablmd(tBKAB.getKablmd());
                }
                if (tBKAB.getKabuid() != null) {
                    existingTBKAB.setKabuid(tBKAB.getKabuid());
                }

                return existingTBKAB;
            })
            .map(tBKABRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBKAB.getKabcod())
        );
    }

    /**
     * {@code GET  /tbkabs} : get all the tBKABS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tBKABS in body.
     */
    @GetMapping("/tbkabs")
    public List<TBKAB> getAllTBKABS() {
        log.debug("REST request to get all TBKABS");
        return tBKABRepository.findAll();
    }

    /**
     * {@code GET  /tbkabs/:id} : get the "id" tBKAB.
     *
     * @param id the id of the tBKAB to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tBKAB, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tbkabs/{id}")
    public ResponseEntity<TBKAB> getTBKAB(@PathVariable String id) {
        log.debug("REST request to get TBKAB : {}", id);
        Optional<TBKAB> tBKAB = tBKABRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tBKAB);
    }

    /**
     * {@code DELETE  /tbkabs/:id} : delete the "id" tBKAB.
     *
     * @param id the id of the tBKAB to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tbkabs/{id}")
    public ResponseEntity<Void> deleteTBKAB(@PathVariable String id) {
        log.debug("REST request to delete TBKAB : {}", id);
        tBKABRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
