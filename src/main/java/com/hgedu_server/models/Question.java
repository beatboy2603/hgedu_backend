/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author admin
 */
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long questionId;
    @Column(unique = false)
    private String questionCode;

    @Column
    private int teacherId;
    @Column
    private int folderId;
    @Column
    private Long questionParentId;
    @Column
    private String formIdentifier;
    @Column
//    @NotBlank(message = "content is required") //error?
    private String content;
    @Column
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dateCreated;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = new Date();
    }

    @Column
    private int difficultyId;
    @Column
    private int gradeLevelId;
    @Column
    private int questionTypeId;
    @Column
    private String knowledgeGroup;
    @Column
    private String specialKnowledge;
    @Column
    private String explanation;
    @Column
    private boolean isPublic;
    @Column
    private String questionKatex;
    @Column
    private String images;

    //questionId - questionCode - teacherId - folderId - folderId - questionParentId
    // formIdentifier - content - description - dateCreated - difficultyId -
    //gradeLevelId - questionTypeId - knowledgeGroup - specialKnowledge - explanation - isPublic - questionKatex - images
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public Long getQuestionParentId() {
        return questionParentId;
    }

    public void setQuestionParentId(Long questionParentId) {
        this.questionParentId = questionParentId;
    }

    public String getFormIdentifier() {
        return formIdentifier;
    }

    public void setFormIdentifier(String formIdentifier) {
        this.formIdentifier = formIdentifier;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getDifficultyId() {
        return difficultyId;
    }

    public void setDifficultyId(int difficultyId) {
        this.difficultyId = difficultyId;
    }

    public int getGradeLevelId() {
        return gradeLevelId;
    }

    public void setGradeLevelId(int gradeLevelId) {
        this.gradeLevelId = gradeLevelId;
    }

    public int getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(int questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public String getKnowledgeGroup() {
        return knowledgeGroup;
    }

    public void setKnowledgeGroup(String knowledgeGroup) {
        this.knowledgeGroup = knowledgeGroup;
    }

    public String getSpecialKnowledge() {
        return specialKnowledge;
    }

    public void setSpecialKnowledge(String specialKnowledge) {
        this.specialKnowledge = specialKnowledge;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getQuestionKatex() {
        return questionKatex;
    }

    public void setQuestionKatex(String questionKatex) {
        this.questionKatex = questionKatex;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Question() {
    }

    public Question(Long questionId, String questionCode, int teacherId, int folderId, Long questionParentId, String formIdentifier, String content, String description, Date dateCreated, int difficultyId, int gradeLevelId, int questionTypeId, String knowledgeGroup, String specialKnowledge, String explanation, boolean isPublic, String questionKatex, String images) {
        this.questionId = questionId;
        this.questionCode = questionCode;
        this.teacherId = teacherId;
        this.folderId = folderId;
        this.questionParentId = questionParentId;
        this.formIdentifier = formIdentifier;
        this.content = content;
        this.description = description;
        this.dateCreated = dateCreated;
        this.difficultyId = difficultyId;
        this.gradeLevelId = gradeLevelId;
        this.questionTypeId = questionTypeId;
        this.knowledgeGroup = knowledgeGroup;
        this.specialKnowledge = specialKnowledge;
        this.explanation = explanation;
        this.isPublic = isPublic;
        this.questionKatex = questionKatex;
        this.images = images;
    }
    
    
}
