package com.emard.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Regime.
 */
@Entity
@Table(name = "regime")
public class Regime implements Serializable {

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

    @NotNull
    @Column(name = "taux_patronal", nullable = false)
    private Double tauxPatronal;

    @NotNull
    @Column(name = "taux_salarial", nullable = false)
    private Double tauxSalarial;

    @NotNull
    @Column(name = "plafond", nullable = false)
    private Double plafond;

    @Column(name = "deleted")
    private Boolean deleted;

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

    public Regime libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public Regime code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getTauxPatronal() {
        return tauxPatronal;
    }

    public Regime tauxPatronal(Double tauxPatronal) {
        this.tauxPatronal = tauxPatronal;
        return this;
    }

    public void setTauxPatronal(Double tauxPatronal) {
        this.tauxPatronal = tauxPatronal;
    }

    public Double getTauxSalarial() {
        return tauxSalarial;
    }

    public Regime tauxSalarial(Double tauxSalarial) {
        this.tauxSalarial = tauxSalarial;
        return this;
    }

    public void setTauxSalarial(Double tauxSalarial) {
        this.tauxSalarial = tauxSalarial;
    }

    public Double getPlafond() {
        return plafond;
    }

    public Regime plafond(Double plafond) {
        this.plafond = plafond;
        return this;
    }

    public void setPlafond(Double plafond) {
        this.plafond = plafond;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Regime deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
        Regime regime = (Regime) o;
        if (regime.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), regime.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Regime{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", code='" + getCode() + "'" +
            ", tauxPatronal=" + getTauxPatronal() +
            ", tauxSalarial=" + getTauxSalarial() +
            ", plafond=" + getPlafond() +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
