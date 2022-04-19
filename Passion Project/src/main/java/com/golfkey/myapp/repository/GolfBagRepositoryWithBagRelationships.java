package com.golfkey.myapp.repository;

import com.golfkey.myapp.domain.GolfBag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface GolfBagRepositoryWithBagRelationships {
    Optional<GolfBag> fetchBagRelationships(Optional<GolfBag> golfBag);

    List<GolfBag> fetchBagRelationships(List<GolfBag> golfBags);

    Page<GolfBag> fetchBagRelationships(Page<GolfBag> golfBags);
}
