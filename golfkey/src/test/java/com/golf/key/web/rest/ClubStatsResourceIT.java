package com.golf.key.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.golf.key.IntegrationTest;
import com.golf.key.domain.ClubStats;
import com.golf.key.repository.ClubStatsRepository;
import com.golf.key.service.ClubStatsService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ClubStatsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ClubStatsResourceIT {

    private static final String DEFAULT_CLUB_DISTANCE = "AAAAAAAAAA";
    private static final String UPDATED_CLUB_DISTANCE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/club-stats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClubStatsRepository clubStatsRepository;

    @Mock
    private ClubStatsRepository clubStatsRepositoryMock;

    @Mock
    private ClubStatsService clubStatsServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClubStatsMockMvc;

    private ClubStats clubStats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClubStats createEntity(EntityManager em) {
        ClubStats clubStats = new ClubStats().clubDistance(DEFAULT_CLUB_DISTANCE);
        return clubStats;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClubStats createUpdatedEntity(EntityManager em) {
        ClubStats clubStats = new ClubStats().clubDistance(UPDATED_CLUB_DISTANCE);
        return clubStats;
    }

    @BeforeEach
    public void initTest() {
        clubStats = createEntity(em);
    }

    @Test
    @Transactional
    void createClubStats() throws Exception {
        int databaseSizeBeforeCreate = clubStatsRepository.findAll().size();
        // Create the ClubStats
        restClubStatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clubStats)))
            .andExpect(status().isCreated());

        // Validate the ClubStats in the database
        List<ClubStats> clubStatsList = clubStatsRepository.findAll();
        assertThat(clubStatsList).hasSize(databaseSizeBeforeCreate + 1);
        ClubStats testClubStats = clubStatsList.get(clubStatsList.size() - 1);
        assertThat(testClubStats.getClubDistance()).isEqualTo(DEFAULT_CLUB_DISTANCE);
    }

    @Test
    @Transactional
    void createClubStatsWithExistingId() throws Exception {
        // Create the ClubStats with an existing ID
        clubStats.setId(1L);

        int databaseSizeBeforeCreate = clubStatsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClubStatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clubStats)))
            .andExpect(status().isBadRequest());

        // Validate the ClubStats in the database
        List<ClubStats> clubStatsList = clubStatsRepository.findAll();
        assertThat(clubStatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClubStats() throws Exception {
        // Initialize the database
        clubStatsRepository.saveAndFlush(clubStats);

        // Get all the clubStatsList
        restClubStatsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clubStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].clubDistance").value(hasItem(DEFAULT_CLUB_DISTANCE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClubStatsWithEagerRelationshipsIsEnabled() throws Exception {
        when(clubStatsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClubStatsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(clubStatsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClubStatsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(clubStatsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClubStatsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(clubStatsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getClubStats() throws Exception {
        // Initialize the database
        clubStatsRepository.saveAndFlush(clubStats);

        // Get the clubStats
        restClubStatsMockMvc
            .perform(get(ENTITY_API_URL_ID, clubStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clubStats.getId().intValue()))
            .andExpect(jsonPath("$.clubDistance").value(DEFAULT_CLUB_DISTANCE));
    }

    @Test
    @Transactional
    void getNonExistingClubStats() throws Exception {
        // Get the clubStats
        restClubStatsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClubStats() throws Exception {
        // Initialize the database
        clubStatsRepository.saveAndFlush(clubStats);

        int databaseSizeBeforeUpdate = clubStatsRepository.findAll().size();

        // Update the clubStats
        ClubStats updatedClubStats = clubStatsRepository.findById(clubStats.getId()).get();
        // Disconnect from session so that the updates on updatedClubStats are not directly saved in db
        em.detach(updatedClubStats);
        updatedClubStats.clubDistance(UPDATED_CLUB_DISTANCE);

        restClubStatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClubStats.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClubStats))
            )
            .andExpect(status().isOk());

        // Validate the ClubStats in the database
        List<ClubStats> clubStatsList = clubStatsRepository.findAll();
        assertThat(clubStatsList).hasSize(databaseSizeBeforeUpdate);
        ClubStats testClubStats = clubStatsList.get(clubStatsList.size() - 1);
        assertThat(testClubStats.getClubDistance()).isEqualTo(UPDATED_CLUB_DISTANCE);
    }

    @Test
    @Transactional
    void putNonExistingClubStats() throws Exception {
        int databaseSizeBeforeUpdate = clubStatsRepository.findAll().size();
        clubStats.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClubStatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clubStats.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clubStats))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClubStats in the database
        List<ClubStats> clubStatsList = clubStatsRepository.findAll();
        assertThat(clubStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClubStats() throws Exception {
        int databaseSizeBeforeUpdate = clubStatsRepository.findAll().size();
        clubStats.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClubStatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clubStats))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClubStats in the database
        List<ClubStats> clubStatsList = clubStatsRepository.findAll();
        assertThat(clubStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClubStats() throws Exception {
        int databaseSizeBeforeUpdate = clubStatsRepository.findAll().size();
        clubStats.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClubStatsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clubStats)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClubStats in the database
        List<ClubStats> clubStatsList = clubStatsRepository.findAll();
        assertThat(clubStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClubStatsWithPatch() throws Exception {
        // Initialize the database
        clubStatsRepository.saveAndFlush(clubStats);

        int databaseSizeBeforeUpdate = clubStatsRepository.findAll().size();

        // Update the clubStats using partial update
        ClubStats partialUpdatedClubStats = new ClubStats();
        partialUpdatedClubStats.setId(clubStats.getId());

        restClubStatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClubStats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClubStats))
            )
            .andExpect(status().isOk());

        // Validate the ClubStats in the database
        List<ClubStats> clubStatsList = clubStatsRepository.findAll();
        assertThat(clubStatsList).hasSize(databaseSizeBeforeUpdate);
        ClubStats testClubStats = clubStatsList.get(clubStatsList.size() - 1);
        assertThat(testClubStats.getClubDistance()).isEqualTo(DEFAULT_CLUB_DISTANCE);
    }

    @Test
    @Transactional
    void fullUpdateClubStatsWithPatch() throws Exception {
        // Initialize the database
        clubStatsRepository.saveAndFlush(clubStats);

        int databaseSizeBeforeUpdate = clubStatsRepository.findAll().size();

        // Update the clubStats using partial update
        ClubStats partialUpdatedClubStats = new ClubStats();
        partialUpdatedClubStats.setId(clubStats.getId());

        partialUpdatedClubStats.clubDistance(UPDATED_CLUB_DISTANCE);

        restClubStatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClubStats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClubStats))
            )
            .andExpect(status().isOk());

        // Validate the ClubStats in the database
        List<ClubStats> clubStatsList = clubStatsRepository.findAll();
        assertThat(clubStatsList).hasSize(databaseSizeBeforeUpdate);
        ClubStats testClubStats = clubStatsList.get(clubStatsList.size() - 1);
        assertThat(testClubStats.getClubDistance()).isEqualTo(UPDATED_CLUB_DISTANCE);
    }

    @Test
    @Transactional
    void patchNonExistingClubStats() throws Exception {
        int databaseSizeBeforeUpdate = clubStatsRepository.findAll().size();
        clubStats.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClubStatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clubStats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clubStats))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClubStats in the database
        List<ClubStats> clubStatsList = clubStatsRepository.findAll();
        assertThat(clubStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClubStats() throws Exception {
        int databaseSizeBeforeUpdate = clubStatsRepository.findAll().size();
        clubStats.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClubStatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clubStats))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClubStats in the database
        List<ClubStats> clubStatsList = clubStatsRepository.findAll();
        assertThat(clubStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClubStats() throws Exception {
        int databaseSizeBeforeUpdate = clubStatsRepository.findAll().size();
        clubStats.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClubStatsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clubStats))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClubStats in the database
        List<ClubStats> clubStatsList = clubStatsRepository.findAll();
        assertThat(clubStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClubStats() throws Exception {
        // Initialize the database
        clubStatsRepository.saveAndFlush(clubStats);

        int databaseSizeBeforeDelete = clubStatsRepository.findAll().size();

        // Delete the clubStats
        restClubStatsMockMvc
            .perform(delete(ENTITY_API_URL_ID, clubStats.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClubStats> clubStatsList = clubStatsRepository.findAll();
        assertThat(clubStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
