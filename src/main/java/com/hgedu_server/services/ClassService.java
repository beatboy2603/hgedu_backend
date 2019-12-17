/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.repositories.ClassRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hgedu_server.models.Class;
import java.util.List;

/**
 *
 * @author Administrator
 */
@Service
public class ClassService {
    
    @Autowired
    private ClassRepository classRepo;
    
    public List<Class> getAllClassesForTeacher(Long teacherId) {
        return classRepo.findAllByTeacherId(teacherId);
    }

    public List<Class> getAllClassesForStudent(Long studentId) {
        return classRepo.getClassesForStudent(studentId);
    }
    
}
