/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dziencomparator;
import comparator.Umowacomparator;
import dao.AngazFacade;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.UmowaFacade;
import embeddable.StanZatrudnienia;
import entity.Angaz;
import entity.Dzien;
import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
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
public class WskaznikPfronView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private double wskaznik;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    private List<StanZatrudnienia> lista;
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;
    private List<Angaz> listaeast2;
    private Angaz selectedeast;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private AngazFacade angazFacade;
    private double razemetaty;
    private double iloscdni;
    private double sredniamiesieczna;
    
    @PostConstruct
    private void init() {
        listaeast2 = angazFacade.findByFirmaAktywni(wpisView.getFirma());
        lista = new ArrayList<>();
        List<Kalendarzmiesiac> pracownicy = kalendarzmiesiacFacade.findByFirmaRokMc(wpisView.getFirma(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        Kalendarzwzor kalendarzsumaryczny = kalendarzwzorFacade.findByFirmaRokMc(wpisView.getFirma(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        sumujkalendarze(kalendarzsumaryczny, pracownicy);
    }
    
    private void sumujkalendarze(Kalendarzwzor kalendarzsumaryczny, List<Kalendarzmiesiac> pracownicy) {
        razemetaty = 0.0;
        iloscdni = 0.0;
        sredniamiesieczna = 0.0;
        List<Dzien> dzienList = kalendarzsumaryczny.getDzienList();
        Collections.sort(dzienList, new Dziencomparator());
        for (Dzien dzien : kalendarzsumaryczny.getDzienList()) {
            StanZatrudnienia stannadzien = new StanZatrudnienia();
            double etatyrazem = 0.0;
            double etatykobiety = 0.0;
            double etatymezczyzni = 0.0;
            double osobyrazem = 0.0;
            double kobiety = 0.0;
            double mezczyzni = 0.0;
            for (Kalendarzmiesiac kalendarz : pracownicy) {
                Dzien dzienpracownika = kalendarz.getDzień(dzien.getNrdnia());
                if (dzienpracownika.getKod()==null||(dzienpracownika.getKod()!=null&&!dzienpracownika.getKod().equals("WY")&&!dzienpracownika.getKod().equals("UM")&&!dzienpracownika.getKod().equals("UM"))) {
                    Pracownik pracownik = kalendarz.getPracownik();
                    double etat1 = (double) dzienpracownika.getEtat1();
                    double etat2 = (double) dzienpracownika.getEtat2();
                    double etatdecym = etat1/etat2;
                    boolean mezczyzna = pracownik.isMezczyzna();
                    if (mezczyzna) {
                        osobyrazem = osobyrazem + 1;
                        mezczyzni = mezczyzni + 1;
                        etatyrazem = etatyrazem + etatdecym;
                        etatymezczyzni = etatymezczyzni + etatdecym;
                    } else {
                        osobyrazem = osobyrazem + 1;
                        kobiety = kobiety + 1;
                        etatyrazem = etatyrazem + etatdecym;
                        etatykobiety = etatykobiety + etatdecym;
                    }
                }
            }
            stannadzien.setDzien(dzien.getDatastring());
            stannadzien.setOsobyrazem(osobyrazem);
            stannadzien.setMezczyzni(mezczyzni);
            stannadzien.setKobiety(kobiety);
            stannadzien.setEtatyrazem(etatyrazem);
            stannadzien.setEtatymezczyzni(etatymezczyzni);
            stannadzien.setEtatykobiety(etatykobiety);
            lista.add(stannadzien);
            razemetaty = razemetaty+stannadzien.getEtatyrazem();
            iloscdni = kalendarzsumaryczny.getDzienList().size();
            sredniamiesieczna = razemetaty/iloscdni;
        }
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
            List<Umowa> umowy = wpisView.getAngaz().getUmowaList();
            if (umowy==null) {
                try {
                    umowy = umowaFacade.findByAngaz(angaz);
                } catch (Exception ex){}
            }
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
    
    public double getWskaznik() {
        return wskaznik;
    }

    public void setWskaznik(double wskaznik) {
        this.wskaznik = wskaznik;
    }

    public List<StanZatrudnienia> getLista() {
        return lista;
    }

    public void setLista(List<StanZatrudnienia> lista) {
        this.lista = lista;
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

    public double getRazemetaty() {
        return razemetaty;
    }

    public void setRazemetaty(double razemetaty) {
        this.razemetaty = razemetaty;
    }

    public double getIloscdni() {
        return iloscdni;
    }

    public void setIloscdni(double iloscdni) {
        this.iloscdni = iloscdni;
    }

    public double getSredniamiesieczna() {
        return sredniamiesieczna;
    }

    public void setSredniamiesieczna(double sredniamiesieczna) {
        this.sredniamiesieczna = sredniamiesieczna;
    }

    
    
    
    
}
