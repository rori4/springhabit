package org.rangelstoilov.controllers;

import org.rangelstoilov.models.view.user.UserDashboardViewModel;
import org.rangelstoilov.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
public class ChallengeController extends BaseController{
    private final UserService userService;

    @Autowired
    public ChallengeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/challenges/users")
    public ModelAndView users(Principal principal) {
        List<UserDashboardViewModel> allUsers = this.userService.getAllUserAvailableToChallenge(principal.getName());
        return this.view("/challenges/users", "users", allUsers);
    }

    @GetMapping("/challenges/sent")
    public ModelAndView sent(Principal principal) {
        List<UserDashboardViewModel> challengesSent = this.userService.getChallengesSent(principal.getName());
        return this.view("/challenges/sent", "users", challengesSent);
    }

    @GetMapping("/challenges/pending")
    public ModelAndView pending(Principal principal) {
        List<UserDashboardViewModel> challengesPending = this.userService.getChallengesPending(principal.getName());
        return this.view("/challenges/pending", "users", challengesPending);
    }

    @GetMapping("/challenges/accepted")
    public ModelAndView accepted(Principal principal) {
        List<UserDashboardViewModel> challengesAccepted = this.userService.getChallengesAccepted(principal.getName());
        return this.view("/challenges/accepted", "users", challengesAccepted);
    }

    @PostMapping(value = "/api/challenge/send")
    @ResponseBody
    public ResponseEntity<?> sendChallenge(@RequestParam String id, Principal principal) {
        UserDashboardViewModel result = this.userService.sendChallenge(id, principal.getName());
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when performing sending a challenge", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/api/challenge/cancel")
    @ResponseBody
    public ResponseEntity<?> cancelChallenge(@RequestParam String id, Principal principal) {
        UserDashboardViewModel result = this.userService.cancelChallenge(id, principal.getName());
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when canceling a challenge", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/api/challenge/approve")
    @ResponseBody
    public ResponseEntity<?> approveChallenge(@RequestParam String id, Principal principal) {
        UserDashboardViewModel result = this.userService.approveChallenge(id, principal.getName());
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when approving a challenge", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/api/challenge/decline")
    @ResponseBody
    public ResponseEntity<?> declineChallenge(@RequestParam String id, Principal principal) {
        UserDashboardViewModel result = this.userService.declineChallenge(id, principal.getName());
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when declining a challenge", HttpStatus.BAD_REQUEST);
    }
}
