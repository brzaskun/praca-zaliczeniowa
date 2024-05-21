/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.xml.ws.WebServiceRef;
import webserviceksiegowa.PodatnikKsiegowa;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PodatnikKsiegowaService implements Serializable {
    private static final long serialVersionUID = 1L;
    @WebServiceRef(name = "Podatnikkadryinfo", wsdlLocation = "http://localhost:8080/Podatnikkadryinfo/Podatnikkadryinfo?wsdl")
    private webserviceksiegowa.Podatnikkadryinfo_Service podatnikkadryinfo_Service;
    
    public void init() {
        PodatnikKsiegowa podatnikksiegowapobierz = podatnikkadryinfo_Service.getPodatnikkadryinfoPort().podatnikksiegowapobierz("8511005008");
        System.out.println(podatnikksiegowapobierz.toString());
    }
    
}
