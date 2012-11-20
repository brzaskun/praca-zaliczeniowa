/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Platnosci;
import entity.PlatnosciPK;
import entity.ZobowiazaniePK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import session.PlatnosciFacade;

/**
 *
 * @author Osito
 */
@Named
public class PlatnosciDAO implements Serializable{
    @Inject
    private PlatnosciFacade platnosciFacade;
    //tablica wciagnieta z bazy danych
    private static List<Platnosci> downloaded;
    private static List<Platnosci> down2;
  
   
    public PlatnosciDAO() {
        downloaded = new ArrayList<>();
        down2 = new ArrayList<>();
    }

   
    
    
    public  List<Platnosci> getDownloaded() {
        return downloaded;
    }
     public static List<Platnosci> getDownloadedS() {
        return downloaded;
    }
        public void setDownloaded(List<Platnosci> downloaded) {
        dao.PlatnosciDAO.downloaded = downloaded;
    }

    
    //pobieranie danych z bazy danych i wklejanie ich do ArrayList o nazwie downloaded

    @PostConstruct
    public void init(){
        Collection c = null;    
        try {
            c = platnosciFacade.findAll();
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala"+e.toString());
            }
            downloaded.addAll(c);
            System.out.println("Pobrano z bazy danych."+c.toString());
    }

  
     public void dodajNowyWpis(Platnosci selected){
            platnosciFacade.create(selected);
    }
   
     public void destroy(Platnosci selected) {
        try {
        platnosciFacade.remove(selected);
        } catch (Exception e) {
            System.out.println("Nie usnieto danych podatkowych DAO"+e.toString());
        }
    }
     public void edit(Platnosci selected){
        try {
            platnosciFacade.edit(selected);
        } catch (Exception e) {
            System.out.println(e.toString());
            FacesMessage msg = new FacesMessage("Platnosci nie zedytowane DAO", e.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
     }
     
     public Platnosci findPK(PlatnosciPK key) throws Exception{
        return platnosciFacade.findPK(key);
     }
   
}
