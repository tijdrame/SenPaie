package com.emard.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Structure.
 */
@Entity
@Table(name = "structure")
public class Structure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "denomination", nullable = false)
    private String denomination;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @NotNull
    @Column(name = "ninea", nullable = false)
    private String ninea;

    @Column(name = "capital")
    private Double capital;

    @Column(name = "numero_ipres")
    private String numeroIpres;

    @Column(name = "numero_css")
    private String numeroCss;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Column(name = "ipm")
    private Boolean ipm;

    @Lob
    @Column(name = "signature")
    private byte[] signature;

    @Column(name = "signature_content_type")
    private String signatureContentType;

    @Column(name = "montant_ipm")
    private Double montantIpm;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    private Convention convention;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenomination() {
        return denomination;
    }

    public Structure denomination(String denomination) {
        this.denomination = denomination;
        return this;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getTelephone() {
        return telephone;
    }

    public Structure telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public Structure adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNinea() {
        return ninea;
    }

    public Structure ninea(String ninea) {
        this.ninea = ninea;
        return this;
    }

    public void setNinea(String ninea) {
        this.ninea = ninea;
    }

    public Double getCapital() {
        return capital;
    }

    public Structure capital(Double capital) {
        this.capital = capital;
        return this;
    }

    public void setCapital(Double capital) {
        this.capital = capital;
    }

    public String getNumeroIpres() {
        return numeroIpres;
    }

    public Structure numeroIpres(String numeroIpres) {
        this.numeroIpres = numeroIpres;
        return this;
    }

    public void setNumeroIpres(String numeroIpres) {
        this.numeroIpres = numeroIpres;
    }

    public String getNumeroCss() {
        return numeroCss;
    }

    public Structure numeroCss(String numeroCss) {
        this.numeroCss = numeroCss;
        return this;
    }

    public void setNumeroCss(String numeroCss) {
        this.numeroCss = numeroCss;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Structure logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Structure logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public Boolean isIpm() {
        return ipm;
    }

    public Structure ipm(Boolean ipm) {
        this.ipm = ipm;
        return this;
    }

    public void setIpm(Boolean ipm) {
        this.ipm = ipm;
    }

    public byte[] getSignature() {
        return signature;
    }

    public Structure signature(byte[] signature) {
        this.signature = signature;
        return this;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getSignatureContentType() {
        return signatureContentType;
    }

    public Structure signatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
        return this;
    }

    public void setSignatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
    }

    public Double getMontantIpm() {
        return montantIpm;
    }

    public Structure montantIpm(Double montantIpm) {
        this.montantIpm = montantIpm;
        return this;
    }

    public void setMontantIpm(Double montantIpm) {
        this.montantIpm = montantIpm;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Structure deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Convention getConvention() {
        return convention;
    }

    public Structure convention(Convention convention) {
        this.convention = convention;
        return this;
    }

    public void setConvention(Convention convention) {
        this.convention = convention;
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
        Structure structure = (Structure) o;
        if (structure.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), structure.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Structure{" +
            "id=" + getId() +
            ", denomination='" + getDenomination() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", ninea='" + getNinea() + "'" +
            ", capital=" + getCapital() +
            ", numeroIpres='" + getNumeroIpres() + "'" +
            ", numeroCss='" + getNumeroCss() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", ipm='" + isIpm() + "'" +
            ", signature='" + getSignature() + "'" +
            ", signatureContentType='" + getSignatureContentType() + "'" +
            ", montantIpm=" + getMontantIpm() +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
