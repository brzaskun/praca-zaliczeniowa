/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import embeddable.Pod;
import embeddable.Umorzenie;
import entity.Amodok;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import session.SessionFacade;


/**
 *
 * @author Osito
 */
public class AmoDokDAO {
    @Inject
    private SessionFacade amodokFacade;
    
    private List<Amodok> amoDokList;

    public AmoDokDAO() {
        amoDokList = new ArrayList<Amodok>();
    }

    public List<Amodok> getAmoDokList() {
        return amoDokList;
    }

    public void setAmoDokList(List<Amodok> amoDokList) {
        this.amoDokList = amoDokList;
    }
 @PostConstruct
    public void init(){
        Collection c = null;
        try {
            c = amodokFacade.findAll(Amodok.class);
        } catch (Exception e) {
            System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
        }
        if(c.size()>0){
        amoDokList.addAll(c);
        System.out.println("Pobrano z bazy danych." + c.toString());
        }
    }
           
   
//    
//    public AmoDok znajdzDuplikat(AmoDok selD) throws Exception{
//        AmoDok tmp = null;
//        tmp = amodokFacade.duplicat(selD);
//        return tmp;
//        }
   
    
     public void dodajNowyWpis(Amodok selected){
        try {
            amodokFacade.create(selected);
            System.out.println("Nowy dokument amortyzacji zachowany"+selected.getId()+" "+selected.getMc());
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Nowy dokument amortyzacji zachowany", selected.getPodatnik().toString());
//            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Nie utworzono srodka "+e.toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"AmoDokument nie uwtorzony błąd bazy"+e.toString(),"");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
        }
    }
    
    public void destroy(Amodok selected) {
        try {
        amodokFacade.remove(selected);
        } catch (Exception e) {
            System.out.println("Nie usnieto "+selected.getPodatnik()+" "+e.toString());
        }
    }
    
     public void destroyPod(String pod) {
        List<Amodok> lista = new ArrayList<>();
        lista.addAll(amoDokList);
        Iterator it;
        it = lista.iterator();
        while(it.hasNext()){
            Amodok wpis = (Amodok) it.next();
             if (wpis.getPodatnik().equals(pod)) {
                 try {
                     amodokFacade.remove(wpis);
                 } catch (Exception e) {
                     System.out.println("Nie usnieto " + wpis.getPodatnik() + " " + e.toString());
                 }
             }
         }
        
    }
     
    public void edit(Amodok selected){
        try {
            amodokFacade.edit(selected);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
        }
     }
    
    
        
}
