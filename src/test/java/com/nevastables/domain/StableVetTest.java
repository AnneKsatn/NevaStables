package com.nevastables.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nevastables.web.rest.TestUtil;

public class StableVetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StableVet.class);
        StableVet stableVet1 = new StableVet();
        stableVet1.setId(1L);
        StableVet stableVet2 = new StableVet();
        stableVet2.setId(stableVet1.getId());
        assertThat(stableVet1).isEqualTo(stableVet2);
        stableVet2.setId(2L);
        assertThat(stableVet1).isNotEqualTo(stableVet2);
        stableVet1.setId(null);
        assertThat(stableVet1).isNotEqualTo(stableVet2);
    }
}
