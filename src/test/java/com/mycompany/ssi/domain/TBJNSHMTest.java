package com.mycompany.ssi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.ssi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TBJNSHMTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBJNSHM.class);
        TBJNSHM tBJNSHM1 = new TBJNSHM();
        tBJNSHM1.setJshcod("id1");
        TBJNSHM tBJNSHM2 = new TBJNSHM();
        tBJNSHM2.setJshcod(tBJNSHM1.getJshcod());
        assertThat(tBJNSHM1).isEqualTo(tBJNSHM2);
        tBJNSHM2.setJshcod("id2");
        assertThat(tBJNSHM1).isNotEqualTo(tBJNSHM2);
        tBJNSHM1.setJshcod(null);
        assertThat(tBJNSHM1).isNotEqualTo(tBJNSHM2);
    }
}
