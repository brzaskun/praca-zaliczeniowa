/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Evpozycja;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import session.EvpozycjaFacade;

/**
 *
 * @author Osito
 */
@Named
public class EvpozycjaDAO {
  @Inject private EvpozycjaFacade evpozycjaFacade;

    private static List<Evpozycja> downloaded;

    public EvpozycjaFacade getevpozycjaFacade() {
        return evpozycjaFacade;
    }

    public List<Evpozycja> getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(List<Evpozycja> downloaded) {
        EvpozycjaDAO.downloaded = downloaded;
    }

  
    public EvpozycjaDAO() {
        downloaded = new ArrayList<Evpozycja>();
    }
    
    @PostConstruct
    public void init(){
        Collection c = null;
        try {
            c = evpozycjaFacade.findAll();
        } catch (Exception e) {
            System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
        }
        if(c.size()>0){
        downloaded.addAll(c);
        System.out.println("Pobrano z bazy danych." + c.toString());
        }
    }
    
    public void dodajNowyWpis(Evpozycja sel){
        try {
            evpozycjaFacade.create(sel);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Nowa stawkazachowana", sel.getNazwapola().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nowa stawka nie zachowana DAO", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    public void destroy(Evpozycja sel) {
        try {
        evpozycjaFacade.remove(sel);
        } catch (Exception e) {
            System.out.println("Nie usnieto "+sel.getNazwapola()+" "+e.toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nie usunieto",sel.getNazwapola());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void clear(){
        Collection c = null;
        c = evpozycjaFacade.findAll();
        Iterator it;
        it = c.iterator();
        while(it.hasNext()){
            Evpozycja x = (Evpozycja) it.next();
            evpozycjaFacade.remove(x);
        }
    }   

   public void edit(Evpozycja sel){
            evpozycjaFacade.edit(sel);
     }
}
