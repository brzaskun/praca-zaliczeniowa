/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansSrodkiTrwale.SrodkiTrwBean;
import com.itextpdf.text.DocumentException;
import comparator.SrodekTrwNowaWartoscComparator;
import comparator.SrodekTrwcomparator;
import comparator.UmorzenieNcomparator;
import dao.STRDAO;
import dao.UmorzenieNDAO;
import data.Data;
import entity.SrodekTrw;
import entity.SrodekTrw_NowaWartosc;
import entity.Srodkikst;
import entity.UmorzenieN;
import entityfk.Konto;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
 import org.primefaces.PrimeFaces;
import pdf.PdfSTRtabela;
import pdf.PdfSrodekTrwKarta;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class STRTabView implements Serializable {
    private static final long serialVersionUID = 1L;
    private SrodekTrw wybranySrodekTrw;
    private boolean napewnousunac;

    @Inject
    protected STRDAO sTRDAO;
    @Inject
    private SrodekTrw selectedSTR;
    @Inject
    private Srodkikst srodekkategoria;
    @Inject
    private WpisView wpisView;
    @Inject
    private STREwidencja sTREwidencja;
    //tablica obiektów
    private List<SrodekTrw> srodkiTrwaleWyposazenie;
    //tablica obiektw danego klienta
    private List<SrodekTrw> srodkiTrwale;
    private List<SrodekTrw> planUmorzen;
    private List<SrodekTrw> planUmorzen_100;
    private List<SrodekTrw> filteredValues;
    private List<SrodekTrw> posiadane;
    private List<SrodekTrw> posiadane2;
    private List<SrodekTrw> filtrowaneposiadane;
    private double posiadanesumanetto;
    private List<SrodekTrw> sprzedane;
    //tablica obiektów danego klienta z określonego roku i miesiąca
    protected List<SrodekTrw> srodkiZakupRokBiezacy;
    //wyposazenie
    private List<SrodekTrw> wyposazenie;
    //dokumenty amortyzacyjne
    private Konto kontonetto;
    private Konto kontoumorzenie;
   
    @Inject
    private SrodekTrw wybranysrodektrwalyPosiadane;
    @Inject
    private SrodekTrw wybranysrodektrwalyPosiadane2;
    @Inject
    private SrodekTrw edytowanysrodek;
    @Inject
    private SrodekTrw wybranysrodektrwalySprzedane;
    private List<SrodekTrw> listaWyposazenia;
    /**
     * Dane informacyjne gora strony srodkitablica.xhtml
     */
    private int iloscsrodkow;
    private int zakupionewbiezacyrok;
    //zmiana wartosci srodka trwalego
    private String datazmiany;
    private double kwotazmiany;
    private boolean bezcalkowicieumorzonych;
    private boolean zmienilasiedataumorzenia;
    private boolean zmienilasiekwotaumorzenia;
    private double umplan_zakupnetto;
    private double umplan_umpocz;
    private double umplan_narast;
    private double umplan_wartoscnetto;
    private double umplan_odpisrok;
    private double umplan_odpismc;
    private boolean pokazwnip;
    @Inject
    private UmorzenieNDAO umorzenieNDAO;
    private List<UmorzenieN> umorzeniazamiesiaclista;
    

    public STRTabView() {
        ustawTabele();
    }
    
    private void ustawTabele() {
        srodkiTrwaleWyposazenie = Collections.synchronizedList(new ArrayList<>());
        srodkiTrwale = Collections.synchronizedList(new ArrayList<>());
        srodkiZakupRokBiezacy = Collections.synchronizedList(new ArrayList<>());
        planUmorzen = Collections.synchronizedList(new ArrayList<>());
        planUmorzen_100 = Collections.synchronizedList(new ArrayList<>());
        wyposazenie = Collections.synchronizedList(new ArrayList<>());
        posiadane = Collections.synchronizedList(new ArrayList<>());
        posiadane2 = Collections.synchronizedList(new ArrayList<>());
        sprzedane = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    public void init() { //E.m(this);
        umplan_zakupnetto = 0.0;
        umplan_umpocz = 0.0;
        umplan_narast = 0.0;
        umplan_wartoscnetto = 0.0;
        umplan_odpisrok = 0.0;
        umplan_odpismc = 0.0;
        ustawTabele();
        String rokdzisiejszy = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        zakupionewbiezacyrok = 0;
        try {
            if (wpisView.getPodatnikWpisu() != null) {
                List<SrodekTrw> srodkizBazy = Collections.synchronizedList(new ArrayList<>());
                try {
                    srodkizBazy = pobierzsrodki();
                } catch (Exception e) {
                    E.e(e);
                }
                if (!srodkizBazy.isEmpty()) {
                    int i = 1;
                    int j = 1;
                    for (Iterator<SrodekTrw> it = srodkizBazy.iterator(); it.hasNext();) {
                        SrodekTrw srodek = it.next();
                        if (srodek.getRokPrzekazania() > wpisView.getRokWpisu()) {
                            it.remove();
                        } else {
                            srodkiTrwaleWyposazenie.add(srodek);
                            if (srodek.getPodatnik().trim().equals(wpisView.getPodatnikWpisu().trim())) {
                                if (srodek.getTyp() != null && srodek.getTyp().equals("wyposazenie")) {
                                    srodek.setNrsrodka(i++);
                                    wyposazenie.add(srodek);
                                } else {
                                    srodek.setNrsrodka(j++);
                                    if (srodek.getDatazak().substring(0, 4).equals(rokdzisiejszy)) {
                                        zakupionewbiezacyrok++;
                                    }
                                    if (srodek.getPlanumorzen() != null && srodek.getPlanumorzen().size() > 0 && srodek.getStawka() < 100) {
                                        planUmorzen.add(srodek);
                                    }
                                    if (srodek.getNetto().equals(srodek.getUmorzeniepoczatkowe())) {
                                        planUmorzen_100.add(srodek);
                                    } else if (srodek.getPlanumorzen() != null && srodek.getPlanumorzen().size() > 0 && srodek.getStawka() == 100) {
                                        planUmorzen_100.add(srodek);
                                    }
                                    srodkiTrwale.add(srodek);
                                    if (srodek.getDatasprzedazy() == null || srodek.getDatasprzedazy().equals("")) {
                                        if (bezcalkowicieumorzonych && srodek.getStawka()==100) {
                                        } else {
                                            if (srodek.getNetto().doubleValue() != srodek.getUmorzeniepoczatkowe().doubleValue()) {
                                                posiadane2.add(srodek);
                                            }
                                            posiadane.add(srodek);
                                            sumujposiadane(srodek);
                                            posiadanesumanetto += srodek.getNetto();
                                        }
                                    } else if (srodek.getRokSprzedazy() <= wpisView.getRokWpisu()){
                                        sprzedane.add(srodek);
                                    } else {
                                        if (bezcalkowicieumorzonych && srodek.getStawka()==100) {
                                        } else {
                                            if (srodek.getNetto().doubleValue() != srodek.getUmorzeniepoczatkowe().doubleValue()) {
                                                posiadane2.add(srodek);
                                            }
                                            posiadane.add(srodek);
                                            sumujposiadane(srodek);
                                            posiadanesumanetto += srodek.getNetto();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    iloscsrodkow = srodkiTrwale.size();
                }
            }
            umorzeniazamiesiaclista = umorzenieNDAO.findByPodatnikRokMc(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
            sprawdzkonta();
            /**
             * to co bylo w amodok
             */
            //PrimeFaces.current().ajax().update("formSTR");
        } catch (Exception e) {
            E.e(e);
        }
    }

   private List<SrodekTrw> pobierzsrodki() {
        List<SrodekTrw> lista = sTRDAO.findStrPod(wpisView.getPodatnikWpisu());
        if (pokazwnip) {
            for (Iterator<SrodekTrw> it = lista.iterator(); it.hasNext();) {
                SrodekTrw st = it.next();
                if (st.getTyp()!=null && st.getTyp().equals("srodek trw.")) {
                    it.remove();;
                }
            }
        } else {
            for (Iterator<SrodekTrw> it = lista.iterator(); it.hasNext();) {
                SrodekTrw st = it.next();
                if (st.getTyp()!=null && st.getTyp().equals("wnip")) {
                    it.remove();;
                }
            }
        }
        return lista;
    }
    
    public void kopiujdate(SrodekTrw selDok) {
        selDok.setDataprzek(selDok.getDatazak());
    }
    public void destroy(SrodekTrw selDok) {
        wybranySrodekTrw = new SrodekTrw();
        wybranySrodekTrw = selDok;
    }

    public void destroy2() {
        boolean czysazaksiegowaneumorzenia = sprawdzczysaumorzenia(wybranySrodekTrw);
        try {
            if (czysazaksiegowaneumorzenia == true && napewnousunac == false) {
                throw new Exception();
            }
            srodkiTrwale.remove(wybranySrodekTrw);
            posiadane.remove(wybranySrodekTrw);
            posiadane2.remove(wybranySrodekTrw);
            sprzedane.remove(wybranySrodekTrw);
            wyposazenie.remove(wybranySrodekTrw);
            sTRDAO.remove(wybranySrodekTrw);
            Msg.msg("Usunieto środek trwały o nazwie: "+wybranySrodekTrw.getNazwa());
        } catch (Exception e) { 
            E.e(e);
            Msg.msg("e", "Nie usnieto " + wybranySrodekTrw.getNazwa() + ". Umorzenie srodka w ksiegach");
        }
    }
        
    private boolean sprawdzczysaumorzenia(SrodekTrw wybranySrodekTrw) {
        boolean saumorzenia = false;
        for (UmorzenieN p : wybranySrodekTrw.getPlanumorzen()) {
            if (p.getAmodok() != null) {
                saumorzenia = true;
                break;
            }
        }
        return saumorzenia;
    }


    

    public void wycofajsrodek() {
        SrodekTrw p = wybranysrodektrwalyPosiadane;
        p.setZlikwidowany(9);
        p.setStyl("color: blue; font-style:  italic;");
        String data = Data.ostatniDzien(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        p.setDatasprzedazy(data);
        p.setNrwldokumentu("wycofanie");
        int rok = wpisView.getRokWpisu();
        int mc = Integer.parseInt(wpisView.getMiesiacWpisu());
        Double suma = 0.0;
        Double umorzeniesprzedaz = 0.0;
        Collections.sort(p.getPlanumorzen(), new UmorzenieNcomparator());
        for (Iterator<UmorzenieN> it = p.getPlanumorzen().iterator(); it.hasNext(); ) {
            UmorzenieN x = it.next();
            if (x.getRokUmorzenia() < rok) {
                suma += x.getKwota();
            } else if (x.getRokUmorzenia() == rok && x.getMcUmorzenia() < mc) {
                suma += x.getKwota();
            } else if (x.getRokUmorzenia() == rok && x.getMcUmorzenia() == mc) {
                umorzeniesprzedaz = p.getNetto() - p.getUmorzeniepoczatkowe() - suma;
                x.setKwota(0.0);
                p.setKwotaodpislikwidacja(umorzeniesprzedaz);
            } else {
                it.remove();
            }
        }
        try {
            sTRDAO.edit(p);
            posiadane.remove(p);
            sprzedane.add(p);
            Collections.sort(sprzedane, new SrodekTrwcomparator());
            Msg.msg("i", "Naniesiono wycofanie: " + p.getNazwa() + ". Pamiętaj o wygenerowaniu nowych dokumentow umorzeń!");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystapił błąd - nie naniesiono wycofania: " + p.getNazwa());
        }
    }

    public void przywrocsrodki() {
        SrodekTrw p = wybranysrodektrwalySprzedane;
        p.setZlikwidowany(0);
        p.setStyl("color: black;");
        p.setDatasprzedazy("");
        p.setNrwldokumentu("");
        try {
            p.setUmorzPlan(null);
            p.setPlanumorzen(null);
            sTRDAO.edit(p);
            p.setUmorzPlan(SrodkiTrwBean.naliczodpisymczne(p));
            p.setPlanumorzen(SrodkiTrwBean.generujumorzeniadlasrodka(p, wpisView));
            sTRDAO.edit(p);
            posiadane.add(p);
            Collections.sort(posiadane, new SrodekTrwcomparator());
            sprzedane.remove(p);
            Msg.msg("i", "Cofnięto sprzedaż/wycofanie: " + p.getNazwa() + ". Pamiętaj o wygenerowaniu nowych dokumentow umorzeń!");
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e", "Wystapił błąd - nie cofnięto sprzedaży/wycofania: " + p.getNazwa());
        }
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public List<SrodekTrw> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<SrodekTrw> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public List<UmorzenieN> getUmorzeniazamiesiaclista() {
        return umorzeniazamiesiaclista;
    }

    public void setUmorzeniazamiesiaclista(List<UmorzenieN> umorzeniazamiesiaclista) {
        this.umorzeniazamiesiaclista = umorzeniazamiesiaclista;
    }

    public double getUmplan_zakupnetto() {
        return umplan_zakupnetto;
    }

    public void setUmplan_zakupnetto(double umplan_zakupnetto) {
        this.umplan_zakupnetto = umplan_zakupnetto;
    }

    public double getUmplan_umpocz() {
        return umplan_umpocz;
    }

    public void setUmplan_umpocz(double umplan_umpocz) {
        this.umplan_umpocz = umplan_umpocz;
    }

    public double getUmplan_narast() {
        return umplan_narast;
    }

    public void setUmplan_narast(double umplan_narast) {
        this.umplan_narast = umplan_narast;
    }

    public double getUmplan_wartoscnetto() {
        return umplan_wartoscnetto;
    }

    public void setUmplan_wartoscnetto(double umplan_wartoscnetto) {
        this.umplan_wartoscnetto = umplan_wartoscnetto;
    }

    public double getUmplan_odpisrok() {
        return umplan_odpisrok;
    }

    public void setUmplan_odpisrok(double umplan_odpisrok) {
        this.umplan_odpisrok = umplan_odpisrok;
    }

    public double getUmplan_odpismc() {
        return umplan_odpismc;
    }

    public void setUmplan_odpismc(double umplan_odpismc) {
        this.umplan_odpismc = umplan_odpismc;
    }

     public List<SrodekTrw> getListaWyposazenia() {
         return listaWyposazenia;
     }
     
     public void setListaWyposazenia(List<SrodekTrw> listaWyposazenia) {
         this.listaWyposazenia = listaWyposazenia;
     }
    
     
     public STREwidencja getsTREwidencja() {
         return sTREwidencja;
     }
     
     public void setsTREwidencja(STREwidencja sTREwidencja) {
         this.sTREwidencja = sTREwidencja;
     }
     
     public List<SrodekTrw> getFiltrowaneposiadane() {
         return filtrowaneposiadane;
     }
     
     public void setFiltrowaneposiadane(List<SrodekTrw> filtrowaneposiadane) {
         this.filtrowaneposiadane = filtrowaneposiadane;
     }
     
     public List<SrodekTrw> getPlanUmorzen() {
         return planUmorzen;
     }
     
     public void setPlanUmorzen(List<SrodekTrw> planUmorzen) {
         this.planUmorzen = planUmorzen;
     }
     
     public boolean isBezcalkowicieumorzonych() {
         return bezcalkowicieumorzonych;
     }
     
     public void setBezcalkowicieumorzonych(boolean bezcalkowicieumorzonych) {
         this.bezcalkowicieumorzonych = bezcalkowicieumorzonych;
     }
     
     public List<SrodekTrw> getPosiadane2() {
         return posiadane2;
     }
     
     public void setPosiadane2(List<SrodekTrw> posiadane2) {
         this.posiadane2 = posiadane2;
     }
     
     public List<SrodekTrw> getPlanUmorzen_100() {
         return planUmorzen_100;
     }
     
     public void setPlanUmorzen_100(List<SrodekTrw> planUmorzen_100) {
         this.planUmorzen_100 = planUmorzen_100;
     }
     
    public STRDAO getsTRDAO() {
        return sTRDAO;
    }

    public Konto getKontonetto() {
        return kontonetto;
    }

    public void setKontonetto(Konto kontonetto) {
        this.kontonetto = kontonetto;
    }

    public Konto getKontoumorzenie() {
        return kontoumorzenie;
    }

    public void setKontoumorzenie(Konto kontoumorzenie) {
        this.kontoumorzenie = kontoumorzenie;
    }

    public void setsTRDAO(STRDAO sTRDAO) {
        this.sTRDAO = sTRDAO;
    }

    public SrodekTrw getSelectedSTR() {
        return selectedSTR;
    }

    public void setSelectedSTR(SrodekTrw selectedSTR) {
        this.selectedSTR = selectedSTR;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<SrodekTrw> getSrodkiTrwaleWyposazenie() {
        return srodkiTrwaleWyposazenie;
    }

    public void setSrodkiTrwaleWyposazenie(List<SrodekTrw> srodkiTrwaleWyposazenie) {
        this.srodkiTrwaleWyposazenie = srodkiTrwaleWyposazenie;
    }

    public List<SrodekTrw> getSrodkiTrwale() {
        return srodkiTrwale;
    }

    public void setSrodkiTrwale(List<SrodekTrw> srodkiTrwale) {
        this.srodkiTrwale = srodkiTrwale;
    }

    public List<SrodekTrw> getSrodkiZakupRokBiezacy() {
        return srodkiZakupRokBiezacy;
    }

    public void setSrodkiZakupRokBiezacy(List<SrodekTrw> srodkiZakupRokBiezacy) {
        this.srodkiZakupRokBiezacy = srodkiZakupRokBiezacy;
    }

    public String getDatazmiany() {
        return datazmiany;
    }

    public void setDatazmiany(String datazmiany) {
        this.datazmiany = datazmiany;
    }

    public double getKwotazmiany() {
        return kwotazmiany;
    }

    public void setKwotazmiany(double kwotazmiany) {
        this.kwotazmiany = kwotazmiany;
    }


    public List<SrodekTrw> getWyposazenie() {
        return wyposazenie;
    }

    public void setWyposazenie(List<SrodekTrw> wyposazenie) {
        this.wyposazenie = wyposazenie;
    }

   

    public SrodekTrw getWybranySrodekTrw() {
        return wybranySrodekTrw;
    }

    public void setWybranySrodekTrw(SrodekTrw wybranySrodekTrw) {
        this.wybranySrodekTrw = wybranySrodekTrw;
    }

    public boolean isNapewnousunac() {
        return napewnousunac;
    }

    public void setNapewnousunac(boolean napewnousunac) {
        this.napewnousunac = napewnousunac;
    }

    public double getPosiadanesumanetto() {
        return posiadanesumanetto;
    }

    public void setPosiadanesumanetto(double posiadanesumanetto) {
        this.posiadanesumanetto = posiadanesumanetto;
    }

    public int getIloscsrodkow() {
        return iloscsrodkow;
    }

    public int getZakupionewbiezacyrok() {
        return zakupionewbiezacyrok;
    }

    public List<SrodekTrw> getPosiadane() {
        return posiadane;
    }

    public void setPosiadane(List<SrodekTrw> posiadane) {
        this.posiadane = posiadane;
    }

    public boolean isPokazwnip() {
        return pokazwnip;
    }

    public void setPokazwnip(boolean pokazwnip) {
        this.pokazwnip = pokazwnip;
    }

    public List<SrodekTrw> getSprzedane() {
        return sprzedane;
    }

    public void setSprzedane(List<SrodekTrw> sprzedane) {
        this.sprzedane = sprzedane;
    }

    public SrodekTrw getEdytowanysrodek() {
        return edytowanysrodek;
    }

    public void setEdytowanysrodek(SrodekTrw edytowanysrodek) {
        this.edytowanysrodek = edytowanysrodek;
    }

    public SrodekTrw getWybranysrodektrwalyPosiadane() {
        return wybranysrodektrwalyPosiadane;
    }

    public void setWybranysrodektrwalyPosiadane(SrodekTrw wybranysrodektrwalyPosiadane) {
        this.wybranysrodektrwalyPosiadane2 = null;
        this.wybranysrodektrwalyPosiadane = wybranysrodektrwalyPosiadane;
    }

    public SrodekTrw getWybranysrodektrwalyPosiadane2() {
        return wybranysrodektrwalyPosiadane2;
    }

    public void setWybranysrodektrwalyPosiadane2(SrodekTrw wybranysrodektrwalyPosiadane2) {
        this.wybranysrodektrwalyPosiadane = null;
        this.wybranysrodektrwalyPosiadane2 = wybranysrodektrwalyPosiadane2;
    }

    public Srodkikst getSrodekkategoria() {
        return srodekkategoria;
    }

    public void setSrodekkategoria(Srodkikst srodekkategoria) {
        this.srodekkategoria = srodekkategoria;
    }
    public SrodekTrw getWybranysrodektrwalySprzedane() {
        return wybranysrodektrwalySprzedane;
    }

    public void setWybranysrodektrwalySprzedane(SrodekTrw wybranysrodektrwalySprzedane) {
        this.wybranysrodektrwalySprzedane = wybranysrodektrwalySprzedane;
    }
    //</editor-fold>

    

    public void skopiujSTR() {
        try {
            selectedSTR.setKst(srodekkategoria.getSymbol());
            selectedSTR.setUmorzeniepoczatkowe(0.0);
            selectedSTR.setStawka(Double.parseDouble(srodekkategoria.getStawka()));
            String data = wpisView.getRokUprzedniSt()+"-12-31";
            selectedSTR.setDataprzek(data);
            selectedSTR.setNrwldokzak("BO");
            PrimeFaces.current().ajax().update("formdialogsrodki:tabelasrodkitrwaleOT");
        } catch (Exception e) {
            E.e(e); 
        }
        
    }

    public void dodajSrodekTrwaly() {
        double vat = 0.0;
        //dla dokumentu bez vat bedzie blad
        try {
            selectedSTR.setVat(0.0);
            selectedSTR.setUmorzeniezaksiegowane(Boolean.FALSE);
            selectedSTR.setZlikwidowany(0);
            selectedSTR.setDatasprzedazy("");
            String podatnik = wpisView.getPodatnikWpisu();
            selectedSTR.setPodatnik(podatnik);
            selectedSTR.setNrwldokumentu(podatnik);
            selectedSTR.setNrsrodka(posiadane.size()+1);
            selectedSTR.setUmorzPlan(new ArrayList<Double>());
            selectedSTR.setPlanumorzen(new ArrayList<UmorzenieN>());
            selectedSTR.setKontonetto(kontonetto);
            selectedSTR.setKontoumorzenie(kontoumorzenie);
            dodajSrodekTrwaly(selectedSTR);
            posiadane.add(selectedSTR);
            Collections.sort(sprzedane, new SrodekTrwcomparator());
            selectedSTR = new SrodekTrw();
            srodekkategoria =  null;
        } catch (Exception e) { E.e(e); 
        }
    }
    
     public void dodajSrodekTrwaly(SrodekTrw dodawanysrodektrwaly) {
        try {
            SrodkiTrwBean.odpisroczny(dodawanysrodektrwaly);
            SrodkiTrwBean.odpismiesieczny(dodawanysrodektrwaly);
            //oblicza planowane umorzenia
            dodawanysrodektrwaly.setUmorzPlan(SrodkiTrwBean.naliczodpisymczne(dodawanysrodektrwaly));
            dodawanysrodektrwaly.setPlanumorzen(SrodkiTrwBean.generujumorzeniadlasrodka(dodawanysrodektrwaly, wpisView));
            sTRDAO.create(dodawanysrodektrwaly);
//            PrimeFaces.current().ajax().update("srodki:panelekXA");
            Msg.msg("i", "Środek trwały "+dodawanysrodektrwaly.getNazwa()+" dodany", "formSTR:messages");
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e","Nowy srodek nie zachowany "+dodawanysrodektrwaly.getNazwa());
        }
    }
    
    public void naliczumorzeniaSrodekTrwaly() {
         try {
            SrodkiTrwBean.odpisroczny(wybranysrodektrwalyPosiadane);
            SrodkiTrwBean.odpismiesieczny(wybranysrodektrwalyPosiadane);
            //oblicza planowane umorzenia
            wybranysrodektrwalyPosiadane.setUmorzPlan(SrodkiTrwBean.naliczodpisymczne(wybranysrodektrwalyPosiadane));
            wybranysrodektrwalyPosiadane.setPlanumorzen(SrodkiTrwBean.generujumorzeniadlasrodka(wybranysrodektrwalyPosiadane, wpisView));
            sTRDAO.edit(wybranysrodektrwalyPosiadane);
            Msg.msg("i", "Środek trwały "+wybranysrodektrwalyPosiadane.getNazwa()+" przeliczony");
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e","Nowy srodek nie pzeliczony "+wybranysrodektrwalyPosiadane.getNazwa());
        }
    }
    public void edytujSrodekTrwaly() {
        try {
            if (datazmiany != null && kwotazmiany != 0.0) {
                SrodekTrw_NowaWartosc s = new SrodekTrw_NowaWartosc(edytowanysrodek, wpisView, datazmiany, kwotazmiany);
                if (edytowanysrodek.getZmianawartosci() == null) {
                    edytowanysrodek.setZmianawartosci(new ArrayList<SrodekTrw_NowaWartosc>());
                }
                edytowanysrodek.getZmianawartosci().add(s);
                SrodkiTrwBean.naliczodpisymczneUlepszenie(edytowanysrodek);
                edytowanysrodek.setPlanumorzen(null);
                sTRDAO.edit(edytowanysrodek);
                edytowanysrodek.setPlanumorzen(SrodkiTrwBean.generujumorzeniadlasrodka(edytowanysrodek, wpisView));
                Collections.sort(edytowanysrodek.getZmianawartosci(), new SrodekTrwNowaWartoscComparator());
                sTRDAO.edit(edytowanysrodek);
            } else if (zmienilasiedataumorzenia || zmienilasiekwotaumorzenia) {
                przeliczumorzeniapozmainach(edytowanysrodek);
            } else {
                sTRDAO.edit(edytowanysrodek);
            }
            zmienilasiedataumorzenia = false;
            zmienilasiekwotaumorzenia = false;
            wybranysrodektrwalyPosiadane = null;
            wybranysrodektrwalyPosiadane2 = null;
            edytowanysrodek = null;
            datazmiany = null;
            kwotazmiany = 0.0;
            sTREwidencja.init();
            init();
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public void destroyZmianaWartosci(SrodekTrw_NowaWartosc w) {
        try {
            wybranysrodektrwalyPosiadane.getZmianawartosci().remove(w);
            SrodkiTrwBean.odpisroczny(wybranysrodektrwalyPosiadane);
            SrodkiTrwBean.odpismiesieczny(wybranysrodektrwalyPosiadane);
            wybranysrodektrwalyPosiadane.setUmorzPlan(SrodkiTrwBean.naliczodpisymczne(wybranysrodektrwalyPosiadane));
            if (wybranysrodektrwalyPosiadane.getZmianawartosci() != null && wybranysrodektrwalyPosiadane.getZmianawartosci().size() > 0) {
                SrodkiTrwBean.naliczodpisymczneUlepszenie(wybranysrodektrwalyPosiadane);
            }
            wybranysrodektrwalyPosiadane.setPlanumorzen(SrodkiTrwBean.generujumorzeniadlasrodka(wybranysrodektrwalyPosiadane, wpisView));
            Collections.sort(wybranysrodektrwalyPosiadane.getZmianawartosci(), new SrodekTrwNowaWartoscComparator());
            sTRDAO.edit(wybranysrodektrwalyPosiadane);
        } catch (Exception e) {
            E.e(e);
        }
    }
    
   
    
    public void drukowanietabeli(List<SrodekTrw> l, String nazwapliku, int modyfikator) {
        try {
            double netto = 0.0;
            double vat = 0.0;
            double umorzeniepocz = 0.0;
            double odpisrocz = 0.0;
            double odpismc = 0.0;
            double strnetto = 0.0;
            if (filtrowaneposiadane != null) {
                l = filtrowaneposiadane;
            }
            for (Iterator<SrodekTrw> it = l.iterator(); it.hasNext();) {
                SrodekTrw p = it.next();
                if (p.getNrsrodka() == 999999) {
                    it.remove();
                } else {
                    netto += p.getNetto();
                    vat += p.getVat();
                    umorzeniepocz += p.getUmorzeniepoczatkowe();
                    if (p.getOdpisrok() != null) {
                        odpisrocz += p.getOdpisrok();
                        odpismc += p.getOdpismc();
                    }
                    strnetto += p.getStrNetto();
                }
            }
            SrodekTrw suma = new SrodekTrw();
            suma.setNrsrodka(999999);
            suma.setNetto(Z.z(netto));
            if (modyfikator == 0) {
                suma.setVat(Z.z(vat));
            } else {
                suma.setVat(Z.z(strnetto));
            }
            suma.setUmorzeniepoczatkowe(Z.z(umorzeniepocz));
            suma.setOdpisrok(Z.z(odpisrocz));
            suma.setOdpismc(Z.z(odpismc));
            l.add(suma);
            PdfSTRtabela.drukujSTRtabela(wpisView, l, nazwapliku, modyfikator);
        } catch (DocumentException ex) {
            E.e(ex);
            Msg.msg("e", "Nieudane drukowanie wykazu posiadanych środków trwałych");
        } catch (IOException ex) {
            E.e(ex);
            Msg.msg("e", "Nieudane drukowanie wykazu posiadanych środków trwałych");
        }
    }
    
    public void drukowanietabeliPlan(List<SrodekTrw> l, String nazwapliku, int modyfikator) {
        try {
            double netto = 0.0;
            double umorzeniepocz = 0.0;
            double odpisplan = 0.0;
            double nettoplan = 0.0;
            double odpisrok = 0.0;
            double odpismc = 0.0;
            if (filtrowaneposiadane != null) {
                l = filtrowaneposiadane;
            }
            for (Iterator<SrodekTrw> it = l.iterator(); it.hasNext();) {
                SrodekTrw p = it.next();
                if (p.getNrsrodka() == 999999) {
                    it.remove();
                } else {
                    netto += p.getNetto();
                    umorzeniepocz += p.getUmorzeniepoczatkowe();
                    odpisplan += p.getStrOdpisyPlan();
                    nettoplan += p.getStrNettoPlan();
                    odpisrok += p.getOdpisrok()!=null?p.getOdpisrok():0;
                    odpismc += p.getOdpismc()!=null?p.getOdpismc():0;
                }
            }
            SrodekTrw suma = new SrodekTrw();
            suma.setNrsrodka(999999);
            suma.setNetto(Z.z(netto));
            suma.setUmorzeniepoczatkowe(Z.z(umorzeniepocz));
            suma.setVat(Z.z(odpisrok));
            suma.setStawka(Z.z(odpismc));
            suma.setOdpisrok(Z.z(odpisplan));
            suma.setOdpismc(Z.z(nettoplan));
            l.add(suma);
            PdfSTRtabela.drukujSTRtabela(wpisView, l, nazwapliku, modyfikator);
        } catch (DocumentException ex) {
            E.e(ex);
            Msg.msg("e", "Nieudane drukowanie wykazu posiadanych środków trwałych");
        } catch (IOException ex) {
            E.e(ex);
            Msg.msg("e", "Nieudane drukowanie wykazu posiadanych środków trwałych");
        }
    }
    
    public void drukujsrodek() {
        if (wybranysrodektrwalyPosiadane != null) {
            try {
                PdfSrodekTrwKarta.drukujSTRkartasrodka(wpisView, wybranysrodektrwalyPosiadane);
            } catch (DocumentException ex) {
                // Logger.getLogger(STRTabView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                // Logger.getLogger(STRTabView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void drukujsrodeksprzedaz() {
        if (wybranysrodektrwalySprzedane != null) {
            try {
                PdfSrodekTrwKarta.drukujSTRkartasrodka(wpisView, wybranysrodektrwalySprzedane);
            } catch (DocumentException ex) {
                // Logger.getLogger(STRTabView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                // Logger.getLogger(STRTabView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void zmianakwotynetto(ValueChangeEvent e) {
        Double nowa = (Double) e.getNewValue();
        wybranysrodektrwalyPosiadane.setNetto(nowa);
        SrodkiTrwBean.odpisroczny(wybranysrodektrwalyPosiadane);
        SrodkiTrwBean.odpismiesieczny(wybranysrodektrwalyPosiadane);
        //oblicza planowane umorzenia
        wybranysrodektrwalyPosiadane.setUmorzPlan(SrodkiTrwBean.naliczodpisymczne(wybranysrodektrwalyPosiadane));
        wybranysrodektrwalyPosiadane.setPlanumorzen(SrodkiTrwBean.generujumorzeniadlasrodka(wybranysrodektrwalyPosiadane, wpisView));
        try {
            sTRDAO.edit(wybranysrodektrwalyPosiadane);
        } catch (Exception ex) {
            E.e(ex);
        }
        
    }
    
    public void zmianaumorzeniapocz(ValueChangeEvent e) {
        zmienilasiekwotaumorzenia = true;
    }
    
    private boolean umorzeniaporownaj(UmorzenieN stareumorzenie, UmorzenieN noweumorzenie) {
        boolean zwrot = false;
        int staryrok = stareumorzenie.getRokUmorzenia();
        int starymc = stareumorzenie.getMcUmorzenia();
        int nowyrok = noweumorzenie.getRokUmorzenia();
        int nowymc = noweumorzenie.getMcUmorzenia();
        if (staryrok == nowyrok && starymc == nowymc) {
            zwrot = true;
        }
        return zwrot;
    }
    
        
     public void zmianadatyprzyjecia(ValueChangeEvent e) {
        zmienilasiedataumorzenia = true;
    }
     
     private void przeliczumorzeniapozmainach(SrodekTrw wybrany) {
        double odpisroczny = SrodkiTrwBean.odpisroczny(wybrany);
        double odpismiesieczny = SrodkiTrwBean.odpismiesieczny(wybrany);
        //oblicza planowane umorzenia
        wybrany.setUmorzPlan(SrodkiTrwBean.naliczodpisymczne(wybrany));
        List<UmorzenieN> noweumorzenia = SrodkiTrwBean.generujumorzeniadlasrodka(wybrany, wpisView);
        List<UmorzenieN> umorzeniadododania = Collections.synchronizedList(new ArrayList<>());
        for (Iterator<UmorzenieN> it = wybrany.getPlanumorzen().iterator(); it.hasNext();) {
            UmorzenieN stareumorzenie = it.next();
            boolean byljuztakiokres = false;
            for (Iterator<UmorzenieN> itx = noweumorzenia.iterator(); itx.hasNext();) {
                UmorzenieN noweumorzenie = itx.next();
                if (umorzeniaporownaj(stareumorzenie, noweumorzenie)) {
                    //odnalazlem daty w nowych umorzeniach tylko koryguje warotsc
                    byljuztakiokres = true;
                    stareumorzenie.setKwota(noweumorzenie.getKwota());
                    itx.remove();
                    break;
                }
            }
            if (byljuztakiokres == false) {
                //usuwamy te ktorych nie ma w nowych umorzeniach
                it.remove();
            }
        }
        if (noweumorzenia.size() > 0) {
            for (UmorzenieN um : noweumorzenia) {
                umorzeniadododania.add(um);
            }
        }
        if (umorzeniadododania.size() > 0) {
            wybrany.getPlanumorzen().addAll(umorzeniadododania);
        }
        try {
            sTRDAO.edit(wybrany);
            Msg.msg("Przeliczono ponownie umorzenia, po zmianie kwota umorzenia początkowego lub daty przyjęcia");
        } catch (Exception ex) {
            Msg.msg("Nieudane przeliczenie umorzeń");
            E.e(ex);
        }
     }

    public void kopiujwybrany(SrodekTrw wybrany) {
        edytowanysrodek = wybrany;   
    }

    private void sumujposiadane(SrodekTrw srodek) {
        umplan_zakupnetto += srodek.getNetto();
        umplan_umpocz += srodek.getUmorzeniepoczatkowe();
        umplan_narast += srodek.getStrOdpisyPlan();
        umplan_wartoscnetto += srodek.getStrNettoPlan();
        umplan_odpisrok += srodek.getOdpisrok()==null ? 0.0 : srodek.getOdpisrok();
        umplan_odpismc += srodek.getOdpismc()==null ? 0.0 : srodek.getOdpismc();
    }

    private void sprawdzkonta() {
        for (UmorzenieN u : umorzeniazamiesiaclista) {
            boolean zachowaj = false;
            if (u.getKontonetto()==null && u.getSrodekTrw().getKontonetto()!=null) {
                u.setKontonetto(u.getSrodekTrw().getKontonetto().getPelnynumer());
                zachowaj = true;
            }
            if (u.getKontoumorzenie()==null && u.getSrodekTrw().getKontoumorzenie()!=null) {
                u.setKontoumorzenie(u.getSrodekTrw().getKontoumorzenie().getPelnynumer());
                zachowaj = true;
            }
            umorzenieNDAO.edit(u);
        }
    }

        
}
