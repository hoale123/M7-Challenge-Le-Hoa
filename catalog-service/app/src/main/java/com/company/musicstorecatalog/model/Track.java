package com.company.musicstorecatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="track")
public class Track {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "track_id")
    private Integer id;
    private Integer album_id;
    private String title;
    @Column(name = "run_time")
    private int runTime;

    public Track() {
    }

    public Track(Integer album_id, String title, int runTime) {
        this.album_id = album_id;
        this.title = title;
        this.runTime = runTime;
    }

    public Track(Integer id, Integer album_id, String title, int runTime) {
        this.id = id;
        this.album_id = album_id;
        this.title = title;
        this.runTime = runTime;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return runTime == track.runTime && Objects.equals(id, track.id) && Objects.equals(album_id, track.album_id) && Objects.equals(title, track.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, album_id, title, runTime);
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", album_id=" + album_id +
                ", title='" + title + '\'' +
                ", runTime=" + runTime +
                '}';
    }
}
