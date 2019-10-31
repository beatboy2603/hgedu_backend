/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.TestToWord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author admin
 */
public interface TestToWordRepository extends JpaRepository<TestToWord, Integer>{
    
    @Query(value = "SELECT content FROM TestToWord")
    List<String> getAllContent();
}
