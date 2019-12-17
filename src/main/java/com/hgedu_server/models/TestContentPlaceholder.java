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
public class TestContentPlaceholder {
    Folder testFolder;
    Test test;
    List<TestQuestion> testQuestionList;

    public TestContentPlaceholder() {
    }

    public TestContentPlaceholder(Folder testFolder, Test test, List<TestQuestion> testQuestionList) {
        this.testFolder = testFolder;
        this.test = test;
        this.testQuestionList = testQuestionList;
    }

    public Folder getTestFolder() {
        return testFolder;
    }

    public void setTestFolder(Folder testFolder) {
        this.testFolder = testFolder;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public List<TestQuestion> getTestQuestionList() {
        return testQuestionList;
    }

    public void setTestQuestionList(List<TestQuestion> testQuestionList) {
        this.testQuestionList = testQuestionList;
    }
    
    
    
}
