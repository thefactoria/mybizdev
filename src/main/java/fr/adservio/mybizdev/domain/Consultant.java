package fr.adservio.mybizdev.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
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
    @Column(name = "tj_min", nullable = false)
    private Integer tjMin;

    @Column(name = "tj_m_final")
    private Integer tjMFinal;

    @NotNull
    @Column(name = "date_debut_interco", nullable = false)
    private ZonedDateTime dateDebutInterco;

    @Column(name = "date_demarrage")
    private ZonedDateTime dateDemarrage;

    /**
     * Another side of the same relationship
     */
    @ApiModelProperty(value = "Another side of the same relationship")
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

    public Integer getTjMin() {
        return tjMin;
    }

    public Consultant tjMin(Integer tjMin) {
        this.tjMin = tjMin;
        return this;
    }

    public void setTjMin(Integer tjMin) {
        this.tjMin = tjMin;
    }

    public Integer getTjMFinal() {
        return tjMFinal;
    }

    public Consultant tjMFinal(Integer tjMFinal) {
        this.tjMFinal = tjMFinal;
        return this;
    }

    public void setTjMFinal(Integer tjMFinal) {
        this.tjMFinal = tjMFinal;
    }

    public ZonedDateTime getDateDebutInterco() {
        return dateDebutInterco;
    }

    public Consultant dateDebutInterco(ZonedDateTime dateDebutInterco) {
        this.dateDebutInterco = dateDebutInterco;
        return this;
    }

    public void setDateDebutInterco(ZonedDateTime dateDebutInterco) {
        this.dateDebutInterco = dateDebutInterco;
    }

    public ZonedDateTime getDateDemarrage() {
        return dateDemarrage;
    }

    public Consultant dateDemarrage(ZonedDateTime dateDemarrage) {
        this.dateDemarrage = dateDemarrage;
        return this;
    }

    public void setDateDemarrage(ZonedDateTime dateDemarrage) {
        this.dateDemarrage = dateDemarrage;
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
            ", tjMin=" + getTjMin() +
            ", tjMFinal=" + getTjMFinal() +
            ", dateDebutInterco='" + getDateDebutInterco() + "'" +
            ", dateDemarrage='" + getDateDemarrage() + "'" +
            "}";
    }
}
