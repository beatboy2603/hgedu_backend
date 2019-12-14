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
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 *
 * @author ADMIN
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from User where email = ?1", nativeQuery = true)
    List<User> getUserByEmail(String email);

    @Query(value = "SELECT * FROM User WHERE email COLLATE utf8mb4_general_ci IN (SELECT parentEmail FROM LinkRequest WHERE studentEmail = 'ducbhse04740@fpt.edu.vn')", nativeQuery = true)
    List<User> getByEmail();
    
    @Query(value = "select * from User where email COLLATE utf8mb4_general_ci in (select parentEmail from LinkRequest where studentEmail = ?1)", nativeQuery = true)
    List<User> getUserByRequestEmail(String email);
    @Query(value = "select * from User where email COLLATE utf8mb4_general_ci in (select parentEmail from LinkRequest where studentEmail = ?1)", nativeQuery = true)
    List<User> getStudentByRequestEmail(String email);

    List<User> findByUserSub(String userSub);
//    @Override
//    public Iterable<User> findAll();
    @Query(value = "SELECT * FROM webgiaoduc.User where roleId != 1 order by roleId", nativeQuery = true)
    public Iterable<User> listUser();
    
    @Query(value = "select count(userId) from User where roleId != 1", nativeQuery = true)
    public int countUsers();
    
    List<User> findByEmail(String email);
}
