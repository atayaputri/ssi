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
 * A TBJNSHM.
 */
@Entity
@Table(name = "tbjnshm")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TBJNSHM implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "jshsts", nullable = false)
    private String jshsts;

    @NotNull
    @Size(max = 1)
    @Id
    @Column(name = "jshcod", length = 1, nullable = false)
    private String jshcod;

    @NotNull
    @Size(max = 40)
    @Column(name = "jshnam", length = 40, nullable = false)
    private String jshnam;

    @NotNull
    @Column(name = "jshlmd", nullable = false)
    private LocalDate jshlmd;

    @NotNull
    @Column(name = "jshuid", nullable = false)
    private String jshuid;

    @OneToMany(mappedBy = "cojnsh")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cojnsh" }, allowSetters = true)
    private Set<TBCOMS> tBCOMS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getJshsts() {
        return this.jshsts;
    }

    public TBJNSHM jshsts(String jshsts) {
        this.setJshsts(jshsts);
        return this;
    }

    public void setJshsts(String jshsts) {
        this.jshsts = jshsts;
    }

    public String getJshcod() {
        return this.jshcod;
    }

    public TBJNSHM jshcod(String jshcod) {
        this.setJshcod(jshcod);
        return this;
    }

    public void setJshcod(String jshcod) {
        this.jshcod = jshcod;
    }

    public String getJshnam() {
        return this.jshnam;
    }

    public TBJNSHM jshnam(String jshnam) {
        this.setJshnam(jshnam);
        return this;
    }

    public void setJshnam(String jshnam) {
        this.jshnam = jshnam;
    }

    public LocalDate getJshlmd() {
        return this.jshlmd;
    }

    public TBJNSHM jshlmd(LocalDate jshlmd) {
        this.setJshlmd(jshlmd);
        return this;
    }

    public void setJshlmd(LocalDate jshlmd) {
        this.jshlmd = jshlmd;
    }

    public String getJshuid() {
        return this.jshuid;
    }

    public TBJNSHM jshuid(String jshuid) {
        this.setJshuid(jshuid);
        return this;
    }

    public void setJshuid(String jshuid) {
        this.jshuid = jshuid;
    }

    public Set<TBCOMS> getTBCOMS() {
        return this.tBCOMS;
    }

    public void setTBCOMS(Set<TBCOMS> tBCOMS) {
        if (this.tBCOMS != null) {
            this.tBCOMS.forEach(i -> i.setCojnsh(null));
        }
        if (tBCOMS != null) {
            tBCOMS.forEach(i -> i.setCojnsh(this));
        }
        this.tBCOMS = tBCOMS;
    }

    public TBJNSHM tBCOMS(Set<TBCOMS> tBCOMS) {
        this.setTBCOMS(tBCOMS);
        return this;
    }

    public TBJNSHM addTBCOMS(TBCOMS tBCOMS) {
        this.tBCOMS.add(tBCOMS);
        tBCOMS.setCojnsh(this);
        return this;
    }

    public TBJNSHM removeTBCOMS(TBCOMS tBCOMS) {
        this.tBCOMS.remove(tBCOMS);
        tBCOMS.setCojnsh(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TBJNSHM)) {
            return false;
        }
        return jshcod != null && jshcod.equals(((TBJNSHM) o).jshcod);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TBJNSHM{" +
            "jshcod=" + getJshcod() +
            ", jshsts='" + getJshsts() + "'" +
            ", jshnam='" + getJshnam() + "'" +
            ", jshlmd='" + getJshlmd() + "'" +
            ", jshuid='" + getJshuid() + "'" +
            "}";
    }
}
