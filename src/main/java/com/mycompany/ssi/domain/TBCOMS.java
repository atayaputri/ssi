package com.mycompany.ssi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TBCOMS.
 */
@Entity
@Table(name = "tbcoms")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TBCOMS implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "costs", nullable = false)
    private String costs;

    @NotNull
    @Size(max = 10)
    @Id
    @Column(name = "cocode", length = 10, nullable = false)
    private String cocode;

    @NotNull
    @Size(max = 40)
    @Column(name = "conam", length = 40, nullable = false)
    private String conam;

    @NotNull
    @Size(max = 6)
    @Column(name = "cocbei", length = 6, nullable = false)
    private String cocbei;

    @NotNull
    @Size(max = 40)
    @Column(name = "conbei", length = 40, nullable = false)
    private String conbei;

    @NotNull
    @Size(max = 15)
    @Column(name = "cosat", length = 15, nullable = false)
    private String cosat;

    @NotNull
    @Column(name = "conom", nullable = false)
    private Integer conom;

    @NotNull
    @Size(max = 15)
    @Column(name = "coisin", length = 15, nullable = false)
    private String coisin;

    @NotNull
    @Size(max = 20)
    @Column(name = "conpwp", length = 20, nullable = false)
    private String conpwp;

    @NotNull
    @Column(name = "coseri", nullable = false)
    private String coseri;

    @NotNull
    @Column(name = "colshm", nullable = false)
    private Integer colshm;

    @NotNull
    @Column(name = "colsks", nullable = false)
    private Integer colsks;

    @NotNull
    @Column(name = "cotshm", nullable = false)
    private Integer cotshm;

    @NotNull
    @Column(name = "codshm", nullable = false)
    private Integer codshm;

    @Size(max = 100)
    @Column(name = "conote_1", length = 100)
    private String conote1;

    @Size(max = 100)
    @Column(name = "conote_2", length = 100)
    private String conote2;

    @Size(max = 100)
    @Column(name = "conote_3", length = 100)
    private String conote3;

    @NotNull
    @Column(name = "coskps", nullable = false)
    private Integer coskps;

    @NotNull
    @Column(name = "cothld", nullable = false)
    private Integer cothld;

    @Size(max = 30)
    @Column(name = "codir_1", length = 30)
    private String codir1;

    @Size(max = 30)
    @Column(name = "codir_2", length = 30)
    private String codir2;

    @Size(max = 30)
    @Column(name = "codir_3", length = 30)
    private String codir3;

    @Size(max = 30)
    @Column(name = "codir_4", length = 30)
    private String codir4;

    @Size(max = 30)
    @Column(name = "codir_5", length = 30)
    private String codir5;

    @NotNull
    @Column(name = "colmd", nullable = false)
    private LocalDate colmd;

    @NotNull
    @Column(name = "couid", nullable = false)
    private String couid;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tBCOMS" }, allowSetters = true)
    private TBJNSHM cojnsh;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getCosts() {
        return this.costs;
    }

    public TBCOMS costs(String costs) {
        this.setCosts(costs);
        return this;
    }

    public void setCosts(String costs) {
        this.costs = costs;
    }

    public String getCocode() {
        return this.cocode;
    }

    public TBCOMS cocode(String cocode) {
        this.setCocode(cocode);
        return this;
    }

    public void setCocode(String cocode) {
        this.cocode = cocode;
    }

    public String getConam() {
        return this.conam;
    }

    public TBCOMS conam(String conam) {
        this.setConam(conam);
        return this;
    }

    public void setConam(String conam) {
        this.conam = conam;
    }

    public String getCocbei() {
        return this.cocbei;
    }

    public TBCOMS cocbei(String cocbei) {
        this.setCocbei(cocbei);
        return this;
    }

    public void setCocbei(String cocbei) {
        this.cocbei = cocbei;
    }

    public String getConbei() {
        return this.conbei;
    }

    public TBCOMS conbei(String conbei) {
        this.setConbei(conbei);
        return this;
    }

    public void setConbei(String conbei) {
        this.conbei = conbei;
    }

    public String getCosat() {
        return this.cosat;
    }

    public TBCOMS cosat(String cosat) {
        this.setCosat(cosat);
        return this;
    }

    public void setCosat(String cosat) {
        this.cosat = cosat;
    }

    public Integer getConom() {
        return this.conom;
    }

    public TBCOMS conom(Integer conom) {
        this.setConom(conom);
        return this;
    }

    public void setConom(Integer conom) {
        this.conom = conom;
    }

    public String getCoisin() {
        return this.coisin;
    }

    public TBCOMS coisin(String coisin) {
        this.setCoisin(coisin);
        return this;
    }

    public void setCoisin(String coisin) {
        this.coisin = coisin;
    }

    public String getConpwp() {
        return this.conpwp;
    }

    public TBCOMS conpwp(String conpwp) {
        this.setConpwp(conpwp);
        return this;
    }

    public void setConpwp(String conpwp) {
        this.conpwp = conpwp;
    }

    public String getCoseri() {
        return this.coseri;
    }

    public TBCOMS coseri(String coseri) {
        this.setCoseri(coseri);
        return this;
    }

    public void setCoseri(String coseri) {
        this.coseri = coseri;
    }

    public Integer getColshm() {
        return this.colshm;
    }

    public TBCOMS colshm(Integer colshm) {
        this.setColshm(colshm);
        return this;
    }

    public void setColshm(Integer colshm) {
        this.colshm = colshm;
    }

    public Integer getColsks() {
        return this.colsks;
    }

    public TBCOMS colsks(Integer colsks) {
        this.setColsks(colsks);
        return this;
    }

    public void setColsks(Integer colsks) {
        this.colsks = colsks;
    }

    public Integer getCotshm() {
        return this.cotshm;
    }

    public TBCOMS cotshm(Integer cotshm) {
        this.setCotshm(cotshm);
        return this;
    }

    public void setCotshm(Integer cotshm) {
        this.cotshm = cotshm;
    }

    public Integer getCodshm() {
        return this.codshm;
    }

    public TBCOMS codshm(Integer codshm) {
        this.setCodshm(codshm);
        return this;
    }

    public void setCodshm(Integer codshm) {
        this.codshm = codshm;
    }

    public String getConote1() {
        return this.conote1;
    }

    public TBCOMS conote1(String conote1) {
        this.setConote1(conote1);
        return this;
    }

    public void setConote1(String conote1) {
        this.conote1 = conote1;
    }

    public String getConote2() {
        return this.conote2;
    }

    public TBCOMS conote2(String conote2) {
        this.setConote2(conote2);
        return this;
    }

    public void setConote2(String conote2) {
        this.conote2 = conote2;
    }

    public String getConote3() {
        return this.conote3;
    }

    public TBCOMS conote3(String conote3) {
        this.setConote3(conote3);
        return this;
    }

    public void setConote3(String conote3) {
        this.conote3 = conote3;
    }

    public Integer getCoskps() {
        return this.coskps;
    }

    public TBCOMS coskps(Integer coskps) {
        this.setCoskps(coskps);
        return this;
    }

    public void setCoskps(Integer coskps) {
        this.coskps = coskps;
    }

    public Integer getCothld() {
        return this.cothld;
    }

    public TBCOMS cothld(Integer cothld) {
        this.setCothld(cothld);
        return this;
    }

    public void setCothld(Integer cothld) {
        this.cothld = cothld;
    }

    public String getCodir1() {
        return this.codir1;
    }

    public TBCOMS codir1(String codir1) {
        this.setCodir1(codir1);
        return this;
    }

    public void setCodir1(String codir1) {
        this.codir1 = codir1;
    }

    public String getCodir2() {
        return this.codir2;
    }

    public TBCOMS codir2(String codir2) {
        this.setCodir2(codir2);
        return this;
    }

    public void setCodir2(String codir2) {
        this.codir2 = codir2;
    }

    public String getCodir3() {
        return this.codir3;
    }

    public TBCOMS codir3(String codir3) {
        this.setCodir3(codir3);
        return this;
    }

    public void setCodir3(String codir3) {
        this.codir3 = codir3;
    }

    public String getCodir4() {
        return this.codir4;
    }

    public TBCOMS codir4(String codir4) {
        this.setCodir4(codir4);
        return this;
    }

    public void setCodir4(String codir4) {
        this.codir4 = codir4;
    }

    public String getCodir5() {
        return this.codir5;
    }

    public TBCOMS codir5(String codir5) {
        this.setCodir5(codir5);
        return this;
    }

    public void setCodir5(String codir5) {
        this.codir5 = codir5;
    }

    public LocalDate getColmd() {
        return this.colmd;
    }

    public TBCOMS colmd(LocalDate colmd) {
        this.setColmd(colmd);
        return this;
    }

    public void setColmd(LocalDate colmd) {
        this.colmd = colmd;
    }

    public String getCouid() {
        return this.couid;
    }

    public TBCOMS couid(String couid) {
        this.setCouid(couid);
        return this;
    }

    public void setCouid(String couid) {
        this.couid = couid;
    }

    public TBJNSHM getCojnsh() {
        return this.cojnsh;
    }

    public void setCojnsh(TBJNSHM tBJNSHM) {
        this.cojnsh = tBJNSHM;
    }

    public TBCOMS cojnsh(TBJNSHM tBJNSHM) {
        this.setCojnsh(tBJNSHM);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TBCOMS)) {
            return false;
        }
        return cocode != null && cocode.equals(((TBCOMS) o).cocode);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TBCOMS{" +
            "cocode=" + getCocode() +
            ", costs='" + getCosts() + "'" +
            ", conam='" + getConam() + "'" +
            ", cocbei='" + getCocbei() + "'" +
            ", conbei='" + getConbei() + "'" +
            ", cosat='" + getCosat() + "'" +
            ", conom=" + getConom() +
            ", coisin='" + getCoisin() + "'" +
            ", conpwp='" + getConpwp() + "'" +
            ", coseri='" + getCoseri() + "'" +
            ", colshm=" + getColshm() +
            ", colsks=" + getColsks() +
            ", cotshm=" + getCotshm() +
            ", codshm=" + getCodshm() +
            ", conote1='" + getConote1() + "'" +
            ", conote2='" + getConote2() + "'" +
            ", conote3='" + getConote3() + "'" +
            ", coskps=" + getCoskps() +
            ", cothld=" + getCothld() +
            ", codir1='" + getCodir1() + "'" +
            ", codir2='" + getCodir2() + "'" +
            ", codir3='" + getCodir3() + "'" +
            ", codir4='" + getCodir4() + "'" +
            ", codir5='" + getCodir5() + "'" +
            ", colmd='" + getColmd() + "'" +
            ", couid='" + getCouid() + "'" +
            "}";
    }
}
