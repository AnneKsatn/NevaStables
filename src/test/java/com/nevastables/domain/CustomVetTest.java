package com.nevastables.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nevastables.web.rest.TestUtil;

public class CustomVetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomVet.class);
        CustomVet customVet1 = new CustomVet();
        customVet1.setId(1L);
        CustomVet customVet2 = new CustomVet();
        customVet2.setId(customVet1.getId());
        assertThat(customVet1).isEqualTo(customVet2);
        customVet2.setId(2L);
        assertThat(customVet1).isNotEqualTo(customVet2);
        customVet1.setId(null);
        assertThat(customVet1).isNotEqualTo(customVet2);
    }
}
