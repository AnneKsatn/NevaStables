package com.nevastables.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nevastables.web.rest.TestUtil;

public class StableTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stable.class);
        Stable stable1 = new Stable();
        stable1.setId(1L);
        Stable stable2 = new Stable();
        stable2.setId(stable1.getId());
        assertThat(stable1).isEqualTo(stable2);
        stable2.setId(2L);
        assertThat(stable1).isNotEqualTo(stable2);
        stable1.setId(null);
        assertThat(stable1).isNotEqualTo(stable2);
    }
}
