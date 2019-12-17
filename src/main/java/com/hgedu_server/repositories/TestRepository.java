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
    
    @Query(value = "select * from Test where teacherId = ?1 and folderId = ?2 and isPublic = 0", nativeQuery = true)
    List<Test> getTestsOfFolder(Long teacherId, Long folderId);
    
    @Query(value = "select t.* from Test t, ExamTest et where t.testId = et.testId and et.examId = ?1", nativeQuery = true)
    List<Test> getSelectedExamTests(Long examId);

    public Test findByTestCode(String testCode);
}
