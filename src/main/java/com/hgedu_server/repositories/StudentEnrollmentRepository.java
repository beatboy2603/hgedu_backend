/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.StudentRequest;
import com.hgedu_server.models.User;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Admin
 */
public interface StudentEnrollmentRepository extends JpaRepository<StudentRequest, Integer> {
    @Modifying  
    @Query(value = "insert into StudentRequest(note, teacherEmail, studentId) values(:note, :teacherEmail, :studentId)", nativeQuery = true)
    @Transactional
    void addToEnrollmentRequest(@Param("note") String note, @Param("teacherEmail") String teacherEmail, @Param("studentId") int studentId);
   
    
    @Query(value = "SELECT COUNT(*) FROM StudentRequest WHERE teacherEmail = ?1 AND studentId = ?2", nativeQuery = true)
    int checkDuplicateEnrolledUser(String teacherEmail, int studentId);
    
    List<StudentRequest> findByTeacherEmailAndStudentId(String teacherEmail, int studentId);
    
    @Query(value = "SELECT note FROM `StudentRequest` WHERE studentId IN (SELECT studentId FROM StudentRequest WHERE teacherEmail = ?1 ) AND teacherEmail = ?1", nativeQuery = true)
    List<String> getStudentNote(String teacherEmail);
    
    @Modifying  
    @Query(value = "delete from StudentRequest where teacherEmail =:teacherEmail and studentId = :studentId", nativeQuery = true)
    @Transactional
    void deleteByTeacherEmailAndStudentId( @Param("teacherEmail") String teacherEmail, @Param("studentId") int studentId);
}

