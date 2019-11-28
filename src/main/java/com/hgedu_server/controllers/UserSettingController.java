/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.User;
import com.hgedu_server.services.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/user")
public class UserSettingController {

    @Autowired
    private UserService userService;
    
    
//    ----- get user info through id -----
//    userService
    @GetMapping("/{userId}")
    @ResponseBody
    public ResponseEntity show(@PathVariable("userId") int userId) {
        Optional<User> users = userService.getAnUser(userId);
        if (users.isPresent()) {
            return ResponseEntity.ok(users.get());
        } else {
            return null;
        }
    }

    
//    ----- edit user information -----
//    userService
    @PutMapping("/{userId}")
    public ResponseEntity edit(@PathVariable("userId") int userId, @RequestBody User user) {
        Optional<User> users = userService.getAnUser(userId);
        if (users.isPresent()) {
            System.out.println(users);
            User getUser = users.get();
            getUser.setFullName(user.getFullName());
            getUser.setEmail(user.getEmail());
            getUser.setPhoneNumber(user.getPhoneNumber());
            getUser.setGender(user.isGender());
            User savedUser = userService.saveUser(getUser);
            return ResponseEntity.ok(savedUser);
        } else {
            System.out.println("save not done");
            return null;
        }
    }

}