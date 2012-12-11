/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Evewidencja;
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
public class EvewidencjaDAO {
  @Inject private SessionFacade evewidencjaFacade;

    private static List<Evewidencja> downloaded;
    

    public SessionFacade getevewidencjaFacade() {
        return evewidencjaFacade;
    }

    public List<Evewidencja> getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(List<Evewidencja> downloaded) {
        EvewidencjaDAO.downloaded = downloaded;
    }

    public SessionFacade getEvewidencjaFacade() {
        return evewidencjaFacade;
    }

    public void setEvewidencjaFacade(SessionFacade evewidencjaFacade) {
        this.evewidencjaFacade = evewidencjaFacade;
    }
    
  
    public EvewidencjaDAO() {
        downloaded = new ArrayList<Evewidencja>();
    }
    
    @PostConstruct
    public void init(){
        Collection c = null;
        try {
            c = evewidencjaFacade.findAll(Evewidencja.class);
        } catch (Exception e) {
            System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
        }
        if(c.size()>0){
        downloaded.addAll(c);
        System.out.println("Pobrano z bazy danych." + c.toString());
        }
    }
    
    public void dodajNowyWpis(Evewidencja sel){
        try {
            evewidencjaFacade.create(sel);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Nowa ewidencja VAT zachowana DAO", sel.getNazwa().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nowa ewidencja nie zachowana EvewidencjaDAO 71", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    public void destroy(Evewidencja sel) {
        try {
        evewidencjaFacade.remove(sel);
        } catch (Exception e) {
            System.out.println("Nie usnieto "+sel.getNazwa()+" "+e.toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nie usunieto",sel.getNazwa());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void clear(){
        Collection c = null;
        c = evewidencjaFacade.findAll(Evewidencja.class);
        Iterator it;
        it = c.iterator();
        while(it.hasNext()){
            Evewidencja x = (Evewidencja) it.next();
            evewidencjaFacade.remove(x);
        }
    }   

   public void edit(Evewidencja sel){
            evewidencjaFacade.edit(sel);
     }
   
    public Evewidencja znajdzponazwie(String nazwa) throws Exception{
        Evewidencja tmp = new Evewidencja();
        try {
            tmp = evewidencjaFacade.findEvewidencjaByName(nazwa);
            return tmp;
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
