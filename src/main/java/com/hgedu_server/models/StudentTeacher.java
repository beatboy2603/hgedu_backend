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
    private Long studentTeacherId;
    
    @Column
    private Long studentId;
    
    @Column
    private Long teacherId;
    
    @Column
    private boolean isConnected;
    
    @Column
    private String displayedName;

    public Long getStudentTeacherId() {
        return studentTeacherId;
    }

    public void setStudentTeacherId(Long studentTeacherId) {
        this.studentTeacherId = studentTeacherId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
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