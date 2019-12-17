/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 *
 * @author admin
 */
@Entity
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String title;

    @Column
    private String code;

    @Column
    private String startEntryTime;

    @Column
    private String endEntryTime;

    @Column
    private int duration;

    @Column
    private boolean isMarked;

    @Column
    private int trials;

    @Column
    private long teacherId;

    @Column
    private int powerLevel;

    @Column
    private boolean isShowAnswers;

    @Column
    private boolean isShowExplanation;

    @Column
    private Date dateCreated;

    @Column
    private Date dateUpdated;

   
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isIsMarked() {
        return isMarked;
    }

    public void setIsMarked(boolean isMarked) {
        this.isMarked = isMarked;
    }

    public int getTrials() {
        return trials;
    }

    public void setTrials(int trials) {
        this.trials = trials;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }



    public Date getDateUpdated() {
        return dateUpdated;
    }

 

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(int powerLevel) {
        this.powerLevel = powerLevel;
    }

    public boolean isIsShowAnswers() {
        return isShowAnswers;
    }

    public void setIsShowAnswers(boolean isShowAnswers) {
        this.isShowAnswers = isShowAnswers;
    }

    public boolean isIsShowExplanation() {
        return isShowExplanation;
    }

    public void setIsShowExplanation(boolean isShowExplanation) {
        this.isShowExplanation = isShowExplanation;
    }

}
