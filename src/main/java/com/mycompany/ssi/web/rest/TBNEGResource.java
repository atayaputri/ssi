package com.mycompany.ssi.web.rest;

import com.mycompany.ssi.domain.TBNEG;
import com.mycompany.ssi.repository.TBNEGRepository;
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
 * REST controller for managing {@link com.mycompany.ssi.domain.TBNEG}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TBNEGResource {

    private final Logger log = LoggerFactory.getLogger(TBNEGResource.class);

    private static final String ENTITY_NAME = "tBNEG";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TBNEGRepository tBNEGRepository;

    public TBNEGResource(TBNEGRepository tBNEGRepository) {
        this.tBNEGRepository = tBNEGRepository;
    }

    /**
     * {@code POST  /tbnegs} : Create a new tBNEG.
     *
     * @param tBNEG the tBNEG to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tBNEG, or with status {@code 400 (Bad Request)} if the tBNEG has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tbnegs")
    public ResponseEntity<TBNEG> createTBNEG(@Valid @RequestBody TBNEG tBNEG) throws URISyntaxException {
        log.debug("REST request to save TBNEG : {}", tBNEG);
        if (tBNEG.getNegcod() != null) {
            throw new BadRequestAlertException("A new tBNEG cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TBNEG result = tBNEGRepository.save(tBNEG);
        return ResponseEntity
            .created(new URI("/api/tbnegs/" + result.getNegcod()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getNegcod()))
            .body(result);
    }

    /**
     * {@code PUT  /tbnegs/:negcod} : Updates an existing tBNEG.
     *
     * @param negcod the id of the tBNEG to save.
     * @param tBNEG the tBNEG to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBNEG,
     * or with status {@code 400 (Bad Request)} if the tBNEG is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tBNEG couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tbnegs/{negcod}")
    public ResponseEntity<TBNEG> updateTBNEG(
        @PathVariable(value = "negcod", required = false) final String negcod,
        @Valid @RequestBody TBNEG tBNEG
    ) throws URISyntaxException {
        log.debug("REST request to update TBNEG : {}, {}", negcod, tBNEG);
        if (tBNEG.getNegcod() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(negcod, tBNEG.getNegcod())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBNEGRepository.existsById(negcod)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TBNEG result = tBNEGRepository.save(tBNEG);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBNEG.getNegcod()))
            .body(result);
    }

    /**
     * {@code PATCH  /tbnegs/:negcod} : Partial updates given fields of an existing tBNEG, field will ignore if it is null
     *
     * @param negcod the id of the tBNEG to save.
     * @param tBNEG the tBNEG to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBNEG,
     * or with status {@code 400 (Bad Request)} if the tBNEG is not valid,
     * or with status {@code 404 (Not Found)} if the tBNEG is not found,
     * or with status {@code 500 (Internal Server Error)} if the tBNEG couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tbnegs/{negcod}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TBNEG> partialUpdateTBNEG(
        @PathVariable(value = "negcod", required = false) final String negcod,
        @NotNull @RequestBody TBNEG tBNEG
    ) throws URISyntaxException {
        log.debug("REST request to partial update TBNEG partially : {}, {}", negcod, tBNEG);
        if (tBNEG.getNegcod() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(negcod, tBNEG.getNegcod())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBNEGRepository.existsById(negcod)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TBNEG> result = tBNEGRepository
            .findById(tBNEG.getNegcod())
            .map(existingTBNEG -> {
                if (tBNEG.getNegsts() != null) {
                    existingTBNEG.setNegsts(tBNEG.getNegsts());
                }
                if (tBNEG.getNegnam() != null) {
                    existingTBNEG.setNegnam(tBNEG.getNegnam());
                }
                if (tBNEG.getNegtax() != null) {
                    existingTBNEG.setNegtax(tBNEG.getNegtax());
                }
                if (tBNEG.getNeglmd() != null) {
                    existingTBNEG.setNeglmd(tBNEG.getNeglmd());
                }
                if (tBNEG.getNeguid() != null) {
                    existingTBNEG.setNeguid(tBNEG.getNeguid());
                }

                return existingTBNEG;
            })
            .map(tBNEGRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBNEG.getNegcod())
        );
    }

    /**
     * {@code GET  /tbnegs} : get all the tBNEGS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tBNEGS in body.
     */
    @GetMapping("/tbnegs")
    public List<TBNEG> getAllTBNEGS() {
        log.debug("REST request to get all TBNEGS");
        return tBNEGRepository.findAll();
    }

    /**
     * {@code GET  /tbnegs/:id} : get the "id" tBNEG.
     *
     * @param id the id of the tBNEG to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tBNEG, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tbnegs/{id}")
    public ResponseEntity<TBNEG> getTBNEG(@PathVariable String id) {
        log.debug("REST request to get TBNEG : {}", id);
        Optional<TBNEG> tBNEG = tBNEGRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tBNEG);
    }

    /**
     * {@code DELETE  /tbnegs/:id} : delete the "id" tBNEG.
     *
     * @param id the id of the tBNEG to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tbnegs/{id}")
    public ResponseEntity<Void> deleteTBNEG(@PathVariable String id) {
        log.debug("REST request to delete TBNEG : {}", id);
        tBNEGRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
