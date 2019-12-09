/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.Question;
import com.hgedu_server.repositories.QuestionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    
    public List<Question> getQuestionByFolderId(int teacherId, int folderId) {
        return questionRepository.findByTeacherIdAndFolderId(teacherId, folderId);
    }
}
