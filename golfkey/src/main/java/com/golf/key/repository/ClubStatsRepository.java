package com.golf.key.repository;

import com.golf.key.domain.ClubStats;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClubStats entity.
 */
@Repository
public interface ClubStatsRepository extends JpaRepository<ClubStats, Long> {
    default Optional<ClubStats> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ClubStats> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ClubStats> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct clubStats from ClubStats clubStats left join fetch clubStats.golfBag",
        countQuery = "select count(distinct clubStats) from ClubStats clubStats"
    )
    Page<ClubStats> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct clubStats from ClubStats clubStats left join fetch clubStats.golfBag")
    List<ClubStats> findAllWithToOneRelationships();

    @Query("select clubStats from ClubStats clubStats left join fetch clubStats.golfBag where clubStats.id =:id")
    Optional<ClubStats> findOneWithToOneRelationships(@Param("id") Long id);
}
