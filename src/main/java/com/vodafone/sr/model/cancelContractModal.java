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
public class cancelContractModal {
    private String coId;
    private String response;

    public String getCoId() {
        return coId;
    }

    public void setCoId(String coId) {
        this.coId = coId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public cancelContractModal(String coId, String response) {
        this.coId = coId;
        this.response = response;
    }
    
    
}
