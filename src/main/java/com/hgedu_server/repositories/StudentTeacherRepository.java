/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.StudentTeacher;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author admin
 */
public interface StudentTeacherRepository extends JpaRepository<StudentTeacher, Long>{
    
    Iterable<StudentTeacher> findByIsConnectedAndTeacherId(boolean isConnected, long teacherId);
    
    Iterable<StudentTeacher> findByIsConnectedAndStudentId(boolean isConnected, long studentId);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM StudentTeacher WHERE studentId = ?1", nativeQuery = true)
    void deleteByStudentId(long studentId);
    
    
    
}
