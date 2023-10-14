package ru.mtuci.demo.repository;

import org.springframework.data.repository.CrudRepository; //Встроенный интерфейс, которые имеет все функции: добавить, обновить, удалить и тд
import ru.mtuci.demo.models.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
