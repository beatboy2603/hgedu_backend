/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.LinkRequest;
import com.hgedu_server.models.User;
import com.hgedu_server.repositories.LinkRequestRepository;
import com.hgedu_server.repositories.ParentStudentRepository;
import com.hgedu_server.repositories.UserRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class LinkRequestService {

    @Autowired
    private LinkRequestRepository linkRequestRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ParentStudentRepository parentStudent;

//    public boolean sendRequest(String parentMail, String studentMail) {
//        if (userRepository.getUserByEmail(studentMail).isEmpty()) {
//            System.out.println("K tim duoc hoc sinh");
//            return false;
//        } else {
//            System.out.println("tim duoc hoc sinh");
////            if user duplicate
//            if (linkRequestRepo.checkDuplicateUser(parentMail, studentMail) >= 1) {
//                System.out.println("dupe");
//                return false;
//            }
//            linkRequestRepo.addRequest(parentMail, studentMail);
//            return true;
//        }
//    }
    public Map<String, Object> checkSendRequest(String parentMail, String studentMail) {
        Map<String, Object> responseList = new LinkedHashMap<>();
        if (parentMail.equals(studentMail)) {
            responseList.put("mess", "Bạn không thể gửi cho chính bạn");
            return responseList;
        }
        if (userRepository.getUserByEmail(studentMail).isEmpty()) {
            responseList.put("mess", "Không tìm thấy người dùng");
        } else {
            Long parentId = userRepository.getUserIdByEmail(parentMail);
            Long studentId = userRepository.getUserIdByEmail(studentMail);
            if(parentStudent.findByStudentIdAndParentId(parentId, studentId).size()>0){
                responseList.put("mess", "Người này đang là phụ huynh của bạn");
            } else if (linkRequestRepo.checkDuplicateLinkedUser(parentId, studentId) >= 1) {
                responseList.put("mess", "Bạn có liên kết tới người dùng này rồi");
            } else {
                if (linkRequestRepo.checkDuplicateUser(parentMail, studentMail) >= 1) {
                    responseList.put("mess", "Bạn đã gửi liên kết tới người dùng này rồi");
                } else {
                    linkRequestRepo.addRequest(parentMail, studentMail);
                    responseList.put("mess", "Gửi liên kết thành công!");
                }
            }
        }
        return responseList;
    }

    public List<LinkRequest> getRequest(Long id) {
        User user = userRepository.getOne(id);
        return linkRequestRepo.getRequest(user.getEmail());
    }

    public List<User> getUserByRequest(String studentMail) {
        return userRepository.getUserByRequestEmail(studentMail);
//        return userRepository.getByEmail();
    }

    public List<User> getUserByEmail(String email) {
        List<User> getUser = userRepository.getUserByEmail(email);
        return getUser;
    }

    public void acceptRequest(String parentEmail, String studentEmail) {
        Long parentId = userRepository.getUserIdByEmail(parentEmail);
        Long studentId = userRepository.getUserIdByEmail(studentEmail);
        System.out.println("Accept Request - Student mail: " + studentEmail);
        linkRequestRepo.addToLink(parentId, studentId);
        linkRequestRepo.deleteRequest(parentEmail, studentEmail);
    }

    public void refuseRequest(String parentEmail, String studentEmail) {
        linkRequestRepo.deleteRequest(parentEmail, studentEmail);
    }
}
