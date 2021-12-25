package com.mycompany.ssi.web.rest;

import com.mycompany.ssi.domain.TBJNPS;
import com.mycompany.ssi.repository.TBJNPSRepository;
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
 * REST controller for managing {@link com.mycompany.ssi.domain.TBJNPS}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TBJNPSResource {

    private final Logger log = LoggerFactory.getLogger(TBJNPSResource.class);

    private static final String ENTITY_NAME = "tBJNPS";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TBJNPSRepository tBJNPSRepository;

    public TBJNPSResource(TBJNPSRepository tBJNPSRepository) {
        this.tBJNPSRepository = tBJNPSRepository;
    }

    /**
     * {@code POST  /tbjnps} : Create a new tBJNPS.
     *
     * @param tBJNPS the tBJNPS to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tBJNPS, or with status {@code 400 (Bad Request)} if the tBJNPS has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tbjnps")
    public ResponseEntity<TBJNPS> createTBJNPS(@Valid @RequestBody TBJNPS tBJNPS) throws URISyntaxException {
        log.debug("REST request to save TBJNPS : {}", tBJNPS);
        if (tBJNPS.getJpscod() != null) {
            throw new BadRequestAlertException("A new tBJNPS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TBJNPS result = tBJNPSRepository.save(tBJNPS);
        return ResponseEntity
            .created(new URI("/api/tbjnps/" + result.getJpscod()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getJpscod()))
            .body(result);
    }

    /**
     * {@code PUT  /tbjnps/:jpscod} : Updates an existing tBJNPS.
     *
     * @param jpscod the id of the tBJNPS to save.
     * @param tBJNPS the tBJNPS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBJNPS,
     * or with status {@code 400 (Bad Request)} if the tBJNPS is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tBJNPS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tbjnps/{jpscod}")
    public ResponseEntity<TBJNPS> updateTBJNPS(
        @PathVariable(value = "jpscod", required = false) final String jpscod,
        @Valid @RequestBody TBJNPS tBJNPS
    ) throws URISyntaxException {
        log.debug("REST request to update TBJNPS : {}, {}", jpscod, tBJNPS);
        if (tBJNPS.getJpscod() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(jpscod, tBJNPS.getJpscod())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBJNPSRepository.existsById(jpscod)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TBJNPS result = tBJNPSRepository.save(tBJNPS);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBJNPS.getJpscod()))
            .body(result);
    }

    /**
     * {@code PATCH  /tbjnps/:jpscod} : Partial updates given fields of an existing tBJNPS, field will ignore if it is null
     *
     * @param jpscod the id of the tBJNPS to save.
     * @param tBJNPS the tBJNPS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBJNPS,
     * or with status {@code 400 (Bad Request)} if the tBJNPS is not valid,
     * or with status {@code 404 (Not Found)} if the tBJNPS is not found,
     * or with status {@code 500 (Internal Server Error)} if the tBJNPS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tbjnps/{jpscod}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TBJNPS> partialUpdateTBJNPS(
        @PathVariable(value = "jpscod", required = false) final String jpscod,
        @NotNull @RequestBody TBJNPS tBJNPS
    ) throws URISyntaxException {
        log.debug("REST request to partial update TBJNPS partially : {}, {}", jpscod, tBJNPS);
        if (tBJNPS.getJpscod() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(jpscod, tBJNPS.getJpscod())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBJNPSRepository.existsById(jpscod)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TBJNPS> result = tBJNPSRepository
            .findById(tBJNPS.getJpscod())
            .map(existingTBJNPS -> {
                if (tBJNPS.getJpssts() != null) {
                    existingTBJNPS.setJpssts(tBJNPS.getJpssts());
                }
                if (tBJNPS.getJpsnam() != null) {
                    existingTBJNPS.setJpsnam(tBJNPS.getJpsnam());
                }
                if (tBJNPS.getJpslmd() != null) {
                    existingTBJNPS.setJpslmd(tBJNPS.getJpslmd());
                }
                if (tBJNPS.getJpsuid() != null) {
                    existingTBJNPS.setJpsuid(tBJNPS.getJpsuid());
                }

                return existingTBJNPS;
            })
            .map(tBJNPSRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBJNPS.getJpscod())
        );
    }

    /**
     * {@code GET  /tbjnps} : get all the tBJNPS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tBJNPS in body.
     */
    @GetMapping("/tbjnps")
    public List<TBJNPS> getAllTBJNPS() {
        log.debug("REST request to get all TBJNPS");
        return tBJNPSRepository.findAll();
    }

    /**
     * {@code GET  /tbjnps/:id} : get the "id" tBJNPS.
     *
     * @param id the id of the tBJNPS to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tBJNPS, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tbjnps/{id}")
    public ResponseEntity<TBJNPS> getTBJNPS(@PathVariable String id) {
        log.debug("REST request to get TBJNPS : {}", id);
        Optional<TBJNPS> tBJNPS = tBJNPSRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tBJNPS);
    }

    /**
     * {@code DELETE  /tbjnps/:id} : delete the "id" tBJNPS.
     *
     * @param id the id of the tBJNPS to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tbjnps/{id}")
    public ResponseEntity<Void> deleteTBJNPS(@PathVariable String id) {
        log.debug("REST request to delete TBJNPS : {}", id);
        tBJNPSRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
