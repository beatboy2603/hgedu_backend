/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.models;

import java.util.List;

/**
 *
 * @author Administrator
 */
public class QuestionDetail {
    private Question mainQuestion;
    private List<AnswerOption> mainAnswers;
    private List<QuestionDetail> childQuestion;

    public QuestionDetail() {
    }

    public QuestionDetail(Question mainQuestion, List<AnswerOption> mainAnswers, List<QuestionDetail> childQuestion) {
        this.mainQuestion = mainQuestion;
        this.mainAnswers = mainAnswers;
        this.childQuestion = childQuestion;
    }

    public Question getMainQuestion() {
        return mainQuestion;
    }

    public void setMainQuestion(Question mainQuestion) {
        this.mainQuestion = mainQuestion;
    }

    public List<AnswerOption> getMainAnswers() {
        return mainAnswers;
    }

    public void setMainAnswers(List<AnswerOption> mainAnswers) {
        this.mainAnswers = mainAnswers;
    }

    public List<QuestionDetail> getChildQuestion() {
        return childQuestion;
    }

    public void setChildQuestion(List<QuestionDetail> childQuestion) {
        this.childQuestion = childQuestion;
    }
    
    
}
