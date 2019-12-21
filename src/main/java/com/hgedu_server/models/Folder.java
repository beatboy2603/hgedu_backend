/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ADMIN
 */
@Entity
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="folderId")
    private int folderId;
    @Column(name="teacherId")
    private int teacherId;
    @Column(name="folderName")
    private String folderName;
    @Column(name="folderTypeId")
    private int folderTypeId;
    @Column(name="parentFolderId")
    private int parentFolderId;
    @Column(name="subGroupId")
    private int subGroupId;

    public Folder() {
    }

    public Folder(int folderId, int teacherId, String folderName, int folderTypeId, int parentFolderId, int subGroupId) {
        this.folderId = folderId;
        this.teacherId = teacherId;
        this.folderName = folderName;
        this.folderTypeId = folderTypeId;
        this.parentFolderId = parentFolderId;
        this.subGroupId = subGroupId;
    }

    public int getSubGroupId() {
        return subGroupId;
    }

    public void setSubGroupId(int subGroupId) {
        this.subGroupId = subGroupId;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getFolderTypeId() {
        return folderTypeId;
    }

    public void setFolderTypeId(int folderTypeId) {
        this.folderTypeId = folderTypeId;
    }

    public int getParentFolderId() {
        return parentFolderId;
    }

    public void setParentFolderId(int parentFolderId) {
        this.parentFolderId = parentFolderId;
    }
    
    
}
