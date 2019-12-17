///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.hgedu_server.services;
//
//import com.hgedu_server.models.Class;
//import com.hgedu_server.models.ClassExam;
//import java.util.List;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
///**
// *
// * @author ADMIN
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ClassExamServiceTest {
//    
//    public ClassExamServiceTest() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of assignExamToClass method, of class ClassExamService.
//     */
//    @Test
//    public void testAssignExamToClass() {
//        System.out.println("assignExamToClass");
//        ClassExam item = null;
//        ClassExamService instance = new ClassExamService();
//        ClassExam expResult = null;
//        ClassExam result = instance.assignExamToClass(item);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of createClassExam method, of class ClassExamService.
//     */
//    @Test
//    public void testCreateClassExam() {
//        System.out.println("createClassExam");
//        List<ClassExam> classList = null;
//        ClassExamService instance = new ClassExamService();
//        List<ClassExam> expResult = null;
//        List<ClassExam> result = instance.createClassExam(classList);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getSelectedExamClasses method, of class ClassExamService.
//     */
//    @Test
//    public void testGetSelectedExamClasses() {
//        System.out.println("getSelectedExamClasses");
//        Long examId = null;
//        ClassExamService instance = new ClassExamService();
//        List<Class> expResult = null;
//        List<Class> result = instance.getSelectedExamClasses(examId);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getExamClasses method, of class ClassExamService.
//     */
//    @Test
//    public void testGetExamClasses() {
//        System.out.println("getExamClasses");
//        Long examId = null;
//        ClassExamService instance = new ClassExamService();
//        List<ClassExam> expResult = null;
//        List<ClassExam> result = instance.getExamClasses(examId);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deleteExamClass method, of class ClassExamService.
//     */
//    @Test
//    public void testDeleteExamClass() throws Exception {
//        System.out.println("deleteExamClass");
//        Long examClassId = null;
//        ClassExamService instance = new ClassExamService();
//        instance.deleteExamClass(examClassId);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    
//}
