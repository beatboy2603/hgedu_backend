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
public class FolderTest {
    private Long folderId;
    private List<Folder> childFolderList;
    private List<Test> testList;

    public FolderTest() {
    }

    public FolderTest(Long folderId, List<Folder> childFolderList, List<Test> testList) {
        this.folderId = folderId;
        this.childFolderList = childFolderList;
        this.testList = testList;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public List<Folder> getChildFolderList() {
        return childFolderList;
    }

    public void setChildFolderList(List<Folder> childFolderList) {
        this.childFolderList = childFolderList;
    }

    public List<Test> getTestList() {
        return testList;
    }

    public void setTestList(List<Test> testList) {
        this.testList = testList;
    }

    
    
}
