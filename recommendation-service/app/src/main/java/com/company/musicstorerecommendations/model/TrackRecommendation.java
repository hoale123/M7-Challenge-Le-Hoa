package com.company.musicstorerecommendations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="track_recommendation")
public class TrackRecommendation {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "track_recommendation_id")
    private Integer id;
    private Integer track_id;
    private Integer user_id;
    private boolean liked;

    public TrackRecommendation() {
    }

    public TrackRecommendation(Integer track_id, Integer user_id, boolean liked) {
        this.track_id = track_id;
        this.user_id = user_id;
        this.liked = liked;
    }

    public TrackRecommendation(Integer id, Integer track_id, Integer user_id, boolean liked) {
        this.id = id;
        this.track_id = track_id;
        this.user_id = user_id;
        this.liked = liked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrack_id() {
        return track_id;
    }

    public void setTrack_id(Integer track_id) {
        this.track_id = track_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @Override
    public String toString() {
        return "TrackRecommendation{" +
                "id=" + id +
                ", track_id=" + track_id +
                ", user_id=" + user_id +
                ", liked=" + liked +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackRecommendation that = (TrackRecommendation) o;
        return liked == that.liked && Objects.equals(id, that.id) && Objects.equals(track_id, that.track_id) && Objects.equals(user_id, that.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, track_id, user_id, liked);
    }
}
