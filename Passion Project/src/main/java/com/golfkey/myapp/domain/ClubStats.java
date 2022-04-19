package com.golfkey.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @Column(name = "bag_id")
    private Long bagId;

    @Size(max = 20)
    @Column(name = "club_distance", length = 20)
    private String clubDistance;

    @Size(max = 128)
    @Column(name = "comment", length = 128)
    private String comment;

    @OneToMany(mappedBy = "clubStats")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "clubStats", "golfBags" }, allowSetters = true)
    private Set<Clubs> clubs = new HashSet<>();

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

    public Long getBagId() {
        return this.bagId;
    }

    public ClubStats bagId(Long bagId) {
        this.setBagId(bagId);
        return this;
    }

    public void setBagId(Long bagId) {
        this.bagId = bagId;
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

    public String getComment() {
        return this.comment;
    }

    public ClubStats comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<Clubs> getClubs() {
        return this.clubs;
    }

    public void setClubs(Set<Clubs> clubs) {
        if (this.clubs != null) {
            this.clubs.forEach(i -> i.setClubStats(null));
        }
        if (clubs != null) {
            clubs.forEach(i -> i.setClubStats(this));
        }
        this.clubs = clubs;
    }

    public ClubStats clubs(Set<Clubs> clubs) {
        this.setClubs(clubs);
        return this;
    }

    public ClubStats addClubs(Clubs clubs) {
        this.clubs.add(clubs);
        clubs.setClubStats(this);
        return this;
    }

    public ClubStats removeClubs(Clubs clubs) {
        this.clubs.remove(clubs);
        clubs.setClubStats(null);
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
            ", bagId=" + getBagId() +
            ", clubDistance='" + getClubDistance() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
