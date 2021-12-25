package com.mycompany.ssi.web.rest;

import com.mycompany.ssi.domain.LISTEMT;
import com.mycompany.ssi.repository.LISTEMTRepository;
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
 * REST controller for managing {@link com.mycompany.ssi.domain.LISTEMT}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LISTEMTResource {

    private final Logger log = LoggerFactory.getLogger(LISTEMTResource.class);

    private static final String ENTITY_NAME = "lISTEMT";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LISTEMTRepository lISTEMTRepository;

    public LISTEMTResource(LISTEMTRepository lISTEMTRepository) {
        this.lISTEMTRepository = lISTEMTRepository;
    }

    /**
     * {@code POST  /listemts} : Create a new lISTEMT.
     *
     * @param lISTEMT the lISTEMT to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lISTEMT, or with status {@code 400 (Bad Request)} if the lISTEMT has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/listemts")
    public ResponseEntity<LISTEMT> createLISTEMT(@Valid @RequestBody LISTEMT lISTEMT) throws URISyntaxException {
        log.debug("REST request to save LISTEMT : {}", lISTEMT);
        if (lISTEMT.getId() != null) {
            throw new BadRequestAlertException("A new lISTEMT cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LISTEMT result = lISTEMTRepository.save(lISTEMT);
        return ResponseEntity
            .created(new URI("/api/listemts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /listemts/:id} : Updates an existing lISTEMT.
     *
     * @param id the id of the lISTEMT to save.
     * @param lISTEMT the lISTEMT to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lISTEMT,
     * or with status {@code 400 (Bad Request)} if the lISTEMT is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lISTEMT couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/listemts/{id}")
    public ResponseEntity<LISTEMT> updateLISTEMT(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LISTEMT lISTEMT
    ) throws URISyntaxException {
        log.debug("REST request to update LISTEMT : {}, {}", id, lISTEMT);
        if (lISTEMT.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lISTEMT.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lISTEMTRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LISTEMT result = lISTEMTRepository.save(lISTEMT);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lISTEMT.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /listemts/:id} : Partial updates given fields of an existing lISTEMT, field will ignore if it is null
     *
     * @param id the id of the lISTEMT to save.
     * @param lISTEMT the lISTEMT to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lISTEMT,
     * or with status {@code 400 (Bad Request)} if the lISTEMT is not valid,
     * or with status {@code 404 (Not Found)} if the lISTEMT is not found,
     * or with status {@code 500 (Internal Server Error)} if the lISTEMT couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/listemts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LISTEMT> partialUpdateLISTEMT(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LISTEMT lISTEMT
    ) throws URISyntaxException {
        log.debug("REST request to partial update LISTEMT partially : {}, {}", id, lISTEMT);
        if (lISTEMT.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lISTEMT.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lISTEMTRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LISTEMT> result = lISTEMTRepository
            .findById(lISTEMT.getId())
            .map(existingLISTEMT -> {
                if (lISTEMT.getLiscode() != null) {
                    existingLISTEMT.setLiscode(lISTEMT.getLiscode());
                }
                if (lISTEMT.getLisnam() != null) {
                    existingLISTEMT.setLisnam(lISTEMT.getLisnam());
                }
                if (lISTEMT.getLisdir() != null) {
                    existingLISTEMT.setLisdir(lISTEMT.getLisdir());
                }

                return existingLISTEMT;
            })
            .map(lISTEMTRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lISTEMT.getId().toString())
        );
    }

    /**
     * {@code GET  /listemts} : get all the lISTEMTS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lISTEMTS in body.
     */
    @GetMapping("/listemts")
    public List<LISTEMT> getAllLISTEMTS() {
        log.debug("REST request to get all LISTEMTS");
        return lISTEMTRepository.findAll();
    }

    /**
     * {@code GET  /listemts/:id} : get the "id" lISTEMT.
     *
     * @param id the id of the lISTEMT to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lISTEMT, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/listemts/{id}")
    public ResponseEntity<LISTEMT> getLISTEMT(@PathVariable Long id) {
        log.debug("REST request to get LISTEMT : {}", id);
        Optional<LISTEMT> lISTEMT = lISTEMTRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lISTEMT);
    }

    /**
     * {@code DELETE  /listemts/:id} : delete the "id" lISTEMT.
     *
     * @param id the id of the lISTEMT to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/listemts/{id}")
    public ResponseEntity<Void> deleteLISTEMT(@PathVariable Long id) {
        log.debug("REST request to delete LISTEMT : {}", id);
        lISTEMTRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
