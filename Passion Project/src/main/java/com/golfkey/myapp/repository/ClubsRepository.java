package com.golfkey.myapp.repository;

import com.golfkey.myapp.domain.Clubs;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Clubs entity.
 */
@Repository
public interface ClubsRepository extends JpaRepository<Clubs, Long> {
    default Optional<Clubs> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Clubs> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Clubs> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct clubs from Clubs clubs left join fetch clubs.golfBag",
        countQuery = "select count(distinct clubs) from Clubs clubs"
    )
    Page<Clubs> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct clubs from Clubs clubs left join fetch clubs.golfBag")
    List<Clubs> findAllWithToOneRelationships();

    @Query("select clubs from Clubs clubs left join fetch clubs.golfBag where clubs.id =:id")
    Optional<Clubs> findOneWithToOneRelationships(@Param("id") Long id);
}
