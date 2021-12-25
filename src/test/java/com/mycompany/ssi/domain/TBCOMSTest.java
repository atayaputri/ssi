package com.mycompany.ssi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.ssi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TBCOMSTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBCOMS.class);
        TBCOMS tBCOMS1 = new TBCOMS();
        tBCOMS1.setCocode("id1");
        TBCOMS tBCOMS2 = new TBCOMS();
        tBCOMS2.setCocode(tBCOMS1.getCocode());
        assertThat(tBCOMS1).isEqualTo(tBCOMS2);
        tBCOMS2.setCocode("id2");
        assertThat(tBCOMS1).isNotEqualTo(tBCOMS2);
        tBCOMS1.setCocode(null);
        assertThat(tBCOMS1).isNotEqualTo(tBCOMS2);
    }
}
