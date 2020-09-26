/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viesapi;

/**
 *
 * @author Osito
 */
public class ViesVatServiceException  extends Exception {
    private String errorKey;

    public ViesVatServiceException(String key, String message) {
        super(message);
        this.errorKey = key;
    }

    public String getErrorKey() {
        return errorKey;
    }
    
}
