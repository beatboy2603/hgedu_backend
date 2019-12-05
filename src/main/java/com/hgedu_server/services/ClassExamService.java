/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.ClassExam;
import com.hgedu_server.repositories.ClassExamRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hgedu_server.models.Class;
import com.hgedu_server.repositories.ClassRepository;

/**
 *
 * @author Administrator
 */
@Service
public class ClassExamService {
    
    @Autowired
    private ClassExamRepository classExamRepo;
    
    @Autowired
    private ClassRepository classRepo;
    
    public ClassExam assignExamToClass(ClassExam item) {
        return classExamRepo.save(item);
    }
    
    public List<ClassExam> createClassExam(List<ClassExam> classList) {
        List<ClassExam> resultList = new ArrayList<>();
        classList.forEach((item) -> {
            resultList.add(assignExamToClass(item));
        });
        return resultList;
    }
    
    public List<Class> getSelectedExamClasses(Long examId) {
        return classRepo.getSelectedExamClasses(examId);
    }
    
    public List<ClassExam> getExamClasses(Long examId) {
        return classExamRepo.getExamClasses(examId);
    }
    
    public void deleteExamClass(Long examClassId) throws Exception{
        try {
            classExamRepo.deleteById(examClassId);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
