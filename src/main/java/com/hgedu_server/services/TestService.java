/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.AnswerOption;
import com.hgedu_server.models.Folder;
import com.hgedu_server.models.Question;
import com.hgedu_server.models.Test;
import com.hgedu_server.models.TestContentPlaceholder;
import com.hgedu_server.models.TestQuestion;
import com.hgedu_server.repositories.AnswerRepository;
import com.hgedu_server.repositories.FolderRepository;
import com.hgedu_server.repositories.QuestionRepository;
import com.hgedu_server.repositories.TestQuestionRepository;
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

    @Autowired
    private FolderRepository folderRepository;
    
    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private TestQuestionRepository testQuestionRepository;
    
    @Autowired
    private AnswerRepository answerRepository;

    public List<Test> getAllTestByFolderId(Long teacherId, Long folderId) {
        return testRepo.getTestsOfFolder(teacherId, folderId);
    }

    public String addTest(TestContentPlaceholder testContentPlaceholder) {
        Folder testFolder = testContentPlaceholder.getTestFolder();
        folderRepository.save(testFolder);
        Folder addedFolder = folderRepository.findByTeacherIdAndFolderNameAndParentFolderId(testFolder.getTeacherId(), testFolder.getFolderName(), testFolder.getParentFolderId());

        Test test = testContentPlaceholder.getTest();
        test.setFolderId(Long.valueOf(addedFolder.getFolderId()));
        testRepo.save(test);
        Test addedTest = testRepo.findByTestCode(test.getTestCode());

        List<TestQuestion> testQuestionList = testContentPlaceholder.getTestQuestionList();
        for(int i=0;i<testQuestionList.size();i++){
            testQuestionList.get(i).setTestId(addedTest.getId().intValue());
            List<AnswerOption> answers = answerRepository.findByQuestionId(Long.valueOf(testQuestionList.get(i).getTestQuestionIdentity().getQuestionId()));
            String answersOrder = "";
            for (int k=0; k<answers.size();k++){
                answersOrder+= answers.get(k).getAnswerId()+",";
            }
            testQuestionList.get(i).setAnswersOrder(answersOrder);
        }
        testQuestionRepository.saveAll(testQuestionList);
        return "added";
    }

    public List<Question> getTestContent(Long testFolderId) {
        Test test = testRepo.findByFolderId(testFolderId);
        System.out.println(test.getId());
        List<Question> testQuestions = questionRepository.getTestQuestions(test.getId().intValue());
        return testQuestions;
    }

    public List<Test> getAllTests(int teacherId) {
        return testRepo.findByTeacherId(Long.valueOf(teacherId));
    }

    public List<TestQuestion> getAllTestQuestions(int teacherId) {
        return testQuestionRepository.findByTeacherId(teacherId);
    }
}
