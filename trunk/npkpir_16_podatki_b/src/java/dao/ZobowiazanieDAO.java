/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Zobowiazanie;
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
import session.ZobowiazanieFacade;

/**
 *
 * @author Osito
 */
@Named
public class ZobowiazanieDAO implements Serializable{
    @Inject
    private ZobowiazanieFacade zobowiazanieFacade;
    //tablica wciagnieta z bazy danych
    private static List<Zobowiazanie> downloaded;
    private static List<Zobowiazanie> down2;
  
   
    public ZobowiazanieDAO() {
        downloaded = new ArrayList<>();
        down2 = new ArrayList<>();
    }

   
    
    
    public  List<Zobowiazanie> getDownloaded() {
        return downloaded;
    }
     public static List<Zobowiazanie> getDownloadedS() {
        return downloaded;
    }
        public void setDownloaded(List<Zobowiazanie> downloaded) {
        dao.ZobowiazanieDAO.downloaded = downloaded;
    }

    
    //pobieranie danych z bazy danych i wklejanie ich do ArrayList o nazwie downloaded

    @PostConstruct
    public void init(){
        Collection c = null;    
        try {
            c = zobowiazanieFacade.findAll();
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala"+e.toString());
            }
            downloaded.addAll(c);
            System.out.println("Pobrano z bazy danych."+c.toString());
    }

  
     public void dodajNowyWpis(Zobowiazanie selected){
            zobowiazanieFacade.create(selected);
    }
   
     public void destroy(Zobowiazanie selected) {
        try {
        zobowiazanieFacade.remove(selected);
        } catch (Exception e) {
            System.out.println("Nie usnieto danych podatkowych DAO"+e.toString());
        }
    }
     public void edit(Zobowiazanie selected){
        try {
            zobowiazanieFacade.edit(selected);
        } catch (Exception e) {
            System.out.println(e.toString());
            FacesMessage msg = new FacesMessage("Dane podatkowe nie zedytowane DAO", e.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
     }
     
     public Zobowiazanie find(String rok, String mc){
        return zobowiazanieFacade.find(rok, mc);
     }
   
}
