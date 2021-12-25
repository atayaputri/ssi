package com.mycompany.ssi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.ssi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LISTEMTTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LISTEMT.class);
        LISTEMT lISTEMT1 = new LISTEMT();
        lISTEMT1.setId(1L);
        LISTEMT lISTEMT2 = new LISTEMT();
        lISTEMT2.setId(lISTEMT1.getId());
        assertThat(lISTEMT1).isEqualTo(lISTEMT2);
        lISTEMT2.setId(2L);
        assertThat(lISTEMT1).isNotEqualTo(lISTEMT2);
        lISTEMT1.setId(null);
        assertThat(lISTEMT1).isNotEqualTo(lISTEMT2);
    }
}
