/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.WierszBOcomparator;
import comparator.WierszBOcomparatorKwota;
import dao.DokDAOfk;
import dao.KlienciDAO;
import dao.KontoDAOfk;
import dao.RodzajedokDAO;
import dao.StronaWierszaDAO;
import dao.TabelanbpDAO;
import dao.WalutyDAOfk;
import dao.WierszBODAO;
import dao.WierszDAO;
import data.Data;
import embeddablefk.TreeNodeExtended;
import entity.Evewidencja;
import entity.Klienci;
import entity.Podatnik;
import entity.Rodzajedok;
import entity.Uz;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersz;
import entityfk.WierszBO;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import pdffk.PdfWierszBO;
 import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class BilansWprowadzanieView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private WierszDAO wierszDAO;
    @Inject
    private WalutyDAOfk walutyDAOfk;
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
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
    private WierszBO selected;
    private List<WierszBO> listaBO;
    private List<WierszBO> listaBOFiltered;
    private List<WierszBO> listaBOselected;
    private List<WierszBO> listaBOs1;
    private Integer nraktualnejlisty;
    private List<WierszBO> listaBOsumy;
    private DataTable listaBOdatatable;
    private List<WierszBO> lista0;
    private List<WierszBO> lista1;
    private List<WierszBO> lista2;
    private List<WierszBO> lista3;
    private List<WierszBO> lista4;
    private List<WierszBO> lista6;
    private List<WierszBO> lista7;
    private List<WierszBO> lista8;
    private Map<Integer, List<WierszBO>> listaGrupa;
    //to sa listy selected
    private List<WierszBO> lista0s;
    private List<WierszBO> lista1s;
    private List<WierszBO> lista2s;
    private List<WierszBO> lista3s;
    private List<WierszBO> lista4s;
    private List<WierszBO> lista6s;
    private List<WierszBO> lista7s;
    private List<WierszBO> lista8s;
    private List<WierszBO> listaW;
    private List<WierszBO> listaWKonsolidacja;
    private Map<Integer, List<WierszBO>> listazbiorcza;
    private Map<Integer, List> listaSumList;
    private double stronaWn;
    private double stronaMa;
    private double stronaWn_stronaMa;
    private Dokfk dokumentBO;
    private boolean isteniejeDokBO;
    private Dokfk dokumentBOR;
    private boolean isteniejeDokBOR;
    private List<WierszBO> wierszedousuniecia;
    private Waluty walutadomyslna;
    private boolean sortujwgwartosci;
    private Konto ostatniekonto;
    private String miesiacWpisu;
    private boolean tojestgenerowanieobrotow;
    private boolean tojestbilanslikwidacyjny;
    private boolean pokazstarekonta;
    private List<Waluty> wprowadzonesymbolewalut;
    private EVatwpisFK ewidencjaVatRK;
    private WierszBO wierszBOVAT;
    private List<Evewidencja> listaewidencjivatRK;
    private boolean ewidencjaVATRKzapis0edycja1;

    @Inject
    private WpisView wpisView;

    public BilansWprowadzanieView() {
         ////E.m(this);

    }
    
    public void rozpocznijBO() {
        this.tojestgenerowanieobrotow = false;
        this.tojestbilanslikwidacyjny = false;
        init();
    }
    
    public void rozpocznijObroty() {
        this.tojestgenerowanieobrotow = true;
        this.tojestbilanslikwidacyjny = false;
        init();
    }
    
    public void rozpocznijLikwidacja() {
        this.tojestgenerowanieobrotow = false;
        this.tojestbilanslikwidacyjny = true;
        init();
    }

    public void init() { //E.m(this);
        this.miesiacWpisu = wpisView.getMiesiacWpisu();
        this.lista0 = Collections.synchronizedList(new ArrayList<>());
        this.lista1 = Collections.synchronizedList(new ArrayList<>());
        this.lista2 = Collections.synchronizedList(new ArrayList<>());
        this.lista3 = Collections.synchronizedList(new ArrayList<>());
        this.lista4 = Collections.synchronizedList(new ArrayList<>());
        this.lista6 = Collections.synchronizedList(new ArrayList<>());
        this.lista7 = Collections.synchronizedList(new ArrayList<>());
        this.lista8 = Collections.synchronizedList(new ArrayList<>());
        this.listaGrupa = new ConcurrentHashMap<>();
        this.listaWKonsolidacja = Collections.synchronizedList(new ArrayList<>());
        this.listaSumList = new ConcurrentHashMap<>();
        listaSumList.put(0, new ArrayList());
        listaSumList.put(1, new ArrayList());
        listaSumList.put(2, new ArrayList());
        listaSumList.put(3, new ArrayList());
        listaSumList.put(4, new ArrayList());
        listaSumList.put(6, new ArrayList());
        listaSumList.put(7, new ArrayList());
        listaSumList.put(8, new ArrayList());
        this.pokazstarekonta = false;
        tworzListeZbiorcza();
        wprowadzonesymbolewalut = walutyDAOfk.findAll();
        Podatnik p = wpisView.getPodatnikObiekt();
        String r = wpisView.getRokWpisuSt();
        String mc = wpisView.getMiesiacWpisu();
        walutadomyslna = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        this.listaW = Collections.synchronizedList(new ArrayList<>());
        if (wpisView.getPodatnikObiekt().getDataotwarcialikwidacji()!=null && !wpisView.getPodatnikObiekt().getDataotwarcialikwidacji().equals("")) {
            String mcl = Data.getMc(wpisView.getPodatnikObiekt().getDataotwarcialikwidacji());
            wpisView.setMiesiacWpisu(mcl);
        }
        this.lista0.addAll(wierszBODAO.lista("0%", wpisView, tojestbilanslikwidacyjny));
        if (lista0.isEmpty()) {
            this.lista0.add(new WierszBO(p, r, walutadomyslna, mc));
        } else {
            this.listaW.addAll(this.lista0);
        }
        this.lista1.addAll(wierszBODAO.lista("1%", wpisView, tojestbilanslikwidacyjny));
        if (lista1.isEmpty()) {
            this.lista1.add(new WierszBO(p, r, walutadomyslna, mc));
        } else {
            this.listaW.addAll(this.lista1);
        }
        this.lista2.addAll(wierszBODAO.lista("2%", wpisView, tojestbilanslikwidacyjny));
        if (lista2.isEmpty()) {
            this.lista2.add(new WierszBO(p, r, walutadomyslna, mc));
        } else {
            this.listaW.addAll(this.lista2);
        }
        this.lista3.addAll(wierszBODAO.lista("3%", wpisView, tojestbilanslikwidacyjny));
        if (lista3.isEmpty()) {
            this.lista3.add(new WierszBO(p, r, walutadomyslna, mc));
        } else {
            this.listaW.addAll(this.lista3);
        }
        this.lista4.addAll(wierszBODAO.lista("4%", wpisView, tojestbilanslikwidacyjny));
        if (lista4.isEmpty()) {
            this.lista4.add(new WierszBO(p, r, walutadomyslna, mc));
        } else {
            this.listaW.addAll(this.lista4);
        }
        this.lista6.addAll(wierszBODAO.lista("6%", wpisView, tojestbilanslikwidacyjny));
        if (lista6.isEmpty()) {
            this.lista6.add(new WierszBO(p, r, walutadomyslna, mc));
        } else {
            this.listaW.addAll(this.lista6);
        }
        this.lista7.addAll(wierszBODAO.lista("7%", wpisView, tojestbilanslikwidacyjny));
        if (lista7.isEmpty()) {
            this.lista7.add(new WierszBO(p, r, walutadomyslna, mc));
        } else {
            this.listaW.addAll(this.lista7);
        }
        this.lista8.addAll(wierszBODAO.lista("8%", wpisView, tojestbilanslikwidacyjny));
        if (lista8.isEmpty()) {
            this.lista8.add(new WierszBO(p, r, walutadomyslna, mc));
        } else {
            this.listaW.addAll(this.lista8);
        }
        usunpodwojnekontawListaW();
        Collections.sort(lista0, new WierszBOcomparator());
        Collections.sort(lista1, new WierszBOcomparator());
        Collections.sort(lista2, new WierszBOcomparator());
        Collections.sort(lista3, new WierszBOcomparator());
        Collections.sort(lista4, new WierszBOcomparator());
        Collections.sort(lista6, new WierszBOcomparator());
        Collections.sort(lista7, new WierszBOcomparator());
        Collections.sort(lista8, new WierszBOcomparator());
        Collections.sort(listaW, new WierszBOcomparator());
        podsumujWnMa(listaW);
        podsumujWnMa(lista0, listaSumList.get(0));
        podsumujWnMa(lista1, listaSumList.get(1));
        podsumujWnMa(lista2, listaSumList.get(2));
        podsumujWnMa(lista3, listaSumList.get(3));
        podsumujWnMa(lista4, listaSumList.get(4));
        podsumujWnMa(lista6, listaSumList.get(6));
        podsumujWnMa(lista7, listaSumList.get(7));
        podsumujWnMa(lista8, listaSumList.get(8));
        listaGrupa.put(0, lista0);
        listaGrupa.put(1, lista1);
        listaGrupa.put(2, lista2);
        listaGrupa.put(3, lista3);
        listaGrupa.put(4, lista4);
        listaGrupa.put(6, lista6);
        listaGrupa.put(7, lista7);
        listaGrupa.put(8, lista8);
        selected = new WierszBO(p, r, walutadomyslna, mc);
        listaBO = lista0;
        listaBOsumy = listaSumList.get(0);
        nraktualnejlisty = 0;
        dokumentBO = dokDAOfk.findDokfkLastofaTypeMc(wpisView.getPodatnikObiekt(), "BO", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        if (dokumentBO != null) {
            isteniejeDokBO = true;
        } else {
            isteniejeDokBO = false;
        }
        dokumentBOR = dokDAOfk.findDokfkLastofaTypeMc(wpisView.getPodatnikObiekt(), "BOR", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        if (dokumentBOR != null) {
            isteniejeDokBOR = true;
        } else {
            isteniejeDokBOR = false;
        }
        wierszedousuniecia = Collections.synchronizedList(new ArrayList<>());
    }
    
    public void init2() {
        if (!miesiacWpisu.equals("CR")) {
            wpisView.setMiesiacWpisu(miesiacWpisu);
            wpisView.aktualizuj();
            init();
        }
    }

    public void pobierzlista(int nrlisty) {
        try {
            pokazstarekonta = false;
            if (listaBOdatatable != null) {
                listaBOdatatable.setStyle("overflow-y: scroll; height: 400px; width: 1100px; padding: 10px; margin-top: 5px;");
            }
            listaBO = listazbiorcza.get(nrlisty);
            if (listaBO.size() == 1 && listaBO.get(0).getKonto() == null) {
                listaBO.remove(0);
            }
            listaBOsumy = listaSumList.get(nrlisty);
            nraktualnejlisty = nrlisty;
            if (listaBOFiltered != null) {
                listaBOFiltered = null;
                PrimeFaces.current().executeScript("try{PF('tab0prosta').clearFilters()}catch(e){}");
                PrimeFaces.current().executeScript("try{PF('tab0zlozona').clearFilters()}catch(e){}");
            }
        } catch (Exception e) {}
    }
    
    public void pobierzlistaS() {
        pokazstarekonta = true;
        listaBOdatatable.setStyle("overflow-y: scroll; height: 400px; width: 1280px; padding: 10px; margin-top: 5px;");
        List<WierszBO> listawstepna = Collections.synchronizedList(new ArrayList<>());
        for (List<WierszBO> l : listazbiorcza.values()) {
            listawstepna.addAll(l);
        }
        Set<WierszBO> wierszemac = new HashSet<>();
        for (WierszBO wb : listawstepna) {
            if (wb.getKonto() != null && niezawiera(wb,wierszemac)) {
                WierszBO nowy = serialclone.SerialClone.clone(wb);
                nowy.setKwotaMa(0);
                nowy.setKwotaWn(0);
                nowy.setKwotaMaPLN(0);
                nowy.setKwotaWnPLN(0);
                wierszemac.add(nowy);
            }
        }
        List<Konto> kontarokpop = kontoDAO.findKontaBilansowePodatnikaBezPotomkowRokPoprzedni(wpisView);
        for (WierszBO wb : wierszemac) {
            for (WierszBO wb1 : listawstepna) {
                if (wb1.getKonto() != null && wb1.getKonto().equals(wb.getKonto())) {
                    try {
                        if (!kontarokpop.isEmpty()) {
                                Konto rokpop = kontarokpop.get(kontarokpop.indexOf(wb.getKonto()));
                                if (rokpop != null && wb != null) {
                                    wb.setKontostare(rokpop);
                                }
                        }
                    } catch (Exception e) {}
                    wb.setKwotaWn(wb.getKwotaWn()+wb1.getKwotaWn());
                    wb.setKwotaMa(wb.getKwotaMa()+wb1.getKwotaMa());
                       wb.setKwotaWnPLN(wb.getKwotaWnPLN()+wb1.getKwotaWnPLN());
                    wb.setKwotaMaPLN(wb.getKwotaMaPLN()+wb1.getKwotaMaPLN());
                }
            }
        }
        listaBO = Collections.synchronizedList(new ArrayList<>());
        listaBO.addAll(wierszemac);
        Collections.sort(listaBO, new WierszBOcomparator());
        podsumujWnMa(listaBO, listaBOsumy);
        nraktualnejlisty = 9;
        if (listaBOFiltered != null) {
            listaBOFiltered = null;
            PrimeFaces.current().executeScript("try{PF('tab0prosta').clearFilters()}catch(e){}");
            PrimeFaces.current().executeScript("try{PF('tab0zlozona').clearFilters()}catch(e){}");
        }
    }

    private boolean niezawiera(WierszBO wb, Set<WierszBO> wierszemac) {
        boolean zwrot = true;
        for (WierszBO p : wierszemac) {
            if (p.getKonto().equals(wb.getKonto())) {
                zwrot = false;
                break;
            }
        }
        return zwrot;
    }
    
    private void tworzListeZbiorcza() {
        this.listazbiorcza = new ConcurrentHashMap<>();
        this.listazbiorcza.put(0, lista0);
        this.listazbiorcza.put(1, lista1);
        this.listazbiorcza.put(2, lista2);
        this.listazbiorcza.put(3, lista3);
        this.listazbiorcza.put(4, lista4);
        this.listazbiorcza.put(6, lista6);
        this.listazbiorcza.put(7, lista7);
        this.listazbiorcza.put(8, lista8);
    }

    public void edycjawiersza(WierszBO wiersz) {
        selected = wiersz;
    }

    public void dodajwiersz() {
        if (selected.getKonto() == null) {
            Msg.msg("e", "Brak wybranego konta. Nie można zapisać");
        } else if (!selected.getKonto().getPelnynumer().startsWith(String.valueOf(nraktualnejlisty))) {;
            Msg.msg("e", "Wprowadzane konto nie należy do bieżącej grupy. Nie można zapisać");
        } else if (selected.getKwotaWn() != 0.0 && selected.getKwotaMa() != 0.0) {
            Msg.msg("e", "Występują salda po dwóch stronach konta. Nie można zapisać");
        } else {
            if (listaBO.contains(selected)) {
                selected.setWprowadzil(wpisView.getUzer());
                if (selected.getNowy0edycja1usun2Int()!= 0) {
                    selected.setNowy0edycja1usun2(1);
                }
                wierszBODAO.edit(selected);
                if (listaBOFiltered != null) {
                    podsumujWnMa(listaBOFiltered, listaBOsumy);
                    PrimeFaces.current().ajax().update("formbilanswprowadzanie2:sum0");
                }
                Msg.msg("Wyedytowano pozycję");
            } else {
                if (listaBO.size() == 1 && listaBO.get(0).getKonto() == null) {
                    listaBO.remove(listaBO.get(0));
                }
                selected.setWprowadzil(wpisView.getUzer());
                listaBO.add(selected);
                selected.setNowy0edycja1usun2(0);
                wierszBODAO.create(selected);
                if (listaBOFiltered != null) {
                    listaBOFiltered.add(selected);
                    podsumujWnMa(listaBOFiltered, listaBOsumy);
                    PrimeFaces.current().ajax().update("formbilanswprowadzanie2:sum0");
                }
                Msg.msg("Zachowano pozycję");
            }
            ostatniekonto = selected.getKonto();
            selected = new WierszBO(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), walutadomyslna, wpisView.getMiesiacWpisu());
        }
    }


    
    public void dodajewidencjeVAT(WierszBO wierszBO) {
        wierszBOVAT = wierszBO;
        if (wierszBOVAT.geteVatwpisFK()!=null) {
            ewidencjaVatRK = wierszBOVAT.geteVatwpisFK();
        } else {
            ewidencjaVatRK = new EVatwpisFK();
        }
        ewidencjaVatRK.setDataoperacji(wpisView.getRokWpisuSt()+"-01-01");
        ewidencjaVatRK.setDatadokumentu(wpisView.getRokWpisuSt()+"-01-01");
        PrimeFaces.current().ajax().update("dialogewidencjavatRK");
        PrimeFaces.current().executeScript("PF('dialogewidencjavatRK').show()");
            PrimeFaces.current().focus("dialogewidencjavatRK:data1DialogVAT");
    }
    
    public void dolaczWierszEwidencja() {
        try {
            wierszBOVAT.seteVatwpisFK(ewidencjaVatRK);
            wierszBODAO.edit(wierszBOVAT);
            Msg.msg("Dodano ewidencję VAT do wiersza BO");
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd.  Nie dodano ewidencji");
        }
    }
    
    public void edytujWierszEwidencja() {
        try {
            wierszBODAO.edit(wierszBOVAT);
            Msg.msg("Wyedytowano ewidencję VAT do wiersza BO");
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd.  Nie wyedytowano ewidencji");
        }
    }

    

    
    public void updatevatRK() {
        EVatwpisFK e = ewidencjaVatRK;
        Waluty w = wierszBOVAT.getWaluta();
        double kurs = wierszBOVAT.getKurs();
        e.setBrutto(Z.z(e.getNetto() + e.getVat()));
        String update = "ewidencjavatRK:brutto";
        PrimeFaces.current().ajax().update(update);
        String activate = "document.getElementById('ewidencjavatRK:brutto_input').select();";
        PrimeFaces.current().executeScript(activate);
    }
    
    
    public void usunwierszN(WierszBO wierszBO) {
        try {
            usuwaniejeden(listaBO, wierszBO);
            if (listaBOFiltered != null) {
                listaBOFiltered.remove(wierszBO);
                podsumujWnMa(listaBOFiltered, listaBOsumy);
            } else {
                podsumujWnMa(listaBO, listaBOsumy);
            }
            Msg.msg("Usunięto zapis BO z tabeli");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd - nie usunięto zapis BO z tabeli");
        }
    }

    //teraz to nie usuwa tylko oznacza jako usunniete
    //realnie usuwamy jak naniesiemy do dok BO
    public void usunwiele(List<WierszBO> wierszBOlista) {
        try {
            for (WierszBO p : wierszBOlista) {
                if (p.getNowy0edycja1usun2Int() == 0) {
                    wierszBODAO.remove(p);
                    listaBO.remove(p);
                } else {
                    p.setNowy0edycja1usun2(2);
                    wierszBODAO.edit(p);
                }
            }
            if (listaBOFiltered != null) {
                for (WierszBO p : wierszBOlista) {
                    listaBOFiltered.remove(p);
                }
                podsumujWnMa(listaBOFiltered, listaBOsumy);
            } else {
                podsumujWnMa(listaBO, listaBOsumy);
            }
            Msg.msg("Oznaczono zapis BO do usunięcia. Edytuj dokument BO");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd - nie oznaczono zapisu BO do usunięcia");
        }
    }
    
    public void przywrocwiele(List<WierszBO> wierszBOlista) {
        try {
            for (WierszBO p : wierszBOlista) {
                if (p.getNowy0edycja1usun2Int() == 2) {
                    p.setNowy0edycja1usun2(0);
                    wierszBODAO.edit(p);
                }
            }
            if (listaBOFiltered != null) {
                podsumujWnMa(listaBOFiltered, listaBOsumy);
            } else {
                podsumujWnMa(listaBO, listaBOsumy);
            }
            Msg.msg("Przywrocono zapis BO do usunięcia. Edytuj dokument BO");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd - nie przywrocono zapisu BO do usunięcia");
        }
    }
    
    public void usunwielebezBO(List<WierszBO> wierszBOlista) {
        try {
            for (WierszBO p : wierszBOlista) {
                try {
                    StronaWiersza s = stronaWierszaDAO.findStronaByWierszBO(p);
                    if (s!=null) {
                        s.setWierszbo(null);
                        Wiersz w = s.getWiersz();
                        s.setWiersz(null);
                        stronaWierszaDAO.edit(s);
                        s = stronaWierszaDAO.findStronaByWierszBO(p);
                        stronaWierszaDAO.remove(s);
                        if (w!=null) {
                            wierszDAO.remove(w);
                        }
                    }
                } catch (Exception ex) {
                  E.e(ex);
                  Msg.msg("e", "Problem ze stroną wiersza");
                }
                 wierszBODAO.remove(p);
                 listaBO.remove(p);
            }
            if (listaBOFiltered != null) {
                for (WierszBO p : wierszBOlista) {
                    listaBOFiltered.remove(p);
                }
                podsumujWnMa(listaBOFiltered, listaBOsumy);
            } else {
                podsumujWnMa(listaBO, listaBOsumy);
            }
            Msg.msg("Usunięto zapis BO");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd - nie usunięto zapisu BO");
        }
    }

    //usuwanie w filtrowanych jest tak gdzie wywolujaca
    private void usuwaniejeden(List<WierszBO> l, WierszBO wierszBO) {
        try {
            l.remove(wierszBO);
        } catch (Exception e) {

        }
    }

    public void usunnowe() {
        List<WierszBO> listawstepna = Collections.synchronizedList(new ArrayList<>());
        for (List<WierszBO> l : listazbiorcza.values()) {
            listawstepna.addAll(l);
        }
        for (Iterator<WierszBO> it = listawstepna.iterator(); it.hasNext();) {
            WierszBO wb = it.next();
            if (wb.getNowy0edycja1usun2Int()==0) {
                wierszBODAO.remove(wb);
                it.remove();
            }
        }
        init();
        Msg.msg("Usunięto niezaksięgowane nowe pozycje");
    }
    
    public List<WierszBO> zapiszBilansBOdoBazy() {
        List<WierszBO> zachowaneWiersze = Collections.synchronizedList(new ArrayList<>());
        Set<Integer> numerylist = listazbiorcza.keySet();
        for (Integer r : numerylist) {
            List<WierszBO> biezacalista = listazbiorcza.get(r);
            if (biezacalista != null && biezacalista.size() > 0) {
                for (WierszBO p : biezacalista) {
                    if (p.getKonto() != null) {
                        if (tojestbilanslikwidacyjny) {
                            String mc = Data.getMc(wpisView.getPodatnikObiekt().getDataotwarcialikwidacji());
                            String rok = Data.getRok(wpisView.getPodatnikObiekt().getDataotwarcialikwidacji());
                            p.setOtwarcielikwidacji(true);
                            p.setRok(rok);
                            p.setMc(mc);
                        }
                        try {
                            if (p.getWaluta().getSymbolwaluty().equals("PLN")) {
                                p.setKwotaWnPLN(p.getKwotaWn());
                                p.setKwotaMaPLN(p.getKwotaMa());
                            }
                            zachowaneWiersze.add(p);
                        } catch (Exception e) {
                            E.e(e);
                        }
                    }
                }
                wierszBODAO.editList(zachowaneWiersze);
            }
        }
        aktualizujListaW();
        podsumujWnMa(listaW);
        podsumujWnMa(lista0, listaSumList.get(0));
        podsumujWnMa(lista1, listaSumList.get(1));
        podsumujWnMa(lista2, listaSumList.get(2));
        podsumujWnMa(lista3, listaSumList.get(3));
        podsumujWnMa(lista4, listaSumList.get(4));
        podsumujWnMa(lista6, listaSumList.get(6));
        podsumujWnMa(lista7, listaSumList.get(7));
        podsumujWnMa(lista8, listaSumList.get(8));
        Msg.msg("Naniesiono zapisy BO na konta i zapisano do bazy");
        return zachowaneWiersze;
    }
    
    public void zapiszWierszeBOnaKonta() {
        try {
            List<WierszBO> zachowaneWiersze = Collections.synchronizedList(new ArrayList<>());
            Set<Integer> numerylist = listazbiorcza.keySet();
            for (Integer r : numerylist) {
                zachowaneWiersze.addAll(listazbiorcza.get(r));
            }
            if (zachowaneWiersze != null) {
                kontoDAO.wyzerujBoWnBoMawKontach(wpisView);
                List<Konto> listakont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                Set<Konto> kontadosumowania = new HashSet<>();
                for (WierszBO p : zachowaneWiersze) {
                    if (p.getNowy0edycja1usun2Int()!=2 && p.getKonto() != null) {
                        Konto k = listakont.get(listakont.indexOf(p.getKonto()));
                        k.setBoWn(k.getBoWn() + p.getKwotaWnPLN());
                        k.setBoMa(k.getBoMa() + p.getKwotaMaPLN());
                        if (k.getBoWn() != 0.0 || k.getBoMa() != 0.0) {
                            k.setBlokada(true);
                            kontadosumowania.add(k);
                        }
                        kontoDAO.edit(k);
                    }
                }
                List<Konto> listakonta2 = new ArrayList(kontadosumowania);
                obliczsaldoBOkonta(listakonta2);
                edytujsumujdlamacierzystych(listakonta2);
            }
            Msg.msg("Naniesiono BO i obroty rozpoczęcia na konta");
        } catch (Exception e) {
            Msg.msg("e", "Błąd! Nie naniesiono BO i obroty rozpoczęcia na konta");
        }
    }
    
    

    private void edytujsumujdlamacierzystych(List<Konto> przygotowanalista) {
        Set<Konto> listadoprzekazania = new HashSet<>();
        for (Konto k : przygotowanalista) {
            Konto macierzyste = k.getKontomacierzyste();
            if (macierzyste != null) {
                macierzyste.dodajpotomkaBO(k);
                listadoprzekazania.add(macierzyste);
            }
        }
        kontoDAOfk.editList(przygotowanalista);
        if (!listadoprzekazania.isEmpty()) {
            List<Konto> s = new ArrayList(listadoprzekazania);
            edytujsumujdlamacierzystych(s);
        }
    }
    
    private void obliczsaldoBOkonta(List<Konto> przygotowanalista) {
        for (Konto r : przygotowanalista) {
            if (r.getBoWn() > r.getBoMa()) {
                r.setBoWn(Z.z(r.getBoWn() - r.getBoMa()));
                r.setBoMa(0.0);
            } else if (r.getBoWn() < r.getBoMa()) {
                r.setBoMa(Z.z(r.getBoMa() - r.getBoWn()));
                r.setBoWn(0.0);
            } else {
                r.setBoWn(0.0);
                r.setBoMa(0.0);
            }
        }
        kontoDAOfk.editList(przygotowanalista);
    }

    private void aktualizujListaW() {
        listaW = Collections.synchronizedList(new ArrayList<>());
        Set<Integer> numerylist = listazbiorcza.keySet();
        for (Integer r : numerylist) {
            for (WierszBO p : listazbiorcza.get(r)) {
                if (p.getKonto() != null) {
                    listaW.add(p);
                }
            }
        }
    }

//    public int weryfikacjaopisu(WierszBO selected, List<WierszBO> l, String pole) {
//        int licznik = 0;
//        String opis = selected.getOpis().toLowerCase();
//        Konto konto = selected.getKonto();
//        if (konto != null) {
//            for (WierszBO p : l) {
//                if (p.getKonto().equals(konto)) {
//                    String opislista = p.getOpis().toLowerCase();
//                    if (opislista.equals(opis.toLowerCase())) {
//                        licznik++;
//                    }
//                    if (licznik > 0) {
//                        Msg.msg("e", "Taki opis już istnieje na koncie: " + konto.getPelnynumer() + " opis: " + opis);
//                        selected.setOpis("zmień opis");
//                        PrimeFaces.current().ajax().update(pole);
//                        return 1;
//                    }
//                }
//            }
//        }
//        return 0;
//    }


    
    public void przepiszkurs(WierszBO wiersz, double kwota, String strona) {
        if (kwota != 0.0) {
            if (strona.equals("Wn")) {
                wiersz.setKwotaWnPLN(kwota);
            } else {
                wiersz.setKwotaMaPLN(kwota);
            }
        }
    }


    public void przewalutuj(WierszBO wiersz, double kurs, double kwotaWwalucie, String strona, int idx, String tab) {
        String w = null;
        if (kurs != 0.0 && !wiersz.getWaluta().getSymbolwaluty().equals("PLN")) {
            double kwotawPLN = Math.round(kwotaWwalucie * kurs * 100);
            kwotawPLN /= 100;
            if (strona.equals("Wn")) {
                w = "formbilanswprowadzanie:tabviewbilans:" + tab + ":" + idx + ":Wnpln";
                wiersz.setKwotaWnPLN(kwotawPLN);
            } else {
                w = "formbilanswprowadzanie:tabviewbilans:" + tab + ":" + idx + ":Mapln";
                wiersz.setKwotaMaPLN(kwotawPLN);
            }

            PrimeFaces.current().ajax().update(w);
        } else if (wiersz.getWaluta().getSymbolwaluty().equals("PLN")) {
            if (strona.equals("Wn")) {
                w = "formbilanswprowadzanie:tabviewbilans:" + tab + ":" + idx + ":Wnpln";
                wiersz.setKwotaWnPLN(kwotaWwalucie);
            } else {
                w = "formbilanswprowadzanie:tabviewbilans:" + tab + ":" + idx + ":Mapln";
                wiersz.setKwotaMaPLN(kwotaWwalucie);
            }
            PrimeFaces.current().ajax().update(w);
        }
    }

    public void przewalutujN(double kurs, double kwotaWwalucie, String strona) {
        String w = null;
        if (kurs != 0.0 && !selected.getWaluta().getSymbolwaluty().equals("PLN")) {
            double kwotawPLN = Math.round(kwotaWwalucie * kurs * 100);
            kwotawPLN /= 100;
            if (strona.equals("Wn")) {
                w = "formbilanswprowadzanie2_wiersz:Wnpln";
                selected.setKwotaWnPLN(kwotawPLN);
            } else {
                w = "formbilanswprowadzanie2_wiersz:Mapln";
                selected.setKwotaMaPLN(kwotawPLN);
            }
            PrimeFaces.current().ajax().update(w);
        } else if (selected.getWaluta().getSymbolwaluty().equals("PLN")) {
            if (strona.equals("Wn")) {
                w = "formbilanswprowadzanie2_wiersz:Wnpln";
                selected.setKwotaWnPLN(kwotaWwalucie);
            } else {
                w = "formbilanswprowadzanie2_wiersz:Mapln";
                selected.setKwotaMaPLN(kwotaWwalucie);
            }
            PrimeFaces.current().ajax().update(w);
        }
    }


    public void obliczkursN(double kurs, double kwotaWwalucie, double kwotaWPLN, String WnMa) {
        String w = null;
         if (kurs == 0.0 && !selected.getWaluta().getSymbolwaluty().equals("PLN")) {
            if (kwotaWwalucie != 0.0) {
                selected.setKurs(Z.z4(kwotaWPLN / kwotaWwalucie));
            } else {
                selected.setKurs(0.0);
            }
            w = "formbilanswprowadzanie2_wiersz:polekurs";
            PrimeFaces.current().ajax().update(w);
        } else if (kurs != 0.0 && !selected.getWaluta().getSymbolwaluty().equals("PLN") && kwotaWwalucie == 0.0) {
            double kwotaWwaluciewyliczona = Z.z(kwotaWPLN / kurs);
            if (WnMa.equals("Wn")) {
                w = "formbilanswprowadzanie2_wiersz:Wnwal";
                selected.setKwotaWn(kwotaWwaluciewyliczona);
            } else {
                w = "formbilanswprowadzanie2_wiersz:Mawal";
                selected.setKwotaMa(kwotaWwaluciewyliczona);
            }
            PrimeFaces.current().ajax().update(w);
        }
    }


    public void przeliczkursN() {
        if (!selected.getWaluta().getSymbolwaluty().equals("PLN")) {
            double kurs = selected.getKurs();
            if (kurs != 0.0) {
                if (selected.getKwotaWn() != 0.0) {
                    double kwotawPLN = Math.round(selected.getKwotaWn() * kurs * 100);
                    kwotawPLN /= 100;
                    selected.setKwotaWnPLN(kwotawPLN);
                } else if (selected.getKwotaMa() != 0.0) {
                    double kwotawPLN = Math.round(selected.getKwotaMa() * kurs * 100);
                    kwotawPLN /= 100;
                    selected.setKwotaMaPLN(kwotawPLN);
                }
            } else {
                selected.setKwotaWnPLN(0.0);
                selected.setKwotaMaPLN(0.0);
            }
            podsumujWnMa(listaW);
        } else if (selected.getKwotaWn() != 0.0 || selected.getKwotaMa() != 0.0) {
            selected.setKurs(0.0);
            selected.setKwotaWnPLN(selected.getKwotaWn());
            selected.setKwotaMaPLN(selected.getKwotaMa());
            podsumujWnMa(listaW);
        }

    }

    public void ksiegujrozrachunki() {
        List<WierszBO> zachowaneWiersze = wierszBODAO.findPodatnikRokRozrachunkowe(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        for (WierszBO p : zachowaneWiersze) {
            double kwotaWn = p.getKwotaWn();
            double kwotaMa = p.getKwotaMa();
            if (kwotaWn != 0) {
                StronaWiersza stronaWiersza = new StronaWiersza(p, "Wn");
                stronaWierszaDAO.create(stronaWiersza);
            } else if (kwotaMa != 0) {
                StronaWiersza stronaWiersza = new StronaWiersza(p, "Ma");
                stronaWierszaDAO.create(stronaWiersza);
            }
        }
    }

    public void generowanieDokumentuBO(String typ) {
        List<WierszBO> zachowaneWiersze = zapiszBilansBOdoBazy();
//        int nrkolejny = oblicznumerkolejny();
//        if (nrkolejny > 1) {
//        zachowajtransakcje(typ);
        usundokumentztegosamegomiesiaca(typ);
//        }
        Dokfk dok = stworznowydokument(1, zachowaneWiersze, typ);
        dok.przeliczKwotyWierszaDoSumyDokumentu();
        try {
            dokDAOfk.create(dok);
            for (WierszBO p : zachowaneWiersze) {
                p.setNowy0edycja1usun2(3);
            }
            wierszBODAO.editList(zachowaneWiersze);
            wierszedousuniecia = Collections.synchronizedList(new ArrayList<>());
            if (typ.equals("BO")) {
                isteniejeDokBO = true;
                dokumentBO = dok;
                Msg.msg("Zaksięgowano dokument BO");
            } else {
                isteniejeDokBOR = true;
                dokumentBOR = dok;
                Msg.msg("Zaksięgowano dokument BOR");
            }
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu BO/BOR");
        }
    }

    public void edytowanieDokumentuBO(String typ) {
        List<WierszBO> zachowaneWiersze = zapiszBilansBOdoBazy();
        dokumentBO = dokDAOfk.findDokfkLastofaTypeMc(wpisView.getPodatnikObiekt(), typ, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        edytujwiersze(dokumentBO, zachowaneWiersze);
        usunwiersze(dokumentBO, zachowaneWiersze);
        dokumentBO.przeliczKwotyWierszaDoSumyDokumentu();
        try {
            dokDAOfk.edit(dokumentBO);
            for (Iterator<WierszBO> it = zachowaneWiersze.iterator(); it.hasNext();) {
                WierszBO p = it.next();
                if (p.getNowy0edycja1usun2Int() == 2) {
                    wierszBODAO.remove(p);
                    usunwierszzlisty(p);
                    it.remove();
                } else {
                    p.setNowy0edycja1usun2(3);
                }
            }
            wierszBODAO.editList(zachowaneWiersze);
            if (typ.equals("BO")) {
                dokumentBO = dokDAOfk.findDokfkLastofaTypeMc(wpisView.getPodatnikObiekt(), "BO", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                Msg.msg("Naniesiono zmiany w dokumencie BO");
            } else {
                dokumentBOR = dokDAOfk.findDokfkLastofaTypeMc(wpisView.getPodatnikObiekt(), "BOR", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                Msg.msg("Naniesiono zmiany w dokumencie BOR");
            }
            wierszedousuniecia = Collections.synchronizedList(new ArrayList<>());
            init();
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd - nie zmieniono dokumentu BO/BOR");
        }
    }

   private void usunwierszzlisty(WierszBO wiersz) {
       Set<Integer> numerylist = listazbiorcza.keySet();
        for (Integer r : numerylist) {
            List<WierszBO> biezacalista = listazbiorcza.get(r);
            if (biezacalista != null && biezacalista.size() > 0) {
                for (Iterator<WierszBO> it = biezacalista.iterator(); it.hasNext();) {
                    WierszBO w = it.next();
                    if (w.equals(wiersz)) {
                        it.remove();
                    }
                }
            }
        }
   }
    
//    private int oblicznumerkolejny() {
//        Dokfk poprzednidokumentvat = dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikObiekt(), "BO", wpisView.getRokWpisuSt());
//        return poprzednidokumentvat == null ? 1 : poprzednidokumentvat.getNrkolejnywserii() + 1;
//    }
//
//    private void zachowajtransakcje(String typ) {
//        Dokfk popDokfk = dokDAOfk.findDokfofaType(wpisView.getPodatnikObiekt(), typ, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
//        Set<Transakcja> pobranetransakcje = new HashSet<>();
//        for (StronaWiersza s : popDokfk.getStronyWierszy()) {
//            pobranetransakcje.addAll(s.getNowetransakcje());
//            pobranetransakcje.addAll(s.getPlatnosci());
//        }
//        List<TransakcjaAku> przchowalnia = new ArrayList<>();
//        int lp = 0;
//        for (Transakcja p: pobranetransakcje) {
//            TransakcjaAku nowa = new TransakcjaAku(lp++,p);
//        }
//    }
    private void usundokumentztegosamegomiesiaca(String typ) {
        Dokfk popDokfk = dokDAOfk.findDokfofaType(wpisView.getPodatnikObiekt(), typ, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        if (popDokfk != null) {
            dokDAOfk.remove(popDokfk);
        }
    }

    private Dokfk stworznowydokument(int nrkolejny, List<WierszBO> zachowaneWiersze, String typ) {
        Dokfk nd = new Dokfk(nrkolejny, wpisView.getRokWpisuSt());
        ustawdaty(nd);
        ustawkontrahenta(nd);
        ustawnumerwlasny(nd, nrkolejny);
        if (typ.equals("BO")) {
            nd.setOpisdokfk("bilans otwarcia roku: " + wpisView.getRokWpisuSt());
        } else {
            nd.setOpisdokfk("obroty rozpoczęcia na koniec: " + wpisView.getRokWpisuSt()+"/"+wpisView.getMiesiacWpisu());
        }
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        nd.setWprowadzil(wpisView.getUzer().getLogin());
        ustawrodzajedok(nd, typ);
        ustawtabelenbp(nd);
        ustawwiersze(nd, zachowaneWiersze);
        return nd;
    }

    private void ustawdaty(Dokfk nd) {
        String datadokumentu = wpisView.getRokWpisuSt() + "-" + wpisView.getMiesiacWpisu() + "-01";
        nd.setDatadokumentu(datadokumentu);
        nd.setDataoperacji(datadokumentu);
        nd.setDatawplywu(datadokumentu);
        nd.setDatawystawienia(datadokumentu);
        nd.setDataujecia(new Date());
        nd.setMiesiac(wpisView.getMiesiacWpisu());
        nd.setVatM(wpisView.getMiesiacWpisu());
        nd.setVatR(wpisView.getRokWpisuSt());
    }

    private void ustawkontrahenta(Dokfk nd) {
        try {
            Klienci k = klienciDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
            nd.setKontr(k);
        } catch (Exception e) {

        }
    }

    private void ustawnumerwlasny(Dokfk nd, int nrkolejny) {
        String numer = nrkolejny+"/" + wpisView.getRokWpisuSt() + "/BO";
        nd.setNumerwlasnydokfk(numer);
    }

    private void ustawrodzajedok(Dokfk nd, String typ) {
        Rodzajedok rodzajedok = rodzajedokDAO.find(typ, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        if (rodzajedok != null) {
            nd.setSeriadokfk(rodzajedok.getSkrot());
            nd.setRodzajedok(rodzajedok);
        } else {
            Msg.msg("e", "Brak zdefiniowanego dokumentu BO/BOR");
        }
    }

    private void ustawtabelenbp(Dokfk nd) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        nd.setTabelanbp(t);
        Waluty w = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        nd.setWalutadokumentu(w);
    }

   
    private void ustawwiersze(Dokfk nd, List<WierszBO> listabiezaca) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        int idporzadkowy = 1;
        if (listabiezaca != null && listabiezaca.size() > 0) {
            for (WierszBO p : listabiezaca) {
                if (p != null && (p.getKwotaWn() != 0.0 || p.getKwotaMa() != 0.0 || p.getKwotaWnPLN() != 0.0 || p.getKwotaMaPLN() != 0.0)) {
                    Wiersz w = new Wiersz(idporzadkowy++, nd, 0);
                    uzupelnijwiersz(w, nd);
                    if (p.getKonto().getPelnynumer().equals("202-1-5")) {
                    }
                    String opiswiersza = "zapis BO: " + p.getOpis();
                    if (!wpisView.getMiesiacWpisu().equals("01")) {
                        opiswiersza = "kwota obrotów: " + p.getOpis();
                    }
                    w.setOpisWiersza(opiswiersza);
                    if (Z.z(p.getKwotaWn()) != 0.0 || Z.z(p.getKwotaWnPLN()) != 0.0) {
                        uzupelnijTworzonyWiersz(w, p, "Wn", 1);
                    } else if (Z.z(p.getKwotaMa()) != 0.0 || Z.z(p.getKwotaMaPLN()) != 0.0) {
                        uzupelnijTworzonyWiersz(w, p, "Ma", 2);
                    }
                    nd.getListawierszy().add(w);
                }
            }
        }
    }

    private void uzupelnijTworzonyWiersz(Wiersz w, WierszBO p, String wnma, Integer typWiersza) {
        w.setTypWiersza(typWiersza);
        StronaWiersza st = null;
        if (wnma.equals("Wn")) {
            st = new StronaWiersza(w, wnma, Z.z(p.getKwotaWn()), p.getKonto());
        } else {
            st = new StronaWiersza(w, wnma, Z.z(p.getKwotaMa()), p.getKonto());
        }
        if (p.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
            st.setNowatransakcja(true);
        }
        st.setWierszbo(p);
        st.setKursBO(p.getKurs());
        st.setSymbolWalutyBO(p.getWaluta().getSymbolwaluty());
        st.setOpisBO(p.getOpis());
        if (wnma.equals("Wn")) {
            st.setKwotaPLN(p.getKwotaWnPLN());
        } else {
            st.setKwotaPLN(p.getKwotaMaPLN());
        }
        st.setTypStronaWiersza(9);
        if (wnma.equals("Wn")) {
            w.setStronaWn(st);
        } else {
            w.setStronaMa(st);
        }
    }

    private void edytujwiersze(Dokfk nd, List<WierszBO> zachowaneWiersze) {
        List<Wiersz> wiersze = nd.getListawierszy();
        int idporzadkowy = wiersze.size() + 1;
        for (Iterator<WierszBO> it = zachowaneWiersze.iterator(); it.hasNext();) {
            WierszBO p = it.next();
            if (p.getKonto().getPelnynumer().equals("132-2")) {
                error.E.s("");
            }
            if (p.getNowy0edycja1usun2Int()!=3) {
                Wiersz wierszwdokumencie = niezawierategokonta(wiersze, p);
                if (wierszwdokumencie == null && (Z.z(p.getKwotaWn()) != 0.0 || Z.z(p.getKwotaWnPLN()) != 0.0 || Z.z(p.getKwotaMa()) != 0.0 || Z.z(p.getKwotaMaPLN()) != 0.0)) {
                    Wiersz w = new Wiersz(idporzadkowy++, nd, 0);
                    uzupelnijwiersz(w, nd);
                    String opiswiersza = "zapis BO: " + p.getOpis();
                    if (!wpisView.getMiesiacWpisu().equals("01")) {
                        opiswiersza = "kwota obrotów: " + p.getOpis();
                    }
                    w.setOpisWiersza(opiswiersza);
                    if (p.getKwotaWn() != 0.0 || p.getKwotaWnPLN()!= 0.0) {
                        generujStronaWierszaWn(p, w);
                    } else if (p.getKwotaMa() != 0.0 || p.getKwotaMaPLN() != 0.0) {
                        generujStronaWierszaMa(p, w);
                    }
                    nd.getListawierszy().add(w);
                } else if (wierszwdokumencie != null && (Z.z(p.getKwotaWn()) == 0.0 && Z.z(p.getKwotaMa()) == 0.0) && Z.z(p.getKwotaWnPLN()) == 0.0 && Z.z(p.getKwotaMaPLN()) == 0.0) {
                    usunWiersz(wierszwdokumencie);
                } else if (wierszwdokumencie != null && wierszwdokumencie.getStronaWn() != null && (p.getKwotaWn() != 0.0  || p.getKwotaWnPLN()!= 0.0)) {
                    edytujKwotaWiersz(p, wierszwdokumencie.getStronaWn(), "Wn");
                } else if (wierszwdokumencie != null && wierszwdokumencie.getStronaMa() != null && (p.getKwotaMa() != 0.0 || p.getKwotaMaPLN() != 0.0)) {
                    edytujKwotaWiersz(p, wierszwdokumencie.getStronaMa(), "Ma");
                } else if (wierszwdokumencie != null && wierszwdokumencie.getStronaWn() != null && (p.getKwotaMa() != 0.0 || p.getKwotaMaPLN() != 0.0)) {
                    usunStronaWiersza(wierszwdokumencie, "Wn");
                    generujStronaWierszaMa(p, wierszwdokumencie);
                } else if (wierszwdokumencie != null && wierszwdokumencie.getStronaMa() != null && (p.getKwotaWn() != 0.0  || p.getKwotaWnPLN()!= 0.0)) {
                    usunStronaWiersza(wierszwdokumencie, "Ma");
                    generujStronaWierszaWn(p, wierszwdokumencie);
                }
                if (Z.z(p.getKwotaWn()) == 0.0 && Z.z(p.getKwotaMa()) == 0.0 && Z.z(p.getKwotaWnPLN()) == 0.0 && Z.z(p.getKwotaMaPLN()) == 0.0) {
                    wierszBODAO.remove(p);
                    it.remove();
                }
            }
        }
    }
    
    private void generujStronaWierszaWn(WierszBO p, Wiersz w) {
        w.setTypWiersza(1);
        StronaWiersza st = new StronaWiersza(w, "Wn", p.getKwotaWn(), p.getKonto());
        if (p.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
            st.setNowatransakcja(true);
        }
        st.setKursBO(p.getKurs());
        st.setSymbolWalutyBO(p.getWaluta().getSymbolwaluty());
        st.setOpisBO(p.getOpis());
        st.setKwotaPLN(p.getKwotaWnPLN());
        st.setTypStronaWiersza(9);
        st.setWierszbo(p);
        w.setStronaWn(st);
    }
    
    private void generujStronaWierszaMa(WierszBO p, Wiersz w) {
        w.setTypWiersza(2);
        StronaWiersza st = new StronaWiersza(w, "Ma", p.getKwotaMa(), p.getKonto());
        if (p.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
            st.setNowatransakcja(true);
        }
        st.setKursBO(p.getKurs());
        st.setSymbolWalutyBO(p.getWaluta().getSymbolwaluty());
        st.setOpisBO(p.getOpis());
        st.setKwotaPLN(p.getKwotaMaPLN());
        st.setTypStronaWiersza(9);
        st.setWierszbo(p);
        w.setStronaMa(st);
    }
    
    private void edytujKwotaWiersz(WierszBO p, StronaWiersza s, String WnMa) {
        if (WnMa.equals("Wn")) {
            s.setKwota(p.getKwotaWn());
            s.setKwotaPLN(p.getKwotaWnPLN());
            s.setKwotaWaluta(p.getKwotaWn());
            s.setKursBO(p.getKurs());
            s.setSymbolWalutyBO(p.getWaluta().getSymbolwaluty());
            if (s.getWierszbo() == null) {
                s.setWierszbo(p);
            }
        } else {
            s.setKwota(p.getKwotaMa());
            s.setKwotaPLN(p.getKwotaMaPLN());
            s.setKwotaWaluta(p.getKwotaMa());
            s.setKursBO(p.getKurs());
            s.setSymbolWalutyBO(p.getWaluta().getSymbolwaluty());
            if (s.getWierszbo() == null) {
                s.setWierszbo(p);
            }
        }
    }
    
    private void usunWiersz(Wiersz w) {
        w.getDokfk().getListawierszy().remove(w);
    }
    private void usunStronaWiersza(Wiersz w, String WnMa) {
        w.removeStrona(WnMa);
    }

    private void usunwiersze(Dokfk nd, List<WierszBO> zachowaneWiersze) {
        List<Wiersz> wiersze = nd.getListawierszy();
        if (!zachowaneWiersze.isEmpty()) {
            boolean czyedytowac = false;
            for (WierszBO p : zachowaneWiersze) {
                Wiersz w = zawieratokonto(wiersze, p);
                if (w != null && p.getNowy0edycja1usun2Int() == 2) {
                    if (w.getStronaWn()!=null) {
                        w.getStronaWn().setWiersz(null);
                    }
                    if (w.getStronaMa()!=null) {
                        w.getStronaMa().setWiersz(null);
                    }
                    wiersze.remove(w);
                    czyedytowac = true;
                }
            }
            if (czyedytowac) {
                if (wiersze.isEmpty()) {
                    dokDAOfk.remove(nd);
                    isteniejeDokBO = false;
                    dokumentBO = null;
                } else {
                    nd.przenumeruj();;
                    dokDAOfk.edit(nd);
                }
            }

        }
    }

    private Wiersz niezawierategokonta(List<Wiersz> wiersze, WierszBO w) {
        Wiersz niezawiera = null;
        for (Wiersz p : wiersze) {
            if (p.jest0niejest1(w, wpisView.getMiesiacWpisu()) == false) {
                niezawiera = p;
                break;
            }
        }
        return niezawiera;
    }

    private Wiersz zawieratokonto(List<Wiersz> wiersze, WierszBO w) {
        Wiersz znaleziony = null;
        for (Wiersz p : wiersze) {
            if (p.jest0niejest1(w, wpisView.getMiesiacWpisu()) == false) {
                znaleziony = p;
                break;
            }
        }
        return znaleziony;
    }

    private void uzupelnijwiersz(Wiersz w, Dokfk nd) {
        w.setDokfk(nd);
        w.setLpmacierzystego(0);
        w.setTabelanbp(w.getTabelanbp());
        w.setDataksiegowania(nd.getDatawplywu());
    }

    private void podsumujWnMa(List<WierszBO> lista) {
        stronaWn = 0.0;
        stronaMa = 0.0;
        for (WierszBO p : lista) {
            stronaWn += p.getKwotaWnPLN();
            stronaMa += p.getKwotaMaPLN();
        }
        stronaWn = Z.z(stronaWn);
        stronaMa = Z.z(stronaMa);
        stronaWn_stronaMa = Z.z(stronaWn-stronaMa);
        //PrimeFaces.current().ajax().update("formbilanswprowadzanie3:kwotysum");
    }

    public void podsumujWnMa(List<WierszBO> lista, List listasum) {
        listasum.clear();
        double stronaWn = 0.0;
        double stronaMa = 0.0;
        double stronaWnpln = 0.0;
        double stronaMapln = 0.0;
        for (WierszBO p : lista) {
            //to jest żeby nie sumował oznaczone jako usunięte
            if (p.getNowy0edycja1usun2Int() != 2) {
                stronaWn += p.getKwotaWn();
                stronaMa += p.getKwotaMa();
                stronaWnpln += p.getKwotaWnPLN();
                stronaMapln += p.getKwotaMaPLN();
            }
        }
        listasum.add(stronaWn);
        listasum.add(stronaMa);
        listasum.add(Z.z(stronaWn - stronaMa));
        listasum.add(stronaWnpln);
        listasum.add(stronaMapln);
        listasum.add(Z.z(stronaWnpln - stronaMapln));
    }

    public void podsumujWnMa(List<WierszBO> listas, List listasum, List<WierszBO> lista) {
        listasum.clear();
        double stronaWn = 0.0;
        double stronaMa = 0.0;
        double stronaWnpln = 0.0;
        double stronaMapln = 0.0;
        List<WierszBO> l = Collections.synchronizedList(new ArrayList<>());
        if (listas != null && !listas.isEmpty()) {
                l = listas;
        } else if (listaBOFiltered != null && !listaBOFiltered.isEmpty()) {
            l = listaBOFiltered;
        } else {
            l = lista;
        }
        for (WierszBO p : l) {
                stronaWn += p.getKwotaWn();
                stronaMa += p.getKwotaMa();
                stronaWnpln += p.getKwotaWnPLN();
                stronaMapln += p.getKwotaMaPLN();
        }
        listasum.add(stronaWn);
        listasum.add(stronaMa);
        listasum.add(Z.z(stronaWn - stronaMa));
        listasum.add(stronaWnpln);
        listasum.add(stronaMapln);
        listasum.add(Z.z(stronaWnpln - stronaMapln));
    }

    public void drukuj(List<WierszBO> lista) {
        PdfWierszBO.drukujWierszeBO(lista, wpisView);
    }

    public void drukujListaKonsolidacyjna(List<WierszBO> lista) {
        PdfWierszBO.drukujListaKonsolidacyjna(lista, wpisView);
    }

//<editor-fold defaultstate="collapsed" desc="comment">
    public List<WierszBO> getLista0() {
        return lista0;
    }

    public void setLista0(List<WierszBO> lista0) {
        this.lista0 = lista0;
    }

    public List<Waluty> getWprowadzonesymbolewalut() {
        return wprowadzonesymbolewalut;
    }

    public void setWprowadzonesymbolewalut(List<Waluty> wprowadzonesymbolewalut) {
        this.wprowadzonesymbolewalut = wprowadzonesymbolewalut;
    }

    public boolean isTojestbilanslikwidacyjny() {
        return tojestbilanslikwidacyjny;
    }

    public void setTojestbilanslikwidacyjny(boolean tojestbilanslikwidacyjny) {
        this.tojestbilanslikwidacyjny = tojestbilanslikwidacyjny;
    }

    public boolean isPokazstarekonta() {
        return pokazstarekonta;
    }

    public void setPokazstarekonta(boolean pokazstarekonta) {
        this.pokazstarekonta = pokazstarekonta;
    }

    public String getMiesiacWpisu() {
        return miesiacWpisu;
    }

    public void setMiesiacWpisu(String miesiacWpisu) {
        this.miesiacWpisu = miesiacWpisu;
    }

    public List<WierszBO> getListaBOFiltered() {
        return listaBOFiltered;
    }

    public void setListaBOFiltered(List<WierszBO> listaBOFiltered) {
        this.listaBOFiltered = listaBOFiltered;
    }

    public double getStronaWn_stronaMa() {
        return stronaWn_stronaMa;
    }

    public void setStronaWn_stronaMa(double stronaWn_stronaMa) {
        this.stronaWn_stronaMa = stronaWn_stronaMa;
    }

    public Konto getOstatniekonto() {
        return ostatniekonto;
    }

    public void setOstatniekonto(Konto ostatniekonto) {
        this.ostatniekonto = ostatniekonto;
    }

    public List<WierszBO> getListaBOsumy() {
        return listaBOsumy;
    }

    public void setListaBOsumy(List<WierszBO> listaBOsumy) {
        this.listaBOsumy = listaBOsumy;
    }

    public WierszBO getSelected() {
        return selected;
    }

    public void setSelected(WierszBO selected) {
        this.selected = selected;
    }

    public List<WierszBO> getListaBO() {
        return listaBO;
    }

    public void setListaBO(List<WierszBO> listaBO) {
        this.listaBO = listaBO;
    }

    public boolean isIsteniejeDokBO() {
        return isteniejeDokBO;
    }

    public void setIsteniejeDokBO(boolean isteniejeDokBO) {
        this.isteniejeDokBO = isteniejeDokBO;
    }

    public List<WierszBO> getLista1() {
        return lista1;
    }

    public void setLista1(List<WierszBO> lista1) {
        this.lista1 = lista1;
    }

    public boolean isSortujwgwartosci() {
        return sortujwgwartosci;
    }

    public void setSortujwgwartosci(boolean sortujwgwartosci) {
        this.sortujwgwartosci = sortujwgwartosci;
    }

    public List<WierszBO> getLista2() {
        return lista2;
    }

    public void setLista2(List<WierszBO> lista2) {
        this.lista2 = lista2;
    }

    public List<WierszBO> getLista3() {
        return lista3;
    }

    public void setLista3(List<WierszBO> lista3) {
        this.lista3 = lista3;
    }

    public List<WierszBO> getLista6() {
        return lista6;
    }

    public void setLista6(List<WierszBO> lista6) {
        this.lista6 = lista6;
    }

    public List<WierszBO> getLista8() {
        return lista8;
    }

    public void setLista8(List<WierszBO> lista8) {
        this.lista8 = lista8;
    }

    public List<WierszBO> getLista0s() {
        return lista0s;
    }

    public void setLista0s(List<WierszBO> lista0s) {
        this.lista0s = lista0s;
    }

    public List<WierszBO> getLista1s() {
        return lista1s;
    }

    public void setLista1s(List<WierszBO> lista1s) {
        this.lista1s = lista1s;
    }

    public List<WierszBO> getLista2s() {
        return lista2s;
    }

    public void setLista2s(List<WierszBO> lista2s) {
        this.lista2s = lista2s;
    }

    public List<WierszBO> getLista3s() {
        return lista3s;
    }

    public void setLista3s(List<WierszBO> lista3s) {
        this.lista3s = lista3s;
    }

    public List<WierszBO> getLista6s() {
        return lista6s;
    }

    public void setLista6s(List<WierszBO> lista6s) {
        this.lista6s = lista6s;
    }

    public List<WierszBO> getLista8s() {
        return lista8s;
    }

    public void setLista8s(List<WierszBO> lista8s) {
        this.lista8s = lista8s;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Map<Integer, List<WierszBO>> getListazbiorcza() {
        return listazbiorcza;
    }

    public void setListazbiorcza(Map<Integer, List<WierszBO>> listazbiorcza) {
        this.listazbiorcza = listazbiorcza;
    }

    public List<WierszBO> getListaW() {
        return listaW;
    }

    public void setListaW(List<WierszBO> listaW) {
        this.listaW = listaW;
    }

    public double getStronaWn() {
        return stronaWn;
    }

    public void setStronaWn(double stronaWn) {
        this.stronaWn = stronaWn;
    }

    public List<WierszBO> getListaBOselected() {
        return listaBOselected;
    }

    public void setListaBOselected(List<WierszBO> listaBOselected) {
        this.listaBOselected = listaBOselected;
    }

    public Map<Integer, List> getListaSumList() {
        return listaSumList;
    }

    public void setListaSumList(Map<Integer, List> listaSumList) {
        this.listaSumList = listaSumList;
    }

    public List<WierszBO> getListaWKonsolidacja() {
        return listaWKonsolidacja;
    }

    public void setListaWKonsolidacja(List<WierszBO> listaWKonsolidacja) {
        this.listaWKonsolidacja = listaWKonsolidacja;
    }

    public DataTable getListaBOdatatable() {
        return listaBOdatatable;
    }

    public void setListaBOdatatable(DataTable listaBOdatatable) {
        this.listaBOdatatable = listaBOdatatable;
    }

    public double getStronaMa() {
        return stronaMa;
    }

    public void setStronaMa(double stronaMa) {
        this.stronaMa = stronaMa;
    }

    public List<WierszBO> getLista4() {
        return lista4;
    }

    public void setLista4(List<WierszBO> lista4) {
        this.lista4 = lista4;
    }

    public List<WierszBO> getLista7() {
        return lista7;
    }

    public void setLista7(List<WierszBO> lista7) {
        this.lista7 = lista7;
    }

    public List<WierszBO> getLista4s() {
        return lista4s;
    }

    public void setLista4s(List<WierszBO> lista4s) {
        this.lista4s = lista4s;
    }

    public List<WierszBO> getLista7s() {
        return lista7s;
    }

    public void setLista7s(List<WierszBO> lista7s) {
        this.lista7s = lista7s;
    }

    public boolean isTojestgenerowanieobrotow() {
        return tojestgenerowanieobrotow;
    }

    public void setTojestgenerowanieobrotow(boolean tojestgenerowanieobrotow) {
        this.tojestgenerowanieobrotow = tojestgenerowanieobrotow;
    }

    public List<WierszBO> getListaBOs1() {
        return listaBOs1;
    }

    public void setListaBOs1(List<WierszBO> listaBOs1) {
        this.listaBOs1 = listaBOs1;
    }

    public Integer getNraktualnejlisty() {
        return nraktualnejlisty;
    }

    public void setNraktualnejlisty(Integer nraktualnejlisty) {
        this.nraktualnejlisty = nraktualnejlisty;
    }

//</editor-fold>
    private void usunpodwojnekontawListaW() {
        Map<String, WierszBO> nowewiersze = new ConcurrentHashMap<>();
        for (WierszBO p : listaW) {
            WierszBO w = null;
            if (p.getKonto() != null) {
                nowewiersze.get(p.getKonto().getPelnynumer());
            }
            if (w == null && p.getKonto() != null) {
                nowewiersze.put(p.getKonto().getPelnynumer(), p);
            } else if (w != null && p.getKonto() != null) {
                dodajwierszBO(w, p);
            }
        }
        listaWKonsolidacja = Collections.synchronizedList(new ArrayList<>());
        listaWKonsolidacja.addAll(nowewiersze.values());
        Collections.sort(listaWKonsolidacja, new WierszBOcomparator());
    }

    private void dodajwierszBO(WierszBO stary, WierszBO nowy) {
        stary.setKwotaMa(stary.getKwotaMa() + nowy.getKwotaMa());
        stary.setKwotaWn(stary.getKwotaWn() + nowy.getKwotaWn());
        stary.setKwotaMaPLN(stary.getKwotaMaPLN() + nowy.getKwotaMaPLN());
        stary.setKwotaWnPLN(stary.getKwotaWnPLN() + nowy.getKwotaWnPLN());
    }


    public void wklejostatniekonto() {
        if (ostatniekonto != null && selected.getKonto() == null) {
            selected.setKonto(ostatniekonto);
        }
    }

    public void drukujBO() {
        List<WierszBO> w = Collections.synchronizedList(new ArrayList<>());
        if (listaBOselected != null && listaBOselected.size() > 0) {
            w = listaBOselected;
        } else if (listaBOs1 != null && listaBOs1.size() > 0) {
            w = listaBOs1;
        } else if (listaBOFiltered != null && listaBOFiltered.size() >0) {
            w = listaBOFiltered;
        } else if (listaBO != null && listaBO.size() > 0) {
            w = listaBO;
        }
        if (sortujwgwartosci) {
            sortujliste(w);
        }
        dodajwierszsumy(w);
        PdfWierszBO.drukujWierszeBO(w, wpisView);
    }
    
     public void drukujdrukujObrotyWszystkieBO() {
        List<WierszBO> w = Collections.synchronizedList(new ArrayList<>());
        for (List<WierszBO> l : listaGrupa.values()) {
            if (l.size() > 0 && l.get(0) != null && l.get(0).getKonto() != null) {
                w.addAll(l);
            }
        }
        if (sortujwgwartosci) {
            sortujliste(w);
        }
        dodajwierszsumy(w);
        PdfWierszBO.drukujWierszeBO(w, wpisView);
    }
    
    public void drukujObroty() {
        List<WierszBO> w = Collections.synchronizedList(new ArrayList<>());
        if (listaBOselected != null && listaBOselected.size() > 0) {
            w = listaBOselected;
        } else if (listaBOs1 != null && listaBOs1.size() > 0) {
            w = listaBOs1;
        } else if (listaBOFiltered != null && listaBOFiltered.size() >0) {
            w = listaBOFiltered;
        } else if (listaBO != null && listaBO.size() > 0) {
            w = listaBO;
        }
        if (sortujwgwartosci) {
            sortujliste(w);
        }
        dodajwierszsumy(w);
        PdfWierszBO.drukujWierszeObroty(w, wpisView);
    }
    
    public void drukujObrotyWszystkie() {
        List<WierszBO> w = Collections.synchronizedList(new ArrayList<>());
        for (List<WierszBO> l : listaGrupa.values()) {
            if (l.get(0) != null && l.get(0).getKonto() != null) {
                w.addAll(l);
            }
        }
        if (sortujwgwartosci) {
            sortujliste(w);
        }
        dodajwierszsumy(w);
        PdfWierszBO.drukujWierszeObroty(w, wpisView);
    }

    private void sortujliste(List<WierszBO> w) {
        Collections.sort(w, new WierszBOcomparatorKwota());
    }

    private void dodajwierszsumy(List<WierszBO> w) {
        double wn = 0.0;
        double ma = 0.0;
    }

    public boolean filterByKonto(Object value, Object filter, Locale locale) {
        Konto k = (Konto) value;
        String f = (String) filter;
        Integer i = null;
        boolean zwrot = false;
        try {
            i = Integer.parseInt(f.substring(0, 1));
        } catch (Exception e) {
            E.e(e);
        }
        if (i != null) {
            if (f.endsWith(" ")) {
                if (k.getPelnynumer().contains(f.trim())) {
                    zwrot = true;
                }
            }
            if (k.getPelnynumer().startsWith(f)) {
                zwrot = true;
            }
            if (f.trim().matches("^(.*\\s+.*)+$") && f.length() > 6) {
                String numeropis = k.getPelnynumer() + " " + k.getNazwapelna().toLowerCase();
                if (numeropis.startsWith(f.toLowerCase())) {
                    zwrot = true;
                }
            }
        } else if (k.getNazwapelna().toLowerCase().contains(f.toLowerCase())) {
            zwrot = true;
        }
        return zwrot;
    }

    public void doksiegujroznice(TreeNodeExtended<Konto> root) {
        walutadomyslna = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        List<Object> listazwrotnapozycji = new ArrayList<>();
        root.getFinallChildrenData(new ArrayList<TreeNodeExtended>(), listazwrotnapozycji);
        List<Object> kontozroznica = listazwrotnapozycji.stream().filter(((p)->(((Konto)p).getRozBOWn() || ((Konto)p).getRozBOMa()))).collect(Collectors.toList());
        for (Object p : kontozroznica) {
            Konto konto = (Konto) p;
            double roznicaWn = konto.getRozBOWnKwota();
            double roznicaMa = konto.getRozBOMaKwota();
            List<WierszBO> wierszebodlakonta = wierszBODAO.findPodatnikRokKonto(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), konto);
            double sumaWnPlusMaMinus = podsumujWierszBO(wierszebodlakonta);
            double sumaWn = sumaWnPlusMaMinus>0.0?sumaWnPlusMaMinus:0.0;
            double sumaMa = sumaWnPlusMaMinus<0.0?-sumaWnPlusMaMinus:0.0;
            double dodoksiegownia = 0.0;
            WierszBO wierszroznicowy = null;
            if (roznicaWn>0.0 && sumaWnPlusMaMinus>0.0) {
               dodoksiegownia = sumaWn+roznicaWn;
               wierszroznicowy = new WierszBO(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), "01", konto, dodoksiegownia, 0.0, walutadomyslna, wpisView.getUzer());
            } else if (roznicaMa>0.0 && sumaWnPlusMaMinus<0.0){
                dodoksiegownia = sumaMa+roznicaMa;
                wierszroznicowy = new WierszBO(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), "01", konto, 0.0, dodoksiegownia, walutadomyslna, wpisView.getUzer());
            } else if (roznicaWn<0.0 && sumaWnPlusMaMinus<0.0) {
                dodoksiegownia = Math.abs(roznicaWn+sumaWnPlusMaMinus);
                wierszroznicowy = new WierszBO(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), "01", konto, dodoksiegownia, 0.0, walutadomyslna, wpisView.getUzer());
            } else if (roznicaMa<0.0 && sumaWnPlusMaMinus>0.0){
                dodoksiegownia = Math.abs(roznicaMa-sumaWnPlusMaMinus);
                wierszroznicowy = new WierszBO(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), "01", konto, 0.0, dodoksiegownia, walutadomyslna, wpisView.getUzer());
            }
            if (wierszroznicowy!=null) {
                wierszBODAO.create(wierszroznicowy);
                Msg.msg("Zaksięgowano wiersz różnicowy dale konta "+konto.getPelnynumer());
            }
        }
        System.out.println("");
    }
    
    private double podsumujWierszBO(List<WierszBO> wierszebodlakonta) {
        double zwrot = 0.0;
        if (wierszebodlakonta!=null) {
            for (WierszBO p : wierszebodlakonta) {
                if (p.getKwotaWnPLN()!=0.0) {
                    zwrot = zwrot +p.getKwotaWnPLN();
                } else if (p.getKwotaMaPLN()!=0.0) {
                    zwrot = zwrot -p.getKwotaMaPLN();
                }
            }
        }
        return zwrot;
    }

    public void oznaczjakonowy(WierszBO wierszBO) {
        if (wierszBO!=null) {
            wierszBO.setNowy0edycja1usun2(0);
            wierszBODAO.edit(wierszBO);
            Msg.msg("Oznaczono jako nowy");
        }
    }
    
    public static void main(String[] args) {
        double roznicaWn = 650.0;
        double roznicaMa = -2150.0;
        double sumaWnPlusMaMinus = 650;
        double sumaWn = sumaWnPlusMaMinus>0.0?sumaWnPlusMaMinus:0.0;
        double sumaMa = sumaWnPlusMaMinus<0.0?-sumaWnPlusMaMinus:0.0;
        double dodoksiegownia = 0.0;
        WierszBO wierszroznicowy = null;
        if (roznicaWn>0.0 && roznicaMa==0.0 && sumaWnPlusMaMinus>0.0) {
            dodoksiegownia = sumaWn+roznicaWn;
            wierszroznicowy = new WierszBO(new Podatnik(), "2020", "01", new Konto(), dodoksiegownia, 0.0, new Waluty(), new Uz());
        } else if (roznicaMa>0.0 && roznicaWn == 0.0 && sumaWnPlusMaMinus<0.0){
            dodoksiegownia = sumaMa+roznicaMa;
            wierszroznicowy = new WierszBO(new Podatnik(), "2020", "01", new Konto(), 0.0, dodoksiegownia, new Waluty(), new Uz());
        } else if (roznicaWn<0.0 && sumaWnPlusMaMinus<0.0) {
            dodoksiegownia = Math.abs(roznicaWn+sumaWnPlusMaMinus);
            wierszroznicowy = new WierszBO(new Podatnik(), "2020", "01", new Konto(), dodoksiegownia, 0.0, new Waluty(), new Uz());
        } else if (roznicaMa<0.0 && sumaWnPlusMaMinus>0.0){
            dodoksiegownia = Math.abs(roznicaMa-sumaWnPlusMaMinus);
            wierszroznicowy = new WierszBO(new Podatnik(), "2020", "01", new Konto(), 0.0, dodoksiegownia, new Waluty(), new Uz());
        }
        System.out.println("koniec");
        System.out.println("Wn "+wierszroznicowy.getKwotaWnPLN());
        System.out.println("Ma "+wierszroznicowy.getKwotaMaPLN());
    }

    public boolean isIsteniejeDokBOR() {
        return isteniejeDokBOR;
    }

    public void setIsteniejeDokBOR(boolean isteniejeDokBOR) {
        this.isteniejeDokBOR = isteniejeDokBOR;
    }

    public EVatwpisFK getEwidencjaVatRK() {
        return ewidencjaVatRK;
    }

    public void setEwidencjaVatRK(EVatwpisFK ewidencjaVatRK) {
        this.ewidencjaVatRK = ewidencjaVatRK;
    }

    public WierszBO getWierszBOVAT() {
        return wierszBOVAT;
    }

    public void setWierszBOVAT(WierszBO wierszBOVAT) {
        this.wierszBOVAT = wierszBOVAT;
    }

    public List<Evewidencja> getListaewidencjivatRK() {
        return listaewidencjivatRK;
    }

    public void setListaewidencjivatRK(List<Evewidencja> listaewidencjivatRK) {
        this.listaewidencjivatRK = listaewidencjivatRK;
    }

    public boolean isEwidencjaVATRKzapis0edycja1() {
        return ewidencjaVATRKzapis0edycja1;
    }

    public void setEwidencjaVATRKzapis0edycja1(boolean ewidencjaVATRKzapis0edycja1) {
        this.ewidencjaVATRKzapis0edycja1 = ewidencjaVATRKzapis0edycja1;
    }

  

    
    
    

    
}
