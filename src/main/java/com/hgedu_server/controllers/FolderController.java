/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.Folder;
import com.hgedu_server.services.FolderService;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @GetMapping("/{folderId}")
    public Folder getFolderById(@PathVariable("folderId") int folderId) throws Exception {
        return folderService.getFolderById(folderId);
    }

    @PostMapping("/getFoldersForNav")
    public Map<String, Object> getFoldersForNav(@RequestParam("uid") int teacherId) {
        return folderService.getFoldersForNav(teacherId);
    }
    
    @PostMapping("/addFolder")
    @ResponseBody
    public Folder addFolder(@RequestBody Folder folder) throws Exception {
        return folderService.addFolder(folder);
    }
    
    @PostMapping("/updateFolder")
    @ResponseBody
    public Folder updateFolder(@RequestBody Folder folder) throws Exception {
        return folderService.addFolder(folder);
    }
    
//    @PostMapping("/testFolder")
//    @ResponseBody
//    public List<Folder> test(@RequestBody List<Folder> folder) throws Exception {
//        System.out.println(folder.size());
//        System.out.println(folder.get(0).getFolderId());
//        System.out.println("a");
//        return folder;
//    }

    @PostMapping("/deleteFolder")
    public void deleteFolder(@RequestParam("folderId") int folderId) throws Exception {
        folderService.deleteFolder(folderId);
    }
    
    @GetMapping("/testRoot/{teacherId}")
    public ResponseEntity<?> getRootTestsFolder(@PathVariable("teacherId") Long teacherId) {
        Folder folder = folderService.getRootTestsFolder(teacherId);
        return ResponseEntity.ok(folder);
    }
}
