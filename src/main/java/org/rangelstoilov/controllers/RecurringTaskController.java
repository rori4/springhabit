package org.rangelstoilov.controllers;

import com.google.gson.Gson;
import org.rangelstoilov.custom.enums.Period;
import org.rangelstoilov.custom.enums.TaskStatus;
import org.rangelstoilov.custom.enums.converter.PeriodConverter;
import org.rangelstoilov.models.view.recurringTask.RecurringTaskModel;
import org.rangelstoilov.models.view.user.UserRewardModel;
import org.rangelstoilov.services.recurringTask.RecurringTaskService;
import org.rangelstoilov.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
public class RecurringTaskController extends BaseController {
    private final Gson gson;
    private final RecurringTaskService recurringTaskService;

    @Autowired
    public RecurringTaskController(Gson gson, RecurringTaskService recurringTaskService, UserService userService) {
        this.gson = gson;
        this.recurringTaskService = recurringTaskService;
    }

    @GetMapping("/recurring/details")
    public ModelAndView todoDetails(@RequestParam String id){
        RecurringTaskModel model = this.recurringTaskService.findById(id);
        return this.view("tasks/recurring-details","recurring",model);
    }

    @PostMapping("/recurring/save")
    public ModelAndView save(@Valid @ModelAttribute("todo") RecurringTaskModel model, BindingResult result, Principal principal){
        if (!result.hasErrors()) {
            recurringTaskService.add(model, principal.getName());
        } else if (result.hasErrors()) {
            return new ModelAndView("tasks/recurring-details", "recurring", model);
        }
        return new ModelAndView("redirect:/");
    }

    //API

    @GetMapping(value = "/api/recurring")
    @ResponseBody
    public String profile(@RequestParam String status,Principal principal) {
        List<RecurringTaskModel> result = this.recurringTaskService.getAllRecurringTasks(TaskStatus.valueOf(status), principal.getName());
        result.sort(Comparator.comparing(RecurringTaskModel::getOrderNumber));
        return
                this.gson.toJson(result);
    }

    @PostMapping(value = "/api/recurring")
    @ResponseBody
    public ResponseEntity<?> addRecurringTask(@Valid @ModelAttribute RecurringTaskModel toDoModel, Principal principal, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(gson.toJson(errors));
        }
        RecurringTaskModel result = this.recurringTaskService.add(toDoModel, principal.getName());
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when adding yor task", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/api/recurring/done")
    @ResponseBody
    public ResponseEntity<?> markDone(@RequestParam String id, Principal principal){
        UserRewardModel userRewardModel = this.recurringTaskService.markDone(id, principal.getName());
        if(userRewardModel != null){
            return new ResponseEntity<>(userRewardModel, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when adding yor task", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/recurring/archive")
    public ResponseEntity<?> archive(@RequestParam String id, Principal principal){
        RecurringTaskModel result = recurringTaskService.archive(id, principal.getName());
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong when adding yor task", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/recurring/delete")
    public ResponseEntity<?> delete(@RequestParam String id, Principal principal){
        RecurringTaskModel result = recurringTaskService.delete(id, principal.getName());
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong when adding yor task", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/recurring/activate")
    public ResponseEntity<?> activate(@RequestParam String id, Principal principal){
        RecurringTaskModel result = recurringTaskService.activate(id, principal.getName());
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong when adding yor task", HttpStatus.BAD_REQUEST);
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Period.class, new PeriodConverter());
    }
}
