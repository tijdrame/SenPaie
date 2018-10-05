package com.emard.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Remboursement.
 */
@Entity
@Table(name = "remboursement")
public class Remboursement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_remboursement", nullable = false)
    private LocalDate dateRemboursement;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "is_rembourse")
    private Boolean isRembourse;

    @ManyToOne(optional = false)
    @NotNull
    private DetailPret detailPret;

    @ManyToOne
    private User userCreated;

    @ManyToOne
    private User userUpdated;

    @ManyToMany(mappedBy = "remboursements")
    @JsonIgnore
    private Set<Bulletin> bulletins = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateRemboursement() {
        return dateRemboursement;
    }

    public Remboursement dateRemboursement(LocalDate dateRemboursement) {
        this.dateRemboursement = dateRemboursement;
        return this;
    }

    public void setDateRemboursement(LocalDate dateRemboursement) {
        this.dateRemboursement = dateRemboursement;
    }

    public Double getMontant() {
        return montant;
    }

    public Remboursement montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Remboursement deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean isIsRembourse() {
        return isRembourse;
    }

    public Remboursement isRembourse(Boolean isRembourse) {
        this.isRembourse = isRembourse;
        return this;
    }

    public void setIsRembourse(Boolean isRembourse) {
        this.isRembourse = isRembourse;
    }

    public DetailPret getDetailPret() {
        return detailPret;
    }

    public Remboursement detailPret(DetailPret detailPret) {
        this.detailPret = detailPret;
        return this;
    }

    public void setDetailPret(DetailPret detailPret) {
        this.detailPret = detailPret;
    }

    public User getUserCreated() {
        return userCreated;
    }

    public Remboursement userCreated(User user) {
        this.userCreated = user;
        return this;
    }

    public void setUserCreated(User user) {
        this.userCreated = user;
    }

    public User getUserUpdated() {
        return userUpdated;
    }

    public Remboursement userUpdated(User user) {
        this.userUpdated = user;
        return this;
    }

    public void setUserUpdated(User user) {
        this.userUpdated = user;
    }

    public Set<Bulletin> getBulletins() {
        return bulletins;
    }

    public Remboursement bulletins(Set<Bulletin> bulletins) {
        this.bulletins = bulletins;
        return this;
    }

    public Remboursement addBulletins(Bulletin bulletin) {
        this.bulletins.add(bulletin);
        bulletin.getRemboursements().add(this);
        return this;
    }

    public Remboursement removeBulletins(Bulletin bulletin) {
        this.bulletins.remove(bulletin);
        bulletin.getRemboursements().remove(this);
        return this;
    }

    public void setBulletins(Set<Bulletin> bulletins) {
        this.bulletins = bulletins;
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
        Remboursement remboursement = (Remboursement) o;
        if (remboursement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), remboursement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Remboursement{" +
            "id=" + getId() +
            ", dateRemboursement='" + getDateRemboursement() + "'" +
            ", montant=" + getMontant() +
            ", deleted='" + isDeleted() + "'" +
            ", isRembourse='" + isIsRembourse() + "'" +
            "}";
    }
}
