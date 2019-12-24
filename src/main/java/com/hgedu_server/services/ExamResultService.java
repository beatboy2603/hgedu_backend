/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.ExamResult;
import com.hgedu_server.repositories.ExamResultRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class ExamResultService {
    
    @Autowired
    private ExamResultRepository examResultRepo;
    
    public List<ExamResult> getExamResultsForStudent(Long examId, Long classId, Long studentId) {
        return examResultRepo.getExamResultsForStudent(examId, classId, studentId);
    }
    
    public Long getNthTrial(Long examId, Long classStudentId) {
        return examResultRepo.getNthTrial(examId, classStudentId);
    }
    
    public ExamResult getExamResultForTest(Long examId, Long classStudentId) {
        return examResultRepo.getExamResultForTest(examId, classStudentId);
    }

    public ExamResult createNewExamResult(ExamResult result) {
        return examResultRepo.save(result);
    }
    
    public ExamResult updateExamResult(ExamResult result) {
        return examResultRepo.save(result);
    }

    public List<ExamResult> getExamResultsForTeacher(Long examId, Long teacherId) {
        return examResultRepo.getExamResultsForTeacher(examId, teacherId);
    }
}
