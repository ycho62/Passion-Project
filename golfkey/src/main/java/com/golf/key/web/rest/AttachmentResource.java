package com.golf.key.web.rest;

import com.golf.key.domain.Attachment;
import com.golf.key.repository.AttachmentRepository;
import com.golf.key.service.AttachmentService;
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
 * REST controller for managing {@link com.golf.key.domain.Attachment}.
 */
@RestController
@RequestMapping("/api")
public class AttachmentResource {

    private final Logger log = LoggerFactory.getLogger(AttachmentResource.class);

    private static final String ENTITY_NAME = "attachment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttachmentService attachmentService;

    private final AttachmentRepository attachmentRepository;

    public AttachmentResource(AttachmentService attachmentService, AttachmentRepository attachmentRepository) {
        this.attachmentService = attachmentService;
        this.attachmentRepository = attachmentRepository;
    }

    /**
     * {@code POST  /attachments} : Create a new attachment.
     *
     * @param attachment the attachment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attachment, or with status {@code 400 (Bad Request)} if the attachment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attachments")
    public ResponseEntity<Attachment> createAttachment(@Valid @RequestBody Attachment attachment) throws URISyntaxException {
        log.debug("REST request to save Attachment : {}", attachment);
        if (attachment.getId() != null) {
            throw new BadRequestAlertException("A new attachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Attachment result = attachmentService.save(attachment);
        return ResponseEntity
            .created(new URI("/api/attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attachments/:id} : Updates an existing attachment.
     *
     * @param id the id of the attachment to save.
     * @param attachment the attachment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachment,
     * or with status {@code 400 (Bad Request)} if the attachment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attachment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attachments/{id}")
    public ResponseEntity<Attachment> updateAttachment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Attachment attachment
    ) throws URISyntaxException {
        log.debug("REST request to update Attachment : {}, {}", id, attachment);
        if (attachment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attachment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Attachment result = attachmentService.update(attachment);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attachment.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attachments/:id} : Partial updates given fields of an existing attachment, field will ignore if it is null
     *
     * @param id the id of the attachment to save.
     * @param attachment the attachment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachment,
     * or with status {@code 400 (Bad Request)} if the attachment is not valid,
     * or with status {@code 404 (Not Found)} if the attachment is not found,
     * or with status {@code 500 (Internal Server Error)} if the attachment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attachments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Attachment> partialUpdateAttachment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Attachment attachment
    ) throws URISyntaxException {
        log.debug("REST request to partial update Attachment partially : {}, {}", id, attachment);
        if (attachment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attachment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Attachment> result = attachmentService.partialUpdate(attachment);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attachment.getId().toString())
        );
    }

    /**
     * {@code GET  /attachments} : get all the attachments.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attachments in body.
     */
    @GetMapping("/attachments")
    public List<Attachment> getAllAttachments(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Attachments");
        return attachmentService.findAll();
    }

    /**
     * {@code GET  /attachments/:id} : get the "id" attachment.
     *
     * @param id the id of the attachment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attachment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attachments/{id}")
    public ResponseEntity<Attachment> getAttachment(@PathVariable Long id) {
        log.debug("REST request to get Attachment : {}", id);
        Optional<Attachment> attachment = attachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attachment);
    }

    /**
     * {@code DELETE  /attachments/:id} : delete the "id" attachment.
     *
     * @param id the id of the attachment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attachments/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
        log.debug("REST request to delete Attachment : {}", id);
        attachmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
