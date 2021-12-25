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
 * A TBKAB.
 */
@Entity
@Table(name = "tbkab")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TBKAB implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "kabsts", nullable = false)
    private String kabsts;

    @NotNull
    @Size(max = 4)
    @Id
    @Column(name = "kabcod", length = 4, nullable = false)
    private String kabcod;

    @NotNull
    @Size(max = 40)
    @Column(name = "kabnam", length = 40, nullable = false)
    private String kabnam;

    @NotNull
    @Column(name = "kablmd", nullable = false)
    private LocalDate kablmd;

    @NotNull
    @Column(name = "kabuid", nullable = false)
    private String kabuid;

    @OneToMany(mappedBy = "hdkota")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mFSKS", "sksLosts", "sksAdds", "hdkota", "hdprov", "hdneg", "hdjnps", "hdtyps" }, allowSetters = true)
    private Set<MFHDR> mFHDRS = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "mFHDRS", "tBKABS", "provneg" }, allowSetters = true)
    private TBPROV kabprov;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getKabsts() {
        return this.kabsts;
    }

    public TBKAB kabsts(String kabsts) {
        this.setKabsts(kabsts);
        return this;
    }

    public void setKabsts(String kabsts) {
        this.kabsts = kabsts;
    }

    public String getKabcod() {
        return this.kabcod;
    }

    public TBKAB kabcod(String kabcod) {
        this.setKabcod(kabcod);
        return this;
    }

    public void setKabcod(String kabcod) {
        this.kabcod = kabcod;
    }

    public String getKabnam() {
        return this.kabnam;
    }

    public TBKAB kabnam(String kabnam) {
        this.setKabnam(kabnam);
        return this;
    }

    public void setKabnam(String kabnam) {
        this.kabnam = kabnam;
    }

    public LocalDate getKablmd() {
        return this.kablmd;
    }

    public TBKAB kablmd(LocalDate kablmd) {
        this.setKablmd(kablmd);
        return this;
    }

    public void setKablmd(LocalDate kablmd) {
        this.kablmd = kablmd;
    }

    public String getKabuid() {
        return this.kabuid;
    }

    public TBKAB kabuid(String kabuid) {
        this.setKabuid(kabuid);
        return this;
    }

    public void setKabuid(String kabuid) {
        this.kabuid = kabuid;
    }

    public Set<MFHDR> getMFHDRS() {
        return this.mFHDRS;
    }

    public void setMFHDRS(Set<MFHDR> mFHDRS) {
        if (this.mFHDRS != null) {
            this.mFHDRS.forEach(i -> i.setHdkota(null));
        }
        if (mFHDRS != null) {
            mFHDRS.forEach(i -> i.setHdkota(this));
        }
        this.mFHDRS = mFHDRS;
    }

    public TBKAB mFHDRS(Set<MFHDR> mFHDRS) {
        this.setMFHDRS(mFHDRS);
        return this;
    }

    public TBKAB addMFHDR(MFHDR mFHDR) {
        this.mFHDRS.add(mFHDR);
        mFHDR.setHdkota(this);
        return this;
    }

    public TBKAB removeMFHDR(MFHDR mFHDR) {
        this.mFHDRS.remove(mFHDR);
        mFHDR.setHdkota(null);
        return this;
    }

    public TBPROV getKabprov() {
        return this.kabprov;
    }

    public void setKabprov(TBPROV tBPROV) {
        this.kabprov = tBPROV;
    }

    public TBKAB kabprov(TBPROV tBPROV) {
        this.setKabprov(tBPROV);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TBKAB)) {
            return false;
        }
        return kabcod != null && kabcod.equals(((TBKAB) o).kabcod);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TBKAB{" +
            "kabcod=" + getKabcod() +
            ", kabsts='" + getKabsts() + "'" +
            ", kabnam='" + getKabnam() + "'" +
            ", kablmd='" + getKablmd() + "'" +
            ", kabuid='" + getKabuid() + "'" +
            "}";
    }
}
