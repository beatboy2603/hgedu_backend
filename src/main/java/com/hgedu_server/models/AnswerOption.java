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
import org.hibernate.annotations.GenericGenerator;

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
    private boolean isCorrect;
    @Column
    private String linkedAnswers;

//    public String getQuestionCode() {
//        return questionCode;
//    }
//
//    public void setQuestionCode(String questionCode) {
//        this.questionCode = questionCode;
//    }
//
//    @Column
//    private String questionCode;

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

    public boolean isIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getLinkedAnswers() {
        return linkedAnswers;
    }

    public void setLinkedAnswers(String linkedAnswers) {
        this.linkedAnswers = linkedAnswers;
    }

    public AnswerOption() {
    }

    public AnswerOption(Long answerId, Long questionId, String content, boolean isCorrect, String linkedAnswers) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.content = content;
        this.isCorrect = isCorrect;
        this.linkedAnswers = linkedAnswers;
    }
    
    
}
