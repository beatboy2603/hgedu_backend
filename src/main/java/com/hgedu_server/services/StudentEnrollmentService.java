/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.StudentRequest;
import com.hgedu_server.models.StudentTeacher;
import com.hgedu_server.models.TeacherRequest;
import com.hgedu_server.models.User;
import com.hgedu_server.repositories.StudentEnrollmentRepository;
import com.hgedu_server.repositories.StudentTeacherRepository;
import com.hgedu_server.repositories.TeacherRequestRepository;
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
    @Autowired
    TeacherRequestRepository teacherRequestRepository;

    //----student's part----
    public Map<String, Object> addToEnrollmentRequest(String note, String teacherEmail, String studentEmail) {
        Map<String, Object> resMessage = new LinkedHashMap<>();
        System.out.println(teacherEmail);
        System.out.println(studentEmail);
        if (teacherEmail.equals(studentEmail)) {
            System.out.println("yourself");
            resMessage.put("error", "Bạn không thể gửi cho chính bạn");
            return resMessage;
        }
        if (userRepository.getUserByEmail(teacherEmail).isEmpty()) {
            System.out.println("User not found");
            resMessage.put("error", "Không tìm thấy người dùng");
        } else {
            System.out.println("1");
            int studentId = userRepository.getUserIdByEmail(studentEmail);
            int teacherId = userRepository.getUserIdByEmail(teacherEmail);
            if (studentTeacherRepository.findByStudentIdAndTeacherId(studentId, teacherId) >= 1) {
                System.out.println("2");
                System.out.println("Already have link");
                resMessage.put("error", "Bạn đã có liên kết với người dùng này rồi");
            } else {
                System.out.println("3");
                if (studentEnrollmentRepository.checkDuplicateEnrolledUser(teacherEmail, studentId) >= 1) {
                    resMessage.put("error", "Bạn đã gửi liên kết với người dùng này rồi");
                } else {
                    System.out.println("4");
                    studentEnrollmentRepository.addToEnrollmentRequest(note, teacherEmail, studentId);
                    System.out.println("Success");
                    resMessage.put("success", "Gửi thành công!");
                }
            }
        }
        return resMessage;
    }

    public List<Object[]> getTeacherList(int studentId) {
        return studentTeacherRepository.getTeacherData(studentId);
    }

    public List<User> getRequestTeacher(int studentId) {
        return userRepository.getRequestTeacher(studentId);
    }

    //----teacher's part
    public List<Object[]> getStudentList(int teacherId) {
        return studentTeacherRepository.getStudentData(teacherId);
    }

    public User getInfo(int id) {
        System.out.println(id);
        User u = new User();
        User getUser = userRepository.getOne(id);
        u.setFullName(getUser.getFullName());
        u.setEmail(getUser.getEmail());
        u.setPhoneNumber(getUser.getPhoneNumber());
        u.setDob(getUser.getDob());
        u.setGender(getUser.isGender());
        u.setSchool(getUser.getSchool());
        return u;
    }

    public List<User> getStudentParentInfo(int id) {
        System.out.println(id);
        User u = new User();
        List<User> getUser = userRepository.getParentInfoByStudentId(id);
        if (getUser != null) {

            return getUser;
        } else {
            return null;
        }
    }

    public Map<String, String> sendRequestToStudent(String teacherEmail, String studentEmail, String displayName) {
        Map<String, String> res = new LinkedHashMap<>();
        System.out.println("Teacher send request ở đây");
        if (teacherEmail.equals(studentEmail)) {
            res.put("error", "Bạn không thể gửi cho chính bạn");
            return res;
        }
        if (userRepository.getUserByEmail(studentEmail).isEmpty()) {
            System.out.println("User not found");
            res.put("error", "Không tìm thấy người dùng");
        } else {
            int studentId = userRepository.getUserIdByEmail(studentEmail);
            int teacherId = userRepository.getUserIdByEmail(teacherEmail);

            System.out.println("student: " + studentId);
            System.out.println("teacher: " + teacherId);
            if (studentTeacherRepository.findByStudentIdAndTeacherId(studentId, teacherId) >= 1) {
                System.out.println("Already have link");
                res.put("error", "Bạn đã có liên kết với người dùng này rồi");
            } else {
                if (teacherRequestRepository.checkDuplicateEnrolledUser(teacherEmail, studentId) >= 1) {
                    System.out.println("sent request already");
                    res.put("error", "Bạn đã gửi liên kết với người dùng này rồi");
                } else {
                    System.out.println(displayName);
                    if (displayName == null)  {
                        displayName = userRepository.getOne(studentId).getFullName();
                    }
                    TeacherRequest tr = new TeacherRequest();
                    tr.setTeacherEmail(teacherEmail);
                    tr.setStudentId(studentId);
                    tr.setDisplayedName(displayName);
                    teacherRequestRepository.save(tr);
                    res.put("success", "Gửi thành công");
                }
            }
        }
        return res;
    }

    public List<User> getEnrollStudentInfo(int teacherId) {
        System.out.println("teacher email: " + userRepository.getOne(teacherId).getEmail());
        List<User> listStudent = userRepository.getEnrolledStudentByRequestEmail(userRepository.getOne(teacherId).getEmail());
        if (listStudent.isEmpty()) {
            System.out.println("null");
            return null;
        } else {
            System.out.println("not null");
            return listStudent;
        }
    }

    public List<String> getStudentNote(int teacherId) {
        return studentEnrollmentRepository.getStudentNote(userRepository.getOne(teacherId).getEmail());
    }

    public StudentTeacher saveStudentTeacher(StudentTeacher st) {
        return studentTeacherRepository.save(st);
    }

//    public void removeRequest(String teacherEmail, int studentId) {
//        System.out.println("teacherEmail: " + teacherEmail);
//        System.out.println("studentId: " + studentId);
//        if (Integer.toString(studentId) != null && teacherEmail != null) {
//            studentEnrollmentRepository.deleteByTeacherEmailAndStudentId(teacherEmail, studentId);
//        }
//        if (Integer.toString(studentId) != null && teacherEmail != null) {
//
//            teacherRequestRepository.deleteByTeacherEmailAndStudentId(teacherEmail, studentId);
//        }
//    }
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
                    studentEnrollmentRepository.deleteByTeacherEmailAndStudentId(userRepository.getOne(teacherId).getEmail(), studentId);
                    break;
                }
                case "refuse": {
                    studentEnrollmentRepository.deleteByTeacherEmailAndStudentId(userRepository.getOne(teacherId).getEmail(), studentId);
                    break;
                }
            }
        }
    }

    public void teacherRequestHandle(String status, int teacherId, int studentId) {
        StudentTeacher st = new StudentTeacher();
        String displayName = teacherRequestRepository.findByTeacherEmailAndStudentId(userRepository.getOne(teacherId).getEmail(), studentId).getDisplayedName();
        if (status != null) {
            switch (status) {
                case "accept": {
                    st.setStudentId(studentId);
                    st.setTeacherId(teacherId);
                    st.setDisplayedName(displayName);
                    st.setIsConnected(true);
                    saveStudentTeacher(st);
                    teacherRequestRepository.removeByTeacherEmailAndStudentId(userRepository.getOne(teacherId).getEmail(), studentId);
                    break;
                }
                case "refuse": {
                    System.out.println("teacher email: " + userRepository.getOne(teacherId).getEmail());
                    System.out.println("student id: " + studentId);
                    teacherRequestRepository.removeByTeacherEmailAndStudentId(userRepository.getOne(teacherId).getEmail(), studentId);
                    break;
                }
            }
        }
    }

    public List<Object[]> getStudentInfo(int teacherId) {
        return studentTeacherRepository.getStudentData(teacherId);
    }
}
