/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Admin
 */
@Entity
public class TeacherRequest {

    @Id
    @Column(name = "requestId")
    private int requestId;

    @Column(name = "teacherEmail")
    private String teacherEmail;

    @Column(name = "studentId")
    private int studentId;
    
    @Column(name = "displayedName")
    private String displayedName;

    public TeacherRequest(int requestId, String teacherEmail, int studentId, String displayedName) {
        this.requestId = requestId;
        this.teacherEmail = teacherEmail;
        this.studentId = studentId;
        this.displayedName = displayedName;
    }

    public TeacherRequest() {
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String displayedName) {
        this.displayedName = displayedName;
    }

    
}
