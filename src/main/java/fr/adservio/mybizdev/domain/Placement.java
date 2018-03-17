package fr.adservio.mybizdev.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import fr.adservio.mybizdev.domain.enumeration.Statut;

/**
 * A Placement.
 */
@Entity
@Table(name = "placement")
public class Placement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom_client_final", nullable = false)
    private String nomClientFinal;

    @Column(name = "nom_ssii")
    private String nomSSII;

    @Column(name = "contact_ssii")
    private String contactSSII;

    @Column(name = "contact_client")
    private String contactClient;

    @Column(name = "date_demarrage")
    private LocalDate dateDemarrage;

    @Column(name = "commentaires")
    private String commentaires;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private Statut etat;

    /**
     * Another side of the same relationship
     */
    @ApiModelProperty(value = "Another side of the same relationship")
    @ManyToOne
    private Consultant consultant;

    @ManyToOne
    private BizDev bizDev;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomClientFinal() {
        return nomClientFinal;
    }

    public Placement nomClientFinal(String nomClientFinal) {
        this.nomClientFinal = nomClientFinal;
        return this;
    }

    public void setNomClientFinal(String nomClientFinal) {
        this.nomClientFinal = nomClientFinal;
    }

    public String getNomSSII() {
        return nomSSII;
    }

    public Placement nomSSII(String nomSSII) {
        this.nomSSII = nomSSII;
        return this;
    }

    public void setNomSSII(String nomSSII) {
        this.nomSSII = nomSSII;
    }

    public String getContactSSII() {
        return contactSSII;
    }

    public Placement contactSSII(String contactSSII) {
        this.contactSSII = contactSSII;
        return this;
    }

    public void setContactSSII(String contactSSII) {
        this.contactSSII = contactSSII;
    }

    public String getContactClient() {
        return contactClient;
    }

    public Placement contactClient(String contactClient) {
        this.contactClient = contactClient;
        return this;
    }

    public void setContactClient(String contactClient) {
        this.contactClient = contactClient;
    }

    public LocalDate getDateDemarrage() {
        return dateDemarrage;
    }

    public Placement dateDemarrage(LocalDate dateDemarrage) {
        this.dateDemarrage = dateDemarrage;
        return this;
    }

    public void setDateDemarrage(LocalDate dateDemarrage) {
        this.dateDemarrage = dateDemarrage;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public Placement commentaires(String commentaires) {
        this.commentaires = commentaires;
        return this;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public Statut getEtat() {
        return etat;
    }

    public Placement etat(Statut etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(Statut etat) {
        this.etat = etat;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public Placement consultant(Consultant consultant) {
        this.consultant = consultant;
        return this;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    public BizDev getBizDev() {
        return bizDev;
    }

    public Placement bizDev(BizDev bizDev) {
        this.bizDev = bizDev;
        return this;
    }

    public void setBizDev(BizDev bizDev) {
        this.bizDev = bizDev;
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
        Placement placement = (Placement) o;
        if (placement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), placement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Placement{" +
            "id=" + getId() +
            ", nomClientFinal='" + getNomClientFinal() + "'" +
            ", nomSSII='" + getNomSSII() + "'" +
            ", contactSSII='" + getContactSSII() + "'" +
            ", contactClient='" + getContactClient() + "'" +
            ", dateDemarrage='" + getDateDemarrage() + "'" +
            ", commentaires='" + getCommentaires() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
