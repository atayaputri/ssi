package com.mycompany.ssi.web.rest;

import com.mycompany.ssi.domain.TBHDR;
import com.mycompany.ssi.repository.TBHDRRepository;
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
 * REST controller for managing {@link com.mycompany.ssi.domain.TBHDR}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TBHDRResource {

    private final Logger log = LoggerFactory.getLogger(TBHDRResource.class);

    private static final String ENTITY_NAME = "tBHDR";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TBHDRRepository tBHDRRepository;

    public TBHDRResource(TBHDRRepository tBHDRRepository) {
        this.tBHDRRepository = tBHDRRepository;
    }

    /**
     * {@code POST  /tbhdrs} : Create a new tBHDR.
     *
     * @param tBHDR the tBHDR to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tBHDR, or with status {@code 400 (Bad Request)} if the tBHDR has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tbhdrs")
    public ResponseEntity<TBHDR> createTBHDR(@Valid @RequestBody TBHDR tBHDR) throws URISyntaxException {
        log.debug("REST request to save TBHDR : {}", tBHDR);
        if (tBHDR.getThno() != null) {
            throw new BadRequestAlertException("A new tBHDR cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TBHDR result = tBHDRRepository.save(tBHDR);
        return ResponseEntity
            .created(new URI("/api/tbhdrs/" + result.getThno()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getThno().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tbhdrs/:thno} : Updates an existing tBHDR.
     *
     * @param thno the id of the tBHDR to save.
     * @param tBHDR the tBHDR to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBHDR,
     * or with status {@code 400 (Bad Request)} if the tBHDR is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tBHDR couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tbhdrs/{thno}")
    public ResponseEntity<TBHDR> updateTBHDR(
        @PathVariable(value = "thno", required = false) final Long thno,
        @Valid @RequestBody TBHDR tBHDR
    ) throws URISyntaxException {
        log.debug("REST request to update TBHDR : {}, {}", thno, tBHDR);
        if (tBHDR.getThno() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(thno, tBHDR.getThno())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBHDRRepository.existsById(thno)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TBHDR result = tBHDRRepository.save(tBHDR);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBHDR.getThno().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tbhdrs/:thno} : Partial updates given fields of an existing tBHDR, field will ignore if it is null
     *
     * @param thno the id of the tBHDR to save.
     * @param tBHDR the tBHDR to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tBHDR,
     * or with status {@code 400 (Bad Request)} if the tBHDR is not valid,
     * or with status {@code 404 (Not Found)} if the tBHDR is not found,
     * or with status {@code 500 (Internal Server Error)} if the tBHDR couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tbhdrs/{thno}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TBHDR> partialUpdateTBHDR(
        @PathVariable(value = "thno", required = false) final Long thno,
        @NotNull @RequestBody TBHDR tBHDR
    ) throws URISyntaxException {
        log.debug("REST request to partial update TBHDR partially : {}, {}", thno, tBHDR);
        if (tBHDR.getThno() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(thno, tBHDR.getThno())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tBHDRRepository.existsById(thno)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TBHDR> result = tBHDRRepository
            .findById(tBHDR.getThno())
            .map(existingTBHDR -> {
                if (tBHDR.getThsts() != null) {
                    existingTBHDR.setThsts(tBHDR.getThsts());
                }
                if (tBHDR.getThsid() != null) {
                    existingTBHDR.setThsid(tBHDR.getThsid());
                }
                if (tBHDR.getThnm1() != null) {
                    existingTBHDR.setThnm1(tBHDR.getThnm1());
                }
                if (tBHDR.getThjsh() != null) {
                    existingTBHDR.setThjsh(tBHDR.getThjsh());
                }
                if (tBHDR.getThtax() != null) {
                    existingTBHDR.setThtax(tBHDR.getThtax());
                }
                if (tBHDR.getThdis() != null) {
                    existingTBHDR.setThdis(tBHDR.getThdis());
                }
                if (tBHDR.getThlmd() != null) {
                    existingTBHDR.setThlmd(tBHDR.getThlmd());
                }
                if (tBHDR.getThuid() != null) {
                    existingTBHDR.setThuid(tBHDR.getThuid());
                }
                if (tBHDR.getThfil1() != null) {
                    existingTBHDR.setThfil1(tBHDR.getThfil1());
                }
                if (tBHDR.getThfil2() != null) {
                    existingTBHDR.setThfil2(tBHDR.getThfil2());
                }
                if (tBHDR.getThfil3() != null) {
                    existingTBHDR.setThfil3(tBHDR.getThfil3());
                }
                if (tBHDR.getThfil4() != null) {
                    existingTBHDR.setThfil4(tBHDR.getThfil4());
                }

                return existingTBHDR;
            })
            .map(tBHDRRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tBHDR.getThno().toString())
        );
    }

    /**
     * {@code GET  /tbhdrs} : get all the tBHDRS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tBHDRS in body.
     */
    @GetMapping("/tbhdrs")
    public List<TBHDR> getAllTBHDRS() {
        log.debug("REST request to get all TBHDRS");
        return tBHDRRepository.findAll();
    }

    /**
     * {@code GET  /tbhdrs/:id} : get the "id" tBHDR.
     *
     * @param id the id of the tBHDR to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tBHDR, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tbhdrs/{id}")
    public ResponseEntity<TBHDR> getTBHDR(@PathVariable Long id) {
        log.debug("REST request to get TBHDR : {}", id);
        Optional<TBHDR> tBHDR = tBHDRRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tBHDR);
    }

    /**
     * {@code DELETE  /tbhdrs/:id} : delete the "id" tBHDR.
     *
     * @param id the id of the tBHDR to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tbhdrs/{id}")
    public ResponseEntity<Void> deleteTBHDR(@PathVariable Long id) {
        log.debug("REST request to delete TBHDR : {}", id);
        tBHDRRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
