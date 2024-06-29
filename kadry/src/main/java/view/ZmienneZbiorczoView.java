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
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
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
    private String nazwazmiennej;
    private String walutazmiennej;
    private boolean netto0brutto1;
    private  boolean minimalneustatowe;
    
    
    public void init() {
        angazList = wpisView.getFirma().getAngazListAktywne();
        for (Iterator<Angaz> it = angazList.iterator(); it.hasNext();) {
                Angaz angaz = it.next();
                if (angaz.jestumowaAktywna(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu())==false) {
                    it.remove();
                }
            }
        List<Pracownik> listapracownikow = pobierzpracownikow ();
        Collections.sort(listapracownikow, new Pracownikcomparator());
        dataod = Data.dzienpierwszy(wpisView);
        datado = Data.ostatniDzien(wpisView);
        rodzajewynagrodzen = pobierzrodzajewyn();
        netto0brutto1 = true;
    }
    
    public void close() {
        nazwazmiennej = null;
        walutazmiennej = null;
        pracownikzmienna = null;
        wybranyrodzaj = null;
        
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
                 Predicate<Skladnikwynagrodzenia> isQualified = item->(item.getRodzajwynagrodzenia().equals(wybranyrodzaj));
                 skladnikiangaz.removeIf(isQualified.negate());
                    List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList = skladnikiangaz;
                    if (skladnikwynagrodzeniaList!=null) {
                        for (Skladnikwynagrodzenia skl : skladnikwynagrodzeniaList) {
                            Zmiennawynagrodzenia ostatniaZmienna = skl.getOstatniaZmienna();
                            if (skl.getRodzajwynagrodzenia().equals(wybranyrodzaj) && ostatniaZmienna!=null && ostatniaZmienna.getWaluta().equals(walutazmiennej) && !ostatniaZmienna.getDataod().equals(dataod)) {
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
                                zmiennawynagrodzenia.setNetto0brutto1(ostatniaZmienna.isNetto0brutto1());
                                zmiennawynagrodzenia.setMinimalneustatowe(ostatniaZmienna.isMinimalneustatowe());
                                ostatniaZmienna.setAktywna(false);
                                ostatniaZmienna.setDatado(Data.odejmijdni(dataod, 1));
                                zmiennaZbiorcze.setOstatniazmienna(ostatniaZmienna);
                                zmiennaZbiorcze.setZmienna(zmiennawynagrodzenia);
                                
                                pracownikzmienna.add(zmiennaZbiorcze);
                            } else if (skl.getRodzajwynagrodzenia().equals(wybranyrodzaj) && ostatniaZmienna==null ) {
                                ZmiennaZbiorcze zmiennaZbiorcze = new ZmiennaZbiorcze();
                                zmiennaZbiorcze.setPracownik(p.getPracownik());
                                Zmiennawynagrodzenia zmiennawynagrodzenia = new Zmiennawynagrodzenia();
                                zmiennawynagrodzenia.setSkladnikwynagrodzenia(skl);
                                zmiennawynagrodzenia.setDataod(dataod);
                                zmiennawynagrodzenia.setDatado(datado);
                                zmiennawynagrodzenia.setNazwa(nazwazmiennej);
                                zmiennawynagrodzenia.setNrkolejnyzmiennej(1);
                                zmiennawynagrodzenia.setWaluta(walutazmiennej);
                                zmiennawynagrodzenia.setAktywna(true);
                                zmiennawynagrodzenia.setNetto0brutto1(netto0brutto1);
                                zmiennawynagrodzenia.setMinimalneustatowe(minimalneustatowe);
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
                        p.getZmienna().setDatadodania(new Date());
                        p.getZmienna().setUtworzyl(wpisView.getUzer().getImieNazwisko());
                        zmiennaWynagrodzeniaFacade.create(p.getZmienna());
                        if (p.getOstatniazmienna()!=null) {
                            zmiennaWynagrodzeniaFacade.edit(p.getOstatniazmienna());
                        }
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

    public String getNazwazmiennej() {
        return nazwazmiennej;
    }

    public void setNazwazmiennej(String nazwazmiennej) {
        this.nazwazmiennej = nazwazmiennej;
    }

    public String getWalutazmiennej() {
        return walutazmiennej;
    }

    public void setWalutazmiennej(String walutazmiennej) {
        this.walutazmiennej = walutazmiennej;
    }

    public boolean isNetto0brutto1() {
        return netto0brutto1;
    }

    public void setNetto0brutto1(boolean netto0brutto1) {
        this.netto0brutto1 = netto0brutto1;
    }

    public boolean isMinimalneustatowe() {
        return minimalneustatowe;
    }

    public void setMinimalneustatowe(boolean minimalneustatowe) {
        this.minimalneustatowe = minimalneustatowe;
    }

    
    
    
}
