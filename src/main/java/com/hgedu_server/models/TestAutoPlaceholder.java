/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.models;

/**
 *
 * @author ADMIN
 */
public class TestAutoPlaceholder {
    Folder testFolder;
    Test test;
    TestCriteria testCriteria;

    public TestAutoPlaceholder() {
    }

    public TestAutoPlaceholder(Folder testFolder, Test test, TestCriteria testCriteria) {
        this.testFolder = testFolder;
        this.test = test;
        this.testCriteria = testCriteria;
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

    public TestCriteria getTestCriteria() {
        return testCriteria;
    }

    public void setTestCriteria(TestCriteria testCriteria) {
        this.testCriteria = testCriteria;
    }
    
    
}
