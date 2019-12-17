package com.hgedu_server.repositories;

import com.hgedu_server.models.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author admin
 */
public interface GradeRepository extends JpaRepository<Grade, Long>{
    
    
    Iterable<Grade> findByClassStudentIdOrderByPowerIdAsc(long id);
    
}
