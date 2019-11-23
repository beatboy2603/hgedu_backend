/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.models;

/**
 *
 * @author Admin
 */
public class LinkRequest {
 private int id;
 private String senderEmail;
 private String requestedEmail;

    public LinkRequest() {
    }

    public LinkRequest(int id, String senderEmail, String requestedEmail) {
        this.id = id;
        this.senderEmail = senderEmail;
        this.requestedEmail = requestedEmail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getRequestedEmail() {
        return requestedEmail;
    }

    public void setRequestedEmail(String requestedEmail) {
        this.requestedEmail = requestedEmail;
    }

    
    
}
