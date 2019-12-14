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
public class StudentEnrollment {

    @Id
    @Column(name = "requestId")
    private int requestId;

    @Column(name = "note")
    private String note;

    @Column(name = "teacherEmail")
    private String teacherEmail;

    @Column(name = "studentId")
    private String studentId;

    public StudentEnrollment() {
    }

    public StudentEnrollment(int requestId, String note, String teacherEmail, String studentId) {
        this.requestId = requestId;
        this.note = note;
        this.teacherEmail = teacherEmail;
        this.studentId = studentId;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    }
