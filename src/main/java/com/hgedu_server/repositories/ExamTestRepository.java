/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.ExamTest;
import com.hgedu_server.models.Test;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */
@Repository
public interface ExamTestRepository extends JpaRepository<ExamTest, Long>{
    @Query(value = "select et.* from ExamTest et where et.examId = ?1", nativeQuery = true)
    List<ExamTest> getExamTests(Long examId);
    
     @Query(value = "SELECT et.* FROM ExamTest et, Exam e WHERE e.id = ?1 AND e.id = et.examId ORDER BY RAND() LIMIT 1", nativeQuery = true)
    ExamTest getRandomTestIdForExam(Long examId);
    
    @Query(value = "SELECT et.* FROM ExamTest et WHERE et.id = ?1", nativeQuery = true)
    ExamTest getExamTestById(Long examTestId);
}
