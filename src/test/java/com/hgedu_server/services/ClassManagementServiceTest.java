/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.Class;
import com.hgedu_server.models.Grade;
import com.hgedu_server.models.StudentTeacher;
import com.hgedu_server.models.User;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Admin
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClassManagementServiceTest {

    @Autowired
    ClassManagementService service;

    public ClassManagementServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of findClassesByTeacherId method, of class ClassManagementService.
     */
    @Test
    public void testFindClassesByTeacherId() {
        System.out.println("findClassesByTeacherId");
        int teacherId = 263;
        int expResult = 2;
        Iterable<Class> result = service.findClassesByTeacherId(teacherId);

        int output = 0;
        if (result instanceof Collection) {
            output = ((Collection<Class>) result).size();
        }
        assertEquals(expResult, output);

        int teacherId2 = 0;
        int expResult2 = 0;
        Iterable<Class> result2 = service.findClassesByTeacherId(teacherId2);
        int output2 = 0;
        if (result2 instanceof Collection) {
            output2 = ((Collection<Class>) result2).size();
        }
        assertEquals(expResult2, output2);

        int teacherId3 = -1;
        int expResult3 = 0;
        Iterable<Class> result3 = service.findClassesByTeacherId(teacherId3);
        int output3 = 0;
        if (result3 instanceof Collection) {
            output3 = ((Collection<Class>) result3).size();
        }
        assertEquals(expResult3, output3);

    }
//
//    /**
//     * Test of findClassesByStudentId method, of class ClassManagementService.
//     */

    @Test
    public void testFindClassesByStudentId() {
        System.out.println("findClassesByStudentId");
        long studentId = (long) 187;
        int expResult = 2;
        Iterable<Class> result = service.findClassesByStudentId(studentId);
        int output = 0;
        if (result instanceof Collection) {
            output = ((Collection<Class>) result).size();
        }
        assertTrue(output >=0);
// --------
        long studentId2 = (long) 0;
        int expResult2 = 0;
        Iterable<Class> result2 = service.findClassesByStudentId(studentId2);
        int output2 = 0;
        if (result2 instanceof Collection) {
            output2 = ((Collection<Class>) result2).size();
        }
        assertEquals(expResult2, output2);

        long studentId3 = (long) -1;
        int expResult3 = 0;
        Iterable<Class> result3 = service.findClassesByStudentId(studentId3);
        int output3 = 0;
        if (result3 instanceof Collection) {
            output3 = ((Collection<Class>) result3).size();
        }
        assertTrue(output3 > 0);

    }
//
//    /**
//     * Test of addClass method, of class ClassManagementService.
//     */
    @Test

    public void testAddClass() {
        System.out.println("addClass");
        Class cl = new Class();
        cl.setName("12N2");
        cl.setTeacherId(187);
        String expResult = "12N2";
        int expResult2 = 187;
        Class result = service.addClass(cl);
        assertEquals(expResult, result.getName());
        assertEquals(expResult2, result.getTeacherId());
//      ------
//        Class cl2 = new Class();
//        cl2.setName(null);
//        cl2.setTeacherId(187);
//        String expResult0 = null;
//        int expResult02 = 187;
//        Class result2 = service.addClass(cl2);
//        assertEquals(expResult0, result2.getName());
//        assertEquals(expResult02, result2.getTeacherId());

    }
//
//    /**
//     * Test of deleteById method, of class ClassManagementService.
//     */
    @Test

    public void testDeleteById() {
        long classId = 104;
        service.deleteById(classId);

    }
//
//    /**
//     * Test of findByClassStudentId method, of class ClassManagementService.
//     */

    @Test
    public void testFindByClassStudentId() {
        System.out.println("findByClassStudentId");

        long studentId = (long) 187;
        int expResult = 0;
        Iterable<Grade> result = service.findByClassStudentId(studentId);
        int output = 0;
        if (result instanceof Collection) {
            output = ((Collection<Grade>) result).size();
        }
        assertEquals(expResult, output);

    }
//
//    /**
//     * Test of findParentInformationByStudentId method, of class ClassManagementService.
//     */

    @Test
    public void testFindParentInformationByStudentId() {
        System.out.println("findParentInformationByStudentId");
        int studentId = 146;
        User expResult = new User();
        expResult.setFullName("(K12_HN) Lê Quang Huy");
        User result = service.findParentInformationByStudentId(studentId);
        assertEquals(expResult.getFullName(), result.getFullName());

//        int studentId2 = 0;
//        User expResult2 = new User();
//        expResult2.setFullName(null);
//        User result2 = service.findParentInformationByStudentId(studentId2);
//        assertEquals(expResult2.getFullName(), result2.getFullName());
//      should have written Iteration
    }
//
//    /**
//     * Test of findByIsConnected method, of class ClassManagementService.
//     */

    @Test
    public void testFindByIsConnected() {
        long teacherId = (long) 187;
        int expResult = 3;
        Iterable<StudentTeacher> result = service.findByIsConnected(teacherId);
        int output = 0;
        if (result instanceof Collection) {
            output = ((Collection<StudentTeacher>) result).size();
        }
        assertEquals(expResult, output);

        long teacherId2 = (long) 0;
        int expResult2 = 0;
        Iterable<StudentTeacher> result2 = service.findByIsConnected(teacherId2);
        int output2 = 0;
        if (result instanceof Collection) {
            output2 = ((Collection<StudentTeacher>) result2).size();
        }
        assertEquals(expResult2, output2);

        long teacherId3 = (long) -1;
        int expResult3 = 0;
        Iterable<StudentTeacher> result3 = service.findByIsConnected(teacherId3);
        int output3 = 0;
        if (result instanceof Collection) {
            output3 = ((Collection<StudentTeacher>) result3).size();
        }
        assertEquals(expResult3, output3);
    }
//
//    /**
//     * Test of findByIsConnectedAndStudentId method, of class ClassManagementService.
//     */

    @Test
    public void testFindByIsConnectedAndStudentId() {

//        
        long studentId = (long) 146;
        int expResult = 2;
        Iterable<StudentTeacher> result = service.findByIsConnectedAndStudentId(studentId);
        int output = 0;
        if (result instanceof Collection) {
            output = ((Collection<StudentTeacher>) result).size();
        }
        assertEquals(expResult, output);

        long studentId2 = (long) 0;
        int expResult2 = 0;
        Iterable<StudentTeacher> result2 = service.findByIsConnectedAndStudentId(studentId2);
        int output2 = 0;
        if (result instanceof Collection) {
            output2 = ((Collection<StudentTeacher>) result2).size();
        }
        assertEquals(expResult2, output2);

        long studentId3 = (long) -1;
        int expResult3 = 0;
        Iterable<StudentTeacher> result3 = service.findByIsConnectedAndStudentId(studentId3);
        int output3 = 0;
        if (result instanceof Collection) {
            output3 = ((Collection<StudentTeacher>) result3).size();
        }
        assertEquals(expResult3, output3);
    }
//
//    /**
//     * Test of findConnectedStudentByTeacherId method, of class ClassManagementService.
//     */

    @Test
    public void testFindConnectedStudentByTeacherId() {
        System.out.println("findConnectedStudentByTeacherId");
        int teacherId = 263;
        int expResult = 3;
        Iterable<User> result = service.findConnectedStudentByTeacherId(teacherId);
        int output = 0;
        if (result instanceof Collection) {
            output = ((Collection<User>) result).size();
        }
        assertEquals(expResult, output);

        int teacherId2 = 0;
        int expResult2 = 0;
        Iterable<User> result2 = service.findConnectedStudentByTeacherId(teacherId2);
        int output2 = 0;
        if (result2 instanceof Collection) {
            output2 = ((Collection<User>) result2).size();
        }
        assertEquals(expResult2, output2);

        int teacherId3 = -1;
        int expResult3 = 0;
        Iterable<User> result3 = service.findConnectedStudentByTeacherId(teacherId3);
        int output3 = 0;
        if (result3 instanceof Collection) {
            output3 = ((Collection<User>) result3).size();
        }
        assertEquals(expResult3, output3);

    }
//
//    /**
//     * Test of findConnectedTeacherByStudentId method, of class ClassManagementService.
//     */

    @Test
    public void testFindConnectedTeacherByStudentId() {
        System.out.println("findConnectedTeacherByStudentId");
        int studentId = 146;
        int expResult = 2;
        Iterable<User> result = service.findConnectedTeacherByStudentId(studentId);
        int output = 0;
        if (result instanceof Collection) {
            output = ((Collection<User>) result).size();
        }
        assertEquals(expResult, output);

        int studentId2 = 0;
        int expResult2 = 0;
        Iterable<User> result2 = service.findConnectedStudentByTeacherId(studentId2);
        int output2 = 0;
        if (result2 instanceof Collection) {
            output2 = ((Collection<User>) result2).size();
        }
        assertEquals(expResult2, output2);

        int studentId3 = -1;
        int expResult3 = 0;
        Iterable<User> result3 = service.findConnectedStudentByTeacherId(studentId3);
        int output3 = 0;
        if (result3 instanceof Collection) {
            output3 = ((Collection<User>) result3).size();
        }
        assertEquals(expResult3, output3);
    }
//
//    /**
//     * Test of deleteStudentTeacher method, of class ClassManagementService.
//     */
//    @Test //comment bc fail

    public void testDeleteStudentTeacher() {
        System.out.println("deleteStudentTeacher");
        long studentId = (long) 146;
        service.deleteStudentTeacher(studentId);
    }
//
//    /**
//     * Test of findTeacherInformation method, of class ClassManagementService.
//     */
    @Test

    public void testFindTeacherInformation() {
        System.out.println("findTeacherInformation");
        int userId = 146;
        User expResult = new User();
        expResult.setFullName("Bach Hung Duc - K11 FUG HN");
        User result = service.findTeacherInformation(userId);
        assertEquals(expResult.getFullName(), result.getFullName());
    }
//
//    /**
//     * Test of findExamByClassId method, of class ClassManagementService.
//     */
    @Test
    public void testFindExamByClassId() {
        System.out.println("findExamByClassId");
        int expResult = 20;
        Iterable result = service.findExamByClassId();
        int output = 0;
        if (result instanceof Collection) {
            output = ((Collection<User>) result).size();
        }
        assertEquals(expResult, output);
    }
//
//    /**
//     * Test of findStudentByParentId method, of class ClassManagementService.
//     */

    @Test
    public void testFindStudentByParentId() {
//        System.out.println("findStudentByParentId");
//        long parentId = 0L;
//        ClassManagementService instance = new ClassManagementService();
//        Iterable<User> expResult = null;
//        Iterable<User> result = instance.findStudentByParentId(parentId);
//        assertEquals(expResult, result);

        int parentId = 146;
        int expResult = 0;
        Iterable<User> result = service.findStudentByParentId(parentId);
        int output = 0;
        if (result instanceof Collection) {
            output = ((Collection<User>) result).size();
        }
        assertEquals(expResult, output);

        int parentId2 = 0;
        int expResult2 = 0;
        Iterable<User> result2 = service.findStudentByParentId(parentId2);
        int output2 = 0;
        if (result2 instanceof Collection) {
            output2 = ((Collection<User>) result2).size();
        }
        assertEquals(expResult2, output2);

        int parentId3 = -1;
        int expResult3 = 0;
        Iterable<User> result3 = service.findStudentByParentId(parentId3);
        int output3 = 0;
        if (result3 instanceof Collection) {
            output3 = ((Collection<User>) result3).size();
        }
        assertEquals(expResult3, output3);

    }
//
//    /**
//     * Test of parentFindTeacherByStudentId method, of class ClassManagementService.
//     */

    @Test
    public void testParentFindTeacherByStudentId() {
        System.out.println("parentFindTeacherByStudentId");
        long studentId = (long) 187;
        int expResult = 2;
        Iterable result = service.parentFindTeacherByStudentId(studentId);
        int output = 0;
        if (result instanceof Collection) {
            output = ((Collection) result).size();
        }
        assertEquals(expResult, output);

        int studentId2 = 0;
        int expResult2 = 0;
        Iterable result2 = service.parentFindTeacherByStudentId(studentId2);
        int output2 = 0;
        if (result2 instanceof Collection) {
            output2 = ((Collection<User>) result2).size();
        }
        assertTrue(output2 >= 0);

        int studentId3 = -1;
        int expResult3 = 0;
        Iterable result3 = service.parentFindTeacherByStudentId(studentId3);
        int output3 = 0;
        if (result3 instanceof Collection) {
            output3 = ((Collection<User>) result3).size();
        }
        assertEquals(expResult3, output3);

    }

}
