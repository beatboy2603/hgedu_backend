/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.ExamTest;
import com.hgedu_server.models.Test;
import com.hgedu_server.repositories.ExamTestRepository;
import com.hgedu_server.repositories.TestRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class ExamTestService {
    @Autowired
    private ExamTestRepository examTestRepo;
    
   @Autowired
    private TestRepository testRepo;
    
    public ExamTest assignTestToExam(ExamTest item) {
        return examTestRepo.save(item);
    }
    
    public List<ExamTest> createExamTest(List<ExamTest> testList) {
        List<ExamTest> resultList = new ArrayList<>();
        testList.forEach(item -> 
                resultList.add(assignTestToExam(item))
        );
        return resultList;
    }
    
    public ExamTest getExamTestById(Long examTestId) {
        return examTestRepo.getExamTestById(examTestId);
    }
    
    public ExamTest getRandomTestIdForExam(Long examId) {
        return examTestRepo.getRandomTestIdForExam(examId);
    }
    
    public List<Test> getSelectedExamTests(Long examId) {
        return testRepo.getSelectedExamTests(examId);
    }
    
    public List<ExamTest> getExamTests(Long examId) {
        return examTestRepo.getExamTests(examId);
    }
    
    public void deleteExamTest(Long examTestId) throws Exception{
        try {
            examTestRepo.deleteById(examTestId);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
