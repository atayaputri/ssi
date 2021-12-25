package com.mycompany.ssi.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TBCOMFO.
 */
@Entity
@Table(name = "tbcomfo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TBCOMFO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "costs", nullable = false)
    private String costs;

    @NotNull
    @Size(max = 10)
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
    @Column(name = "coseri", length = 15, nullable = false)
    private String coseri;

    @NotNull
    @Size(max = 30)
    @Column(name = "codir", length = 30, nullable = false)
    private String codir;

    @NotNull
    @Column(name = "colmd", nullable = false)
    private LocalDate colmd;

    @NotNull
    @Column(name = "couid", nullable = false)
    private String couid;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TBCOMFO id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCosts() {
        return this.costs;
    }

    public TBCOMFO costs(String costs) {
        this.setCosts(costs);
        return this;
    }

    public void setCosts(String costs) {
        this.costs = costs;
    }

    public String getCocode() {
        return this.cocode;
    }

    public TBCOMFO cocode(String cocode) {
        this.setCocode(cocode);
        return this;
    }

    public void setCocode(String cocode) {
        this.cocode = cocode;
    }

    public String getConam() {
        return this.conam;
    }

    public TBCOMFO conam(String conam) {
        this.setConam(conam);
        return this;
    }

    public void setConam(String conam) {
        this.conam = conam;
    }

    public String getCocbei() {
        return this.cocbei;
    }

    public TBCOMFO cocbei(String cocbei) {
        this.setCocbei(cocbei);
        return this;
    }

    public void setCocbei(String cocbei) {
        this.cocbei = cocbei;
    }

    public String getConbei() {
        return this.conbei;
    }

    public TBCOMFO conbei(String conbei) {
        this.setConbei(conbei);
        return this;
    }

    public void setConbei(String conbei) {
        this.conbei = conbei;
    }

    public String getCosat() {
        return this.cosat;
    }

    public TBCOMFO cosat(String cosat) {
        this.setCosat(cosat);
        return this;
    }

    public void setCosat(String cosat) {
        this.cosat = cosat;
    }

    public Integer getConom() {
        return this.conom;
    }

    public TBCOMFO conom(Integer conom) {
        this.setConom(conom);
        return this;
    }

    public void setConom(Integer conom) {
        this.conom = conom;
    }

    public String getCoseri() {
        return this.coseri;
    }

    public TBCOMFO coseri(String coseri) {
        this.setCoseri(coseri);
        return this;
    }

    public void setCoseri(String coseri) {
        this.coseri = coseri;
    }

    public String getCodir() {
        return this.codir;
    }

    public TBCOMFO codir(String codir) {
        this.setCodir(codir);
        return this;
    }

    public void setCodir(String codir) {
        this.codir = codir;
    }

    public LocalDate getColmd() {
        return this.colmd;
    }

    public TBCOMFO colmd(LocalDate colmd) {
        this.setColmd(colmd);
        return this;
    }

    public void setColmd(LocalDate colmd) {
        this.colmd = colmd;
    }

    public String getCouid() {
        return this.couid;
    }

    public TBCOMFO couid(String couid) {
        this.setCouid(couid);
        return this;
    }

    public void setCouid(String couid) {
        this.couid = couid;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TBCOMFO)) {
            return false;
        }
        return id != null && id.equals(((TBCOMFO) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TBCOMFO{" +
            "id=" + getId() +
            ", costs='" + getCosts() + "'" +
            ", cocode='" + getCocode() + "'" +
            ", conam='" + getConam() + "'" +
            ", cocbei='" + getCocbei() + "'" +
            ", conbei='" + getConbei() + "'" +
            ", cosat='" + getCosat() + "'" +
            ", conom=" + getConom() +
            ", coseri='" + getCoseri() + "'" +
            ", codir='" + getCodir() + "'" +
            ", colmd='" + getColmd() + "'" +
            ", couid='" + getCouid() + "'" +
            "}";
    }
}
