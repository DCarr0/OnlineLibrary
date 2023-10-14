package ru.mtuci.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mtuci.demo.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
