package com.mycompany.ssi.web.rest;

import com.mycompany.ssi.domain.TABJTRX;
import com.mycompany.ssi.repository.TABJTRXRepository;
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
 * REST controller for managing {@link com.mycompany.ssi.domain.TABJTRX}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TABJTRXResource {

    private final Logger log = LoggerFactory.getLogger(TABJTRXResource.class);

    private static final String ENTITY_NAME = "tABJTRX";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TABJTRXRepository tABJTRXRepository;

    public TABJTRXResource(TABJTRXRepository tABJTRXRepository) {
        this.tABJTRXRepository = tABJTRXRepository;
    }

    /**
     * {@code POST  /tabjtrxes} : Create a new tABJTRX.
     *
     * @param tABJTRX the tABJTRX to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tABJTRX, or with status {@code 400 (Bad Request)} if the tABJTRX has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tabjtrxes")
    public ResponseEntity<TABJTRX> createTABJTRX(@Valid @RequestBody TABJTRX tABJTRX) throws URISyntaxException {
        log.debug("REST request to save TABJTRX : {}", tABJTRX);
        if (tABJTRX.getJtjntx() != null) {
            throw new BadRequestAlertException("A new tABJTRX cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TABJTRX result = tABJTRXRepository.save(tABJTRX);
        return ResponseEntity
            .created(new URI("/api/tabjtrxes/" + result.getJtjntx()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getJtjntx()))
            .body(result);
    }

    /**
     * {@code PUT  /tabjtrxes/:jtjntx} : Updates an existing tABJTRX.
     *
     * @param jtjntx the id of the tABJTRX to save.
     * @param tABJTRX the tABJTRX to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tABJTRX,
     * or with status {@code 400 (Bad Request)} if the tABJTRX is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tABJTRX couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tabjtrxes/{jtjntx}")
    public ResponseEntity<TABJTRX> updateTABJTRX(
        @PathVariable(value = "jtjntx", required = false) final String jtjntx,
        @Valid @RequestBody TABJTRX tABJTRX
    ) throws URISyntaxException {
        log.debug("REST request to update TABJTRX : {}, {}", jtjntx, tABJTRX);
        if (tABJTRX.getJtjntx() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(jtjntx, tABJTRX.getJtjntx())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tABJTRXRepository.existsById(jtjntx)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TABJTRX result = tABJTRXRepository.save(tABJTRX);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tABJTRX.getJtjntx()))
            .body(result);
    }

    /**
     * {@code PATCH  /tabjtrxes/:jtjntx} : Partial updates given fields of an existing tABJTRX, field will ignore if it is null
     *
     * @param jtjntx the id of the tABJTRX to save.
     * @param tABJTRX the tABJTRX to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tABJTRX,
     * or with status {@code 400 (Bad Request)} if the tABJTRX is not valid,
     * or with status {@code 404 (Not Found)} if the tABJTRX is not found,
     * or with status {@code 500 (Internal Server Error)} if the tABJTRX couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tabjtrxes/{jtjntx}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TABJTRX> partialUpdateTABJTRX(
        @PathVariable(value = "jtjntx", required = false) final String jtjntx,
        @NotNull @RequestBody TABJTRX tABJTRX
    ) throws URISyntaxException {
        log.debug("REST request to partial update TABJTRX partially : {}, {}", jtjntx, tABJTRX);
        if (tABJTRX.getJtjntx() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(jtjntx, tABJTRX.getJtjntx())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tABJTRXRepository.existsById(jtjntx)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TABJTRX> result = tABJTRXRepository
            .findById(tABJTRX.getJtjntx())
            .map(existingTABJTRX -> {
                if (tABJTRX.getJtsts() != null) {
                    existingTABJTRX.setJtsts(tABJTRX.getJtsts());
                }
                if (tABJTRX.getJtdesc() != null) {
                    existingTABJTRX.setJtdesc(tABJTRX.getJtdesc());
                }
                if (tABJTRX.getJtsdes() != null) {
                    existingTABJTRX.setJtsdes(tABJTRX.getJtsdes());
                }
                if (tABJTRX.getJtlmd() != null) {
                    existingTABJTRX.setJtlmd(tABJTRX.getJtlmd());
                }
                if (tABJTRX.getJtouid() != null) {
                    existingTABJTRX.setJtouid(tABJTRX.getJtouid());
                }

                return existingTABJTRX;
            })
            .map(tABJTRXRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tABJTRX.getJtjntx())
        );
    }

    /**
     * {@code GET  /tabjtrxes} : get all the tABJTRXES.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tABJTRXES in body.
     */
    @GetMapping("/tabjtrxes")
    public List<TABJTRX> getAllTABJTRXES() {
        log.debug("REST request to get all TABJTRXES");
        return tABJTRXRepository.findAll();
    }

    /**
     * {@code GET  /tabjtrxes/:id} : get the "id" tABJTRX.
     *
     * @param id the id of the tABJTRX to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tABJTRX, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tabjtrxes/{id}")
    public ResponseEntity<TABJTRX> getTABJTRX(@PathVariable String id) {
        log.debug("REST request to get TABJTRX : {}", id);
        Optional<TABJTRX> tABJTRX = tABJTRXRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tABJTRX);
    }

    /**
     * {@code DELETE  /tabjtrxes/:id} : delete the "id" tABJTRX.
     *
     * @param id the id of the tABJTRX to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tabjtrxes/{id}")
    public ResponseEntity<Void> deleteTABJTRX(@PathVariable String id) {
        log.debug("REST request to delete TABJTRX : {}", id);
        tABJTRXRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
