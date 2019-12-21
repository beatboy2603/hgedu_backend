/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.TeacherRequest;
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
public interface TeacherRequestRepository extends JpaRepository<TeacherRequest, Integer> {

    @Modifying
    @Query(value = "delete from TeacherRequest where teacherEmail =:teacherEmail and studentId = :studentId", nativeQuery = true)
    @Transactional
    void removeByTeacherEmailAndStudentId(@Param("teacherEmail") String teacherEmail, @Param("studentId") int studentId);

    @Query(value = "SELECT COUNT(*) FROM TeacherRequest WHERE teacherEmail = ?1 AND studentId = ?2", nativeQuery = true)
    int checkDuplicateEnrolledUser(String teacherEmail, int studentId);

    TeacherRequest findByTeacherEmailAndStudentId(String teacherEmail, int studentId);
}
