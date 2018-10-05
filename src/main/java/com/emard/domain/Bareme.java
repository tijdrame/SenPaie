package com.emard.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Bareme.
 */
@Entity
@Table(name = "bareme")
public class Bareme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "revenu_brut", nullable = false)
    private Double revenuBrut;

    @NotNull
    @Column(name = "trim_f", nullable = false)
    private Double trimF;

    @NotNull
    @Column(name = "une_part", nullable = false)
    private Double unePart;

    @NotNull
    @Column(name = "une_part_et_demi", nullable = false)
    private Double unePartEtDemi;

    @NotNull
    @Column(name = "deux_parts", nullable = false)
    private Double deuxParts;

    @NotNull
    @Column(name = "deux_parts_et_demi", nullable = false)
    private Double deuxPartsEtDemi;

    @NotNull
    @Column(name = "trois_parts", nullable = false)
    private Double troisParts;

    @NotNull
    @Column(name = "trois_parts_et_demi", nullable = false)
    private Double troisPartsEtDemi;

    @NotNull
    @Column(name = "quatre_parts", nullable = false)
    private Double quatreParts;

    @NotNull
    @Column(name = "quatre_parts_et_demi", nullable = false)
    private Double quatrePartsEtDemi;

    @NotNull
    @Column(name = "cinq_parts", nullable = false)
    private Double cinqParts;

    @Column(name = "deleted")
    private Boolean deleted;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRevenuBrut() {
        return revenuBrut;
    }

    public Bareme revenuBrut(Double revenuBrut) {
        this.revenuBrut = revenuBrut;
        return this;
    }

    public void setRevenuBrut(Double revenuBrut) {
        this.revenuBrut = revenuBrut;
    }

    public Double getTrimF() {
        return trimF;
    }

    public Bareme trimF(Double trimF) {
        this.trimF = trimF;
        return this;
    }

    public void setTrimF(Double trimF) {
        this.trimF = trimF;
    }

    public Double getUnePart() {
        return unePart;
    }

    public Bareme unePart(Double unePart) {
        this.unePart = unePart;
        return this;
    }

    public void setUnePart(Double unePart) {
        this.unePart = unePart;
    }

    public Double getUnePartEtDemi() {
        return unePartEtDemi;
    }

    public Bareme unePartEtDemi(Double unePartEtDemi) {
        this.unePartEtDemi = unePartEtDemi;
        return this;
    }

    public void setUnePartEtDemi(Double unePartEtDemi) {
        this.unePartEtDemi = unePartEtDemi;
    }

    public Double getDeuxParts() {
        return deuxParts;
    }

    public Bareme deuxParts(Double deuxParts) {
        this.deuxParts = deuxParts;
        return this;
    }

    public void setDeuxParts(Double deuxParts) {
        this.deuxParts = deuxParts;
    }

    public Double getDeuxPartsEtDemi() {
        return deuxPartsEtDemi;
    }

    public Bareme deuxPartsEtDemi(Double deuxPartsEtDemi) {
        this.deuxPartsEtDemi = deuxPartsEtDemi;
        return this;
    }

    public void setDeuxPartsEtDemi(Double deuxPartsEtDemi) {
        this.deuxPartsEtDemi = deuxPartsEtDemi;
    }

    public Double getTroisParts() {
        return troisParts;
    }

    public Bareme troisParts(Double troisParts) {
        this.troisParts = troisParts;
        return this;
    }

    public void setTroisParts(Double troisParts) {
        this.troisParts = troisParts;
    }

    public Double getTroisPartsEtDemi() {
        return troisPartsEtDemi;
    }

    public Bareme troisPartsEtDemi(Double troisPartsEtDemi) {
        this.troisPartsEtDemi = troisPartsEtDemi;
        return this;
    }

    public void setTroisPartsEtDemi(Double troisPartsEtDemi) {
        this.troisPartsEtDemi = troisPartsEtDemi;
    }

    public Double getQuatreParts() {
        return quatreParts;
    }

    public Bareme quatreParts(Double quatreParts) {
        this.quatreParts = quatreParts;
        return this;
    }

    public void setQuatreParts(Double quatreParts) {
        this.quatreParts = quatreParts;
    }

    public Double getQuatrePartsEtDemi() {
        return quatrePartsEtDemi;
    }

    public Bareme quatrePartsEtDemi(Double quatrePartsEtDemi) {
        this.quatrePartsEtDemi = quatrePartsEtDemi;
        return this;
    }

    public void setQuatrePartsEtDemi(Double quatrePartsEtDemi) {
        this.quatrePartsEtDemi = quatrePartsEtDemi;
    }

    public Double getCinqParts() {
        return cinqParts;
    }

    public Bareme cinqParts(Double cinqParts) {
        this.cinqParts = cinqParts;
        return this;
    }

    public void setCinqParts(Double cinqParts) {
        this.cinqParts = cinqParts;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Bareme deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
        Bareme bareme = (Bareme) o;
        if (bareme.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bareme.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bareme{" +
            "id=" + getId() +
            ", revenuBrut=" + getRevenuBrut() +
            ", trimF=" + getTrimF() +
            ", unePart=" + getUnePart() +
            ", unePartEtDemi=" + getUnePartEtDemi() +
            ", deuxParts=" + getDeuxParts() +
            ", deuxPartsEtDemi=" + getDeuxPartsEtDemi() +
            ", troisParts=" + getTroisParts() +
            ", troisPartsEtDemi=" + getTroisPartsEtDemi() +
            ", quatreParts=" + getQuatreParts() +
            ", quatrePartsEtDemi=" + getQuatrePartsEtDemi() +
            ", cinqParts=" + getCinqParts() +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
