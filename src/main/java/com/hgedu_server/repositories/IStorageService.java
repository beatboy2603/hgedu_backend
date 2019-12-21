/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import java.io.IOException;
import java.nio.file.Path;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
public interface IStorageService {
    void init();
    
    Resource loadAsResource(String filename);

    String store(MultipartFile file, String dateCreated, Long userId);

    Path load(String filename);
    
    Boolean delete(String filename) throws IOException;
}
