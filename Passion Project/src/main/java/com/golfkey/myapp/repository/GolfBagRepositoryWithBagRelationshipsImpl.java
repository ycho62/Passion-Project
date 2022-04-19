package com.golfkey.myapp.repository;

import com.golfkey.myapp.domain.GolfBag;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class GolfBagRepositoryWithBagRelationshipsImpl implements GolfBagRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<GolfBag> fetchBagRelationships(Optional<GolfBag> golfBag) {
        return golfBag.map(this::fetchClubs);
    }

    @Override
    public Page<GolfBag> fetchBagRelationships(Page<GolfBag> golfBags) {
        return new PageImpl<>(fetchBagRelationships(golfBags.getContent()), golfBags.getPageable(), golfBags.getTotalElements());
    }

    @Override
    public List<GolfBag> fetchBagRelationships(List<GolfBag> golfBags) {
        return Optional.of(golfBags).map(this::fetchClubs).orElse(Collections.emptyList());
    }

    GolfBag fetchClubs(GolfBag result) {
        return entityManager
            .createQuery("select golfBag from GolfBag golfBag left join fetch golfBag.clubs where golfBag is :golfBag", GolfBag.class)
            .setParameter("golfBag", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<GolfBag> fetchClubs(List<GolfBag> golfBags) {
        return entityManager
            .createQuery(
                "select distinct golfBag from GolfBag golfBag left join fetch golfBag.clubs where golfBag in :golfBags",
                GolfBag.class
            )
            .setParameter("golfBags", golfBags)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
