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
import java.util.Date;
import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.json.JSONObject;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("/test")
    public Date test(@RequestBody HashMap<String, String> data) {
//        System.out.println(date);
//        HashMap<String, Date> json = (HashMap<String, Date>) date;
//        Date a = json.get("date");
//        System.out.println(a);
//        Long mls = Long.parseLong((String) date.get("date"));
        System.out.println(data.get("date"));
        
//            System.out.println(data.get("date"));
//            Object obj = data.get("date");
//            System.out.println((Date) obj);
//        System.out.println(date.getTime());
        return new Date();
    }

    @GetMapping("/hello")
//    @ResponseBody
    public String hello() {
        return "abc2";
    }

}
