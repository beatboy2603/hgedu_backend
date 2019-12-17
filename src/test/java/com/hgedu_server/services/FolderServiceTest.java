/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.Folder;
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
 * @author ADMIN
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FolderServiceTest {
    
    @Autowired
    private FolderService folderService;
    
    public FolderServiceTest() {
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
     * Test of getFolderByName method, of class FolderService.
     */
//    @Test
//    public void testGetFolderByName() {
//        System.out.println("getFolderByName");
//        int teacherId = 0;
//        String folderName = "";
//        FolderService instance = new FolderService();
//        List<Folder> expResult = null;
//        List<Folder> result = instance.getFolderByName(teacherId, folderName);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFolderById method, of class FolderService.
//     */
//    @Test
//    public void testGetFolderById() {
//        System.out.println("getFolderById");
//        int folderId = 0;
//        FolderService instance = new FolderService();
//        Folder expResult = null;
//        Folder result = instance.getFolderById(folderId);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFolderByNameFromRootFolders method, of class FolderService.
//     */
//    @Test
//    public void testGetFolderByNameFromRootFolders() {
//        System.out.println("getFolderByNameFromRootFolders");
//        int teacherId = 0;
//        String folderName = "";
//        FolderService instance = new FolderService();
//        Folder expResult = null;
//        Folder result = instance.getFolderByNameFromRootFolders(teacherId, folderName);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getAllSubfolders method, of class FolderService.
//     */
//    @Test
//    public void testGetAllSubfolders_int_int() {
//        System.out.println("getAllSubfolders");
//        int teacherId = 0;
//        int parentFolderId = 0;
//        FolderService instance = new FolderService();
//        List<Folder> expResult = null;
//        List<Folder> result = instance.getAllSubfolders(teacherId, parentFolderId);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getAllSubfolders method, of class FolderService.
//     */
//    @Test
//    public void testGetAllSubfolders_Long_Long() {
//        System.out.println("getAllSubfolders");
//        Long teacherId = null;
//        Long parentFolderId = null;
//        FolderService instance = new FolderService();
//        List<Folder> expResult = null;
//        List<Folder> result = instance.getAllSubfolders(teacherId, parentFolderId);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRootTestsFolder method, of class FolderService.
//     */
//    @Test
//    public void testGetRootTestsFolder() {
//        System.out.println("getRootTestsFolder");
//        Long teacherId = null;
//        FolderService instance = new FolderService();
//        Folder expResult = null;
//        Folder result = instance.getRootTestsFolder(teacherId);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of checkFolderExisting method, of class FolderService.
//     */
    @Test
    public void testCheckFolderExisting_int() {
        System.out.println("checkFolderExisting int");
        int folderId = 198;
//        FolderService instance = new FolderService();
        boolean expResult = true;
        boolean result = folderService.checkFolderExisting(folderId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
    @Test
    public void testCheckFolderExisting_int2() {
        System.out.println("checkFolderExisting int false");
        int folderId = 0;
//        FolderService instance = new FolderService();
        boolean expResult = false;
        boolean result = folderService.checkFolderExisting(folderId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of checkFolderExisting method, of class FolderService.
     */
    @Test
    public void testCheckFolderExisting_Long() {
        System.out.println("checkFolderExistingUT");
        Long folderId = null;
//        FolderService instance = new FolderService();
        boolean expResult = false;
        boolean result = folderService.checkFolderExisting(folderId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

//    /**
//     * Test of addFolder method, of class FolderService.
//     */
//    @Test
//    public void testAddFolder() {
//        System.out.println("addFolder");
//        Folder folder = null;
//        FolderService instance = new FolderService();
//        Folder expResult = null;
//        Folder result = instance.addFolder(folder);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deleteFolder method, of class FolderService.
//     */
//    @Test
//    public void testDeleteFolder() throws Exception {
//        System.out.println("deleteFolder");
//        int folderId = 0;
//        FolderService instance = new FolderService();
//        instance.deleteFolder(folderId);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFoldersForNav method, of class FolderService.
//     */
//    @Test
//    public void testGetFoldersForNav() {
//        System.out.println("getFoldersForNav");
//        int teacherId = 0;
//        FolderService instance = new FolderService();
//        Map<String, Object> expResult = null;
//        Map<String, Object> result = instance.getFoldersForNav(teacherId);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
