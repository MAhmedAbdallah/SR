/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.sr.dao;

import com.vodafone.sr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author V19MFoda
 */
public interface UserRepository extends JpaRepository<User, String> {

}
