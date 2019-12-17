/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.AnswerOption;
import com.hgedu_server.models.Question;
import com.hgedu_server.models.QuestionDetail;
import com.hgedu_server.repositories.AnswerRepository;
import com.hgedu_server.repositories.QuestionRepository;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepo;
    
    @Autowired 
    private AnswerRepository answerRepo;
    
    public Map<Question, Object> getQuestionsForTestSpecific(Long testId) {
        Map<Question, Object> questionMap = new HashMap<>();
        //get normal questions
        List<Question> normalQuestions = questionRepo.getNormalQuestionsOfTest(testId);
        //get answers for normal question
        for(Question question : normalQuestions) {
            String answersOrderStr = questionRepo.getQuestionAnswersOrder(testId, question.getQuestionId());
            if(answersOrderStr.isEmpty()) {
                Map<Question, Object> specialQuestionMap = new HashMap<>();
                //get questions of special question
                List<Question> childQuestions = questionRepo.getQuestionsForSpecialQuestion(testId, question.getQuestionId());
                for(Question child: childQuestions) {
                    String childAnswersOrderStr = questionRepo.getQuestionAnswersOrder(testId, child.getQuestionId());
                    String[] answersOrderArr = childAnswersOrderStr.split(",");
                    Set<String> answersOrder = new LinkedHashSet<>();
                    answersOrder.addAll(Arrays.asList(answersOrderArr));
                    List<AnswerOption> answerList = getAnswersBySpecificOrder(testId, answersOrder);
                    specialQuestionMap.put(child, answerList);
                }
                questionMap.put(question, specialQuestionMap);
            } else {
                String[] answersOrderArr = answersOrderStr.split(",");
                Set<String> answersOrder = new LinkedHashSet<>();
                answersOrder.addAll(Arrays.asList(answersOrderArr));
                List<AnswerOption> answerList = getAnswersBySpecificOrder(testId, answersOrder);
                questionMap.put(question, answerList);
            }
        }
        return questionMap;
    }
    
    public Map<Question, Object> getQuestionsForTestRandom(Long testId) {
        Map<Question, Object> questionMap = new HashMap<>();
        //get normal questions
        List<Question> normalQuestions = questionRepo.getNormalQuestionsOfTest(testId);
        //get answers for normal question
        for(Question question : normalQuestions) {
            //get questions of special question
            question.setExplanation("");
            List<Question> childQuestions = questionRepo.getQuestionsForSpecialQuestion(testId, question.getQuestionId());
            if(!childQuestions.isEmpty()) {
                Map<Question, Object> specialQuestionMap = new HashMap<>();
                for(Question child: childQuestions) {
                    List<AnswerOption> answerList = getAnswersByNormalOrder(child.getQuestionId());
                    specialQuestionMap.put(child, answerList);
                }
                questionMap.put(question, specialQuestionMap);
            } else {
                List<AnswerOption> answerList = getAnswersByRandomOrder(question.getQuestionId());
                questionMap.put(question, answerList);
            }
        }
        return questionMap;
    }
    
    public List<QuestionDetail> getQuestionsForTestRandomList(Long testId) {
        List<QuestionDetail> questionMap = new ArrayList<>();
        //get normal questions
        List<Question> normalQuestions = questionRepo.getNormalQuestionsOfTest(testId);
        //get answers for normal question
        for(Question question : normalQuestions) {
            //get questions of special question
            question.setExplanation("");
            List<Question> childQuestions = questionRepo.getQuestionsForSpecialQuestion(testId, question.getQuestionId());
            if(!childQuestions.isEmpty()) {
                List<QuestionDetail> specialQuestionMap = new ArrayList<>();
                for(Question child: childQuestions) {
                    List<AnswerOption> answerList = getAnswersByNormalOrder(child.getQuestionId());
                    specialQuestionMap.add(new QuestionDetail(child, answerList, null));
                }
                questionMap.add(new QuestionDetail(question, null, specialQuestionMap));
            } else {
                List<AnswerOption> answerList = getAnswersByRandomOrder(question.getQuestionId());
                questionMap.add(new QuestionDetail(question, answerList, null));
            }
        }
        return questionMap;
    }
    
    public Float getTestMark(Map<Long, Long> studentAnswers, Long testId) {
        //Long numberOfQuestions = questionRepo.countQuestionsOfTest(testId);
        Set<Long> answers = new LinkedHashSet<>();
        studentAnswers.values().forEach((answerId) -> {
            answers.add(answerId);
        });
        return questionRepo.getTestMark(testId, answers);
    }
    
    public List<AnswerOption> getAnswersBySpecificOrder(Long questionId, Set<String> answersOrder) {
        return answerRepo.getAnswersBySpecificOrder(questionId, answersOrder);
    }
    
    public List<AnswerOption> getAnswersByRandomOrder(Long questionId) {
        return answerRepo.getAnswersByRandomOrder(questionId);
    }
    
    public List<AnswerOption> getAnswersByNormalOrder(Long questionId) {
        return answerRepo.getAnswersByNormalOrder(questionId);
    }
}
