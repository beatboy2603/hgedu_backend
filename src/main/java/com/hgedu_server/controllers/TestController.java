/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.Question;
import com.hgedu_server.models.QuestionAndAnswers;
import com.hgedu_server.models.Test;
import com.hgedu_server.models.TestContentPlaceholder;
import com.hgedu_server.models.TestQuestion;
import com.hgedu_server.services.TestService;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
        return testService.addTest(testContentPlaceholder);
    }

    @GetMapping("/{testFolderId}")
    public List<Question> getTestContent(@PathVariable Long testFolderId) throws Exception {
        return testService.getTestContent(testFolderId);
    }

    @GetMapping("/getAllTests/{teacherId}")
    public List<Test> getAllTests(@PathVariable int teacherId) throws Exception {
        return testService.getAllTests(teacherId);
    }

    @GetMapping("/getAllTestQuestions/{teacherId}")
    public List<TestQuestion> getAllTestQuestions(@PathVariable int teacherId) throws Exception {
        return testService.getAllTestQuestions(teacherId);
    }

    @GetMapping("/genRandomString")
    public String genRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
