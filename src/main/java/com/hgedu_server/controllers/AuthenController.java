/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.User;
import com.hgedu_server.services.AuthenService;
import com.hgedu_server.utils.Util;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/api/authen")
public class AuthenController {

    @Autowired
    private AuthenService authenService;

    @PostMapping
    public Map<String, Object> authen(@RequestBody String token) {
        return authenService.authen(token);
    }

    @PostMapping("/signup")
    public Map<String, Object> signup(@RequestBody HashMap<String, String> data) {
        Map<String, Object> responseList = new LinkedHashMap<>();
        String token = data.get("token");
        boolean isExisting = authenService.checkExistence(token);
        if(isExisting){
            responseList.put("message", "already exist");
            return responseList;
        }
        User user = new User();
        if (data.get("phoneNumber") != null) {
            user.setPhoneNumber(data.get("phoneNumber"));
        } else {
            responseList.put("message", "signup failed");
            return responseList;
        }
        if (data.get("dob") != null) {
            user.setDob(data.get("dob"));
        }
        if (data.get("gender") != null) {
            user.setGender(data.get("gender").equals("true"));
        }
        if (data.get("school") != null) {
            user.setSchool(data.get("school"));
        }
        if (data.get("roleId") != null) {
            user.setRoleId(Integer.parseInt(data.get("roleId")));
        }
        return authenService.signup(token, user);
    }

}
