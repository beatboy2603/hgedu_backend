/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.Question;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author admin
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    @Query(value = "SELECT questionId FROM Question WHERE questionCode = ?1", nativeQuery = true)
    Long findQuestionIdByQuestionCode(String code);
    
    @Query(value = "SELECT questionId FROM Question WHERE questionCode = ?1 AND teacherId  = ?2", nativeQuery = true)
    Long findQuestionIdByQuestionCodeAndTeacherId(String code, Long teacherId);
    
   @Query(value = "SELECT * FROM Question q, TestQuestion tq WHERE q.questionId = tq.questionId and tq.testId = ?1", nativeQuery = true)
   List<Question> findByTestId(Long testId); 
    
    
    List<Question> findByTeacherIdAndFolderId(Long teacherId, Long folderId);
    @Query(value = "SELECT q.* FROM Question q, TestQuestion tq, Test t WHERE  q.questionId = tq.questionId AND tq.testId = t.testId AND t.testId = ?1 AND q.questionParentId = 0 ORDER BY Rand()", nativeQuery = true)
    List<Question> getNormalQuestionsOfTest(Long testId);
    
    @Query(value = "SELECT q.* FROM Question q, TestQuestion tq, Test t WHERE  q.questionId = tq.questionId AND tq.testId = t.testId AND t.testId = ?1 AND q.questionParentId = 0", nativeQuery = true)
    List<Question> getNormalQuestionsInOrder(Long testId);
    
    @Query("SELECT Count(questionId) FROM Question")
    Long findCount();
    
    
    List<Question> findByQuestionId(Long questionId);
    @Query(value = "SELECT q.* FROM Question q, TestQuestion tq, Test t WHERE  q.questionId = tq.questionId AND tq.testId = t.testId AND t.testId = ?1 AND tq.answersOrder = NULL", nativeQuery = true)
    List<Question> getSpecialQuestionsOfTest(Long testId);
    
    @Query(value = "SELECT q.* FROM Question q, TestQuestion tq, Test t WHERE  q.questionId = tq.questionId AND tq.testId = t.testId AND t.testId = ?1 AND q.questionParentId = ?2", nativeQuery = true)
    List<Question> getQuestionsForSpecialQuestion(Long testId, Long questionId);
    
    @Query(value = "SELECT tq.answersOrder FROM TestQuestion tq WHERE tq.testId = ?1 AND tq.questionId = ?2 ", nativeQuery = true)
    String getQuestionAnswersOrder(Long testId, Long questionId);
    
    @Query(value = "SELECT COUNT(*) FROM Question q, TestQuestion tq, Test t WHERE  q.questionId = tq.questionId AND tq.testId = t.testId AND t.testId = ?1 AND q.questionParentId = 0", nativeQuery = true)
    Long countQuestionsOfTest(Long testId);
    
    @Query(value = "SELECT ROUND((a.Check/NULLIF(b.Total, 0))*10,1) FROM (SELECT COUNT(ao.`answerId`) 'Check' FROM `AnswerOption` ao WHERE ao.`answerId` IN :ids AND ao.`isCorrect`=1) a, (SELECT COUNT(ao.`answerId`) 'Total' FROM AnswerOption ao WHERE ao.`questionId` IN (SELECT q.`questionId` FROM Question q, TestQuestion tq, Test t WHERE  q.`questionId` = tq.`questionId` AND tq.`testId` = t.`testId` AND t.`testId` = :testId) AND ao.`isCorrect`= 1) b", nativeQuery = true)
    Float getTestMark(@Param("testId") Long testId, @Param("ids") Set<Long> studentAnswers);
    
    List<Question> findByQuestionParentId(Long questionId);

    @Query(value = "SELECT DISTINCT(formIdentifier) FROM `Question` where teacherId = ?1 and formIdentifier != '' and formIdentifier is not null", nativeQuery = true)
    List<String> getAllFormIdentifiersByTeacherId(Long teacherId);
    
    @Query(value = "SELECT DISTINCT(specialKnowledge) FROM `Question` where teacherId=?1 and specialKnowledge != '' and specialKnowledge is not null", nativeQuery = true)
    List<String> getAllSpecialKnowledgeByTeacherId(Long teacherId);

    List<Question> findByTeacherId(Long teacherId);

    @Query(value = "SELECT * FROM `Question` where questionId in (SELECT questionId from TestQuestion where testId = ?1)", nativeQuery = true)
    List<Question> getTestQuestions(Long testId);
}
