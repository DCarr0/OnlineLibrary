package ru.mtuci.demo.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "publications")
@ToString
@Setter
@Getter
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//При добавлении новой записи позволяет генерировать каждый раз новое значение.
    private UUID id;
    private String title;
    private String genre;
    private String publisherName;
    private LocalDateTime date;
    private Boolean ban;
    private String link;
    private String description;
    private Integer rate;



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getBan() {
        return ban;
    }

    public void setBan(Boolean ban) {
        this.ban = ban;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    public Integer getRate() {return rate;}
    public void setRate(Integer rate) {this.rate = rate;}
    public Publication() {
        super();
    }

    public Publication(String title, String genre, String publisherName, LocalDateTime date, Boolean ban, String link, String description, Integer rate) {
        super();
        this.title = title;
        this.genre = genre;
        this.publisherName = publisherName;
        this.date = date;
        this.ban = ban;
        this.link = link;
        this.description = description;
        this.rate = rate;
    }
}
