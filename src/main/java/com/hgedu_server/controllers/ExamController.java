/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.Exam;
import com.hgedu_server.models.ExamProgress;
import com.hgedu_server.services.ExamService;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
public class ExamController {
    @Autowired
    private ExamService examService;
    
    @PostMapping("/api/exam") 
    public ResponseEntity<?> createExam(@Valid @RequestBody Exam exam) {
        Exam createdExam = examService.createExam(exam);
        return ResponseEntity.ok(createdExam);
    }
    
    @PutMapping("/api/exam") 
    public ResponseEntity<?> updateExam(@Valid @RequestBody Exam exam) {
        Exam updatedExam = examService.createExam(exam);
        return ResponseEntity.ok(updatedExam);
    }
    
    @GetMapping("/api/exam/{examId}/progress") 
    public ResponseEntity<?> getProgress(@PathVariable("examId") Long examId) {
        String progress = examService.getExamProgress(examId);
        return ResponseEntity.ok(progress);
    }
    
    @GetMapping("/api/exam/schedule/{teacherId}/all") 
    public ResponseEntity<?> getExamSchedule(@PathVariable("teacherId") Long teacherId) {
        Map<String, List<ExamProgress>> examMap = examService.getExamSchedule(teacherId);
        return ResponseEntity.ok(examMap);
    }
    
    @GetMapping("/api/exam/history/{teacherId}/all") 
    public ResponseEntity<?> getExamHistory(@PathVariable("teacherId") Long teacherId) {
        Map<String,List<ExamProgress>> examMap = examService.getExamHistory(teacherId);
        return ResponseEntity.ok(examMap);
    }
    
    @DeleteMapping("/api/exam/{id}")
    public ResponseEntity deleteNews(@PathVariable("id") Long id) {
      try {
        examService.deleteExam(id);
      } catch (Exception e) {
        return new ResponseEntity<>("Fail to delete!", HttpStatus.EXPECTATION_FAILED);
      }

      return new ResponseEntity<>("Exam has been deleted!", HttpStatus.OK);
    }
}
