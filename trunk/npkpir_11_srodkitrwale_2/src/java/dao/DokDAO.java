/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Dok;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.security.DenyAll;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import session.DokFacade;

/**
 *
 * @author Osito
 */
@Named(value="DokDAO")
public class DokDAO implements Serializable{
   
   
    @Inject
    private DokFacade dokFacade;
    //tablica wciagnieta z bazy danych
    private static List<Dok> downloadedDok;
    //tablica obiektów

    public List<Dok> getDownloadedDok() {
        return downloadedDok;
    }

    public void setDownloadedDok(List<Dok> downloadedDok) {
        DokDAO.downloadedDok = downloadedDok;
    }

    public DokDAO() {
        downloadedDok = new ArrayList<Dok>();
    }
   
    
    
    @PostConstruct
    public void init(){
        Collection c = null;
        try {
            c = dokFacade.findAll();
        } catch (Exception e) {
            System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
        }
        if(c.size()>0){
        downloadedDok.addAll(c);
        System.out.println("Pobrano z bazy danych." + c.toString());
        }
    }
           
     
    
    public void refresh(){
        downloadedDok.clear();
        Collection c = null;
        try {
            c = dokFacade.findAll();
        } catch (Exception e) {
            System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
        }
        downloadedDok.addAll(c);
        System.out.println("Pobrano z bazy danych." + c.toString());
       }
    
    
    public Dok znajdzDuplikat(Dok selD) throws Exception{
        Dok tmp = null;
        tmp = dokFacade.duplicat(selD);
        return tmp;
        }
   
    
     public void dodajNowyWpis(Dok selDokument){
        try {
            dokFacade.create(selDokument);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Nowy dokument zachowany", selDokument.getIdDok().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Dokument nie uwtorzony DAO"+e.getStackTrace().toString(),"");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
        }
    }
    
    public void destroy(Dok selDokument) {
        try {
        dokFacade.remove(selDokument);
        } catch (Exception e) {
            System.out.println("Nie usnieto "+selDokument.getIdDok()+" "+e.toString());
        }
    }
        
     
    public void edit(Dok selDokument){
        try {
            dokFacade.edit(selDokument);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
        }
     }
}

