package com.mycompany.ssi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.ssi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TBBROKERTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBBROKER.class);
        TBBROKER tBBROKER1 = new TBBROKER();
        tBBROKER1.setBrcode("id1");
        TBBROKER tBBROKER2 = new TBBROKER();
        tBBROKER2.setBrcode(tBBROKER1.getBrcode());
        assertThat(tBBROKER1).isEqualTo(tBBROKER2);
        tBBROKER2.setBrcode("id2");
        assertThat(tBBROKER1).isNotEqualTo(tBBROKER2);
        tBBROKER1.setBrcode(null);
        assertThat(tBBROKER1).isNotEqualTo(tBBROKER2);
    }
}
