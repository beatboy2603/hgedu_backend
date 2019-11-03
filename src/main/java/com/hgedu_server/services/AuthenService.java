/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.jwt.JwtController;
import com.hgedu_server.models.Folder;
import com.hgedu_server.models.User;
import com.hgedu_server.repositories.FolderRepository;
import com.hgedu_server.repositories.UserRepository;
import com.hgedu_server.utils.Util;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class AuthenService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FolderService folderService;
    @Autowired
    private FolderRepository folderRepository;
    
    public Map<String, Object> authen(String token){
        JSONObject tokenJson = Util.decodeToken(token);
        String sub = tokenJson.getString("sub");
        List<User> users = findByUserSub(sub);
        Map<String, Object> responseList = new LinkedHashMap<>();
        if(users.size()==0){
            responseList.put("message", (Object) "SignUp");
            signup(tokenJson);
            return responseList;
        }else{
            User user = users.get(0);
            responseList.put("user", user);
            String jwt = JwtController.getInstance().createToken(user.getUserSub(), user.getUserId(), user.getRoleId());
            responseList.put("jwt", jwt);
            return responseList;
        }
    }
    
    public void signup(JSONObject tokenJson){
        User user = new User();
        user.setEmail(tokenJson.getString("email"));
        user.setFullName(tokenJson.getString("name"));
        user.setGender(true);
        user.setPhoneNumber("0000000000");
        user.setUserSub(tokenJson.getString("sub"));
        user.setRoleId(3);
        addUser(user);
        User newUser = findByUserSub(tokenJson.getString("sub")).get(0);
//        System.out.println("user:" + newUser.getUserId());
        Folder[] folders = new Folder[5];
        String[] folderNames={"Thư viện câu hỏi", "Thư viện đề thi","Nhóm", "Nhóm công cộng", "Quản lí thông báo"};
        for(int i=0;i<folders.length; i++){
            System.out.println("iter"+ i);
            folders[i] = new Folder();
//            folders[i].setFolderId(i);
            folders[i].setFolderName(folderNames[i]);
            folders[i].setFolderTypeId(1);
            if(i==3){
                List<Folder> tempFolders = folderService.getAllSubfolders(newUser.getUserId(), 0);
                folders[i].setParentFolderId(tempFolders.get(2).getFolderId());
            }else{
                folders[i].setParentFolderId(0);
            }
            folders[i].setTeacherId(newUser.getUserId());
            folderRepository.save(folders[i]);
        }
//        folderService.addFolder();
    }
    
    public User addUser(User user) {
        return userRepository.save(user);
    }
    
    public List<User> findByUserSub(String sub){
        return userRepository.findByUserSub(sub);
    }
}
