package com.emard.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DetailPret.
 */
@Entity
@Table(name = "detail_pret")
public class DetailPret implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Double montant;

    @Column(name = "is_rembourse")
    private Boolean isRembourse;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne(optional = false)
    @NotNull
    private Collaborateur collaborateur;

    @ManyToOne(optional = false)
    @NotNull
    private Pret pret;

    @ManyToOne
    private User userCreated;

    @ManyToOne
    private User userUpdated;

    @ManyToOne
    private User userDeleted;

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

    public DetailPret montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Boolean isIsRembourse() {
        return isRembourse;
    }

    public DetailPret isRembourse(Boolean isRembourse) {
        this.isRembourse = isRembourse;
        return this;
    }

    public void setIsRembourse(Boolean isRembourse) {
        this.isRembourse = isRembourse;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public DetailPret deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    public DetailPret collaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
        return this;
    }

    public void setCollaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
    }

    public Pret getPret() {
        return pret;
    }

    public DetailPret pret(Pret pret) {
        this.pret = pret;
        return this;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }

    public User getUserCreated() {
        return userCreated;
    }

    public DetailPret userCreated(User user) {
        this.userCreated = user;
        return this;
    }

    public void setUserCreated(User user) {
        this.userCreated = user;
    }

    public User getUserUpdated() {
        return userUpdated;
    }

    public DetailPret userUpdated(User user) {
        this.userUpdated = user;
        return this;
    }

    public void setUserUpdated(User user) {
        this.userUpdated = user;
    }

    public User getUserDeleted() {
        return userDeleted;
    }

    public DetailPret userDeleted(User user) {
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
        DetailPret detailPret = (DetailPret) o;
        if (detailPret.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detailPret.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetailPret{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", isRembourse='" + isIsRembourse() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
