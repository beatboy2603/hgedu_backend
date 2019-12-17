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
 * @author admin
 */
@Entity
public class StudentTeacher {
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long studentTeacherId;
    
    @Column
    private long studentId;
    
    @Column
    private long teacherId;
    
    @Column
    private boolean isConnected;
    
    @Column
    private String displayedName;

    public long getStudentTeacherId() {
        return studentTeacherId;
    }

    public void setStudentTeacherId(long studentTeacherId) {
        this.studentTeacherId = studentTeacherId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
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