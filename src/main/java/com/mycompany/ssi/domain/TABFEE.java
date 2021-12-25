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
 * A TABFEE.
 */
@Entity
@Table(name = "tabfee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TABFEE implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fests", nullable = false)
    private String fests;

    @NotNull
    @Size(max = 10)
    @Column(name = "feemt", length = 10, nullable = false)
    private String feemt;

    @NotNull
    @Column(name = "femin", nullable = false)
    private Integer femin;

    @NotNull
    @Column(name = "femax", nullable = false)
    private Integer femax;

    @NotNull
    @Column(name = "fefee", nullable = false)
    private Integer fefee;

    @NotNull
    @Column(name = "fediscp", precision = 21, scale = 2, nullable = false)
    private BigDecimal fediscp;

    @NotNull
    @Column(name = "fedisc", nullable = false)
    private Integer fedisc;

    @NotNull
    @Column(name = "fetax", precision = 21, scale = 2, nullable = false)
    private BigDecimal fetax;

    @NotNull
    @Column(name = "felmd", nullable = false)
    private LocalDate felmd;

    @NotNull
    @Column(name = "feuid", nullable = false)
    private String feuid;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tABFEES" }, allowSetters = true)
    private TABJTRX fejns;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TABFEE id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFests() {
        return this.fests;
    }

    public TABFEE fests(String fests) {
        this.setFests(fests);
        return this;
    }

    public void setFests(String fests) {
        this.fests = fests;
    }

    public String getFeemt() {
        return this.feemt;
    }

    public TABFEE feemt(String feemt) {
        this.setFeemt(feemt);
        return this;
    }

    public void setFeemt(String feemt) {
        this.feemt = feemt;
    }

    public Integer getFemin() {
        return this.femin;
    }

    public TABFEE femin(Integer femin) {
        this.setFemin(femin);
        return this;
    }

    public void setFemin(Integer femin) {
        this.femin = femin;
    }

    public Integer getFemax() {
        return this.femax;
    }

    public TABFEE femax(Integer femax) {
        this.setFemax(femax);
        return this;
    }

    public void setFemax(Integer femax) {
        this.femax = femax;
    }

    public Integer getFefee() {
        return this.fefee;
    }

    public TABFEE fefee(Integer fefee) {
        this.setFefee(fefee);
        return this;
    }

    public void setFefee(Integer fefee) {
        this.fefee = fefee;
    }

    public BigDecimal getFediscp() {
        return this.fediscp;
    }

    public TABFEE fediscp(BigDecimal fediscp) {
        this.setFediscp(fediscp);
        return this;
    }

    public void setFediscp(BigDecimal fediscp) {
        this.fediscp = fediscp;
    }

    public Integer getFedisc() {
        return this.fedisc;
    }

    public TABFEE fedisc(Integer fedisc) {
        this.setFedisc(fedisc);
        return this;
    }

    public void setFedisc(Integer fedisc) {
        this.fedisc = fedisc;
    }

    public BigDecimal getFetax() {
        return this.fetax;
    }

    public TABFEE fetax(BigDecimal fetax) {
        this.setFetax(fetax);
        return this;
    }

    public void setFetax(BigDecimal fetax) {
        this.fetax = fetax;
    }

    public LocalDate getFelmd() {
        return this.felmd;
    }

    public TABFEE felmd(LocalDate felmd) {
        this.setFelmd(felmd);
        return this;
    }

    public void setFelmd(LocalDate felmd) {
        this.felmd = felmd;
    }

    public String getFeuid() {
        return this.feuid;
    }

    public TABFEE feuid(String feuid) {
        this.setFeuid(feuid);
        return this;
    }

    public void setFeuid(String feuid) {
        this.feuid = feuid;
    }

    public TABJTRX getFejns() {
        return this.fejns;
    }

    public void setFejns(TABJTRX tABJTRX) {
        this.fejns = tABJTRX;
    }

    public TABFEE fejns(TABJTRX tABJTRX) {
        this.setFejns(tABJTRX);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TABFEE)) {
            return false;
        }
        return id != null && id.equals(((TABFEE) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TABFEE{" +
            "id=" + getId() +
            ", fests='" + getFests() + "'" +
            ", feemt='" + getFeemt() + "'" +
            ", femin=" + getFemin() +
            ", femax=" + getFemax() +
            ", fefee=" + getFefee() +
            ", fediscp=" + getFediscp() +
            ", fedisc=" + getFedisc() +
            ", fetax=" + getFetax() +
            ", felmd='" + getFelmd() + "'" +
            ", feuid='" + getFeuid() + "'" +
            "}";
    }
}
