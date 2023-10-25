package com.controller;

import com.dao.UserDAO;
import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showAllUsers(ModelMap model) {

        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsr",allUsers);
        return "all-users";
    }

    @GetMapping("/addNewUser")
    public String addNewUser (Model model) {
        User user = new User();
        model.addAttribute("user",user);

        return "user-info";
    }

    @GetMapping("/saveUser")
    public  String saveUser(@ModelAttribute("user") User user) {

        userService.saveUser(user);

        return "redirect:/";
    }

    @GetMapping("/{id}/updateInfo")
    public String updateUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user",userService.getUserId(id));
        return "user-info";
    }

    @GetMapping("/{id}/deleteUser")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/";
    }

}
