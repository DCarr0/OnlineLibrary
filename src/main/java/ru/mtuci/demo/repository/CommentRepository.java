package ru.mtuci.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mtuci.demo.models.Сomment;

import java.rmi.server.UID;

public interface CommentRepository extends JpaRepository<Сomment,UID> {
}
