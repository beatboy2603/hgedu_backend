/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.Test;
import com.hgedu_server.repositories.TestRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class TestService {
    @Autowired
    private TestRepository testRepo;
    
    public List<Test> getAllTestByFolderId(Long teacherId, Long folderId) {
        return testRepo.getTestsOfFolder(teacherId, folderId);
    }
}
