package org.rangelstoilov.controllers.rest;

import com.google.gson.Gson;
import org.rangelstoilov.custom.enums.Status;
import org.rangelstoilov.models.view.todo.ToDoModel;
import org.rangelstoilov.services.todo.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
@PreAuthorize("isAuthenticated()")
public class ToDoController {
    private final Gson gson;
    private final ToDoService toDoService;

    @Autowired
    public ToDoController(Gson gson, ToDoService toDoService) {
        this.gson = gson;
        this.toDoService = toDoService;
    }

    @GetMapping(value = "/todo")
    public @ResponseBody
    String profile(Principal principal) {
        List<ToDoModel> result = this.toDoService.getAllToDos(Status.ACTIVE, principal.getName());
        return
                this.gson.toJson(result);
    }

    @PostMapping(value = "/todo/add")
    public boolean addToDo(@RequestBody ToDoModel todo, Principal principal) {
        this.toDoService.addToDo(todo, principal.getName());
        return true;
    }
}
