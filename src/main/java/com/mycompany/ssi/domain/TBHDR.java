package com.mycompany.ssi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TBHDR.
 */
@Entity
@Table(name = "tbhdr")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TBHDR implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "thsts", nullable = false)
    private String thsts;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thno", nullable = false)
    private Long thno;

    @NotNull
    @Size(max = 20)
    @Column(name = "thsid", length = 20, nullable = false)
    private String thsid;

    @NotNull
    @Size(max = 50)
    @Column(name = "thnm_1", length = 50, nullable = false)
    private String thnm1;

    @NotNull
    @Column(name = "thjsh", nullable = false)
    private Integer thjsh;

    @NotNull
    @Column(name = "thtax", precision = 21, scale = 2, nullable = false)
    private BigDecimal thtax;

    @NotNull
    @Column(name = "thdis", nullable = false)
    private LocalDate thdis;

    @NotNull
    @Column(name = "thlmd", nullable = false)
    private LocalDate thlmd;

    @NotNull
    @Column(name = "thuid", nullable = false)
    private String thuid;

    @Column(name = "thfil_1")
    private Integer thfil1;

    @Column(name = "thfil_2")
    private Integer thfil2;

    @Size(max = 30)
    @Column(name = "thfil_3", length = 30)
    private String thfil3;

    @Size(max = 30)
    @Column(name = "thfil_4", length = 30)
    private String thfil4;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mFHDRS", "tBHDRS" }, allowSetters = true)
    private TBJNPS thjnps;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getThsts() {
        return this.thsts;
    }

    public TBHDR thsts(String thsts) {
        this.setThsts(thsts);
        return this;
    }

    public void setThsts(String thsts) {
        this.thsts = thsts;
    }

    public Long getThno() {
        return this.thno;
    }

    public TBHDR thno(Long thno) {
        this.setThno(thno);
        return this;
    }

    public void setThno(Long thno) {
        this.thno = thno;
    }

    public String getThsid() {
        return this.thsid;
    }

    public TBHDR thsid(String thsid) {
        this.setThsid(thsid);
        return this;
    }

    public void setThsid(String thsid) {
        this.thsid = thsid;
    }

    public String getThnm1() {
        return this.thnm1;
    }

    public TBHDR thnm1(String thnm1) {
        this.setThnm1(thnm1);
        return this;
    }

    public void setThnm1(String thnm1) {
        this.thnm1 = thnm1;
    }

    public Integer getThjsh() {
        return this.thjsh;
    }

    public TBHDR thjsh(Integer thjsh) {
        this.setThjsh(thjsh);
        return this;
    }

    public void setThjsh(Integer thjsh) {
        this.thjsh = thjsh;
    }

    public BigDecimal getThtax() {
        return this.thtax;
    }

    public TBHDR thtax(BigDecimal thtax) {
        this.setThtax(thtax);
        return this;
    }

    public void setThtax(BigDecimal thtax) {
        this.thtax = thtax;
    }

    public LocalDate getThdis() {
        return this.thdis;
    }

    public TBHDR thdis(LocalDate thdis) {
        this.setThdis(thdis);
        return this;
    }

    public void setThdis(LocalDate thdis) {
        this.thdis = thdis;
    }

    public LocalDate getThlmd() {
        return this.thlmd;
    }

    public TBHDR thlmd(LocalDate thlmd) {
        this.setThlmd(thlmd);
        return this;
    }

    public void setThlmd(LocalDate thlmd) {
        this.thlmd = thlmd;
    }

    public String getThuid() {
        return this.thuid;
    }

    public TBHDR thuid(String thuid) {
        this.setThuid(thuid);
        return this;
    }

    public void setThuid(String thuid) {
        this.thuid = thuid;
    }

    public Integer getThfil1() {
        return this.thfil1;
    }

    public TBHDR thfil1(Integer thfil1) {
        this.setThfil1(thfil1);
        return this;
    }

    public void setThfil1(Integer thfil1) {
        this.thfil1 = thfil1;
    }

    public Integer getThfil2() {
        return this.thfil2;
    }

    public TBHDR thfil2(Integer thfil2) {
        this.setThfil2(thfil2);
        return this;
    }

    public void setThfil2(Integer thfil2) {
        this.thfil2 = thfil2;
    }

    public String getThfil3() {
        return this.thfil3;
    }

    public TBHDR thfil3(String thfil3) {
        this.setThfil3(thfil3);
        return this;
    }

    public void setThfil3(String thfil3) {
        this.thfil3 = thfil3;
    }

    public String getThfil4() {
        return this.thfil4;
    }

    public TBHDR thfil4(String thfil4) {
        this.setThfil4(thfil4);
        return this;
    }

    public void setThfil4(String thfil4) {
        this.thfil4 = thfil4;
    }

    public TBJNPS getThjnps() {
        return this.thjnps;
    }

    public void setThjnps(TBJNPS tBJNPS) {
        this.thjnps = tBJNPS;
    }

    public TBHDR thjnps(TBJNPS tBJNPS) {
        this.setThjnps(tBJNPS);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TBHDR)) {
            return false;
        }
        return thno != null && thno.equals(((TBHDR) o).thno);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TBHDR{" +
            "thno=" + getThno() +
            ", thsts='" + getThsts() + "'" +
            ", thsid='" + getThsid() + "'" +
            ", thnm1='" + getThnm1() + "'" +
            ", thjsh=" + getThjsh() +
            ", thtax=" + getThtax() +
            ", thdis='" + getThdis() + "'" +
            ", thlmd='" + getThlmd() + "'" +
            ", thuid='" + getThuid() + "'" +
            ", thfil1=" + getThfil1() +
            ", thfil2=" + getThfil2() +
            ", thfil3='" + getThfil3() + "'" +
            ", thfil4='" + getThfil4() + "'" +
            "}";
    }
}
