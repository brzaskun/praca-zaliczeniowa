/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DAOsuperplace.FirmaFacade;
import DAOsuperplace.OsobaFacade;
import dao.AngazFacade;
import dao.PracownikFacade;
import dao.UmowaFacade;
import dao.WynagrodzeniahistoryczneFacade;
import data.Data;
import entity.Angaz;
import entity.FirmaKadry;
import entity.Umowa;
import entity.Wynagrodzeniahistoryczne;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import kadryiplace.Firma;
import kadryiplace.Osoba;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class HistoriaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Angaz> listapracownikow;
    private List<Wynagrodzeniahistoryczne> listawynagrodzenhistoria;
    private Wynagrodzeniahistoryczne selectedlista;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private WynagrodzeniahistoryczneFacade wynagrodzeniahistoryczneFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private UmowaFacade umowaFacade;
    private Angaz selectedangaz;
    @Inject
    private WpisView wpisView;
    private List<Firma> firmy;
    private List<Osoba> osoby;
    private Osoba selectedosoba;
    private Firma selectedfirma;
    @Inject
    private OsobaFacade osobaFacade;
     @Inject
    private FirmaFacade firmaFacade;
    
    @PostConstruct
    public void init() {
        firmy = firmaFacade.findAll();
        listapracownikow = angazFacade.findByFirma(wpisView.getFirma());
        if (selectedangaz!=null) {
            listawynagrodzenhistoria = wynagrodzeniahistoryczneFacade.findByAngaz(selectedangaz);
        }
    }
    
    public void pobierzosoby() {
        osoby = osobaFacade.findByFirmaSerial(selectedfirma.getFirSerial());
    }
    
    public void aktywuj(FirmaKadry firma) {
        if (firma!=null) {
            wpisView.setFirma(firma);
            if (firma.getAngazList()==null||firma.getAngazList().isEmpty()) {
                wpisView.setPracownik(null);
                wpisView.setAngaz(null);
                wpisView.setUmowa(null);
            }
            init();
            Msg.msg("Aktywowano firmę "+firma.getNazwa());
        }
    }
    
    public void zachowaj() {
        wynagrodzeniahistoryczneFacade.editList(listawynagrodzenhistoria);
        Msg.msg("Zmiany zachowane");
    }
    
    public void pobierzhistorie() {
        if (selectedangaz!=null) {
            listawynagrodzenhistoria = wynagrodzeniahistoryczneFacade.findByAngaz(selectedangaz);
            if (listawynagrodzenhistoria.isEmpty()) {
                generujlistawynhist();
            }
        }
    }
    
     public void pobierzinfo() {
        if (selectedosoba!=null) {
            Msg.msg("Pobrano pracownika do edycji");
        }
    }

    public List<Angaz> getListapracownikow() {
        return listapracownikow;
    }

    public void setListapracownikow(List<Angaz> listapracownikow) {
        this.listapracownikow = listapracownikow;
    }

    public Angaz getSelectedangaz() {
        return selectedangaz;
    }

    public void setSelectedangaz(Angaz selectedangaz) {
        this.selectedangaz = selectedangaz;
    }

    public List<Wynagrodzeniahistoryczne> getListawynagrodzenhistoria() {
        return listawynagrodzenhistoria;
    }

    public void setListawynagrodzenhistoria(List<Wynagrodzeniahistoryczne> listawynagrodzenhistoria) {
        this.listawynagrodzenhistoria = listawynagrodzenhistoria;
    }

    public List<Osoba> getOsoby() {
        return osoby;
    }

    public void setOsoby(List<Osoba> osoby) {
        this.osoby = osoby;
    }

    public Osoba getSelectedosoba() {
        return selectedosoba;
    }

    public void setSelectedosoba(Osoba selectedosoba) {
        this.selectedosoba = selectedosoba;
    }

    public List<Firma> getFirmy() {
        return firmy;
    }

    public void setFirmy(List<Firma> firmy) {
        this.firmy = firmy;
    }

    public Firma getSelectedfirma() {
        return selectedfirma;
    }

    public void setSelectedfirma(Firma selectedfirma) {
        this.selectedfirma = selectedfirma;
    }

    
    
    
    private void generujlistawynhist() {
        //to sa dni 2020
        Integer[] dni = {21,20,22,21,20,21,23,20,22,22,20,21};
        listawynagrodzenhistoria = new ArrayList<>();
        List<Umowa> umowy = selectedangaz.getUmowaList();
        if (umowy!=null && umowy.size()>0) {
            Umowa umowa = pobierzaktywna(umowy);
            String[] poprzedniOkres = Data.poprzedniOkres("01", "2021");
            for (int i=11;i>=0;i--) {
                Wynagrodzeniahistoryczne wynagrodzeniahistoryczne = new Wynagrodzeniahistoryczne(selectedangaz, poprzedniOkres);
                wynagrodzeniahistoryczne.setDniobowiazku(dni[i]);
                listawynagrodzenhistoria.add(wynagrodzeniahistoryczne);
                poprzedniOkres = Data.poprzedniOkres(poprzedniOkres[0], poprzedniOkres[1]);
            }
         } else {
            Msg.msg("e", "Pracownik bez umowy! Nie można generować historii");
        }
        
    }
    
    private Umowa pobierzaktywna(List<Umowa> umowy) {
        Umowa zwrot = umowy.get(0);
        for (Umowa p : umowy) {
            if (p.isAktywna()) {
                zwrot = p;
            }
        }
        return zwrot;
    }

    public Wynagrodzeniahistoryczne getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Wynagrodzeniahistoryczne selectedlista) {
        this.selectedlista = selectedlista;
    }

    

   

    
}
