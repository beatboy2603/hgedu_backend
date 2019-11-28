/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.LinkRequest;
import com.hgedu_server.models.User;
import com.hgedu_server.services.LinkRequestService;
import com.hgedu_server.services.UserService;
import java.util.HashMap;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private LinkRequestService linkRequestService;
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

//    ----- Check and get whether user has link request or not -----
    @GetMapping("/request/{userId}")
    @ResponseBody
    public ResponseEntity getRequest(@PathVariable("userId") int userId) {
        Optional<LinkRequest> linkRequest = linkRequestService.getRequest(userId);
        if (linkRequest.isPresent()) {
            return ResponseEntity.ok(linkRequest.get());
        } else {
            System.out.println("no data");
            return null;
        }
    }

//    ----- edit user information -----
//    userService
    @PutMapping("/{userId}")
    public ResponseEntity edit(@PathVariable("userId") int userId, @RequestBody User user) {
        Optional<User> users = userService.getAnUser(userId);
        if (users.isPresent()) {
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

    //   ----- send a request to link between parent and student -----
    @PostMapping("")
    public void addRequest(@RequestBody HashMap<String, String> data) {
        System.out.println("email phụ huynh: " + data.get("parentMail"));
        System.out.println("email học sinh: " + data.get("studentMail"));
        linkRequestService.addRequest(data.get("parentMail"), data.get("studentMail"));
    }

}
