/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.QuestionAndAnswers;
import com.hgedu_server.models.Test;
import com.hgedu_server.models.TestContentPlaceholder;
import com.hgedu_server.services.TestService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private TestService testService;
    
    @PostMapping("/addTest")
        public String addTest(@RequestBody TestContentPlaceholder testContentPlaceholder) throws Exception {
//        System.out.println(testContentPlaceholder.getTestFolder().getFolderName());
//        System.out.println(testContentPlaceholder.getTest().getTestCode());
//        System.out.println(testContentPlaceholder.getTestQuestionList().size());
        return testService.addTest(testContentPlaceholder);
    }
}
