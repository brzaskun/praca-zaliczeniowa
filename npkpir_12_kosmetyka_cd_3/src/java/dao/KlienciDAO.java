/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Klienci;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import session.KlienciFacade;

/**
 *
 * @author Osito
 */
@Named
public class KlienciDAO implements Serializable{
   
    @Inject
    private KlienciFacade klienciFacade;
    //tablica wciagnieta z bazy danych
    
    private List<Klienci> downloadedKlienci;

    public KlienciDAO() {
        downloadedKlienci = new ArrayList<Klienci>();
    }
    
    

    public List<Klienci> getdownloadedKlienci() {
        return downloadedKlienci;
    }

    public void setdownloadedKlienci(List<Klienci> downloadedKlienci) {
        this.downloadedKlienci = downloadedKlienci;
    }

    @PostConstruct
    public void init(){
        Collection c = null;
        try {
            c = klienciFacade.findAll();
        } catch (Exception e) {
            System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
        }
        if(c.size()>0){
        downloadedKlienci.addAll(c);
        System.out.println("Pobrano z bazy danych." + c.toString());
        }
    }
           
   
//    
//    public Klienci znajdzDuplikat(Klienci selD) throws Exception{
//        Klienci tmp = null;
//        tmp = klienciFacade.duplicat(selD);
//        return tmp;
//        }
   
    
     public void dodajNowyWpis(Klienci selectedKlient){
        try {
            klienciFacade.create(selectedKlient);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Nowy klient zachowany", selectedKlient.getNpelna().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Nie utworzono Klienta "+e.toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Klient nie uwtorzony DAO"+e.toString(),"");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
        }
    }
    
    public void destroy(Klienci selectedKlient) {
        try {
        klienciFacade.remove(selectedKlient);
        } catch (Exception e) {
            System.out.println("Nie usnieto "+selectedKlient.getNpelna()+" "+e.toString());
        }
    }
        
     
    public void edit(Klienci selectedKlient){
        try {
            klienciFacade.edit(selectedKlient);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
        }
     }
    
    
    
    
}
