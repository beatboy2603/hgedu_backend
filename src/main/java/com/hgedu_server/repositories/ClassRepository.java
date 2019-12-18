/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hgedu_server.models.Class;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */
@Repository
public interface ClassRepository extends JpaRepository<Class, Long>{
    List<Class> findAllByTeacherId(int teacherId);
    
    @Query(value = "select c.* from Class c, ClassExam ce where c.id = ce.classId and ce.examId = ?1", nativeQuery = true)
    List<Class> getSelectedExamClasses(Long examId);
    
    @Query(value = "select c.* from Class c, ClassStudent cs where c.id = cs.classId and cs.studentId = ?1", nativeQuery = true)
    List<Class> getClassesForStudent(Long studentId);
}
