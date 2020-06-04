/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.LinkRequest;
import com.hgedu_server.models.User;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Admin
 */
public interface LinkRequestRepository extends JpaRepository<LinkRequest, Long> {

    @Modifying
    @Query(value = "insert into LinkRequest (parentEmail, studentEmail) values (:parentEmail, :studentEmail)", nativeQuery = true)
    @Transactional
    void addRequest(@Param("parentEmail") String parentMail, @Param("studentEmail") String studentMail);

    @Query(value = "select * from LinkRequest where studentEmail = ?1", nativeQuery = true)
    List<LinkRequest> getRequest(String studentEmail);

    @Modifying
    @Query(value = "insert into ParentStudent (parentId, studentId) value (:parentId, :studentId)", nativeQuery = true)
    @Transactional
    void addToLink(@Param("parentId") Long parentId, @Param("studentId") Long studentId);

    @Query(value = "SELECT COUNT(*) FROM ParentStudent WHERE parentId = ?1 AND studentId = ?2", nativeQuery = true)
    int checkDuplicateLinkedUser(Long parentId, Long studentId);
    
    
    @Modifying
    @Query(value = "delete from LinkRequest where parentEmail =:parentEmail and studentEmail = :studentEmail", nativeQuery = true)
    @Transactional
    void deleteRequest(@Param("parentEmail") String parentEmail, @Param("studentEmail") String studentEmail);

    @Query(value = "SELECT COUNT(*) FROM LinkRequest WHERE parentEmail = ?1 AND studentEmail = ?2", nativeQuery = true)
    int checkDuplicateUser(String parentEmail, String studentEmail);

}
