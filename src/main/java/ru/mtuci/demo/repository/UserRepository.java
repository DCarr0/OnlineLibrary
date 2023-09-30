package ru.mtuci.demo.repository;

import org.springframework.data.repository.CrudRepository; //Встроенный интерфейс, которые имеет все функции: добавить, обновить, удалить и тд
import ru.mtuci.demo.models.User;

public interface UserRepository extends CrudRepository<User,Long> {
}
