/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.repositories.ClassManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hgedu_server.models.Class;
import com.hgedu_server.repositories.ClassStudentRepository;

/**
 *
 * @author admin
 */
@Service
public class ClassManagementService {

    @Autowired
    private ClassManagementRepository classManagementRepository;
    
    @Autowired
    private ClassStudentRepository classStudentRepository;

    public Iterable<Class> findClassesByTeacherId(int teacherId) {
        return classManagementRepository.findByTeacherId(teacherId);
    }

    public Class addClass(Class cl) {
        return classManagementRepository.save(cl);
    }
    
    public void deleteById(long classId) {
        Class cl = classManagementRepository.findById(classId);
        classManagementRepository.delete(cl);
    }

}
