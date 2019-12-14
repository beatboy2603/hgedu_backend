/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.Abbreviation;
import com.hgedu_server.services.AbbreviationService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/api/abbreviation")
public class AbbreviationController {

    @Autowired
    private AbbreviationService abbreviationService;

    @GetMapping("/{teacherId}")
    public List<Abbreviation> getAllAbbreviations(@PathVariable("teacherId") int teacherId) throws Exception {
        return abbreviationService.getAllAbbreviations(teacherId);
    }

    @PostMapping("/addAbbreviation")
    @ResponseBody
    public void addAbbreviation(@RequestBody Abbreviation abbreviation) throws Exception {
        System.out.println(abbreviation.getTeacherId());
        System.out.println(abbreviation.getOriginalForm());
        System.out.println(abbreviation.getShortenForm());
        abbreviationService.addAbbreviation(abbreviation);
    }
    
    @PostMapping("/updateAbbreviation")
    @ResponseBody
    public void updateAbbreviation(@RequestBody Abbreviation abbreviation) throws Exception {
        abbreviationService.addAbbreviation(abbreviation);
    }

    @PostMapping("/deleteAbbreviation/{abbreviationId}")
    public void deleteAbbreviation(@PathVariable("abbreviationId") int abbreviationId) throws Exception {
        abbreviationService.deleteAbbreviation(abbreviationId);
    }
}

