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
import java.util.Optional;

/**
 *
 * @author ADMIN
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from User where email = ?1", nativeQuery = true)
    List<User> getUserByEmail(String email);

    @Query(value = "select * from User where email COLLATE utf8mb4_general_ci in (select parentEmail from LinkRequest where studentEmail = ?1)", nativeQuery = true)
    List<User> getUserByRequestEmail(String email);

    @Query(value = "select * from User where email COLLATE utf8mb4_general_ci in (select parentEmail from LinkRequest where studentEmail = ?1)", nativeQuery = true)
    List<User> getStudentByRequestEmail(String email);

    // get requested student though email [role: teacher]
    @Query(value = "select * from User where userid in (select studentId from StudentRequest where teacherEmail = ?1)", nativeQuery = true)
    List<User> getEnrolledStudentByRequestEmail(String email);
    
    
    // parent info show in student detail [role: teacher, page: studentCustomizedTable]
    @Query(value = "SELECT * FROM `User` WHERE userId in (SELECT parentId FROM `ParentStudent` WHERE studentId = ?1)",nativeQuery = true)
    List<User> getParentInfoByStudentId(int studentId);
    
//    ---- student-teacher ----
    @Query(value = "SELECT * FROM USER WHERE userid IN (SELECT teacherId FROM StudentTeacher WHERE studentId = ?1)", nativeQuery = true)
    List<User> getTeacherList(int studentId);
    
    @Query(value = "SELECT * FROM USER WHERE userid IN (SELECT studentId FROM StudentTeacher WHERE teacherId = ?1)", nativeQuery = true)
    List<User> getStudentList(int studentId);
    
    @Query(value = " select userId from User where email = ?1", nativeQuery = true)
    int getUserIdByEmail(String email);
     
    @Query(value = "SELECT * FROM `User` WHERE email IN (SELECT teacherEmail FROM TeacherRequest WHERE studentId = ?1)", nativeQuery = true)
    List<User> getRequestTeacher(int studentId);

    List<User> findByUserSub(String userSub);
}
