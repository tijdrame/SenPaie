package com.emard.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Exercice.
 */
@Entity
@Table(name = "exercice")
public class Exercice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "debut_exercice", nullable = false)
    private Integer debutExercice;

    @NotNull
    @Column(name = "fin_exercice", nullable = false)
    private Integer finExercice;

    @Column(name = "actif")
    private Boolean actif;

    @Column(name = "deleted")
    private Boolean deleted;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDebutExercice() {
        return debutExercice;
    }

    public Exercice debutExercice(Integer debutExercice) {
        this.debutExercice = debutExercice;
        return this;
    }

    public void setDebutExercice(Integer debutExercice) {
        this.debutExercice = debutExercice;
    }

    public Integer getFinExercice() {
        return finExercice;
    }

    public Exercice finExercice(Integer finExercice) {
        this.finExercice = finExercice;
        return this;
    }

    public void setFinExercice(Integer finExercice) {
        this.finExercice = finExercice;
    }

    public Boolean isActif() {
        return actif;
    }

    public Exercice actif(Boolean actif) {
        this.actif = actif;
        return this;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Exercice deleted(Boolean deleted) {
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
        Exercice exercice = (Exercice) o;
        if (exercice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), exercice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Exercice{" +
            "id=" + getId() +
            ", debutExercice=" + getDebutExercice() +
            ", finExercice=" + getFinExercice() +
            ", actif='" + isActif() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
