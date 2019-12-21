/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.TestContentPlaceholder;
import java.util.ArrayList;
import java.util.List;
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
public class TestServiceTest {
    @Autowired TestService tservice;
    public TestServiceTest() {
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
     * Test of getAllTestByFolderId method, of class TestService.
     */
    @Test
    public void testGetAllTestByFolderId() {
        System.out.println("getAllTestByFolderId");
        Long teacherId = (long)101;
        Long folderId = (long)108;
        com.hgedu_server.models.Test t = new com.hgedu_server.models.Test();
        com.hgedu_server.models.Test t2;
        t.setTestCode("THIDIEM");
        t.setTeacherId((long)101);
        t.setFolderId((long)266);
        t.setDateCreated(null);
        t.setPermutatedFrom(-1);
        t.setIsPublic(0);
        t.setTitle("Thi học kì I Toán 11");
        List<com.hgedu_server.models.Test> result = tservice.getAllTestByFolderId(teacherId, folderId);
        t2 = result.get(0);
        String first = t.getTitle();
        String second = t2.getTitle();
        System.out.println(t+"  "+t2);
        assertEquals(first, second);
        //---
//        List<com.hgedu_server.models.Test> result1 = tservice.getAllTestByFolderId(null, null);
//        assertTrue(result1.size());
     
    }

//    /**
//     * Test of addTest method, of class TestService.
//     */
//    @Test
    public void testAddTest() {
        System.out.println("addTest");
        TestContentPlaceholder testContentPlaceholder = null;
        String expResult = "";
        String result = tservice.addTest(testContentPlaceholder);
        assertEquals(expResult, result);
    }
//    
}
