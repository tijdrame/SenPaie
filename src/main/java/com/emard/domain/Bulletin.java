package com.emard.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Bulletin.
 */
@Entity
@Table(name = "bulletin")
public class Bulletin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "retenue_ipm")
    private Double retenueIpm;

    @Column(name = "retenue_pharmacie")
    private Double retenuePharmacie;

    @Column(name = "autre_retenue")
    private Double autreRetenue;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_updated")
    private LocalDate dateUpdated;

    @Column(name = "date_deleted")
    private LocalDate dateDeleted;

    @Column(name = "brut_fiscal")
    private Double brutFiscal;

    @Column(name = "net_a_payer")
    private Double netAPayer;

    @Column(name = "salaire_brut_mensuel")
    private Double salaireBrutMensuel;

    @Column(name = "impot_sur_revenu")
    private Double impotSurRevenu;

    @Column(name = "trimf")
    private Double trimf;

    @Column(name = "ipres_part_salariale")
    private Double ipresPartSalariale;

    @Column(name = "tot_retenue")
    private Double totRetenue;

    @Column(name = "ipres_part_patronales")
    private Double ipresPartPatronales;

    @Column(name = "css_accident_de_travail")
    private Double cssAccidentDeTravail;

    @Column(name = "css_prestation_familiale")
    private Double cssPrestationFamiliale;

    @Column(name = "ipm_patronale")
    private Double ipmPatronale;

    @Column(name = "contribution_forfaitaire")
    private Double contributionForfaitaire;

    @Column(name = "nb_part")
    private Float nbPart;

    @Column(name = "nb_femmes")
    private Integer nbFemmes;

    @Column(name = "nb_enfants")
    private Integer nbEnfants;

    @Column(name = "prime_imposable")
    private Double primeImposable;

    @Column(name = "prime_non_imposable")
    private Double primeNonImposable;

    @Column(name = "avantage")
    private Double avantage;

    @ManyToOne(optional = false)
    @NotNull
    private Collaborateur collaborateur;

    @ManyToOne(optional = false)
    @NotNull
    private TypePaiement typePaiement;

    @ManyToOne
    private User userCreated;

    @ManyToOne
    private User userUpdated;

    @ManyToOne
    private User userDeleted;

    @ManyToMany
    @JoinTable(name = "bulletin_remboursement",
               joinColumns = @JoinColumn(name="bulletins_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="remboursements_id", referencedColumnName="id"))
    private Set<Remboursement> remboursements = new HashSet<>();

    @ManyToOne
    private Exercice exercice;

    @ManyToOne
    private MoisConcerne moisConcerne;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRetenueIpm() {
        return retenueIpm;
    }

    public Bulletin retenueIpm(Double retenueIpm) {
        this.retenueIpm = retenueIpm;
        return this;
    }

    public void setRetenueIpm(Double retenueIpm) {
        this.retenueIpm = retenueIpm;
    }

    public Double getRetenuePharmacie() {
        return retenuePharmacie;
    }

    public Bulletin retenuePharmacie(Double retenuePharmacie) {
        this.retenuePharmacie = retenuePharmacie;
        return this;
    }

    public void setRetenuePharmacie(Double retenuePharmacie) {
        this.retenuePharmacie = retenuePharmacie;
    }

    public Double getAutreRetenue() {
        return autreRetenue;
    }

    public Bulletin autreRetenue(Double autreRetenue) {
        this.autreRetenue = autreRetenue;
        return this;
    }

    public void setAutreRetenue(Double autreRetenue) {
        this.autreRetenue = autreRetenue;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Bulletin deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Bulletin dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Bulletin dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public Bulletin dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Double getBrutFiscal() {
        return brutFiscal;
    }

    public Bulletin brutFiscal(Double brutFiscal) {
        this.brutFiscal = brutFiscal;
        return this;
    }

    public void setBrutFiscal(Double brutFiscal) {
        this.brutFiscal = brutFiscal;
    }

    public Double getNetAPayer() {
        return netAPayer;
    }

    public Bulletin netAPayer(Double netAPayer) {
        this.netAPayer = netAPayer;
        return this;
    }

    public void setNetAPayer(Double netAPayer) {
        this.netAPayer = netAPayer;
    }

    public Double getSalaireBrutMensuel() {
        return salaireBrutMensuel;
    }

    public Bulletin salaireBrutMensuel(Double salaireBrutMensuel) {
        this.salaireBrutMensuel = salaireBrutMensuel;
        return this;
    }

    public void setSalaireBrutMensuel(Double salaireBrutMensuel) {
        this.salaireBrutMensuel = salaireBrutMensuel;
    }

    public Double getImpotSurRevenu() {
        return impotSurRevenu;
    }

    public Bulletin impotSurRevenu(Double impotSurRevenu) {
        this.impotSurRevenu = impotSurRevenu;
        return this;
    }

    public void setImpotSurRevenu(Double impotSurRevenu) {
        this.impotSurRevenu = impotSurRevenu;
    }

    public Double getTrimf() {
        return trimf;
    }

    public Bulletin trimf(Double trimf) {
        this.trimf = trimf;
        return this;
    }

    public void setTrimf(Double trimf) {
        this.trimf = trimf;
    }

    public Double getIpresPartSalariale() {
        return ipresPartSalariale;
    }

    public Bulletin ipresPartSalariale(Double ipresPartSalariale) {
        this.ipresPartSalariale = ipresPartSalariale;
        return this;
    }

    public void setIpresPartSalariale(Double ipresPartSalariale) {
        this.ipresPartSalariale = ipresPartSalariale;
    }

    public Double getTotRetenue() {
        return totRetenue;
    }

    public Bulletin totRetenue(Double totRetenue) {
        this.totRetenue = totRetenue;
        return this;
    }

    public void setTotRetenue(Double totRetenue) {
        this.totRetenue = totRetenue;
    }

    public Double getIpresPartPatronales() {
        return ipresPartPatronales;
    }

    public Bulletin ipresPartPatronales(Double ipresPartPatronales) {
        this.ipresPartPatronales = ipresPartPatronales;
        return this;
    }

    public void setIpresPartPatronales(Double ipresPartPatronales) {
        this.ipresPartPatronales = ipresPartPatronales;
    }

    public Double getCssAccidentDeTravail() {
        return cssAccidentDeTravail;
    }

    public Bulletin cssAccidentDeTravail(Double cssAccidentDeTravail) {
        this.cssAccidentDeTravail = cssAccidentDeTravail;
        return this;
    }

    public void setCssAccidentDeTravail(Double cssAccidentDeTravail) {
        this.cssAccidentDeTravail = cssAccidentDeTravail;
    }

    public Double getCssPrestationFamiliale() {
        return cssPrestationFamiliale;
    }

    public Bulletin cssPrestationFamiliale(Double cssPrestationFamiliale) {
        this.cssPrestationFamiliale = cssPrestationFamiliale;
        return this;
    }

    public void setCssPrestationFamiliale(Double cssPrestationFamiliale) {
        this.cssPrestationFamiliale = cssPrestationFamiliale;
    }

    public Double getIpmPatronale() {
        return ipmPatronale;
    }

    public Bulletin ipmPatronale(Double ipmPatronale) {
        this.ipmPatronale = ipmPatronale;
        return this;
    }

    public void setIpmPatronale(Double ipmPatronale) {
        this.ipmPatronale = ipmPatronale;
    }

    public Double getContributionForfaitaire() {
        return contributionForfaitaire;
    }

    public Bulletin contributionForfaitaire(Double contributionForfaitaire) {
        this.contributionForfaitaire = contributionForfaitaire;
        return this;
    }

    public void setContributionForfaitaire(Double contributionForfaitaire) {
        this.contributionForfaitaire = contributionForfaitaire;
    }

    public Float getNbPart() {
        return nbPart;
    }

    public Bulletin nbPart(Float nbPart) {
        this.nbPart = nbPart;
        return this;
    }

    public void setNbPart(Float nbPart) {
        this.nbPart = nbPart;
    }

    public Integer getNbFemmes() {
        return nbFemmes;
    }

    public Bulletin nbFemmes(Integer nbFemmes) {
        this.nbFemmes = nbFemmes;
        return this;
    }

    public void setNbFemmes(Integer nbFemmes) {
        this.nbFemmes = nbFemmes;
    }

    public Integer getNbEnfants() {
        return nbEnfants;
    }

    public Bulletin nbEnfants(Integer nbEnfants) {
        this.nbEnfants = nbEnfants;
        return this;
    }

    public void setNbEnfants(Integer nbEnfants) {
        this.nbEnfants = nbEnfants;
    }

    public Double getPrimeImposable() {
        return primeImposable;
    }

    public Bulletin primeImposable(Double primeImposable) {
        this.primeImposable = primeImposable;
        return this;
    }

    public void setPrimeImposable(Double primeImposable) {
        this.primeImposable = primeImposable;
    }

    public Double getPrimeNonImposable() {
        return primeNonImposable;
    }

    public Bulletin primeNonImposable(Double primeNonImposable) {
        this.primeNonImposable = primeNonImposable;
        return this;
    }

    public void setPrimeNonImposable(Double primeNonImposable) {
        this.primeNonImposable = primeNonImposable;
    }

    public Double getAvantage() {
        return avantage;
    }

    public Bulletin avantage(Double avantage) {
        this.avantage = avantage;
        return this;
    }

    public void setAvantage(Double avantage) {
        this.avantage = avantage;
    }

    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    public Bulletin collaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
        return this;
    }

    public void setCollaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
    }

    public TypePaiement getTypePaiement() {
        return typePaiement;
    }

    public Bulletin typePaiement(TypePaiement typePaiement) {
        this.typePaiement = typePaiement;
        return this;
    }

    public void setTypePaiement(TypePaiement typePaiement) {
        this.typePaiement = typePaiement;
    }

    public User getUserCreated() {
        return userCreated;
    }

    public Bulletin userCreated(User user) {
        this.userCreated = user;
        return this;
    }

    public void setUserCreated(User user) {
        this.userCreated = user;
    }

    public User getUserUpdated() {
        return userUpdated;
    }

    public Bulletin userUpdated(User user) {
        this.userUpdated = user;
        return this;
    }

    public void setUserUpdated(User user) {
        this.userUpdated = user;
    }

    public User getUserDeleted() {
        return userDeleted;
    }

    public Bulletin userDeleted(User user) {
        this.userDeleted = user;
        return this;
    }

    public void setUserDeleted(User user) {
        this.userDeleted = user;
    }

    public Set<Remboursement> getRemboursements() {
        return remboursements;
    }

    public Bulletin remboursements(Set<Remboursement> remboursements) {
        this.remboursements = remboursements;
        return this;
    }

    public Bulletin addRemboursement(Remboursement remboursement) {
        this.remboursements.add(remboursement);
        remboursement.getBulletins().add(this);
        return this;
    }

    public Bulletin removeRemboursement(Remboursement remboursement) {
        this.remboursements.remove(remboursement);
        remboursement.getBulletins().remove(this);
        return this;
    }

    public void setRemboursements(Set<Remboursement> remboursements) {
        this.remboursements = remboursements;
    }

    public Exercice getExercice() {
        return exercice;
    }

    public Bulletin exercice(Exercice exercice) {
        this.exercice = exercice;
        return this;
    }

    public void setExercice(Exercice exercice) {
        this.exercice = exercice;
    }

    public MoisConcerne getMoisConcerne() {
        return moisConcerne;
    }

    public Bulletin moisConcerne(MoisConcerne moisConcerne) {
        this.moisConcerne = moisConcerne;
        return this;
    }

    public void setMoisConcerne(MoisConcerne moisConcerne) {
        this.moisConcerne = moisConcerne;
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
        Bulletin bulletin = (Bulletin) o;
        if (bulletin.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bulletin.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bulletin{" +
            "id=" + getId() +
            ", retenueIpm=" + getRetenueIpm() +
            ", retenuePharmacie=" + getRetenuePharmacie() +
            ", autreRetenue=" + getAutreRetenue() +
            ", deleted='" + isDeleted() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", brutFiscal=" + getBrutFiscal() +
            ", netAPayer=" + getNetAPayer() +
            ", salaireBrutMensuel=" + getSalaireBrutMensuel() +
            ", impotSurRevenu=" + getImpotSurRevenu() +
            ", trimf=" + getTrimf() +
            ", ipresPartSalariale=" + getIpresPartSalariale() +
            ", totRetenue=" + getTotRetenue() +
            ", ipresPartPatronales=" + getIpresPartPatronales() +
            ", cssAccidentDeTravail=" + getCssAccidentDeTravail() +
            ", cssPrestationFamiliale=" + getCssPrestationFamiliale() +
            ", ipmPatronale=" + getIpmPatronale() +
            ", contributionForfaitaire=" + getContributionForfaitaire() +
            ", nbPart=" + getNbPart() +
            ", nbFemmes=" + getNbFemmes() +
            ", nbEnfants=" + getNbEnfants() +
            ", primeImposable=" + getPrimeImposable() +
            ", primeNonImposable=" + getPrimeNonImposable() +
            ", avantage=" + getAvantage() +
            "}";
    }
}
