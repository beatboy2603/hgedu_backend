/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.ExamResult;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Administrator
 */
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {
    
    @Query(value = "SELECT er.* FROM ExamResult er, ExamTest et, Exam e WHERE er.examTestId = et.id AND et.examId = e.id and et.examId = ?1 and er.studentId = ?2 ORDER BY er.id DESC", nativeQuery = true)
    List<ExamResult> getExamResultsForStudent(Long examId, Long studentId);
    
    @Query(value = "SELECT er.nthTrial FROM ExamResult er, ExamTest et, Exam e WHERE er.examTestId = et.id and et.examId = ?1 and er.studentId = ?2 ORDER BY er.id DESC LIMIT 1", nativeQuery = true)
    Long getNthTrial(Long examId, Long studentId);
    
    @Query(value = "SELECT er.* FROM ExamResult er, ExamTest et, Exam e WHERE er.examTestId = et.id and et.examId = ?1 and er.studentId = ?2 ORDER BY er.id DESC LIMIT 1", nativeQuery = true)
    ExamResult getExamResultForTest(Long examId, Long studentId);
}
