package com.mycompany.ssi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TBNEG.
 */
@Entity
@Table(name = "tbneg")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TBNEG implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "negsts", nullable = false)
    private String negsts;

    @NotNull
    @Size(max = 4)
    @Id
    @Column(name = "negcod", length = 4, nullable = false)
    private String negcod;

    @NotNull
    @Size(max = 40)
    @Column(name = "negnam", length = 40, nullable = false)
    private String negnam;

    @NotNull
    @Column(name = "negtax", precision = 21, scale = 2, nullable = false)
    private BigDecimal negtax;

    @NotNull
    @Column(name = "neglmd", nullable = false)
    private LocalDate neglmd;

    @NotNull
    @Column(name = "neguid", nullable = false)
    private String neguid;

    @OneToMany(mappedBy = "hdneg")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mFSKS", "sksLosts", "sksAdds", "hdkota", "hdprov", "hdneg", "hdjnps", "hdtyps" }, allowSetters = true)
    private Set<MFHDR> mFHDRS = new HashSet<>();

    @OneToMany(mappedBy = "provneg")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mFHDRS", "tBKABS", "provneg" }, allowSetters = true)
    private Set<TBPROV> tBPROVS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getNegsts() {
        return this.negsts;
    }

    public TBNEG negsts(String negsts) {
        this.setNegsts(negsts);
        return this;
    }

    public void setNegsts(String negsts) {
        this.negsts = negsts;
    }

    public String getNegcod() {
        return this.negcod;
    }

    public TBNEG negcod(String negcod) {
        this.setNegcod(negcod);
        return this;
    }

    public void setNegcod(String negcod) {
        this.negcod = negcod;
    }

    public String getNegnam() {
        return this.negnam;
    }

    public TBNEG negnam(String negnam) {
        this.setNegnam(negnam);
        return this;
    }

    public void setNegnam(String negnam) {
        this.negnam = negnam;
    }

    public BigDecimal getNegtax() {
        return this.negtax;
    }

    public TBNEG negtax(BigDecimal negtax) {
        this.setNegtax(negtax);
        return this;
    }

    public void setNegtax(BigDecimal negtax) {
        this.negtax = negtax;
    }

    public LocalDate getNeglmd() {
        return this.neglmd;
    }

    public TBNEG neglmd(LocalDate neglmd) {
        this.setNeglmd(neglmd);
        return this;
    }

    public void setNeglmd(LocalDate neglmd) {
        this.neglmd = neglmd;
    }

    public String getNeguid() {
        return this.neguid;
    }

    public TBNEG neguid(String neguid) {
        this.setNeguid(neguid);
        return this;
    }

    public void setNeguid(String neguid) {
        this.neguid = neguid;
    }

    public Set<MFHDR> getMFHDRS() {
        return this.mFHDRS;
    }

    public void setMFHDRS(Set<MFHDR> mFHDRS) {
        if (this.mFHDRS != null) {
            this.mFHDRS.forEach(i -> i.setHdneg(null));
        }
        if (mFHDRS != null) {
            mFHDRS.forEach(i -> i.setHdneg(this));
        }
        this.mFHDRS = mFHDRS;
    }

    public TBNEG mFHDRS(Set<MFHDR> mFHDRS) {
        this.setMFHDRS(mFHDRS);
        return this;
    }

    public TBNEG addMFHDR(MFHDR mFHDR) {
        this.mFHDRS.add(mFHDR);
        mFHDR.setHdneg(this);
        return this;
    }

    public TBNEG removeMFHDR(MFHDR mFHDR) {
        this.mFHDRS.remove(mFHDR);
        mFHDR.setHdneg(null);
        return this;
    }

    public Set<TBPROV> getTBPROVS() {
        return this.tBPROVS;
    }

    public void setTBPROVS(Set<TBPROV> tBPROVS) {
        if (this.tBPROVS != null) {
            this.tBPROVS.forEach(i -> i.setProvneg(null));
        }
        if (tBPROVS != null) {
            tBPROVS.forEach(i -> i.setProvneg(this));
        }
        this.tBPROVS = tBPROVS;
    }

    public TBNEG tBPROVS(Set<TBPROV> tBPROVS) {
        this.setTBPROVS(tBPROVS);
        return this;
    }

    public TBNEG addTBPROV(TBPROV tBPROV) {
        this.tBPROVS.add(tBPROV);
        tBPROV.setProvneg(this);
        return this;
    }

    public TBNEG removeTBPROV(TBPROV tBPROV) {
        this.tBPROVS.remove(tBPROV);
        tBPROV.setProvneg(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TBNEG)) {
            return false;
        }
        return negcod != null && negcod.equals(((TBNEG) o).negcod);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TBNEG{" +
            "negcod=" + getNegcod() +
            ", negsts='" + getNegsts() + "'" +
            ", negnam='" + getNegnam() + "'" +
            ", negtax=" + getNegtax() +
            ", neglmd='" + getNeglmd() + "'" +
            ", neguid='" + getNeguid() + "'" +
            "}";
    }
}
