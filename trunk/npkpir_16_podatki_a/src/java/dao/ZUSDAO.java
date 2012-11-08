/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Zusstawki;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import session.ZusstawkiFacade;

/**
 *
 * @author Osito
 */
@Named
public class ZUSDAO implements Serializable{
    @Inject
    private ZusstawkiFacade zUSFacade;
    //tablica wciagnieta z bazy danych
    private static List<Zusstawki> downloaded;
    private static List<Zusstawki> down2;
  
   
    public ZUSDAO() {
        downloaded = new ArrayList<>();
        down2 = new ArrayList<>();
    }
  
    
    public  List<Zusstawki> getDownloaded() {
        return downloaded;
    }
     public static List<Zusstawki> getDownloadedS() {
        return downloaded;
    }
        public void setDownloaded(List<Zusstawki> downloaded) {
        dao.ZUSDAO.downloaded = downloaded;
    }

    
    //pobieranie danych z bazy danych i wklejanie ich do ArrayList o nazwie downloaded

    @PostConstruct
    public void init(){
        Collection c = null;    
        try {
            c = zUSFacade.findAll();
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala"+e.toString());
            }
            downloaded.addAll(c);
            System.out.println("Pobrano z bazy danych."+c.toString());
    }

  
     public void dodajNowyWpis(Zusstawki selected){
            zUSFacade.create(selected);
    }
   
     public void destroy(Zusstawki selected) {
        try {
        zUSFacade.remove(selected);
        } catch (Exception e) {
            System.out.println("Nie usnieto stawki ZUS"+selected.getZusstawkiPK().getRok()+selected.getZusstawkiPK().getMiesiac()+ "+e.toString()");
        }
    }
     public void edit(Zusstawki selected){
        try {
            zUSFacade.edit(selected);
        } catch (Exception e) {
            System.out.println(e.toString());
            FacesMessage msg = new FacesMessage("ZUS STAWKA nie zedytowany DAO", e.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
     }
     
//     public Zusstawki find(String np){
//         return zUSFacade.findNP(np);
     //}
}
