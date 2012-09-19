/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.Nwpis;
import entity.Uz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import session.NwpisFacade;


/**
 *
 * @author Osito
 */
@ManagedBean(name="NwpisView")
@SessionScoped
public final class NwpisView implements Serializable {
    @EJB
    private NwpisFacade wpsFacade;
    private Nwpis wps;
    private Nwpis selWpis;
    private List<Nwpis> wpsLista;
    private String kwaSelected;
    private String nrDokSelected;
    

    public NwpisView() {
        wps = new Nwpis();
        selWpis = new Nwpis();
        wpsLista = new ArrayList<Nwpis>();
        
    }

    public NwpisView(Nwpis wps, List<Nwpis> wpsLista) {
        this.wps = wps;
        this.wpsLista = wpsLista;
    }
   

    public NwpisFacade getWpisFacade() {
        return wpsFacade;
    }

   
    public Nwpis getWps() {
        return wps;
    }

    public void setWpis(Nwpis wps) {
        this.wps = wps;
    }

    public Nwpis getSelWpis() {
        return selWpis;
    }

    public void setSelWpis(Nwpis selWpis) {
        this.selWpis = selWpis;
    }

    public String getKwaSelected() {
        return kwaSelected;
    }

    public void setKwaSelected(String kwaSelected) {
        this.kwaSelected = kwaSelected;
    }

    public String getNrDokSelected() {
        return nrDokSelected;
    }

    public void setNrDokSelected(String nrDokSelected) {
        this.nrDokSelected = nrDokSelected;
    }

 
    
    /**
     *zapełnianie pustej listy danymi z bazy danych
     * @param nie_wymmaga_parametrów
     * @return List typy Nwpis do zapełnienia tabeli w pliku tablica.xhtml
     */
    public List<Nwpis> getWpsLista() {
        if (wpsLista.isEmpty()) {
            Collection c;
            try {
//                if(wybranaMarka!=null){ c = (Collection) carsFacade.findbyMan(wybranaMarka);} else { c = (Collection) carsFacade.findAll();}
                c = (Collection) wpsFacade.findAll();
                FacesMessage msg = new FacesMessage("Zapelnianie WpsLista poszlo ok");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception notfound) {
                FacesMessage msg = new FacesMessage("Zapelnianie WpsLista nie tak ", notfound.toString());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            }
            wpsLista.addAll(c);
        }
        return wpsLista;
    }
    /**
     * metoda skopiowana z primefaces służąca do filtrowania danych w kolumnie tabeli
     * @param tablica typu string
     * @return options tablica typu String z wybranymi obiektami
     */
     private SelectItem[] createFilterOptions(String[] data)  {  
        SelectItem[] options = new SelectItem[data.length + 1];  
  
        options[0] = new SelectItem("", "Select");  
        for(int i = 0; i < data.length; i++) {  
            options[i + 1] = new SelectItem(data[i], data[i]);  
        }  
        return options;  
    }
     
     public  void dodajNowyWpis(){
        try {
            System.out.println("Pobrany wpis");
            System.out.println(wps.toString());
             //wps.setKwa(uzView.returnUzObject(kwaSelected));
             System.out.println("Dodaje nowy wpis");
            System.out.println(wps.toString());
              Long l = new Long(1);
            wps.setId((l));
            wpsLista.add(wps);
//            wpsFacade.create(wps);
//            wpsLista.clear();
//            Collection c = wpsFacade.findAll();
//            wpsLista.addAll(c);
            FacesMessage msg = new FacesMessage("New Car Saved", wps.getKlt());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage("nie wyszedl save",e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}