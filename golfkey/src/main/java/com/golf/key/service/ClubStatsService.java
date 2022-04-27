package com.golf.key.service;

import com.golf.key.domain.ClubStats;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ClubStats}.
 */
public interface ClubStatsService {
    /**
     * Save a clubStats.
     *
     * @param clubStats the entity to save.
     * @return the persisted entity.
     */
    ClubStats save(ClubStats clubStats);

    /**
     * Updates a clubStats.
     *
     * @param clubStats the entity to update.
     * @return the persisted entity.
     */
    ClubStats update(ClubStats clubStats);

    /**
     * Partially updates a clubStats.
     *
     * @param clubStats the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ClubStats> partialUpdate(ClubStats clubStats);

    /**
     * Get all the clubStats.
     *
     * @return the list of entities.
     */
    List<ClubStats> findAll();

    /**
     * Get all the clubStats with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClubStats> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" clubStats.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClubStats> findOne(Long id);

    /**
     * Delete the "id" clubStats.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
