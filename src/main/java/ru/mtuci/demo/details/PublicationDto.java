package ru.mtuci.demo.details;

import jdk.jshell.Snippet;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicationDto {
    private UUID id;
    private String titile;
    private String genre;
    private String link;
    private LocalDateTime requestTime;
    private String publisherName;

}
