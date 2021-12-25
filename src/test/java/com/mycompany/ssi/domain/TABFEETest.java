package com.mycompany.ssi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.ssi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TABFEETest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TABFEE.class);
        TABFEE tABFEE1 = new TABFEE();
        tABFEE1.setId(1L);
        TABFEE tABFEE2 = new TABFEE();
        tABFEE2.setId(tABFEE1.getId());
        assertThat(tABFEE1).isEqualTo(tABFEE2);
        tABFEE2.setId(2L);
        assertThat(tABFEE1).isNotEqualTo(tABFEE2);
        tABFEE1.setId(null);
        assertThat(tABFEE1).isNotEqualTo(tABFEE2);
    }
}
