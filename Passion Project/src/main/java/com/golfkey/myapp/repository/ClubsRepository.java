package com.golfkey.myapp.repository;

import com.golfkey.myapp.domain.Clubs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Clubs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClubsRepository extends JpaRepository<Clubs, Long> {}
