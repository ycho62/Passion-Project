package com.golf.key.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.golf.key.domain.enumeration.ClubTypes;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "clubs")
    private ClubTypes clubs;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "golfBag")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "golfBag" }, allowSetters = true)
    private Set<Attachment> attachments = new HashSet<>();

    @OneToMany(mappedBy = "golfBag")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "golfBag" }, allowSetters = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "golfBag")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "golfBag" }, allowSetters = true)
    private Set<ClubStats> clubStats = new HashSet<>();

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

    public ClubTypes getClubs() {
        return this.clubs;
    }

    public GolfBag clubs(ClubTypes clubs) {
        this.setClubs(clubs);
        return this;
    }

    public void setClubs(ClubTypes clubs) {
        this.clubs = clubs;
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

    public Set<Attachment> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        if (this.attachments != null) {
            this.attachments.forEach(i -> i.setGolfBag(null));
        }
        if (attachments != null) {
            attachments.forEach(i -> i.setGolfBag(this));
        }
        this.attachments = attachments;
    }

    public GolfBag attachments(Set<Attachment> attachments) {
        this.setAttachments(attachments);
        return this;
    }

    public GolfBag addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
        attachment.setGolfBag(this);
        return this;
    }

    public GolfBag removeAttachment(Attachment attachment) {
        this.attachments.remove(attachment);
        attachment.setGolfBag(null);
        return this;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setGolfBag(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setGolfBag(this));
        }
        this.comments = comments;
    }

    public GolfBag comments(Set<Comment> comments) {
        this.setComments(comments);
        return this;
    }

    public GolfBag addComment(Comment comment) {
        this.comments.add(comment);
        comment.setGolfBag(this);
        return this;
    }

    public GolfBag removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setGolfBag(null);
        return this;
    }

    public Set<ClubStats> getClubStats() {
        return this.clubStats;
    }

    public void setClubStats(Set<ClubStats> clubStats) {
        if (this.clubStats != null) {
            this.clubStats.forEach(i -> i.setGolfBag(null));
        }
        if (clubStats != null) {
            clubStats.forEach(i -> i.setGolfBag(this));
        }
        this.clubStats = clubStats;
    }

    public GolfBag clubStats(Set<ClubStats> clubStats) {
        this.setClubStats(clubStats);
        return this;
    }

    public GolfBag addClubStats(ClubStats clubStats) {
        this.clubStats.add(clubStats);
        clubStats.setGolfBag(this);
        return this;
    }

    public GolfBag removeClubStats(ClubStats clubStats) {
        this.clubStats.remove(clubStats);
        clubStats.setGolfBag(null);
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
            ", clubs='" + getClubs() + "'" +
            "}";
    }
}
