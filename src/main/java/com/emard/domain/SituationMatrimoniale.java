package com.emard.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SituationMatrimoniale.
 */
@Entity
@Table(name = "situation_matrimoniale")
public class SituationMatrimoniale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "deleted")
    private Boolean deleted;

    @NotNull
    @Column(name = "nb_parts", nullable = false)
    private Double nbParts;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public SituationMatrimoniale libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public SituationMatrimoniale code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public SituationMatrimoniale deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Double getNbParts() {
        return nbParts;
    }

    public SituationMatrimoniale nbParts(Double nbParts) {
        this.nbParts = nbParts;
        return this;
    }

    public void setNbParts(Double nbParts) {
        this.nbParts = nbParts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SituationMatrimoniale situationMatrimoniale = (SituationMatrimoniale) o;
        if (situationMatrimoniale.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), situationMatrimoniale.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SituationMatrimoniale{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", code='" + getCode() + "'" +
            ", deleted='" + isDeleted() + "'" +
            ", nbParts=" + getNbParts() +
            "}";
    }
}
