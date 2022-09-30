/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SzkolenieWykazFacade;
import dao.UczestnicyFacade;
import dao.ZakladpracyFacade;
import entity.Szkoleniewykaz;
import entity.Uczestnicy;
import entity.Zakladpracy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class OsobyView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private Zakladpracy zakladpracy;
    private List<Zakladpracy> zakladpracylista;
    private List<Szkoleniewykaz> szkolenialista;
    private List<Uczestnicy> listaosoby;
    @Inject
    private Uczestnicy nowy;
    private Uczestnicy nowy_arch;
    private List<Uczestnicy> listaosobyselected;
    private List<Uczestnicy> listaosobyfiltered;
    @Inject
    private ZakladpracyFacade zakladpracyFacade;
    @Inject
    private SzkolenieWykazFacade szkolenieWykazFacade;
    @Inject
    private UczestnicyFacade uczestnicyFacade;
    
    @PostConstruct
    private void init() {
        zakladpracylista = zakladpracyFacade.findAll();
        szkolenialista = szkolenieWykazFacade.findAll();
    }
    
    public List<Zakladpracy> complete(String query) {
        List<Zakladpracy> results = new ArrayList<>();
        try {
            for (Zakladpracy p : zakladpracylista) {
                if (p.getNazwazakladu().toLowerCase().contains(query.toLowerCase())) {
                    results.add(p);
                }
            }
        } catch (Exception e){}
        return results;
    }
    
    public void pobierzosoby() {
        if (zakladpracy!=null) {
            listaosoby  = uczestnicyFacade.findByFirmaId(zakladpracy);
            System.out.println("");
        }
    }
    
    public void dodaj() {
        if (nowy!=null&&nowy.getImienazwisko()!=null) {
            try {
                nowy.setFirma(nowy.getFirmaId().getNazwazakladu());
                uczestnicyFacade.create(nowy);
                nowy.setUtworzony(new Date());
                nowy_arch = new Uczestnicy(nowy);
                nowy=new Uczestnicy();
                Msg.msg("Dodano nowego uczestnika");
            } catch (Exception e) {
                Msg.msg("e","Wystąpił błąd, nie dodano nowego uczestnika");
            }
        }
    }

    public void onRowEdit(RowEditEvent<Uczestnicy> event) {
        Msg.msg("Zmieniono dane użytkownika");
    }

    public void onRowCancel(RowEditEvent<Uczestnicy> event) {
        Msg.msg("w","Niezmieniono danych użytkownika");
    }

    
    
    public Zakladpracy getZakladpracy() {
        return zakladpracy;
    }

    public void setZakladpracy(Zakladpracy zakladpracy) {
        this.zakladpracy = zakladpracy;
    }

    public List<Zakladpracy> getZakladpracylista() {
        return zakladpracylista;
    }

    public void setZakladpracylista(List<Zakladpracy> zakladpracylista) {
        this.zakladpracylista = zakladpracylista;
    }

    public List<Uczestnicy> getListaosoby() {
        return listaosoby;
    }

    public void setListaosoby(List<Uczestnicy> listaosoby) {
        this.listaosoby = listaosoby;
    }

    public List<Uczestnicy> getListaosobyselected() {
        return listaosobyselected;
    }

    public void setListaosobyselected(List<Uczestnicy> listaosobyselected) {
        this.listaosobyselected = listaosobyselected;
    }

    public Uczestnicy getNowy() {
        return nowy;
    }

    public void setNowy(Uczestnicy nowy) {
        this.nowy = nowy;
    }

    public List<Szkoleniewykaz> getSzkolenialista() {
        return szkolenialista;
    }

    public void setSzkolenialista(List<Szkoleniewykaz> szkolenialista) {
        this.szkolenialista = szkolenialista;
    }

    public Uczestnicy getNowy_arch() {
        return nowy_arch;
    }

    public void setNowy_arch(Uczestnicy nowy_arch) {
        this.nowy_arch = nowy_arch;
    }

    public List<Uczestnicy> getListaosobyfiltered() {
        return listaosobyfiltered;
    }

    public void setListaosobyfiltered(List<Uczestnicy> listaosobyfiltered) {
        this.listaosobyfiltered = listaosobyfiltered;
    }

    
    
    
}
