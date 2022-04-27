package com.golf.key.service;

import com.golf.key.domain.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Comment}.
 */
public interface CommentService {
    /**
     * Save a comment.
     *
     * @param comment the entity to save.
     * @return the persisted entity.
     */
    Comment save(Comment comment);

    /**
     * Updates a comment.
     *
     * @param comment the entity to update.
     * @return the persisted entity.
     */
    Comment update(Comment comment);

    /**
     * Partially updates a comment.
     *
     * @param comment the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Comment> partialUpdate(Comment comment);

    /**
     * Get all the comments.
     *
     * @return the list of entities.
     */
    List<Comment> findAll();

    /**
     * Get all the comments with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Comment> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" comment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Comment> findOne(Long id);

    /**
     * Delete the "id" comment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
