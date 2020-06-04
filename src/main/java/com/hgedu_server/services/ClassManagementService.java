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
import com.hgedu_server.models.ClassStudent;
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
import java.util.ArrayList;
import java.util.List;

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

    public Iterable<Class> findClassesByTeacherId(Long teacherId) {
        return classManagementRepository.findByTeacherId(teacherId);
    }

    public Iterable<Class> findClassesByStudentId(Long studentId) {
        return classManagementRepository.findClassByStudentId(studentId);
    }

    public Class addClass(Class cl) {
        return classManagementRepository.save(cl);
    }

    public void deleteById(Long classId) {
        classManagementRepository.deleteById(classId);
    }

    public Iterable<Grade> findByClassStudentId(Long id) {
        return gradeRepository.findByClassStudentIdOrderByPowerIdAsc(id);
    }

    public User findParentInformationByStudentId(Long studentId) {
        return userRepository.findParentInformationByStudentId(studentId);
    }

    public Iterable<StudentTeacher> findByIsConnected(Long teacherId) {
        boolean isConnected = true;
        return studentTeacherRepository.findByIsConnectedAndTeacherId(isConnected, teacherId);
    }

    public Iterable<StudentTeacher> findByIsConnectedAndStudentId(Long studentId) {
        boolean isConnected = true;
        return studentTeacherRepository.findByIsConnectedAndStudentId(isConnected, studentId);
    }

    public Iterable<User> findConnectedStudentByTeacherId(Long teacherId) {
        boolean isConnected = true;
        return userRepository.findConnectedStudentByTeacherId(teacherId, isConnected);
    }

    public Iterable<User> findConnectedTeacherByStudentId(Long studentId) {
        boolean isConnected = true;
        return userRepository.findConnectedTeacherByStudentId(studentId, isConnected);
    }

    public void deleteStudentTeacher(Long studentId) {
        studentTeacherRepository.deleteByStudentId(studentId);
    }

    public User findTeacherInformation(Long userId) {
        return userRepository.findByUserId(userId);
    }

    public Iterable<Object[]> findExamByClassId() {
        return examRepository.findExamsByClassId();
    }

    public Iterable<User> findStudentByParentId(Long parentId) {
        return userRepository.findStudentByParentId(parentId);
    }

    public Iterable<Object[]> parentFindTeacherByStudentId(Long studentId) {
        return userRepository.parentFindTeacherInformationByStudentId(studentId);
    }

    public Iterable<ClassStudent> addStudentToClass(Iterable<ClassStudent> students) {
        classStudentRepository.saveAll(students);
        List<ClassStudent> studentsAfterAdding = new ArrayList<>();
        for (ClassStudent student : students) {
            ClassStudent studentAfterAdding = classStudentRepository.findByClassIdAndStudentId(student.getClassId(), student.getStudentId());
            studentsAfterAdding.add(studentAfterAdding);
        }
        for (int i = 0; i < studentsAfterAdding.size(); i++) {
            if (gradeRepository.findByClassStudentId(studentsAfterAdding.get(i).getId()).size()==0) {
                Grade gradePower14 = new Grade(studentsAfterAdding.get(i).getId(), Long.valueOf(14), "");
                Grade gradePower15 = new Grade(studentsAfterAdding.get(i).getId(), Long.valueOf(15), "");
                Grade gradePower16 = new Grade(studentsAfterAdding.get(i).getId(), Long.valueOf(16), "");
                gradeRepository.save(gradePower14);
                gradeRepository.save(gradePower15);
                gradeRepository.save(gradePower16);
            }
        }
        return students;
    }

}
