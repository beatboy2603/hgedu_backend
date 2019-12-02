/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.LinkRequest;
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
public interface LinkRequestRepository extends JpaRepository<LinkRequest, Integer> {

    @Modifying
    @Query(value = "insert into LinkRequest (parentEmail, studentEmail) values (:parentEmail, :studentEmail)", nativeQuery = true)
    @Transactional
    void addRequest(@Param("parentEmail") String parentMail, @Param("studentEmail") String studentMail);

    @Query(value = "select * from LinkRequest where studentEmail = ?1", nativeQuery = true)
    Optional<LinkRequest> getRequest(String studentEmail);
}
