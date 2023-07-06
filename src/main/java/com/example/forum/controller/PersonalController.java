package com.example.forum.controller;

import com.example.forum.model.User;
import com.example.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonalController {
    @Autowired
    UserService userService;

    @GetMapping("personalPage")
    public String function1 (@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("name", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("pswd", user.getPassword());
        return "personalPage";
    }
    @PostMapping("updateProfile")
    public String function2 (@AuthenticationPrincipal User user, Model model, @RequestParam (name = "email") String email, @RequestParam (name = "password") String pswd) {
        model.addAttribute("name", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("pswd", user.getPassword());
        if (!userService.existsByEmail(email)) {
            userService.updateUser(email,pswd,user);
        }
        else {
            model.addAttribute("answer5", "This mail is already use");
        }
        return "personalPage";
    }
}
