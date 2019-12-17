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
public class StudentRequest {

    @Id
    @Column(name = "requestId")
    private int requestId;

    @Column(name = "note")
    private String note;

    @Column(name = "teacherEmail")
    private String teacherEmail;

    @Column(name = "studentId")
    private int studentId;

    @Column(name = "displayedName")
    private String displayedName;

    public StudentRequest() {
    }

    public StudentRequest(int requestId, String note, String teacherEmail, int studentId, String displayedName) {
        this.requestId = requestId;
        this.note = note;
        this.teacherEmail = teacherEmail;
        this.studentId = studentId;
        this.displayedName = displayedName;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String displayedName) {
        this.displayedName = displayedName;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

}
