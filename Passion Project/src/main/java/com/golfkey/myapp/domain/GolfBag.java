package com.golfkey.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
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

    @Column(name = "bag_id")
    private Long bagId;

    @Column(name = "user_name")
    private String userName;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToMany
    @JoinTable(
        name = "rel_golf_bag__clubs",
        joinColumns = @JoinColumn(name = "golf_bag_id"),
        inverseJoinColumns = @JoinColumn(name = "clubs_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "clubStats", "golfBags" }, allowSetters = true)
    private Set<Clubs> clubs = new HashSet<>();

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

    public Long getBagId() {
        return this.bagId;
    }

    public GolfBag bagId(Long bagId) {
        this.setBagId(bagId);
        return this;
    }

    public void setBagId(Long bagId) {
        this.bagId = bagId;
    }

    public String getUserName() {
        return this.userName;
    }

    public GolfBag userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Set<Clubs> getClubs() {
        return this.clubs;
    }

    public void setClubs(Set<Clubs> clubs) {
        this.clubs = clubs;
    }

    public GolfBag clubs(Set<Clubs> clubs) {
        this.setClubs(clubs);
        return this;
    }

    public GolfBag addClubs(Clubs clubs) {
        this.clubs.add(clubs);
        clubs.getGolfBags().add(this);
        return this;
    }

    public GolfBag removeClubs(Clubs clubs) {
        this.clubs.remove(clubs);
        clubs.getGolfBags().remove(this);
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
            ", bagId=" + getBagId() +
            ", userName='" + getUserName() + "'" +
            "}";
    }
}
