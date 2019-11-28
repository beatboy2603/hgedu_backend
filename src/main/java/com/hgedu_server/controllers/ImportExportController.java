/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.services.TestToWordService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/testimportexport")
public class ImportExportController {

    @Autowired
    private TestToWordService testToWordService;

    @GetMapping("/hello")
    public String Hello() {
        return "alo 1 2 3 4";
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String uploadFileName = file.getOriginalFilename();
        uploadFileName = uploadFileName.toLowerCase().replaceAll("([a-zA-Z0-9\\s_\\\\.\\-\\(\\):])+(.xlsx)$", "read3.xlsx");
        try (FileOutputStream fout = new FileOutputStream(new File(uploadFileName))) {
            fout.write(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FIle has uploaded successfully";
    }

    @GetMapping("/import")
    public String importData() {
        testToWordService.getQuestions();
        return "import ok";
    }
    
    @GetMapping("/export")
    public String exportData() {
        testToWordService.formatWord();
        return "export ok";
    }
    
//    @GetMapping("/test")
//    public String test() {
//        testToWordService.test2();
//        return "test ok";
//    }

    @GetMapping("/download")
    public ResponseEntity<Object> downloadFile() {
        try {
            File file = new File("format_file.docx");
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            
            ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
            return responseEntity;
            
        } catch (FileNotFoundException ex) {
            return new ResponseEntity<>("error occurred", HttpStatus.INTERNAL_SERVER_ERROR);	
        }

    }
}
