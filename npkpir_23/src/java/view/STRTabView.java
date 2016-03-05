/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansFK.DokumentFKBean;
import beansSrodkiTrwale.SrodkiTrwBean;
import com.itextpdf.text.DocumentException;
import comparator.SrodekTrwNowaWartoscComparator;
import comparator.SrodekTrwcomparator;
import dao.AmoDokDAO;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import dao.STRDAO;
import dao.SrodkikstDAO;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import data.Data;
import embeddable.Mce;
import embeddable.Roki;
import embeddable.Umorzenie;
import entity.Amodok;
import entity.AmodokPK;
import entity.SrodekTrw;
import entity.SrodekTrw_NowaWartosc;
import entity.Srodkikst;
import entityfk.Dokfk;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import pdf.PdfSTRtabela;
import pdf.PdfSrodekTrwKarta;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "STRTableView")
@ViewScoped
public class STRTabView implements Serializable {
    private static final long serialVersionUID = 1L;
    private SrodekTrw dokdoUsuniecia;
    private boolean napewnousunac;

    @Inject
    protected STRDAO sTRDAO;
    @Inject
    private AmoDokDAO amoDokDAO;
    @Inject
    private SrodkikstDAO srodkikstDAO;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private KlienciDAO klienciDAO;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private SrodekTrw selectedSTR;
    @Inject
    private Srodkikst srodekkategoria;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    //tablica obiektów
    private List<SrodekTrw> srodkiTrwaleWyposazenie;
    //tablica obiektw danego klienta
    private List<SrodekTrw> srodkiTrwale;
    private List<SrodekTrw> filteredValues;
    private List<SrodekTrw> posiadane;
    private List<SrodekTrw> posiadane_wnip;
    private double posiadanesumanetto;
    private double posiadanesumanetto_wnip;
    private List<SrodekTrw> sprzedane;
    private List<SrodekTrw> sprzedane_wnip;
    //tablica obiektów danego klienta z określonego roku i miesiąca
    protected List<SrodekTrw> srodkiZakupRokBiezacy;
    //wyposazenie
    private List<SrodekTrw> wyposazenie;
    //dokumenty amortyzacyjne
    private List<Amodok> amodoklist;
    private List<Amodok> amodoklistselected;
    @Inject
    private SrodekTrw wybranysrodektrwalyPosiadane;
    @Inject
    private SrodekTrw wybranysrodektrwalySprzedane;
    private List<SrodekTrw> listaWyposazenia;
    /**
     * Dane informacyjne gora strony srodkitablica.xhtml
     */
    private int iloscsrodkow;
    private int iloscsrodkow_wnip;
    private int zakupionewbiezacyrok;
    private int zakupionewbiezacyrok_wnip;
    //zmiana wartosci srodka trwalego
    private String datazmiany;
    private double kwotazmiany;

    public STRTabView() {
        ustawTabele();
    }
    
    private void ustawTabele() {
        srodkiTrwaleWyposazenie = new ArrayList<>();
        srodkiTrwale = new ArrayList<>();
        srodkiZakupRokBiezacy = new ArrayList<>();
        wyposazenie = new ArrayList<>();
        posiadane = new ArrayList<>();
        posiadane_wnip = new ArrayList<>();
        sprzedane = new ArrayList<>();
        sprzedane_wnip = new ArrayList<>();
        amodoklist = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        ustawTabele();
        String rokdzisiejszy = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        zakupionewbiezacyrok = 0;
        zakupionewbiezacyrok_wnip = 0;
        try {
            if (wpisView.getPodatnikWpisu() != null) {
                List<SrodekTrw> srodkizBazy = new ArrayList<>();
                try {
                    srodkizBazy = sTRDAO.findStrPod(wpisView.getPodatnikWpisu());
                } catch (Exception e) {
                    E.e(e);
                }
                if (!srodkizBazy.isEmpty()) {
                    int i = 1;
                    int j = 1;
                    for (SrodekTrw srodek : srodkizBazy) {
                        srodkiTrwaleWyposazenie.add(srodek);
                        if (srodek.getPodatnik().equals(wpisView.getPodatnikWpisu())) {
                            if (srodek.getTyp() != null && srodek.getTyp().equals("wyposazenie")) {
                                srodek.setNrsrodka(i++);
                                wyposazenie.add(srodek);

                            } else if (srodek.getTyp() != null && srodek.getTyp().equals("wnip")) {
                                srodek.setNrsrodka(j++);
                                if (srodek.getDatazak().substring(0, 4).equals(rokdzisiejszy)) {
                                    zakupionewbiezacyrok_wnip++;
                                }
                                srodkiTrwale.add(srodek);
                                if (srodek.getZlikwidowany() == 0) {
                                    posiadane_wnip.add(srodek);
                                    posiadanesumanetto_wnip += srodek.getNetto();
                                } else {
                                    sprzedane_wnip.add(srodek);
                                }
                            } else {
                                srodek.setNrsrodka(j++);
                                if (srodek.getDatazak().substring(0, 4).equals(rokdzisiejszy)) {
                                    zakupionewbiezacyrok++;
                                }
                                srodkiTrwale.add(srodek);
                                if (srodek.getZlikwidowany() == 0) {
                                    posiadane.add(srodek);
                                    posiadanesumanetto += srodek.getNetto();
                                } else {
                                    sprzedane.add(srodek);
                                }
                            }
                        }
                    }
                    iloscsrodkow = srodkiTrwale.size();
                }
            }
            /**
             * to co bylo w amodok
             */
            if (wpisView.getPodatnikWpisu() != null) {
                try {
                    amodoklist = amoDokDAO.amodokKlientRok(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                } catch (Exception e) {
                    E.e(e);
                }
            }
            RequestContext.getCurrentInstance().update("formSTR:akordeon");
        } catch (Exception e) {
            E.e(e);
        }
    }

    //przyporzadkowuje planowane odpisy do konkretnych miesiecy
    public void generujodpisy() {
        if (srodkiTrwale == null || srodkiTrwale.size() == 0) {
            init();
            Msg.msg("Pobieram środki do umorzeń");
        }
        Iterator it = srodkiTrwale.iterator();
        while (it.hasNext()) {
            SrodekTrw srodek = (SrodekTrw) it.next();
            //jak tego nie bedzie to zresetuje odpisy sprzedanych
            if (srodek.getZlikwidowany() == 0) {
                odpisypojedynczysrodek(srodek);
            }
        }
        Msg.msg("Odpisy wygenerowane. Pamiętaj o wygenerowaniu dokumentów umorzeń! W tym celu wybierz w menu stronę umorzenie");
    }
    
    public void odpisypojedynczysrodek(SrodekTrw srodek) {
        try {
            srodek.setUmorzWyk(SrodkiTrwBean.generujumorzeniadlasrodka(srodek, wpisView));
            sTRDAO.edit(srodek);
        } catch (Exception e) { 
            E.e(e); 
        }
    }

    public void generujamodokumenty() {
        generujodpisy();
        List<SrodekTrw> lista = new ArrayList<>();
        lista.addAll(srodkiTrwale);
        String pod = wpisView.getPodatnikWpisu();
        Integer rokOd = wpisView.getRokWpisu();
        Integer mcOd = Integer.parseInt(wpisView.getMiesiacWpisu());
        amoDokDAO.destroy(pod, rokOd, mcOd);
        Roki roki = new Roki();
        while (roki.getRokiList().contains(rokOd)) {
            Amodok amoDok = new Amodok();
            AmodokPK amodokPK = new AmodokPK();
            amodokPK.setPodatnik(pod);
            amodokPK.setRok(rokOd);
            amodokPK.setMc(mcOd);
            amoDok.setAmodokPK(amodokPK);
            amoDok.setZaksiegowane(Boolean.FALSE);
            for (SrodekTrw srodek : lista) {
                List<Umorzenie> umorzeniaWyk = new ArrayList<>();
                umorzeniaWyk.addAll(srodek.getUmorzWyk());
                for (Umorzenie umAkt : umorzeniaWyk) {
                    if ((umAkt.getRokUmorzenia().equals(rokOd)) && (umAkt.getMcUmorzenia().equals(mcOd))) {
                        if (umAkt.getKwota().signum() > 0) {
                            if (srodek.getKontonetto() != null) {
                                umAkt.setSrodekTrwID(srodek.getId());
                                umAkt.setKontonetto(srodek.getKontonetto().getPelnynumer());
                                umAkt.setKontoumorzenie(srodek.getKontoumorzenie().getPelnynumer());
                                umAkt.setRodzaj(srodek.getTyp());
                            }
                            amoDok.getUmorzenia().add(umAkt);
                        }
                    }
                }
                srodek.setUmorzeniezaksiegowane(Boolean.TRUE);
                sTRDAO.edit(srodek);
            }
            //ZAZNACZA PUSTE JAKO TRUe a to w celu zachwoania ciaglosci a to w celu pokazania ze sa sporzadzone za zadany okres a ze nie wsyatpil blad
            if (amoDok.getUmorzenia().isEmpty()) {
                amoDok.setZaksiegowane(true);
            }
            if (mcOd == 12) {
                amoDokDAO.dodaj(amoDok);
                rokOd++;
                mcOd = 1;

            } else {
                amoDokDAO.dodaj(amoDok);
                mcOd++;

            }
        }
        nowalistadokamo();
        RequestContext.getCurrentInstance().update("formSTR");
        Msg.msg("i", "Dokumenty amortyzacyjne wygenerowane od miesiąca " + wpisView.getMiesiacWpisu() + " roku " + wpisView.getRokWpisuSt(), "formSTR:mess_add");
    }
// wycialem te funkcje bo jest konflit on sprawdza czy dokument nie jest zaksiegowany
//ale inna funkcja zerowe tez zaznacza jako zaksiegowane wiec on produkuje sie bez sensu
//    private List sprawdzzaksiegowanedokumenty(String pod) {
//        List<Amodok> amodoki = amoDokDAO.amodokklient(pod);
//        Collections.sort(amodoki, new Amodokcomparator());
//        int rok = 0;
//        int mc = 0;
//        for (Amodok p : amodoki) {
//            if (p.getZaksiegowane() == false) {
//                break;
//            }
//            rok = p.getAmodokPK().getRok();
//            mc = p.getAmodokPK().getMc();
//        }
//        Msg.msg("i", "Pominięto dokumenty zaksięgowane. Aktualizacja po " + rok + "/" + Mce.getMapamcy().get(mc), "formSTR:mess_add");
//        List odpowiedz = new ArrayList<>();
//        odpowiedz.add(rok);
//        odpowiedz.add(mc);
//        return odpowiedz;
//    }

    private void nowalistadokamo() {
        if (wpisView.getPodatnikWpisu() != null) {
            try {
                amodoklist = amoDokDAO.amodokklient(wpisView.getPodatnikWpisu());
            } catch (Exception e) { 
                E.e(e); 
            }
        }
    }

    public void destroy(SrodekTrw selDok) {
        dokdoUsuniecia = new SrodekTrw();
        dokdoUsuniecia = selDok;
    }

    public void destroy2() {
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //       Principal principal = request.getUserPrincipal();
//        if(request.isUserInRole("Administrator")){
//        if(sprawdzczyniemarozrachunkow()==true){
//             FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dokument nie usunięty - Usuń wpierw rozrachunki, proszę", dokdoUsuniecia.getIdDok().toString());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//        } else {
        try {
            if (dokdoUsuniecia.isUmorzeniezaksiegowane() == true && napewnousunac == false) {
                throw new Exception();
            }
            srodkiTrwale.remove(dokdoUsuniecia);
            posiadane.remove(dokdoUsuniecia);
            sprzedane.remove(dokdoUsuniecia);
            sTRDAO.destroy(dokdoUsuniecia);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Środek trwały usunięty", dokdoUsuniecia.getNazwa());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Nie usnieto " + dokdoUsuniecia.getNazwa() + ". Umorzenie srodka w ksiegach", ":formSTR:mess_add");
        }

//    } else {
//            FacesMessage msg = new FacesMessage("Nie masz uprawnien do usuniecia dokumentu", selDokument.getIdDok().toString());
//          FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
//     }
//    }
    }

    public void oznaczjakozaksiegowane() {
        for (Amodok p : amodoklistselected) {
            if (p.getZaksiegowane() == false) {
                p.setZaksiegowane(true);
            } else {
                p.setZaksiegowane(false);
            }
            amoDokDAO.edit(p);
            Msg.msg("i", "Oznaczono AMO jako zaksięgowany");
        }
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
        for (Umorzenie x : p.getUmorzWyk()) {
            if (x.getRokUmorzenia() <= rok && x.getMcUmorzenia() < mc) {
                suma += x.getKwota().doubleValue();
            } else if (x.getRokUmorzenia() == rok && x.getMcUmorzenia() == mc) {
                umorzeniesprzedaz = p.getNetto() - p.getUmorzeniepoczatkowe() - suma;
                x.setKwota(BigDecimal.ZERO);
                p.setKwotaodpislikwidacja(0.0);
            } else {
                x.setKwota(BigDecimal.ZERO);
            }
        }
        try {
            sTRDAO.edit(p);
            posiadane.remove(p);
            sprzedane.add(p);
            Collections.sort(sprzedane, new SrodekTrwcomparator());
            Msg.msg("i", "Naniesiono wycofanie: " + p.getNazwa() + ". Pamiętaj o wygenerowaniu nowych dokumentow umorzeń!", "dodWiad:mess_add");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystapił błąd - nie naniesiono wycofania: " + p.getNazwa(), "dodWiad:mess_add");
        }
    }

    public void przywrocsrodki() {
        SrodekTrw p = wybranysrodektrwalySprzedane;
        p.setZlikwidowany(0);
        p.setStyl("color: black;");
        p.setDatasprzedazy("");
        p.setNrwldokumentu("");
        try {
            sTRDAO.edit(p);
            posiadane.add(p);
            Collections.sort(posiadane, new SrodekTrwcomparator());
            sprzedane.remove(p);
            Msg.msg("i", "Cofnięto sprzedaż/wycofanie: " + p.getNazwa() + ". Pamiętaj o wygenerowaniu nowych dokumentow umorzeń!", "dodWiad:mess_add");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystapił błąd - nie cofnięto sprzedaży/wycofania: " + p.getNazwa(), "dodWiad:mess_add");
        }
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public List<SrodekTrw> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<SrodekTrw> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public STRDAO getsTRDAO() {
        return sTRDAO;
    }

    public void setsTRDAO(STRDAO sTRDAO) {
        this.sTRDAO = sTRDAO;
    }

    public AmoDokDAO getAmoDokDAO() {
        return amoDokDAO;
    }

    public void setAmoDokDAO(AmoDokDAO amoDokDAO) {
        this.amoDokDAO = amoDokDAO;
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

   

    public SrodekTrw getDokdoUsuniecia() {
        return dokdoUsuniecia;
    }

    public void setDokdoUsuniecia(SrodekTrw dokdoUsuniecia) {
        this.dokdoUsuniecia = dokdoUsuniecia;
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

    public List<Amodok> getAmodoklist() {
        return amodoklist;
    }

    public void setAmodoklist(List<Amodok> amodoklist) {
        this.amodoklist = amodoklist;
    }

    public List<SrodekTrw> getPosiadane() {
        return posiadane;
    }

    public void setPosiadane(List<SrodekTrw> posiadane) {
        this.posiadane = posiadane;
    }

    public List<SrodekTrw> getSprzedane() {
        return sprzedane;
    }

    public void setSprzedane(List<SrodekTrw> sprzedane) {
        this.sprzedane = sprzedane;
    }

    public List<Amodok> getAmodoklistselected() {
        return amodoklistselected;
    }

    public void setAmodoklistselected(List<Amodok> amodoklistselected) {
        this.amodoklistselected = amodoklistselected;
    }

    public SrodekTrw getWybranysrodektrwalyPosiadane() {
        return wybranysrodektrwalyPosiadane;
    }

    public void setWybranysrodektrwalyPosiadane(SrodekTrw wybranysrodektrwalyPosiadane) {
        this.wybranysrodektrwalyPosiadane = wybranysrodektrwalyPosiadane;
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
            RequestContext.getCurrentInstance().update("formdialogsrodki:tabelasrodkitrwaleOT");
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
            selectedSTR.setNiepodlegaamortyzacji(selectedSTR.getNetto());
            selectedSTR.setUmorzeniepoczatkowe(selectedSTR.getNetto());
            selectedSTR.setZlikwidowany(0);
            selectedSTR.setDatasprzedazy("");
            String podatnik = wpisView.getPodatnikWpisu();
            selectedSTR.setPodatnik(podatnik);
            selectedSTR.setUmorzPlan(null);
            selectedSTR.setStawka(0.0);
            selectedSTR.setNrwldokumentu(podatnik);
            selectedSTR.setNrsrodka(posiadane.size()+1);
            selectedSTR.setUmorzPlan(new ArrayList<Double>());
            selectedSTR.setUmorzWyk(new ArrayList<Umorzenie>());
            sTRDAO.dodaj(selectedSTR);
            posiadane.add(selectedSTR);
            Collections.sort(sprzedane, new SrodekTrwcomparator());
        } catch (Exception e) { E.e(e); 
        }
    }
    
     public void dodajSrodekTrwaly(SrodekTrw dodawanysrodektrwaly) {
        try {
            SrodkiTrwBean.odpisroczny(dodawanysrodektrwaly);
            SrodkiTrwBean.odpismiesieczny(dodawanysrodektrwaly);
            //oblicza planowane umorzenia
            dodawanysrodektrwaly.setUmorzPlan(SrodkiTrwBean.naliczodpisymczne(dodawanysrodektrwaly));
            dodawanysrodektrwaly.setUmorzWyk(SrodkiTrwBean.generujumorzeniadlasrodka(dodawanysrodektrwaly, wpisView));
            sTRDAO.dodaj(dodawanysrodektrwaly);
            RequestContext.getCurrentInstance().update("srodki:panelekXA");
            Msg.msg("i", "Środek trwały "+dodawanysrodektrwaly.getNazwa()+" dodany", "formSTR:messages");
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e","Nowy srodek nie zachowany "+dodawanysrodektrwaly.getNazwa());
        }
    }
    
    
    public void edytujSrodekTrwaly() {
        try {
            if (datazmiany != null && kwotazmiany != 0.0) {
                SrodekTrw_NowaWartosc s = new SrodekTrw_NowaWartosc(wybranysrodektrwalyPosiadane, wpisView, datazmiany, kwotazmiany);
                if (wybranysrodektrwalyPosiadane.getZmianawartosci() == null) {
                    wybranysrodektrwalyPosiadane.setZmianawartosci(new ArrayList<SrodekTrw_NowaWartosc>());
                }
                wybranysrodektrwalyPosiadane.getZmianawartosci().add(s);
                SrodkiTrwBean.naliczodpisymczneUlepszenie(wybranysrodektrwalyPosiadane);
                wybranysrodektrwalyPosiadane.setUmorzWyk(SrodkiTrwBean.generujumorzeniadlasrodka(wybranysrodektrwalyPosiadane, wpisView));
            }
            Collections.sort(wybranysrodektrwalyPosiadane.getZmianawartosci(), new SrodekTrwNowaWartoscComparator());
            sTRDAO.edit(wybranysrodektrwalyPosiadane);
            wybranysrodektrwalyPosiadane = null;
            datazmiany = null;
            kwotazmiany = 0.0;
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
            wybranysrodektrwalyPosiadane.setUmorzWyk(SrodkiTrwBean.generujumorzeniadlasrodka(wybranysrodektrwalyPosiadane, wpisView));
            Collections.sort(wybranysrodektrwalyPosiadane.getZmianawartosci(), new SrodekTrwNowaWartoscComparator());
            sTRDAO.edit(wybranysrodektrwalyPosiadane);
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public void ksiegujUmorzenieFK(Amodok amodok) {
        Dokfk znalezionyBiezacy = dokDAOfk.findDokfkLastofaTypeMc(wpisView.getPodatnikObiekt(), "AMO", String.valueOf(amodok.getAmodokPK().getRok()), wpisView.getMiesiacWpisu());
        Dokfk dokumentAMO = DokumentFKBean.generujdokument(wpisView, klienciDAO, "AMO", "zaksięgowanie umorzenia ", rodzajedokDAO, tabelanbpDAO, kontoDAOfk, amodok.getUmorzenia(), dokDAOfk);
        String nrdokumentu = null;
        if (znalezionyBiezacy != null) {
            nrdokumentu = znalezionyBiezacy.getNumerwlasnydokfk();
            dokumentAMO.setNumerwlasnydokfk(DokumentFKBean.zwieksznumerojeden(nrdokumentu));
            Msg.msg("w", "Wskazanie umorzenie nie dotyczy bieżącego miesiąca. Nie jest to prawidłowe!");
        }
        try {
            dokDAOfk.dodaj(dokumentAMO);
            amodok.setZaksiegowane(true);
            amoDokDAO.edit(amodok);
            Msg.msg("Zaksięgowano dokument AMO");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu AMO");
        }
    }
    
    public void drukowanietabeli() {
        try {
            double netto = 0.0;
            double vat = 0.0;
            double umorzeniepocz = 0.0;
            double odpisrocz = 0.0;
            double odpismc = 0.0;
            for (SrodekTrw p : posiadane) {
                netto += p.getNetto();
                vat += p.getVat();
                umorzeniepocz += p.getUmorzeniepoczatkowe();
                odpisrocz += p.getOdpisrok();
                odpismc += p.getOdpismc();
            }
            SrodekTrw suma = new SrodekTrw();
            suma.setNrsrodka(999999);
            suma.setNetto(Z.z(netto));
            suma.setVat(Z.z(vat));
            suma.setUmorzeniepoczatkowe(Z.z(umorzeniepocz));
            suma.setOdpisrok(Z.z(odpisrocz));
            suma.setOdpismc(Z.z(odpismc));
            posiadane.add(suma);
            PdfSTRtabela.drukujSTRtabela(wpisView, posiadane);
        } catch (DocumentException ex) {
            Msg.msg("e", "Nieudane drukowanie wykazu posiadanych środków trwałych");
            Logger.getLogger(STRTabView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Msg.msg("e", "Nieudane drukowanie wykazu posiadanych środków trwałych");
            Logger.getLogger(STRTabView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void drukujsrodek() {
        if (wybranysrodektrwalyPosiadane != null) {
            try {
                PdfSrodekTrwKarta.drukujSTRkartasrodka(wpisView, wybranysrodektrwalyPosiadane);
            } catch (DocumentException ex) {
                Logger.getLogger(STRTabView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(STRTabView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void drukujsrodeksprzedaz() {
        if (wybranysrodektrwalySprzedane != null) {
            try {
                PdfSrodekTrwKarta.drukujSTRkartasrodka(wpisView, wybranysrodektrwalySprzedane);
            } catch (DocumentException ex) {
                Logger.getLogger(STRTabView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(STRTabView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List<SrodekTrw> getListaWyposazenia() {
        return listaWyposazenia;
    }

    public void setListaWyposazenia(List<SrodekTrw> listaWyposazenia) {
        this.listaWyposazenia = listaWyposazenia;
    }

    public List<SrodekTrw> getPosiadane_wnip() {
        return posiadane_wnip;
    }

    public void setPosiadane_wnip(List<SrodekTrw> posiadane_wnip) {
        this.posiadane_wnip = posiadane_wnip;
    }

    public double getPosiadanesumanetto_wnip() {
        return posiadanesumanetto_wnip;
    }

    public void setPosiadanesumanetto_wnip(double posiadanesumanetto_wnip) {
        this.posiadanesumanetto_wnip = posiadanesumanetto_wnip;
    }

    public List<SrodekTrw> getSprzedane_wnip() {
        return sprzedane_wnip;
    }

    public void setSprzedane_wnip(List<SrodekTrw> sprzedane_wnip) {
        this.sprzedane_wnip = sprzedane_wnip;
    }

    public int getIloscsrodkow_wnip() {
        return iloscsrodkow_wnip;
    }

    public void setIloscsrodkow_wnip(int iloscsrodkow_wnip) {
        this.iloscsrodkow_wnip = iloscsrodkow_wnip;
    }

    public int getZakupionewbiezacyrok_wnip() {
        return zakupionewbiezacyrok_wnip;
    }

    public void setZakupionewbiezacyrok_wnip(int zakupionewbiezacyrok_wnip) {
        this.zakupionewbiezacyrok_wnip = zakupionewbiezacyrok_wnip;
    }

    
}
