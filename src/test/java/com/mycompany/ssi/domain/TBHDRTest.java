package com.mycompany.ssi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.ssi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TBHDRTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBHDR.class);
        TBHDR tBHDR1 = new TBHDR();
        tBHDR1.setThno(1L);
        TBHDR tBHDR2 = new TBHDR();
        tBHDR2.setThno(tBHDR1.getThno());
        assertThat(tBHDR1).isEqualTo(tBHDR2);
        tBHDR2.setThno(2L);
        assertThat(tBHDR1).isNotEqualTo(tBHDR2);
        tBHDR1.setThno(null);
        assertThat(tBHDR1).isNotEqualTo(tBHDR2);
    }
}
