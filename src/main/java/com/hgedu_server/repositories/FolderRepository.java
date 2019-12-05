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
    
    @Query(value = "select * from Folder where teacherId = ?1 and parentFolderId = ?2", nativeQuery = true)
    List<Folder> getAllSubfolders(Long teacherId, Long parentFolderId);
    
    Folder getOne(int folderId);
    
    @Query(value = "select * from Folder where folderId = ?1", nativeQuery = true)
    Folder getFolderById(Long folderId);
    
    @Query(value = "select * from Folder where teacherId = ?1 and folderName = \"Thư viện đề thi\"", nativeQuery = true)
    Folder getRootTestsFolder(Long teacherId);
}
