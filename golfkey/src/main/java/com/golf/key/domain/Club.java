package com.golf.key.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.golf.key.domain.enumeration.ClubType;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Club.
 */
@Entity
@Table(name = "club")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Club implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "club_type")
    private ClubType clubType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "clubs" }, allowSetters = true)
    private GolfBag golfBag;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Club id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClubType getClubType() {
        return this.clubType;
    }

    public Club clubType(ClubType clubType) {
        this.setClubType(clubType);
        return this;
    }

    public void setClubType(ClubType clubType) {
        this.clubType = clubType;
    }

    public GolfBag getGolfBag() {
        return this.golfBag;
    }

    public void setGolfBag(GolfBag golfBag) {
        this.golfBag = golfBag;
    }

    public Club golfBag(GolfBag golfBag) {
        this.setGolfBag(golfBag);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Club)) {
            return false;
        }
        return id != null && id.equals(((Club) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Club{" +
            "id=" + getId() +
            ", clubType='" + getClubType() + "'" +
            "}";
    }
}
