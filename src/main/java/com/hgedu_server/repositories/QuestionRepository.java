/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author admin
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    @Query("SELECT questionId FROM Question WHERE questionCode = ?1")
    Long findQuestionIdByQuestionCode(String code);
    
    
    
}
