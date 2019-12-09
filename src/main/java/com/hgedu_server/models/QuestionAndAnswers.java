/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.models;

import java.util.List;

/**
 *
 * @author ADMIN
 */
public class QuestionAndAnswers {
    private Question question;
    private List<AnswerOption> answers;

    public QuestionAndAnswers() {
    }

    public QuestionAndAnswers(Question question, List<AnswerOption> answers) {
        this.question = question;
        this.answers = answers;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<AnswerOption> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerOption> answers) {
        this.answers = answers;
    }
    
    
}
