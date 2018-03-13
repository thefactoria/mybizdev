package fr.adservio.mybizdev.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

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

    @NotNull
    @Column(name = "nom_ssii", nullable = false)
    private String nomSSII;

    @OneToMany(mappedBy = "placement")
    @JsonIgnore
    private Set<Consultant> consultants = new HashSet<>();

    @OneToMany(mappedBy = "placement")
    @JsonIgnore
    private Set<BizDev> bizDevs = new HashSet<>();

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

    public Set<BizDev> getBizDevs() {
        return bizDevs;
    }

    public Placement bizDevs(Set<BizDev> bizDevs) {
        this.bizDevs = bizDevs;
        return this;
    }

    public Placement addBizDev(BizDev bizDev) {
        this.bizDevs.add(bizDev);
        bizDev.setPlacement(this);
        return this;
    }

    public Placement removeBizDev(BizDev bizDev) {
        this.bizDevs.remove(bizDev);
        bizDev.setPlacement(null);
        return this;
    }

    public void setBizDevs(Set<BizDev> bizDevs) {
        this.bizDevs = bizDevs;
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
            "}";
    }
}
