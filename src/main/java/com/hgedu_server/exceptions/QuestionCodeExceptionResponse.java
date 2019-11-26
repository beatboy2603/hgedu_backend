/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.exceptions;

/**
 *
 * @author admin
 */
public class QuestionCodeExceptionResponse {
    
    private String questionCode; 

    public QuestionCodeExceptionResponse(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }
    
    
    
}
