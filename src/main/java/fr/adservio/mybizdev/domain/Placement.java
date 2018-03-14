package fr.adservio.mybizdev.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private Statut etat;

    @OneToMany(mappedBy = "placement")
    @JsonIgnore
    private Set<Consultant> consultants = new HashSet<>();

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

    public Set<Consultant> getConsultants() {
        return consultants;
    }

    public Placement consultants(Set<Consultant> consultants) {
        this.consultants = consultants;
        return this;
    }

    public Placement addConsultant(Consultant consultant) {
        this.consultants.add(consultant);
        consultant.setPlacement(this);
        return this;
    }

    public Placement removeConsultant(Consultant consultant) {
        this.consultants.remove(consultant);
        consultant.setPlacement(null);
        return this;
    }

    public void setConsultants(Set<Consultant> consultants) {
        this.consultants = consultants;
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
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
