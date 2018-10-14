package com.emard.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A MembreFamille.
 */
@Entity
@Table(name = "membre_famille")
public class MembreFamille implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @Column(name = "is_actif")
    private Boolean isActif;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "date_mariage")
    private LocalDate dateMariage;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Column(name = "telephone")
    private String telephone;

    @ManyToOne
    private Collaborateur collaborateur;

    @ManyToOne
    private User user;

    @ManyToOne
    private User userUpdate;

    @ManyToOne
    private User userDeleted;

    @ManyToOne
    private TypeRelation typeRelation;

    @ManyToOne
    private Sexe sexe;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public MembreFamille prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public MembreFamille nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public MembreFamille adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Boolean isIsActif() {
        return isActif;
    }

    public MembreFamille isActif(Boolean isActif) {
        this.isActif = isActif;
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public MembreFamille deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public MembreFamille dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public LocalDate getDateMariage() {
        return dateMariage;
    }

    public MembreFamille dateMariage(LocalDate dateMariage) {
        this.dateMariage = dateMariage;
        return this;
    }

    public void setDateMariage(LocalDate dateMariage) {
        this.dateMariage = dateMariage;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public MembreFamille photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public MembreFamille photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getTelephone() {
        return telephone;
    }

    public MembreFamille telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    public MembreFamille collaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
        return this;
    }

    public void setCollaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
    }

    public User getUser() {
        return user;
    }

    public MembreFamille user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUserUpdate() {
        return userUpdate;
    }

    public MembreFamille userUpdate(User user) {
        this.userUpdate = user;
        return this;
    }

    public void setUserUpdate(User user) {
        this.userUpdate = user;
    }

    public User getUserDeleted() {
        return userDeleted;
    }

    public MembreFamille userDeleted(User user) {
        this.userDeleted = user;
        return this;
    }

    public void setUserDeleted(User user) {
        this.userDeleted = user;
    }

    public TypeRelation getTypeRelation() {
        return typeRelation;
    }

    public MembreFamille typeRelation(TypeRelation typeRelation) {
        this.typeRelation = typeRelation;
        return this;
    }

    public void setTypeRelation(TypeRelation typeRelation) {
        this.typeRelation = typeRelation;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public MembreFamille sexe(Sexe sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
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
        MembreFamille membreFamille = (MembreFamille) o;
        if (membreFamille.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), membreFamille.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MembreFamille{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", isActif='" + isIsActif() + "'" +
            ", deleted='" + isDeleted() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", dateMariage='" + getDateMariage() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", telephone='" + getTelephone() + "'" +
            "}";
    }
}
