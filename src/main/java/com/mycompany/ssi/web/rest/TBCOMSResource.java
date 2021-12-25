package com.mycompany.ssi.web.rest;

import com.mycompany.ssi.domain.TBCOMS;
import com.mycompany.ssi.repository.TBCOMSRepository;
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
 * REST controller for managing {@link com.mycompany.ssi.domain.TBCOMS}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TBCOMSResource {

    private final Logger log = LoggerFactory.getLogger(TBCOMSResource.class);

    private static final String ENTITY_NAME = "tBCOMS";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TBCOMSRepository tBCOMSRepository;

    public TBCOMSResource(TBCOMSRepository tBCOMSRepository) {
        this.tBCOMSRepository = tBCOMSRepository;
    }

    /**
     * {@code POST  /tbcoms} : Create a new tBCOMS.
     *
     * @param tBCOMS the tBCOMS to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tBCOMS, or with status {@code 400 (Bad Request)} if the tBCOMS has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tbcoms")
    public ResponseEntity<TBCOMS> createTBCOMS(@Valid @RequestBody TBCOMS tBCOMS) throws URISyntaxException {
        log.debug("REST request to save TBCOMS : {}", tBCOMS);
        if (tBCOMS.getCocode() != null) {
            throw new BadRequestAlertException("A new tBCOMS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TBCOMS result = tBCOMSRepository.save(tBCOMS);
        return ResponseEntity
            .created(new URI("/api/tbcoms/" + result.getCocode()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCocode()))
            .body(result);
    }

    /**
     * {@code PUT  /tbcoms/:cocode} : Updates an existing tBCOMS.
     *
     * @param cocode the id of the tBCOMS to save.
     * @param tBCOMS the tBCOMS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBCOMS,
     * or with status {@code 400 (Bad Request)} if the tBCOMS is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tBCOMS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tbcoms/{cocode}")
    public ResponseEntity<TBCOMS> updateTBCOMS(
        @PathVariable(value = "cocode", required = false) final String cocode,
        @Valid @RequestBody TBCOMS tBCOMS
    ) throws URISyntaxException {
        log.debug("REST request to update TBCOMS : {}, {}", cocode, tBCOMS);
        if (tBCOMS.getCocode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(cocode, tBCOMS.getCocode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBCOMSRepository.existsById(cocode)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TBCOMS result = tBCOMSRepository.save(tBCOMS);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBCOMS.getCocode()))
            .body(result);
    }

    /**
     * {@code PATCH  /tbcoms/:cocode} : Partial updates given fields of an existing tBCOMS, field will ignore if it is null
     *
     * @param cocode the id of the tBCOMS to save.
     * @param tBCOMS the tBCOMS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBCOMS,
     * or with status {@code 400 (Bad Request)} if the tBCOMS is not valid,
     * or with status {@code 404 (Not Found)} if the tBCOMS is not found,
     * or with status {@code 500 (Internal Server Error)} if the tBCOMS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tbcoms/{cocode}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TBCOMS> partialUpdateTBCOMS(
        @PathVariable(value = "cocode", required = false) final String cocode,
        @NotNull @RequestBody TBCOMS tBCOMS
    ) throws URISyntaxException {
        log.debug("REST request to partial update TBCOMS partially : {}, {}", cocode, tBCOMS);
        if (tBCOMS.getCocode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(cocode, tBCOMS.getCocode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBCOMSRepository.existsById(cocode)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TBCOMS> result = tBCOMSRepository
            .findById(tBCOMS.getCocode())
            .map(existingTBCOMS -> {
                if (tBCOMS.getCosts() != null) {
                    existingTBCOMS.setCosts(tBCOMS.getCosts());
                }
                if (tBCOMS.getConam() != null) {
                    existingTBCOMS.setConam(tBCOMS.getConam());
                }
                if (tBCOMS.getCocbei() != null) {
                    existingTBCOMS.setCocbei(tBCOMS.getCocbei());
                }
                if (tBCOMS.getConbei() != null) {
                    existingTBCOMS.setConbei(tBCOMS.getConbei());
                }
                if (tBCOMS.getCosat() != null) {
                    existingTBCOMS.setCosat(tBCOMS.getCosat());
                }
                if (tBCOMS.getConom() != null) {
                    existingTBCOMS.setConom(tBCOMS.getConom());
                }
                if (tBCOMS.getCoisin() != null) {
                    existingTBCOMS.setCoisin(tBCOMS.getCoisin());
                }
                if (tBCOMS.getConpwp() != null) {
                    existingTBCOMS.setConpwp(tBCOMS.getConpwp());
                }
                if (tBCOMS.getCoseri() != null) {
                    existingTBCOMS.setCoseri(tBCOMS.getCoseri());
                }
                if (tBCOMS.getColshm() != null) {
                    existingTBCOMS.setColshm(tBCOMS.getColshm());
                }
                if (tBCOMS.getColsks() != null) {
                    existingTBCOMS.setColsks(tBCOMS.getColsks());
                }
                if (tBCOMS.getCotshm() != null) {
                    existingTBCOMS.setCotshm(tBCOMS.getCotshm());
                }
                if (tBCOMS.getCodshm() != null) {
                    existingTBCOMS.setCodshm(tBCOMS.getCodshm());
                }
                if (tBCOMS.getConote1() != null) {
                    existingTBCOMS.setConote1(tBCOMS.getConote1());
                }
                if (tBCOMS.getConote2() != null) {
                    existingTBCOMS.setConote2(tBCOMS.getConote2());
                }
                if (tBCOMS.getConote3() != null) {
                    existingTBCOMS.setConote3(tBCOMS.getConote3());
                }
                if (tBCOMS.getCoskps() != null) {
                    existingTBCOMS.setCoskps(tBCOMS.getCoskps());
                }
                if (tBCOMS.getCothld() != null) {
                    existingTBCOMS.setCothld(tBCOMS.getCothld());
                }
                if (tBCOMS.getCodir1() != null) {
                    existingTBCOMS.setCodir1(tBCOMS.getCodir1());
                }
                if (tBCOMS.getCodir2() != null) {
                    existingTBCOMS.setCodir2(tBCOMS.getCodir2());
                }
                if (tBCOMS.getCodir3() != null) {
                    existingTBCOMS.setCodir3(tBCOMS.getCodir3());
                }
                if (tBCOMS.getCodir4() != null) {
                    existingTBCOMS.setCodir4(tBCOMS.getCodir4());
                }
                if (tBCOMS.getCodir5() != null) {
                    existingTBCOMS.setCodir5(tBCOMS.getCodir5());
                }
                if (tBCOMS.getColmd() != null) {
                    existingTBCOMS.setColmd(tBCOMS.getColmd());
                }
                if (tBCOMS.getCouid() != null) {
                    existingTBCOMS.setCouid(tBCOMS.getCouid());
                }

                return existingTBCOMS;
            })
            .map(tBCOMSRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBCOMS.getCocode())
        );
    }

    /**
     * {@code GET  /tbcoms} : get all the tBCOMS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tBCOMS in body.
     */
    @GetMapping("/tbcoms")
    public List<TBCOMS> getAllTBCOMS() {
        log.debug("REST request to get all TBCOMS");
        return tBCOMSRepository.findAll();
    }

    /**
     * {@code GET  /tbcoms/:id} : get the "id" tBCOMS.
     *
     * @param id the id of the tBCOMS to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tBCOMS, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tbcoms/{id}")
    public ResponseEntity<TBCOMS> getTBCOMS(@PathVariable String id) {
        log.debug("REST request to get TBCOMS : {}", id);
        Optional<TBCOMS> tBCOMS = tBCOMSRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tBCOMS);
    }

    /**
     * {@code DELETE  /tbcoms/:id} : delete the "id" tBCOMS.
     *
     * @param id the id of the tBCOMS to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tbcoms/{id}")
    public ResponseEntity<Void> deleteTBCOMS(@PathVariable String id) {
        log.debug("REST request to delete TBCOMS : {}", id);
        tBCOMSRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
