/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Uz;
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
public class UzDAO implements Serializable{
    @Inject
    private SessionFacade uzFacade;
    //tablica wciagnieta z bazy danych
    private static List<Uz> downloadedUz;
  
   
    public UzDAO() {
        downloadedUz = new ArrayList<Uz>();
    }
    
    public  List<Uz> getDownloadedUz() {
        return downloadedUz;
    }

    public void setDownloadedUz(List<Uz> downloadedUz) {
        UzDAO.downloadedUz = downloadedUz;
    }

    
    //pobieranie danych z bazy danych i wklejanie ich do ArrayList o nazwie downloadedUz

    @PostConstruct
    public void init(){
        Collection c = null;    
        try {
            c = uzFacade.findAll(Uz.class);
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala"+e.toString());
            }
            downloadedUz.addAll(c);
            System.out.println("Pobrano z bazy danych."+c.toString());
    }

  
     public void dodajNowyWpis(Uz selUzytkownik){
            uzFacade.create(selUzytkownik);
    }
   
     public void destroy(Uz selUzytkownik) {
        try {
        uzFacade.remove(selUzytkownik);
        } catch (Exception e) {
            System.out.println("Nie usnieto "+selUzytkownik.getLogin()+" "+e.toString());
        }
    }
     public void edit(Uz selUzytkownik){
        try {
            uzFacade.edit(selUzytkownik);
        } catch (Exception e) {
            System.out.println(e.toString());
            FacesMessage msg = new FacesMessage("Uzytkownik nie zedytowany DAO", e.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
     }
     
     
    public Uz find(String np){
         return uzFacade.findUzNP(np);
     }
}

