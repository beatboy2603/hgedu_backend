/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.StudentEnrollment;
import com.hgedu_server.models.User;
import com.hgedu_server.services.StudentEnrollmentService;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/enrollment")
public class StudentEnrollmentController {
    @Autowired
    private StudentEnrollmentService studentEnrollmentService;

    
    //----student's part
    @PostMapping("/request")
    public Map<String, Object>sendEnrollmentRequest(@RequestBody HashMap<String,String> req){
        return studentEnrollmentService.addToEnrollmentRequest(req.get("note"), req.get("teacherEmail"), req.get("studentEmail"));
    }
    
    
    
    //----teacher's part
    @GetMapping("/request/studentInfo/{teacherId}")
    public List<User> getRequestedStudentInfo(@PathVariable("teacherId") int teacherId){
        return studentEnrollmentService.getEnrollStudentInfo(teacherId);
    }
    
    @GetMapping("/request/studentInfo/note/{teacherId}")
    public List<String> getRequestStudentNote(@PathVariable int teacherId){
        return studentEnrollmentService.getStudentNote(teacherId);
    }
    
    @PostMapping("/request/studentInfo/requestHandle")
    public Map<String,String> studentRequestHandle(@RequestBody HashMap<String, String> req){
        Map<String, String> response = new LinkedHashMap<>();
        int studentId = Integer.parseInt(req.get("studentId"));
        int teacherId = Integer.parseInt(req.get("teacherId"));
        studentEnrollmentService.studentRequestHandle(req.get("status"), studentId, teacherId, req.get("displayName"));
        response.put("status", "success");
        return response;
    }
}
