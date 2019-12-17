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
public class StudentTeacher {

    @Id
    @Column(name = "studentTeacherId")
    private int studentTeacherId;
    @Column(name = "studentId")
    private int studentId;
    @Column(name = "teacherId")
    private int teacherId;
    @Column(name = "isConnected")
    private boolean isConnected;
    @Column(name = "displayedName")
    private String displayedName;

    public StudentTeacher() {
    }

    public StudentTeacher(int studentTeacherId, int studentId, int teacherId, boolean isConnected, String displayedName) {
        this.studentTeacherId = studentTeacherId;
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.isConnected = isConnected;
        this.displayedName = displayedName;
    }

    public int getStudentTeacherId() {
        return studentTeacherId;
    }

    public void setStudentTeacherId(int studentTeacherId) {
        this.studentTeacherId = studentTeacherId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public boolean isIsConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String displayedName) {
        this.displayedName = displayedName;
    }

}
