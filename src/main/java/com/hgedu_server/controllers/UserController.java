/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.User;
import com.hgedu_server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author DELL
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService; 
    
    @GetMapping("/allUsers")
    public Iterable<User> getAllUsers()
    {
        return userService.findAll();
    }
    
    @GetMapping("/countUsers")
    public int countUsers(){
        return userService.countUsers();
    }
    
    @PostMapping("/addMod")
    public User addMod(@RequestBody User user){
        return userService.addMod(user);
    }
    
    @PostMapping("/banUsers")
    public User banUser(@RequestBody User user){
        System.out.println(user.getUserId());
        return userService.banUser(user);
    }
    
    @PostMapping("/unBanUsers")
    public User unBanUser(@RequestBody User user){
        return userService.unBan(user);
    }
    
    @PostMapping("/banForever")
    public User banForever(@RequestBody User user){
        return userService.banUserForever(user);
    }
}
