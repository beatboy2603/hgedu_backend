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
    private Long folderId;
    @Column(name="teacherId")
    private Long teacherId;
    @Column(name="folderName")
    private String folderName;
    @Column(name="folderTypeId")
    private int folderTypeId;
    @Column(name="parentFolderId")
    private Long parentFolderId;
    @Column(name="subGroupId")
    private int subGroupId;

    public Folder() {
    }

    public Folder(Long folderId, Long teacherId, String folderName, int folderTypeId, Long parentFolderId, int subGroupId) {
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

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
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

    public Long getParentFolderId() {
        return parentFolderId;
    }

    public void setParentFolderId(Long parentFolderId) {
        this.parentFolderId = parentFolderId;
    }
    
    
}
