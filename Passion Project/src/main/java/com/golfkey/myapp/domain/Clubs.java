package com.golfkey.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.golfkey.myapp.domain.enumeration.ClubType;
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
    @Column(name = "clubtype")
    private ClubType clubtype;

    @OneToMany(mappedBy = "clubs")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "clubs" }, allowSetters = true)
    private Set<Attachment> attachments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private GolfBag golfBag;

    @ManyToOne
    private ClubStats clubStats;

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

    public ClubType getClubtype() {
        return this.clubtype;
    }

    public Clubs clubtype(ClubType clubtype) {
        this.setClubtype(clubtype);
        return this;
    }

    public void setClubtype(ClubType clubtype) {
        this.clubtype = clubtype;
    }

    public Set<Attachment> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        if (this.attachments != null) {
            this.attachments.forEach(i -> i.setClubs(null));
        }
        if (attachments != null) {
            attachments.forEach(i -> i.setClubs(this));
        }
        this.attachments = attachments;
    }

    public Clubs attachments(Set<Attachment> attachments) {
        this.setAttachments(attachments);
        return this;
    }

    public Clubs addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
        attachment.setClubs(this);
        return this;
    }

    public Clubs removeAttachment(Attachment attachment) {
        this.attachments.remove(attachment);
        attachment.setClubs(null);
        return this;
    }

    public GolfBag getGolfBag() {
        return this.golfBag;
    }

    public void setGolfBag(GolfBag golfBag) {
        this.golfBag = golfBag;
    }

    public Clubs golfBag(GolfBag golfBag) {
        this.setGolfBag(golfBag);
        return this;
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
            ", clubtype='" + getClubtype() + "'" +
            "}";
    }
}
