/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.Abbreviation;
import com.hgedu_server.repositories.AbbreviationRepository;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class AbbreviationService {

    @Autowired
    private AbbreviationRepository abbreviationRepository;

    public List<Abbreviation> getAllAbbreviations(int teacherId) {
        List<Abbreviation> abbreviationList = abbreviationRepository.findByTeacherId(teacherId);
        Collections.sort(abbreviationList, Collections.reverseOrder());         
        return abbreviationList;
    }

    public void addAbbreviation(Abbreviation abbreviation) {
        abbreviationRepository.save(abbreviation);
    }

    public void deleteAbbreviation(int abbreviationId) throws Exception {
        try {
            abbreviationRepository.deleteById(abbreviationId);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
