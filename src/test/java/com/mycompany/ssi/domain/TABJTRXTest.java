package com.mycompany.ssi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.ssi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TABJTRXTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TABJTRX.class);
        TABJTRX tABJTRX1 = new TABJTRX();
        tABJTRX1.setJtjntx("id1");
        TABJTRX tABJTRX2 = new TABJTRX();
        tABJTRX2.setJtjntx(tABJTRX1.getJtjntx());
        assertThat(tABJTRX1).isEqualTo(tABJTRX2);
        tABJTRX2.setJtjntx("id2");
        assertThat(tABJTRX1).isNotEqualTo(tABJTRX2);
        tABJTRX1.setJtjntx(null);
        assertThat(tABJTRX1).isNotEqualTo(tABJTRX2);
    }
}
