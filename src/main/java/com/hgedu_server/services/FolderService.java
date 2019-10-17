/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.Folder;
import com.hgedu_server.repositories.FolderRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class FolderService {
    @Autowired
    private FolderRepository folderRepository;
    
    public List<Folder> getAllSubfolders(int teacherId, int parentFolderId){
        return folderRepository.getAllSubfolders(teacherId, parentFolderId);
    }
    
    public boolean checkFolderExisting(int folderId){
        Folder folder = folderRepository.getOne(folderId);
        if(folder!=null){
            System.out.println(folder.getFolderName());
            return true;
        }else{
            return false;
        }
    }
    
    public Map<String, Object> getFoldersForNav() {
        Map<String, Object> responseList = new LinkedHashMap<>();
        List<Object> folders = new ArrayList<>();
        loadFolders(responseList, folders, 1, 0);
        return responseList;
    }
    
    private void loadFolders(Map<String, Object> responseList, List<Object> folder, int teacherId, int parentFolderId) {
        try {
            List<Folder> subfolders = folderRepository.getAllSubfolders(teacherId, parentFolderId);
            for (int i = 0; i < subfolders.size(); i++) {
                responseList.put("folder" + subfolders.get(i).getFolderId(), subfolders.get(i));
                if (folderRepository.getAllSubfolders(teacherId, subfolders.get(i).getFolderId()).size() > 0) {
                    HashMap<String, List> subfolder = new HashMap<>();
                    List<Object> subfoldersOfThis = new ArrayList<>();
                    loadFolders(responseList, subfoldersOfThis, teacherId, subfolders.get(i).getFolderId());

                    subfolder.put(subfolders.get(i).getFolderId() + "", subfoldersOfThis);
                    folder.add(subfolder);
                } else {
                    folder.add(subfolders.get(i).getFolderId() + "");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(FolderService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
