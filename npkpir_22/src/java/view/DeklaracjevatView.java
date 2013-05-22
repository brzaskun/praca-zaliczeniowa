/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Vatcomparator;
import dao.DeklaracjevatDAO;
import entity.Deklaracjevat;
import entity.Dok;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class DeklaracjevatView implements Serializable {
    @Inject private DeklaracjevatDAO deklaracjevatDAO;
    private List<Deklaracjevat> wyslane;
    private List<Deklaracjevat> wyslanenormalne;
    private List<Deklaracjevat> wyslaneniepotwierdzone;
    private List<Deklaracjevat> wyslanetestowe;
    private List<Deklaracjevat> wyslanezbledem;
    private List<Deklaracjevat> oczekujace;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    private static Deklaracjevat selected;

    public DeklaracjevatView() {
        wyslane = new ArrayList<>();
        oczekujace = new ArrayList<>();
        wyslanenormalne = new ArrayList<>();
        wyslanetestowe = new ArrayList<>();
        wyslanezbledem = new ArrayList<>();
        wyslaneniepotwierdzone = new ArrayList<>();
    }
    
    
    @PostConstruct
    private void init(){
        try{
            Deklaracjevat temp = deklaracjevatDAO.findDeklaracjeDowyslania(wpisView.getPodatnikWpisu());
            oczekujace.add(temp);
        } catch (Exception e){}
         try{
            wyslane =  deklaracjevatDAO.findDeklaracjeWyslane(wpisView.getPodatnikWpisu());
            for(Deklaracjevat p : wyslane){
                    try{
                    if(p.isTestowa()){
                        wyslanetestowe.add(p);
                    }
                    } catch (Exception e) {}
            }
            for(Deklaracjevat p : wyslane){
                    if(!wyslanetestowe.contains(p)){
                        if (p.getStatus().startsWith("4")){
                            wyslanezbledem.add(p);
                            } else if (p.getStatus().startsWith("3")) {
                            wyslaneniepotwierdzone.add(p);
                            } else {
                            wyslanenormalne.add(p);
                        }
                    }
                    
                }
        } catch (Exception e){}
         Collections.sort(wyslanenormalne, new Vatcomparator());
    }
    
     public void edit(RowEditEvent ex) {
        try {
            //sformatuj();
            deklaracjevatDAO.edit(selected);
            FacesMessage msg = new FacesMessage("Nowy dokytkownik edytowany " + ex.getObject().toString(), selected.getPodatnik());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage("Dokytkownik nie zedytowany", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
     
   public void destroy(Deklaracjevat selDok) {
        selected = selDok;
    }
   
    public void destroy2() {
         try {
               oczekujace.remove(selected);
               deklaracjevatDAO.destroy(selected);
                Msg.msg("i","Deklaracja usunięta","formX:msg");
            } catch (Exception e) {
                Msg.msg("e","Deklaracja nie usunięta","formX:msg");
                System.out.println("Nie usunieto " + selected.getIdentyfikator() + " " + e.toString());
            }
           
        }
    
    public void destroy2a() {
         try {
               wyslanezbledem.remove(selected);
               wyslanetestowe.remove(selected);
               deklaracjevatDAO.destroy(selected);
                Msg.msg("i","Deklaracja usunięta","formX:msg");
            } catch (Exception e) {
                Msg.msg("e","Deklaracja nie usunięta","formX:msg");
                System.out.println("Nie usunieto " + selected.getIdentyfikator() + " " + e.toString());
            }
           
        }

    public List<Deklaracjevat> getWyslane() {
        return wyslane;
    }

    public void setWyslane(List<Deklaracjevat> wyslane) {
        this.wyslane = wyslane;
    }

    public List<Deklaracjevat> getOczekujace() {
        return oczekujace;
    }

    public void setOczekujace(List<Deklaracjevat> oczekujace) {
        this.oczekujace = oczekujace;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Deklaracjevat getSelected() {
        return selected;
    }

    public void setSelected(Deklaracjevat selected) {
        this.selected = selected;
    }

    public List<Deklaracjevat> getWyslanenormalne() {
        return wyslanenormalne;
    }

    public void setWyslanenormalne(List<Deklaracjevat> wyslanenormalne) {
        this.wyslanenormalne = wyslanenormalne;
    }

    public List<Deklaracjevat> getWyslanetestowe() {
        return wyslanetestowe;
    }

    public void setWyslanetestowe(List<Deklaracjevat> wyslanetestowe) {
        this.wyslanetestowe = wyslanetestowe;
    }

    public List<Deklaracjevat> getWyslanezbledem() {
        return wyslanezbledem;
    }

    public void setWyslanezbledem(List<Deklaracjevat> wyslanezbledem) {
        this.wyslanezbledem = wyslanezbledem;
    }

    public List<Deklaracjevat> getWyslaneniepotwierdzone() {
        return wyslaneniepotwierdzone;
    }

    public void setWyslaneniepotwierdzone(List<Deklaracjevat> wyslaneniepotwierdzone) {
        this.wyslaneniepotwierdzone = wyslaneniepotwierdzone;
    }
    
    
    
}
