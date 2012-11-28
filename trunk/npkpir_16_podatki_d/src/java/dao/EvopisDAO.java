/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Evopis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import session.EvopisFacade;

/**
 *
 * @author Osito
 */
@Named
public class EvopisDAO {
  @Inject private EvopisFacade evopisFacade;

    private static List<Evopis> downloaded;

    public EvopisFacade getevopisFacade() {
        return evopisFacade;
    }

    public List<Evopis> getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(List<Evopis> downloaded) {
        EvopisDAO.downloaded = downloaded;
    }

  
    public EvopisDAO() {
        downloaded = new ArrayList<Evopis>();
    }
    
    @PostConstruct
    public void init(){
        Collection c = null;
        try {
            c = evopisFacade.findAll();
        } catch (Exception e) {
            System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
        }
        if(c.size()>0){
        downloaded.addAll(c);
        System.out.println("Pobrano z bazy danych." + c.toString());
        }
    }
    
    public void dodajNowyWpis(Evopis sel){
        try {
            evopisFacade.create(sel);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Nowa stawkazachowana", sel.getOpis().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nowa stawka nie zachowana DAO", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    public void destroy(Evopis sel) {
        try {
        evopisFacade.remove(sel);
        } catch (Exception e) {
            System.out.println("Nie usnieto "+sel.getOpis()+" "+e.toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nie usunieto",sel.getOpis());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void clear(){
        Collection c = null;
        c = evopisFacade.findAll();
        Iterator it;
        it = c.iterator();
        while(it.hasNext()){
            Evopis x = (Evopis) it.next();
            evopisFacade.remove(x);
        }
    }   

   public void edit(Evopis sel){
            evopisFacade.edit(sel);
     }
}
