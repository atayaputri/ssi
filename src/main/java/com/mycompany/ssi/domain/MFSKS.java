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
 * A MFSKS.
 */
@Entity
@Table(name = "mfsks")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MFSKS implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "sksts", length = 1, nullable = false)
    private String sksts;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skno", nullable = false)
    private Long skno;

    @NotNull
    @Column(name = "skjsh", nullable = false)
    private Integer skjsh;

    @NotNull
    @Column(name = "skbat", nullable = false)
    private Integer skbat;

    @NotNull
    @Column(name = "skseq", nullable = false)
    private Integer skseq;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "skref", length = 20, nullable = false)
    private String skref;

    @NotNull
    @Column(name = "skdis", nullable = false)
    private LocalDate skdis;

    @NotNull
    @Column(name = "sklmd", nullable = false)
    private LocalDate sklmd;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "skuid", length = 10, nullable = false)
    private String skuid;

    @Column(name = "skfil_1")
    private Integer skfil1;

    @Column(name = "skfil_2")
    private Integer skfil2;

    @Size(min = 1, max = 30)
    @Column(name = "skfil_3", length = 30)
    private String skfil3;

    @Size(min = 1, max = 30)
    @Column(name = "skfil_4", length = 30)
    private String skfil4;

    @OneToMany(mappedBy = "shsks")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "shsks" }, allowSetters = true)
    private Set<MFSHM> mfshms = new HashSet<>();

    @OneToMany(mappedBy = "mskno")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mskno", "mskohdr", "mskhdr" }, allowSetters = true)
    private Set<MAPSKS> mAPSKS = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "mFSKS", "sksLosts", "sksAdds", "hdkota", "hdprov", "hdneg", "hdjnps", "hdtyps" }, allowSetters = true)
    private MFHDR skshdr;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getSksts() {
        return this.sksts;
    }

    public MFSKS sksts(String sksts) {
        this.setSksts(sksts);
        return this;
    }

    public void setSksts(String sksts) {
        this.sksts = sksts;
    }

    public Long getSkno() {
        return this.skno;
    }

    public MFSKS skno(Long skno) {
        this.setSkno(skno);
        return this;
    }

    public void setSkno(Long skno) {
        this.skno = skno;
    }

    public Integer getSkjsh() {
        return this.skjsh;
    }

    public MFSKS skjsh(Integer skjsh) {
        this.setSkjsh(skjsh);
        return this;
    }

    public void setSkjsh(Integer skjsh) {
        this.skjsh = skjsh;
    }

    public Integer getSkbat() {
        return this.skbat;
    }

    public MFSKS skbat(Integer skbat) {
        this.setSkbat(skbat);
        return this;
    }

    public void setSkbat(Integer skbat) {
        this.skbat = skbat;
    }

    public Integer getSkseq() {
        return this.skseq;
    }

    public MFSKS skseq(Integer skseq) {
        this.setSkseq(skseq);
        return this;
    }

    public void setSkseq(Integer skseq) {
        this.skseq = skseq;
    }

    public String getSkref() {
        return this.skref;
    }

    public MFSKS skref(String skref) {
        this.setSkref(skref);
        return this;
    }

    public void setSkref(String skref) {
        this.skref = skref;
    }

    public LocalDate getSkdis() {
        return this.skdis;
    }

    public MFSKS skdis(LocalDate skdis) {
        this.setSkdis(skdis);
        return this;
    }

    public void setSkdis(LocalDate skdis) {
        this.skdis = skdis;
    }

    public LocalDate getSklmd() {
        return this.sklmd;
    }

    public MFSKS sklmd(LocalDate sklmd) {
        this.setSklmd(sklmd);
        return this;
    }

    public void setSklmd(LocalDate sklmd) {
        this.sklmd = sklmd;
    }

    public String getSkuid() {
        return this.skuid;
    }

    public MFSKS skuid(String skuid) {
        this.setSkuid(skuid);
        return this;
    }

    public void setSkuid(String skuid) {
        this.skuid = skuid;
    }

    public Integer getSkfil1() {
        return this.skfil1;
    }

    public MFSKS skfil1(Integer skfil1) {
        this.setSkfil1(skfil1);
        return this;
    }

    public void setSkfil1(Integer skfil1) {
        this.skfil1 = skfil1;
    }

    public Integer getSkfil2() {
        return this.skfil2;
    }

    public MFSKS skfil2(Integer skfil2) {
        this.setSkfil2(skfil2);
        return this;
    }

    public void setSkfil2(Integer skfil2) {
        this.skfil2 = skfil2;
    }

    public String getSkfil3() {
        return this.skfil3;
    }

    public MFSKS skfil3(String skfil3) {
        this.setSkfil3(skfil3);
        return this;
    }

    public void setSkfil3(String skfil3) {
        this.skfil3 = skfil3;
    }

    public String getSkfil4() {
        return this.skfil4;
    }

    public MFSKS skfil4(String skfil4) {
        this.setSkfil4(skfil4);
        return this;
    }

    public void setSkfil4(String skfil4) {
        this.skfil4 = skfil4;
    }

    public Set<MFSHM> getMfshms() {
        return this.mfshms;
    }

    public void setMfshms(Set<MFSHM> mFSHMS) {
        if (this.mfshms != null) {
            this.mfshms.forEach(i -> i.setShsks(null));
        }
        if (mFSHMS != null) {
            mFSHMS.forEach(i -> i.setShsks(this));
        }
        this.mfshms = mFSHMS;
    }

    public MFSKS mfshms(Set<MFSHM> mFSHMS) {
        this.setMfshms(mFSHMS);
        return this;
    }

    public MFSKS addMfshm(MFSHM mFSHM) {
        this.mfshms.add(mFSHM);
        mFSHM.setShsks(this);
        return this;
    }

    public MFSKS removeMfshm(MFSHM mFSHM) {
        this.mfshms.remove(mFSHM);
        mFSHM.setShsks(null);
        return this;
    }

    public Set<MAPSKS> getMAPSKS() {
        return this.mAPSKS;
    }

    public void setMAPSKS(Set<MAPSKS> mAPSKS) {
        if (this.mAPSKS != null) {
            this.mAPSKS.forEach(i -> i.setMskno(null));
        }
        if (mAPSKS != null) {
            mAPSKS.forEach(i -> i.setMskno(this));
        }
        this.mAPSKS = mAPSKS;
    }

    public MFSKS mAPSKS(Set<MAPSKS> mAPSKS) {
        this.setMAPSKS(mAPSKS);
        return this;
    }

    public MFSKS addMAPSKS(MAPSKS mAPSKS) {
        this.mAPSKS.add(mAPSKS);
        mAPSKS.setMskno(this);
        return this;
    }

    public MFSKS removeMAPSKS(MAPSKS mAPSKS) {
        this.mAPSKS.remove(mAPSKS);
        mAPSKS.setMskno(null);
        return this;
    }

    public MFHDR getSkshdr() {
        return this.skshdr;
    }

    public void setSkshdr(MFHDR mFHDR) {
        this.skshdr = mFHDR;
    }

    public MFSKS skshdr(MFHDR mFHDR) {
        this.setSkshdr(mFHDR);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MFSKS)) {
            return false;
        }
        return skno != null && skno.equals(((MFSKS) o).skno);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MFSKS{" +
            "skno=" + getSkno() +
            ", sksts='" + getSksts() + "'" +
            ", skjsh=" + getSkjsh() +
            ", skbat=" + getSkbat() +
            ", skseq=" + getSkseq() +
            ", skref='" + getSkref() + "'" +
            ", skdis='" + getSkdis() + "'" +
            ", sklmd='" + getSklmd() + "'" +
            ", skuid='" + getSkuid() + "'" +
            ", skfil1=" + getSkfil1() +
            ", skfil2=" + getSkfil2() +
            ", skfil3='" + getSkfil3() + "'" +
            ", skfil4='" + getSkfil4() + "'" +
            "}";
    }
}
