
package com.hgedu_server.repositories;

import com.hgedu_server.models.ClassStudent;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;


import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author admin
 */
public interface ClassStudentRepository extends JpaRepository<ClassStudent, Long> {

    Iterable<ClassStudent> findByClassId(Long classId);

    Iterable<ClassStudent> findByStudentId(Long studentId);

//    @Transactional
//    @Modifying
//    @Query(value = "DELETE FROM ClassStudent WHERE studentId = ?1", nativeQuery = true)
//    void deleteByClassStudentId(long studentId);

    @Query(value = "SELECT * FROM ClassStudent cs WHERE cs.classId = ?1 AND cs.studentId =?2", nativeQuery = true)
    ClassStudent getByClassIdAndStudentId(Long classId, Long studentId);

    ClassStudent findByClassIdAndStudentId(Long classId, Long studentId);
}
