/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.sr.model;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author V19MFoda
 */
@Table(name = "USER")
@Entity
public class User {
    
    
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "ID")
//    private int userId;
    @Id
    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "PASSWORD")
    private String passWord;
    @Column(name = "TEAM")
    private String team;

 

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
    
}
