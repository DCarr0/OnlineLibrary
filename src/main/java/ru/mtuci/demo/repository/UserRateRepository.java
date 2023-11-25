package ru.mtuci.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.mtuci.demo.models.UserRate;

import java.util.UUID;


public interface UserRateRepository extends JpaRepository<UserRate, Long> {
    Iterable<UserRate> findByUserId(UUID id);
}
