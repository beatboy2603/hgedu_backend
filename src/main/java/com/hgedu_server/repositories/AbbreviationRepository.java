/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.Abbreviation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ADMIN
 */
public interface AbbreviationRepository extends JpaRepository<Abbreviation, Long>{

    public List<Abbreviation> findByTeacherId(Long teacherId);
    
}
