/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.models;

/**
 *
 * @author Administrator
 */
public class ExamResultDetails {
    private String studentName;
    private String className;
    private ExamResult examResult;

    public ExamResultDetails() {
    }

    public ExamResultDetails(String studentName, String className, ExamResult examResult) {
        this.studentName = studentName;
        this.className = className;
        this.examResult = examResult;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ExamResult getExamResult() {
        return examResult;
    }

    public void setExamResult(ExamResult examResult) {
        this.examResult = examResult;
    }
    
    
}
