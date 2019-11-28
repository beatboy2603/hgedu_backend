/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.LinkRequest;
import com.hgedu_server.models.User;
import com.hgedu_server.repositories.LinkRequestRepository;
import com.hgedu_server.repositories.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class LinkRequestService {

    @Autowired
    private LinkRequestRepository linkRequestRepo;
    @Autowired
    private UserRepository userRepository;

    public void addRequest(String parentMail, String studentMail) {
        System.out.println("addRequest parent's mail: " + parentMail);
        System.out.println("addRequest student's mail: " + studentMail);
        linkRequestRepo.addRequest(parentMail, studentMail);
    }

//    public String getUserEmail(int id){
//        User user = userRepository.getOne(id);
//        return user.getEmail();
//    }
    public Optional<LinkRequest> getRequest(int id) {
        User user = userRepository.getOne(id);
        String mail = user.getEmail();
        System.out.println("mail: "+mail);
        return linkRequestRepo.getRequest(mail);
    }
}
