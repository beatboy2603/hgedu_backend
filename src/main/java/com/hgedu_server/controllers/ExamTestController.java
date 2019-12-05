/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.ExamTest;
import com.hgedu_server.models.Folder;
import com.hgedu_server.models.FolderTest;
import com.hgedu_server.models.Test;
import com.hgedu_server.services.ExamTestService;
import com.hgedu_server.services.FolderService;
import com.hgedu_server.services.TestService;
import java.util.List;
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
public class ExamTestController {
    
    @Autowired
    private ExamTestService examTestService;
    
    @Autowired
    private TestService testService;
    
    @Autowired
    private FolderService folderService;
    
    @GetMapping("/api/examTest/{folderId}/{teacherId}/all")
    public ResponseEntity getFolderTestList(@PathVariable("teacherId") Long teacherId, @PathVariable("folderId") Long folderId) {
        if(folderService.checkFolderExisting(folderId)) {
            List<Folder> childFolderList = folderService.getAllSubfolders(teacherId, folderId);
            List<Test> testList = testService.getAllTestByFolderId(teacherId,folderId);
            FolderTest folderTest = new FolderTest(folderId, childFolderList, testList);
            return ResponseEntity.ok(folderTest);
        } else {
            return ResponseEntity.badRequest().body("Folder not exist");
        }
    }
    
//    @GetMapping("/api/examTest/tests/{examId}/all")
//    public ResponseEntity getFolderTestList(@PathVariable("examId") Long examId) {
//        List<Test> selectedTestList = examTestService.getSelectedExamTests(examId);
//        return ResponseEntity.ok(selectedTestList);
//    }
    
    @PostMapping("/api/examTest")
    public ResponseEntity<?> createExamTest(@Valid @RequestBody List<ExamTest> testList) {
        List<ExamTest> resultList = examTestService.createExamTest(testList);
        return ResponseEntity.ok(resultList);
    }
    
    @GetMapping("/api/examTest/tests/{examId}/all")
    public ResponseEntity getSelectedExamTests(@PathVariable("examId") Long examId) {
        List<Test> selectedTestList = examTestService.getSelectedExamTests(examId);
        return ResponseEntity.ok(selectedTestList);
    }
    
    @DeleteMapping("/api/examTest/{id}")
    public ResponseEntity deleteExamTest(@PathVariable("id") Long id) {
        List<ExamTest> testList = examTestService.getExamTests(id);
        for(ExamTest item: testList) {
            try {
                examTestService.deleteExamTest(item.getId());
            } catch (Exception ex) {
                return new ResponseEntity<>("Fail to delete!", HttpStatus.EXPECTATION_FAILED);
            }
        }
      return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }
}
