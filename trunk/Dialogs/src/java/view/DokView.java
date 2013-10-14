/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.ViewScoped;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DokView implements Serializable{
    

    private static final String pierwszalinia = "Gupana kopana";
    private static final String drugalinia = "Fupana Mumana";

    public String getPierwszalinia() {
        return pierwszalinia;
    }

    public String getDrugalinia() {
        return drugalinia;
    }

  
    
}
