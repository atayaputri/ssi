package com.mycompany.ssi.web.rest;

import com.mycompany.ssi.domain.MAPSKS;
import com.mycompany.ssi.repository.MAPSKSRepository;
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
 * REST controller for managing {@link com.mycompany.ssi.domain.MAPSKS}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MAPSKSResource {

    private final Logger log = LoggerFactory.getLogger(MAPSKSResource.class);

    private static final String ENTITY_NAME = "mAPSKS";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MAPSKSRepository mAPSKSRepository;

    public MAPSKSResource(MAPSKSRepository mAPSKSRepository) {
        this.mAPSKSRepository = mAPSKSRepository;
    }

    /**
     * {@code POST  /mapsks} : Create a new mAPSKS.
     *
     * @param mAPSKS the mAPSKS to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mAPSKS, or with status {@code 400 (Bad Request)} if the mAPSKS has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mapsks")
    public ResponseEntity<MAPSKS> createMAPSKS(@Valid @RequestBody MAPSKS mAPSKS) throws URISyntaxException {
        log.debug("REST request to save MAPSKS : {}", mAPSKS);
        if (mAPSKS.getId() != null) {
            throw new BadRequestAlertException("A new mAPSKS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MAPSKS result = mAPSKSRepository.save(mAPSKS);
        return ResponseEntity
            .created(new URI("/api/mapsks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mapsks/:id} : Updates an existing mAPSKS.
     *
     * @param id the id of the mAPSKS to save.
     * @param mAPSKS the mAPSKS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mAPSKS,
     * or with status {@code 400 (Bad Request)} if the mAPSKS is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mAPSKS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mapsks/{id}")
    public ResponseEntity<MAPSKS> updateMAPSKS(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MAPSKS mAPSKS
    ) throws URISyntaxException {
        log.debug("REST request to update MAPSKS : {}, {}", id, mAPSKS);
        if (mAPSKS.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mAPSKS.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mAPSKSRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MAPSKS result = mAPSKSRepository.save(mAPSKS);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mAPSKS.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mapsks/:id} : Partial updates given fields of an existing mAPSKS, field will ignore if it is null
     *
     * @param id the id of the mAPSKS to save.
     * @param mAPSKS the mAPSKS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mAPSKS,
     * or with status {@code 400 (Bad Request)} if the mAPSKS is not valid,
     * or with status {@code 404 (Not Found)} if the mAPSKS is not found,
     * or with status {@code 500 (Internal Server Error)} if the mAPSKS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mapsks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MAPSKS> partialUpdateMAPSKS(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MAPSKS mAPSKS
    ) throws URISyntaxException {
        log.debug("REST request to partial update MAPSKS partially : {}, {}", id, mAPSKS);
        if (mAPSKS.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mAPSKS.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mAPSKSRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MAPSKS> result = mAPSKSRepository
            .findById(mAPSKS.getId())
            .map(existingMAPSKS -> {
                if (mAPSKS.getMsksts() != null) {
                    existingMAPSKS.setMsksts(mAPSKS.getMsksts());
                }

                return existingMAPSKS;
            })
            .map(mAPSKSRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mAPSKS.getId().toString())
        );
    }

    /**
     * {@code GET  /mapsks} : get all the mAPSKS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mAPSKS in body.
     */
    @GetMapping("/mapsks")
    public List<MAPSKS> getAllMAPSKS() {
        log.debug("REST request to get all MAPSKS");
        return mAPSKSRepository.findAll();
    }

    /**
     * {@code GET  /mapsks/:id} : get the "id" mAPSKS.
     *
     * @param id the id of the mAPSKS to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mAPSKS, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mapsks/{id}")
    public ResponseEntity<MAPSKS> getMAPSKS(@PathVariable Long id) {
        log.debug("REST request to get MAPSKS : {}", id);
        Optional<MAPSKS> mAPSKS = mAPSKSRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mAPSKS);
    }

    /**
     * {@code DELETE  /mapsks/:id} : delete the "id" mAPSKS.
     *
     * @param id the id of the mAPSKS to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mapsks/{id}")
    public ResponseEntity<Void> deleteMAPSKS(@PathVariable Long id) {
        log.debug("REST request to delete MAPSKS : {}", id);
        mAPSKSRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
