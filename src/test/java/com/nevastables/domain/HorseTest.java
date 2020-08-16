package com.nevastables.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nevastables.web.rest.TestUtil;

public class HorseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Horse.class);
        Horse horse1 = new Horse();
        horse1.setId(1L);
        Horse horse2 = new Horse();
        horse2.setId(horse1.getId());
        assertThat(horse1).isEqualTo(horse2);
        horse2.setId(2L);
        assertThat(horse1).isNotEqualTo(horse2);
        horse1.setId(null);
        assertThat(horse1).isNotEqualTo(horse2);
    }
}
