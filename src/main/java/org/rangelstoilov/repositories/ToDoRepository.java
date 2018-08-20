package org.rangelstoilov.repositories;

import org.rangelstoilov.entities.ToDo;
import org.rangelstoilov.custom.enums.Status;
import org.rangelstoilov.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, String> {
    List<ToDo> findAllByStatus(Status status);

    List<ToDo> findAllByStatusAndUser(Status status, User user);

    Integer countAllByUserAndStatus(User user, Status status);
}
