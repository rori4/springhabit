package org.rangelstoilov.controllers;

import com.google.gson.Gson;
import org.rangelstoilov.custom.enums.TaskStatus;
import org.rangelstoilov.models.view.todo.ToDoModel;
import org.rangelstoilov.models.view.user.UserRewardModel;
import org.rangelstoilov.services.todo.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
public class ToDoController extends BaseController {
    private final Gson gson;
    private final ToDoService toDoService;

    @Autowired
    public ToDoController(Gson gson, ToDoService toDoService) {
        this.gson = gson;
        this.toDoService = toDoService;
    }

    @GetMapping("/todo/details")
    public ModelAndView todoDetails(@RequestParam String id){
        ToDoModel model = this.toDoService.findById(id);
        return this.view("tasks/todo-details","todo",model);
    }

    @PostMapping("/todo/save")
    public ModelAndView save(@Valid @ModelAttribute("todo") ToDoModel model, BindingResult result, Principal principal, Errors errors){
        if (!result.hasErrors()) {
            toDoService.add(model, principal.getName());
        } else if (result.hasErrors()) {
            return new ModelAndView("tasks/todo-details", "todo", model);
        }
        return new ModelAndView("redirect:/");
    }

    // API

    @GetMapping(value = "/api/todo")
    @ResponseBody
    public String profile(@RequestParam String status,Principal principal) {
        List<ToDoModel> result = this.toDoService.getAllToDos(TaskStatus.valueOf(status), principal.getName());
        result.sort(Comparator.comparing(ToDoModel::getOrderNumber));
        return
                this.gson.toJson(result);
    }

    @PostMapping(value = "/api/todo")
    @ResponseBody
    public ResponseEntity<?> addToDo(@Valid @ModelAttribute ToDoModel toDoModel, Errors errors, Principal principal) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(gson.toJson(errors));
        }
        ToDoModel result = this.toDoService.add(toDoModel, principal.getName());
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when adding yor task", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/api/todo/activate")
    @ResponseBody
    public ResponseEntity<?> activate(@RequestParam String id, Principal principal){
        ToDoModel result = toDoService.activate(id, principal.getName());
        if(result != null){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when adding yor task", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/api/todo/done")
    @ResponseBody
    public ResponseEntity<?> done(@RequestParam String id, Principal principal){
        UserRewardModel result = this.toDoService.done(id, principal.getName());
        if(result != null){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when adding yor task", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/todo/archive")
    @ResponseBody
    public ResponseEntity<?> archive(@RequestParam String id, Principal principal){
        ToDoModel result = toDoService.archive(id, principal.getName());
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong when adding yor task", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/todo/delete")
    @ResponseBody
    public ResponseEntity<?> delete(@RequestParam String id, Principal principal){
        ToDoModel result = toDoService.delete(id, principal.getName());
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong when adding yor task", HttpStatus.BAD_REQUEST);
    }

}
