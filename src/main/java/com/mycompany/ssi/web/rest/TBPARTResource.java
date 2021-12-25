package com.mycompany.ssi.web.rest;

import com.mycompany.ssi.domain.TBPART;
import com.mycompany.ssi.repository.TBPARTRepository;
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
 * REST controller for managing {@link com.mycompany.ssi.domain.TBPART}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TBPARTResource {

    private final Logger log = LoggerFactory.getLogger(TBPARTResource.class);

    private static final String ENTITY_NAME = "tBPART";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TBPARTRepository tBPARTRepository;

    public TBPARTResource(TBPARTRepository tBPARTRepository) {
        this.tBPARTRepository = tBPARTRepository;
    }

    /**
     * {@code POST  /tbparts} : Create a new tBPART.
     *
     * @param tBPART the tBPART to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tBPART, or with status {@code 400 (Bad Request)} if the tBPART has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tbparts")
    public ResponseEntity<TBPART> createTBPART(@Valid @RequestBody TBPART tBPART) throws URISyntaxException {
        log.debug("REST request to save TBPART : {}", tBPART);
        if (tBPART.getTpacode() != null) {
            throw new BadRequestAlertException("A new tBPART cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TBPART result = tBPARTRepository.save(tBPART);
        return ResponseEntity
            .created(new URI("/api/tbparts/" + result.getTpacode()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getTpacode()))
            .body(result);
    }

    /**
     * {@code PUT  /tbparts/:tpacode} : Updates an existing tBPART.
     *
     * @param tpacode the id of the tBPART to save.
     * @param tBPART the tBPART to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBPART,
     * or with status {@code 400 (Bad Request)} if the tBPART is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tBPART couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tbparts/{tpacode}")
    public ResponseEntity<TBPART> updateTBPART(
        @PathVariable(value = "tpacode", required = false) final String tpacode,
        @Valid @RequestBody TBPART tBPART
    ) throws URISyntaxException {
        log.debug("REST request to update TBPART : {}, {}", tpacode, tBPART);
        if (tBPART.getTpacode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(tpacode, tBPART.getTpacode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBPARTRepository.existsById(tpacode)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TBPART result = tBPARTRepository.save(tBPART);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBPART.getTpacode()))
            .body(result);
    }

    /**
     * {@code PATCH  /tbparts/:tpacode} : Partial updates given fields of an existing tBPART, field will ignore if it is null
     *
     * @param tpacode the id of the tBPART to save.
     * @param tBPART the tBPART to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBPART,
     * or with status {@code 400 (Bad Request)} if the tBPART is not valid,
     * or with status {@code 404 (Not Found)} if the tBPART is not found,
     * or with status {@code 500 (Internal Server Error)} if the tBPART couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tbparts/{tpacode}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TBPART> partialUpdateTBPART(
        @PathVariable(value = "tpacode", required = false) final String tpacode,
        @NotNull @RequestBody TBPART tBPART
    ) throws URISyntaxException {
        log.debug("REST request to partial update TBPART partially : {}, {}", tpacode, tBPART);
        if (tBPART.getTpacode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(tpacode, tBPART.getTpacode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBPARTRepository.existsById(tpacode)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TBPART> result = tBPARTRepository
            .findById(tBPART.getTpacode())
            .map(existingTBPART -> {
                if (tBPART.getTpasts() != null) {
                    existingTBPART.setTpasts(tBPART.getTpasts());
                }
                if (tBPART.getTpanam() != null) {
                    existingTBPART.setTpanam(tBPART.getTpanam());
                }
                if (tBPART.getTparek() != null) {
                    existingTBPART.setTparek(tBPART.getTparek());
                }
                if (tBPART.getTpadis() != null) {
                    existingTBPART.setTpadis(tBPART.getTpadis());
                }
                if (tBPART.getTpalmd() != null) {
                    existingTBPART.setTpalmd(tBPART.getTpalmd());
                }
                if (tBPART.getTpauid() != null) {
                    existingTBPART.setTpauid(tBPART.getTpauid());
                }

                return existingTBPART;
            })
            .map(tBPARTRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBPART.getTpacode())
        );
    }

    /**
     * {@code GET  /tbparts} : get all the tBPARTS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tBPARTS in body.
     */
    @GetMapping("/tbparts")
    public List<TBPART> getAllTBPARTS() {
        log.debug("REST request to get all TBPARTS");
        return tBPARTRepository.findAll();
    }

    /**
     * {@code GET  /tbparts/:id} : get the "id" tBPART.
     *
     * @param id the id of the tBPART to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tBPART, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tbparts/{id}")
    public ResponseEntity<TBPART> getTBPART(@PathVariable String id) {
        log.debug("REST request to get TBPART : {}", id);
        Optional<TBPART> tBPART = tBPARTRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tBPART);
    }

    /**
     * {@code DELETE  /tbparts/:id} : delete the "id" tBPART.
     *
     * @param id the id of the tBPART to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tbparts/{id}")
    public ResponseEntity<Void> deleteTBPART(@PathVariable String id) {
        log.debug("REST request to delete TBPART : {}", id);
        tBPARTRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
