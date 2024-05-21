/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/EjbWebService.java to edit this template
 */
package webservice;

import dao.PodatnikDAO;
import embeddable.PodatnikKsiegowa;
import entity.Podatnik;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Osito
 */
@WebService(serviceName = "Podatnikkadryinfo")
@Stateless()
public class Podatnikkadryinfo {

    @Inject
    private PodatnikDAO podatnikDAO;
    /**
     * This is a sample web service operation
     * @param nip
     * @return 
     */
    @WebMethod(operationName = "Podatnikksiegowapobierz")
    public PodatnikKsiegowa hello(@WebParam(name = "nip") String nip) {
        System.out.println("webserwis start");
        PodatnikKsiegowa zwrot = new PodatnikKsiegowa("brak podatnika");
        Podatnik findPodatnikByNIP = podatnikDAO.findPodatnikByNIP(nip);
        if (findPodatnikByNIP!=null) {
            zwrot.setNip(nip);
            zwrot.setNazwa(findPodatnikByNIP.getPrintnazwa());
            zwrot.setKsiegowa(findPodatnikByNIP.getKsiegowa().getEmail());
        }
        return zwrot;
    }
}
