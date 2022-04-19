package com.golfkey.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.golfkey.myapp.IntegrationTest;
import com.golfkey.myapp.domain.Clubs;
import com.golfkey.myapp.domain.enumeration.ClubName;
import com.golfkey.myapp.repository.ClubsRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ClubsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClubsResourceIT {

    private static final ClubName DEFAULT_CLUBNAME = ClubName.DRIVER;
    private static final ClubName UPDATED_CLUBNAME = ClubName.SIXTYWEDGE;

    private static final String ENTITY_API_URL = "/api/clubs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClubsRepository clubsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClubsMockMvc;

    private Clubs clubs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clubs createEntity(EntityManager em) {
        Clubs clubs = new Clubs().clubname(DEFAULT_CLUBNAME);
        return clubs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clubs createUpdatedEntity(EntityManager em) {
        Clubs clubs = new Clubs().clubname(UPDATED_CLUBNAME);
        return clubs;
    }

    @BeforeEach
    public void initTest() {
        clubs = createEntity(em);
    }

    @Test
    @Transactional
    void createClubs() throws Exception {
        int databaseSizeBeforeCreate = clubsRepository.findAll().size();
        // Create the Clubs
        restClubsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clubs)))
            .andExpect(status().isCreated());

        // Validate the Clubs in the database
        List<Clubs> clubsList = clubsRepository.findAll();
        assertThat(clubsList).hasSize(databaseSizeBeforeCreate + 1);
        Clubs testClubs = clubsList.get(clubsList.size() - 1);
        assertThat(testClubs.getClubname()).isEqualTo(DEFAULT_CLUBNAME);
    }

    @Test
    @Transactional
    void createClubsWithExistingId() throws Exception {
        // Create the Clubs with an existing ID
        clubs.setId(1L);

        int databaseSizeBeforeCreate = clubsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClubsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clubs)))
            .andExpect(status().isBadRequest());

        // Validate the Clubs in the database
        List<Clubs> clubsList = clubsRepository.findAll();
        assertThat(clubsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClubs() throws Exception {
        // Initialize the database
        clubsRepository.saveAndFlush(clubs);

        // Get all the clubsList
        restClubsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clubs.getId().intValue())))
            .andExpect(jsonPath("$.[*].clubname").value(hasItem(DEFAULT_CLUBNAME.toString())));
    }

    @Test
    @Transactional
    void getClubs() throws Exception {
        // Initialize the database
        clubsRepository.saveAndFlush(clubs);

        // Get the clubs
        restClubsMockMvc
            .perform(get(ENTITY_API_URL_ID, clubs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clubs.getId().intValue()))
            .andExpect(jsonPath("$.clubname").value(DEFAULT_CLUBNAME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingClubs() throws Exception {
        // Get the clubs
        restClubsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClubs() throws Exception {
        // Initialize the database
        clubsRepository.saveAndFlush(clubs);

        int databaseSizeBeforeUpdate = clubsRepository.findAll().size();

        // Update the clubs
        Clubs updatedClubs = clubsRepository.findById(clubs.getId()).get();
        // Disconnect from session so that the updates on updatedClubs are not directly saved in db
        em.detach(updatedClubs);
        updatedClubs.clubname(UPDATED_CLUBNAME);

        restClubsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClubs.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClubs))
            )
            .andExpect(status().isOk());

        // Validate the Clubs in the database
        List<Clubs> clubsList = clubsRepository.findAll();
        assertThat(clubsList).hasSize(databaseSizeBeforeUpdate);
        Clubs testClubs = clubsList.get(clubsList.size() - 1);
        assertThat(testClubs.getClubname()).isEqualTo(UPDATED_CLUBNAME);
    }

    @Test
    @Transactional
    void putNonExistingClubs() throws Exception {
        int databaseSizeBeforeUpdate = clubsRepository.findAll().size();
        clubs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClubsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clubs.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clubs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clubs in the database
        List<Clubs> clubsList = clubsRepository.findAll();
        assertThat(clubsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClubs() throws Exception {
        int databaseSizeBeforeUpdate = clubsRepository.findAll().size();
        clubs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClubsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clubs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clubs in the database
        List<Clubs> clubsList = clubsRepository.findAll();
        assertThat(clubsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClubs() throws Exception {
        int databaseSizeBeforeUpdate = clubsRepository.findAll().size();
        clubs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClubsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clubs)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clubs in the database
        List<Clubs> clubsList = clubsRepository.findAll();
        assertThat(clubsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClubsWithPatch() throws Exception {
        // Initialize the database
        clubsRepository.saveAndFlush(clubs);

        int databaseSizeBeforeUpdate = clubsRepository.findAll().size();

        // Update the clubs using partial update
        Clubs partialUpdatedClubs = new Clubs();
        partialUpdatedClubs.setId(clubs.getId());

        restClubsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClubs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClubs))
            )
            .andExpect(status().isOk());

        // Validate the Clubs in the database
        List<Clubs> clubsList = clubsRepository.findAll();
        assertThat(clubsList).hasSize(databaseSizeBeforeUpdate);
        Clubs testClubs = clubsList.get(clubsList.size() - 1);
        assertThat(testClubs.getClubname()).isEqualTo(DEFAULT_CLUBNAME);
    }

    @Test
    @Transactional
    void fullUpdateClubsWithPatch() throws Exception {
        // Initialize the database
        clubsRepository.saveAndFlush(clubs);

        int databaseSizeBeforeUpdate = clubsRepository.findAll().size();

        // Update the clubs using partial update
        Clubs partialUpdatedClubs = new Clubs();
        partialUpdatedClubs.setId(clubs.getId());

        partialUpdatedClubs.clubname(UPDATED_CLUBNAME);

        restClubsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClubs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClubs))
            )
            .andExpect(status().isOk());

        // Validate the Clubs in the database
        List<Clubs> clubsList = clubsRepository.findAll();
        assertThat(clubsList).hasSize(databaseSizeBeforeUpdate);
        Clubs testClubs = clubsList.get(clubsList.size() - 1);
        assertThat(testClubs.getClubname()).isEqualTo(UPDATED_CLUBNAME);
    }

    @Test
    @Transactional
    void patchNonExistingClubs() throws Exception {
        int databaseSizeBeforeUpdate = clubsRepository.findAll().size();
        clubs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClubsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clubs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clubs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clubs in the database
        List<Clubs> clubsList = clubsRepository.findAll();
        assertThat(clubsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClubs() throws Exception {
        int databaseSizeBeforeUpdate = clubsRepository.findAll().size();
        clubs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClubsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clubs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clubs in the database
        List<Clubs> clubsList = clubsRepository.findAll();
        assertThat(clubsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClubs() throws Exception {
        int databaseSizeBeforeUpdate = clubsRepository.findAll().size();
        clubs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClubsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clubs)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clubs in the database
        List<Clubs> clubsList = clubsRepository.findAll();
        assertThat(clubsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClubs() throws Exception {
        // Initialize the database
        clubsRepository.saveAndFlush(clubs);

        int databaseSizeBeforeDelete = clubsRepository.findAll().size();

        // Delete the clubs
        restClubsMockMvc
            .perform(delete(ENTITY_API_URL_ID, clubs.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Clubs> clubsList = clubsRepository.findAll();
        assertThat(clubsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
