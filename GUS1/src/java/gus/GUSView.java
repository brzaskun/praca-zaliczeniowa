/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gus;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class GUSView implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public void poblicz() {
        NewWebServiceFromWSDL poc = new NewWebServiceFromWSDL();
        poc.pobierz("8511005008");
        System.out.println("");
    }
    
}
