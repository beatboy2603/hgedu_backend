/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.News;
import com.hgedu_server.repositories.NewsRepository;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepo;
    
    public News createNews(Map<String, Object> data) {
        News news = new News();
        for(String key: data.keySet()) {
            switch(key) {
                case "title":
                    news.setTitle((String) data.get(key));
                    break;
                case "shortDescription":
                    news.setDescription((String) data.get(key));
                    break;
                case "thumbnail":
                    news.setThumbnail((String) data.get(key));
                    break;
                case "content":
                    news.setContent((String) data.get(key));
                    break;
                case "dateCreated":
                    news.setDateCreated((String)data.get(key));
                    break;
                case "modId":
                    news.setModId(Integer.parseInt((String) data.get(key)));
                    break;
            }
        }
        return newsRepo.save(news);
    }
    
    public News createNews(News news) {
        return newsRepo.save(news);
    }
    
    public List<News> getAllNews() {
        return newsRepo.findAllByOrderByIdDesc();
    }
    
    public Optional<News> getNewsById(Long id) {
        Optional<News> news = newsRepo.findById(id);
        return news;
    }
    
    public News updateNews(News news) {
        return newsRepo.save(news);
    }
    
    public void deleteNews(Long id) throws Exception{
        try {
            newsRepo.deleteById(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
