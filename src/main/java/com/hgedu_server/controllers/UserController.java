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
}
