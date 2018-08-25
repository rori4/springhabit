package org.rangelstoilov.controllers;

import com.google.gson.Gson;
import org.rangelstoilov.custom.enums.Status;
import org.rangelstoilov.models.view.habit.HabitModel;
import org.rangelstoilov.models.view.user.UserRewardModel;
import org.rangelstoilov.services.habit.HabitService;
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
import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
public class HabitController extends BaseController {
    private final HabitService habitService;
    private final Gson gson;

    @Autowired
    public HabitController(HabitService habitService, Gson gson) {
        this.habitService = habitService;
        this.gson = gson;
    }


    @GetMapping("/habit/details")
    public ModelAndView todoDetails(@RequestParam String id){
        HabitModel model = this.habitService.findById(id);
        return this.view("tasks/habit-details","habit",model);
    }

    @PostMapping("/habit/save")
    public ModelAndView save(@Valid @ModelAttribute("todo") HabitModel model, BindingResult result, Principal principal){
        if (!result.hasErrors()) {
            habitService.add(model, principal.getName());
        } else if (result.hasErrors()) {
            return new ModelAndView("tasks/habit-details", "habit", model);
        }
        return new ModelAndView("redirect:/");
    }

    // API

    @GetMapping(value = "/api/habit")
    @ResponseBody
    public String getAllHabits(@RequestParam String status,Principal principal) {
        List<HabitModel> result = this.habitService.getAllHabitsOrdered(Status.valueOf(status), principal.getName());
        return this.gson.toJson(result);
    }

    @PostMapping(value = "/api/habit", produces={"application/json"})
    @ResponseBody
    public ResponseEntity<?> addHabit(@Valid @ModelAttribute HabitModel habitModel, Principal principal, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(gson.toJson(errors));
        }
        HabitModel result = this.habitService.add(habitModel, principal.getName());
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when adding your habit", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/api/habit/plus")
    @ResponseBody
    public ResponseEntity<?> markPlus(@RequestParam String id, Principal principal){
        UserRewardModel result = this.habitService.markPlus(id, principal.getName());
        if(result != null){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when performing plus on habit", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/api/habit/minus")
    @ResponseBody
    public ResponseEntity<?> markMinus(@RequestParam String id, Principal principal){
        UserRewardModel result = this.habitService.markMinus(id, principal.getName());
        if(result != null){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when performing minus on habit", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/habit/archive")
    @ResponseBody
    public ResponseEntity<?> archive(@RequestParam String id, Principal principal){
        HabitModel result = habitService.archive(id, principal.getName());
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong when archiving yor task", HttpStatus.BAD_REQUEST);
    }
}
