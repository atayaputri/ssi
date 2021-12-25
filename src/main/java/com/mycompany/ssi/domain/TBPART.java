package com.mycompany.ssi.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TBPART.
 */
@Entity
@Table(name = "tbpart")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TBPART implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "tpasts", nullable = false)
    private String tpasts;

    @NotNull
    @Size(max = 10)
    @Id
    @Column(name = "tpacode", length = 10, nullable = false)
    private String tpacode;

    @NotNull
    @Size(max = 40)
    @Column(name = "tpanam", length = 40, nullable = false)
    private String tpanam;

    @NotNull
    @Size(max = 30)
    @Column(name = "tparek", length = 30, nullable = false)
    private String tparek;

    @NotNull
    @Column(name = "tpadis", nullable = false)
    private LocalDate tpadis;

    @NotNull
    @Column(name = "tpalmd", nullable = false)
    private LocalDate tpalmd;

    @NotNull
    @Column(name = "tpauid", nullable = false)
    private String tpauid;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getTpasts() {
        return this.tpasts;
    }

    public TBPART tpasts(String tpasts) {
        this.setTpasts(tpasts);
        return this;
    }

    public void setTpasts(String tpasts) {
        this.tpasts = tpasts;
    }

    public String getTpacode() {
        return this.tpacode;
    }

    public TBPART tpacode(String tpacode) {
        this.setTpacode(tpacode);
        return this;
    }

    public void setTpacode(String tpacode) {
        this.tpacode = tpacode;
    }

    public String getTpanam() {
        return this.tpanam;
    }

    public TBPART tpanam(String tpanam) {
        this.setTpanam(tpanam);
        return this;
    }

    public void setTpanam(String tpanam) {
        this.tpanam = tpanam;
    }

    public String getTparek() {
        return this.tparek;
    }

    public TBPART tparek(String tparek) {
        this.setTparek(tparek);
        return this;
    }

    public void setTparek(String tparek) {
        this.tparek = tparek;
    }

    public LocalDate getTpadis() {
        return this.tpadis;
    }

    public TBPART tpadis(LocalDate tpadis) {
        this.setTpadis(tpadis);
        return this;
    }

    public void setTpadis(LocalDate tpadis) {
        this.tpadis = tpadis;
    }

    public LocalDate getTpalmd() {
        return this.tpalmd;
    }

    public TBPART tpalmd(LocalDate tpalmd) {
        this.setTpalmd(tpalmd);
        return this;
    }

    public void setTpalmd(LocalDate tpalmd) {
        this.tpalmd = tpalmd;
    }

    public String getTpauid() {
        return this.tpauid;
    }

    public TBPART tpauid(String tpauid) {
        this.setTpauid(tpauid);
        return this;
    }

    public void setTpauid(String tpauid) {
        this.tpauid = tpauid;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TBPART)) {
            return false;
        }
        return tpacode != null && tpacode.equals(((TBPART) o).tpacode);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TBPART{" +
            "tpacode=" + getTpacode() +
            ", tpasts='" + getTpasts() + "'" +
            ", tpanam='" + getTpanam() + "'" +
            ", tparek='" + getTparek() + "'" +
            ", tpadis='" + getTpadis() + "'" +
            ", tpalmd='" + getTpalmd() + "'" +
            ", tpauid='" + getTpauid() + "'" +
            "}";
    }
}
