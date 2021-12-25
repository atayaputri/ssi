package com.mycompany.ssi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.ssi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TBKABTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBKAB.class);
        TBKAB tBKAB1 = new TBKAB();
        tBKAB1.setKabcod("id1");
        TBKAB tBKAB2 = new TBKAB();
        tBKAB2.setKabcod(tBKAB1.getKabcod());
        assertThat(tBKAB1).isEqualTo(tBKAB2);
        tBKAB2.setKabcod("id2");
        assertThat(tBKAB1).isNotEqualTo(tBKAB2);
        tBKAB1.setKabcod(null);
        assertThat(tBKAB1).isNotEqualTo(tBKAB2);
    }
}
