/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.StudentTeacher;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Admin
 */
public interface StudentTeacherRepository extends JpaRepository<StudentTeacher, Long> {

    @Query(value = "select count(*) from StudentTeacher where studentId = ?1 and teacherId = ?2", nativeQuery = true)
    Integer findByStudentIdAndTeacherId(Long studentId, Long teacherId);

    @Query(value = "SELECT u.userId, st.displayedname , u.`email`, u.`phoneNumber`, u.`gender`, u.`dob`\n"
            + "	 FROM `User` u\n"
            + "	 INNER JOIN `StudentTeacher` st ON u.`userId` = st.`studentId` \n"
            + "	  WHERE st.teacherId = ?1", nativeQuery = true)
    List<Object[]> getStudentData(Long teacherId);

    @Query(value = "SELECT  u.userId, u.fullName , u.`email`, u.`phoneNumber`, u.`gender`, u.`dob`\n"
            + "	 FROM `User` u\n"
            + "	 INNER JOIN `StudentTeacher` st ON u.`userId` = st.`teacherId` \n"
            + "	  WHERE st.studentId = ?1", nativeQuery = true)
    List<Object[]> getTeacherData(Long studentId);

    Iterable<StudentTeacher> findByIsConnectedAndTeacherId(boolean isConnected, Long teacherId);

    Iterable<StudentTeacher> findByIsConnectedAndStudentId(boolean isConnected, Long studentId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM StudentTeacher WHERE studentId = ?1", nativeQuery = true)
    void deleteByStudentId(Long studentId);
}