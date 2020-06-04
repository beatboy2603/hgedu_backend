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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    private UserService userService;
    @Autowired
    private LinkRequestService linkRequestService;

//    ----- get user info through id -----
//    userService
//    ----- Check and get whether user has link request or not -----
    @GetMapping("/request/{userId}")
    @ResponseBody
    public List<User> getRequest(@PathVariable("userId") Long userId) {
        String getAnUser = userService.getAnUser(userId).get().getEmail();
        return linkRequestService.getUserByRequest(getAnUser);
    }

//    ----- edit user information -----
//    userService
    @PutMapping("/{userId}")
    public ResponseEntity edit(@PathVariable("userId") Long userId, @RequestBody HashMap<String, Object> user) {
        Optional<User> users = userService.getAnUser(userId);
        if (users.isPresent()) {
            User getUser = users.get();
            getUser.setFullName(user.get("name").toString());
            getUser.setEmail(user.get("email").toString());
            getUser.setPhoneNumber(user.get("phone").toString());
            getUser.setDob(user.get("dob").toString());
            System.out.println(user.get("dob").toString());
            getUser.setGender(Boolean.valueOf(String.valueOf(user.get("gender"))));
            getUser.setSchool(user.get("school").toString());
            User savedUser = userService.saveUser(getUser);
            return ResponseEntity.ok(savedUser);
        } else {
            System.out.println("save not done");
            return null;
        }
    }

    //   ----- send a request to link between parent and student -----
    @PostMapping("")
    public Map<String, Object> sendRequest(@RequestBody HashMap<String, String> data) {
        return linkRequestService.checkSendRequest(data.get("parentMail"), data.get("studentMail"));

    }

    @PostMapping("/requestResponse")
    public Map<String, Object> linkResponse(@RequestBody HashMap<String, String> data) {
        Map<String, Object> responseList = new LinkedHashMap<>();
        if (data == null) {
            responseList.put("error", "Không có data, đang thử server ư?");
        } else {
            switch (data.get("status")) {
                case "accept":
                    linkRequestService.acceptRequest(data.get("parentEmail"), data.get("studentEmail"));
                    responseList.put("mess", "Thành công!");
                    return responseList;

                case "refuse":
                    linkRequestService.refuseRequest(data.get("parentEmail"), data.get("studentEmail"));
                    responseList.put("mess", "Thành công!");
                    return responseList;
            }
        }
        return responseList;
    }

}
