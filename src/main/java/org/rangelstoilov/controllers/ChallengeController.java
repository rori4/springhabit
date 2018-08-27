//package org.rangelstoilov.controllers;
//
//import org.rangelstoilov.entities.Challenge;
//import org.rangelstoilov.models.challenge.ChallengeViewModel;
//import org.rangelstoilov.models.view.user.UserDashboardViewModel;
//import org.rangelstoilov.services.challange.ChallengeService;
//import org.rangelstoilov.services.user.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.security.Principal;
//import java.util.List;
//
//@Controller
//@PreAuthorize("isAuthenticated()")
//public class ChallengeController extends BaseController{
//    private final UserService userService;
//    private final ChallengeService challengeService;
//
//    @Autowired
//    public ChallengeController(UserService userService, ChallengeService challengeService) {
//        this.userService = userService;
//        this.challengeService = challengeService;
//    }
//
//    @GetMapping("/challenges/users")
//    public ModelAndView register() {
//        List<UserDashboardViewModel> allUsers = this.userService.getAllUsers();
//        return this.view("/challenges/users", "users", allUsers);
//    }
//
//    @GetMapping("/challenges/pending")
//    public ModelAndView pending(Principal principal) {
//        List<Challenge> allByOpponent = this.challengeService.getAllByOpponent(principal.getName());
//        return this.view("/challenges/pending", "users", allByOpponent);
//    }
//
//    @PostMapping(value = "/api/challenge")
//    @ResponseBody
//    public ResponseEntity<?> challenge(@RequestParam String id, Principal principal){
//        ChallengeViewModel result = this.challengeService.sendChallenge(id, principal.getName());
//        if(result != null){
//            return new ResponseEntity<>(result, HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>("Something went wrong when performing sending a challenge", HttpStatus.BAD_REQUEST);
//    }
//}
