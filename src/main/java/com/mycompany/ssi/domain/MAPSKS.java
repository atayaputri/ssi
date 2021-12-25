package com.mycompany.ssi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.ssi.domain.enumeration.StatusSKS;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MAPSKS.
 */
@Entity
@Table(name = "mapsks")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MAPSKS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "msksts", nullable = false)
    private StatusSKS msksts;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mfshms", "mAPSKS", "skshdr" }, allowSetters = true)
    private MFSKS mskno;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mFSKS", "sksLosts", "sksAdds", "hdkota", "hdprov", "hdneg", "hdjnps", "hdtyps" }, allowSetters = true)
    private MFHDR mskohdr;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mFSKS", "sksLosts", "sksAdds", "hdkota", "hdprov", "hdneg", "hdjnps", "hdtyps" }, allowSetters = true)
    private MFHDR mskhdr;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MAPSKS id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusSKS getMsksts() {
        return this.msksts;
    }

    public MAPSKS msksts(StatusSKS msksts) {
        this.setMsksts(msksts);
        return this;
    }

    public void setMsksts(StatusSKS msksts) {
        this.msksts = msksts;
    }

    public MFSKS getMskno() {
        return this.mskno;
    }

    public void setMskno(MFSKS mFSKS) {
        this.mskno = mFSKS;
    }

    public MAPSKS mskno(MFSKS mFSKS) {
        this.setMskno(mFSKS);
        return this;
    }

    public MFHDR getMskohdr() {
        return this.mskohdr;
    }

    public void setMskohdr(MFHDR mFHDR) {
        this.mskohdr = mFHDR;
    }

    public MAPSKS mskohdr(MFHDR mFHDR) {
        this.setMskohdr(mFHDR);
        return this;
    }

    public MFHDR getMskhdr() {
        return this.mskhdr;
    }

    public void setMskhdr(MFHDR mFHDR) {
        this.mskhdr = mFHDR;
    }

    public MAPSKS mskhdr(MFHDR mFHDR) {
        this.setMskhdr(mFHDR);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MAPSKS)) {
            return false;
        }
        return id != null && id.equals(((MAPSKS) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MAPSKS{" +
            "id=" + getId() +
            ", msksts='" + getMsksts() + "'" +
            "}";
    }
}
