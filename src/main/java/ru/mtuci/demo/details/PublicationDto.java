package ru.mtuci.demo.details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PublicationDto {
    private UUID id;
    private String titile;
    private String genre;
    private String link;
    private LocalDateTime requestTime;
    private String publisherName;
}
