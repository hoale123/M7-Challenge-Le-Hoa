package com.company.musicstorecatalog.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="album")
public class Album implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Integer id;

    private String title;
    private Integer artist_id;
    @Column(name = "release_date")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private Integer label_id;
    @Column(name = "list_price")
    private double listPrice;

    public Album() {
    }

    public Album(String title, Integer artist_id, LocalDate releaseDate, Integer label_id, double listPrice) {
        this.title = title;
        this.artist_id = artist_id;
        this.releaseDate = releaseDate;
        this.label_id = label_id;
        this.listPrice = listPrice;
    }

    public Album(Integer id, String title, Integer artist_id, LocalDate releaseDate, Integer label_id, double listPrice) {
        this.id = id;
        this.title = title;
        this.artist_id = artist_id;
        this.releaseDate = releaseDate;
        this.label_id = label_id;
        this.listPrice = listPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(Integer artist_id) {
        this.artist_id = artist_id;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getLabel_id() {
        return label_id;
    }

    public void setLabel_id(Integer label_id) {
        this.label_id = label_id;
    }

    public double getListPrice() {
        return listPrice;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Double.compare(album.listPrice, listPrice) == 0 && Objects.equals(id, album.id) && Objects.equals(title, album.title) && Objects.equals(artist_id, album.artist_id) && Objects.equals(releaseDate, album.releaseDate) && Objects.equals(label_id, album.label_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, artist_id, releaseDate, label_id, listPrice);
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist_id=" + artist_id +
                ", releaseDate=" + releaseDate +
                ", label_id=" + label_id +
                ", listPrice=" + listPrice +
                '}';
    }
}
