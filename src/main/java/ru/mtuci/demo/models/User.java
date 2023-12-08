package ru.mtuci.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//При добавлении новой записи позволяет генерировать каждый раз новое значение.
    private UUID id;
    private String name;
    private String password;
    private String email;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean requestToRedactor;
    private LocalDateTime date;
    private Boolean ban;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserRole getUserRole() {
        return this.role != null ? this.role : UserRole.USER;
    }
}
