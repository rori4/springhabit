package org.rangelstoilov.controllers;

import org.rangelstoilov.models.view.user.UserDashboardViewModel;
import org.rangelstoilov.models.view.user.UserRegisterModel;
import org.rangelstoilov.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController extends BaseController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/register")
    public ModelAndView register() {
        return this.view("/users/register", "user", new UserRegisterModel());
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("user") @Valid UserRegisterModel model, BindingResult result) {
        if (!result.hasErrors()) {
            userService.register(model);
        } else if (result.hasErrors()) {
            return new ModelAndView("users/register", "user", model);
        }
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/forgotten")
    public ModelAndView forgotten() {
        return new ModelAndView("/users/forgotten");
    }

    //API endpoints

    @GetMapping(value = "/api/user")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public ResponseEntity<?> getUserData(Principal principal) {
        UserDashboardViewModel userDashboardDataByEmail = userService.getUserDashboardDataByEmail(principal.getName());
        return new ResponseEntity<>(userDashboardDataByEmail, HttpStatus.OK);
    }

    @GetMapping("/challenges/users")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView users(Principal principal) {
        List<UserDashboardViewModel> allUsers = this.userService.getAllUserAvailableToChallenge(principal.getName());
        return this.view("/challenges/users", "users", allUsers);
    }

    @GetMapping("/challenges/sent")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView sent(Principal principal) {
        List<UserDashboardViewModel> challengesSent = this.userService.getChallengesSent(principal.getName());
        return this.view("/challenges/sent", "users", challengesSent);
    }

    @GetMapping("/challenges/pending")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView pending(Principal principal) {
        List<UserDashboardViewModel> challengesPending = this.userService.getChallengesPending(principal.getName());
        return this.view("/challenges/pending", "users", challengesPending);
    }

    @GetMapping("/challenges/accepted")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView accepted(Principal principal) {
        List<UserDashboardViewModel> challengesAccepted = this.userService.getChallengesAccepted(principal.getName());
        return this.view("/challenges/accepted", "users", challengesAccepted);
    }

    //API

    @PostMapping(value = "/api/challenge")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public ResponseEntity<?> sendChallenge(@RequestParam String id, Principal principal){
        UserDashboardViewModel result = this.userService.sendChallenge(id, principal.getName());
        if(result != null){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when performing sending a challenge", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/api/challenge/cancel")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public ResponseEntity<?> cancelChallenge(@RequestParam String id, Principal principal){
        UserDashboardViewModel result = this.userService.cancelChallenge(id, principal.getName());
        if(result != null){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when canceling a challenge", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/api/challenge/approve")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public ResponseEntity<?> approveChallenge(@RequestParam String id, Principal principal){
        UserDashboardViewModel result = this.userService.approveChallenge(id, principal.getName());
        if(result != null){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when approving a challenge", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/api/challenge/decline")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public ResponseEntity<?> declineChallenge(@RequestParam String id, Principal principal){
        UserDashboardViewModel result = this.userService.declineChallenge(id, principal.getName());
        if(result != null){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when declining a challenge", HttpStatus.BAD_REQUEST);
    }
}
