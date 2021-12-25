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
 * A TBJNPS.
 */
@Entity
@Table(name = "tbjnps")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TBJNPS implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "jpssts", nullable = false)
    private String jpssts;

    @NotNull
    @Size(max = 1)
    @Id
    @Column(name = "jpscod", length = 1, nullable = false)
    private String jpscod;

    @NotNull
    @Size(max = 40)
    @Column(name = "jpsnam", length = 40, nullable = false)
    private String jpsnam;

    @NotNull
    @Column(name = "jpslmd", nullable = false)
    private LocalDate jpslmd;

    @NotNull
    @Column(name = "jpsuid", nullable = false)
    private String jpsuid;

    @OneToMany(mappedBy = "hdjnps")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mFSKS", "sksLosts", "sksAdds", "hdkota", "hdprov", "hdneg", "hdjnps", "hdtyps" }, allowSetters = true)
    private Set<MFHDR> mFHDRS = new HashSet<>();

    @OneToMany(mappedBy = "thjnps")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "thjnps" }, allowSetters = true)
    private Set<TBHDR> tBHDRS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getJpssts() {
        return this.jpssts;
    }

    public TBJNPS jpssts(String jpssts) {
        this.setJpssts(jpssts);
        return this;
    }

    public void setJpssts(String jpssts) {
        this.jpssts = jpssts;
    }

    public String getJpscod() {
        return this.jpscod;
    }

    public TBJNPS jpscod(String jpscod) {
        this.setJpscod(jpscod);
        return this;
    }

    public void setJpscod(String jpscod) {
        this.jpscod = jpscod;
    }

    public String getJpsnam() {
        return this.jpsnam;
    }

    public TBJNPS jpsnam(String jpsnam) {
        this.setJpsnam(jpsnam);
        return this;
    }

    public void setJpsnam(String jpsnam) {
        this.jpsnam = jpsnam;
    }

    public LocalDate getJpslmd() {
        return this.jpslmd;
    }

    public TBJNPS jpslmd(LocalDate jpslmd) {
        this.setJpslmd(jpslmd);
        return this;
    }

    public void setJpslmd(LocalDate jpslmd) {
        this.jpslmd = jpslmd;
    }

    public String getJpsuid() {
        return this.jpsuid;
    }

    public TBJNPS jpsuid(String jpsuid) {
        this.setJpsuid(jpsuid);
        return this;
    }

    public void setJpsuid(String jpsuid) {
        this.jpsuid = jpsuid;
    }

    public Set<MFHDR> getMFHDRS() {
        return this.mFHDRS;
    }

    public void setMFHDRS(Set<MFHDR> mFHDRS) {
        if (this.mFHDRS != null) {
            this.mFHDRS.forEach(i -> i.setHdjnps(null));
        }
        if (mFHDRS != null) {
            mFHDRS.forEach(i -> i.setHdjnps(this));
        }
        this.mFHDRS = mFHDRS;
    }

    public TBJNPS mFHDRS(Set<MFHDR> mFHDRS) {
        this.setMFHDRS(mFHDRS);
        return this;
    }

    public TBJNPS addMFHDR(MFHDR mFHDR) {
        this.mFHDRS.add(mFHDR);
        mFHDR.setHdjnps(this);
        return this;
    }

    public TBJNPS removeMFHDR(MFHDR mFHDR) {
        this.mFHDRS.remove(mFHDR);
        mFHDR.setHdjnps(null);
        return this;
    }

    public Set<TBHDR> getTBHDRS() {
        return this.tBHDRS;
    }

    public void setTBHDRS(Set<TBHDR> tBHDRS) {
        if (this.tBHDRS != null) {
            this.tBHDRS.forEach(i -> i.setThjnps(null));
        }
        if (tBHDRS != null) {
            tBHDRS.forEach(i -> i.setThjnps(this));
        }
        this.tBHDRS = tBHDRS;
    }

    public TBJNPS tBHDRS(Set<TBHDR> tBHDRS) {
        this.setTBHDRS(tBHDRS);
        return this;
    }

    public TBJNPS addTBHDR(TBHDR tBHDR) {
        this.tBHDRS.add(tBHDR);
        tBHDR.setThjnps(this);
        return this;
    }

    public TBJNPS removeTBHDR(TBHDR tBHDR) {
        this.tBHDRS.remove(tBHDR);
        tBHDR.setThjnps(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TBJNPS)) {
            return false;
        }
        return jpscod != null && jpscod.equals(((TBJNPS) o).jpscod);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TBJNPS{" +
            "jpscod=" + getJpscod() +
            ", jpssts='" + getJpssts() + "'" +
            ", jpsnam='" + getJpsnam() + "'" +
            ", jpslmd='" + getJpslmd() + "'" +
            ", jpsuid='" + getJpsuid() + "'" +
            "}";
    }
}
