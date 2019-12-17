/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.AnswerOption;
import com.hgedu_server.models.Question;
import com.hgedu_server.models.QuestionAndAnswers;
import com.hgedu_server.repositories.AnswerRepository;
import com.hgedu_server.repositories.QuestionRepository;
import java.util.ArrayList;
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
