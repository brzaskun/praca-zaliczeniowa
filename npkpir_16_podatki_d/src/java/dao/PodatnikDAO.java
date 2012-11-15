
package dao;

import entity.Podatnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import session.PodatnikFacade;

/**
 *
 * @author Osito
 */
@Named
public class PodatnikDAO implements Serializable{
    @Inject
    private PodatnikFacade podatnikFacade;
    //tablica wciagnieta z bazy danych
    private static List<Podatnik> downloaded;
    private static List<Podatnik> down2;
  
   
    public PodatnikDAO() {
        downloaded = new ArrayList<Podatnik>();
        down2 = new ArrayList<Podatnik>();
    }

   
    
    
    public  List<Podatnik> getDownloaded() {
        return downloaded;
    }
     public static List<Podatnik> getDownloadedS() {
        return downloaded;
    }
        public void setDownloaded(List<Podatnik> downloaded) {
        dao.PodatnikDAO.downloaded = downloaded;
    }

    
    //pobieranie danych z bazy danych i wklejanie ich do ArrayList o nazwie downloaded

    @PostConstruct
    public void init(){
        Collection c = null;    
        try {
            c = podatnikFacade.findAll();
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala"+e.toString());
            }
            downloaded.addAll(c);
            System.out.println("Pobrano z bazy danych."+c.toString());
    }

  
     public void dodajNowyWpis(Podatnik selected){
            podatnikFacade.create(selected);
    }
   
     public void destroy(Podatnik selected) {
        try {
        podatnikFacade.remove(selected);
        } catch (Exception e) {
            System.out.println("Nie usnieto "+selected.getNip()+" "+e.toString());
        }
    }
     public void edit(Podatnik selected){
        try {
            podatnikFacade.edit(selected);
        } catch (Exception e) {
            System.out.println(e.toString());
            FacesMessage msg = new FacesMessage("Uzytkownik nie zedytowany DAO", e.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
     }
     
     public Podatnik find(String np){
         return podatnikFacade.findNP(np);
     }
     
     public Podatnik findN(String np){
         return podatnikFacade.findNPN(np);
     }
     
     
}
