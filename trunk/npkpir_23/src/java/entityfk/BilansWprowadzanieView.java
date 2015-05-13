/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import comparator.WierszBOcomparator;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import dao.StronaWierszaDAO;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import daoFK.WierszBODAO;
import entity.Klienci;
import entity.Podatnik;
import entity.Rodzajedok;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import pdffk.PdfWierszBO;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class BilansWprowadzanieView implements Serializable {

    @Inject
    private WierszBODAO wierszBODAO;
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

    private List<WierszBO> lista0;
    private List<WierszBO> lista1;
    private List<WierszBO> lista2;
    private List<WierszBO> lista3;
    private List<WierszBO> lista6;
    private List<WierszBO> lista8;
    private List<WierszBO> listaW;
    private Map<Integer, List<WierszBO>> listazbiorcza;
    private Map<String, List> listaSumList;
    private double stronaWn;
    private double stronaMa;

    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public BilansWprowadzanieView() {
        this.lista0 = new ArrayList<>();
        this.lista1 = new ArrayList<>();
        this.lista2 = new ArrayList<>();
        this.lista3 = new ArrayList<>();
        this.lista6 = new ArrayList<>();
        this.lista8 = new ArrayList<>();
        this.listaSumList = new HashMap<>();
        listaSumList.put("lista0", new ArrayList());
        listaSumList.put("lista1", new ArrayList());
        listaSumList.put("lista2", new ArrayList());
        listaSumList.put("lista3", new ArrayList());
        listaSumList.put("lista6", new ArrayList());
        listaSumList.put("lista8", new ArrayList());
        tworzListeZbiorcza();
    }

    @PostConstruct
    private void init() {
        Podatnik p = wpisView.getPodatnikObiekt();
        String r = wpisView.getRokWpisuSt();
        Waluty w = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        this.listaW = new ArrayList<>();
        this.lista0.addAll(wierszBODAO.lista("0%", wpisView));
        if (lista0.isEmpty()) {
            this.lista0.add(new WierszBO(p, r, w));
        } else {
            this.listaW.addAll(this.lista0);
        }
        this.lista1.addAll(wierszBODAO.lista("1%", wpisView));
        if (lista1.isEmpty()) {
            this.lista1.add(new WierszBO(p, r, w));
        } else {
            this.listaW.addAll(this.lista1);
        }
        this.lista2.addAll(wierszBODAO.lista("2%", wpisView));
        if (lista2.isEmpty()) {
            this.lista2.add(new WierszBO(p, r, w));
        } else {
            this.listaW.addAll(this.lista2);
        }
        this.lista3.addAll(wierszBODAO.lista("3%", wpisView));
        if (lista3.isEmpty()) {
            this.lista3.add(new WierszBO(p, r, w));
        } else {
            this.listaW.addAll(this.lista3);
        }
        this.lista6.addAll(wierszBODAO.lista("6%", wpisView));
        if (lista6.isEmpty()) {
            this.lista6.add(new WierszBO(p, r, w));
        } else {
            this.listaW.addAll(this.lista6);
        }
        this.lista8.addAll(wierszBODAO.lista("8%", wpisView));
        if (lista8.isEmpty()) {
            this.lista8.add(new WierszBO(p, r, w));
        } else {
            this.listaW.addAll(this.lista8);
        }
        Collections.sort(lista0, new WierszBOcomparator());
        Collections.sort(lista1, new WierszBOcomparator());
        Collections.sort(lista2, new WierszBOcomparator());
        Collections.sort(lista3, new WierszBOcomparator());
        Collections.sort(lista6, new WierszBOcomparator());
        Collections.sort(lista8, new WierszBOcomparator());
        Collections.sort(listaW, new WierszBOcomparator());
        podsumujWnMa(listaW);
        podsumujWnMa(lista0, listaSumList.get("lista0"));
        podsumujWnMa(lista1, listaSumList.get("lista1"));
        podsumujWnMa(lista2, listaSumList.get("lista2"));
        podsumujWnMa(lista3, listaSumList.get("lista3"));
        podsumujWnMa(lista6, listaSumList.get("lista6"));
        podsumujWnMa(lista8, listaSumList.get("lista8"));
    }

    private void tworzListeZbiorcza() {
        this.listazbiorcza = new HashMap<>();
        this.listazbiorcza.put(0, lista0);
        this.listazbiorcza.put(1, lista1);
        this.listazbiorcza.put(2, lista2);
        this.listazbiorcza.put(3, lista3);
        this.listazbiorcza.put(6, lista6);
        this.listazbiorcza.put(8, lista8);
    }

    public void dodajwiersz(int kategoria) {
        switch (kategoria) {
            case 0:
                dodawanielista(lista0);
                podsumujWnMa(lista0, listaSumList.get("lista0"));
                break;
            case 1:
                dodawanielista(lista1);
                podsumujWnMa(lista1, listaSumList.get("lista1"));
                break;
            case 2:
                dodawanielista(lista2);
                podsumujWnMa(lista2, listaSumList.get("lista2"));
                break;
            case 3:
                dodawanielista(lista3);
                podsumujWnMa(lista3, listaSumList.get("lista3"));
                break;
            case 6:
                dodawanielista(lista6);
                podsumujWnMa(lista6, listaSumList.get("lista6"));
                break;
            case 8:
                dodawanielista(lista8);
                podsumujWnMa(lista8, listaSumList.get("lista8"));
                break;
        }
        podsumujWnMa(listaW);
        RequestContext.getCurrentInstance().update("formbilanswprowadzanie:kwotysum");

    }

    private void dodawanielista(List<WierszBO> l) {
        Waluty w = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        Podatnik p = wpisView.getPodatnikObiekt();
        String r = wpisView.getRokWpisuSt();
        l.add(new WierszBO(p, r, w));
    }

    public void usunwiersz(int kategoria, WierszBO wierszBO) {
        switch (kategoria) {
            case 0:
                usuwanielista(lista0, wierszBO);
                podsumujWnMa(lista0, listaSumList.get("lista0"));
                break;
            case 1:
                usuwanielista(lista1, wierszBO);
                podsumujWnMa(lista1, listaSumList.get("lista1"));
                break;
            case 2:
                usuwanielista(lista2, wierszBO);
                podsumujWnMa(lista2, listaSumList.get("lista2"));
                break;
            case 3:
                usuwanielista(lista3, wierszBO);
                podsumujWnMa(lista3, listaSumList.get("lista3"));
                break;
            case 6:
                usuwanielista(lista6, wierszBO);
                podsumujWnMa(lista6, listaSumList.get("lista6"));
                break;
            case 8:
                usuwanielista(lista8, wierszBO);
                podsumujWnMa(lista8, listaSumList.get("lista8"));
                break;
        }
    }

    private void usuwanielista(List<WierszBO> l, WierszBO wierszBO) {
        try {
            Waluty w = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
            Podatnik p = wpisView.getPodatnikObiekt();
            String r = wpisView.getRokWpisuSt();
            if (l.size() > 1) {
                wierszBODAO.destroy(wierszBO);
                l.remove(wierszBO);
            } else {
                wierszBODAO.destroy(wierszBO);
                l.remove(wierszBO);
                l.add(new WierszBO(p, r, w));
            }
        } catch (Exception e) {

        }
    }

    public void zapiszBilansBOdoBazy() {
        Set<Integer> numerylist = listazbiorcza.keySet();
        int flagaOK = 0;
        for (Integer r : numerylist) {
            List<WierszBO> biezacalista = listazbiorcza.get(r);
            if (biezacalista != null && biezacalista.size() > 0) {
                for (WierszBO p : biezacalista) {
                    if (p.getKonto() != null) {
                        if (p.getKonto().getPelnynumer().equals("261-1")) {
                            flagaOK = weryfikacjaopisuZapis(p, listazbiorcza.get(r));
                        }
                        if (p.getKonto() != null && flagaOK == 0) {
                            try {
                                if (p.getWaluta().getSymbolwaluty().equals("PLN")) {
                                    p.setKwotaWnPLN(p.getKwotaWn());
                                    p.setKwotaMaPLN(p.getKwotaMa());
                                }
                                wierszBODAO.edit(p);
                            } catch (Exception e) {
                                if (e.getStackTrace() != null) {
                                    System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString());
                                } else {
                                    System.out.println("Blad "+e.toString());
                                }
                            }
                        }
                    }
                }
            }
        }
        if (flagaOK == 0) {
            List<WierszBO> zachowaneWiersze = wierszBODAO.findPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            List<Konto> listakont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            for (Konto p : listakont) {
                p.setBoWn(0.0);
                p.setBoMa(0.0);
                p.setBlokada(false);
            }
            kontoDAO.editList(listakont);
            for (WierszBO p : zachowaneWiersze) {
                Konto k = listakont.get(listakont.indexOf(p.getKonto()));
                k.setBoWn(k.getBoWn() + p.getKwotaWnPLN());
                k.setBoMa(k.getBoMa() + p.getKwotaMaPLN());
                if (k.getBoWn() > 0.0 || k.getBoMa() > 0.0) {
                    k.setBlokada(true);
                }
                kontoDAO.edit(k);
            }
            aktualizujListaW();
            podsumujWnMa(listaW);
            podsumujWnMa(lista0, listaSumList.get("lista0"));
            podsumujWnMa(lista1, listaSumList.get("lista1"));
            podsumujWnMa(lista2, listaSumList.get("lista2"));
            podsumujWnMa(lista3, listaSumList.get("lista3"));
            podsumujWnMa(lista6, listaSumList.get("lista6"));
            podsumujWnMa(lista8, listaSumList.get("lista8"));
            Msg.msg("Naniesiono zapisy BO");
        } else {
            Msg.msg("e", "Sprawdź opisy przy kontach. Niektóre się powtarzają. Nie można zachować bilansu");
        }
    }

    private void aktualizujListaW() {
        listaW = new ArrayList<>();
        Set<Integer> numerylist = listazbiorcza.keySet();
        for (Integer r : numerylist) {
            for (WierszBO p : listazbiorcza.get(r)) {
                if (p.getKonto() != null) {
                    listaW.add(p);
                }
            }
        }
    }

    public int weryfikacjaopisu(String opis, List<WierszBO> l) {
        int licznik = 0;
        String nrkonta = null;
        for (WierszBO p : l) {
            if (p.getWierszBOPK().getOpis().equals(opis)) {
                if (p.getKonto() != null) {
                    nrkonta = p.getKonto().getPelnynumer();
                    licznik++;
                }
            }
            if (licznik > 1) {
                Msg.msg("e", "Taki opis już istnieje na koncie: " + nrkonta + " opis: " + opis);
                return 1;
            }
        }
        return 0;
    }

    public int weryfikacjaopisuZapis(WierszBO wiersz, List<WierszBO> l) {
        int licznik = 0;
        String nrkonta = null;
        for (WierszBO wb : l) {
            if (wb.getKonto().getPelnynumer().equals("261-1")) {
                System.out.println("ll");
            }
            boolean konto = wb.getKonto().getPelnynumer().equals(wiersz.getKonto().getPelnynumer());
            boolean opis = wb.getWierszBOPK().getOpis().equals(wiersz.getWierszBOPK().getOpis());
            if (konto && opis) {
                nrkonta = wb.getKonto().getPelnynumer();
                licznik++;
            }
            if (licznik > 1) {
                Msg.msg("e", "Duplikat opisu dla konta: " + nrkonta + " opis: " + wiersz.getWierszBOPK().getOpis());
                return 1;
            }
        }
        return 0;
    }
    
    public void przepiszkurs(WierszBO wiersz, double kwota, String strona) {
        if (kwota != 0.0) {
            if (strona.equals("Wn")) {
                wiersz.setKwotaWnPLN(kwota);
            } else {
                wiersz.setKwotaMaPLN(kwota);
            }
        }
    }

    public void przeliczkurs(WierszBO wiersz, double kurs, double kwotaWwalucie, String strona) {
        if (kurs != 0.0) {
            double kwotawPLN = Math.round(kwotaWwalucie * kurs * 100);
            kwotawPLN /= 100;
            if (strona.equals("Wn")) {
                wiersz.setKwotaWnPLN(kwotawPLN);
            } else {
                wiersz.setKwotaMaPLN(kwotawPLN);
            }
        } else {
            if (strona.equals("Wn")) {
                wiersz.setKwotaWnPLN(kwotaWwalucie);
            } else {
                wiersz.setKwotaMaPLN(kwotaWwalucie);
            }
        }
    }

    public void przeliczkurs(WierszBO wiersz) {
        double kurs = wiersz.getKurs();
        if (wiersz.getKwotaWn() != 0.0) {
            double kwotawPLN = Math.round(wiersz.getKwotaWn() * kurs * 100);
            kwotawPLN /= 100;
            wiersz.setKwotaWnPLN(kwotawPLN);
        } else if (wiersz.getKwotaMa() != 0.0) {
            double kwotawPLN = Math.round(wiersz.getKwotaMa() * kurs * 100);
            kwotawPLN /= 100;
            wiersz.setKwotaMaPLN(kwotawPLN);
        }
        podsumujWnMa(listaW);
    }

    public void ksiegujrozrachunki() {
        List<WierszBO> zachowaneWiersze = wierszBODAO.findPodatnikRokRozrachunkowe(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        for (WierszBO p : zachowaneWiersze) {
            double kwotaWn = p.getKwotaWn();
            double kwotaMa = p.getKwotaMa();
            if (kwotaWn != 0) {
                StronaWiersza stronaWiersza = new StronaWiersza(p, "Wn");
                stronaWierszaDAO.dodaj(stronaWiersza);
            } else if (kwotaMa != 0) {
                StronaWiersza stronaWiersza = new StronaWiersza(p, "Ma");
                stronaWierszaDAO.dodaj(stronaWiersza);
            }
        }
    }

    public void generowanieDokumentuBO() {
        int nrkolejny = oblicznumerkolejny();
        if (nrkolejny > 1) {
            usundokumentztegosamegomiesiaca(nrkolejny);
        }
        Dokfk dokumentvat = stworznowydokument(nrkolejny);
        try {
            dokDAOfk.dodaj(dokumentvat);
            Msg.msg("Zaksięgowano dokument BO");
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu BO");
        }
    }

    private int oblicznumerkolejny() {
        Dokfk poprzednidokumentvat = dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikObiekt(), "BO", wpisView.getRokWpisuSt());
        return poprzednidokumentvat == null ? 1 : poprzednidokumentvat.getDokfkPK().getNrkolejnywserii() + 1;
    }

    private void usundokumentztegosamegomiesiaca(int numerkolejny) {
        Dokfk popDokfk = dokDAOfk.findDokfofaType(wpisView.getPodatnikObiekt(), "BO", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        if (popDokfk != null) {
            dokDAOfk.destroy(popDokfk);
        }
    }

    private Dokfk stworznowydokument(int nrkolejny) {
        Dokfk nd = new Dokfk("BO", nrkolejny, wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        ustawdaty(nd);
        ustawkontrahenta(nd);
        ustawnumerwlasny(nd);
        nd.setOpisdokfk("bilans otwarcia roku: " + wpisView.getRokWpisuSt());
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        ustawrodzajedok(nd);
        ustawtabelenbp(nd);
        ustawwiersze(nd);
        return nd;
    }

    private void ustawdaty(Dokfk nd) {
        String datadokumentu = wpisView.getRokWpisuSt() + "-" + wpisView.getMiesiacWpisu() + "-01";
        nd.setDatadokumentu(datadokumentu);
        nd.setDataoperacji(datadokumentu);
        nd.setDatawplywu(datadokumentu);
        nd.setDatawystawienia(datadokumentu);
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

    private void ustawnumerwlasny(Dokfk nd) {
        String numer = "1/" + wpisView.getRokWpisuSt() + "/BO";
        nd.setNumerwlasnydokfk(numer);
    }

    private void ustawrodzajedok(Dokfk nd) {
        Rodzajedok rodzajedok = rodzajedokDAO.find("BO", wpisView.getPodatnikObiekt());
        if (rodzajedok != null) {
            nd.setRodzajedok(rodzajedok);
        } else {
            Msg.msg("e", "Brak zdefiniowanego dokumentu BO");
        }
    }

    private void ustawtabelenbp(Dokfk nd) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        nd.setTabelanbp(t);
        Waluty w = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        nd.setWalutadokumentu(w);
    }

    private void ustawwiersze(Dokfk nd) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        int idporzadkowy = 1;
        Set<Integer> numerylist = listazbiorcza.keySet();
        for (Integer r : numerylist) {
            List<WierszBO> listabiezaca = listazbiorcza.get(r);
            if (listabiezaca != null && listabiezaca.size() > 0) {
                for (WierszBO p : listabiezaca) {
                    if (p != null && (p.getKwotaWn() != 0 || p.getKwotaMa() != 0)) {
                        Wiersz w = new Wiersz(idporzadkowy++, 0);
                        uzupelnijwiersz(w, nd);
                        String opiswiersza = "zapis BO: " + p.getWierszBOPK().getOpis();
                        w.setOpisWiersza(opiswiersza);
                        if (p.getKwotaWn() != 0) {
                            w.setTypWiersza(1);
                            StronaWiersza st = new StronaWiersza(w, "Wn", p.getKwotaWn(), p.getKonto());
                            if (p.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
                                st.setNowatransakcja(true);
                            }
                            st.setKursBO(p.getKurs());
                            st.setSymbolWalutyBO(p.getWaluta().getSymbolwaluty());
                            st.setOpisBO(p.getWierszBOPK().getOpis());
                            st.setKwotaPLN(p.getKwotaWnPLN());
                            st.setTypStronaWiersza(9);
                            w.setStronaWn(st);
                        } else if (p.getKwotaMa() != 0) {
                            w.setTypWiersza(2);
                            StronaWiersza st = new StronaWiersza(w, "Ma", p.getKwotaMa(), p.getKonto());
                            if (p.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
                                st.setNowatransakcja(true);
                            }
                            st.setKursBO(p.getKurs());
                            st.setSymbolWalutyBO(p.getWaluta().getSymbolwaluty());
                            st.setOpisBO(p.getWierszBOPK().getOpis());
                            st.setKwotaPLN(p.getKwotaMaPLN());
                            st.setTypStronaWiersza(9);
                            w.setStronaMa(st);
                        }
                        nd.getListawierszy().add(w);
                    }
                }
            }
        }
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
        RequestContext.getCurrentInstance().update("formbilanswprowadzanie:kwotysum");
    }

    private void podsumujWnMa(List<WierszBO> lista, List listasum) {
        listasum.clear();
        double stronaWn = 0.0;
        double stronaMa = 0.0;
        for (WierszBO p : lista) {
            stronaWn += p.getKwotaWnPLN();
            stronaMa += p.getKwotaMaPLN();
        }
        listasum.add(stronaWn);
        listasum.add(stronaMa);
        RequestContext.getCurrentInstance().update("formbilanswprowadzanie");
    }
    
    public void drukuj(List<WierszBO> lista) {
        PdfWierszBO.drukujRKK(lista, wpisView);
    }

//<editor-fold defaultstate="collapsed" desc="comment">
    public List<WierszBO> getLista0() {
        return lista0;
    }

    public void setLista0(List<WierszBO> lista0) {
        this.lista0 = lista0;
    }

    public List<WierszBO> getLista1() {
        return lista1;
    }

    public void setLista1(List<WierszBO> lista1) {
        this.lista1 = lista1;
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

    public Map<String, List> getListaSumList() {
        return listaSumList;
    }

    public void setListaSumList(Map<String, List> listaSumList) {
        this.listaSumList = listaSumList;
    }

    public double getStronaMa() {
        return stronaMa;
    }

    public void setStronaMa(double stronaMa) {
        this.stronaMa = stronaMa;
    }

//</editor-fold>
}
