package com.shopme.admin.controller;

import com.shopme.admin.exception.UserNotFoundException;
import com.shopme.admin.service.UserService;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping("users")
//    public String getAllUser(){
//        return "users";
//    }
    @GetMapping("/users")
    public String getAllUser(Model model){
        List<User> users = userService.getAllUser();
        model.addAttribute("listUsers",users);
        return "users";
    }

    @GetMapping("/users/new")
    public String createNewUser(Model model){
        User user = new User();
        user.setEnabled(true);
        model.addAttribute("pageTitle","Create New User");
        model.addAttribute("user",user);
        List<Role> roles = userService.getAllRole();
        model.addAttribute("roles",roles);
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveNewUser(User user, RedirectAttributes redirectAttributes){
        System.out.println(user.toString());
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("message","The User has been saved successfully.");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Integer id,RedirectAttributes redirectAttributes,Model model) throws UserNotFoundException {
        try{
            User user = userService.get(id);
            model.addAttribute("user",user);
            model.addAttribute("pageTitle","Edit User");
            List<Role> roles = userService.getAllRole();
            model.addAttribute("roles",roles);
            model.addAttribute("edit",true);
            return "user_form";
        } catch (UserNotFoundException ex){
            redirectAttributes.addFlashAttribute("message","Could not find any user  with the id: "+id);
            return "redirect:/users";
        }

    }


}
