package com.company.musicstorerecommendations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="artist_recommendation")
public class ArtistRecommendation {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "artist_recommendation_id")
    private Integer id;
    private Integer artist_id;
    private Integer user_id;
    private boolean liked;

    public ArtistRecommendation() {
    }

    public ArtistRecommendation(Integer artist_id, Integer user_id, boolean liked) {
        this.artist_id = artist_id;
        this.user_id = user_id;
        this.liked = liked;
    }

    public ArtistRecommendation(Integer id, Integer artist_id, Integer user_id, boolean liked) {
        this.id = id;
        this.artist_id = artist_id;
        this.user_id = user_id;
        this.liked = liked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(Integer artist_id) {
        this.artist_id = artist_id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistRecommendation that = (ArtistRecommendation) o;
        return liked == that.liked && Objects.equals(id, that.id) && Objects.equals(artist_id, that.artist_id) && Objects.equals(user_id, that.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, artist_id, user_id, liked);
    }

    @Override
    public String toString() {
        return "ArtistRecommendation{" +
                "id=" + id +
                ", artist_id=" + artist_id +
                ", user_id=" + user_id +
                ", liked=" + liked +
                '}';
    }
}
