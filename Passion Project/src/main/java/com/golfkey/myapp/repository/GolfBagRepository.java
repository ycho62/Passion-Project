package com.golfkey.myapp.repository;

import com.golfkey.myapp.domain.GolfBag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GolfBag entity.
 */
@Repository
public interface GolfBagRepository extends GolfBagRepositoryWithBagRelationships, JpaRepository<GolfBag, Long> {
    default Optional<GolfBag> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<GolfBag> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<GolfBag> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct golfBag from GolfBag golfBag left join fetch golfBag.user",
        countQuery = "select count(distinct golfBag) from GolfBag golfBag"
    )
    Page<GolfBag> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct golfBag from GolfBag golfBag left join fetch golfBag.user")
    List<GolfBag> findAllWithToOneRelationships();

    @Query("select golfBag from GolfBag golfBag left join fetch golfBag.user where golfBag.id =:id")
    Optional<GolfBag> findOneWithToOneRelationships(@Param("id") Long id);
}
