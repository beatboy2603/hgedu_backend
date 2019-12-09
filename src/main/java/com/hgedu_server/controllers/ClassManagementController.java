/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.services.ClassManagementService;
import com.hgedu_server.models.Class;
import com.hgedu_server.models.ClassStudent;
import com.hgedu_server.services.ClassStudentService;
import com.hgedu_server.services.MapValidationErrorService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/api/class")
public class ClassManagementController {

    @Autowired
    private ClassManagementService classManagementService;

    @Autowired
    private ClassStudentService classStudentService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @GetMapping("/{teacherId}")
    public ResponseEntity<?> getClasses(@PathVariable int teacherId) {
        Iterable<Class> classes = classManagementService.findClassesByTeacherId(teacherId);
        return new ResponseEntity<Iterable<Class>>(classes, HttpStatus.OK);
    }
    
    @GetMapping("students/{classId}")
    public ResponseEntity<?> getStudents(@PathVariable long classId) {
        Iterable<ClassStudent> students = classStudentService.getStudentsByClassId(classId);
        return new ResponseEntity<Iterable<ClassStudent>>(students, HttpStatus.OK);
    }
   

    @PostMapping("")
    public ResponseEntity<?> addClass(@Valid @RequestBody Class cl, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        } else {
            Class class1 = classManagementService.addClass(cl);
            return new ResponseEntity<Class>(cl, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{classId}")
    public ResponseEntity<?> deleteById(@PathVariable long classId) {
        classManagementService.deleteById(classId);
        return new ResponseEntity<String>("Class with ID " + classId + " was deleted", HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String hello() {
        return "class desu";
    }
}
