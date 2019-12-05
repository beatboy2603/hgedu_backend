/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.ClassExam;
import com.hgedu_server.models.Class;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */
@Repository
public interface ClassExamRepository extends JpaRepository<ClassExam, Long> {
    @Query(value = "select ce.* from ClassExam ce where ce.examId = ?1", nativeQuery = true)
    List<ClassExam> getExamClasses(Long examId);
}
