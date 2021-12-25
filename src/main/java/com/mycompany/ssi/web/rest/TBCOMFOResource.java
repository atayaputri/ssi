package com.mycompany.ssi.web.rest;

import com.mycompany.ssi.domain.TBCOMFO;
import com.mycompany.ssi.repository.TBCOMFORepository;
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
 * REST controller for managing {@link com.mycompany.ssi.domain.TBCOMFO}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TBCOMFOResource {

    private final Logger log = LoggerFactory.getLogger(TBCOMFOResource.class);

    private static final String ENTITY_NAME = "tBCOMFO";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TBCOMFORepository tBCOMFORepository;

    public TBCOMFOResource(TBCOMFORepository tBCOMFORepository) {
        this.tBCOMFORepository = tBCOMFORepository;
    }

    /**
     * {@code POST  /tbcomfos} : Create a new tBCOMFO.
     *
     * @param tBCOMFO the tBCOMFO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tBCOMFO, or with status {@code 400 (Bad Request)} if the tBCOMFO has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tbcomfos")
    public ResponseEntity<TBCOMFO> createTBCOMFO(@Valid @RequestBody TBCOMFO tBCOMFO) throws URISyntaxException {
        log.debug("REST request to save TBCOMFO : {}", tBCOMFO);
        if (tBCOMFO.getId() != null) {
            throw new BadRequestAlertException("A new tBCOMFO cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TBCOMFO result = tBCOMFORepository.save(tBCOMFO);
        return ResponseEntity
            .created(new URI("/api/tbcomfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tbcomfos/:id} : Updates an existing tBCOMFO.
     *
     * @param id the id of the tBCOMFO to save.
     * @param tBCOMFO the tBCOMFO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBCOMFO,
     * or with status {@code 400 (Bad Request)} if the tBCOMFO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tBCOMFO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tbcomfos/{id}")
    public ResponseEntity<TBCOMFO> updateTBCOMFO(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TBCOMFO tBCOMFO
    ) throws URISyntaxException {
        log.debug("REST request to update TBCOMFO : {}, {}", id, tBCOMFO);
        if (tBCOMFO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tBCOMFO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBCOMFORepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TBCOMFO result = tBCOMFORepository.save(tBCOMFO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBCOMFO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tbcomfos/:id} : Partial updates given fields of an existing tBCOMFO, field will ignore if it is null
     *
     * @param id the id of the tBCOMFO to save.
     * @param tBCOMFO the tBCOMFO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBCOMFO,
     * or with status {@code 400 (Bad Request)} if the tBCOMFO is not valid,
     * or with status {@code 404 (Not Found)} if the tBCOMFO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tBCOMFO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tbcomfos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TBCOMFO> partialUpdateTBCOMFO(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TBCOMFO tBCOMFO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TBCOMFO partially : {}, {}", id, tBCOMFO);
        if (tBCOMFO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tBCOMFO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBCOMFORepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TBCOMFO> result = tBCOMFORepository
            .findById(tBCOMFO.getId())
            .map(existingTBCOMFO -> {
                if (tBCOMFO.getCosts() != null) {
                    existingTBCOMFO.setCosts(tBCOMFO.getCosts());
                }
                if (tBCOMFO.getCocode() != null) {
                    existingTBCOMFO.setCocode(tBCOMFO.getCocode());
                }
                if (tBCOMFO.getConam() != null) {
                    existingTBCOMFO.setConam(tBCOMFO.getConam());
                }
                if (tBCOMFO.getCocbei() != null) {
                    existingTBCOMFO.setCocbei(tBCOMFO.getCocbei());
                }
                if (tBCOMFO.getConbei() != null) {
                    existingTBCOMFO.setConbei(tBCOMFO.getConbei());
                }
                if (tBCOMFO.getCosat() != null) {
                    existingTBCOMFO.setCosat(tBCOMFO.getCosat());
                }
                if (tBCOMFO.getConom() != null) {
                    existingTBCOMFO.setConom(tBCOMFO.getConom());
                }
                if (tBCOMFO.getCoseri() != null) {
                    existingTBCOMFO.setCoseri(tBCOMFO.getCoseri());
                }
                if (tBCOMFO.getCodir() != null) {
                    existingTBCOMFO.setCodir(tBCOMFO.getCodir());
                }
                if (tBCOMFO.getColmd() != null) {
                    existingTBCOMFO.setColmd(tBCOMFO.getColmd());
                }
                if (tBCOMFO.getCouid() != null) {
                    existingTBCOMFO.setCouid(tBCOMFO.getCouid());
                }

                return existingTBCOMFO;
            })
            .map(tBCOMFORepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBCOMFO.getId().toString())
        );
    }

    /**
     * {@code GET  /tbcomfos} : get all the tBCOMFOS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tBCOMFOS in body.
     */
    @GetMapping("/tbcomfos")
    public List<TBCOMFO> getAllTBCOMFOS() {
        log.debug("REST request to get all TBCOMFOS");
        return tBCOMFORepository.findAll();
    }

    /**
     * {@code GET  /tbcomfos/:id} : get the "id" tBCOMFO.
     *
     * @param id the id of the tBCOMFO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tBCOMFO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tbcomfos/{id}")
    public ResponseEntity<TBCOMFO> getTBCOMFO(@PathVariable Long id) {
        log.debug("REST request to get TBCOMFO : {}", id);
        Optional<TBCOMFO> tBCOMFO = tBCOMFORepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tBCOMFO);
    }

    /**
     * {@code DELETE  /tbcomfos/:id} : delete the "id" tBCOMFO.
     *
     * @param id the id of the tBCOMFO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tbcomfos/{id}")
    public ResponseEntity<Void> deleteTBCOMFO(@PathVariable Long id) {
        log.debug("REST request to delete TBCOMFO : {}", id);
        tBCOMFORepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
