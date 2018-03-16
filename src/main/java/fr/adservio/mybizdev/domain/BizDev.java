package fr.adservio.mybizdev.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BizDev.
 */
@Entity
@Table(name = "biz_dev")
public class BizDev implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "surnom", nullable = false)
    private String surnom;

    @OneToMany(mappedBy = "bizDev")
    @JsonIgnore
    private Set<Placement> bizDevPlacements = new HashSet<>();

    /**
     * Another side of the same relationship
     */
    @ApiModelProperty(value = "Another side of the same relationship")
    @ManyToOne
    private Equipe equipe;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurnom() {
        return surnom;
    }

    public BizDev surnom(String surnom) {
        this.surnom = surnom;
        return this;
    }

    public void setSurnom(String surnom) {
        this.surnom = surnom;
    }

    public Set<Placement> getBizDevPlacements() {
        return bizDevPlacements;
    }

    public BizDev bizDevPlacements(Set<Placement> placements) {
        this.bizDevPlacements = placements;
        return this;
    }

    public BizDev addBizDevPlacement(Placement placement) {
        this.bizDevPlacements.add(placement);
        placement.setBizDev(this);
        return this;
    }

    public BizDev removeBizDevPlacement(Placement placement) {
        this.bizDevPlacements.remove(placement);
        placement.setBizDev(null);
        return this;
    }

    public void setBizDevPlacements(Set<Placement> placements) {
        this.bizDevPlacements = placements;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public BizDev equipe(Equipe equipe) {
        this.equipe = equipe;
        return this;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
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
        BizDev bizDev = (BizDev) o;
        if (bizDev.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bizDev.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BizDev{" +
            "id=" + getId() +
            ", surnom='" + getSurnom() + "'" +
            "}";
    }
}
