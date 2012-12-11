/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Odsetki;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class OdsetkiDAO implements Serializable{
    @Inject
    private SessionFacade odsetkiFacade;
    //tablica wciagnieta z bazy danych
    private static List<Odsetki> downloaded;
    private static List<Odsetki> down2;
  
   
    public OdsetkiDAO() {
        downloaded = new ArrayList<>();
        down2 = new ArrayList<>();
    }

   
    
    
    public  List<Odsetki> getDownloaded() {
        return downloaded;
    }
     public static List<Odsetki> getDownloadedS() {
        return downloaded;
    }
        public void setDownloaded(List<Odsetki> downloaded) {
        dao.OdsetkiDAO.downloaded = downloaded;
    }

    
    //pobieranie danych z bazy danych i wklejanie ich do ArrayList o nazwie downloaded

    @PostConstruct
    public void init(){
        Collection c = null;    
        try {
            c = odsetkiFacade.findAll(Odsetki.class);
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala"+e.toString());
            }
            downloaded.addAll(c);
            System.out.println("Pobrano z bazy danych."+c.toString());
    }

  
     public void dodajNowyWpis(Odsetki selected){
            odsetkiFacade.create(selected);
    }
   
     public void destroy(Odsetki selected) {
        try {
        odsetkiFacade.remove(selected);
        } catch (Exception e) {
            System.out.println("Nie usnieto danych podatkowych DAO"+e.toString());
        }
    }
     public void edit(Odsetki selected){
        try {
            odsetkiFacade.edit(selected);
        } catch (Exception e) {
            System.out.println(e.toString());
            FacesMessage msg = new FacesMessage("Odsetki nie zedytowane DAO", e.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
     }
     
//     public Odsetki findPK(OdsetkiPK key) throws Exception{
//        return odsetkiFacade.findPK(key);
//     }
//   
}
