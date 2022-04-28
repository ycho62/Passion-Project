package com.golf.key.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.golf.key.domain.enumeration.ClubType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @OneToMany(mappedBy = "club")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "club" }, allowSetters = true)
    private Set<Attachment> attachments = new HashSet<>();

    @OneToMany(mappedBy = "club")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "club" }, allowSetters = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "club")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "club" }, allowSetters = true)
    private Set<ClubStats> clubStats = new HashSet<>();

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

    public Set<Attachment> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        if (this.attachments != null) {
            this.attachments.forEach(i -> i.setClub(null));
        }
        if (attachments != null) {
            attachments.forEach(i -> i.setClub(this));
        }
        this.attachments = attachments;
    }

    public Club attachments(Set<Attachment> attachments) {
        this.setAttachments(attachments);
        return this;
    }

    public Club addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
        attachment.setClub(this);
        return this;
    }

    public Club removeAttachment(Attachment attachment) {
        this.attachments.remove(attachment);
        attachment.setClub(null);
        return this;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setClub(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setClub(this));
        }
        this.comments = comments;
    }

    public Club comments(Set<Comment> comments) {
        this.setComments(comments);
        return this;
    }

    public Club addComment(Comment comment) {
        this.comments.add(comment);
        comment.setClub(this);
        return this;
    }

    public Club removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setClub(null);
        return this;
    }

    public Set<ClubStats> getClubStats() {
        return this.clubStats;
    }

    public void setClubStats(Set<ClubStats> clubStats) {
        if (this.clubStats != null) {
            this.clubStats.forEach(i -> i.setClub(null));
        }
        if (clubStats != null) {
            clubStats.forEach(i -> i.setClub(this));
        }
        this.clubStats = clubStats;
    }

    public Club clubStats(Set<ClubStats> clubStats) {
        this.setClubStats(clubStats);
        return this;
    }

    public Club addClubStats(ClubStats clubStats) {
        this.clubStats.add(clubStats);
        clubStats.setClub(this);
        return this;
    }

    public Club removeClubStats(ClubStats clubStats) {
        this.clubStats.remove(clubStats);
        clubStats.setClub(null);
        return this;
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
