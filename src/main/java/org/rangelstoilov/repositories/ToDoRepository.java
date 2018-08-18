package org.rangelstoilov.repositories;

import org.rangelstoilov.entities.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo, String> {
}
