/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.News;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
public class NewsServiceTest {

    @Autowired
    NewsService news;

    public NewsServiceTest() {
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
     * Test of createNews method, of class NewsService.
     */
    @Test
    public void testCreateNews_Map() {
//        System.out.println("createNews");
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("title", "abc");
        data.put("content", "dsc");
        data.put("shortDescription", "dsc");
        data.put("modId", "101");
        data.put("thumbnail", "thumb");
        System.out.println(data.get("title") + " key");
//        NewsService instance = new NewsService();
        News n = new News();
        n.setTitle("abc");
        n.setDescription("dsc");
        n.setThumbnail("thumb");
        n.setContent("dsc");
        n.setModId(101);
//        News expResult = n;
        News result = news.createNews(data);
        System.out.println(n.getTitle());
        System.out.println(result.getTitle());
        assertEquals(n.getTitle(), result.getTitle());
    }
//-----
    /**
     * Test of createNews method, of class NewsService.
     */
    @Test
    public void testCreateNews_News() {
        System.out.println("createNews");
        News n = new News();
        n.setTitle("abc");
        n.setDescription("desc");
        n.setThumbnail("thumb");
        n.setContent("con");
        n.setDateCreated(null);
        n.setModId(101);
        News result = news.createNews(n);
        assertEquals(n, result);

    }
//-----
//    /**
//     * Test of getAllNews method, of class NewsService.
//     */
    @Test
    public void testGetAllNews_Long() {
        System.out.println("getAllNews");
        Long modId = (long)1;
        System.out.println(modId);
        List<News> result = news.getAllNews(modId);
        int size = result.size();
        assertTrue(size > 1);
        
        Long mod2 = null;
        List<News> res2 = news.getAllNews(mod2);
        assertTrue(res2.size() < 1);

    }
//-----
//    /**
//     * Test of getAllNews method, of class NewsService.
//     */
    @Test
    public void testGetAllNews_0args() {
        System.out.println("getAllNews");
        int expResult = 13;
        List<News> result = news.getAllNews();
        int size = result.size();
        assertTrue(size > 1);
    }
//-----
//    /**
//     * Test of getNewsById method, of class NewsService.
//     */
    @Test
    public void testGetNewsById() {
        System.out.println("getNewsById");
        String title = "Em la ai";
        Optional<News> result = news.getNewsById((long)4);
        News n = result.get();
        assertEquals(title, n.getTitle());
        
//        Optional<News> result2 = news.getNewsById(null);
//        assertTrue(result2.get().getTitle().equals(""));

    }
// -----
//    /**
//     * Test of getOne method, of class NewsService.
//     */
    @Test
    public void testGetOne() {
        System.out.println("getOne");
        Long id = (long) 4;
        String title = "Em la ai";
        News result = news.getOne(id);
        assertEquals(title, result.getTitle());
//      --- check null ---
//        Long id2 = null;
//        News result2 = news.getOne(id2);
//        assertNull(result2.getTitle());
    }
// -----
    /**
     * Test of updateNews method, of class NewsService.
     */
    @Test
    public void testUpdateNews() {
        System.out.println("updateNews");
        News n = new News();
        n.setTitle("title");
        n.setDescription("A whole new world");
        n.setThumbnail("files/TwentyGraph_20191101071736_1.jpg");
        n.setContent("con");
        n.setDateCreated("2012-12-12");
        n.setModId(101);
        News result = news.updateNews(n);
        assertEquals(n, result);
    }
    /**
     * Test of deleteNews method, of class NewsService.
     */
    @Test
    public void testDeleteNews() throws Exception {
        System.out.println("deleteNews");
        Long id = (long)1103;
        news.deleteNews(id);
    }
}
