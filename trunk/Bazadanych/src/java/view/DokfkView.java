/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.Dokfk;
import entity.DokfkPK;
import entity.Wiersz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import msg.Msg;
import session.DokfkFacade;
import session.WierszFacade;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DokfkView implements Serializable{
    private Dokfk dokfk;
    private Wiersz wiersz;
    private static ArrayList<Dokfk> dokfklist;
    private static ArrayList<Wiersz> wierszelista;
    @Inject DokfkFacade dokfkFacade;
    @Inject private WierszFacade wierszFacade;

    public DokfkView() {
        dokfklist = new ArrayList<>();
        dokfk = new Dokfk();
        dokfk.setDokfkPK(new DokfkPK());
        wiersz = new Wiersz();
        wierszelista = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        try {
            dokfklist.addAll(dokfkFacade.findAll());
            wierszelista.add(new Wiersz());
        } catch (Exception e) {}
    }
    
    
    public void dodaj() {
        for (Wiersz p : wierszelista) {
            p.setDokfk(dokfk);
        }
        dokfk.setWierszList(wierszelista);
        try {
            dokfkFacade.create(dokfk);
            Msg.msg("i", "Dodano dokument "+dokfk.getOpisdokumentu());
            Msg.msg("i", "Dodano wiersz "+wierszelista.toString());
        } catch (Exception e) {
            Msg.msg("e", "Taki dokument juz istnieje "+dokfk.getDokfkPK());
        }
    }
    
     public void edit() {
        List<Wiersz> tmp = dokfk.getWierszList();
        for (Wiersz p : tmp) {
            p.setDokfk(dokfk);
        }
        dokfkFacade.edit(dokfk);
        Msg.msg("i", "Wyedtowano dokument "+dokfk.getOpisdokumentu());
    }
     
     

     public void edytujopisdokumenty(ValueChangeEvent event) {
            Object source = event.getSource();
            Object oldValue = event.getOldValue();
            Msg.msg("i", "Stara wartosc obiektu: "+source+" to "+oldValue);
            Object newValue = event.getNewValue();
            Msg.msg("i", "Nowa wartosc "+source+" to "+newValue);
            // ...
        }
     
    
     
     public void usun(Dokfk dokfkusun) {
         dokfklist.remove(dokfkusun);
         dokfkFacade.remove(dokfkusun);
     }
     
     public void usunwiersz(Integer index) {
        Wiersz dokwiersz = dokfk.getWierszList().get(index);
        dokfk.getWierszList().remove(dokwiersz);
        dokfkFacade.edit(dokfk);
        Msg.msg("i", "Wyedtowano dokument "+dokfk.getOpisdokumentu());
    }
     
     public void dodajwiersz() {
         wierszelista.add(new Wiersz());
     }
     
     public void dodajwierszedycja() {
         List<Wiersz> tmp = dokfk.getWierszList();
         tmp.add(new Wiersz());
         dokfk.setWierszList(tmp);
     }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public Dokfk getDokfk() {
        return dokfk;
    }
    
    public void setDokfk(Dokfk dokfk) {
        this.dokfk = dokfk;
    }
    
    public ArrayList<Dokfk> getDokfklist() {
        return dokfklist;
    }
    
    public static ArrayList<Dokfk> getDokfklistS() {
        return dokfklist;
    }
    
    public void setDokfklist(ArrayList<Dokfk> dokfklist) {
        this.dokfklist = dokfklist;
    }
        
    public Wiersz getWiersz() {
        return wiersz;
    }

    public void setWiersz(Wiersz wiersz) {
        this.wiersz = wiersz;
    }
    
    public ArrayList<Wiersz> getWierszelista() {
        return wierszelista;
    }

    public void setWierszelista(ArrayList<Wiersz> wierszelista) {
        DokfkView.wierszelista = wierszelista;
    }
     
    
    //</editor-fold>

      
    

    
}
