/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.Setting;
import com.hgedu_server.services.SettingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
public class SettingController {
    @Autowired
    private SettingService settingService;
    
    @GetMapping("/api/powers")
    public ResponseEntity<?> getPowers() {
        List<Setting> powers = settingService.getPowers();
        return ResponseEntity.ok(powers);
    }
}
