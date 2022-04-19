package com.golfkey.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.golfkey.myapp.domain.enumeration.ClubName;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Clubs.
 */
@Entity
@Table(name = "clubs")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Clubs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "clubname")
    private ClubName clubname;

    @ManyToOne
    @JsonIgnoreProperties(value = { "clubs" }, allowSetters = true)
    private ClubStats clubStats;

    @ManyToMany(mappedBy = "clubs")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "clubs" }, allowSetters = true)
    private Set<GolfBag> golfBags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Clubs id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClubName getClubname() {
        return this.clubname;
    }

    public Clubs clubname(ClubName clubname) {
        this.setClubname(clubname);
        return this;
    }

    public void setClubname(ClubName clubname) {
        this.clubname = clubname;
    }

    public ClubStats getClubStats() {
        return this.clubStats;
    }

    public void setClubStats(ClubStats clubStats) {
        this.clubStats = clubStats;
    }

    public Clubs clubStats(ClubStats clubStats) {
        this.setClubStats(clubStats);
        return this;
    }

    public Set<GolfBag> getGolfBags() {
        return this.golfBags;
    }

    public void setGolfBags(Set<GolfBag> golfBags) {
        if (this.golfBags != null) {
            this.golfBags.forEach(i -> i.removeClubs(this));
        }
        if (golfBags != null) {
            golfBags.forEach(i -> i.addClubs(this));
        }
        this.golfBags = golfBags;
    }

    public Clubs golfBags(Set<GolfBag> golfBags) {
        this.setGolfBags(golfBags);
        return this;
    }

    public Clubs addGolfBag(GolfBag golfBag) {
        this.golfBags.add(golfBag);
        golfBag.getClubs().add(this);
        return this;
    }

    public Clubs removeGolfBag(GolfBag golfBag) {
        this.golfBags.remove(golfBag);
        golfBag.getClubs().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Clubs)) {
            return false;
        }
        return id != null && id.equals(((Clubs) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Clubs{" +
            "id=" + getId() +
            ", clubname='" + getClubname() + "'" +
            "}";
    }
}
