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
public class Service {
    private String SNCode;
    private String status;

    public String getSNCode() {
        return SNCode;
    }

    public void setSNCode(String SNCode) {
        this.SNCode = SNCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Service(String SNCode, String status) {
        this.SNCode = SNCode;
        this.status = status;
    }
    
}
