package com.golf.key.service.impl;

import com.golf.key.domain.Attachment;
import com.golf.key.repository.AttachmentRepository;
import com.golf.key.service.AttachmentService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Attachment}.
 */
@Service
@Transactional
public class AttachmentServiceImpl implements AttachmentService {

    private final Logger log = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    private final AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public Attachment save(Attachment attachment) {
        log.debug("Request to save Attachment : {}", attachment);
        return attachmentRepository.save(attachment);
    }

    @Override
    public Attachment update(Attachment attachment) {
        log.debug("Request to save Attachment : {}", attachment);
        return attachmentRepository.save(attachment);
    }

    @Override
    public Optional<Attachment> partialUpdate(Attachment attachment) {
        log.debug("Request to partially update Attachment : {}", attachment);

        return attachmentRepository
            .findById(attachment.getId())
            .map(existingAttachment -> {
                if (attachment.getName() != null) {
                    existingAttachment.setName(attachment.getName());
                }
                if (attachment.getDate() != null) {
                    existingAttachment.setDate(attachment.getDate());
                }
                if (attachment.getFile() != null) {
                    existingAttachment.setFile(attachment.getFile());
                }
                if (attachment.getFileContentType() != null) {
                    existingAttachment.setFileContentType(attachment.getFileContentType());
                }

                return existingAttachment;
            })
            .map(attachmentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Attachment> findAll() {
        log.debug("Request to get all Attachments");
        return attachmentRepository.findAllWithEagerRelationships();
    }

    public Page<Attachment> findAllWithEagerRelationships(Pageable pageable) {
        return attachmentRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Attachment> findOne(Long id) {
        log.debug("Request to get Attachment : {}", id);
        return attachmentRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Attachment : {}", id);
        attachmentRepository.deleteById(id);
    }
}
