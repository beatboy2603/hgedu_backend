/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.Folder;
import com.hgedu_server.models.TestToWord;
import com.hgedu_server.services.FolderService;
import com.hgedu_server.services.TestToWordService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private TestToWordService testToWordService;
    
//    @GetMapping("/test")
//    public Map<String, Object> test(){
//        return folderService.getFoldersForNav();
//    }
    
    @GetMapping("/test2")
    public List<String> test2() {
        return testToWordService.getAllContent();
    }
    
    @GetMapping("/test2/export")
    public void format() {
        testToWordService.formatWord();
    }
    
    @GetMapping("/test2/import")
    public ResponseEntity importData() {
       TestToWord test = testToWordService.saveContent();
       return ResponseEntity.ok(test);
    }
    
    @GetMapping("/hello")
//    @ResponseBody
    public String hello(){
        return "abc2";
    }
}
