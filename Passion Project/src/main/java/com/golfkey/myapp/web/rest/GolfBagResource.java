package com.golfkey.myapp.web.rest;

import com.golfkey.myapp.domain.GolfBag;
import com.golfkey.myapp.repository.GolfBagRepository;
import com.golfkey.myapp.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.golfkey.myapp.domain.GolfBag}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GolfBagResource {

    private final Logger log = LoggerFactory.getLogger(GolfBagResource.class);

    private static final String ENTITY_NAME = "golfBag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GolfBagRepository golfBagRepository;

    public GolfBagResource(GolfBagRepository golfBagRepository) {
        this.golfBagRepository = golfBagRepository;
    }

    /**
     * {@code POST  /golf-bags} : Create a new golfBag.
     *
     * @param golfBag the golfBag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new golfBag, or with status {@code 400 (Bad Request)} if the golfBag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/golf-bags")
    public ResponseEntity<GolfBag> createGolfBag(@Valid @RequestBody GolfBag golfBag) throws URISyntaxException {
        log.debug("REST request to save GolfBag : {}", golfBag);
        if (golfBag.getId() != null) {
            throw new BadRequestAlertException("A new golfBag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GolfBag result = golfBagRepository.save(golfBag);
        return ResponseEntity
            .created(new URI("/api/golf-bags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /golf-bags/:id} : Updates an existing golfBag.
     *
     * @param id the id of the golfBag to save.
     * @param golfBag the golfBag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated golfBag,
     * or with status {@code 400 (Bad Request)} if the golfBag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the golfBag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/golf-bags/{id}")
    public ResponseEntity<GolfBag> updateGolfBag(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GolfBag golfBag
    ) throws URISyntaxException {
        log.debug("REST request to update GolfBag : {}, {}", id, golfBag);
        if (golfBag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, golfBag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!golfBagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GolfBag result = golfBagRepository.save(golfBag);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, golfBag.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /golf-bags/:id} : Partial updates given fields of an existing golfBag, field will ignore if it is null
     *
     * @param id the id of the golfBag to save.
     * @param golfBag the golfBag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated golfBag,
     * or with status {@code 400 (Bad Request)} if the golfBag is not valid,
     * or with status {@code 404 (Not Found)} if the golfBag is not found,
     * or with status {@code 500 (Internal Server Error)} if the golfBag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/golf-bags/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GolfBag> partialUpdateGolfBag(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GolfBag golfBag
    ) throws URISyntaxException {
        log.debug("REST request to partial update GolfBag partially : {}, {}", id, golfBag);
        if (golfBag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, golfBag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!golfBagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GolfBag> result = golfBagRepository
            .findById(golfBag.getId())
            .map(existingGolfBag -> {
                if (golfBag.getName() != null) {
                    existingGolfBag.setName(golfBag.getName());
                }

                return existingGolfBag;
            })
            .map(golfBagRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, golfBag.getId().toString())
        );
    }

    /**
     * {@code GET  /golf-bags} : get all the golfBags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of golfBags in body.
     */
    @GetMapping("/golf-bags")
    public List<GolfBag> getAllGolfBags() {
        log.debug("REST request to get all GolfBags");
        return golfBagRepository.findAll();
    }

    /**
     * {@code GET  /golf-bags/:id} : get the "id" golfBag.
     *
     * @param id the id of the golfBag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the golfBag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/golf-bags/{id}")
    public ResponseEntity<GolfBag> getGolfBag(@PathVariable Long id) {
        log.debug("REST request to get GolfBag : {}", id);
        Optional<GolfBag> golfBag = golfBagRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(golfBag);
    }

    /**
     * {@code DELETE  /golf-bags/:id} : delete the "id" golfBag.
     *
     * @param id the id of the golfBag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/golf-bags/{id}")
    public ResponseEntity<Void> deleteGolfBag(@PathVariable Long id) {
        log.debug("REST request to delete GolfBag : {}", id);
        golfBagRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
