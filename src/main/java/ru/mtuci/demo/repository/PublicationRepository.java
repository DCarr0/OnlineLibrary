package ru.mtuci.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mtuci.demo.models.Publication;

import java.util.UUID;


public interface PublicationRepository extends JpaRepository<Publication, UUID> {
    Iterable<Publication> findByTitleContainingIgnoreCase(String title) throws Exception;
    Iterable<Publication> findByGenreContainingIgnoreCase(String title) throws Exception;
}
