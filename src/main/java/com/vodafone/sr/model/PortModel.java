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
public class PortModel {

    private String msi;
    private String destinationOperator ;
    private String serviceResponse;

    public PortModel(String msi, String destinationOperator, String serviceResponse) {
        this.msi = msi;
        this.destinationOperator = destinationOperator;
        this.serviceResponse = serviceResponse;
    }


    

    public String getMsi() {
        return msi;
    }

    public void setMsi(String msi) {
        this.msi = msi;
    }

    public String getDestinationOperator() {
        return destinationOperator;
    }

    public void setDestinationOperator(String destinationOperator) {
        this.destinationOperator = destinationOperator;
    }

    public String getServiceResponse() {
        return serviceResponse;
    }

    public void setServiceResponse(String serviceResponse) {
        this.serviceResponse = serviceResponse;
    }
    
}
