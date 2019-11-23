/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.LinkRequest;
import com.hgedu_server.repositories.LinkRequestRepository;

/**
 *
 * @author Admin
 */
public class LinkRequestService {
    
    private LinkRequestRepository linkRequestRepo;
    
    public LinkRequest addRequest(String requestMail, String receiveMail){
        return linkRequestRepo.addRequest(requestMail, receiveMail);
    }
}
