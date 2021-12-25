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
 * A TABJTRX.
 */
@Entity
@Table(name = "tabjtrx")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TABJTRX implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "jtsts", length = 1, nullable = false)
    private String jtsts;

    @NotNull
    @Size(min = 1, max = 1)
    @Id
    @Column(name = "jtjntx", length = 1, nullable = false)
    private String jtjntx;

    @Size(min = 1, max = 50)
    @Column(name = "jtdesc", length = 50)
    private String jtdesc;

    @Size(min = 1, max = 20)
    @Column(name = "jtsdes", length = 20)
    private String jtsdes;

    @NotNull
    @Column(name = "jtlmd", nullable = false)
    private LocalDate jtlmd;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "jtouid", length = 10, nullable = false)
    private String jtouid;

    @OneToMany(mappedBy = "fejns")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fejns" }, allowSetters = true)
    private Set<TABFEE> tABFEES = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getJtsts() {
        return this.jtsts;
    }

    public TABJTRX jtsts(String jtsts) {
        this.setJtsts(jtsts);
        return this;
    }

    public void setJtsts(String jtsts) {
        this.jtsts = jtsts;
    }

    public String getJtjntx() {
        return this.jtjntx;
    }

    public TABJTRX jtjntx(String jtjntx) {
        this.setJtjntx(jtjntx);
        return this;
    }

    public void setJtjntx(String jtjntx) {
        this.jtjntx = jtjntx;
    }

    public String getJtdesc() {
        return this.jtdesc;
    }

    public TABJTRX jtdesc(String jtdesc) {
        this.setJtdesc(jtdesc);
        return this;
    }

    public void setJtdesc(String jtdesc) {
        this.jtdesc = jtdesc;
    }

    public String getJtsdes() {
        return this.jtsdes;
    }

    public TABJTRX jtsdes(String jtsdes) {
        this.setJtsdes(jtsdes);
        return this;
    }

    public void setJtsdes(String jtsdes) {
        this.jtsdes = jtsdes;
    }

    public LocalDate getJtlmd() {
        return this.jtlmd;
    }

    public TABJTRX jtlmd(LocalDate jtlmd) {
        this.setJtlmd(jtlmd);
        return this;
    }

    public void setJtlmd(LocalDate jtlmd) {
        this.jtlmd = jtlmd;
    }

    public String getJtouid() {
        return this.jtouid;
    }

    public TABJTRX jtouid(String jtouid) {
        this.setJtouid(jtouid);
        return this;
    }

    public void setJtouid(String jtouid) {
        this.jtouid = jtouid;
    }

    public Set<TABFEE> getTABFEES() {
        return this.tABFEES;
    }

    public void setTABFEES(Set<TABFEE> tABFEES) {
        if (this.tABFEES != null) {
            this.tABFEES.forEach(i -> i.setFejns(null));
        }
        if (tABFEES != null) {
            tABFEES.forEach(i -> i.setFejns(this));
        }
        this.tABFEES = tABFEES;
    }

    public TABJTRX tABFEES(Set<TABFEE> tABFEES) {
        this.setTABFEES(tABFEES);
        return this;
    }

    public TABJTRX addTABFEE(TABFEE tABFEE) {
        this.tABFEES.add(tABFEE);
        tABFEE.setFejns(this);
        return this;
    }

    public TABJTRX removeTABFEE(TABFEE tABFEE) {
        this.tABFEES.remove(tABFEE);
        tABFEE.setFejns(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TABJTRX)) {
            return false;
        }
        return jtjntx != null && jtjntx.equals(((TABJTRX) o).jtjntx);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TABJTRX{" +
            "jtjntx=" + getJtjntx() +
            ", jtsts='" + getJtsts() + "'" +
            ", jtdesc='" + getJtdesc() + "'" +
            ", jtsdes='" + getJtsdes() + "'" +
            ", jtlmd='" + getJtlmd() + "'" +
            ", jtouid='" + getJtouid() + "'" +
            "}";
    }
}
