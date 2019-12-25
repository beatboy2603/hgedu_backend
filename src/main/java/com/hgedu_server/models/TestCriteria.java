/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.models;

import java.util.List;
import javax.persistence.Column;

/**
 *
 * @author ADMIN
 */
public class TestCriteria {
    List<Integer> difficultiesAuto;
    List<Integer> questionTypes;
    List<Integer> knowledgeGroups;
    List<String> specialKnowledges;
    int gradeLevelId;

    public TestCriteria() {
    }

    public TestCriteria(List<Integer> difficultiesAuto, List<Integer> questionTypes, List<Integer> knowledgeGroups, List<String> specialKnowledges, int gradeLevelId) {
        this.difficultiesAuto = difficultiesAuto;
        this.questionTypes = questionTypes;
        this.knowledgeGroups = knowledgeGroups;
        this.specialKnowledges = specialKnowledges;
        this.gradeLevelId = gradeLevelId;
    }

    public List<Integer> getDifficultiesAuto() {
        return difficultiesAuto;
    }

    public void setDifficultiesAuto(List<Integer> difficultiesAuto) {
        this.difficultiesAuto = difficultiesAuto;
    }

    public List<Integer> getQuestionTypes() {
        return questionTypes;
    }

    public void setQuestionTypes(List<Integer> questionTypes) {
        this.questionTypes = questionTypes;
    }

    public List<Integer> getKnowledgeGroups() {
        return knowledgeGroups;
    }

    public void setKnowledgeGroups(List<Integer> knowledgeGroups) {
        this.knowledgeGroups = knowledgeGroups;
    }

    public List<String> getSpecialKnowledges() {
        return specialKnowledges;
    }

    public void setSpecialKnowledges(List<String> specialKnowledges) {
        this.specialKnowledges = specialKnowledges;
    }

    public int getGradeLevelId() {
        return gradeLevelId;
    }

    public void setGradeLevelId(int gradeLevelId) {
        this.gradeLevelId = gradeLevelId;
    }
    
    
}
