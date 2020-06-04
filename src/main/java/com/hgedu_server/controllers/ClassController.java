/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.services.ClassService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.hgedu_server.models.Class;
import com.hgedu_server.models.ClassExam;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Administrator
 */
@RestController
public class ClassController {
    
    @Autowired
    private ClassService service;
    
    @GetMapping("/api/class/{teacherId}")
    public ResponseEntity getAllClassesForTeacher(@PathVariable("teacherId") Long teacherId) {
        System.out.println("teacherId: " + teacherId);
        
        List<Class> classList = service.getAllClassesForTeacher(teacherId);
        if(classList.isEmpty()) {
            return ResponseEntity.badRequest().body("No class found");
        } else {
            return ResponseEntity.ok(classList);
        }
    }
    
    @GetMapping("/api/class/student/{studentId}")
    public ResponseEntity getAllClassesForStudent(@PathVariable("studentId") Long studentId) {    
        List<Class> classList = service.getAllClassesForStudent(studentId);
        if(classList.isEmpty()) {
            return ResponseEntity.badRequest().body("No class found");
        } else {
            return ResponseEntity.ok(classList);
        }
    }
    
}
