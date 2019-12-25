/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.AnswerOption;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author admin
 */
public interface AnswerRepository extends JpaRepository<AnswerOption, Long>{
    @Query(value = "SELECT answerId, questionId, content, linkedAnswers, answerKatex, images, questionCode, -1 'isCorrect' FROM AnswerOption WHERE questionId = :questionId ORDER BY FIELD(answerId,:ids)", nativeQuery = true)
    List<AnswerOption> getAnswersBySpecificOrder(@Param("questionId") Long questionId, @Param("ids") Set<String> answersOrder);
    
    @Query(value = "SELECT * FROM AnswerOption WHERE questionId = ?1 ORDER BY FIELD(answerId,?2)", nativeQuery = true)
    List<AnswerOption> getAnswersInOrder(Long questionId, Set<Integer> answersOrder);
    
    @Query(value = "SELECT answerId, questionId, content, linkedAnswers, answerKatex, images, questionCode, -1 'isCorrect' FROM AnswerOption WHERE questionId = ?1 ORDER BY Rand()", nativeQuery = true)
    List<AnswerOption> getAnswersByRandomOrder(Long questionId);
    
    @Query(value = "SELECT answerId, questionId, content, linkedAnswers, answerKatex, images, questionCode, -1 'isCorrect' FROM AnswerOption WHERE questionId = ?1", nativeQuery = true)
    List<AnswerOption> getAnswersByNormalOrder(Long questionId);
    List<AnswerOption> findByQuestionId(Long questionId);
}
