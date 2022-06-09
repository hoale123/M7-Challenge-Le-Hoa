package com.company.musicstorerecommendations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="album_recommendation")
public class AlbumRecommendation {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "album_recommendation_id")
    private Integer id;
    private Integer album_id;
    private Integer user_id;
    private boolean liked;

    public AlbumRecommendation() {
    }

    public AlbumRecommendation(Integer album_id, Integer user_id, boolean liked) {
        this.album_id = album_id;
        this.user_id = user_id;
        this.liked = liked;
    }

    public AlbumRecommendation(Integer id, Integer album_id, Integer user_id, boolean liked) {
        this.id = id;
        this.album_id = album_id;
        this.user_id = user_id;
        this.liked = liked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(Integer album_id) {
        this.album_id = album_id;
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
        AlbumRecommendation that = (AlbumRecommendation) o;
        return liked == that.liked && Objects.equals(id, that.id) && Objects.equals(album_id, that.album_id) && Objects.equals(user_id, that.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, album_id, user_id, liked);
    }

    @Override
    public String toString() {
        return "AlbumRecommendation{" +
                "id=" + id +
                ", album_id=" + album_id +
                ", user_id=" + user_id +
                ", liked=" + liked +
                '}';
    }
}
