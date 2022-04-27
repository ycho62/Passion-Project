package com.golf.key.service.impl;

import com.golf.key.domain.GolfBag;
import com.golf.key.repository.GolfBagRepository;
import com.golf.key.service.GolfBagService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GolfBag}.
 */
@Service
@Transactional
public class GolfBagServiceImpl implements GolfBagService {

    private final Logger log = LoggerFactory.getLogger(GolfBagServiceImpl.class);

    private final GolfBagRepository golfBagRepository;

    public GolfBagServiceImpl(GolfBagRepository golfBagRepository) {
        this.golfBagRepository = golfBagRepository;
    }

    @Override
    public GolfBag save(GolfBag golfBag) {
        log.debug("Request to save GolfBag : {}", golfBag);
        return golfBagRepository.save(golfBag);
    }

    @Override
    public GolfBag update(GolfBag golfBag) {
        log.debug("Request to save GolfBag : {}", golfBag);
        return golfBagRepository.save(golfBag);
    }

    @Override
    public Optional<GolfBag> partialUpdate(GolfBag golfBag) {
        log.debug("Request to partially update GolfBag : {}", golfBag);

        return golfBagRepository
            .findById(golfBag.getId())
            .map(existingGolfBag -> {
                if (golfBag.getName() != null) {
                    existingGolfBag.setName(golfBag.getName());
                }
                if (golfBag.getClubs() != null) {
                    existingGolfBag.setClubs(golfBag.getClubs());
                }

                return existingGolfBag;
            })
            .map(golfBagRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GolfBag> findAll() {
        log.debug("Request to get all GolfBags");
        return golfBagRepository.findAllWithEagerRelationships();
    }

    public Page<GolfBag> findAllWithEagerRelationships(Pageable pageable) {
        return golfBagRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GolfBag> findOne(Long id) {
        log.debug("Request to get GolfBag : {}", id);
        return golfBagRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GolfBag : {}", id);
        golfBagRepository.deleteById(id);
    }
}
