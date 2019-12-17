/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.repositories;

import com.hgedu_server.models.Setting;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */
@Repository
public interface SettingRepository extends JpaRepository<Setting, Long>{
    @Query (value = "SELECT * FROM Setting WHERE Setting.name LIKE 'Hệ số%'", nativeQuery = true)
    List<Setting> getPowers();
}
