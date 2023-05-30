package com.example.demo.controllers;
import com.example.demo.services.UserService;
import com.example.demo.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private final UserService userService;
    @Autowired
    public RegistrationController( UserService userService)
    {
        this.userService = userService;
    }
    @GetMapping("/registration")
    public String registration()
    {
        System.out.println("aaaa");
        return "registration";
    }

    @PostMapping("/registration")
    public String adduser(User user, Model model) {
        try {
            userService.addUser(user);
            return "redirect:/login";
        } catch (Exception ex) {
            model.addAttribute("message", "User exists");
            return "registration";
        }
    }

    }
