package org.rangelstoilov.controllers.rest;

import com.google.gson.Gson;
import org.rangelstoilov.custom.enums.Status;
import org.rangelstoilov.custom.pojo.ValidationErrorBuilder;
import org.rangelstoilov.models.view.todo.ToDoModel;
import org.rangelstoilov.services.todo.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api")
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
        result.sort(Comparator.comparing(ToDoModel::getOrderNumber));
        return
                this.gson.toJson(result);
    }

    @PostMapping(value = "/todo")
    public ResponseEntity<?> addToDo(@Valid @ModelAttribute ToDoModel toDoModel, Principal principal, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        ToDoModel result = this.toDoService.add(toDoModel, principal.getName());
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when adding yor task", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/todo/done")
    public ResponseEntity<?> markDone(@RequestParam String id, Principal principal){
        boolean result = this.toDoService.markDone(id, principal.getName());
        if(result){
            return new ResponseEntity<>("Task marked done successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when adding yor task", HttpStatus.BAD_REQUEST);
    }
}
