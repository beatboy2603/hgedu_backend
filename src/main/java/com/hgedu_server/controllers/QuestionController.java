/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.Question;
import com.hgedu_server.models.QuestionAndAnswers;
import com.hgedu_server.services.QuestionService;
import java.util.List;
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
@RequestMapping("/api/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    
    @GetMapping("/{teacherId}/{folderId}")
    public List<Question> getQuestionByFolderId(@PathVariable("teacherId") int teacherId, 
            @PathVariable("folderId") int folderId) throws Exception {
        return questionService.getQuestionByFolderId(teacherId, folderId);
    }
    
    @PostMapping("/addQuestion")
    public List<QuestionAndAnswers> addQuestion(@RequestBody List<QuestionAndAnswers> questionAndAnswersList) throws Exception {
        return questionAndAnswersList;
    }
    
    @GetMapping("/getFormIdentifiers/{teacherId}")
    public List<String> getFormIdentifiers(@PathVariable("teacherId") int teacherId) throws Exception {
        return questionService.getFormIdentifiers(teacherId);
    }
    
    @GetMapping("/getAllSpecialKnowledge/{teacherId}")
    public List<String> getAllSpecialKnowledge(@PathVariable("teacherId") int teacherId) throws Exception {
        return questionService.getAllSpecialKnowledge(teacherId);
    }
    
    @GetMapping("/getFullQuestionAndAnswers/{questionId}")
    public List<QuestionAndAnswers> getFullQuestionAndAnswers(@PathVariable("questionId") Long questionId) throws Exception {
        return questionService.getFullQuestionAndAnswers(questionId);
    }
}
