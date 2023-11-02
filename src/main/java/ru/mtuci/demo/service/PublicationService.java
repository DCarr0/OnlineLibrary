package ru.mtuci.demo.service;

import ru.mtuci.demo.details.PublicationDto;
import ru.mtuci.demo.models.Publication;

import java.util.Optional;
import java.util.UUID;

public interface PublicationService {
    PublicationDto addPublication(PublicationDto publicationDto);
    Optional<Publication> findOne(UUID id);
}
