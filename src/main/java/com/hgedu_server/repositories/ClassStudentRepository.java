/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.ClassStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */
@Repository
public interface ClassStudentRepository extends JpaRepository<ClassStudent, Long>{
    @Query(value = "SELECT * FROM ClassStudent cs WHERE cs.classId = ?1 AND cs.studentId =?2", nativeQuery = true)
    ClassStudent getByClassIdAndStudentId(Long classId, Long studentId);
}
