/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.ClassStudent;
import com.hgedu_server.repositories.ClassStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class ClassStudentService {
    
    @Autowired
    private ClassStudentRepository classStudentRepository;
    
    
    public Iterable<ClassStudent> getStudentsByClassId(long classId) {
        return classStudentRepository.findByClassId(classId);
    }
    
    public Iterable<ClassStudent> getClassesByStudentId(long studentId) {
        return classStudentRepository.findByStudentId(studentId);
    }
    
    public void deleteById(long studentId) {
        classStudentRepository.deleteByStudentId(studentId);
    }
    
    public ClassStudent getByClassIdAndStudentId(Long classId, Long studentId) {
        return classStudentRepository.getByClassIdAndStudentId(classId, studentId);
    }
    
}