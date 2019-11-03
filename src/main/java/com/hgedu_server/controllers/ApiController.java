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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    
    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String uploadFileName = file.getOriginalFilename();
        uploadFileName = uploadFileName.toLowerCase().replaceAll("([a-zA-Z0-9\\s_\\\\.\\-\\(\\):])+(.xlsx)$", "excelFile.xlsx");
        try (FileOutputStream fout = new FileOutputStream(new File(uploadFileName))) {
            fout.write(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FIle has uploaded successfully";
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
        return "hello2";
    }
}
