/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.LinkRequest;
import com.hgedu_server.models.User;
import com.hgedu_server.repositories.LinkRequestRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
public class LinkRequestServiceTest {

    @Autowired
    LinkRequestService service;
    @Autowired
    LinkRequestRepository repo;

    public LinkRequestServiceTest() {
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
     * Test of checkSendRequest method, of class LinkRequestService.
     */
//    @Test
    public void testCheckSendRequest() {
        System.out.println("checkSendRequest");
        String parentMail = "ducbhse04740@fpt.edu.vn";
        String studentMail = "ducbhse04740@fpt.edu.vn";
        Map<String, Object> expResult = new LinkedHashMap<>();
        expResult.put("mess", "Bạn không thể gửi cho chính bạn");
        Map<String, Object> result = service.checkSendRequest(parentMail, studentMail);
        System.out.println(result);
        System.out.println(expResult);
        assertEquals(expResult, result);
        
        String parentMail1 = "ducbhse04740@fpt.edu.vn";
        String studentMail1 = "ducbhse04740@gmail.com";
        Map<String, Object>expResult1 = new LinkedHashMap<>();
        expResult1.put("mess", "Bạn đã gửi liên kết tới người dùng này rồi");
        Map<String, Object> result1;
        result1 = service.checkSendRequest(parentMail1, studentMail1);
        assertEquals(expResult1, result1);
//        
//    
////           ---- error ----
        String parentMail2 = "ducbhse04740@fpt.edu.vn";
        String studentMail2 = "abcdesc@gmail.com.vn";
        Map<String, Object> expResult2 = new LinkedHashMap<>();
        expResult2.put("mess", "Không tìm thấy người dùng");
        Map<String, Object> result2 ;
        result2 = service.checkSendRequest(parentMail2, studentMail2);
        assertEquals(expResult2, result2);
      
        String parentMail3 = "ducbhse04740@fpt.edu.vn";
        String studentMail3 = "huyngse05752@fpt.edu.vn";
        Map<String, Object>expResult3 = new LinkedHashMap<>();
        expResult3.put("mess", "Bạn gửi liên kết tới người dùng này rồi");
        Map<String, Object> result3 ;
        result3 = service.checkSendRequest(parentMail3, studentMail3);
        assertEquals(expResult3, result3);
//        
        String parentMail4 = "ducbhse04740@fpt.edu.vn";
        String studentMail4 = "dungbnse04948@fpt.edu.vn";
        Map<String, Object>expResult4 = new LinkedHashMap<>();
        expResult4.put("mess", "Gửi liên kết thành công!");
        Map<String, Object> result4 = service.checkSendRequest(parentMail4, studentMail4);
        assertEquals(expResult4, result4);
    }
    /**
     * Test of getRequest method, of class LinkRequestService. //
     */
//    @Test  
    public void testGetRequest() {
        System.out.println("getRequest");
        int expResult = 1;
//        int studentId = 0;
        List<LinkRequest> result = service.getRequest(146);
        int res = result.size();
        assertEquals(expResult, res);
    }
    /**
     * Test of getUserByRequest method, of class LinkRequestService.
     */
    @Test //done
    public void testGetUserByRequest() {
        System.out.println("getUserByRequest");
        String studentMail = "ducbhse04740@fpt.edu.vn";
        int expResult = 0;
        List<User> result = service.getUserByRequest(studentMail);
        int num = result.size();
        assertEquals(expResult, num);
    }

//    /**
//     * Test of getUserByEmail method, of class LinkRequestService.
//     */
//
    @Test
    public void testGetUserByEmail() {
        System.out.println("getUserByEmail");
        String email = "ducbhse04740@fpt.edu.vn";
        int expResult = 1;
        List<User> result = service.getUserByEmail(email);
        int num = result.size();
        assertEquals(expResult, num);
    }

//    /**
//     * Test of acceptRequest method, of class LinkRequestService.
//     */
    @Test
    public void testAcceptRequest() {
        System.out.println("acceptRequest");
        String parentEmail = "dungbnse04948@fpt.edu.vn";
        String studentEmail = "huyngse05752@fpt.edu.vn";
        repo.addRequest(parentEmail, studentEmail);
        service.acceptRequest(parentEmail, studentEmail);
        
    }
////
////    /**
////     * Test of refuseRequest method, of class LinkRequestService.
////     */
    @Test
    public void testRefuseRequest() {
        System.out.println("refuseRequest");
        String parentEmail = "dungbnse04948@fpt.edu.vn";
        String studentEmail = "huyngse05752@fpt.edu.vn";
        repo.addRequest(parentEmail, studentEmail);
        service.refuseRequest(parentEmail, studentEmail);
    }
}
