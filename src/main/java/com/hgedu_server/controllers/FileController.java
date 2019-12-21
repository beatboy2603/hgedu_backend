/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.hgedu_server.repositories.IStorageService;

/**
 *
 * @author Administrator
 */
@RestController
public class FileController {
    
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    
    @Autowired
    private IStorageService storage;
    
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storage.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping(value = "/file-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity handleFileUpload(@RequestParam("image") MultipartFile file, 
            @RequestParam("dateCreated") String dateCreated, @RequestParam("userId") Long userId,
            RedirectAttributes redirectAttributes) {
        
        String newFileName = storage.store(file, dateCreated, userId);
  
        logger.info(String.format("File name '%s' uploaded successfully.", file.getOriginalFilename()));

        return ResponseEntity.ok().body("files/" + newFileName);
    }
    
    @DeleteMapping(value = "/files/{filename:.+}")
    public ResponseEntity deleteFile(@PathVariable String filename) {
        logger.info("delete: " + filename);
        try {
            storage.delete(filename);
            return ResponseEntity.ok().build();
        } catch (IOException ex) {
            return ResponseEntity.ok().body(ex);
        }
    }
}
