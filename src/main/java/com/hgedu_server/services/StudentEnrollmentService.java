/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.StudentEnrollment;
import com.hgedu_server.models.StudentTeacher;
import com.hgedu_server.models.User;
import com.hgedu_server.repositories.StudentEnrollmentRepository;
import com.hgedu_server.repositories.StudentTeacherRepository;
import com.hgedu_server.repositories.UserRepository;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class StudentEnrollmentService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    StudentEnrollmentRepository studentEnrollmentRepository;
    @Autowired
    StudentTeacherRepository studentTeacherRepository;

    //----student's part----
    public Map<String, Object> addToEnrollmentRequest(String note, String teacherEmail, String studentEmail) {
        Map<String, Object> resMessage = new LinkedHashMap<>();
        int studendId = userRepository.getUserIdByEmail(studentEmail);
        if (teacherEmail.equals(studentEmail)) {
            System.out.println("yourself");
            resMessage.put("error", "Bạn không thể gửi cho chính bạn");
            return resMessage;
        }
        if (userRepository.getUserByEmail(teacherEmail).isEmpty()) {
            System.out.println("User not found");
            resMessage.put("error", "Không tìm thấy người dùng");
        } else {
            if (studentEnrollmentRepository.checkDuplicateEnrolledUser(teacherEmail, studendId) >= 1) {
                resMessage.put("error", "Bạn đã gửi liên kết với người dùng này rồi");
            } else {
                studentEnrollmentRepository.addToEnrollmentRequest(note, teacherEmail, studendId);
                System.out.println("Success");
                resMessage.put("success", "Gửi thành công!");
            }
        }
        return resMessage;
    }

    //----teacher's part
    public List<User> getEnrollStudentInfo(int teacherId) {
        System.out.println("teacher email: " + userRepository.getOne(teacherId).getEmail());
        List<User> listStudent = userRepository.getEnrolledStudentByRequestEmail(userRepository.getOne(teacherId).getEmail());
        if (listStudent.isEmpty()) {
            System.out.println("null sml");
            return null;
        } else {
            System.out.println("not null...");
            return listStudent;
        }
    }

    public List<String> getStudentNote(int teacherId) {
        return studentEnrollmentRepository.getStudentNote(userRepository.getOne(teacherId).getEmail());
    }

    public StudentTeacher saveStudentTeacher(StudentTeacher st){
        return studentTeacherRepository.save(st);
    }
    
    public void studentRequestHandle(String status, int teacherId, int studentId, String displayName) {
        StudentTeacher st = new StudentTeacher();
        if (status != null) {
            switch (status) {
                case "accept": {
                    st.setStudentId(studentId);
                    st.setTeacherId(teacherId);
                    st.setDisplayedName(displayName);
                    st.setIsConnected(true);
                    saveStudentTeacher(st);
                    break;
                }
                case "refuse": {
                    break;
                }
            }
        }
    }
}
