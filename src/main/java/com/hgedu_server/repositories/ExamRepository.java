/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.Exam;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */
@Repository
public interface ExamRepository extends JpaRepository<Exam, Long>{
    @Query(value = "SELECT * FROM Exam WHERE Exam.startEntryTime > CONVERT_TZ(NOW(),@@global.time_zone,'+07:00') AND Exam.teacherId = ?1 AND DATE(Exam.dateUpdated) = ?2 ORDER BY DATE(Exam.dateUpdated) ASC", nativeQuery = true)
    List<Exam> getExamSchedule(Long teacherId, String date);
    
    @Query(value = "SELECT * FROM Exam WHERE Exam.startEntryTime < CONVERT_TZ(NOW(),@@global.time_zone,'+07:00') AND Exam.startEntryTime != '0000-00-00 00:00:00' AND Exam.teacherId = ?1 AND DATE(Exam.dateUpdated) = ?2 ORDER BY DATE(Exam.dateUpdated) ASC", nativeQuery = true)
    List<Exam> getExamHistory(Long teacherId, String date);
    
    @Query(value = "SELECT IF(a.Current = b.Total, 'Xong', CONCAT(a.Current,'/',b.Total)) 'Progress' FROM (SELECT COUNT(er.classStudentId) 'Current' FROM ExamResult er, ExamTest et WHERE er.examTestId = et.id AND et.examId = ?1) a, \n" +
                    "(SELECT COUNT(cs.id) 'Total' FROM ClassStudent cs WHERE cs.classId IN ( SELECT c.id FROM ClassExam ce, Class c WHERE ce.classId = c.id AND ce.examId = ?1)) b ", nativeQuery = true)
    String getExamProgress(Long examId);
    
    @Query(value = "SELECT DATE(Exam.dateUpdated) FROM Exam WHERE Exam.startEntryTime <= CONVERT_TZ(NOW(),@@global.time_zone,'+07:00') AND Exam.startEntryTime != '0000-00-00 00:00:00' AND Exam.teacherId = ?1 GROUP BY DATE(Exam.dateUpdated) ORDER BY DATE(Exam.dateUpdated) ASC", nativeQuery = true)
    List<String> getExamHistoryDates(long teacherId);
    
    @Query(value = "SELECT DATE(Exam.dateUpdated) FROM Exam WHERE Exam.startEntryTime > CONVERT_TZ(NOW(),@@global.time_zone,'+07:00') AND Exam.teacherId = ?1 GROUP BY DATE(Exam.dateUpdated) ORDER BY DATE(Exam.dateUpdated) ASC", nativeQuery = true)
    List<String> getExamScheduleDates(long teacherId);
    
    @Query(value = "SELECT Exam.* FROM Exam, ClassExam WHERE Exam.id = ClassExam.examId and ClassExam.classId = ?1 ORDER BY DATE(Exam.dateUpdated) ASC", nativeQuery = true)
    List<Exam> getExamsForClass(Long classId);
    
    @Query(value = "SELECT Exam.* FROM Exam WHERE Exam.id = ?1", nativeQuery = true)
    Exam getExamById(Long examId);
    
    @Query(value = "SELECT e.id, e.title, e.startEntryTime, e.code, ce.id as classExamId, ce.classId FROM Exam e JOIN ClassExam ce ON e.id = ce.examId ORDER BY ID ASC", nativeQuery = true)
    Iterable<Object[]> findExamsByClassId();
}
