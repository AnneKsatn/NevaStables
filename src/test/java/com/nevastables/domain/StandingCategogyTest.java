package com.nevastables.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nevastables.web.rest.TestUtil;

public class StandingCategogyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StandingCategogy.class);
        StandingCategogy standingCategogy1 = new StandingCategogy();
        standingCategogy1.setId(1L);
        StandingCategogy standingCategogy2 = new StandingCategogy();
        standingCategogy2.setId(standingCategogy1.getId());
        assertThat(standingCategogy1).isEqualTo(standingCategogy2);
        standingCategogy2.setId(2L);
        assertThat(standingCategogy1).isNotEqualTo(standingCategogy2);
        standingCategogy1.setId(null);
        assertThat(standingCategogy1).isNotEqualTo(standingCategogy2);
    }
}
