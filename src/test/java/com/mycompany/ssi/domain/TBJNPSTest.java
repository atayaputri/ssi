package com.mycompany.ssi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.ssi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TBJNPSTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBJNPS.class);
        TBJNPS tBJNPS1 = new TBJNPS();
        tBJNPS1.setJpscod("id1");
        TBJNPS tBJNPS2 = new TBJNPS();
        tBJNPS2.setJpscod(tBJNPS1.getJpscod());
        assertThat(tBJNPS1).isEqualTo(tBJNPS2);
        tBJNPS2.setJpscod("id2");
        assertThat(tBJNPS1).isNotEqualTo(tBJNPS2);
        tBJNPS1.setJpscod(null);
        assertThat(tBJNPS1).isNotEqualTo(tBJNPS2);
    }
}
