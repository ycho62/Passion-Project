package com.golfkey.myapp.repository;

import com.golfkey.myapp.domain.Attachment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Attachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {}
