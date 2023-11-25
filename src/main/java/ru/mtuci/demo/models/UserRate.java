package ru.mtuci.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users_rates")
@Data
@AllArgsConstructor
@Builder
public class UserRate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//При добавлении новой записи позволяет генерировать каждый раз новое значение.
    private Long id;
    private UUID userId;
    private UUID publicationId;
    public UserRate() {
        super();
    }

    public UserRate(UUID userId,UUID publicationId) {
        super();
        this.userId=userId;
        this.publicationId=publicationId;
    }
}
