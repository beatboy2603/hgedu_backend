/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ADMIN
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId")
    private int userId;
    @Column(name = "userSub")
    private String userSub;
    @Column(name = "email")
    private String email;
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "gender")
    private boolean gender;
    @Column(name = "roleId")
    private int roleId;
    @Column(name = "dob")
    private String dob;
    @Column(name = "school")
    private String school;

    public User() {
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public User(int userId, String userSub, String email, String fullName, String phoneNumber, boolean gender, int roleId, String dob, String school) {
        this.userId = userId;
        this.userSub = userSub;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.roleId = roleId;
        this.dob = dob;
        this.school = school;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserSub() {
        return userSub;
    }

    public void setUserSub(String userSub) {
        this.userSub = userSub;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

}
