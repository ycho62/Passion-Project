package com.golf.key.repository;

import com.golf.key.domain.Attachment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Attachment entity.
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    default Optional<Attachment> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Attachment> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Attachment> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct attachment from Attachment attachment left join fetch attachment.golfBag",
        countQuery = "select count(distinct attachment) from Attachment attachment"
    )
    Page<Attachment> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct attachment from Attachment attachment left join fetch attachment.golfBag")
    List<Attachment> findAllWithToOneRelationships();

    @Query("select attachment from Attachment attachment left join fetch attachment.golfBag where attachment.id =:id")
    Optional<Attachment> findOneWithToOneRelationships(@Param("id") Long id);
}
