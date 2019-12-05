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
public class ExamProgress {
    private Exam exam;
    private String progress;

    public ExamProgress(Exam exam, String progress) {
        this.exam = exam;
        this.progress = progress;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
    
    
}
