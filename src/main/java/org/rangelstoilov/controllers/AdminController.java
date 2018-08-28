package org.rangelstoilov.controllers;

import com.google.gson.Gson;
import org.rangelstoilov.models.role.RoleModel;
import org.rangelstoilov.models.view.user.UserAdminViewModel;
import org.rangelstoilov.services.role.RoleService;
import org.rangelstoilov.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController extends BaseController {
    private final UserService userService;
    private final RoleService roleService;
    private final Gson gson;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, Gson gson) {
        this.userService = userService;
        this.roleService = roleService;
        this.gson = gson;
    }



    @GetMapping("/admin/users")
    public ModelAndView users() {
        List<UserAdminViewModel> allUsers = this.userService.getAllUsersWithRoles();
        return this.view("/admin/users", "users", allUsers);
    }


    @GetMapping("api/admin/roles")
    @ResponseBody
    public ResponseEntity<?> getRoles() {
        List<RoleModel> roles = this.roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/api/admin/user")
    @ResponseBody
    public ResponseEntity<?> getUser(@RequestParam String id) {
        UserAdminViewModel user = this.userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/api/admin/user")
    @ResponseBody
    public ResponseEntity<?> saveUserEdit(@Valid @RequestBody UserAdminViewModel userAdminViewModel, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(gson.toJson(errors));
        }
        UserAdminViewModel result = this.userService.saveUserEdit(userAdminViewModel);
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong when adding yor task", HttpStatus.BAD_REQUEST);
    }

}
