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
 * A TBTYPS.
 */
@Entity
@Table(name = "tbtyps")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TBTYPS implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "tpssts", nullable = false)
    private String tpssts;

    @NotNull
    @Size(max = 1)
    @Id
    @Column(name = "tpscod", length = 1, nullable = false)
    private String tpscod;

    @NotNull
    @Size(max = 40)
    @Column(name = "tpsnam", length = 40, nullable = false)
    private String tpsnam;

    @NotNull
    @Column(name = "tpslmd", nullable = false)
    private LocalDate tpslmd;

    @NotNull
    @Column(name = "tpsuid", nullable = false)
    private String tpsuid;

    @OneToMany(mappedBy = "hdtyps")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mFSKS", "sksLosts", "sksAdds", "hdkota", "hdprov", "hdneg", "hdjnps", "hdtyps" }, allowSetters = true)
    private Set<MFHDR> mFHDRS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getTpssts() {
        return this.tpssts;
    }

    public TBTYPS tpssts(String tpssts) {
        this.setTpssts(tpssts);
        return this;
    }

    public void setTpssts(String tpssts) {
        this.tpssts = tpssts;
    }

    public String getTpscod() {
        return this.tpscod;
    }

    public TBTYPS tpscod(String tpscod) {
        this.setTpscod(tpscod);
        return this;
    }

    public void setTpscod(String tpscod) {
        this.tpscod = tpscod;
    }

    public String getTpsnam() {
        return this.tpsnam;
    }

    public TBTYPS tpsnam(String tpsnam) {
        this.setTpsnam(tpsnam);
        return this;
    }

    public void setTpsnam(String tpsnam) {
        this.tpsnam = tpsnam;
    }

    public LocalDate getTpslmd() {
        return this.tpslmd;
    }

    public TBTYPS tpslmd(LocalDate tpslmd) {
        this.setTpslmd(tpslmd);
        return this;
    }

    public void setTpslmd(LocalDate tpslmd) {
        this.tpslmd = tpslmd;
    }

    public String getTpsuid() {
        return this.tpsuid;
    }

    public TBTYPS tpsuid(String tpsuid) {
        this.setTpsuid(tpsuid);
        return this;
    }

    public void setTpsuid(String tpsuid) {
        this.tpsuid = tpsuid;
    }

    public Set<MFHDR> getMFHDRS() {
        return this.mFHDRS;
    }

    public void setMFHDRS(Set<MFHDR> mFHDRS) {
        if (this.mFHDRS != null) {
            this.mFHDRS.forEach(i -> i.setHdtyps(null));
        }
        if (mFHDRS != null) {
            mFHDRS.forEach(i -> i.setHdtyps(this));
        }
        this.mFHDRS = mFHDRS;
    }

    public TBTYPS mFHDRS(Set<MFHDR> mFHDRS) {
        this.setMFHDRS(mFHDRS);
        return this;
    }

    public TBTYPS addMFHDR(MFHDR mFHDR) {
        this.mFHDRS.add(mFHDR);
        mFHDR.setHdtyps(this);
        return this;
    }

    public TBTYPS removeMFHDR(MFHDR mFHDR) {
        this.mFHDRS.remove(mFHDR);
        mFHDR.setHdtyps(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TBTYPS)) {
            return false;
        }
        return tpscod != null && tpscod.equals(((TBTYPS) o).tpscod);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TBTYPS{" +
            "tpscod=" + getTpscod() +
            ", tpssts='" + getTpssts() + "'" +
            ", tpsnam='" + getTpsnam() + "'" +
            ", tpslmd='" + getTpslmd() + "'" +
            ", tpsuid='" + getTpsuid() + "'" +
            "}";
    }
}
