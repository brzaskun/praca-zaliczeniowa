/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Pitpoz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import session.PitpozFacade;

/**
 *
 * @author Osito
 */
@Named(value="PitDAO")
public class PitDAO implements Serializable{
    @Inject
    private PitpozFacade pitpozFacade;
    //tablica wciagnieta z bazy danych
    private static List<Pitpoz> downloaded;
  
   
    public PitDAO() {
        downloaded = new ArrayList<>();
    }
    
    public  List<Pitpoz> getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(List<Pitpoz> downloaded) {
        PitDAO.downloaded = downloaded;
    }

    
    //pobieranie danych z bazy danych i wklejanie ich do ArrayList o nazwie downloaded

    @PostConstruct
    public void init(){
        Collection c = null;    
        try {
            c = pitpozFacade.findAll();
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala"+e.toString());
            }
            downloaded.addAll(c);
            System.out.println("Pobrano z bazy danych."+c.toString());
    }

  
     public void dodajNowyWpis(Pitpoz selected){
            pitpozFacade.create(selected);
    }
   
     public void destroy(Pitpoz selected) {
        try {
        pitpozFacade.remove(selected);
        } catch (Exception e) {
            System.out.println("Nie usunieto DAO"+selected.getId()+" "+e.toString());
        }
    }
     public void edit(Pitpoz selected){
        try {
            pitpozFacade.edit(selected);
        } catch (Exception e) {
            System.out.println(e.toString());
            FacesMessage msg = new FacesMessage("PIT nie zedytowany DAO", e.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
     }
     
     
}

