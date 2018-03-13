package fr.adservio.mybizdev.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Consultant.
 */
@Entity
@Table(name = "consultant")
public class Consultant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "cjm", nullable = false)
    private Integer cjm;

    @NotNull
    @Column(name = "tjm", nullable = false)
    private Integer tjm;

    @NotNull
    @Column(name = "date_integration", nullable = false)
    private Instant dateIntegration;

    @ManyToOne
    private Placement placement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Consultant nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Consultant prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getCjm() {
        return cjm;
    }

    public Consultant cjm(Integer cjm) {
        this.cjm = cjm;
        return this;
    }

    public void setCjm(Integer cjm) {
        this.cjm = cjm;
    }

    public Integer getTjm() {
        return tjm;
    }

    public Consultant tjm(Integer tjm) {
        this.tjm = tjm;
        return this;
    }

    public void setTjm(Integer tjm) {
        this.tjm = tjm;
    }

    public Instant getDateIntegration() {
        return dateIntegration;
    }

    public Consultant dateIntegration(Instant dateIntegration) {
        this.dateIntegration = dateIntegration;
        return this;
    }

    public void setDateIntegration(Instant dateIntegration) {
        this.dateIntegration = dateIntegration;
    }

    public Placement getPlacement() {
        return placement;
    }

    public Consultant placement(Placement placement) {
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
        Consultant consultant = (Consultant) o;
        if (consultant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Consultant{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", cjm=" + getCjm() +
            ", tjm=" + getTjm() +
            ", dateIntegration='" + getDateIntegration() + "'" +
            "}";
    }
}
