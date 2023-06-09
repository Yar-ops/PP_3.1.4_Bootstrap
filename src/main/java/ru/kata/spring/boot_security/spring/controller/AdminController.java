package ru.kata.spring.boot_security.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.spring.model.User;
import ru.kata.spring.boot_security.spring.service.RoleService;
import ru.kata.spring.boot_security.spring.service.UserService;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String adminPage(@ModelAttribute("user") User user, Model model, Principal principal) {
        User curentUser = userService.getByeMail(principal.getName());
        model.addAttribute("currentUser", curentUser);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getRoles());
        return "adminPanel";
    }

    @PatchMapping("/editUser")
    public String patchAdminRedactor(@ModelAttribute("user") User user) {
        userService.editUser(user);
        return "redirect:users";
    }

    @DeleteMapping("/deleteUser/{id}")
    public String adminDelete(@PathVariable("id") Long id) {
        userService.deleteUser((id));
        return "redirect:/admin/users";
    }

    @PostMapping("/addUser")
    public String registrationPost(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:users";
    }
}
