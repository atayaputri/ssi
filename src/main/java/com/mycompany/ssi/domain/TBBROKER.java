package com.mycompany.ssi.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TBBROKER.
 */
@Entity
@Table(name = "tbbroker")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TBBROKER implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "brsts", nullable = false)
    private String brsts;

    @NotNull
    @Size(max = 2)
    @Id
    @Column(name = "brcode", length = 2, nullable = false)
    private String brcode;

    @NotNull
    @Size(max = 40)
    @Column(name = "brnam", length = 40, nullable = false)
    private String brnam;

    @NotNull
    @Column(name = "brlmd", nullable = false)
    private LocalDate brlmd;

    @NotNull
    @Column(name = "bruid", nullable = false)
    private String bruid;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getBrsts() {
        return this.brsts;
    }

    public TBBROKER brsts(String brsts) {
        this.setBrsts(brsts);
        return this;
    }

    public void setBrsts(String brsts) {
        this.brsts = brsts;
    }

    public String getBrcode() {
        return this.brcode;
    }

    public TBBROKER brcode(String brcode) {
        this.setBrcode(brcode);
        return this;
    }

    public void setBrcode(String brcode) {
        this.brcode = brcode;
    }

    public String getBrnam() {
        return this.brnam;
    }

    public TBBROKER brnam(String brnam) {
        this.setBrnam(brnam);
        return this;
    }

    public void setBrnam(String brnam) {
        this.brnam = brnam;
    }

    public LocalDate getBrlmd() {
        return this.brlmd;
    }

    public TBBROKER brlmd(LocalDate brlmd) {
        this.setBrlmd(brlmd);
        return this;
    }

    public void setBrlmd(LocalDate brlmd) {
        this.brlmd = brlmd;
    }

    public String getBruid() {
        return this.bruid;
    }

    public TBBROKER bruid(String bruid) {
        this.setBruid(bruid);
        return this;
    }

    public void setBruid(String bruid) {
        this.bruid = bruid;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TBBROKER)) {
            return false;
        }
        return brcode != null && brcode.equals(((TBBROKER) o).brcode);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TBBROKER{" +
            "brcode=" + getBrcode() +
            ", brsts='" + getBrsts() + "'" +
            ", brnam='" + getBrnam() + "'" +
            ", brlmd='" + getBrlmd() + "'" +
            ", bruid='" + getBruid() + "'" +
            "}";
    }
}
