/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hgedu_server.models.News;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface NewsRepository extends JpaRepository<News, Long>{
    List<News> findAllByOrderByIdDesc();
}
