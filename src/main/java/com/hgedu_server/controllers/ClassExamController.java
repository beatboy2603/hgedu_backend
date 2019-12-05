/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.ClassExam;
import com.hgedu_server.models.Class;
import com.hgedu_server.services.ClassExamService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
public class ClassExamController {
    @Autowired
    private ClassExamService classExamService;
    
     @PostMapping("/api/examClass") 
    public ResponseEntity<?> createExam(@Valid @RequestBody List<ClassExam> classList) {
        List<ClassExam> resultList = classExamService.createClassExam(classList);
        return ResponseEntity.ok(resultList);
    }
    
    @GetMapping("api/examClass/classes/{examId}/all")
    public ResponseEntity<?> getSelectedExamClasses(@PathVariable("examId") Long examId) {
        List<Class> selectedClasses = classExamService.getSelectedExamClasses(examId);
        return ResponseEntity.ok(selectedClasses);
    }
    
    @DeleteMapping("/api/examClass/{id}")
    public ResponseEntity deleteExamClass(@PathVariable("id") Long id) {
        List<ClassExam> classList = classExamService.getExamClasses(id);
        for(ClassExam item: classList) {
            try {
                classExamService.deleteExamClass(item.getId());
            } catch (Exception ex) {
                return new ResponseEntity<>("Fail to delete!", HttpStatus.EXPECTATION_FAILED);
            }
        }
      return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }
}
