package com.emard.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DemandeConge.
 */
@Entity
@Table(name = "demande_conge")
public class DemandeConge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @NotNull
    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @Column(name = "motif_rejet")
    private String motifRejet;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    private StatutDemande statutRH;

    @ManyToOne
    private StatutDemande statutDG;

    @ManyToOne
    private Collaborateur collaborateur;

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

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public DemandeConge dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public DemandeConge dateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public DemandeConge dateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getMotifRejet() {
        return motifRejet;
    }

    public DemandeConge motifRejet(String motifRejet) {
        this.motifRejet = motifRejet;
        return this;
    }

    public void setMotifRejet(String motifRejet) {
        this.motifRejet = motifRejet;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public DemandeConge deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public StatutDemande getStatutRH() {
        return statutRH;
    }

    public DemandeConge statutRH(StatutDemande statutDemande) {
        this.statutRH = statutDemande;
        return this;
    }

    public void setStatutRH(StatutDemande statutDemande) {
        this.statutRH = statutDemande;
    }

    public StatutDemande getStatutDG() {
        return statutDG;
    }

    public DemandeConge statutDG(StatutDemande statutDemande) {
        this.statutDG = statutDemande;
        return this;
    }

    public void setStatutDG(StatutDemande statutDemande) {
        this.statutDG = statutDemande;
    }

    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    public DemandeConge collaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
        return this;
    }

    public void setCollaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
    }

    public User getUserCreated() {
        return userCreated;
    }

    public DemandeConge userCreated(User user) {
        this.userCreated = user;
        return this;
    }

    public void setUserCreated(User user) {
        this.userCreated = user;
    }

    public User getUserUpdated() {
        return userUpdated;
    }

    public DemandeConge userUpdated(User user) {
        this.userUpdated = user;
        return this;
    }

    public void setUserUpdated(User user) {
        this.userUpdated = user;
    }

    public User getUserDeleted() {
        return userDeleted;
    }

    public DemandeConge userDeleted(User user) {
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
        DemandeConge demandeConge = (DemandeConge) o;
        if (demandeConge.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), demandeConge.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DemandeConge{" +
            "id=" + getId() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", motifRejet='" + getMotifRejet() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
