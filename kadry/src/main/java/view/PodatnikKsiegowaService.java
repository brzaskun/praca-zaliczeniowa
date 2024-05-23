/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import dao.FirmaKadryFacade;
import entity.FirmaKadry;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.WebServiceRef;
import webserviceksiegowa.PodatnikKsiegowa;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class PodatnikKsiegowaService implements Serializable {
    private static final long serialVersionUID = 1L;
    @WebServiceRef(name = "Podatnikkadryinfo", wsdlLocation = "http://localhost:8080/Podatnikkadryinfo/Podatnikkadryinfo?wsdl")
    private webserviceksiegowa.Podatnikkadryinfo_Service podatnikkadryinfo_Service;
    @Inject
    private FirmaKadryFacade firmaKadryFacade;
    
//   @Schedule(hour = "12,22", minute = "30", persistent = false)
   @Schedule(hour = "10, 23", minute = "30", persistent = false)
    public void init() {
        List<FirmaKadry> listafirm = firmaKadryFacade.findByBezglobal();
        if (listafirm!=null&&listafirm.isEmpty()==false) {
            for (FirmaKadry firma : listafirm) {
                PodatnikKsiegowa podatnikksiegowapobierz = podatnikkadryinfo_Service.getPodatnikkadryinfoPort().podatnikksiegowapobierz(firma.getNip());
                firma.setKsiegowa(podatnikksiegowapobierz.getKsiegowa());
                System.out.println(podatnikksiegowapobierz.toString());
            }
            firmaKadryFacade.editList(listafirm);
        }
    }
    
}
