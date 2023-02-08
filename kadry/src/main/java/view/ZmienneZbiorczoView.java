/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Pracownikcomparator;
import dao.SkladnikWynagrodzeniaFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import data.Data;
import embeddable.ZmiennaZbiorcze;
import entity.Angaz;
import entity.Pracownik;
import entity.Rodzajwynagrodzenia;
import entity.Skladnikwynagrodzenia;
import entity.Zmiennawynagrodzenia;
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
    private SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade;
    private List<Rodzajwynagrodzenia> rodzajewynagrodzen;
    private Rodzajwynagrodzenia wybranyrodzaj;
    private String dataod;
    private String datado;
    private List<ZmiennaZbiorcze> pracownikzmienna;
    private ZmiennaZbiorcze selectedlista;
    private List<Angaz> angazList;
    
    @PostConstruct
    public void init() {
        angazList = wpisView.getFirma().getAngazListAktywne();
        List<Pracownik> listapracownikow = pobierzpracownikow ();
        Collections.sort(listapracownikow, new Pracownikcomparator());
        dataod = Data.dzienpierwszy(wpisView);
        datado = Data.ostatniDzien(wpisView);
        rodzajewynagrodzen = pobierzrodzajewyn();
    }
    
    private List<Pracownik> pobierzpracownikow() {
        Set<Pracownik> zwrot = new HashSet<>();
        for (Angaz a : angazList) {
            zwrot.add(a.getPracownik());
        }
        return new ArrayList<Pracownik>(zwrot);
    }
    
     private List<Rodzajwynagrodzenia> pobierzrodzajewyn() {
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
    
     public void tworzzmienne() {
         pracownikzmienna = new ArrayList<>();
         if (wybranyrodzaj!=null) {
             for (Angaz p : angazList) {
                 List<Skladnikwynagrodzenia> skladnikiangaz = skladnikWynagrodzeniaFacade.findByAngaz(p);
                    List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList = skladnikiangaz;
                    if (skladnikwynagrodzeniaList!=null) {
                        for (Skladnikwynagrodzenia skl : skladnikwynagrodzeniaList) {
                            Zmiennawynagrodzenia ostatniaZmienna = skl.getOstatniaZmienna();
                            if (skl.getRodzajwynagrodzenia().equals(wybranyrodzaj) && ostatniaZmienna!=null && !ostatniaZmienna.getDataod().equals(dataod)) {
                                ZmiennaZbiorcze zmiennaZbiorcze = new ZmiennaZbiorcze();
                                zmiennaZbiorcze.setPracownik(p.getPracownik());
                                Zmiennawynagrodzenia zmiennawynagrodzenia = new Zmiennawynagrodzenia();
                                zmiennawynagrodzenia.setSkladnikwynagrodzenia(skl);
                                zmiennawynagrodzenia.setDataod(dataod);
                                zmiennawynagrodzenia.setDatado(datado);
                                zmiennawynagrodzenia.setNazwa(ostatniaZmienna.getNazwa());
                                zmiennawynagrodzenia.setNrkolejnyzmiennej(ostatniaZmienna.getNrkolejnyzmiennej()+1);
                                zmiennawynagrodzenia.setWaluta(ostatniaZmienna.getWaluta());
                                zmiennawynagrodzenia.setAktywna(true);
                                ostatniaZmienna.setAktywna(false);
                                ostatniaZmienna.setDatado(Data.odejmijdni(dataod, 1));
                                zmiennaZbiorcze.setOstatniazmienna(ostatniaZmienna);
                                zmiennaZbiorcze.setZmienna(zmiennawynagrodzenia);
                                pracownikzmienna.add(zmiennaZbiorcze);
                            }
                        }
                    }

                }
         }
     }

     public void createzbiorczo() {
         if (!pracownikzmienna.isEmpty()) {
             for (ZmiennaZbiorcze p : pracownikzmienna) {
                if (p.getZmienna().getKwota()>0.0) {
                    try {
                        zmiennaWynagrodzeniaFacade.create(p.getZmienna());
                        zmiennaWynagrodzeniaFacade.edit(p.getOstatniazmienna());
                    } catch (Exception e) {
                        Msg.msg("e","Błąd przy nanoszeniu zmiennej "+p.getPracownik().getNazwiskoImie());
                    }
                }
             }
             Msg.msg("Naniesiono zmienne");
         }
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

    public List<ZmiennaZbiorcze> getPracownikzmienna() {
        return pracownikzmienna;
    }

    public void setPracownikzmienna(List<ZmiennaZbiorcze> pracownikzmienna) {
        this.pracownikzmienna = pracownikzmienna;
    }

    public ZmiennaZbiorcze getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(ZmiennaZbiorcze selectedlista) {
        this.selectedlista = selectedlista;
    }

    
    
    
}
