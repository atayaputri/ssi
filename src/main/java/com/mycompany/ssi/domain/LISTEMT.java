package com.mycompany.ssi.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LISTEMT.
 */
@Entity
@Table(name = "listemt")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LISTEMT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 4)
    @Column(name = "liscode", length = 4, nullable = false)
    private String liscode;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "lisnam", length = 40, nullable = false)
    private String lisnam;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "lisdir", length = 30, nullable = false)
    private String lisdir;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LISTEMT id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLiscode() {
        return this.liscode;
    }

    public LISTEMT liscode(String liscode) {
        this.setLiscode(liscode);
        return this;
    }

    public void setLiscode(String liscode) {
        this.liscode = liscode;
    }

    public String getLisnam() {
        return this.lisnam;
    }

    public LISTEMT lisnam(String lisnam) {
        this.setLisnam(lisnam);
        return this;
    }

    public void setLisnam(String lisnam) {
        this.lisnam = lisnam;
    }

    public String getLisdir() {
        return this.lisdir;
    }

    public LISTEMT lisdir(String lisdir) {
        this.setLisdir(lisdir);
        return this;
    }

    public void setLisdir(String lisdir) {
        this.lisdir = lisdir;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LISTEMT)) {
            return false;
        }
        return id != null && id.equals(((LISTEMT) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LISTEMT{" +
            "id=" + getId() +
            ", liscode='" + getLiscode() + "'" +
            ", lisnam='" + getLisnam() + "'" +
            ", lisdir='" + getLisdir() + "'" +
            "}";
    }
}
