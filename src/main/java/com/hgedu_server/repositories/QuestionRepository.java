/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author admin
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    @Query(value = "SELECT questionId FROM Question WHERE questionCode = ?1", nativeQuery = true)
    Long findQuestionIdByQuestionCode(String code);
    
    List<Question> findByTeacherIdAndFolderId(int teacherId, int folderId);
    
    List<Question> findByQuestionId(Long questionId);
    
    List<Question> findByQuestionParentId(Long questionId);

    @Query(value = "SELECT DISTINCT(formIdentifier) FROM `Question` where teacherId = ?1 and formIdentifier != '' and formIdentifier is not null", nativeQuery = true)
    List<String> getAllFormIdentifiersByTeacherId(int teacherId);
    
    @Query(value = "SELECT DISTINCT(specialKnowledge) FROM `Question` where teacherId=?1 and specialKnowledge != '' and specialKnowledge is not null", nativeQuery = true)
    List<String> getAllSpecialKnowledgeByTeacherId(int teacherId);
}
