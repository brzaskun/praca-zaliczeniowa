/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Sesja;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import session.SesjaFacade;

/**
 *
 * @author Osito
 */
public class SesjaDAO {
     @Inject private SesjaFacade sesjaFacade;

    private static List<Sesja> downloaded;

    public SesjaFacade getsesjaFacade() {
        return sesjaFacade;
    }

    public List<Sesja> getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(List<Sesja> downloaded) {
        SesjaDAO.downloaded = downloaded;
    }

  
    public SesjaDAO() {
        downloaded = new ArrayList<Sesja>();
    }
    
    @PostConstruct
    public void init(){
        Collection c = null;
        try {
            c = sesjaFacade.findAll();
        } catch (Exception e) {
            System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
        }
        if(c.size()>0){
        downloaded.addAll(c);
        System.out.println("Pobrano z bazy danych." + c.toString());
        }
    }
    
    public void dodajNowyWpis(Sesja sel){
        try {
            sesjaFacade.create(sel);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Nowa sesja zachowana", sel.getNrsesji().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nowa sesja nie zachowana DAO", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    public void destroy(Sesja sel) {
        try {
        sesjaFacade.remove(sel);
        } catch (Exception e) {
            System.out.println("Nie usnieto "+sel.getNrsesji()+" "+e.toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nie usunieto sesji",sel.getNrsesji());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void clear(){
        Collection c = null;
        c = sesjaFacade.findAll();
        Iterator it;
        it = c.iterator();
        while(it.hasNext()){
            Sesja x = (Sesja) it.next();
            sesjaFacade.remove(x);
        }
    }   

   public void edit(Sesja sel){
            sesjaFacade.edit(sel);
     }
   
   public Sesja find(String nrsesji){
       return sesjaFacade.find(nrsesji);
   }
}
