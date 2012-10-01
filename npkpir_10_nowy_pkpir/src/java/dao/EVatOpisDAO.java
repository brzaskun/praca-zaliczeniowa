/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.EVatOpis;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import session.EVatOpisFacade;

/**
 *
 * @author Osito
 */
@Named
public class EVatOpisDAO implements Serializable{
    @Inject
    private EVatOpisFacade eVatOpisFacade;
    private static List<EVatOpis> downloadedEVatOpis;

    public EVatOpisFacade geteVatOpisFacade() {
        return eVatOpisFacade;
    }

    public List<EVatOpis> getDownloadedEVatOpis() {
        return downloadedEVatOpis;
    }

    public void setDownloadedEVatOpis(List<EVatOpis> downloadedEVatOpis) {
        EVatOpisDAO.downloadedEVatOpis = downloadedEVatOpis;
    }

  
    public EVatOpisDAO() {
        downloadedEVatOpis = new ArrayList<EVatOpis>();
    }
    
    @PostConstruct
    public void init(){
        Collection c = null;
        try {
            c = eVatOpisFacade.findAll();
        } catch (Exception e) {
            System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
        }
        if(c.size()>0){
        downloadedEVatOpis.addAll(c);
        System.out.println("Pobrano z bazy danych." + c.toString());
        }
    }
    
    public void dodajNowyWpis(EVatOpis selDokument){
        try {
            eVatOpisFacade.create(selDokument);
            FacesMessage msg = new FacesMessage("Nowy dokument zachowany", selDokument.getOpis1().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage("Dokument nie uwtorzony DAO", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
        }
    }
    public void destroy(EVatOpis selDokument) {
        try {
        eVatOpisFacade.remove(selDokument);
        } catch (Exception e) {
            System.out.println("Nie usnieto "+selDokument.getOpis1()+" "+e.toString());
        }
    }
    
    public void clear(){
        Collection c = null;
        c = eVatOpisFacade.findAll();
        Iterator it;
        it = c.iterator();
        while(it.hasNext()){
            EVatOpis x = (EVatOpis) it.next();
            eVatOpisFacade.remove(x);
        }
    }
}
