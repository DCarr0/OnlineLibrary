package ru.mtuci.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mtuci.demo.models.Publication;

import java.util.UUID;


public interface PublicationRepository extends JpaRepository<Publication, UUID> {
}
