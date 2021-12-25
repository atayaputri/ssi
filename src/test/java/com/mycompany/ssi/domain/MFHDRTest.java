package com.mycompany.ssi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.ssi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MFHDRTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MFHDR.class);
        MFHDR mFHDR1 = new MFHDR();
        mFHDR1.setHdno(1L);
        MFHDR mFHDR2 = new MFHDR();
        mFHDR2.setHdno(mFHDR1.getHdno());
        assertThat(mFHDR1).isEqualTo(mFHDR2);
        mFHDR2.setHdno(2L);
        assertThat(mFHDR1).isNotEqualTo(mFHDR2);
        mFHDR1.setHdno(null);
        assertThat(mFHDR1).isNotEqualTo(mFHDR2);
    }
}
