package com.golf.key.service.impl;

import com.golf.key.domain.ClubStats;
import com.golf.key.repository.ClubStatsRepository;
import com.golf.key.service.ClubStatsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClubStats}.
 */
@Service
@Transactional
public class ClubStatsServiceImpl implements ClubStatsService {

    private final Logger log = LoggerFactory.getLogger(ClubStatsServiceImpl.class);

    private final ClubStatsRepository clubStatsRepository;

    public ClubStatsServiceImpl(ClubStatsRepository clubStatsRepository) {
        this.clubStatsRepository = clubStatsRepository;
    }

    @Override
    public ClubStats save(ClubStats clubStats) {
        log.debug("Request to save ClubStats : {}", clubStats);
        return clubStatsRepository.save(clubStats);
    }

    @Override
    public ClubStats update(ClubStats clubStats) {
        log.debug("Request to save ClubStats : {}", clubStats);
        return clubStatsRepository.save(clubStats);
    }

    @Override
    public Optional<ClubStats> partialUpdate(ClubStats clubStats) {
        log.debug("Request to partially update ClubStats : {}", clubStats);

        return clubStatsRepository
            .findById(clubStats.getId())
            .map(existingClubStats -> {
                if (clubStats.getClubDistance() != null) {
                    existingClubStats.setClubDistance(clubStats.getClubDistance());
                }

                return existingClubStats;
            })
            .map(clubStatsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubStats> findAll() {
        log.debug("Request to get all ClubStats");
        return clubStatsRepository.findAllWithEagerRelationships();
    }

    public Page<ClubStats> findAllWithEagerRelationships(Pageable pageable) {
        return clubStatsRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClubStats> findOne(Long id) {
        log.debug("Request to get ClubStats : {}", id);
        return clubStatsRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClubStats : {}", id);
        clubStatsRepository.deleteById(id);
    }
}
