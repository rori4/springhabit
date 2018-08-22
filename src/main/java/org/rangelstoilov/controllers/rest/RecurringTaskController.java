package org.rangelstoilov.controllers.rest;

import com.google.gson.Gson;
import org.rangelstoilov.custom.enums.Period;
import org.rangelstoilov.custom.enums.Status;
import org.rangelstoilov.custom.enums.converter.PeriodConverter;
import org.rangelstoilov.custom.pojo.ValidationErrorBuilder;
import org.rangelstoilov.models.view.recurringTask.RecurringTaskModel;
import org.rangelstoilov.services.recurringTask.RecurringTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class RecurringTaskController {
    private final Gson gson;
    private final RecurringTaskService recurringTaskService;

    @Autowired
    public RecurringTaskController(Gson gson, RecurringTaskService recurringTaskService) {
        this.gson = gson;
        this.recurringTaskService = recurringTaskService;
    }

    @GetMapping(value = "/recurring")
    public @ResponseBody
    String profile(Principal principal) {
        List<RecurringTaskModel> result = this.recurringTaskService.getAllRecurringTasks(Status.ACTIVE, principal.getName());
        result.sort(Comparator.comparing(RecurringTaskModel::getOrderNumber));
        return
                this.gson.toJson(result);
    }

    @PostMapping(value = "/recurring")
    public ResponseEntity<?> addRecurringTask(@Valid @ModelAttribute RecurringTaskModel toDoModel, Principal principal, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        RecurringTaskModel result = this.recurringTaskService.add(toDoModel, principal.getName());
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when adding yor task", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/recurring/done")
    public ResponseEntity<?> markDone(@RequestParam String id, Principal principal){
        boolean result = this.recurringTaskService.markDone(id, principal.getName());
        if(result){
            return new ResponseEntity<>("Recurring Task marked done successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when adding yor task", HttpStatus.BAD_REQUEST);
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Period.class, new PeriodConverter());
    }
}
