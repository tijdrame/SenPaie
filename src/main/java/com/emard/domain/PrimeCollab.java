package com.emard.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PrimeCollab.
 */
@Entity
@Table(name = "prime_collab")
public class PrimeCollab implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Double montant;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne(optional = false)
    @NotNull
    private Collaborateur collaborateur;

    @ManyToOne(optional = false)
    @NotNull
    private Prime prime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public PrimeCollab montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public PrimeCollab deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    public PrimeCollab collaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
        return this;
    }

    public void setCollaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
    }

    public Prime getPrime() {
        return prime;
    }

    public PrimeCollab prime(Prime prime) {
        this.prime = prime;
        return this;
    }

    public void setPrime(Prime prime) {
        this.prime = prime;
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
        PrimeCollab primeCollab = (PrimeCollab) o;
        if (primeCollab.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), primeCollab.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrimeCollab{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
