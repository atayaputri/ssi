package com.mycompany.ssi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.ssi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MFSHMTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MFSHM.class);
        MFSHM mFSHM1 = new MFSHM();
        mFSHM1.setId(1L);
        MFSHM mFSHM2 = new MFSHM();
        mFSHM2.setId(mFSHM1.getId());
        assertThat(mFSHM1).isEqualTo(mFSHM2);
        mFSHM2.setId(2L);
        assertThat(mFSHM1).isNotEqualTo(mFSHM2);
        mFSHM1.setId(null);
        assertThat(mFSHM1).isNotEqualTo(mFSHM2);
    }
}
