/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author admin
 */
public interface ExamRepository extends JpaRepository<Exam, Long>{
    
    @Query(value = "SELECT e.id, e.title, e.startEntryTime, e.code, ce.id as classExamId, ce.classId FROM Exam e JOIN ClassExam ce ON e.id = ce.examId ORDER BY ID ASC", nativeQuery = true)
    Iterable<Object[]> findExamsByClassId();
}
