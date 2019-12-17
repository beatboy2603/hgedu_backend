/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.services.ClassManagementService;
import com.hgedu_server.models.Class;
import com.hgedu_server.models.ClassStudent;
import com.hgedu_server.models.Exam;
import com.hgedu_server.models.Grade;
import com.hgedu_server.models.ParentStudent;
import com.hgedu_server.models.StudentTeacher;
import com.hgedu_server.models.User;
import com.hgedu_server.services.ClassStudentService;
import com.hgedu_server.services.MapValidationErrorService;
import com.hgedu_server.services.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/api/classManagement")
public class ClassManagementController {

    @Autowired
    private ClassManagementService classManagementService;

    @Autowired
    private ClassStudentService classStudentService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @GetMapping("/{teacherId}")
    public ResponseEntity<?> getClasses(@PathVariable int teacherId) {
        Iterable<Class> classes = classManagementService.findClassesByTeacherId(teacherId);
        return new ResponseEntity<Iterable<Class>>(classes, HttpStatus.OK);
    }
    
    @GetMapping("/studentClass/{studentId}")
        public ResponseEntity<?> getClassesByStudentId(@PathVariable long studentId) {
        Iterable<Class> classes = classManagementService.findClassesByStudentId(studentId);
        return new ResponseEntity<Iterable<Class>>(classes, HttpStatus.OK);
    }

    @GetMapping("/students/{classId}")
    public ResponseEntity<?> getStudents(@PathVariable long classId) {
        Iterable<User> students = userService.findUserByClassId(classId);
        return new ResponseEntity<Iterable<User>>(students, HttpStatus.OK);
    }

    @GetMapping("/student/{classStudentId}")
    public ResponseEntity<?> getStudenGrade(@PathVariable long classStudentId) {
        Iterable<Grade> grades = classManagementService.findByClassStudentId(classStudentId);
        return new ResponseEntity<Iterable<Grade>>(grades, HttpStatus.OK);
    }

    @GetMapping("/parent/{studentId}")
    public ResponseEntity<?> getParentInformation(@PathVariable int studentId) {
        User user = classManagementService.findParentInformationByStudentId(studentId);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<?> getTeacherByClassId(@PathVariable int teacherId) {
        User user = classManagementService.findTeacherInformation(teacherId);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("/studentTeacher/{teacherId}")
    public ResponseEntity<?> getStudentOfTeacher(@PathVariable long teacherId) {
        Iterable<StudentTeacher> studentTeachers = classManagementService.findByIsConnected(teacherId);
        return new ResponseEntity<Iterable<StudentTeacher>>(studentTeachers, HttpStatus.OK);
    }

    @GetMapping("/studentTeacher/teacher/{studentId}")
    public ResponseEntity<?> getTeacherOfStudent(@PathVariable long studentId) {
        Iterable<StudentTeacher> studentTeachers = classManagementService.findByIsConnectedAndStudentId(studentId);
        return new ResponseEntity<Iterable<StudentTeacher>>(studentTeachers, HttpStatus.OK);
    }

    @GetMapping("/studentTeacher/connected/{teacherId}")
    public ResponseEntity<?> getInformationOfConntectedStudent(@PathVariable int teacherId) {
        Iterable<User> users = classManagementService.findConnectedStudentByTeacherId(teacherId);
        return new ResponseEntity<Iterable<User>>(users, HttpStatus.OK);
    }

    @GetMapping("/studentTeacher/connected/teacher/{studentId}")
    public ResponseEntity<?> getInformationOfConntectedTeacher(@PathVariable int studentId) {
        Iterable<User> users = classManagementService.findConnectedTeacherByStudentId(studentId);
        return new ResponseEntity<Iterable<User>>(users, HttpStatus.OK);
    }

    @GetMapping("/classStudent/{classId}")
    public ResponseEntity<?> getClassStudent(@PathVariable long classId) {
        Iterable<ClassStudent> classStudents = classStudentService.getStudentsByClassId(classId);
        return new ResponseEntity<Iterable<ClassStudent>>(classStudents, HttpStatus.OK);
    }
    
    //jjjj
    @GetMapping("/classStudent/classes/{studentId}")
    public ResponseEntity<?> getClassStudentByStudentId(@PathVariable long studentId) {
        Iterable<ClassStudent> classStudents = classStudentService.getClassesByStudentId(studentId);
        return new ResponseEntity<Iterable<ClassStudent>>(classStudents, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addClass(@Valid @RequestBody Class cl, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        } else {
            System.out.println(cl.getTeacherId());
            Class class1 = classManagementService.addClass(cl);
            return new ResponseEntity<Class>(cl, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{classId}")
    public ResponseEntity<?> deleteById(@PathVariable long classId) {
        classManagementService.deleteById(classId);
        return new ResponseEntity<String>("Class with ID " + classId + " was deleted", HttpStatus.OK);
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable long id) {
        classStudentService.deleteById(id);
        return new ResponseEntity<String>("Student with ID " + id + " was deleted", HttpStatus.OK);
    }

    @DeleteMapping("/studentTeacher/{studentId}")
    public ResponseEntity<?> deleteStudentTeacherByStudentId(@PathVariable long studentId) {
        classManagementService.deleteStudentTeacher(studentId);
        return new ResponseEntity<String>("Student with ID " + studentId + " was deleted", HttpStatus.OK);
    }
    
    @GetMapping("/exam")
    public ResponseEntity<?> getExamsByClassId() {
        Iterable<Object[]> exams = classManagementService.findExamByClassId();
        return new ResponseEntity<Iterable<Object[]>>(exams, HttpStatus.OK); 
    }
    
    @GetMapping("/parentStudent/{parentId}")
    public  ResponseEntity<?> getStudentsByParentId(@PathVariable long parentId) {
        Iterable<User> students = classManagementService.findStudentByParentId(parentId);
        return new ResponseEntity<Iterable<User>>(students, HttpStatus.OK);
    }
    
    @GetMapping("/parentFindTeacher/{studentId}")
    public ResponseEntity<?> parentFindTeacherByStudentId(@PathVariable long studentId) {
        Iterable<Object[]> teachers = classManagementService.parentFindTeacherByStudentId(studentId);
        return new ResponseEntity<Iterable<Object[]>>(teachers, HttpStatus.OK); 
    }

}
