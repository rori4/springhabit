package org.rangelstoilov.repositories;

import org.rangelstoilov.custom.enums.TaskStatus;
import org.rangelstoilov.entities.ToDo;
import org.rangelstoilov.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, String> {

    List<ToDo> findAllByTaskStatus(TaskStatus taskStatus);

    List<ToDo> findAllByOrderNumberGreaterThan(Integer orderNumber);

    List<ToDo> findAllByTaskStatusAndOrderNumberLessThan(TaskStatus taskStatus, Integer orderNumber);

    List<ToDo> findAllByTaskStatusAndOrderNumberGreaterThan(TaskStatus taskStatus, Integer orderNumber);

    List<ToDo> findAllByTaskStatusAndUser(TaskStatus taskStatus, User user);

    Integer countAllByUserAndTaskStatus(User user, TaskStatus taskStatus);

    ToDo findToDoById(String id);

}
