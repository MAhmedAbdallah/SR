/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.sr.controller;

import static com.vodafone.sr.controller.MainController.logger;
import com.vodafone.sr.dao.UserRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author V19MFoda
 */
@Controller

public class SeibelController {

    static Logger logger = Logger.getLogger(SeibelController.class.getName());
    @Autowired
    private UserRepository repo;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "restart", method = RequestMethod.POST)

    public String restartSiebelMachines() {
        StringBuilder bashResponse = new StringBuilder("Welcome to fatma");
        if (request.getSession().getAttribute("userName") == null) {
            request.setAttribute("message", "Please Login ");
            return "index";
        } else {
            try {
                bashResponse = runBash();
                logger.info("Restart Siebel Machines By" + request.getSession().getAttribute("userName"));

            } catch (Exception e) {
                e.printStackTrace();
                bashResponse.append(e.getMessage());
                logger.error("Error Happend " + e.getMessage());

            }
            request.setAttribute("message", bashResponse);
            return "forward:/siebel";
        }
    }

    private StringBuilder runBash() throws IOException, InterruptedException {
        Process p;

        String[] cmd = {"sh", "/home/ITAutomation/fatma/restart.sh"};
        p = Runtime.getRuntime().exec(cmd);
        p.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                p.getInputStream()));
        String response;
        StringBuilder line = new StringBuilder();
        while ((response = reader.readLine()) != null) {
            System.out.println(response);
            line.append(response);
        }
        return line;
    }

}
