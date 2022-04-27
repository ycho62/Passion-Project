package com.golf.key.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ClubStats.
 */
@Entity
@Table(name = "club_stats")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClubStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "club_distance", length = 20)
    private String clubDistance;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "attachments", "comments", "clubStats" }, allowSetters = true)
    private GolfBag golfBag;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClubStats id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClubDistance() {
        return this.clubDistance;
    }

    public ClubStats clubDistance(String clubDistance) {
        this.setClubDistance(clubDistance);
        return this;
    }

    public void setClubDistance(String clubDistance) {
        this.clubDistance = clubDistance;
    }

    public GolfBag getGolfBag() {
        return this.golfBag;
    }

    public void setGolfBag(GolfBag golfBag) {
        this.golfBag = golfBag;
    }

    public ClubStats golfBag(GolfBag golfBag) {
        this.setGolfBag(golfBag);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClubStats)) {
            return false;
        }
        return id != null && id.equals(((ClubStats) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClubStats{" +
            "id=" + getId() +
            ", clubDistance='" + getClubDistance() + "'" +
            "}";
    }
}
