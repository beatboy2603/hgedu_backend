/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Admin
 */
//    @Query(value = "SELECT * FROM User WHERE userId = ?1", nativeQuery = true)
import java.util.List;


/**
 *
 * @author ADMIN
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByUserSub(String userSub);
}
