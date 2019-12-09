/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;
import com.hgedu_server.models.Class;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author admin
 */
public interface ClassManagementRepository extends JpaRepository<Class, Long>{
    
    Iterable<Class> findByTeacherId(int teacherId);
    
    Class findById(long classId);
    
}
