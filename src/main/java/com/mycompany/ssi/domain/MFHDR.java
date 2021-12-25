package com.mycompany.ssi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.ssi.domain.enumeration.Citizenship;
import com.mycompany.ssi.domain.enumeration.HolderGroup;
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
 * A MFHDR.
 */
@Entity
@Table(name = "mfhdr")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MFHDR implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "hdsts", nullable = false)
    private String hdsts;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hdno", nullable = false)
    private Long hdno;

    @NotNull
    @Size(max = 20)
    @Column(name = "hdsid", length = 20, nullable = false)
    private String hdsid;

    @NotNull
    @Size(max = 50)
    @Column(name = "hdnm_1", length = 50, nullable = false)
    private String hdnm1;

    @NotNull
    @Size(max = 50)
    @Column(name = "hdnm_2", length = 50, nullable = false)
    private String hdnm2;

    @NotNull
    @Size(max = 40)
    @Column(name = "hdal_1", length = 40, nullable = false)
    private String hdal1;

    @NotNull
    @Size(max = 40)
    @Column(name = "hdal_2", length = 40, nullable = false)
    private String hdal2;

    @NotNull
    @Column(name = "hdjsh", nullable = false)
    private Integer hdjsh;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hdinco", nullable = false)
    private HolderGroup hdinco;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hdkwn", nullable = false)
    private Citizenship hdkwn;

    @NotNull
    @Size(max = 20)
    @Column(name = "hdktp", length = 20, nullable = false)
    private String hdktp;

    @NotNull
    @Size(max = 20)
    @Column(name = "hdnpwp", length = 20, nullable = false)
    private String hdnpwp;

    @NotNull
    @Size(max = 20)
    @Column(name = "hdsiup", length = 20, nullable = false)
    private String hdsiup;

    @NotNull
    @Column(name = "hdtax", precision = 21, scale = 2, nullable = false)
    private BigDecimal hdtax;

    @NotNull
    @Column(name = "hddis", nullable = false)
    private LocalDate hddis;

    @NotNull
    @Column(name = "hdlmd", nullable = false)
    private LocalDate hdlmd;

    @NotNull
    @Column(name = "hduid", nullable = false)
    private String hduid;

    @OneToMany(mappedBy = "skshdr")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mfshms", "mAPSKS", "skshdr" }, allowSetters = true)
    private Set<MFSKS> mFSKS = new HashSet<>();

    @OneToMany(mappedBy = "mskohdr")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mskno", "mskohdr", "mskhdr" }, allowSetters = true)
    private Set<MAPSKS> sksLosts = new HashSet<>();

    @OneToMany(mappedBy = "mskhdr")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mskno", "mskohdr", "mskhdr" }, allowSetters = true)
    private Set<MAPSKS> sksAdds = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "mFHDRS", "kabprov" }, allowSetters = true)
    private TBKAB hdkota;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mFHDRS", "tBKABS", "provneg" }, allowSetters = true)
    private TBPROV hdprov;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mFHDRS", "tBPROVS" }, allowSetters = true)
    private TBNEG hdneg;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mFHDRS", "tBHDRS" }, allowSetters = true)
    private TBJNPS hdjnps;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mFHDRS" }, allowSetters = true)
    private TBTYPS hdtyps;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getHdsts() {
        return this.hdsts;
    }

    public MFHDR hdsts(String hdsts) {
        this.setHdsts(hdsts);
        return this;
    }

    public void setHdsts(String hdsts) {
        this.hdsts = hdsts;
    }

    public Long getHdno() {
        return this.hdno;
    }

    public MFHDR hdno(Long hdno) {
        this.setHdno(hdno);
        return this;
    }

    public void setHdno(Long hdno) {
        this.hdno = hdno;
    }

    public String getHdsid() {
        return this.hdsid;
    }

    public MFHDR hdsid(String hdsid) {
        this.setHdsid(hdsid);
        return this;
    }

    public void setHdsid(String hdsid) {
        this.hdsid = hdsid;
    }

    public String getHdnm1() {
        return this.hdnm1;
    }

    public MFHDR hdnm1(String hdnm1) {
        this.setHdnm1(hdnm1);
        return this;
    }

    public void setHdnm1(String hdnm1) {
        this.hdnm1 = hdnm1;
    }

    public String getHdnm2() {
        return this.hdnm2;
    }

    public MFHDR hdnm2(String hdnm2) {
        this.setHdnm2(hdnm2);
        return this;
    }

    public void setHdnm2(String hdnm2) {
        this.hdnm2 = hdnm2;
    }

    public String getHdal1() {
        return this.hdal1;
    }

    public MFHDR hdal1(String hdal1) {
        this.setHdal1(hdal1);
        return this;
    }

    public void setHdal1(String hdal1) {
        this.hdal1 = hdal1;
    }

    public String getHdal2() {
        return this.hdal2;
    }

    public MFHDR hdal2(String hdal2) {
        this.setHdal2(hdal2);
        return this;
    }

    public void setHdal2(String hdal2) {
        this.hdal2 = hdal2;
    }

    public Integer getHdjsh() {
        return this.hdjsh;
    }

    public MFHDR hdjsh(Integer hdjsh) {
        this.setHdjsh(hdjsh);
        return this;
    }

    public void setHdjsh(Integer hdjsh) {
        this.hdjsh = hdjsh;
    }

    public HolderGroup getHdinco() {
        return this.hdinco;
    }

    public MFHDR hdinco(HolderGroup hdinco) {
        this.setHdinco(hdinco);
        return this;
    }

    public void setHdinco(HolderGroup hdinco) {
        this.hdinco = hdinco;
    }

    public Citizenship getHdkwn() {
        return this.hdkwn;
    }

    public MFHDR hdkwn(Citizenship hdkwn) {
        this.setHdkwn(hdkwn);
        return this;
    }

    public void setHdkwn(Citizenship hdkwn) {
        this.hdkwn = hdkwn;
    }

    public String getHdktp() {
        return this.hdktp;
    }

    public MFHDR hdktp(String hdktp) {
        this.setHdktp(hdktp);
        return this;
    }

    public void setHdktp(String hdktp) {
        this.hdktp = hdktp;
    }

    public String getHdnpwp() {
        return this.hdnpwp;
    }

    public MFHDR hdnpwp(String hdnpwp) {
        this.setHdnpwp(hdnpwp);
        return this;
    }

    public void setHdnpwp(String hdnpwp) {
        this.hdnpwp = hdnpwp;
    }

    public String getHdsiup() {
        return this.hdsiup;
    }

    public MFHDR hdsiup(String hdsiup) {
        this.setHdsiup(hdsiup);
        return this;
    }

    public void setHdsiup(String hdsiup) {
        this.hdsiup = hdsiup;
    }

    public BigDecimal getHdtax() {
        return this.hdtax;
    }

    public MFHDR hdtax(BigDecimal hdtax) {
        this.setHdtax(hdtax);
        return this;
    }

    public void setHdtax(BigDecimal hdtax) {
        this.hdtax = hdtax;
    }

    public LocalDate getHddis() {
        return this.hddis;
    }

    public MFHDR hddis(LocalDate hddis) {
        this.setHddis(hddis);
        return this;
    }

    public void setHddis(LocalDate hddis) {
        this.hddis = hddis;
    }

    public LocalDate getHdlmd() {
        return this.hdlmd;
    }

    public MFHDR hdlmd(LocalDate hdlmd) {
        this.setHdlmd(hdlmd);
        return this;
    }

    public void setHdlmd(LocalDate hdlmd) {
        this.hdlmd = hdlmd;
    }

    public String getHduid() {
        return this.hduid;
    }

    public MFHDR hduid(String hduid) {
        this.setHduid(hduid);
        return this;
    }

    public void setHduid(String hduid) {
        this.hduid = hduid;
    }

    public Set<MFSKS> getMFSKS() {
        return this.mFSKS;
    }

    public void setMFSKS(Set<MFSKS> mFSKS) {
        if (this.mFSKS != null) {
            this.mFSKS.forEach(i -> i.setSkshdr(null));
        }
        if (mFSKS != null) {
            mFSKS.forEach(i -> i.setSkshdr(this));
        }
        this.mFSKS = mFSKS;
    }

    public MFHDR mFSKS(Set<MFSKS> mFSKS) {
        this.setMFSKS(mFSKS);
        return this;
    }

    public MFHDR addMFSKS(MFSKS mFSKS) {
        this.mFSKS.add(mFSKS);
        mFSKS.setSkshdr(this);
        return this;
    }

    public MFHDR removeMFSKS(MFSKS mFSKS) {
        this.mFSKS.remove(mFSKS);
        mFSKS.setSkshdr(null);
        return this;
    }

    public Set<MAPSKS> getSksLosts() {
        return this.sksLosts;
    }

    public void setSksLosts(Set<MAPSKS> mAPSKS) {
        if (this.sksLosts != null) {
            this.sksLosts.forEach(i -> i.setMskohdr(null));
        }
        if (mAPSKS != null) {
            mAPSKS.forEach(i -> i.setMskohdr(this));
        }
        this.sksLosts = mAPSKS;
    }

    public MFHDR sksLosts(Set<MAPSKS> mAPSKS) {
        this.setSksLosts(mAPSKS);
        return this;
    }

    public MFHDR addSksLost(MAPSKS mAPSKS) {
        this.sksLosts.add(mAPSKS);
        mAPSKS.setMskohdr(this);
        return this;
    }

    public MFHDR removeSksLost(MAPSKS mAPSKS) {
        this.sksLosts.remove(mAPSKS);
        mAPSKS.setMskohdr(null);
        return this;
    }

    public Set<MAPSKS> getSksAdds() {
        return this.sksAdds;
    }

    public void setSksAdds(Set<MAPSKS> mAPSKS) {
        if (this.sksAdds != null) {
            this.sksAdds.forEach(i -> i.setMskhdr(null));
        }
        if (mAPSKS != null) {
            mAPSKS.forEach(i -> i.setMskhdr(this));
        }
        this.sksAdds = mAPSKS;
    }

    public MFHDR sksAdds(Set<MAPSKS> mAPSKS) {
        this.setSksAdds(mAPSKS);
        return this;
    }

    public MFHDR addSksAdd(MAPSKS mAPSKS) {
        this.sksAdds.add(mAPSKS);
        mAPSKS.setMskhdr(this);
        return this;
    }

    public MFHDR removeSksAdd(MAPSKS mAPSKS) {
        this.sksAdds.remove(mAPSKS);
        mAPSKS.setMskhdr(null);
        return this;
    }

    public TBKAB getHdkota() {
        return this.hdkota;
    }

    public void setHdkota(TBKAB tBKAB) {
        this.hdkota = tBKAB;
    }

    public MFHDR hdkota(TBKAB tBKAB) {
        this.setHdkota(tBKAB);
        return this;
    }

    public TBPROV getHdprov() {
        return this.hdprov;
    }

    public void setHdprov(TBPROV tBPROV) {
        this.hdprov = tBPROV;
    }

    public MFHDR hdprov(TBPROV tBPROV) {
        this.setHdprov(tBPROV);
        return this;
    }

    public TBNEG getHdneg() {
        return this.hdneg;
    }

    public void setHdneg(TBNEG tBNEG) {
        this.hdneg = tBNEG;
    }

    public MFHDR hdneg(TBNEG tBNEG) {
        this.setHdneg(tBNEG);
        return this;
    }

    public TBJNPS getHdjnps() {
        return this.hdjnps;
    }

    public void setHdjnps(TBJNPS tBJNPS) {
        this.hdjnps = tBJNPS;
    }

    public MFHDR hdjnps(TBJNPS tBJNPS) {
        this.setHdjnps(tBJNPS);
        return this;
    }

    public TBTYPS getHdtyps() {
        return this.hdtyps;
    }

    public void setHdtyps(TBTYPS tBTYPS) {
        this.hdtyps = tBTYPS;
    }

    public MFHDR hdtyps(TBTYPS tBTYPS) {
        this.setHdtyps(tBTYPS);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MFHDR)) {
            return false;
        }
        return hdno != null && hdno.equals(((MFHDR) o).hdno);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MFHDR{" +
            "hdno=" + getHdno() +
            ", hdsts='" + getHdsts() + "'" +
            ", hdsid='" + getHdsid() + "'" +
            ", hdnm1='" + getHdnm1() + "'" +
            ", hdnm2='" + getHdnm2() + "'" +
            ", hdal1='" + getHdal1() + "'" +
            ", hdal2='" + getHdal2() + "'" +
            ", hdjsh=" + getHdjsh() +
            ", hdinco='" + getHdinco() + "'" +
            ", hdkwn='" + getHdkwn() + "'" +
            ", hdktp='" + getHdktp() + "'" +
            ", hdnpwp='" + getHdnpwp() + "'" +
            ", hdsiup='" + getHdsiup() + "'" +
            ", hdtax=" + getHdtax() +
            ", hddis='" + getHddis() + "'" +
            ", hdlmd='" + getHdlmd() + "'" +
            ", hduid='" + getHduid() + "'" +
            "}";
    }
}
