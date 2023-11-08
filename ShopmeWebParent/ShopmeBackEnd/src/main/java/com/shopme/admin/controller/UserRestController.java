package com.shopme.admin.controller;

import com.shopme.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;


    @PostMapping("/users/check_email")
    public String checkDuplicateEmail(@Param("email") String email){
        return userService.isEmailUnique(email) ? "OK" : "Duplicated";
    }
}
