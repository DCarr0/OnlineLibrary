package ru.mtuci.demo.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comments")
public class Сomment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//При добавлении новой записи позволяет генерировать каждый раз новое значение.

    private UUID id;
    private String commentary;
    private UUID publicationId;
    private UUID publisherId;
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

    public UUID getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(UUID publisherId) {
        this.publisherId = publisherId;
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
}

