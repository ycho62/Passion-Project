package com.golf.key.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.golf.key.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GolfBagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GolfBag.class);
        GolfBag golfBag1 = new GolfBag();
        golfBag1.setId(1L);
        GolfBag golfBag2 = new GolfBag();
        golfBag2.setId(golfBag1.getId());
        assertThat(golfBag1).isEqualTo(golfBag2);
        golfBag2.setId(2L);
        assertThat(golfBag1).isNotEqualTo(golfBag2);
        golfBag1.setId(null);
        assertThat(golfBag1).isNotEqualTo(golfBag2);
    }
}
