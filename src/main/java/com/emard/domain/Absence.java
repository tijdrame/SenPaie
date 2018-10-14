package com.emard.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Absence.
 */
@Entity
@Table(name = "absence")
public class Absence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_absence", nullable = false)
    private LocalDate dateAbsence;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @ManyToOne
    private User userCreated;

    @ManyToOne
    private User userUpdated;

    @ManyToOne
    private User userDeleted;

    @ManyToOne(optional = false)
    @NotNull
    private Collaborateur collaborateur;

    @ManyToOne(optional = false)
    @NotNull
    private Motif motif;

    @ManyToOne(optional = false)
    @NotNull
    private Exercice exercice;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateAbsence() {
        return dateAbsence;
    }

    public Absence dateAbsence(LocalDate dateAbsence) {
        this.dateAbsence = dateAbsence;
        return this;
    }

    public void setDateAbsence(LocalDate dateAbsence) {
        this.dateAbsence = dateAbsence;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Absence deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Absence dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getUserCreated() {
        return userCreated;
    }

    public Absence userCreated(User user) {
        this.userCreated = user;
        return this;
    }

    public void setUserCreated(User user) {
        this.userCreated = user;
    }

    public User getUserUpdated() {
        return userUpdated;
    }

    public Absence userUpdated(User user) {
        this.userUpdated = user;
        return this;
    }

    public void setUserUpdated(User user) {
        this.userUpdated = user;
    }

    public User getUserDeleted() {
        return userDeleted;
    }

    public Absence userDeleted(User user) {
        this.userDeleted = user;
        return this;
    }

    public void setUserDeleted(User user) {
        this.userDeleted = user;
    }

    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    public Absence collaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
        return this;
    }

    public void setCollaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
    }

    public Motif getMotif() {
        return motif;
    }

    public Absence motif(Motif motif) {
        this.motif = motif;
        return this;
    }

    public void setMotif(Motif motif) {
        this.motif = motif;
    }

    public Exercice getExercice() {
        return exercice;
    }

    public Absence exercice(Exercice exercice) {
        this.exercice = exercice;
        return this;
    }

    public void setExercice(Exercice exercice) {
        this.exercice = exercice;
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
        Absence absence = (Absence) o;
        if (absence.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), absence.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Absence{" +
            "id=" + getId() +
            ", dateAbsence='" + getDateAbsence() + "'" +
            ", deleted='" + isDeleted() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            "}";
    }
}
