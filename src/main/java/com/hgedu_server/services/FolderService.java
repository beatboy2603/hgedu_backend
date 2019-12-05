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
import java.util.Optional;
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

    public List<Folder> getFolderByName(int teacherId, String folderName) {
//        Folder folder = folderRepository.findByTeacherIdAndFolderName(teacherId, folderName);
//        System.out.println(folder.getFolderId());
        return folderRepository.findByTeacherIdAndFolderName(teacherId, folderName);
    }
    
    public Folder getFolderById(int folderId) {
        List<Folder> folders = folderRepository.findByFolderId(folderId);
        if (folders.size() > 0) {
            return folders.get(0);
        } else {
            return null;
        }
    }

    public Folder getFolderByNameFromRootFolders(int teacherId, String folderName) {
        return folderRepository.findByTeacherIdAndFolderNameAndParentFolderId(teacherId, folderName, 0);
    }

    public List<Folder> getAllSubfolders(int teacherId, int parentFolderId) {
        return folderRepository.getAllSubfolders(teacherId, parentFolderId);
    }
    
    public List<Folder> getAllSubfolders(Long teacherId, Long parentFolderId) {
        return folderRepository.getAllSubfolders(teacherId, parentFolderId);
    }
    
    public Folder getRootTestsFolder(Long teacherId) {
        return folderRepository.getRootTestsFolder(teacherId);
    }

    public boolean checkFolderExisting(int folderId) {
        Folder folder = folderRepository.getOne(folderId);
        if (folder != null) {
            System.out.println(folder.getFolderName());
            return true;
        } else {
            return false;
        }
    }
    
    public boolean checkFolderExisting(Long folderId) {
        Folder folder = folderRepository.getFolderById(folderId);
        if (folder != null) {
            System.out.println(folder.getFolderName());
            return true;
        } else {
            return false;
        }
    }

    public Folder addFolder(Folder folder) {
        if (folder.getFolderName() != null && folder.getParentFolderId() != 0) {
            return folderRepository.save(folder);
        }
        return null;
    }

    public void deleteFolder(int folderId) throws Exception {
        try {
            folderRepository.deleteById(folderId);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public Map<String, Object> getFoldersForNav(int teacherId) {
        Map<String, Object> responseList = new LinkedHashMap<>();
        List<Folder> folders = folderRepository.findByTeacherId(teacherId);
        Folder publicGroup = null;
        for(int i=0;i<folders.size();i++){
            Folder folder = folders.get(i);
            responseList.put("folder" + folder.getFolderId(), folder);
            if(folder.getFolderName().equals("Nhóm")&&folder.getParentFolderId()==0){
                publicGroup = new Folder(1, 1, "Nhóm công cộng", 4, folder.getFolderId(), 3);
                responseList.put("folder" + publicGroup.getFolderId(), publicGroup);
            }
        }
//        Folder groups = getFolderByNameFromRootFolders(teacherId, "Nhóm");
//        Folder publicGroup = getFolderByNameFromRootFolders(1, "Nhóm công cộng");
//        publicGroup.setParentFolderId(groups.getFolderId());
        return responseList;
    }

//    public Map<String, Object> testFolder(int teacherId) {
//        Map<String, Object> responseList = new LinkedHashMap<>();
//        List<Object> folders = new ArrayList<>();
//        testFolderRecur(responseList, folders, teacherId, 0);
//        Folder groups = getFolderByNameFromRootFolders(teacherId, "Nhóm");
//        Folder publicGroup = getFolderByNameFromRootFolders(1, "Nhóm công cộng");
//        publicGroup.setParentFolderId(groups.getFolderId());
//        responseList.put("folder" + publicGroup.getFolderId(), publicGroup);
//        return responseList;
//    }
//    
//    private void testFolderRecur(Map<String, Object> responseList, List<Object> folder, int teacherId, int parentFolderId) {
//        try {
//            List<Folder> subfolders = folderRepository.getAllSubfolders(teacherId, parentFolderId);
//            for (int i = 0; i < subfolders.size(); i++) {
//                responseList.put("folder" + subfolders.get(i).getFolderId(), subfolders.get(i));
//                if (folderRepository.getAllSubfolders(teacherId, subfolders.get(i).getFolderId()).size() > 0) {
//                    HashMap<String, List> subfolder = new HashMap<>();
//                    List<Object> subfoldersOfThis = new ArrayList<>();
//                    loadFolders(responseList, subfoldersOfThis, teacherId, subfolders.get(i).getFolderId());
//
//                    subfolder.put(subfolders.get(i).getFolderId() + "", subfoldersOfThis);
//                    folder.add(subfolder);
//                } else {
//                    folder.add(subfolders.get(i).getFolderId() + "");
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(FolderService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
