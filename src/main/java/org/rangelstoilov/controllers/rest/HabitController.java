package org.rangelstoilov.controllers.rest;

import com.google.gson.Gson;
import org.rangelstoilov.custom.enums.Status;
import org.rangelstoilov.custom.pojo.ValidationErrorBuilder;
import org.rangelstoilov.models.view.habit.HabitModel;
import org.rangelstoilov.services.habit.HabitService;
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
public class HabitController {
    private final Gson gson;
    private final HabitService habitService;

    @Autowired
    public HabitController(Gson gson, HabitService habitService) {
        this.gson = gson;
        this.habitService = habitService;
    }

    @GetMapping(value = "/habit")
    public @ResponseBody String getAllHabits(Principal principal) {
        List<HabitModel> result = this.habitService.getAllHabits(Status.ACTIVE, principal.getName());
        result.sort(Comparator.comparing(HabitModel::getOrderNumber));
        return
                this.gson.toJson(result);
    }

    @PostMapping(value = "/habit", produces={"application/json; charset=UTF-8"})
    public ResponseEntity<?> addHabit(@Valid @ModelAttribute HabitModel habitModel, Principal principal, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        HabitModel result = this.habitService.add(habitModel, principal.getName());
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when adding yor task", HttpStatus.BAD_REQUEST);
    }
}
