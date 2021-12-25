package com.mycompany.ssi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.ssi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TBNEGTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBNEG.class);
        TBNEG tBNEG1 = new TBNEG();
        tBNEG1.setNegcod("id1");
        TBNEG tBNEG2 = new TBNEG();
        tBNEG2.setNegcod(tBNEG1.getNegcod());
        assertThat(tBNEG1).isEqualTo(tBNEG2);
        tBNEG2.setNegcod("id2");
        assertThat(tBNEG1).isNotEqualTo(tBNEG2);
        tBNEG1.setNegcod(null);
        assertThat(tBNEG1).isNotEqualTo(tBNEG2);
    }
}
