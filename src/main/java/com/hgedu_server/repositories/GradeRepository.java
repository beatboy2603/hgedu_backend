package com.hgedu_server.repositories;

import com.hgedu_server.models.Grade;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author admin
 */
public interface GradeRepository extends JpaRepository<Grade, Long>{
    
    
    Iterable<Grade> findByClassStudentIdOrderByPowerIdAsc(Long id);
    
    @Query(value = "SELECT g.* FROM Grade g, Setting s WHERE g.classStudentId = ?1 AND g.powerId = s.settingId AND s.value = ?2", nativeQuery = true)
    Grade getStudentGradeByClassStudentIdAndPowerLevel(Long classStudentId, Long powerLevel);

    List<Grade> findByClassStudentId(Long id);
}
