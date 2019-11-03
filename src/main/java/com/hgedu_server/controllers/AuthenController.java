/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.services.AuthenService;
import com.hgedu_server.utils.Util;
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
    
//    public void signup(){
//        
//    }
}
