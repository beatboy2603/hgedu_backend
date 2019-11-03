/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.Folder;
import com.hgedu_server.models.User;
import com.hgedu_server.repositories.UserRepository;
import com.hgedu_server.services.FolderService;
import com.hgedu_server.services.UserService;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/api")
public class ApiController {
//    @GetMapping("/hello/{id}")
//    public String hello(@PathVariable int id){
//        return "hello world d";
//    }

    @Autowired
    private FolderService folderService;
    @Autowired
    private UserService userService;

    @GetMapping("/user/{userId}")
    @ResponseBody
    public ResponseEntity show(@PathVariable("userId") int userId) {
        Optional<User> users = userService.getAnUser(userId);
        if (users.isPresent()) {
            return ResponseEntity.ok(users.get());
        } else {
            return null;
        }
    }

    @PutMapping("/user/{userId}")
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

    @GetMapping("/test")
    public Map<String, Object> test() {
        return folderService.getFoldersForNav();
    }

    @GetMapping("/test/{id}")
    @ResponseBody
    public Boolean test(@PathVariable("id") int folderId) {
        return folderService.checkFolderExisting(folderId);
    }

    @GetMapping("/hello")
//    @ResponseBody
    public String hello() {
        return "abc";
    }
}
