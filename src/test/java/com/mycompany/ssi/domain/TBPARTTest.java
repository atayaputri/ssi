package com.mycompany.ssi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.ssi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TBPARTTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBPART.class);
        TBPART tBPART1 = new TBPART();
        tBPART1.setTpacode("id1");
        TBPART tBPART2 = new TBPART();
        tBPART2.setTpacode(tBPART1.getTpacode());
        assertThat(tBPART1).isEqualTo(tBPART2);
        tBPART2.setTpacode("id2");
        assertThat(tBPART1).isNotEqualTo(tBPART2);
        tBPART1.setTpacode(null);
        assertThat(tBPART1).isNotEqualTo(tBPART2);
    }
}
