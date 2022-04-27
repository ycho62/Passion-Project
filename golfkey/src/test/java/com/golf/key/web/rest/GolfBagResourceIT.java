package com.golf.key.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.golf.key.IntegrationTest;
import com.golf.key.domain.GolfBag;
import com.golf.key.domain.enumeration.ClubTypes;
import com.golf.key.repository.GolfBagRepository;
import com.golf.key.service.GolfBagService;
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
 * Integration tests for the {@link GolfBagResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class GolfBagResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ClubTypes DEFAULT_CLUBS = ClubTypes.DRIVER;
    private static final ClubTypes UPDATED_CLUBS = ClubTypes.SIXTYWEDGE;

    private static final String ENTITY_API_URL = "/api/golf-bags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GolfBagRepository golfBagRepository;

    @Mock
    private GolfBagRepository golfBagRepositoryMock;

    @Mock
    private GolfBagService golfBagServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGolfBagMockMvc;

    private GolfBag golfBag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GolfBag createEntity(EntityManager em) {
        GolfBag golfBag = new GolfBag().name(DEFAULT_NAME).clubs(DEFAULT_CLUBS);
        return golfBag;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GolfBag createUpdatedEntity(EntityManager em) {
        GolfBag golfBag = new GolfBag().name(UPDATED_NAME).clubs(UPDATED_CLUBS);
        return golfBag;
    }

    @BeforeEach
    public void initTest() {
        golfBag = createEntity(em);
    }

    @Test
    @Transactional
    void createGolfBag() throws Exception {
        int databaseSizeBeforeCreate = golfBagRepository.findAll().size();
        // Create the GolfBag
        restGolfBagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(golfBag)))
            .andExpect(status().isCreated());

        // Validate the GolfBag in the database
        List<GolfBag> golfBagList = golfBagRepository.findAll();
        assertThat(golfBagList).hasSize(databaseSizeBeforeCreate + 1);
        GolfBag testGolfBag = golfBagList.get(golfBagList.size() - 1);
        assertThat(testGolfBag.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGolfBag.getClubs()).isEqualTo(DEFAULT_CLUBS);
    }

    @Test
    @Transactional
    void createGolfBagWithExistingId() throws Exception {
        // Create the GolfBag with an existing ID
        golfBag.setId(1L);

        int databaseSizeBeforeCreate = golfBagRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGolfBagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(golfBag)))
            .andExpect(status().isBadRequest());

        // Validate the GolfBag in the database
        List<GolfBag> golfBagList = golfBagRepository.findAll();
        assertThat(golfBagList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = golfBagRepository.findAll().size();
        // set the field null
        golfBag.setName(null);

        // Create the GolfBag, which fails.

        restGolfBagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(golfBag)))
            .andExpect(status().isBadRequest());

        List<GolfBag> golfBagList = golfBagRepository.findAll();
        assertThat(golfBagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGolfBags() throws Exception {
        // Initialize the database
        golfBagRepository.saveAndFlush(golfBag);

        // Get all the golfBagList
        restGolfBagMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(golfBag.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].clubs").value(hasItem(DEFAULT_CLUBS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGolfBagsWithEagerRelationshipsIsEnabled() throws Exception {
        when(golfBagServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGolfBagMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(golfBagServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGolfBagsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(golfBagServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGolfBagMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(golfBagServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getGolfBag() throws Exception {
        // Initialize the database
        golfBagRepository.saveAndFlush(golfBag);

        // Get the golfBag
        restGolfBagMockMvc
            .perform(get(ENTITY_API_URL_ID, golfBag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(golfBag.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.clubs").value(DEFAULT_CLUBS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingGolfBag() throws Exception {
        // Get the golfBag
        restGolfBagMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGolfBag() throws Exception {
        // Initialize the database
        golfBagRepository.saveAndFlush(golfBag);

        int databaseSizeBeforeUpdate = golfBagRepository.findAll().size();

        // Update the golfBag
        GolfBag updatedGolfBag = golfBagRepository.findById(golfBag.getId()).get();
        // Disconnect from session so that the updates on updatedGolfBag are not directly saved in db
        em.detach(updatedGolfBag);
        updatedGolfBag.name(UPDATED_NAME).clubs(UPDATED_CLUBS);

        restGolfBagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGolfBag.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGolfBag))
            )
            .andExpect(status().isOk());

        // Validate the GolfBag in the database
        List<GolfBag> golfBagList = golfBagRepository.findAll();
        assertThat(golfBagList).hasSize(databaseSizeBeforeUpdate);
        GolfBag testGolfBag = golfBagList.get(golfBagList.size() - 1);
        assertThat(testGolfBag.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGolfBag.getClubs()).isEqualTo(UPDATED_CLUBS);
    }

    @Test
    @Transactional
    void putNonExistingGolfBag() throws Exception {
        int databaseSizeBeforeUpdate = golfBagRepository.findAll().size();
        golfBag.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGolfBagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, golfBag.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(golfBag))
            )
            .andExpect(status().isBadRequest());

        // Validate the GolfBag in the database
        List<GolfBag> golfBagList = golfBagRepository.findAll();
        assertThat(golfBagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGolfBag() throws Exception {
        int databaseSizeBeforeUpdate = golfBagRepository.findAll().size();
        golfBag.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGolfBagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(golfBag))
            )
            .andExpect(status().isBadRequest());

        // Validate the GolfBag in the database
        List<GolfBag> golfBagList = golfBagRepository.findAll();
        assertThat(golfBagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGolfBag() throws Exception {
        int databaseSizeBeforeUpdate = golfBagRepository.findAll().size();
        golfBag.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGolfBagMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(golfBag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GolfBag in the database
        List<GolfBag> golfBagList = golfBagRepository.findAll();
        assertThat(golfBagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGolfBagWithPatch() throws Exception {
        // Initialize the database
        golfBagRepository.saveAndFlush(golfBag);

        int databaseSizeBeforeUpdate = golfBagRepository.findAll().size();

        // Update the golfBag using partial update
        GolfBag partialUpdatedGolfBag = new GolfBag();
        partialUpdatedGolfBag.setId(golfBag.getId());

        partialUpdatedGolfBag.name(UPDATED_NAME).clubs(UPDATED_CLUBS);

        restGolfBagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGolfBag.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGolfBag))
            )
            .andExpect(status().isOk());

        // Validate the GolfBag in the database
        List<GolfBag> golfBagList = golfBagRepository.findAll();
        assertThat(golfBagList).hasSize(databaseSizeBeforeUpdate);
        GolfBag testGolfBag = golfBagList.get(golfBagList.size() - 1);
        assertThat(testGolfBag.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGolfBag.getClubs()).isEqualTo(UPDATED_CLUBS);
    }

    @Test
    @Transactional
    void fullUpdateGolfBagWithPatch() throws Exception {
        // Initialize the database
        golfBagRepository.saveAndFlush(golfBag);

        int databaseSizeBeforeUpdate = golfBagRepository.findAll().size();

        // Update the golfBag using partial update
        GolfBag partialUpdatedGolfBag = new GolfBag();
        partialUpdatedGolfBag.setId(golfBag.getId());

        partialUpdatedGolfBag.name(UPDATED_NAME).clubs(UPDATED_CLUBS);

        restGolfBagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGolfBag.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGolfBag))
            )
            .andExpect(status().isOk());

        // Validate the GolfBag in the database
        List<GolfBag> golfBagList = golfBagRepository.findAll();
        assertThat(golfBagList).hasSize(databaseSizeBeforeUpdate);
        GolfBag testGolfBag = golfBagList.get(golfBagList.size() - 1);
        assertThat(testGolfBag.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGolfBag.getClubs()).isEqualTo(UPDATED_CLUBS);
    }

    @Test
    @Transactional
    void patchNonExistingGolfBag() throws Exception {
        int databaseSizeBeforeUpdate = golfBagRepository.findAll().size();
        golfBag.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGolfBagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, golfBag.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(golfBag))
            )
            .andExpect(status().isBadRequest());

        // Validate the GolfBag in the database
        List<GolfBag> golfBagList = golfBagRepository.findAll();
        assertThat(golfBagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGolfBag() throws Exception {
        int databaseSizeBeforeUpdate = golfBagRepository.findAll().size();
        golfBag.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGolfBagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(golfBag))
            )
            .andExpect(status().isBadRequest());

        // Validate the GolfBag in the database
        List<GolfBag> golfBagList = golfBagRepository.findAll();
        assertThat(golfBagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGolfBag() throws Exception {
        int databaseSizeBeforeUpdate = golfBagRepository.findAll().size();
        golfBag.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGolfBagMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(golfBag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GolfBag in the database
        List<GolfBag> golfBagList = golfBagRepository.findAll();
        assertThat(golfBagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGolfBag() throws Exception {
        // Initialize the database
        golfBagRepository.saveAndFlush(golfBag);

        int databaseSizeBeforeDelete = golfBagRepository.findAll().size();

        // Delete the golfBag
        restGolfBagMockMvc
            .perform(delete(ENTITY_API_URL_ID, golfBag.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GolfBag> golfBagList = golfBagRepository.findAll();
        assertThat(golfBagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
