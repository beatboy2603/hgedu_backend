/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.News;
import com.hgedu_server.services.NewsService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
    
    @Autowired
    private NewsService newsService;
    
    @PostMapping(value = "/create-news")
    public ResponseEntity createNews(@Valid @RequestBody News news ) {
        News createdNews = newsService.createNews(news);
        return ResponseEntity.ok(createdNews);
    }
    
    @GetMapping("/news")
    public ResponseEntity getAllNews() {
      List<News> list = newsService.getAllNews();
      return ResponseEntity.ok(list);
    }
    
    @GetMapping("/news/{id}")
    public ResponseEntity getNews(@PathVariable("id") Long id) {
      Optional<News> news = newsService.getNewsById(id);
      if (news.isPresent()) {
        logger.info("present");
        return new ResponseEntity<>(news.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    
    @PostMapping("/news/{id}")
    public ResponseEntity updateNews(@PathVariable("id") Long id, @RequestBody Map<String, Object> news) {
      Optional<News> data = newsService.getNewsById(id);
      if (data.isPresent()) {
        News savedNews = data.get();
        logger.info(savedNews.getTitle());
        savedNews.setTitle((String) news.get("title"));
        savedNews.setDescription((String) news.get("shortDescription"));
        savedNews.setDateCreated((String) news.get("dateCreated"));
        savedNews.setContent((String) news.get("content"));
        savedNews.setThumbnail((String) news.get("thumbnail"));

        News updatedNews = newsService.updateNews(savedNews);
        return new ResponseEntity<>(updatedNews, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    
    @DeleteMapping("/news/{id}")
    public ResponseEntity deleteNews(@PathVariable("id") Long id) {
      logger.info("delete: " + id);
      try {
        newsService.deleteNews(id);
      } catch (Exception e) {
        return new ResponseEntity<>("Fail to delete!", HttpStatus.EXPECTATION_FAILED);
      }

      return new ResponseEntity<>("News has been deleted!", HttpStatus.OK);
    }
}
