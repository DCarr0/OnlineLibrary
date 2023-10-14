package ru.mtuci.demo.repository;

import org.springframework.data.repository.CrudRepository;
import ru.mtuci.demo.models.Сomment;
import java.rmi.server.UID;

public interface CommentRepository extends CrudRepository<Сomment,UID> {
}
