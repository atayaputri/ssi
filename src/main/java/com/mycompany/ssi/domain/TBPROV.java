package com.mycompany.ssi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TBPROV.
 */
@Entity
@Table(name = "tbprov")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TBPROV implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "provsts", nullable = false)
    private String provsts;

    @NotNull
    @Size(max = 4)
    @Id
    @Column(name = "provcod", length = 4, nullable = false)
    private String provcod;

    @NotNull
    @Size(max = 40)
    @Column(name = "provnam", length = 40, nullable = false)
    private String provnam;

    @NotNull
    @Column(name = "provlmd", nullable = false)
    private LocalDate provlmd;

    @NotNull
    @Column(name = "provuid", nullable = false)
    private String provuid;

    @OneToMany(mappedBy = "hdprov")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mFSKS", "sksLosts", "sksAdds", "hdkota", "hdprov", "hdneg", "hdjnps", "hdtyps" }, allowSetters = true)
    private Set<MFHDR> mFHDRS = new HashSet<>();

    @OneToMany(mappedBy = "kabprov")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mFHDRS", "kabprov" }, allowSetters = true)
    private Set<TBKAB> tBKABS = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "mFHDRS", "tBPROVS" }, allowSetters = true)
    private TBNEG provneg;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getProvsts() {
        return this.provsts;
    }

    public TBPROV provsts(String provsts) {
        this.setProvsts(provsts);
        return this;
    }

    public void setProvsts(String provsts) {
        this.provsts = provsts;
    }

    public String getProvcod() {
        return this.provcod;
    }

    public TBPROV provcod(String provcod) {
        this.setProvcod(provcod);
        return this;
    }

    public void setProvcod(String provcod) {
        this.provcod = provcod;
    }

    public String getProvnam() {
        return this.provnam;
    }

    public TBPROV provnam(String provnam) {
        this.setProvnam(provnam);
        return this;
    }

    public void setProvnam(String provnam) {
        this.provnam = provnam;
    }

    public LocalDate getProvlmd() {
        return this.provlmd;
    }

    public TBPROV provlmd(LocalDate provlmd) {
        this.setProvlmd(provlmd);
        return this;
    }

    public void setProvlmd(LocalDate provlmd) {
        this.provlmd = provlmd;
    }

    public String getProvuid() {
        return this.provuid;
    }

    public TBPROV provuid(String provuid) {
        this.setProvuid(provuid);
        return this;
    }

    public void setProvuid(String provuid) {
        this.provuid = provuid;
    }

    public Set<MFHDR> getMFHDRS() {
        return this.mFHDRS;
    }

    public void setMFHDRS(Set<MFHDR> mFHDRS) {
        if (this.mFHDRS != null) {
            this.mFHDRS.forEach(i -> i.setHdprov(null));
        }
        if (mFHDRS != null) {
            mFHDRS.forEach(i -> i.setHdprov(this));
        }
        this.mFHDRS = mFHDRS;
    }

    public TBPROV mFHDRS(Set<MFHDR> mFHDRS) {
        this.setMFHDRS(mFHDRS);
        return this;
    }

    public TBPROV addMFHDR(MFHDR mFHDR) {
        this.mFHDRS.add(mFHDR);
        mFHDR.setHdprov(this);
        return this;
    }

    public TBPROV removeMFHDR(MFHDR mFHDR) {
        this.mFHDRS.remove(mFHDR);
        mFHDR.setHdprov(null);
        return this;
    }

    public Set<TBKAB> getTBKABS() {
        return this.tBKABS;
    }

    public void setTBKABS(Set<TBKAB> tBKABS) {
        if (this.tBKABS != null) {
            this.tBKABS.forEach(i -> i.setKabprov(null));
        }
        if (tBKABS != null) {
            tBKABS.forEach(i -> i.setKabprov(this));
        }
        this.tBKABS = tBKABS;
    }

    public TBPROV tBKABS(Set<TBKAB> tBKABS) {
        this.setTBKABS(tBKABS);
        return this;
    }

    public TBPROV addTBKAB(TBKAB tBKAB) {
        this.tBKABS.add(tBKAB);
        tBKAB.setKabprov(this);
        return this;
    }

    public TBPROV removeTBKAB(TBKAB tBKAB) {
        this.tBKABS.remove(tBKAB);
        tBKAB.setKabprov(null);
        return this;
    }

    public TBNEG getProvneg() {
        return this.provneg;
    }

    public void setProvneg(TBNEG tBNEG) {
        this.provneg = tBNEG;
    }

    public TBPROV provneg(TBNEG tBNEG) {
        this.setProvneg(tBNEG);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TBPROV)) {
            return false;
        }
        return provcod != null && provcod.equals(((TBPROV) o).provcod);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TBPROV{" +
            "provcod=" + getProvcod() +
            ", provsts='" + getProvsts() + "'" +
            ", provnam='" + getProvnam() + "'" +
            ", provlmd='" + getProvlmd() + "'" +
            ", provuid='" + getProvuid() + "'" +
            "}";
    }
}
