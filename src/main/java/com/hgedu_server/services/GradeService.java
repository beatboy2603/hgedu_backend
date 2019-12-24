/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.Grade;
import com.hgedu_server.repositories.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class GradeService {
    @Autowired
    private GradeRepository gradeRepo;
    
    public Grade createNewGrade(Grade grade) {
        return gradeRepo.save(grade);
    }
    
    public Grade updateGrade(Grade grade) {
        return gradeRepo.save(grade);
    }
    
    public Grade getStudentGradeByClassStudentIdAndPowerLevel(Long classStudentId, Long powerLevel) {
        return gradeRepo.getStudentGradeByClassStudentIdAndPowerLevel(classStudentId, powerLevel);
    }
}
