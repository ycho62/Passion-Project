package com.golfkey.myapp.repository;

import com.golfkey.myapp.domain.ClubStats;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClubStats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClubStatsRepository extends JpaRepository<ClubStats, Long> {}
