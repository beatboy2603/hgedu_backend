/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.ExamResult;
import com.hgedu_server.services.ExamResultService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
public class ExamResultController {
    
    @Autowired
    private ExamResultService resultService;
    
    @GetMapping("/api/exam/{examId}/result/{studentId}")
    public ResponseEntity<?> getExamsForClass(@PathVariable("examId") Long examId, @PathVariable("studentId") Long studentId) {
        List<ExamResult> resultList = resultService.getExamResultsForStudent(examId, studentId);
        return ResponseEntity.ok(resultList);
    }
    
    @GetMapping("/api/exam/{examId}/attempt/{studentId}")
    public ResponseEntity<?> getNthTrial(@PathVariable("examId") Long examId, @PathVariable("studentId") Long studentId) {
        Long attempt = resultService.getNthTrial(examId, studentId);
        return ResponseEntity.ok(attempt);
    }
    
     @GetMapping("/api/exam/{examId}/{studentId}/latest")
    public ResponseEntity<?> getExamResultForTest(@PathVariable("examId") Long examId, @PathVariable("studentId") Long studentId) {
        ExamResult result = resultService.getExamResultForTest(examId, studentId);
        return ResponseEntity.ok(result);
    }
}
