/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.Folder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author ADMIN
 */
public interface FolderRepository extends JpaRepository<Folder, Integer> {

    @Query(value = "select * from Folder where teacherId = ?1 and parentFolderId = ?2", nativeQuery = true)
    List<Folder> getAllSubfolders(int teacherId, int parentFolderId);
    
    Folder getOne(int folderId);
    
    List<Folder> findByTeacherId(int teacherId);
    
    Folder findByTeacherIdAndFolderName(int teacherId, String folderName);
    
    Folder findByTeacherIdAndFolderNameAndParentFolderId(int teacherId, String folderName, int parentFolderId);
}
