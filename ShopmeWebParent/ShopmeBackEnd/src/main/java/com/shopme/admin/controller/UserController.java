package com.shopme.admin.controller;

import com.shopme.admin.exception.UserNotFoundException;
import com.shopme.admin.service.UserService;
import com.shopme.admin.util.FileUploadUtil;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
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
    public String saveNewUser(User user, RedirectAttributes redirectAttributes, @RequestParam("image")MultipartFile multipartFile) throws IOException {
        System.out.println(user.toString());
        System.out.println(multipartFile.getOriginalFilename());
        if(!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhotos(fileName);
            User savedUser = userService.saveUser(user);
            String uploadDir = "user-photos/"+savedUser.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
        }else{
            if(user.getPhotos().isEmpty()) user.setPhotos(null);
            User savedUser = userService.saveUser(user);
        }

//        userService.saveUser(user);
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

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id,RedirectAttributes redirectAttributes) throws UserNotFoundException{
        try{
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message","User is successfully Deleted with the id: "+id);
            return "redirect:/users";
        } catch (UserNotFoundException ex){
            redirectAttributes.addFlashAttribute("message","Could not find any user  with the id: "+id);
            return "redirect:/users";
        }

    }

    @GetMapping("/users/enable/{id}")
    public String enableUser(@PathVariable("id") Integer id,RedirectAttributes redirectAttributes) throws UserNotFoundException{
        try{
            userService.userEnable(id);
            redirectAttributes.addFlashAttribute("message","User Enabled with the id: "+id);
            return "redirect:/users";
        }catch (UserNotFoundException ex){
            redirectAttributes.addFlashAttribute("message","Could not found user with id: "+id);
            return "redirect:/users";
        }
    }

    @GetMapping("/users/disable/{id}")
    public String disableUser(@PathVariable("id") Integer id,RedirectAttributes redirectAttributes) throws UserNotFoundException{
        try{
            userService.userDisable(id);
            redirectAttributes.addFlashAttribute("message","User Disabled with the id: "+id);
            return "redirect:/users";
        }catch (UserNotFoundException ex){
            redirectAttributes.addFlashAttribute("message","Could not found user with id: "+id);
            return "redirect:/users";
        }
    }


}
