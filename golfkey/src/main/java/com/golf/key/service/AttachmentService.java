package com.golf.key.service;

import com.golf.key.domain.Attachment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Attachment}.
 */
public interface AttachmentService {
    /**
     * Save a attachment.
     *
     * @param attachment the entity to save.
     * @return the persisted entity.
     */
    Attachment save(Attachment attachment);

    /**
     * Updates a attachment.
     *
     * @param attachment the entity to update.
     * @return the persisted entity.
     */
    Attachment update(Attachment attachment);

    /**
     * Partially updates a attachment.
     *
     * @param attachment the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Attachment> partialUpdate(Attachment attachment);

    /**
     * Get all the attachments.
     *
     * @return the list of entities.
     */
    List<Attachment> findAll();

    /**
     * Get all the attachments with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Attachment> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" attachment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Attachment> findOne(Long id);

    /**
     * Delete the "id" attachment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
