package com.mycompany.ssi.web.rest;

import com.mycompany.ssi.domain.TBTYPS;
import com.mycompany.ssi.repository.TBTYPSRepository;
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
 * REST controller for managing {@link com.mycompany.ssi.domain.TBTYPS}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TBTYPSResource {

    private final Logger log = LoggerFactory.getLogger(TBTYPSResource.class);

    private static final String ENTITY_NAME = "tBTYPS";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TBTYPSRepository tBTYPSRepository;

    public TBTYPSResource(TBTYPSRepository tBTYPSRepository) {
        this.tBTYPSRepository = tBTYPSRepository;
    }

    /**
     * {@code POST  /tbtyps} : Create a new tBTYPS.
     *
     * @param tBTYPS the tBTYPS to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tBTYPS, or with status {@code 400 (Bad Request)} if the tBTYPS has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tbtyps")
    public ResponseEntity<TBTYPS> createTBTYPS(@Valid @RequestBody TBTYPS tBTYPS) throws URISyntaxException {
        log.debug("REST request to save TBTYPS : {}", tBTYPS);
        if (tBTYPS.getTpscod() != null) {
            throw new BadRequestAlertException("A new tBTYPS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TBTYPS result = tBTYPSRepository.save(tBTYPS);
        return ResponseEntity
            .created(new URI("/api/tbtyps/" + result.getTpscod()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getTpscod()))
            .body(result);
    }

    /**
     * {@code PUT  /tbtyps/:tpscod} : Updates an existing tBTYPS.
     *
     * @param tpscod the id of the tBTYPS to save.
     * @param tBTYPS the tBTYPS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBTYPS,
     * or with status {@code 400 (Bad Request)} if the tBTYPS is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tBTYPS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tbtyps/{tpscod}")
    public ResponseEntity<TBTYPS> updateTBTYPS(
        @PathVariable(value = "tpscod", required = false) final String tpscod,
        @Valid @RequestBody TBTYPS tBTYPS
    ) throws URISyntaxException {
        log.debug("REST request to update TBTYPS : {}, {}", tpscod, tBTYPS);
        if (tBTYPS.getTpscod() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(tpscod, tBTYPS.getTpscod())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBTYPSRepository.existsById(tpscod)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TBTYPS result = tBTYPSRepository.save(tBTYPS);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBTYPS.getTpscod()))
            .body(result);
    }

    /**
     * {@code PATCH  /tbtyps/:tpscod} : Partial updates given fields of an existing tBTYPS, field will ignore if it is null
     *
     * @param tpscod the id of the tBTYPS to save.
     * @param tBTYPS the tBTYPS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBTYPS,
     * or with status {@code 400 (Bad Request)} if the tBTYPS is not valid,
     * or with status {@code 404 (Not Found)} if the tBTYPS is not found,
     * or with status {@code 500 (Internal Server Error)} if the tBTYPS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tbtyps/{tpscod}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TBTYPS> partialUpdateTBTYPS(
        @PathVariable(value = "tpscod", required = false) final String tpscod,
        @NotNull @RequestBody TBTYPS tBTYPS
    ) throws URISyntaxException {
        log.debug("REST request to partial update TBTYPS partially : {}, {}", tpscod, tBTYPS);
        if (tBTYPS.getTpscod() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(tpscod, tBTYPS.getTpscod())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBTYPSRepository.existsById(tpscod)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TBTYPS> result = tBTYPSRepository
            .findById(tBTYPS.getTpscod())
            .map(existingTBTYPS -> {
                if (tBTYPS.getTpssts() != null) {
                    existingTBTYPS.setTpssts(tBTYPS.getTpssts());
                }
                if (tBTYPS.getTpsnam() != null) {
                    existingTBTYPS.setTpsnam(tBTYPS.getTpsnam());
                }
                if (tBTYPS.getTpslmd() != null) {
                    existingTBTYPS.setTpslmd(tBTYPS.getTpslmd());
                }
                if (tBTYPS.getTpsuid() != null) {
                    existingTBTYPS.setTpsuid(tBTYPS.getTpsuid());
                }

                return existingTBTYPS;
            })
            .map(tBTYPSRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBTYPS.getTpscod())
        );
    }

    /**
     * {@code GET  /tbtyps} : get all the tBTYPS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tBTYPS in body.
     */
    @GetMapping("/tbtyps")
    public List<TBTYPS> getAllTBTYPS() {
        log.debug("REST request to get all TBTYPS");
        return tBTYPSRepository.findAll();
    }

    /**
     * {@code GET  /tbtyps/:id} : get the "id" tBTYPS.
     *
     * @param id the id of the tBTYPS to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tBTYPS, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tbtyps/{id}")
    public ResponseEntity<TBTYPS> getTBTYPS(@PathVariable String id) {
        log.debug("REST request to get TBTYPS : {}", id);
        Optional<TBTYPS> tBTYPS = tBTYPSRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tBTYPS);
    }

    /**
     * {@code DELETE  /tbtyps/:id} : delete the "id" tBTYPS.
     *
     * @param id the id of the tBTYPS to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tbtyps/{id}")
    public ResponseEntity<Void> deleteTBTYPS(@PathVariable String id) {
        log.debug("REST request to delete TBTYPS : {}", id);
        tBTYPSRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
