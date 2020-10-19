package com.nevastables.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nevastables.web.rest.TestUtil;

public class UserVetInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserVetInfo.class);
        UserVetInfo userVetInfo1 = new UserVetInfo();
        userVetInfo1.setId(1L);
        UserVetInfo userVetInfo2 = new UserVetInfo();
        userVetInfo2.setId(userVetInfo1.getId());
        assertThat(userVetInfo1).isEqualTo(userVetInfo2);
        userVetInfo2.setId(2L);
        assertThat(userVetInfo1).isNotEqualTo(userVetInfo2);
        userVetInfo1.setId(null);
        assertThat(userVetInfo1).isNotEqualTo(userVetInfo2);
    }
}
