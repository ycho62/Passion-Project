package com.golfkey.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.golfkey.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClubStatsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClubStats.class);
        ClubStats clubStats1 = new ClubStats();
        clubStats1.setId(1L);
        ClubStats clubStats2 = new ClubStats();
        clubStats2.setId(clubStats1.getId());
        assertThat(clubStats1).isEqualTo(clubStats2);
        clubStats2.setId(2L);
        assertThat(clubStats1).isNotEqualTo(clubStats2);
        clubStats1.setId(null);
        assertThat(clubStats1).isNotEqualTo(clubStats2);
    }
}
