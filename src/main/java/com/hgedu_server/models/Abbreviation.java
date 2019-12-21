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
 * @author ADMIN
 */
@Entity
public class Abbreviation implements Comparable<Abbreviation> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="abbreviationId")
    private int abbreviationId;
    @Column(name="teacherId")
    private int teacherId;
    @Column(name="shortenForm")
    private String shortenForm;
    @Column(name="originalForm")
    private String originalForm;
    @Column(name="isKatex")
    private boolean isKatex;

    public Abbreviation() {
    }

    public Abbreviation(int abbreviationId, int teacherId, String shortenForm, String originalForm, boolean isKatex) {
        this.abbreviationId = abbreviationId;
        this.teacherId = teacherId;
        this.shortenForm = shortenForm;
        this.originalForm = originalForm;
        this.isKatex = isKatex;
    }

    public int getAbbreviationId() {
        return abbreviationId;
    }

    public void setAbbreviationId(int abbreviationId) {
        this.abbreviationId = abbreviationId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getShortenForm() {
        return shortenForm;
    }

    public void setShortenForm(String shortenForm) {
        this.shortenForm = shortenForm;
    }

    public String getOriginalForm() {
        return originalForm;
    }

    public void setOriginalForm(String originalForm) {
        this.originalForm = originalForm;
    }

    public boolean isIsKatex() {
        return isKatex;
    }

    public void setIsKatex(boolean isKatex) {
        this.isKatex = isKatex;
    }

    @Override
    public int compareTo(Abbreviation o) {
        return (this.getAbbreviationId()< o.getAbbreviationId() ? -1 : 
            (this.getAbbreviationId() == o.getAbbreviationId() ? 0 : 1));     
    }
    
    
}
