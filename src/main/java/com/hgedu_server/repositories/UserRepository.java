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
    
    @Query(value = "SELECT * FROM ClassStudent cs INNER JOIN User u ON cs.studentId = u.userId WHERE cs.classId = ?1", nativeQuery = true)
    Iterable<User> findUserByClassId(long classId);
    
    @Query(value = "SELECT * FROM ParentStudent ps INNER JOIN User u ON ps.parentId = u.userId WHERE ps.studentId = ?1", nativeQuery = true)
    User findParentInformationByStudentId(int studentId);
    
    @Query(value = "SELECT * FROM ParentStudent ps INNER JOIN User u ON ps.studentId = u.userId WHERE ps.parentId = 150", nativeQuery = true)
    Iterable<User> findStudentByParentId(long parentId);
    
    @Query(value = "SELECT * FROM User u INNER JOIN StudentTeacher st ON u.userId =  st.studentId WHERE st.teacherId = ?1 and st.isConnected = ?2", nativeQuery = true)
    Iterable<User> findConnectedStudentByTeacherId(int teacherId, boolean isConnected);
    
    @Query(value = "SELECT * FROM User u INNER JOIN StudentTeacher st ON u.userId =  st.teacherId WHERE st.studentId = ?1 and st.isConnected = ?2", nativeQuery = true)
    Iterable<User> findConnectedTeacherByStudentId(int student, boolean isConnected);
    
    @Query(value = "SELECT u.fullName, u.gender, u.dob, a.teacherId,u.phoneNumber, u.school, u.email, a.classId, a.name, a.classStudentId FROM (SELECT cs.id as classStudentId,  cs.ClassId, c.teacherId, c.name FROM ClassStudent cs JOIN Class c ON cs.classId = c.id WHERE studentId = ?1) as a JOIN User u ON a.teacherId = u.userId", nativeQuery = true)
    Iterable<Object[]> parentFindTeacherInformationByStudentId(long studentId);
    
    User findByUserId(int userId);
}
