/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.User;
import java.util.Collection;
import java.util.Optional;
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
public class UserServiceTest {

    @Autowired
    UserService service;

    public UserServiceTest() {
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
     * Test of getAnUser method, of class UserService.
     */
    @Test
    public void testGetAnUser() {
        System.out.println("getAnUser");
        int userId = 146;
        User test = new User();
        test.setFullName("Bach Hung Duc - K11 FUG HN");
        Optional<User> result = service.getAnUser(userId);
        assertEquals(test.getFullName(), result.get().getFullName());

    }

    /**
     * Test of saveUser method, of class UserService.
     */
    @Test
    public void testSaveUser() {
        User user = new User();
        user.setFullName("new user");
        user.setEmail("banned@gmail.com");
        User expResult = new User();
        expResult.setFullName("new user");
        User result = service.saveUser(user);
        assertEquals(expResult.getFullName(), result.getFullName());
    }
//
//    /**
//     * Test of addMod method, of class UserService.
//     */

    @Test
    public void testAddMod() {
        System.out.println("addMod");
        User user = new User();
        user.setFullName("mod user");
        user.setEmail("modded@gmail.com");
        user.setRoleId(2);
        User expResult = new User();
        expResult.setFullName("mod user");
        expResult.setEmail("modded@gmail.com");
        expResult.setRoleId(2);
        User result = service.addMod(user);
        assertEquals(expResult.getRoleId(), result.getRoleId());
    }
//
//    /**
//     * Test of banUser method, of class UserService.
//     */
    @Test

    public void testBanUser() {
        System.out.println("banUser");
        User user = new User();
        user.setFullName("ban user");
        user.setEmail("banned@gmail.com");
        user.setIsBan(true);
        user.setBannedUntil("2019-12-20");
        User expResult = new User();
        expResult.setFullName("ban user");
        expResult.setEmail("banned@gmail.com");
        expResult.setIsBan(true);
        expResult.setBannedUntil("2019-12-20");
        User result = service.banUser(user);
        assertEquals(expResult.getFullName(), result.getFullName());
        assertEquals(expResult.isIsBan(), result.isIsBan());
        assertEquals(expResult.getBannedUntil(), result.getBannedUntil());
    }
//
//    /**
//     * Test of unBan method, of class UserService.
//     */

    @Test
    public void testUnBan() {
        System.out.println("unBan");
        User user = new User();
        user.setFullName("unban user");
        user.setEmail("banned@gmail.com");
        user.setIsBan(false);
        user.setBannedUntil(null);
        User expResult = new User();
        expResult.setFullName("unban user");
        expResult.setEmail("banned@gmail.com");
        expResult.setIsBan(false);
        expResult.setBannedUntil(null);
        User result = service.unBan(user);
        assertEquals(expResult.getFullName(), result.getFullName());
        assertEquals(expResult.isIsBan(), result.isIsBan());
        assertEquals(expResult.getBannedUntil(), result.getBannedUntil());
        assertEquals(expResult.isIsBan(), result.isIsBan());

    }
//
//    /**
//     * Test of banUserForever method, of class UserService.
//     */
    @Test

    public void testBanUserForever() {
        System.out.println("banUserForever");
        User user = new User();
        user.setFullName("banforever user");
        user.setEmail("bannedforever@gmail.com");
        user.setIsBanForever(true);
        user.setBannedUntil(null);
        User expResult = new User();
        expResult.setFullName("banforever user");
        expResult.setEmail("bannedforever@gmail.com");
        expResult.setIsBanForever(true);
        expResult.setBannedUntil(null);
        User result = service.banUserForever(user);
        assertEquals(expResult.isIsBanForever(), result.isIsBanForever());

    }
//
//    /**
//     * Test of findAll method, of class UserService.
//     */
    @Test

    public void testFindAll() {
        System.out.println("findAll");
        int expResult = 14;
        Iterable<User> result = service.findAll();
        int output = 0;
        if (result instanceof Collection) {
            output = ((Collection<User>) result).size();
        }
        assertEquals(expResult, output);
    }
//
//    /**
//     * Test of countUsers method, of class UserService.
//     */

    @Test
    public void testCountUsers() {
        System.out.println("countUsers");
        int expResult = 12;
        int result = service.countUsers();
        assertTrue( result > 1);

    }
//
//    /**
//     * Test of findUserByClassId method, of class UserService.
//     */

    @Test
    public void testFindUserByClassId() {
        System.out.println("findUserByClassId");
        long classId = (long) 146;
        int expResult = 0;
        Iterable<User> result = service.findUserByClassId(classId);
        int output = 0;
        if (result instanceof Collection) {
            output = ((Collection<User>) result).size();
        }
        assertEquals(expResult, output);
    }
}
