package com.golf.key.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GolfBag.
 */
@Entity
@Table(name = "golf_bag")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GolfBag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "golfBag")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "attachments", "comments", "clubStats", "golfBag" }, allowSetters = true)
    private Set<Club> clubs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GolfBag id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public GolfBag name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GolfBag user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Club> getClubs() {
        return this.clubs;
    }

    public void setClubs(Set<Club> clubs) {
        if (this.clubs != null) {
            this.clubs.forEach(i -> i.setGolfBag(null));
        }
        if (clubs != null) {
            clubs.forEach(i -> i.setGolfBag(this));
        }
        this.clubs = clubs;
    }

    public GolfBag clubs(Set<Club> clubs) {
        this.setClubs(clubs);
        return this;
    }

    public GolfBag addClub(Club club) {
        this.clubs.add(club);
        club.setGolfBag(this);
        return this;
    }

    public GolfBag removeClub(Club club) {
        this.clubs.remove(club);
        club.setGolfBag(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GolfBag)) {
            return false;
        }
        return id != null && id.equals(((GolfBag) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GolfBag{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
