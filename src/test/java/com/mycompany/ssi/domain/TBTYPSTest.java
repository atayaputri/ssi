package com.mycompany.ssi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.ssi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TBTYPSTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBTYPS.class);
        TBTYPS tBTYPS1 = new TBTYPS();
        tBTYPS1.setTpscod("id1");
        TBTYPS tBTYPS2 = new TBTYPS();
        tBTYPS2.setTpscod(tBTYPS1.getTpscod());
        assertThat(tBTYPS1).isEqualTo(tBTYPS2);
        tBTYPS2.setTpscod("id2");
        assertThat(tBTYPS1).isNotEqualTo(tBTYPS2);
        tBTYPS1.setTpscod(null);
        assertThat(tBTYPS1).isNotEqualTo(tBTYPS2);
    }
}
