package ru.mtuci.demo.models;

import jakarta.persistence.*;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comments")
@ToString
public class Comment{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//При добавлении новой записи позволяет генерировать каждый раз новое значение.
    private UUID id;
    private String commentary;
    private UUID publicationId;
    private String publisherName;
    private LocalDateTime date;
    private Boolean ban;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public UUID getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(UUID publicationId) {
        this.publicationId = publicationId;
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

    public Comment(){super();};

    public Comment(String commentary, UUID publicationId, String publisherName, LocalDateTime date, Boolean ban) {
        super();
        this.commentary = commentary;
        this.publicationId = publicationId;
        this.publisherName = publisherName;
        this.date = date;
        this.ban = ban;
    }
}

