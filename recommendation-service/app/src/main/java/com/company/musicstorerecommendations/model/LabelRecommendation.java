package com.company.musicstorerecommendations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="label_recommendation")
public class LabelRecommendation {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "label_recommendation_id")
    private Integer id;
    private Integer label_id;
    private Integer user_id;
    private boolean liked;

    public LabelRecommendation() {
    }

    public LabelRecommendation(Integer label_id, Integer user_id, boolean liked) {
        this.label_id = label_id;
        this.user_id = user_id;
        this.liked = liked;
    }

    public LabelRecommendation(Integer id, Integer label_id, Integer user_id, boolean liked) {
        this.id = id;
        this.label_id = label_id;
        this.user_id = user_id;
        this.liked = liked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLabel_id() {
        return label_id;
    }

    public void setLabel_id(Integer label_id) {
        this.label_id = label_id;
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
        LabelRecommendation that = (LabelRecommendation) o;
        return liked == that.liked && Objects.equals(id, that.id) && Objects.equals(label_id, that.label_id) && Objects.equals(user_id, that.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label_id, user_id, liked);
    }

    @Override
    public String toString() {
        return "LabelRecommendation{" +
                "id=" + id +
                ", label_id=" + label_id +
                ", user_id=" + user_id +
                ", liked=" + liked +
                '}';
    }
}
