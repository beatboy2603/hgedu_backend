/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.User;
import com.hgedu_server.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class UserService {
    
    @Autowired
    private UserRepository usersRepository;
    
    public UserService() {
    }
    
    
    public User saveUser(User user){
        return usersRepository.save(user);
    }
    
    public User addMod(User user){
        return usersRepository.save(user);
    }
    
    public User banUser(User user){
        return usersRepository.save(user);
    }
    
    public User unBan(User user){
        return usersRepository.save(user);
    }
    
    public User banUserForever(User user){
        return usersRepository.save(user);
    }
    
    public Iterable<User> findAll(){
        return usersRepository.listUser();
    }
    
    public int countUsers(){
        return usersRepository.countUsers();
    }

}
