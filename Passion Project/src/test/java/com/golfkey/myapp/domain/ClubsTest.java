package com.golfkey.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.golfkey.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClubsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clubs.class);
        Clubs clubs1 = new Clubs();
        clubs1.setId(1L);
        Clubs clubs2 = new Clubs();
        clubs2.setId(clubs1.getId());
        assertThat(clubs1).isEqualTo(clubs2);
        clubs2.setId(2L);
        assertThat(clubs1).isNotEqualTo(clubs2);
        clubs1.setId(null);
        assertThat(clubs1).isNotEqualTo(clubs2);
    }
}
