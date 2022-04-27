package com.golf.key.web.rest;

import com.golf.key.domain.ClubStats;
import com.golf.key.repository.ClubStatsRepository;
import com.golf.key.service.ClubStatsService;
import com.golf.key.web.rest.errors.BadRequestAlertException;
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
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.golf.key.domain.ClubStats}.
 */
@RestController
@RequestMapping("/api")
public class ClubStatsResource {

    private final Logger log = LoggerFactory.getLogger(ClubStatsResource.class);

    private static final String ENTITY_NAME = "clubStats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClubStatsService clubStatsService;

    private final ClubStatsRepository clubStatsRepository;

    public ClubStatsResource(ClubStatsService clubStatsService, ClubStatsRepository clubStatsRepository) {
        this.clubStatsService = clubStatsService;
        this.clubStatsRepository = clubStatsRepository;
    }

    /**
     * {@code POST  /club-stats} : Create a new clubStats.
     *
     * @param clubStats the clubStats to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clubStats, or with status {@code 400 (Bad Request)} if the clubStats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/club-stats")
    public ResponseEntity<ClubStats> createClubStats(@Valid @RequestBody ClubStats clubStats) throws URISyntaxException {
        log.debug("REST request to save ClubStats : {}", clubStats);
        if (clubStats.getId() != null) {
            throw new BadRequestAlertException("A new clubStats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClubStats result = clubStatsService.save(clubStats);
        return ResponseEntity
            .created(new URI("/api/club-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /club-stats/:id} : Updates an existing clubStats.
     *
     * @param id the id of the clubStats to save.
     * @param clubStats the clubStats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clubStats,
     * or with status {@code 400 (Bad Request)} if the clubStats is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clubStats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/club-stats/{id}")
    public ResponseEntity<ClubStats> updateClubStats(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClubStats clubStats
    ) throws URISyntaxException {
        log.debug("REST request to update ClubStats : {}, {}", id, clubStats);
        if (clubStats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clubStats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clubStatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClubStats result = clubStatsService.update(clubStats);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clubStats.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /club-stats/:id} : Partial updates given fields of an existing clubStats, field will ignore if it is null
     *
     * @param id the id of the clubStats to save.
     * @param clubStats the clubStats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clubStats,
     * or with status {@code 400 (Bad Request)} if the clubStats is not valid,
     * or with status {@code 404 (Not Found)} if the clubStats is not found,
     * or with status {@code 500 (Internal Server Error)} if the clubStats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/club-stats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClubStats> partialUpdateClubStats(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClubStats clubStats
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClubStats partially : {}, {}", id, clubStats);
        if (clubStats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clubStats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clubStatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClubStats> result = clubStatsService.partialUpdate(clubStats);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clubStats.getId().toString())
        );
    }

    /**
     * {@code GET  /club-stats} : get all the clubStats.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clubStats in body.
     */
    @GetMapping("/club-stats")
    public List<ClubStats> getAllClubStats(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ClubStats");
        return clubStatsService.findAll();
    }

    /**
     * {@code GET  /club-stats/:id} : get the "id" clubStats.
     *
     * @param id the id of the clubStats to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clubStats, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/club-stats/{id}")
    public ResponseEntity<ClubStats> getClubStats(@PathVariable Long id) {
        log.debug("REST request to get ClubStats : {}", id);
        Optional<ClubStats> clubStats = clubStatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clubStats);
    }

    /**
     * {@code DELETE  /club-stats/:id} : delete the "id" clubStats.
     *
     * @param id the id of the clubStats to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/club-stats/{id}")
    public ResponseEntity<Void> deleteClubStats(@PathVariable Long id) {
        log.debug("REST request to delete ClubStats : {}", id);
        clubStatsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
