/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Administrator
 */
@Entity
public class ExamResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "examTestId")
    private Long examTestId;
    
    @Column(name = "studentId")
    private Long studentId;
    
    @Column(name = "classId")
    private Long classId;
    
    @Column(name = "mark")
    private float mark;
    
    @Column(name = "startedTime")
    private String startedTime;
    
    @Column(name = "completedTime")
    private String completedTime;
    
    @Column(name = "nthTrial")
    private Long nthTrial;
    
    @Column(name = "isCompleted")
    private Boolean isCompleted;
    
    @Column(name = "isResetted")
    private Boolean isResetted;
    
    @Column(name = "studentAnswers")
    private String studentAnswers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExamTestId() {
        return examTestId;
    }

    public void setExamTestId(Long examTestId) {
        this.examTestId = examTestId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public String getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(String startedTime) {
        this.startedTime = startedTime;
    }

    public String getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(String completedTime) {
        this.completedTime = completedTime;
    }

    public Long getNthTrial() {
        return nthTrial;
    }

    public void setNthTrial(Long nthTrial) {
        this.nthTrial = nthTrial;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Boolean getIsResetted() {
        return isResetted;
    }

    public void setIsResetted(Boolean isResetted) {
        this.isResetted = isResetted;
    }

    public String getStudentAnswers() {
        return studentAnswers;
    }

    public void setStudentAnswers(String studentAnswers) {
        this.studentAnswers = studentAnswers;
    }
    
    
}
