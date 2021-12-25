package com.mycompany.ssi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MFSHM.
 */
@Entity
@Table(name = "mfshm")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MFSHM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "shsts", length = 1, nullable = false)
    private String shsts;

    @NotNull
    @Column(name = "shfr", nullable = false)
    private Integer shfr;

    @NotNull
    @Column(name = "shto", nullable = false)
    private Integer shto;

    @NotNull
    @Column(name = "shjshm", nullable = false)
    private Integer shjshm;

    @NotNull
    @Column(name = "shbat", nullable = false)
    private Integer shbat;

    @NotNull
    @Column(name = "shseq", nullable = false)
    private Integer shseq;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "shref", length = 20, nullable = false)
    private String shref;

    @NotNull
    @Column(name = "shdis", nullable = false)
    private LocalDate shdis;

    @NotNull
    @Column(name = "shlmd", nullable = false)
    private LocalDate shlmd;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "shuid", length = 10, nullable = false)
    private String shuid;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mfshms", "mAPSKS", "skshdr" }, allowSetters = true)
    private MFSKS shsks;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MFSHM id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShsts() {
        return this.shsts;
    }

    public MFSHM shsts(String shsts) {
        this.setShsts(shsts);
        return this;
    }

    public void setShsts(String shsts) {
        this.shsts = shsts;
    }

    public Integer getShfr() {
        return this.shfr;
    }

    public MFSHM shfr(Integer shfr) {
        this.setShfr(shfr);
        return this;
    }

    public void setShfr(Integer shfr) {
        this.shfr = shfr;
    }

    public Integer getShto() {
        return this.shto;
    }

    public MFSHM shto(Integer shto) {
        this.setShto(shto);
        return this;
    }

    public void setShto(Integer shto) {
        this.shto = shto;
    }

    public Integer getShjshm() {
        return this.shjshm;
    }

    public MFSHM shjshm(Integer shjshm) {
        this.setShjshm(shjshm);
        return this;
    }

    public void setShjshm(Integer shjshm) {
        this.shjshm = shjshm;
    }

    public Integer getShbat() {
        return this.shbat;
    }

    public MFSHM shbat(Integer shbat) {
        this.setShbat(shbat);
        return this;
    }

    public void setShbat(Integer shbat) {
        this.shbat = shbat;
    }

    public Integer getShseq() {
        return this.shseq;
    }

    public MFSHM shseq(Integer shseq) {
        this.setShseq(shseq);
        return this;
    }

    public void setShseq(Integer shseq) {
        this.shseq = shseq;
    }

    public String getShref() {
        return this.shref;
    }

    public MFSHM shref(String shref) {
        this.setShref(shref);
        return this;
    }

    public void setShref(String shref) {
        this.shref = shref;
    }

    public LocalDate getShdis() {
        return this.shdis;
    }

    public MFSHM shdis(LocalDate shdis) {
        this.setShdis(shdis);
        return this;
    }

    public void setShdis(LocalDate shdis) {
        this.shdis = shdis;
    }

    public LocalDate getShlmd() {
        return this.shlmd;
    }

    public MFSHM shlmd(LocalDate shlmd) {
        this.setShlmd(shlmd);
        return this;
    }

    public void setShlmd(LocalDate shlmd) {
        this.shlmd = shlmd;
    }

    public String getShuid() {
        return this.shuid;
    }

    public MFSHM shuid(String shuid) {
        this.setShuid(shuid);
        return this;
    }

    public void setShuid(String shuid) {
        this.shuid = shuid;
    }

    public MFSKS getShsks() {
        return this.shsks;
    }

    public void setShsks(MFSKS mFSKS) {
        this.shsks = mFSKS;
    }

    public MFSHM shsks(MFSKS mFSKS) {
        this.setShsks(mFSKS);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MFSHM)) {
            return false;
        }
        return id != null && id.equals(((MFSHM) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MFSHM{" +
            "id=" + getId() +
            ", shsts='" + getShsts() + "'" +
            ", shfr=" + getShfr() +
            ", shto=" + getShto() +
            ", shjshm=" + getShjshm() +
            ", shbat=" + getShbat() +
            ", shseq=" + getShseq() +
            ", shref='" + getShref() + "'" +
            ", shdis='" + getShdis() + "'" +
            ", shlmd='" + getShlmd() + "'" +
            ", shuid='" + getShuid() + "'" +
            "}";
    }
}
