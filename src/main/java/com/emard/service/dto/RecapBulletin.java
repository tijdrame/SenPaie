package com.emard.service.dto;

import com.emard.domain.Collaborateur;

public class RecapBulletin {
    private Collaborateur collaborateur;
    private Double brutFiscal;
    private Double netAPayer;
    private Double salaireBrutMensuel;
    private Double impotSurRevenu;
    private Double trimf;
    private Double ipresPartSalariale;
    private Double totRetenue;
    private Double ipresPartPatronales;

    private Double cssAccidentDeTravail;

    private Double cssPrestationFamiliale;

    private Double ipmPatronale;

    private Double contributionForfaitaire;

    private Float nbPart;

    private Integer nbFemmes;

    private Integer nbEnfants;

    private Double primeImposable;

    private Double primeNonImposable;

    private Double avantage;

    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    public void setCollaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
    }

    public Double getBrutFiscal() {
        return brutFiscal;
    }

    public void setBrutFiscal(Double brutFiscal) {
        this.brutFiscal = brutFiscal;
    }

    public Double getNetAPayer() {
        return netAPayer;
    }

    public void setNetAPayer(Double netAPayer) {
        this.netAPayer = netAPayer;
    }

    public Double getSalaireBrutMensuel() {
        return salaireBrutMensuel;
    }

    public void setSalaireBrutMensuel(Double salaireBrutMensuel) {
        this.salaireBrutMensuel = salaireBrutMensuel;
    }

    public Double getImpotSurRevenu() {
        return impotSurRevenu;
    }

    public void setImpotSurRevenu(Double impotSurRevenu) {
        this.impotSurRevenu = impotSurRevenu;
    }

    public Double getTrimf() {
        return trimf;
    }

    public void setTrimf(Double trimf) {
        this.trimf = trimf;
    }

    public Double getIpresPartSalariale() {
        return ipresPartSalariale;
    }

    public void setIpresPartSalariale(Double ipresPartSalariale) {
        this.ipresPartSalariale = ipresPartSalariale;
    }

    public Double getTotRetenue() {
        return totRetenue;
    }

    public void setTotRetenue(Double totRetenue) {
        this.totRetenue = totRetenue;
    }

    public Double getIpresPartPatronales() {
        return ipresPartPatronales;
    }

    public void setIpresPartPatronales(Double ipresPartPatronales) {
        this.ipresPartPatronales = ipresPartPatronales;
    }

    public Double getCssAccidentDeTravail() {
        return cssAccidentDeTravail;
    }

    public void setCssAccidentDeTravail(Double cssAccidentDeTravail) {
        this.cssAccidentDeTravail = cssAccidentDeTravail;
    }

    public Double getCssPrestationFamiliale() {
        return cssPrestationFamiliale;
    }

    public void setCssPrestationFamiliale(Double cssPrestationFamiliale) {
        this.cssPrestationFamiliale = cssPrestationFamiliale;
    }

    public Double getIpmPatronale() {
        return ipmPatronale;
    }

    public void setIpmPatronale(Double ipmPatronale) {
        this.ipmPatronale = ipmPatronale;
    }

    public Double getContributionForfaitaire() {
        return contributionForfaitaire;
    }

    public void setContributionForfaitaire(Double contributionForfaitaire) {
        this.contributionForfaitaire = contributionForfaitaire;
    }

    public Float getNbPart() {
        return nbPart;
    }

    public void setNbPart(Float nbPart) {
        this.nbPart = nbPart;
    }

    public Integer getNbFemmes() {
        return nbFemmes;
    }

    public void setNbFemmes(Integer nbFemmes) {
        this.nbFemmes = nbFemmes;
    }

    public Integer getNbEnfants() {
        return nbEnfants;
    }

    public void setNbEnfants(Integer nbEnfants) {
        this.nbEnfants = nbEnfants;
    }

    public Double getPrimeImposable() {
        return primeImposable;
    }

    public void setPrimeImposable(Double primeImposable) {
        this.primeImposable = primeImposable;
    }

    public Double getPrimeNonImposable() {
        return primeNonImposable;
    }

    public void setPrimeNonImposable(Double primeNonImposable) {
        this.primeNonImposable = primeNonImposable;
    }

    public Double getAvantage() {
        return avantage;
    }

    public void setAvantage(Double avantage) {
        this.avantage = avantage;
    }
}
