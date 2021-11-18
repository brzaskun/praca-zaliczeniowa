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
import kadryiplace.OsobaDet;
import kadryiplace.OsobaPropTyp;
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
    private List<Firma> firmysuperplace;
    private List<Osoba> osoby;
    private List<Osoba> selectedosoby;
    private Firma selectedfirma;
    @Inject
    private OsobaFacade osobaFacade;
    @Inject
    private FirmaFacade firmaFacade;
    @Inject
    private dao.FirmaKadryFacade firmaKadryFacade;
    
    @PostConstruct
    public void init() {
        firmysuperplace = firmaFacade.findAll();
        List<FirmaKadry> nowefirmy = firmaKadryFacade.findAll();
        if (firmysuperplace!=null&&nowefirmy!=null) {
            for (FirmaKadry f : nowefirmy) {
                Firma odnaleziona = firmysuperplace.stream().filter(p->ladnynip(p.getFirNip()).equals(f.getNip())).findFirst().orElse(null);
                if (odnaleziona!=null) {
                    odnaleziona.setZaimportowana(f.isZaimportowana());
                }
            }
        }
        listapracownikow = angazFacade.findByFirma(wpisView.getFirma());
        if (selectedangaz!=null) {
            listawynagrodzenhistoria = wynagrodzeniahistoryczneFacade.findByAngaz(selectedangaz);
        }
    }
    
    private String ladnynip(String firNip) {
        String zwrot = firNip;
        if (zwrot!=null) {
            zwrot = firNip.replace("-","");
        }
        return zwrot;
    }
    
    public void pobierzosoby() {
        osoby = osobaFacade.findByFirmaSerial(selectedfirma.getFirSerial());
        for (Osoba o : osoby) {
            for (Angaz a : listapracownikow) {
                if (a.getSerialsp()!=null) {
                    if (o.getOsoSerial().equals(Integer.parseInt(a.getSerialsp()))) {
                        o.setOsoDodVchar3("tak");
                        break;
                    }
                }
            }
        }
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
        if (selectedosoby!=null) {
            Msg.msg("Pobrano pracownika do edycji");
        }
    }
     
    public String zwrotkolor(Osoba osoba) {
        String zwrot = "color: initial";
        if (osoba.getOsoDataZwol()!=null) {
            boolean czyjestpozniej = Data.czyjestpo(osoba.getOsoDataZwol(), wpisView);
            if (czyjestpozniej==false) {
                zwrot = "color: grey";
            }
        }
        return zwrot;
    }

    public void naniesimport(kadryiplace.Firma firmasp) {
        if (firmasp!=null) {
            entity.FirmaKadry firmakadry = firmaKadryFacade.findByNIP(ladnynip(firmasp.getFirNip()));
            if (firmakadry!=null) {
                firmakadry.setZaimportowana(firmasp.isZaimportowana());
                firmaKadryFacade.edit(firmakadry);
            }
            Msg.msg("Naniesiono zmiany");
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

    public List<Osoba> getSelectedosoby() {
        return selectedosoby;
    }

    public void setSelectedosoby(List<Osoba> selectedosoby) {
        this.selectedosoby = selectedosoby;
    }

   

    public List<Firma> getFirmysuperplace() {
        return firmysuperplace;
    }

    public void setFirmysuperplace(List<Firma> firmysuperplace) {
        this.firmysuperplace = firmysuperplace;
    }

    public Firma getSelectedfirma() {
        return selectedfirma;
    }

    public void setSelectedfirma(Firma selectedfirma) {
        this.selectedfirma = selectedfirma;
    }

    public String historycznykodumowy(Osoba osoba) {
        String zwrot = null;
        List<OsobaDet> osobaDetList = osoba.getOsobaDetList();
        if (!osobaDetList.isEmpty()) {
            for (OsobaDet p : osobaDetList) {
                OsobaPropTyp osdOptSerial = p.getOsdOptSerial();
                zwrot = p.getOsdWktSerial().getWktKod();
            }
        }
        return zwrot;
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
