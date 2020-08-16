package com.nevastables.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nevastables.web.rest.TestUtil;

public class StableVetInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StableVetInfo.class);
        StableVetInfo stableVetInfo1 = new StableVetInfo();
        stableVetInfo1.setId(1L);
        StableVetInfo stableVetInfo2 = new StableVetInfo();
        stableVetInfo2.setId(stableVetInfo1.getId());
        assertThat(stableVetInfo1).isEqualTo(stableVetInfo2);
        stableVetInfo2.setId(2L);
        assertThat(stableVetInfo1).isNotEqualTo(stableVetInfo2);
        stableVetInfo1.setId(null);
        assertThat(stableVetInfo1).isNotEqualTo(stableVetInfo2);
    }
}
