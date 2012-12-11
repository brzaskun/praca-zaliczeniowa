/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podstawki;
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
public class PodStawkiDAO implements Serializable{
    @Inject
    private SessionFacade podstawkiFacade;
    //tablica wciagnieta z bazy danych
    private static List<Podstawki> downloaded;
    private static List<Podstawki> down2;
  
   
    public PodStawkiDAO() {
        downloaded = new ArrayList<>();
        down2 = new ArrayList<>();
    }

   
    
    
    public  List<Podstawki> getDownloaded() {
        return downloaded;
    }
     public static List<Podstawki> getDownloadedS() {
        return downloaded;
    }
        public void setDownloaded(List<Podstawki> downloaded) {
        dao.PodStawkiDAO.downloaded = downloaded;
    }

    
    //pobieranie danych z bazy danych i wklejanie ich do ArrayList o nazwie downloaded

    @PostConstruct
    public void init(){
        Collection c = null;    
        try {
            c = podstawkiFacade.findAll(Podstawki.class);
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala"+e.toString());
            }
            downloaded.addAll(c);
            System.out.println("Pobrano z bazy danych."+c.toString());
    }

  
     public void dodajNowyWpis(Podstawki selected){
            podstawkiFacade.create(selected);
    }
   
     public void destroy(Podstawki selected) {
        try {
        podstawkiFacade.remove(selected);
        } catch (Exception e) {
            System.out.println("Nie usnieto danych podatkowych DAO"+e.toString());
        }
    }
     public void edit(Podstawki selected){
        try {
            podstawkiFacade.edit(selected);
        } catch (Exception e) {
            System.out.println(e.toString());
            FacesMessage msg = new FacesMessage("Dane podatkowe nie zedytowane DAO", e.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
     }
     
     public Podstawki find(Integer rok){
        return podstawkiFacade.findPodstawkiyear(rok);
     }
   
}
