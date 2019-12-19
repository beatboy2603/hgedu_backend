/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.repositories.ClassManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hgedu_server.models.Class;
import com.hgedu_server.models.Exam;
import com.hgedu_server.models.Grade;
import com.hgedu_server.models.StudentTeacher;
import com.hgedu_server.models.User;
import com.hgedu_server.repositories.ClassStudentRepository;
import com.hgedu_server.repositories.ExamRepository;
import com.hgedu_server.repositories.GradeRepository;
import com.hgedu_server.repositories.ParentStudentRepository;
import com.hgedu_server.repositories.StudentTeacherRepository;
import com.hgedu_server.repositories.UserRepository;

/**
 *
 * @author admin
 */
@Service
public class ClassManagementService {

    @Autowired
    private ClassManagementRepository classManagementRepository;

    @Autowired
    private ClassStudentRepository classStudentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GradeRepository gradeRepository;
    
    @Autowired
    private StudentTeacherRepository studentTeacherRepository;
    
    @Autowired
    private ExamRepository examRepository;

    
    public Iterable<Class> findClassesByTeacherId(int teacherId) {
        return classManagementRepository.findByTeacherId(teacherId);
    }
    
    public Iterable<Class> findClassesByStudentId(long studentId) {
        return classManagementRepository.findClassByStudentId(studentId);
    }

    public Class addClass(Class cl) {
        return classManagementRepository.save(cl);
    }

    public void deleteById(long classId) {
        classManagementRepository.deleteById(classId);
    }

    public Iterable<Grade> findByClassStudentId(long id) {
        return gradeRepository.findByClassStudentIdOrderByPowerIdAsc(id);
    }
    
    public User findParentInformationByStudentId(int studentId) {
        return userRepository.findParentInformationByStudentId(studentId);
    }
    
    public Iterable<StudentTeacher> findByIsConnected(long teacherId) {
        boolean isConnected = true;
        return studentTeacherRepository.findByIsConnectedAndTeacherId(isConnected, teacherId);
    }
    
    public Iterable<StudentTeacher> findByIsConnectedAndStudentId(long studentId) {
        boolean isConnected = true;
        return studentTeacherRepository.findByIsConnectedAndStudentId(isConnected, studentId);
    }
    
    public Iterable<User> findConnectedStudentByTeacherId(int teacherId) {
        boolean isConnected = true;
        return userRepository.findConnectedStudentByTeacherId(teacherId, isConnected);
    }
    
    public Iterable<User> findConnectedTeacherByStudentId(int studentId) {
        boolean isConnected = true;
        return userRepository.findConnectedTeacherByStudentId(studentId, isConnected);
    }
    
    public void deleteStudentTeacher(long studentId) {
        studentTeacherRepository.deleteByStudentId(studentId);
    }
    
    public User findTeacherInformation(int userId) {
        return userRepository.findByUserId(userId);
    }
    
    public Iterable<Object[]> findExamByClassId() {
        return examRepository.findExamsByClassId();
    }
    
    public Iterable<User> findStudentByParentId(long parentId) {
        return userRepository.findStudentByParentId(parentId);
    }
    
    public Iterable<Object[]> parentFindTeacherByStudentId(long studentId) {
        return userRepository.parentFindTeacherInformationByStudentId(studentId);
    }
    
    
    

}