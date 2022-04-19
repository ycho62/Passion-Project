package com.golfkey.myapp.web.rest;

import com.golfkey.myapp.domain.Clubs;
import com.golfkey.myapp.repository.ClubsRepository;
import com.golfkey.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.golfkey.myapp.domain.Clubs}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClubsResource {

    private final Logger log = LoggerFactory.getLogger(ClubsResource.class);

    private static final String ENTITY_NAME = "clubs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClubsRepository clubsRepository;

    public ClubsResource(ClubsRepository clubsRepository) {
        this.clubsRepository = clubsRepository;
    }

    /**
     * {@code POST  /clubs} : Create a new clubs.
     *
     * @param clubs the clubs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clubs, or with status {@code 400 (Bad Request)} if the clubs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clubs")
    public ResponseEntity<Clubs> createClubs(@RequestBody Clubs clubs) throws URISyntaxException {
        log.debug("REST request to save Clubs : {}", clubs);
        if (clubs.getId() != null) {
            throw new BadRequestAlertException("A new clubs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Clubs result = clubsRepository.save(clubs);
        return ResponseEntity
            .created(new URI("/api/clubs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clubs/:id} : Updates an existing clubs.
     *
     * @param id the id of the clubs to save.
     * @param clubs the clubs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clubs,
     * or with status {@code 400 (Bad Request)} if the clubs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clubs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clubs/{id}")
    public ResponseEntity<Clubs> updateClubs(@PathVariable(value = "id", required = false) final Long id, @RequestBody Clubs clubs)
        throws URISyntaxException {
        log.debug("REST request to update Clubs : {}, {}", id, clubs);
        if (clubs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clubs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clubsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Clubs result = clubsRepository.save(clubs);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clubs.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /clubs/:id} : Partial updates given fields of an existing clubs, field will ignore if it is null
     *
     * @param id the id of the clubs to save.
     * @param clubs the clubs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clubs,
     * or with status {@code 400 (Bad Request)} if the clubs is not valid,
     * or with status {@code 404 (Not Found)} if the clubs is not found,
     * or with status {@code 500 (Internal Server Error)} if the clubs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/clubs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Clubs> partialUpdateClubs(@PathVariable(value = "id", required = false) final Long id, @RequestBody Clubs clubs)
        throws URISyntaxException {
        log.debug("REST request to partial update Clubs partially : {}, {}", id, clubs);
        if (clubs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clubs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clubsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Clubs> result = clubsRepository
            .findById(clubs.getId())
            .map(existingClubs -> {
                if (clubs.getClubname() != null) {
                    existingClubs.setClubname(clubs.getClubname());
                }

                return existingClubs;
            })
            .map(clubsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clubs.getId().toString())
        );
    }

    /**
     * {@code GET  /clubs} : get all the clubs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clubs in body.
     */
    @GetMapping("/clubs")
    public List<Clubs> getAllClubs() {
        log.debug("REST request to get all Clubs");
        return clubsRepository.findAll();
    }

    /**
     * {@code GET  /clubs/:id} : get the "id" clubs.
     *
     * @param id the id of the clubs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clubs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clubs/{id}")
    public ResponseEntity<Clubs> getClubs(@PathVariable Long id) {
        log.debug("REST request to get Clubs : {}", id);
        Optional<Clubs> clubs = clubsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(clubs);
    }

    /**
     * {@code DELETE  /clubs/:id} : delete the "id" clubs.
     *
     * @param id the id of the clubs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clubs/{id}")
    public ResponseEntity<Void> deleteClubs(@PathVariable Long id) {
        log.debug("REST request to delete Clubs : {}", id);
        clubsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
