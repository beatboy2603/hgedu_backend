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

    @Query(value = "select * from User where email COLLATE utf8mb4_general_ci in (select parentEmail from LinkRequest where studentEmail = ?1)", nativeQuery = true)
    List<User> getUserByRequestEmail(String email);

    @Query(value = "select * from User where email COLLATE utf8mb4_general_ci in (select parentEmail from LinkRequest where studentEmail = ?1)", nativeQuery = true)
    List<User> getStudentByRequestEmail(String email);

    // get requested student though email [role: teacher]
    @Query(value = "select * from `User` where userid in (select studentId from StudentRequest where teacherEmail = ?1)", nativeQuery = true)
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
    
    @Query(value = "SELECT * FROM ClassStudent cs INNER JOIN User u ON cs.studentId = u.userId WHERE cs.classId = ?1", nativeQuery = true)
    Iterable<User> findUserByClassId(long classId);
    
    @Query(value = "SELECT * FROM ParentStudent ps INNER JOIN User u ON ps.parentId = u.userId WHERE ps.studentId = ?1", nativeQuery = true)
    User findParentInformationByStudentId(int studentId);
    
    @Query(value = "SELECT * FROM ParentStudent ps INNER JOIN User u ON ps.studentId = u.userId WHERE ps.parentId = ?1", nativeQuery = true)
    Iterable<User> findStudentByParentId(long parentId);
    
    @Query(value = "SELECT * FROM User u INNER JOIN StudentTeacher st ON u.userId =  st.studentId WHERE st.teacherId = ?1 and st.isConnected = ?2", nativeQuery = true)
    Iterable<User> findConnectedStudentByTeacherId(int teacherId, boolean isConnected);
    
    @Query(value = "SELECT * FROM User u INNER JOIN StudentTeacher st ON u.userId =  st.teacherId WHERE st.studentId = ?1 and st.isConnected = ?2", nativeQuery = true)
    Iterable<User> findConnectedTeacherByStudentId(int student, boolean isConnected);
    
    @Query(value = "SELECT u.fullName, u.gender, u.dob, a.teacherId,u.phoneNumber, u.school, u.email, a.classId, a.name, a.classStudentId FROM (SELECT cs.id as classStudentId,  cs.ClassId, c.teacherId, c.name FROM ClassStudent cs JOIN Class c ON cs.classId = c.id WHERE studentId = ?1) as a JOIN User u ON a.teacherId = u.userId", nativeQuery = true)
    Iterable<Object[]> parentFindTeacherInformationByStudentId(long studentId);
    
    User findByUserId(int userId);
//    @Override
//    public Iterable<User> findAll();
    @Query(value = "SELECT * FROM webgiaoduc.User where roleId != 1 order by roleId", nativeQuery = true)
    public Iterable<User> listUser();
    
    @Query(value = "select count(userId) from User where roleId != 1", nativeQuery = true)
    public int countUsers();
    
    List<User> findByEmail(String email);
}
