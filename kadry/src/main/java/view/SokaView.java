/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dziencomparator;
import comparator.Pasekwynagrodzencomparator;
import comparator.Umowacomparator;
import dao.AngazFacade;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.PasekwynagrodzenFacade;
import dao.UmowaFacade;
import embeddable.Soka;
import embeddable.StanZatrudnienia;
import entity.Angaz;
import entity.Dzien;
import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import entity.Naliczenienieobecnosc;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Nieobecnosc;
import entity.Pasekwynagrodzen;
import entity.Pracownik;
import entity.Umowa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class SokaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
     @Inject
    private UmowaFacade umowaFacade;
    private List<Soka> lista;
    private List<Angaz> listaeast2;
    private Angaz selectedeast;
    @Inject
    private AngazFacade angazFacade;

    
    @PostConstruct
    private void init() {
        listaeast2 = angazFacade.findByFirmaAktywni(wpisView.getFirma());
        lista = new ArrayList<>();
        List<Pasekwynagrodzen> paski = pasekwynagrodzenFacade.findByRokMcFirma(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu(),wpisView.getFirma());
        sumujpaski(paski);
    }
    
    private void sumujpaski( List<Pasekwynagrodzen> paski) {
        Collections.sort(paski, new Pasekwynagrodzencomparator());
        for (Pasekwynagrodzen pasek : paski) {
            List<Naliczenienieobecnosc> naliczenienieobecnoscList = pasek.getNaliczenienieobecnoscList();
            for (Naliczenienieobecnosc nal : naliczenienieobecnoscList) {
                if (nal.getNieobecnosc().getRodzajnieobecnosci().getKod().equals("Z")||nal.getNieobecnosc().getRodzajnieobecnosci().getKod().equals("UD")) {
                    lista.add(new Soka(nal));
                }
            }
            Kalendarzmiesiac kalendarzmiesiac = pasek.getKalendarzmiesiac();
            List<Dzien> dzienList = kalendarzmiesiac.getDzienList();
            List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList = pasek.getNaliczenieskladnikawynagrodzeniaList();
            Naliczenieskladnikawynagrodzenia oddelegowanie = pobierzoddelegowanie(naliczenieskladnikawynagrodzeniaList);
            Nieobecnosc nieobecnoscaccu = null;
            String dataod = null;
            String datado = null;
            for (Dzien dzien : dzienList) {
                Nieobecnosc dziennieob = dzien.getNieobecnosc();
                if (dziennieob!=null&&dziennieob.getRodzajnieobecnosci().getKod().equals("Z")&&nieobecnoscaccu==null) {
                    nieobecnoscaccu = dziennieob;
                }
                if (nieobecnoscaccu!=null&&nieobecnoscaccu.equals(dziennieob)) {
                    if (dataod==null) {
                        dataod = dzien.getDatastring();
                    }
                    datado = dzien.getDatastring();
                }
                if ((dziennieob==null||dziennieob!=null&&!dziennieob.getRodzajnieobecnosci().getKod().equals("Z"))&&nieobecnoscaccu!=null) {
                    lista.add(new Soka(nieobecnoscaccu, dataod,datado, oddelegowanie));
                    nieobecnoscaccu = null;
                    dataod = null;
                    datado = null;
                }
                if (dziennieob!=null&&nieobecnoscaccu!=null&&dzien.getTypdnia()==-1) {
                    lista.add(new Soka(nieobecnoscaccu, dataod,datado, oddelegowanie));
                    nieobecnoscaccu = null;
                    dataod = null;
                    datado = null;
                }
                if (dziennieob!=null&&nieobecnoscaccu!=null&&dzien.getNrdnia()==31) {
                    lista.add(new Soka(nieobecnoscaccu, dataod,datado, oddelegowanie));
                    nieobecnoscaccu = null;
                    dataod = null;
                    datado = null;
                }
            }
            
        }
    }

    
    private Naliczenieskladnikawynagrodzenia pobierzoddelegowanie(List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList) {
        Naliczenieskladnikawynagrodzenia zwrot = null;
        for (Naliczenieskladnikawynagrodzenia skl : naliczenieskladnikawynagrodzeniaList) {
            if (skl.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getWks_serial()==1064) {
                zwrot = skl;
            }
        }
        return zwrot;
    }

    public void aktywujPracAngaze(FirmaKadry firma) {
        if (firma!=null) {
            wpisView.setFirma(firma);
            if (firma.getAngazList()==null||firma.getAngazList().isEmpty()) {
                wpisView.setPracownik(null);
                wpisView.setAngaz(null);
                wpisView.setUmowa(null);
            } else {
                Angaz angaz = firma.getAngazList().get(0);
                wpisView.setPracownik(angaz.getPracownik());
                wpisView.setAngaz(angaz);
                List<Umowa> umowy = angaz.getUmowaList();
                if (umowy!=null && umowy.size()==1) {
                    wpisView.setUmowa(umowy.get(0));
                } else if (umowy!=null) {
                    try {
                        wpisView.setUmowa(umowy.stream().filter(p->p.isAktywna()).findFirst().get());
                    } catch (Exception e){}
                }
            }
            init();
            Msg.msg("Aktywowano firmę "+firma.getNazwa());
        }
    }
    
    public void aktywuj(Angaz angaz) {
        if (angaz!=null) {
            wpisView.setAngaz(angaz);
            wpisView.setPracownik(angaz.getPracownik());
            List<Umowa> umowy = umowaFacade.findByAngaz(angaz);
            if (umowy!=null && umowy.size()==1) {
                wpisView.setUmowa(umowy.get(0));
            } else if (umowy!=null&&!umowy.isEmpty()) {
                Umowa umowaaktywna = null;
                Optional badanie  = umowy.stream().filter(p->p.isAktywna()).findFirst();
                if (badanie.isPresent()) {
                    umowaaktywna = (Umowa) badanie.get();
                }
                if (umowaaktywna==null) {
                    Collections.sort(umowy, new Umowacomparator());
                    umowaaktywna = umowy.get(0);
                    umowaaktywna.setAktywna(true);
                    umowaFacade.edit(umowaaktywna);
                }
                wpisView.setUmowa(umowaaktywna);
            } else {
                Msg.msg("e","Nie ma żadnej umowy do angażu");
                wpisView.setUmowa(null);
                System.out.println("Nie pobrano umów do angażu");
            }
            init();
            Msg.msg("Aktywowano pracownika");
        }
    }
    
   
    public List<Angaz> getListaeast2() {
        return listaeast2;
    }

    public void setListaeast2(List<Angaz> listaeast2) {
        this.listaeast2 = listaeast2;
    }

    public Angaz getSelectedeast() {
        return selectedeast;
    }

    public void setSelectedeast(Angaz selectedeast) {
        this.selectedeast = selectedeast;
    }

    public List<Soka> getLista() {
        return lista;
    }

    public void setLista(List<Soka> lista) {
        this.lista = lista;
    }

    
  
    
    
    
    
}
