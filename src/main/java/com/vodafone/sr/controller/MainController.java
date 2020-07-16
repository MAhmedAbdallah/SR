/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.sr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author V19MFoda
 */
@Controller
public class MainController {
    
    
    @RequestMapping("home")
    public String getHome(){
        return "home";
    }
}
