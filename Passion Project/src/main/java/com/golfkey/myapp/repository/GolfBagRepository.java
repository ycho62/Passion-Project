package com.golfkey.myapp.repository;

import com.golfkey.myapp.domain.GolfBag;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GolfBag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GolfBagRepository extends JpaRepository<GolfBag, Long> {}
