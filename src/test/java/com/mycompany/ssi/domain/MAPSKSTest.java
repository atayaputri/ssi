package com.mycompany.ssi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.ssi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MAPSKSTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAPSKS.class);
        MAPSKS mAPSKS1 = new MAPSKS();
        mAPSKS1.setId(1L);
        MAPSKS mAPSKS2 = new MAPSKS();
        mAPSKS2.setId(mAPSKS1.getId());
        assertThat(mAPSKS1).isEqualTo(mAPSKS2);
        mAPSKS2.setId(2L);
        assertThat(mAPSKS1).isNotEqualTo(mAPSKS2);
        mAPSKS1.setId(null);
        assertThat(mAPSKS1).isNotEqualTo(mAPSKS2);
    }
}
