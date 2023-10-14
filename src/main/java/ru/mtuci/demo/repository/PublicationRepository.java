package ru.mtuci.demo.repository;

import org.springframework.data.repository.CrudRepository; //Встроенный интерфейс, которые имеет все функции: добавить, обновить, удалить и тд
import ru.mtuci.demo.models.Publication;
import java.rmi.server.UID;


public interface PublicationRepository extends CrudRepository<Publication, UID>{
}
