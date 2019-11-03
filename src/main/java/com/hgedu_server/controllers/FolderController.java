/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.Folder;
import com.hgedu_server.services.FolderService;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/api/folder")
public class FolderController {
    @Autowired
    private FolderService folderService;
    
    @GetMapping("/getFoldersForNav")
    public Map<String, Object> getFoldersForNav(){
        return folderService.getFoldersForNav();
    }
    
    @PostMapping("/addFolder")
    public Folder addFolder(@RequestBody Folder folder) throws Exception {
        return folderService.addFolder(folder);
    }
    
    @PostMapping("/deleteFolder/{id}")
    public void deleteFolder(@PathVariable("id") int folderId) throws Exception {
        folderService.deleteFolder(folderId);
    }
}
