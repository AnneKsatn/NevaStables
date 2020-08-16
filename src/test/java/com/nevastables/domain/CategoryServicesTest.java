package com.nevastables.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nevastables.web.rest.TestUtil;

public class CategoryServicesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryServices.class);
        CategoryServices categoryServices1 = new CategoryServices();
        categoryServices1.setId(1L);
        CategoryServices categoryServices2 = new CategoryServices();
        categoryServices2.setId(categoryServices1.getId());
        assertThat(categoryServices1).isEqualTo(categoryServices2);
        categoryServices2.setId(2L);
        assertThat(categoryServices1).isNotEqualTo(categoryServices2);
        categoryServices1.setId(null);
        assertThat(categoryServices1).isNotEqualTo(categoryServices2);
    }
}
