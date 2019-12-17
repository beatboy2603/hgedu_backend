/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.Setting;
import com.hgedu_server.repositories.SettingRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class SettingService {
    @Autowired
    private SettingRepository settingRepo;
    
    public List<Setting> getPowers() {
        return settingRepo.getPowers();
    }
}
