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
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
//pomaga przenosci opisy bo inaczej nie chca sie zachowac. scopy nie pasuja
public class EVatOpisDAO implements Serializable{
    @Inject
    private SessionFacade eVatOpisFacade;
    private static List<EVatOpis> downloadedEVatOpis;

    public SessionFacade geteVatOpisFacade() {
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
            c = eVatOpisFacade.findAll(EVatOpis.class);
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
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Nowa faktura zachowana", selDokument.getOpis1().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nowa faktura nie zachowana DAO", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
        }
    }
    public void destroy(EVatOpis selDokument) {
        try {
        eVatOpisFacade.remove(selDokument);
        } catch (Exception e) {
            System.out.println("Nie usnieto "+selDokument.getOpis1()+" "+e.toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nie usunieto",selDokument.getOpis1());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void clear(){
        Collection c = null;
        c = eVatOpisFacade.findAll(EVatOpis.class);
        Iterator it;
        it = c.iterator();
        while(it.hasNext()){
            EVatOpis x = (EVatOpis) it.next();
            eVatOpisFacade.remove(x);
        }
    }
}
