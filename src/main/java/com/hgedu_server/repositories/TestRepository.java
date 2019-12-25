/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

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
public interface TestRepository extends JpaRepository<Test, Long>{
    
    @Query(value = "SELECT t.* FROM Test t, Folder f WHERE t.teacherId = ?1 AND t.folderId = f.folderId AND f.parentFolderId = ?2 AND isPublic = 0", nativeQuery = true)
    List<Test> getTestsOfFolder(Long teacherId, Long folderId);
    
    @Query(value = "SELECT t.* FROM Test t, ExamTest et WHERE et.examId = ?1 AND t.testId = et.testId ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Test getRandomTestForExam(Long examId);
    
    @Query(value = "select t.* from Test t, ExamTest et where t.testId = et.testId and et.examId = ?1", nativeQuery = true)
    List<Test> getSelectedExamTests(Long examId);
    
    @Query(value = "SELECT t.* FROM Test t, User u WHERE t.testCode = ?1 AND t.teacherId = u.userId AND u.email =  ?2", nativeQuery = true)
    Test getTestForOMR(String testCode, String teacherEmail);

    public Test findByTestCode(String testCode);
}
