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
    private Long requestId;

    @Column(name = "note")
    private String note;

    @Column(name = "teacherEmail")
    private String teacherEmail;

    @Column(name = "studentId")
    private Long studentId;

    @Column(name = "displayedName")
    private String displayedName;

    public StudentRequest() {
    }

    public StudentRequest(Long requestId, String note, String teacherEmail, Long studentId, String displayedName) {
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

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
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

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

}
