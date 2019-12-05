/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.Exam;
import com.hgedu_server.models.ExamProgress;
import com.hgedu_server.repositories.ExamRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class ExamService {
    
    @Autowired
    private ExamRepository examRepo;
    
    public Exam createExam(Exam exam) {
        return examRepo.save(exam);
    }
    
    public Map<String,List<ExamProgress>> getExamSchedule(Long teacherId) {
        List<Exam> examList = null;
        List<ExamProgress> examProgressList = null;
        Map<String, List<ExamProgress>> examScheduleMap = new HashMap<>();
        List<String> examScheduleDates = examRepo.getExamScheduleDates(teacherId);
        for(String date: examScheduleDates) {
            examList = examRepo.getExamSchedule(teacherId, date);
            examProgressList = new ArrayList<>();
            for(Exam exam: examList){
                String progress = examRepo.getExamProgress(exam.getId());
                examProgressList.add(new ExamProgress(exam, progress));
            }
            examScheduleMap.put(date, examProgressList);
        }
        return examScheduleMap;
    }
    
    public Map<String,List<ExamProgress>> getExamHistory(Long teacherId) {
        List<Exam> examList = null;
        List<ExamProgress> examProgressList = null;
        Map<String, List<ExamProgress>> examHistoryMap = new HashMap<>();
        List<String> examHistoryDates = examRepo.getExamHistoryDates(teacherId);
        for(String date: examHistoryDates) {
            examList = examRepo.getExamHistory(teacherId, date);
            examProgressList = new ArrayList<>();
            for(Exam exam: examList){
                String progress = examRepo.getExamProgress(exam.getId());
                examProgressList.add(new ExamProgress(exam, progress));
            }
            examHistoryMap.put(date, examProgressList);
        }
        return examHistoryMap;
    }
    
    public String getExamProgress(Long examId) {
        return examRepo.getExamProgress(examId);
    }
    
    public void deleteExam(Long id) throws Exception{
        try {
            examRepo.deleteById(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
