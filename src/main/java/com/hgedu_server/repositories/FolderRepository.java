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
public interface FolderRepository extends JpaRepository<Folder, Long> {

    @Query(value = "select * from Folder where teacherId = ?1 and parentFolderId = ?2", nativeQuery = true)
    List<Folder> getAllSubfolders(Long teacherId, Long parentFolderId);
    
    @Query(value = "SELECT a.* FROM Folder a WHERE a.parentFolderId = ?1 AND a.folderId NOT IN (SELECT t.folderId FROM Test t, Folder f WHERE t.teacherId = ?2 AND t.folderId = f.folderId AND f.parentFolderId = ?1 AND isPublic = 0)", nativeQuery = true)
    List<Folder> getAllSubfoldersForExam(Long parentFolderId, Long teacherId);
    
    Folder getOne(Long folderId);
    
    @Query(value = "select * from Folder where folderId = ?1", nativeQuery = true)
    Folder getFolderById(Long folderId);
    
//    @Query(value = "select * from Folder where folderId = ?1", nativeQuery = true)
//    Folder getFolderById(int folderId);
    
    @Query(value = "select * from Folder where teacherId = ?1 and folderName = \"Thư viện đề thi\"", nativeQuery = true)
    Folder getRootTestsFolder(Long teacherId);

    List<Folder> findByTeacherId(Long teacherId);
    
    List<Folder> findByFolderId(Long folderId);

    List<Folder> findByTeacherIdAndFolderName(Long teacherId, String folderName);

    Folder findByTeacherIdAndFolderNameAndParentFolderId(Long teacherId, String folderName, Long parentFolderId);

    @Query(value = "select distinct(folderName) from Folder where teacherId = ?1 and folderTypeId=2", nativeQuery = true)
    List<String> getAllKnowledgeGroups(Long teacherId);

    @Query(value = "select distinct(folderId) from Folder where teacherId = ?1 and folderTypeId=2", nativeQuery = true)
    List<Integer> getAllKnowledgeGroupsId(Long teacherId);

}
