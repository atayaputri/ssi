package com.mycompany.ssi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.ssi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TBCOMFOTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBCOMFO.class);
        TBCOMFO tBCOMFO1 = new TBCOMFO();
        tBCOMFO1.setId(1L);
        TBCOMFO tBCOMFO2 = new TBCOMFO();
        tBCOMFO2.setId(tBCOMFO1.getId());
        assertThat(tBCOMFO1).isEqualTo(tBCOMFO2);
        tBCOMFO2.setId(2L);
        assertThat(tBCOMFO1).isNotEqualTo(tBCOMFO2);
        tBCOMFO1.setId(null);
        assertThat(tBCOMFO1).isNotEqualTo(tBCOMFO2);
    }
}
