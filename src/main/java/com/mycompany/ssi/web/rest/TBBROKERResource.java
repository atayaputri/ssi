package com.mycompany.ssi.web.rest;

import com.mycompany.ssi.domain.TBBROKER;
import com.mycompany.ssi.repository.TBBROKERRepository;
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
 * REST controller for managing {@link com.mycompany.ssi.domain.TBBROKER}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TBBROKERResource {

    private final Logger log = LoggerFactory.getLogger(TBBROKERResource.class);

    private static final String ENTITY_NAME = "tBBROKER";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TBBROKERRepository tBBROKERRepository;

    public TBBROKERResource(TBBROKERRepository tBBROKERRepository) {
        this.tBBROKERRepository = tBBROKERRepository;
    }

    /**
     * {@code POST  /tbbrokers} : Create a new tBBROKER.
     *
     * @param tBBROKER the tBBROKER to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tBBROKER, or with status {@code 400 (Bad Request)} if the tBBROKER has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tbbrokers")
    public ResponseEntity<TBBROKER> createTBBROKER(@Valid @RequestBody TBBROKER tBBROKER) throws URISyntaxException {
        log.debug("REST request to save TBBROKER : {}", tBBROKER);
        if (tBBROKER.getBrcode() != null) {
            throw new BadRequestAlertException("A new tBBROKER cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TBBROKER result = tBBROKERRepository.save(tBBROKER);
        return ResponseEntity
            .created(new URI("/api/tbbrokers/" + result.getBrcode()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getBrcode()))
            .body(result);
    }

    /**
     * {@code PUT  /tbbrokers/:brcode} : Updates an existing tBBROKER.
     *
     * @param brcode the id of the tBBROKER to save.
     * @param tBBROKER the tBBROKER to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBBROKER,
     * or with status {@code 400 (Bad Request)} if the tBBROKER is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tBBROKER couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tbbrokers/{brcode}")
    public ResponseEntity<TBBROKER> updateTBBROKER(
        @PathVariable(value = "brcode", required = false) final String brcode,
        @Valid @RequestBody TBBROKER tBBROKER
    ) throws URISyntaxException {
        log.debug("REST request to update TBBROKER : {}, {}", brcode, tBBROKER);
        if (tBBROKER.getBrcode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(brcode, tBBROKER.getBrcode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBBROKERRepository.existsById(brcode)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TBBROKER result = tBBROKERRepository.save(tBBROKER);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBBROKER.getBrcode()))
            .body(result);
    }

    /**
     * {@code PATCH  /tbbrokers/:brcode} : Partial updates given fields of an existing tBBROKER, field will ignore if it is null
     *
     * @param brcode the id of the tBBROKER to save.
     * @param tBBROKER the tBBROKER to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBBROKER,
     * or with status {@code 400 (Bad Request)} if the tBBROKER is not valid,
     * or with status {@code 404 (Not Found)} if the tBBROKER is not found,
     * or with status {@code 500 (Internal Server Error)} if the tBBROKER couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tbbrokers/{brcode}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TBBROKER> partialUpdateTBBROKER(
        @PathVariable(value = "brcode", required = false) final String brcode,
        @NotNull @RequestBody TBBROKER tBBROKER
    ) throws URISyntaxException {
        log.debug("REST request to partial update TBBROKER partially : {}, {}", brcode, tBBROKER);
        if (tBBROKER.getBrcode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(brcode, tBBROKER.getBrcode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBBROKERRepository.existsById(brcode)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TBBROKER> result = tBBROKERRepository
            .findById(tBBROKER.getBrcode())
            .map(existingTBBROKER -> {
                if (tBBROKER.getBrsts() != null) {
                    existingTBBROKER.setBrsts(tBBROKER.getBrsts());
                }
                if (tBBROKER.getBrnam() != null) {
                    existingTBBROKER.setBrnam(tBBROKER.getBrnam());
                }
                if (tBBROKER.getBrlmd() != null) {
                    existingTBBROKER.setBrlmd(tBBROKER.getBrlmd());
                }
                if (tBBROKER.getBruid() != null) {
                    existingTBBROKER.setBruid(tBBROKER.getBruid());
                }

                return existingTBBROKER;
            })
            .map(tBBROKERRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBBROKER.getBrcode())
        );
    }

    /**
     * {@code GET  /tbbrokers} : get all the tBBROKERS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tBBROKERS in body.
     */
    @GetMapping("/tbbrokers")
    public List<TBBROKER> getAllTBBROKERS() {
        log.debug("REST request to get all TBBROKERS");
        return tBBROKERRepository.findAll();
    }

    /**
     * {@code GET  /tbbrokers/:id} : get the "id" tBBROKER.
     *
     * @param id the id of the tBBROKER to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tBBROKER, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tbbrokers/{id}")
    public ResponseEntity<TBBROKER> getTBBROKER(@PathVariable String id) {
        log.debug("REST request to get TBBROKER : {}", id);
        Optional<TBBROKER> tBBROKER = tBBROKERRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tBBROKER);
    }

    /**
     * {@code DELETE  /tbbrokers/:id} : delete the "id" tBBROKER.
     *
     * @param id the id of the tBBROKER to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tbbrokers/{id}")
    public ResponseEntity<Void> deleteTBBROKER(@PathVariable String id) {
        log.debug("REST request to delete TBBROKER : {}", id);
        tBBROKERRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
