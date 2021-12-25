package com.mycompany.ssi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.ssi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TBPROVTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBPROV.class);
        TBPROV tBPROV1 = new TBPROV();
        tBPROV1.setProvcod("id1");
        TBPROV tBPROV2 = new TBPROV();
        tBPROV2.setProvcod(tBPROV1.getProvcod());
        assertThat(tBPROV1).isEqualTo(tBPROV2);
        tBPROV2.setProvcod("id2");
        assertThat(tBPROV1).isNotEqualTo(tBPROV2);
        tBPROV1.setProvcod(null);
        assertThat(tBPROV1).isNotEqualTo(tBPROV2);
    }
}
