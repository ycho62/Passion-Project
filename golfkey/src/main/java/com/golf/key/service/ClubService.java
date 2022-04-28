package com.golf.key.service;

import com.golf.key.domain.Club;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Club}.
 */
public interface ClubService {
    /**
     * Save a club.
     *
     * @param club the entity to save.
     * @return the persisted entity.
     */
    Club save(Club club);

    /**
     * Updates a club.
     *
     * @param club the entity to update.
     * @return the persisted entity.
     */
    Club update(Club club);

    /**
     * Partially updates a club.
     *
     * @param club the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Club> partialUpdate(Club club);

    /**
     * Get all the clubs.
     *
     * @return the list of entities.
     */
    List<Club> findAll();

    /**
     * Get all the clubs with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Club> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" club.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Club> findOne(Long id);

    /**
     * Delete the "id" club.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
