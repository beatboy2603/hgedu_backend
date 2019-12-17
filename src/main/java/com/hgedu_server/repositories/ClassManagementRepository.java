/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.Class;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author admin
 */
public interface ClassManagementRepository extends JpaRepository<Class, Long> {

    Iterable<Class> findByTeacherId(int teacherId);
    
    @Query(value = "SELECT * FROM Class c INNER JOIN ClassStudent ct ON c.id = ct.classId WHERE studentid = ?1", nativeQuery = true)
    Iterable<Class> findClassByStudentId(long studentId);
    
}
