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
import com.hgedu_server.models.QuestionAndAnswers;
import com.hgedu_server.repositories.AnswerRepository;
import com.hgedu_server.repositories.QuestionRepository;
import java.util.ArrayList;
import java.util.List;
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
        if(!answers.isEmpty())
            return questionRepo.getTestMark(testId, answers);
        else
            return 0.0f;
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

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    public List<Question> getQuestionByFolderId(int teacherId, int folderId) {
        return questionRepository.findByTeacherIdAndFolderId(teacherId, folderId);
    }

    public List<String> getFormIdentifiers(int teacherId) {
        return questionRepository.getAllFormIdentifiersByTeacherId(teacherId);
    }

    public List<String> getAllSpecialKnowledge(int teacherId) {
        return questionRepository.getAllSpecialKnowledgeByTeacherId(teacherId);
    }

    public List<QuestionAndAnswers> getFullQuestionAndAnswers(Long questionId) {
        List<QuestionAndAnswers> questionAndAnswersList = new ArrayList<QuestionAndAnswers>();
        QuestionAndAnswers questionAndAnswers = new QuestionAndAnswers();
        List<Question> queryQuestions = questionRepository.findByQuestionId(questionId);
        if (queryQuestions.size() > 0) {
            questionAndAnswers.setQuestion(queryQuestions.get(0));
        }
        List<AnswerOption> queryAnswers = answerRepository.findByQuestionId(questionId);
        if (queryAnswers.size() > 0) {
            questionAndAnswers.setAnswers(queryAnswers);
        }
        questionAndAnswersList.add(questionAndAnswers);

        if (questionAndAnswers.getQuestion().getQuestionTypeId() == 3) {
            List<Question> queryChildQuestions = questionRepository.findByQuestionParentId(questionId);
//            System.out.println("size "+queryChildQuestions.size());
            for (int i = 0; i < queryChildQuestions.size(); i++) {
                QuestionAndAnswers questionAndAnswersChild = new QuestionAndAnswers();
                questionAndAnswersChild.setQuestion(queryChildQuestions.get(i));
                List<AnswerOption> queryChildAnswers = answerRepository.findByQuestionId(queryChildQuestions.get(i).getQuestionId());
//                System.out.println("childsize"+queryChildAnswers.size());
                if (queryChildAnswers.size() > 0) {
                    questionAndAnswersChild.setAnswers(queryChildAnswers);
                }
                questionAndAnswersList.add(questionAndAnswersChild);
            }
        }

        return questionAndAnswersList;
    }

    public List<Question> getAllQuestions(int teacherId) {
        return questionRepository.findByTeacherId(teacherId);
    }

    public String addQuestion(List<QuestionAndAnswers> questionAndAnswersList, int teacherId) {
        if (questionAndAnswersList.size() == 1) {
            Question question = questionAndAnswersList.get(0).getQuestion();
            question.setQuestionParentId(Long.valueOf(0));
            question.setTeacherId(teacherId);
            questionRepository.save(question);
            Long questionId = questionRepository.findQuestionIdByQuestionCode(question.getQuestionCode());
            List<AnswerOption> answers = questionAndAnswersList.get(0).getAnswers();
            for (int i = 0; i < answers.size(); i++) {
                answers.get(i).setQuestionId(questionId);
            }
            answerRepository.saveAll(answers);
        } else if (questionAndAnswersList.size() > 1) {
            Question questionParent = questionAndAnswersList.get(0).getQuestion();
            questionParent.setQuestionParentId(Long.valueOf(0));
            questionParent.setTeacherId(teacherId);
            questionRepository.save(questionParent);
            Long questionParentId = questionRepository.findQuestionIdByQuestionCode(questionParent.getQuestionCode());
            for (int i = 1; i < questionAndAnswersList.size(); i++) {
                Question question = questionAndAnswersList.get(i).getQuestion();
                question.setQuestionParentId(questionParentId);
                question.setTeacherId(teacherId);
                questionRepository.save(question);
                Long questionId = questionRepository.findQuestionIdByQuestionCode(question.getQuestionCode());
                List<AnswerOption> answers = questionAndAnswersList.get(i).getAnswers();
                for (int k = 0; k < answers.size(); k++) {
                    answers.get(k).setQuestionId(questionId);
                }
                answerRepository.saveAll(answers);
            }
        }
        return "added";
    }

}
