/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.ParentStudent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author admin
 */
public interface ParentStudentRepository extends JpaRepository<ParentStudent, Long>{

    List<ParentStudent> findByStudentIdAndParentId(Long parentId, Long studentId);
    
//    ParentStudent findByStudentId(long studentId);
    
    
    
    
}
