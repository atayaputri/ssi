package com.mycompany.ssi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.ssi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MFSKSTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MFSKS.class);
        MFSKS mFSKS1 = new MFSKS();
        mFSKS1.setSkno(1L);
        MFSKS mFSKS2 = new MFSKS();
        mFSKS2.setSkno(mFSKS1.getSkno());
        assertThat(mFSKS1).isEqualTo(mFSKS2);
        mFSKS2.setSkno(2L);
        assertThat(mFSKS1).isNotEqualTo(mFSKS2);
        mFSKS1.setSkno(null);
        assertThat(mFSKS1).isNotEqualTo(mFSKS2);
    }
}
