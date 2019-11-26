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
public class AnswerOption {
   // answerId - questionId - content - images - isCorrect - answerKatex - linkedAnswers
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;
    @Column
    private Long questionId;
    @Column
    private String content;
    @Column
    private String images;
    @Column
    private boolean isCorrect;
    @Column
    private String answerKatex;
    @Column
    private String linkedAnswers;

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public boolean isIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getAnswerKatex() {
        return answerKatex;
    }

    public void setAnswerKatex(String answerKatex) {
        this.answerKatex = answerKatex;
    }

    public String getLinkedAnswers() {
        return linkedAnswers;
    }

    public void setLinkedAnswers(String linkedAnswers) {
        this.linkedAnswers = linkedAnswers;
    }
    
    
}
