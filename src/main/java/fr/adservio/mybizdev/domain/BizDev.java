package fr.adservio.mybizdev.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
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

    @ManyToOne
    private Placement placement;

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

    public Placement getPlacement() {
        return placement;
    }

    public BizDev placement(Placement placement) {
        this.placement = placement;
        return this;
    }

    public void setPlacement(Placement placement) {
        this.placement = placement;
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
