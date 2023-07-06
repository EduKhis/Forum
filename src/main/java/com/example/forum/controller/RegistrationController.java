package com.example.forum.controller;

//import ch.qos.logback.core.model.Model;
import com.example.forum.model.Role;
import com.example.forum.model.User;
import com.example.forum.repository.UserRepository;
import com.example.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class RegistrationController {
    @Autowired
    UserService userService;
    @GetMapping("registration")
    public String function1 (Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }
    @PostMapping("registration")
    public String function2 (Model model, @ModelAttribute("user") User user) {
        if (user.getEmail().isEmpty()) {
            model.addAttribute("response", "Input Email");
        }
        if (user.getUsername().isEmpty()) {
            model.addAttribute("response", "Input User name");
        }
        if (user.getPassword().isEmpty()) {
            model.addAttribute("response", "Input Password");
        }
        else {
            if (!userService.saveUser(user.getUsername(), user.getPassword(), user.getEmail())) {
                model.addAttribute("answer5", "This User name is used to");
                return "registration";
            }
        }
        return "/login";
    }
    @GetMapping("/active/{code}")
    public String function3(@PathVariable("code") String a, Model model){
        if (userService.activeUser(a)) {
            model.addAttribute("response", "Code activated");
        }
        else {
            model.addAttribute("response", "Code unactivated");
        }
        return "login";
    }

}
