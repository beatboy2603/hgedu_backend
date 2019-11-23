/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.LinkRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Admin
 */
public interface LinkRequestRepository extends JpaRepository<LinkRequest, Integer> {
    @Query(value = "Insert into ParentStudentRequest values ?1, ?2", nativeQuery = true)
    LinkRequest addRequest(String requestMail, String receivedMail);

}
