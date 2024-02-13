/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.WierszFakturaBean;
import comparator.FirmaKadrycomparator;
import comparator.Kadryfakturapozycjacomparator;
import dao.FakturaopisuslugiFacade;
import dao.FirmaKadryFacade;
import dao.KadryfakturapozycjaFacade;
import dao.PasekwynagrodzenFacade;
import dao.WalutyFacade;
import dao.WierszFakturyFacade;
import entity.Fakturaopisuslugi;
import entity.FirmaKadry;
import entity.Kadryfakturapozycja;
import entity.Pasekwynagrodzen;
import entity.Waluty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import webservice.WierszFaktury;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class KadryfakturapozycjaView  implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private List<Kadryfakturapozycja> listauslugklientcena;
    private List<WierszFaktury> listawierszfaktury;
    @Inject
    private KadryfakturapozycjaFacade kadryfakturapozycjaFacade;
    @Inject
    private Kadryfakturapozycja selected;
    @Inject
    private WalutyFacade walutyFacade;
    @Inject
    private WierszFakturyFacade wierszFakturyFacade;
    private List<FirmaKadry> listafirm;
    private List<Fakturaopisuslugi> listauslug;
    private List<Waluty> listawaluty;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    @Inject
    private FirmaKadryFacade firmaKadryFacade;
     @Inject
    private FakturaopisuslugiFacade fakturaopisuslugiFacade;
    private String rok;
    private String mc;
    
    @PostConstruct
    public void init() {
        listauslugklientcena = kadryfakturapozycjaFacade.findByRok(wpisView.getRokWpisu());
        Collections.sort(listauslugklientcena, new Kadryfakturapozycjacomparator());
        if (listauslugklientcena ==null) {
            listauslugklientcena = new ArrayList<>();
        }
        listafirm = firmaKadryFacade.findAll();
        Collections.sort(listafirm,new FirmaKadrycomparator());
        listauslug = fakturaopisuslugiFacade.findAll();
        listawaluty = walutyFacade.findAll();
        selected.setWaluta(listawaluty.stream().filter(p->p.getSymbolwaluty().equals("PLN")).findFirst().get());
        rok = wpisView.getRokWpisu();
        mc = wpisView.getMiesiacWpisu();
    }

    public void dodaj() {
        if (selected.getFirmakadry()!=null) {
            try {
                selected.setUz(wpisView.getUzer());
                selected.setDatadodania(new Date());
                selected.setRok(wpisView.getRokWpisu());
                kadryfakturapozycjaFacade.create(selected);
                listauslugklientcena.add(selected);
                Waluty waluta = selected.getWaluta();
                selected = new Kadryfakturapozycja();
                selected.setWaluta(waluta);
                Msg.msg("Wprowadzono nową pozycję");
            } catch (Exception e) {
                Msg.msg("e","Taka pozycja już istnieje");
            }
        } else {
            Msg.msg("e","Wystąpił błąd");
        }
    }
    
     public void usun(Kadryfakturapozycja selected) {
        if (selected!=null) {
            kadryfakturapozycjaFacade.remove(selected);
            listauslugklientcena.remove(selected);
            Msg.msg("Usunięto pozycję");
            
        }
     }
     
     public void usunwiersz(WierszFaktury selected) {
        if (selected!=null) {
            wierszFakturyFacade.remove(selected);
            listawierszfaktury.remove(selected);
            Msg.msg("Usunięto pozycję");
            
        }
     }
     
     public void edytuj(Kadryfakturapozycja selected) {
        if (selected!=null&&selected.getCena()!=0) {
            selected.setDatadodania(new Date());
            kadryfakturapozycjaFacade.edit(selected);
            Msg.msg("Zmieniono pozycję");
            
        }
     }
     
     
     public void pobierz () {
         listawierszfaktury = wierszFakturyFacade.findbyRokMc(rok, mc);
         if (listauslugklientcena.isEmpty()==false) {
             przetworzuslugiwmiesiacu();
         }
         if (listawierszfaktury.isEmpty()) {
             Msg.msg("w","Brak pozycji w wybranym miesiącu");
         } else {
             wierszFakturyFacade.editList(listawierszfaktury);
             Msg.msg("Pobrano pozycje");
         }
     }

    private void przetworzuslugiwmiesiacu() {
        for (Kadryfakturapozycja k : listauslugklientcena) {
            List<Pasekwynagrodzen> paski = pasekwynagrodzenFacade.findByRokMcNip(rok, mc, k.getFirmakadry().getNip());
            WierszFaktury wierszpobrany = WierszFakturaBean.pobierzwiersz(listawierszfaktury, k, rok, mc);
            WierszFakturaBean.naniesusluge (wierszpobrany, k.getOpisuslugi(), paski);
            if (wierszpobrany.getIlosc()>0&&wierszpobrany.getId()==null) {
                listawierszfaktury.add(wierszpobrany);
                wierszFakturyFacade.create(wierszpobrany);
            } else if (wierszpobrany.getId()!=null) {
                wierszFakturyFacade.edit(wierszpobrany);
            }
            System.out.println(k.getOpisuslugi().getClass()+" "+wierszpobrany.getIlosc());
        }
    }
    
   

     
    public List<Kadryfakturapozycja> getListauslugklientcena() {
        return listauslugklientcena;
    }

    public void setListauslugklientcena(List<Kadryfakturapozycja> listauslugklientcena) {
        this.listauslugklientcena = listauslugklientcena;
    }

    public Kadryfakturapozycja getSelected() {
        return selected;
    }

    public void setSelected(Kadryfakturapozycja selected) {
        this.selected = selected;
    }

    public List<FirmaKadry> getListafirm() {
        return listafirm;
    }

    public void setListafirm(List<FirmaKadry> listafirm) {
        this.listafirm = listafirm;
    }

    public List<Fakturaopisuslugi> getListauslug() {
        return listauslug;
    }

    public void setListauslug(List<Fakturaopisuslugi> listauslug) {
        this.listauslug = listauslug;
    }

    public List<Waluty> getListawaluty() {
        return listawaluty;
    }

    public void setListawaluty(List<Waluty> listawaluty) {
        this.listawaluty = listawaluty;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public List<WierszFaktury> getListawierszfaktury() {
        return listawierszfaktury;
    }

    public void setListawierszfaktury(List<WierszFaktury> listawierszfaktury) {
        this.listawierszfaktury = listawierszfaktury;
    }

    

    
   
    

    
}
