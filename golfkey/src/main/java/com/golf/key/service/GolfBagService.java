package com.golf.key.service;

import com.golf.key.domain.GolfBag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link GolfBag}.
 */
public interface GolfBagService {
    /**
     * Save a golfBag.
     *
     * @param golfBag the entity to save.
     * @return the persisted entity.
     */
    GolfBag save(GolfBag golfBag);

    /**
     * Updates a golfBag.
     *
     * @param golfBag the entity to update.
     * @return the persisted entity.
     */
    GolfBag update(GolfBag golfBag);

    /**
     * Partially updates a golfBag.
     *
     * @param golfBag the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GolfBag> partialUpdate(GolfBag golfBag);

    /**
     * Get all the golfBags.
     *
     * @return the list of entities.
     */
    List<GolfBag> findAll();

    /**
     * Get all the golfBags with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GolfBag> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" golfBag.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GolfBag> findOne(Long id);

    /**
     * Delete the "id" golfBag.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
