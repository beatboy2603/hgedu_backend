/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


/**
 *
 * @author Admin
 */
@Entity
public class LinkRequest {
    @Id
    @Column(name = "requestId")
    private Long id;
    @Column(name = "parentEmail")
    private String parentEmail;
    @Column(name = "studentEmail")
    private String studentEmail;

    public LinkRequest() {
    }

    public LinkRequest(Long id, String parentEmail, String studentEmail) {
        this.id = id;
        this.parentEmail = parentEmail;
        this.studentEmail= studentEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }


}
