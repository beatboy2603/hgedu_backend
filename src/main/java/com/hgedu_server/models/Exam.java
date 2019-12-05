/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.models;

import java.sql.Timestamp;
import java.util.Date;
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
public class Exam {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "teacherId")
    private Long teacherId;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "startEntryTime")
    private String startEntryTime;
    
    @Column(name = "endEntryTime")
    private String endEntryTime;
    
    @Column(name = "duration")
    private Long duration;
    
    @Column(name = "trials")
    private Long trials;
    
    @Column(name = "code")
    private String code;
    
    @Column(name = "isMarked")
    private Boolean isMarked;
    
    @Column(name = "dateCreated", updatable = false)
    private String dateCreated;
    
    @Column(name = "dateUpdated")
    private String dateUpdated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartEntryTime() {
        return startEntryTime;
    }

    public void setStartEntryTime(String startEntryTime) {
        this.startEntryTime = startEntryTime;
    }

    public String getEndEntryTime() {
        return endEntryTime;
    }

    public void setEndEntryTime(String endEntryTime) {
        this.endEntryTime = endEntryTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getTrials() {
        return trials;
    }

    public void setTrials(Long trials) {
        this.trials = trials;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIsMarked() {
        return isMarked;
    }

    public void setIsMarked(Boolean isMarked) {
        this.isMarked = isMarked;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
    
    
    
}
