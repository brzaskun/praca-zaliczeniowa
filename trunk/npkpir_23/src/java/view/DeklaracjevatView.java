/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Vatcomparator;
import dao.DeklaracjevatDAO;
import dao.PodatnikDAO;
import dao.WpisDAO;
import entity.Deklaracjevat;
import entity.Wpis;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import mail.MailOther;
import msg.Msg;
import org.primefaces.event.RowEditEvent;
import pdf.PdfVAT7;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DeklaracjevatView implements Serializable {
    private Deklaracjevat selected;
    @Inject private DeklaracjevatDAO deklaracjevatDAO;
    private List<Deklaracjevat> wyslane;
    private List<Deklaracjevat> wyslanenormalne;
    private List<Deklaracjevat> wyslaneniepotwierdzone;
    private List<Deklaracjevat> wyslanetestowe;
    private List<Deklaracjevat> wyslanezbledem;
    private List<Deklaracjevat> oczekujace;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @Inject
    private WpisDAO wpisDAO;
    @Inject
    private PodatnikDAO podatnikDAO;

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
        wyslane = new ArrayList<>();
        oczekujace = new ArrayList<>();
        wyslanenormalne = new ArrayList<>();
        wyslanetestowe = new ArrayList<>();
        wyslanezbledem = new ArrayList<>();
        wyslaneniepotwierdzone = new ArrayList<>();
        try{
            List<Deklaracjevat> pobranadeklaracja = deklaracjevatDAO.findDeklaracjeDowyslaniaList(wpisView.getPodatnikWpisu());
                  oczekujace.addAll(pobranadeklaracja);
        } catch (Exception e) { 
            E.e(e); }
         try{
            wyslane =  deklaracjevatDAO.findDeklaracjeWyslane(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu().toString());
            for(Deklaracjevat p : wyslane){
                    try{
                    if(p.isTestowa()){
                        wyslanetestowe.add(p);
                    }
                    } catch (Exception e) { E.e(e); }
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
        } catch (Exception e) { 
         E.e(e); }
         Collections.sort(wyslanenormalne, new Vatcomparator());
    }
    
     public void edit(RowEditEvent ex) {
        try {
            //sformatuj();
            deklaracjevatDAO.edit(selected);
            FacesMessage msg = new FacesMessage("Nowy dokytkownik edytowany " + ex.getObject().toString(), selected.getPodatnik());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) { E.e(e); 
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
            } catch (Exception e) { E.e(e); 
                Msg.msg("e","Deklaracja nie usunięta","formX:msg");
            }
           
        }
    
    public void destroy2a() {
         try {
               wyslanezbledem.remove(selected);
               wyslanetestowe.remove(selected);
               deklaracjevatDAO.destroy(selected);
                Msg.msg("i","Deklaracja usunięta","formX:msg");
            } catch (Exception e) { E.e(e); 
                Msg.msg("e","Deklaracja nie usunięta","formX:msg");
            }
           
        }

    public void mailvat7(int row) {
        try {
            MailOther.vat7(row, wpisView);
        } catch (Exception e) { E.e(e); 
            
        }
    }
    
    public void drukujprzygotowanedowysylki(Deklaracjevat d) {
        try {
            PdfVAT7.drukujwys(podatnikDAO, d);
        } catch (Exception e) { E.e(e); 
            
        }
    }
    
    private void aktualizujGuest(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpisDAO.edit(wpistmp);
    }
     private void aktualizuj(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        wpistmp.setPodatnikWpisu(wpisView.getPodatnikWpisu());
        wpisDAO.edit(wpistmp);
        wpisView.findWpis();
    }
    
     public void aktualizujGuest(String strona) throws IOException {
        aktualizujGuest();
        aktualizuj();
        init();
        //FacesContext.getCurrentInstance().getExternalContext().redirect(strona);
    }
     
    public void drukujdeklaracje(Deklaracjevat dkl, int index) {
        try {
            PdfVAT7.drukuj(dkl, index, podatnikDAO);
        } catch (Exception e) { E.e(e); 
            
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
