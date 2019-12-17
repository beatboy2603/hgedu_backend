/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.ExamTest;
import com.hgedu_server.models.TestQuestion;
import com.hgedu_server.models.TestQuestionIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ADMIN
 */
public interface TestQuestionRepository extends JpaRepository<TestQuestion, TestQuestionIdentity>  {
    
}
