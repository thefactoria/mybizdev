package fr.adservio.mybizdev.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import fr.adservio.mybizdev.domain.enumeration.Statut;

/**
 * A Etat.
 */
@Entity
@Table(name = "etat")
public class Etat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_demarrage")
    private Instant dateDemarrage;

    @NotNull
    @Column(name = "date_debut_interco", nullable = false)
    private Instant dateDebutInterco;

    @Enumerated(EnumType.STRING)
    @Column(name = "libelle_statut")
    private Statut libelleStatut;

    @OneToOne
    @JoinColumn(unique = true)
    private Placement placement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateDemarrage() {
        return dateDemarrage;
    }

    public Etat dateDemarrage(Instant dateDemarrage) {
        this.dateDemarrage = dateDemarrage;
        return this;
    }

    public void setDateDemarrage(Instant dateDemarrage) {
        this.dateDemarrage = dateDemarrage;
    }

    public Instant getDateDebutInterco() {
        return dateDebutInterco;
    }

    public Etat dateDebutInterco(Instant dateDebutInterco) {
        this.dateDebutInterco = dateDebutInterco;
        return this;
    }

    public void setDateDebutInterco(Instant dateDebutInterco) {
        this.dateDebutInterco = dateDebutInterco;
    }

    public Statut getLibelleStatut() {
        return libelleStatut;
    }

    public Etat libelleStatut(Statut libelleStatut) {
        this.libelleStatut = libelleStatut;
        return this;
    }

    public void setLibelleStatut(Statut libelleStatut) {
        this.libelleStatut = libelleStatut;
    }

    public Placement getPlacement() {
        return placement;
    }

    public Etat placement(Placement placement) {
        this.placement = placement;
        return this;
    }

    public void setPlacement(Placement placement) {
        this.placement = placement;
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
        Etat etat = (Etat) o;
        if (etat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Etat{" +
            "id=" + getId() +
            ", dateDemarrage='" + getDateDemarrage() + "'" +
            ", dateDebutInterco='" + getDateDebutInterco() + "'" +
            ", libelleStatut='" + getLibelleStatut() + "'" +
            "}";
    }
}
