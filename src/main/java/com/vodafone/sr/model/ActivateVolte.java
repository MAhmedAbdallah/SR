/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.sr.model;

/**
 *
 * @author V19MFoda
 */
public class ActivateVolte {
    
    private String msisdn;
    private String message;

    public ActivateVolte(String msisdn, String message) {
        this.msisdn = msisdn;
        this.message = message;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
