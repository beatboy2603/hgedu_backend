/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ADMIN
 */
@Entity
public class TestQuestion implements Serializable{

//    @Id
//    @Column(name = "testId")
//    private int testId;
//    @Id
//    @Column(name = "questionId")
//    private int questionId;
    
    @EmbeddedId
    private TestQuestionIdentity testQuestionIdentity;

    @Column(name = "answersOrder")
    private String answersOrder;

    public TestQuestion() {
    }

    public TestQuestion(TestQuestionIdentity testQuestionIdentity, String answersOrder) {
        this.testQuestionIdentity = testQuestionIdentity;
        this.answersOrder = answersOrder;
    }

    public TestQuestionIdentity getTestQuestionIdentity() {
        return testQuestionIdentity;
    }

    public void setTestQuestionIdentity(TestQuestionIdentity testQuestionIdentity) {
        this.testQuestionIdentity = testQuestionIdentity;
    }

    public String getAnswersOrder() {
        return answersOrder;
    }

    public void setAnswersOrder(String answersOrder) {
        this.answersOrder = answersOrder;
    }
    
    public void setTestId(Long testId) {
        this.testQuestionIdentity.setTestId(testId);
    }

    

    
}
