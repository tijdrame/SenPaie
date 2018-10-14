package com.emard.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Pret.
 */
@Entity
@Table(name = "pret")
public class Pret implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "nb_remboursement", nullable = false)
    private Integer nbRemboursement;

    @NotNull
    @Column(name = "date_pret", nullable = false)
    private LocalDate datePret;

    @NotNull
    @Column(name = "date_debut_remboursement", nullable = false)
    private LocalDate dateDebutRemboursement;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    private User userCreate;

    @ManyToOne
    private User userUpdate;

    @ManyToOne
    private User userDeleted;

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

    public Pret libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getNbRemboursement() {
        return nbRemboursement;
    }

    public Pret nbRemboursement(Integer nbRemboursement) {
        this.nbRemboursement = nbRemboursement;
        return this;
    }

    public void setNbRemboursement(Integer nbRemboursement) {
        this.nbRemboursement = nbRemboursement;
    }

    public LocalDate getDatePret() {
        return datePret;
    }

    public Pret datePret(LocalDate datePret) {
        this.datePret = datePret;
        return this;
    }

    public void setDatePret(LocalDate datePret) {
        this.datePret = datePret;
    }

    public LocalDate getDateDebutRemboursement() {
        return dateDebutRemboursement;
    }

    public Pret dateDebutRemboursement(LocalDate dateDebutRemboursement) {
        this.dateDebutRemboursement = dateDebutRemboursement;
        return this;
    }

    public void setDateDebutRemboursement(LocalDate dateDebutRemboursement) {
        this.dateDebutRemboursement = dateDebutRemboursement;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Pret deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public User getUserCreate() {
        return userCreate;
    }

    public Pret userCreate(User user) {
        this.userCreate = user;
        return this;
    }

    public void setUserCreate(User user) {
        this.userCreate = user;
    }

    public User getUserUpdate() {
        return userUpdate;
    }

    public Pret userUpdate(User user) {
        this.userUpdate = user;
        return this;
    }

    public void setUserUpdate(User user) {
        this.userUpdate = user;
    }

    public User getUserDeleted() {
        return userDeleted;
    }

    public Pret userDeleted(User user) {
        this.userDeleted = user;
        return this;
    }

    public void setUserDeleted(User user) {
        this.userDeleted = user;
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
        Pret pret = (Pret) o;
        if (pret.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pret.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pret{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", nbRemboursement=" + getNbRemboursement() +
            ", datePret='" + getDatePret() + "'" +
            ", dateDebutRemboursement='" + getDateDebutRemboursement() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
