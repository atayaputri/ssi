package com.mycompany.ssi.web.rest;

import com.mycompany.ssi.domain.TABFEE;
import com.mycompany.ssi.repository.TABFEERepository;
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
 * REST controller for managing {@link com.mycompany.ssi.domain.TABFEE}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TABFEEResource {

    private final Logger log = LoggerFactory.getLogger(TABFEEResource.class);

    private static final String ENTITY_NAME = "tABFEE";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TABFEERepository tABFEERepository;

    public TABFEEResource(TABFEERepository tABFEERepository) {
        this.tABFEERepository = tABFEERepository;
    }

    /**
     * {@code POST  /tabfees} : Create a new tABFEE.
     *
     * @param tABFEE the tABFEE to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tABFEE, or with status {@code 400 (Bad Request)} if the tABFEE has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tabfees")
    public ResponseEntity<TABFEE> createTABFEE(@Valid @RequestBody TABFEE tABFEE) throws URISyntaxException {
        log.debug("REST request to save TABFEE : {}", tABFEE);
        if (tABFEE.getId() != null) {
            throw new BadRequestAlertException("A new tABFEE cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TABFEE result = tABFEERepository.save(tABFEE);
        return ResponseEntity
            .created(new URI("/api/tabfees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tabfees/:id} : Updates an existing tABFEE.
     *
     * @param id the id of the tABFEE to save.
     * @param tABFEE the tABFEE to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tABFEE,
     * or with status {@code 400 (Bad Request)} if the tABFEE is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tABFEE couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tabfees/{id}")
    public ResponseEntity<TABFEE> updateTABFEE(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TABFEE tABFEE
    ) throws URISyntaxException {
        log.debug("REST request to update TABFEE : {}, {}", id, tABFEE);
        if (tABFEE.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tABFEE.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tABFEERepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TABFEE result = tABFEERepository.save(tABFEE);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tABFEE.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tabfees/:id} : Partial updates given fields of an existing tABFEE, field will ignore if it is null
     *
     * @param id the id of the tABFEE to save.
     * @param tABFEE the tABFEE to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tABFEE,
     * or with status {@code 400 (Bad Request)} if the tABFEE is not valid,
     * or with status {@code 404 (Not Found)} if the tABFEE is not found,
     * or with status {@code 500 (Internal Server Error)} if the tABFEE couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tabfees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TABFEE> partialUpdateTABFEE(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TABFEE tABFEE
    ) throws URISyntaxException {
        log.debug("REST request to partial update TABFEE partially : {}, {}", id, tABFEE);
        if (tABFEE.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tABFEE.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tABFEERepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TABFEE> result = tABFEERepository
            .findById(tABFEE.getId())
            .map(existingTABFEE -> {
                if (tABFEE.getFests() != null) {
                    existingTABFEE.setFests(tABFEE.getFests());
                }
                if (tABFEE.getFeemt() != null) {
                    existingTABFEE.setFeemt(tABFEE.getFeemt());
                }
                if (tABFEE.getFemin() != null) {
                    existingTABFEE.setFemin(tABFEE.getFemin());
                }
                if (tABFEE.getFemax() != null) {
                    existingTABFEE.setFemax(tABFEE.getFemax());
                }
                if (tABFEE.getFefee() != null) {
                    existingTABFEE.setFefee(tABFEE.getFefee());
                }
                if (tABFEE.getFediscp() != null) {
                    existingTABFEE.setFediscp(tABFEE.getFediscp());
                }
                if (tABFEE.getFedisc() != null) {
                    existingTABFEE.setFedisc(tABFEE.getFedisc());
                }
                if (tABFEE.getFetax() != null) {
                    existingTABFEE.setFetax(tABFEE.getFetax());
                }
                if (tABFEE.getFelmd() != null) {
                    existingTABFEE.setFelmd(tABFEE.getFelmd());
                }
                if (tABFEE.getFeuid() != null) {
                    existingTABFEE.setFeuid(tABFEE.getFeuid());
                }

                return existingTABFEE;
            })
            .map(tABFEERepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tABFEE.getId().toString())
        );
    }

    /**
     * {@code GET  /tabfees} : get all the tABFEES.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tABFEES in body.
     */
    @GetMapping("/tabfees")
    public List<TABFEE> getAllTABFEES() {
        log.debug("REST request to get all TABFEES");
        return tABFEERepository.findAll();
    }

    /**
     * {@code GET  /tabfees/:id} : get the "id" tABFEE.
     *
     * @param id the id of the tABFEE to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tABFEE, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tabfees/{id}")
    public ResponseEntity<TABFEE> getTABFEE(@PathVariable Long id) {
        log.debug("REST request to get TABFEE : {}", id);
        Optional<TABFEE> tABFEE = tABFEERepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tABFEE);
    }

    /**
     * {@code DELETE  /tabfees/:id} : delete the "id" tABFEE.
     *
     * @param id the id of the tABFEE to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tabfees/{id}")
    public ResponseEntity<Void> deleteTABFEE(@PathVariable Long id) {
        log.debug("REST request to delete TABFEE : {}", id);
        tABFEERepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
