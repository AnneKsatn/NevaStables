package com.nevastables.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nevastables.web.rest.TestUtil;

public class UserVetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserVet.class);
        UserVet userVet1 = new UserVet();
        userVet1.setId(1L);
        UserVet userVet2 = new UserVet();
        userVet2.setId(userVet1.getId());
        assertThat(userVet1).isEqualTo(userVet2);
        userVet2.setId(2L);
        assertThat(userVet1).isNotEqualTo(userVet2);
        userVet1.setId(null);
        assertThat(userVet1).isNotEqualTo(userVet2);
    }
}
