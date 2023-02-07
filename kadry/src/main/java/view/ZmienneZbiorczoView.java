/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Pracownikcomparator;
import dao.PracownikFacade;
import dao.RodzajwynagrodzeniaFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import data.Data;
import entity.Angaz;
import entity.Pracownik;
import entity.Rodzajwynagrodzenia;
import entity.Skladnikwynagrodzenia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ZmienneZbiorczoView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade;
    @Inject
    private RodzajwynagrodzeniaFacade rodzajwynagrodzeniaFacade;
    @Inject
    private PracownikFacade pracownikFacade;
    private List<Rodzajwynagrodzenia> rodzajewynagrodzen;
    private org.primefaces.model.DualListModel<Pracownik> listapracownikow;
    private Rodzajwynagrodzenia wybranyrodzaj;
    private String dataod;
    private String datado;
    private double kwota;
    
    @PostConstruct
    private void init() {
        List<Angaz> angazList = wpisView.getFirma().getAngazListAktywne();
        List<Pracownik> listapracownikow = pobierzpracownikow (angazList);
        Collections.sort(listapracownikow, new Pracownikcomparator());
        if (listapracownikow != null) {
            this.listapracownikow = new org.primefaces.model.DualListModel<>();
            this.listapracownikow.setSource(listapracownikow);
            this.listapracownikow.setTarget(new ArrayList<>());
        }
        dataod = Data.dzienpierwszy(wpisView);
        datado = Data.ostatniDzien(wpisView);
        rodzajewynagrodzen = pobierzrodzajewyn(angazList);
    }
    
    private List<Pracownik> pobierzpracownikow(List<Angaz> angazList) {
        Set<Pracownik> zwrot = new HashSet<>();
        for (Angaz a : angazList) {
            zwrot.add(a.getPracownik());
        }
        return new ArrayList<Pracownik>(zwrot);
    }
    
     private List<Rodzajwynagrodzenia> pobierzrodzajewyn(List<Angaz> angazList) {
        Set<Rodzajwynagrodzenia> rodzajeset = new HashSet<>();
        for (Angaz p : angazList) {
            List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList = p.getSkladnikwynagrodzeniaList();
            if (skladnikwynagrodzeniaList!=null) {
                for (Skladnikwynagrodzenia skl : skladnikwynagrodzeniaList) {
                    rodzajeset.add(skl.getRodzajwynagrodzenia());
                }
            }
            
        }
        return new ArrayList<>(rodzajeset);
    }
    

     public void createzbiorczo() {
         Msg.msg("e", "Funkcja jeszcze nie zaimplementowana");
     }
     
    public DualListModel<Pracownik> getListapracownikow() {
        return listapracownikow;
    }

    public void setListapracownikow(DualListModel<Pracownik> listapracownikow) {
        this.listapracownikow = listapracownikow;
    }

    public Rodzajwynagrodzenia getWybranyrodzaj() {
        return wybranyrodzaj;
    }

    public void setWybranyrodzaj(Rodzajwynagrodzenia wybranyrodzaj) {
        this.wybranyrodzaj = wybranyrodzaj;
    }

    public List<Rodzajwynagrodzenia> getRodzajewynagrodzen() {
        return rodzajewynagrodzen;
    }

    public void setRodzajewynagrodzen(List<Rodzajwynagrodzenia> rodzajewynagrodzen) {
        this.rodzajewynagrodzen = rodzajewynagrodzen;
    }

    public String getDataod() {
        return dataod;
    }

    public void setDataod(String dataod) {
        this.dataod = dataod;
    }

    public String getDatado() {
        return datado;
    }

    public void setDatado(String datado) {
        this.datado = datado;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

   
    
    
}
