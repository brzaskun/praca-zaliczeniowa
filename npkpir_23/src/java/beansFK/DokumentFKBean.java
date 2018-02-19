/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import dao.KlienciDAO;
import dao.RodzajedokDAO;
import daoFK.CechazapisuDAOfk;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import data.Data;
import embeddablefk.ListaSum;
import entity.Klienci;
import entity.Rodzajedok;
import entity.UmorzenieN;
import entityfk.Cechazapisu;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.MiejscePrzychodow;
import entityfk.StowNaliczenie;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Transakcja;
import entityfk.Wiersz;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named

public class DokumentFKBean implements Serializable {

    public static Dokfk generujdokument(WpisView wpisView, KlienciDAO klienciDAO, String symbokdok, String opisdok, RodzajedokDAO rodzajedokDAO, TabelanbpDAO tabelanbpDAO, KontoDAOfk kontoDAOfk, List wiersze, DokDAOfk dokDAOfk) {
        Dokfk nowydok = stworznowydokument(wpisView, klienciDAO, symbokdok, opisdok, rodzajedokDAO, tabelanbpDAO, dokDAOfk);
        switch (symbokdok) {
            case "RRK":
                ustawwierszeRKK(1, nowydok, wiersze, wpisView, kontoDAOfk, tabelanbpDAO);
                break;
            case "AMO":
                ustawwierszeAMO(nowydok, wiersze, wpisView, kontoDAOfk, tabelanbpDAO);
                break;
        }
        if (nowydok.getListawierszy() != null) {
            nowydok.przeliczKwotyWierszaDoSumyDokumentu();
        }
        return nowydok;
    }
    
    public static Dokfk generujdokumentSkladki(WpisView wpisView, KlienciDAO klienciDAO, String symbokdok, String opisdok, RodzajedokDAO rodzajedokDAO, TabelanbpDAO tabelanbpDAO, Konto kontoWn, Konto kontoMa, KontoDAOfk kontoDAOfk, List wiersze, DokDAOfk dokDAOfk) {
        Dokfk nowydok = stworznowydokumentPK(wpisView, klienciDAO, symbokdok, opisdok, rodzajedokDAO, tabelanbpDAO, dokDAOfk);
        ustawwierszeSkladki(nowydok, wiersze, wpisView, kontoWn, kontoMa,kontoDAOfk, tabelanbpDAO, opisdok);
        if (nowydok.getListawierszy() != null) {
            nowydok.przeliczKwotyWierszaDoSumyDokumentu();
        }
        return nowydok;
    }
    
    public static Dokfk generujdokumentAutomRozrach(WpisView wpisView, KlienciDAO klienciDAO, String symbokdok, String opisdok, RodzajedokDAO rodzajedokDAO, TabelanbpDAO tabelanbpDAO, KontoDAOfk kontoDAOfk, List konta, Map<String, ListaSum> sumy, DokDAOfk dokDAOfk, CechazapisuDAOfk cechazapisuDAOfk) {
        Dokfk nowydok = stworznowydokument(wpisView, klienciDAO, symbokdok, opisdok, rodzajedokDAO, tabelanbpDAO, dokDAOfk);
        Cechazapisu nkup = cechazapisuDAOfk.findPodatniknkup();
        nowydok.setNumerwlasnydokfk(DokFKBean.wygenerujnumerkolejnyRozrach(nowydok, wpisView, dokDAOfk));
        ustawwierszePK(nowydok, konta, new ArrayList<ListaSum>(sumy.values()), wpisView, kontoDAOfk, nkup);
        if (nowydok.getListawierszy() != null) {
            nowydok.przeliczKwotyWierszaDoSumyDokumentu();
        }
        return nowydok;
    }
    
    public static Dokfk generujdokumentAutomSaldo(WpisView wpisView, KlienciDAO klienciDAO, String symbokdok, String opisdok, RodzajedokDAO rodzajedokDAO, TabelanbpDAO tabelanbpDAO, KontoDAOfk kontoDAOfk, List konta, double[] rownicawnroznicama, DokDAOfk dokDAOfk) {
        Dokfk nowydok = stworznowydokument(wpisView, klienciDAO, symbokdok, opisdok, rodzajedokDAO, tabelanbpDAO, dokDAOfk);
        nowydok.setNumerwlasnydokfk(DokFKBean.wygenerujnumerkolejnyRozrach(nowydok, wpisView, dokDAOfk));
        ustawwierszePKSaldo(nowydok, konta, rownicawnroznicama, wpisView, kontoDAOfk, tabelanbpDAO);
        if (nowydok.getListawierszy() != null) {
            nowydok.przeliczKwotyWierszaDoSumyDokumentu();
        }
        return nowydok;
    }

    private static Dokfk stworznowydokument(WpisView wpisView, KlienciDAO klienciDAO, String symbokdok, String opisdok, RodzajedokDAO rodzajedokDAO, TabelanbpDAO tabelanbpDAO, DokDAOfk dokDAOfk) {
        int numerkolejny = oblicznumerkolejny(dokDAOfk, wpisView, symbokdok);
        Dokfk nd = new Dokfk();
        nd.setSeriadokfk(symbokdok);
        nd.setNrkolejnywserii(numerkolejny);
        nd.setRok(wpisView.getRokWpisuSt());
        ustawdaty(nd, wpisView);
        ustawkontrahenta(nd, klienciDAO, wpisView);
        ustawnumerwlasny(nd, wpisView, symbokdok);
        ustawopis(nd, wpisView, opisdok);
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        nd.setWprowadzil(wpisView.getWprowadzil().getLogin());
        ustawrodzajedok(nd, symbokdok, rodzajedokDAO, wpisView);
        ustawtabelenbp(nd, tabelanbpDAO);
        return nd;
    }
    
    private static Dokfk stworznowydokumentPK(WpisView wpisView, KlienciDAO klienciDAO, String symbokdok, String opisdok, RodzajedokDAO rodzajedokDAO, TabelanbpDAO tabelanbpDAO, DokDAOfk dokDAOfk) {
        int numerkolejny = oblicznumerkolejny(dokDAOfk, wpisView, symbokdok);
        Dokfk nd = new Dokfk();
        nd.setSeriadokfk(symbokdok);
        nd.setNrkolejnywserii(numerkolejny);
        nd.setRok(wpisView.getRokWpisuSt());
        ustawdaty(nd, wpisView);
        ustawkontrahenta(nd, klienciDAO, wpisView);
        nd.setNumerwlasnydokfk(oblicznumerwlasnykolejny(dokDAOfk, wpisView, symbokdok));
        ustawopis(nd, wpisView, opisdok);
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        nd.setWprowadzil(wpisView.getWprowadzil().getLogin());
        ustawrodzajedok(nd, symbokdok, rodzajedokDAO, wpisView);
        ustawtabelenbp(nd, tabelanbpDAO);
        return nd;
    }

    private static int oblicznumerkolejny(DokDAOfk dokDAOfk, WpisView wpisView, String symbokdok) {
        Dokfk poprzednidokumentvat = dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikObiekt(), symbokdok, wpisView.getRokWpisuSt());
        return poprzednidokumentvat == null ? 1 : poprzednidokumentvat.getNrkolejnywserii() + 1;
    }
    
    private static String oblicznumerwlasnykolejny(DokDAOfk dokDAOfk, WpisView wpisView, String symbokdok) {
        Dokfk poprzednidokumentvat = dokDAOfk.findDokfkLastofaTypeMc(wpisView.getPodatnikObiekt(), symbokdok, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        return poprzednidokumentvat == null ?  "1/"+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisuSt()+"/PK" : zwieksznumerojeden(poprzednidokumentvat.getNumerwlasnydokfk());
    }
    
  
    private static void ustawdaty(Dokfk nd, WpisView wpisView) {
        String datadokumentu = Data.ostatniDzien(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        nd.setDatadokumentu(datadokumentu);
        nd.setDataoperacji(datadokumentu);
        nd.setDatawplywu(datadokumentu);
        nd.setDatawystawienia(datadokumentu);
        nd.setMiesiac(wpisView.getMiesiacWpisu());
        nd.setVatM(wpisView.getMiesiacWpisu());
        nd.setVatR(wpisView.getRokWpisuSt());
        nd.setDataujecia(new Date());
    }

    private static void ustawkontrahenta(Dokfk nd, KlienciDAO klienciDAO, WpisView wpisView) {
        try {
            Klienci k = klienciDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
            nd.setKontr(k);
        } catch (Exception e) {
            E.e(e);

        }
    }
    

     
    private static void ustawnumerwlasny(Dokfk nd, WpisView wpisView, String symbokdok) {
        StringBuilder sb = new StringBuilder();
        sb.append("1/");
        sb.append(wpisView.getMiesiacWpisu());
        sb.append("/");
        sb.append(wpisView.getRokWpisuSt());
        sb.append("/");
        sb.append(symbokdok);
        nd.setNumerwlasnydokfk(sb.toString());
    }

    private static void ustawopis(Dokfk nd, WpisView wpisView, String opisdok) {
        StringBuilder sb = new StringBuilder();
        sb.append(opisdok);
        sb.append(" za ");
        sb.append(wpisView.getMiesiacWpisu());
        sb.append("/");
        sb.append(wpisView.getRokWpisuSt());
        nd.setOpisdokfk(sb.toString());
    }

    private static void ustawrodzajedok(Dokfk nd, String symbokdok, RodzajedokDAO rodzajedokDAO, WpisView wpisView) {
        Rodzajedok rodzajedok = rodzajedokDAO.find(symbokdok, wpisView.getPodatnikObiekt());
        if (rodzajedok != null) {
            nd.setSeriadokfk(rodzajedok.getSkrot());
            nd.setRodzajedok(rodzajedok);
        } else {
            Msg.msg("e", "Brak zdefiniowanego dokumentu " + symbokdok);
        }
    }

    private static void ustawtabelenbp(Dokfk nd, TabelanbpDAO tabelanbpDAO) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        nd.setTabelanbp(t);
        nd.setWalutadokumentu(t.getWaluta());
    }

    private static void ustawwierszeRKK(int idporzadkowy, Dokfk nd, List pobranetransakcje, WpisView wpisView, KontoDAOfk kontoDAOfk, TabelanbpDAO tabelanbpDAO) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        Konto kontoRozniceKursowe = kontoDAOfk.findKonto("755", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        Konto przychodyfinansowe = kontoDAOfk.findKonto("756", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        Konto kosztyfinansowe = kontoDAOfk.findKonto("759", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        for (Iterator<Transakcja> it = pobranetransakcje.iterator(); it.hasNext();) {
            Transakcja p = it.next();
            naniesPojedynczaTransakcje(idporzadkowy, nd, p, tabelanbpDAO, kontoRozniceKursowe, przychodyfinansowe, kosztyfinansowe);
        }
    }

    public static void naniesPojedynczaTransakcje(int idporzadkowy, Dokfk nd, Transakcja p, TabelanbpDAO tabelanbpDAO, Konto kontoRozniceKursowe, Konto przychodyfinansowe, Konto kosztyfinansowe) {
        Wiersz w = new Wiersz(idporzadkowy++, 0);
        uzupelnijwiersz(w, nd, tabelanbpDAO);
        w.setDataWalutyWiersza(p.getDatarozrachunku().split("-")[2]);
        String rozliczajacy = p.getRozliczajacy().getWiersz().getDokfk().getSeriadokfk() + "/" + p.getRozliczajacy().getWiersz().getDokfk().getNrkolejnywserii();
        String dok = p.getNowaTransakcja().getWiersz() == null ? "BO: " + p.getNowaTransakcja().getOpisBO() : p.getNowaTransakcja().getWiersz().getDokfk().getSeriadokfk() + "/" + p.getNowaTransakcja().getWiersz().getDokfk().getNrkolejnywserii();
        String opiswiersza = "księg. różnic kurs: " + dok + " & " + rozliczajacy + " w." + p.getNowaTransakcja().getWiersz().getIdporzadkowy() + "/w." + p.getRozliczajacy().getWiersz().getIdporzadkowy();
        w.setOpisWiersza(opiswiersza);
        String walutarachunku = p.getNowaTransakcja().getSymbolWalutBOiSW();
        String walutaplatnosci = p.getRozliczajacy().getSymbolWalutBOiSW();
        boolean sazlotowki = walutarachunku.equals("PLN") || walutaplatnosci.equals("PLN") ? true : false;
        double roznicakursowa = Math.abs(p.getRoznicekursowe());
        if (p.getNowaTransakcja().getWnma().equals("Wn")) {
            if (p.getRoznicekursowe() < 0) {
                nanieswierszeDokRRK(roznicakursowa, w, p, kontoRozniceKursowe, sazlotowki, kosztyfinansowe);
            } else {
                nanieswierszeDokRRK(roznicakursowa, w, p, kontoRozniceKursowe, sazlotowki, przychodyfinansowe);
            }

        } else if (p.getRoznicekursowe() > 0) {
            nanieswierszeDokRRK(roznicakursowa, w, p, kontoRozniceKursowe, sazlotowki, kosztyfinansowe);
        } else {
            nanieswierszeDokRRK(roznicakursowa, w, p, kontoRozniceKursowe, sazlotowki, przychodyfinansowe);
        }
        w.setStronanowatransakcja(p.getNowaTransakcja());
        w.setStronarozliczajacy(p.getRozliczajacy());
        nd.getListawierszy().add(w);
    }

    private static void nanieswierszeDokRRK(double roznicakursowa, Wiersz w, Transakcja p, Konto kontoRozniceKursowe, boolean sazlotowki, Konto k) {
        if (k.getPelnynumer().equals("756")) {
            w.setStronaWn(new StronaWiersza(w, "Wn", roznicakursowa, p.getNowaTransakcja().getKonto()));
            w.setStronaMa(new StronaWiersza(w, "Ma", roznicakursowa, kontoRozniceKursowe));
            if (sazlotowki) {
                w.getStronaMa().setKonto(k);
            }
            w.getStronaWn().setKwotaPLN(roznicakursowa);
            w.getStronaMa().setKwotaPLN(roznicakursowa);
        } else if (k.getPelnynumer().equals("759")) {
            w.setStronaWn(new StronaWiersza(w, "Wn", roznicakursowa, kontoRozniceKursowe));
            w.setStronaMa(new StronaWiersza(w, "Ma", roznicakursowa, p.getNowaTransakcja().getKonto()));
            if (sazlotowki) {
                w.getStronaWn().setKonto(k);
            }
            w.getStronaWn().setKwotaPLN(roznicakursowa);
            w.getStronaMa().setKwotaPLN(roznicakursowa);
        }
    }

    private static void ustawwierszeAMO(Dokfk nd, List pobranetransakcje, WpisView wpisView, KontoDAOfk kontoDAOfk, TabelanbpDAO tabelanbpDAO) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        int idporzadkowy = 1;
        for (Iterator<UmorzenieN> it = pobranetransakcje.iterator(); it.hasNext();) {
            UmorzenieN p = it.next();
            Wiersz w = new Wiersz(idporzadkowy++, 0);
            uzupelnijwiersz(w, nd, tabelanbpDAO);
            String opiswiersza = "umorzenie: " + p.getSrodekTrw().getNazwa() + " " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt();
            w.setOpisWiersza(opiswiersza);
            Konto umorzeniesrodkitrwale = kontoDAOfk.findKonto("401-1-1", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
            Konto kontosrodka = kontoDAOfk.findKonto(p.getKontoumorzenie(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
            double kwota = p.getKwota();
            StronaWiersza stronaumorzenie = new StronaWiersza(w, "Wn", kwota, umorzeniesrodkitrwale);
            StronaWiersza stronasrodka = new StronaWiersza(w, "Ma", kwota, kontosrodka);
            w.setStronaWn(stronaumorzenie);
            w.setStronaMa(stronasrodka);
            w.getStronaWn().setKwotaPLN(kwota);
            w.getStronaMa().setKwotaPLN(kwota);
            nd.getListawierszy().add(w);
        }
    }
    
    private static void ustawwierszeSkladki(Dokfk nd, List lista, WpisView wpisView, Konto kontoWn, Konto kontoMa, KontoDAOfk kontoDAOfk, TabelanbpDAO tabelanbpDAO, String opisdok) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        int idporzadkowy = 1;
        for (Iterator<StowNaliczenie> it = lista.iterator(); it.hasNext();) {
            StowNaliczenie p = it.next();
            double kwota = p.getKwota();
            if (kwota != 0.0) {
                Wiersz w = new Wiersz(idporzadkowy++, 0);
                uzupelnijwiersz(w, nd, tabelanbpDAO);
                String opiswiersza = ((MiejscePrzychodow) p.getMiejsce()).getOpismiejsca() + " "+opisdok+" " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt();
                w.setOpisWiersza(opiswiersza);
                //dzieki temu mozna po jednej stronie dac analityke, a po drugiej konto zbiorcze.
                Konto analitykaWn = kontoWn;
                if (kontoWn.getIdslownika() == 7) {
                    analitykaWn = znajdzanalityke(kontoWn, (MiejscePrzychodow) p.getMiejsce(), kontoDAOfk, wpisView);
                }
                Konto analitykaMa = kontoMa;
                if (kontoMa.getIdslownika() == 7) {
                    analitykaMa = znajdzanalityke(kontoMa, (MiejscePrzychodow) p.getMiejsce(), kontoDAOfk, wpisView);
                }
                StronaWiersza stronaWn = new StronaWiersza(w, "Wn", kwota, analitykaWn);
                StronaWiersza stronaMa = new StronaWiersza(w, "Ma", kwota, analitykaMa);
                w.setStronaWn(stronaWn);
                w.setStronaMa(stronaMa);
                w.getStronaWn().setKwotaPLN(kwota);
                w.getStronaMa().setKwotaPLN(kwota);
                nd.getListawierszy().add(w);
            }
        }
    }
    
    private static Konto znajdzanalityke(Konto konto, MiejscePrzychodow miejscePrzychodow, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        String nrkonta = miejscePrzychodow.getNrkonta();
        return kontoDAOfk.findBySlownikoweMacierzyste(konto,nrkonta, wpisView);
    }
    
    private static void ustawwierszePK(Dokfk nowydok, List stronywiersza, List sumy, WpisView wpisView, KontoDAOfk kontoDAOfk, Cechazapisu nkup) {
        nowydok.setListawierszy(new ArrayList<Wiersz>());
        int idporzadkowy = 1;
        StronaWiersza sw = (StronaWiersza) stronywiersza.get(0);
        Konto pko = kontoDAOfk.findKonto("764", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        Konto ppo = kontoDAOfk.findKonto("763", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        Konto kontodorozliczenia = sw.getKonto();
        for (Object z : sumy) {
            ListaSum wierszsum = (ListaSum) z;
            if (wierszsum.getSaldoWn() > 0.0 || wierszsum.getSaldoMa() > 0.0) {
                Wiersz w = new Wiersz(idporzadkowy++, 0);
                Tabelanbp tabela = wierszsum.getTabelanbp() != null ? wierszsum.getTabelanbp() : nowydok.getTabelanbp();
                uzupelnijwierszWaluta(w, nowydok, tabela);
                String opiswiersza = "automatyczna korekta salda: " + sw.getKonto().getPelnynumer() + " na koniec " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt()+" dla waluty "+wierszsum.getWaluta();
                w.setOpisWiersza(opiswiersza);
                double kwotaWal = pobierzkwotezsumyWal(wierszsum);
                double kwotaPLN = pobierzkwotezsumyPLN(wierszsum);
                if (wierszsum.getSaldoWn() > 0.0) {
                    StronaWiersza strWn = new StronaWiersza(w, "Wn", kwotaWal, pko);
                    strWn.getCechazapisuLista().add(nkup);
                    StronaWiersza strMa = new StronaWiersza(w, "Ma", kwotaWal, kontodorozliczenia);
                    if (wierszsum.getTabelanbp()==null && wierszsum.getWalutabo()!=null) {
                        strWn.setSymbolWalutyBO(wierszsum.getWalutabo().getSymbolwaluty());
                        strMa.setSymbolWalutyBO(wierszsum.getWalutabo().getSymbolwaluty());
                    }
                    w.setStronaWn(strWn);
                    w.setStronaMa(strMa);
                } else if (wierszsum.getSaldoMa() > 0.0){
                    StronaWiersza strWn = new StronaWiersza(w, "Wn", kwotaWal, kontodorozliczenia);
                    StronaWiersza strMa = new StronaWiersza(w, "Ma", kwotaWal, ppo);
                    if (wierszsum.getTabelanbp()==null && wierszsum.getWalutabo()!=null) {
                        strWn.setSymbolWalutyBO(wierszsum.getWalutabo().getSymbolwaluty());
                        strMa.setSymbolWalutyBO(wierszsum.getWalutabo().getSymbolwaluty());
                    }
                    w.setStronaWn(strWn);
                    w.setStronaMa(strMa);
                }
                w.getStronaWn().setKwotaPLN(kwotaPLN);
                w.getStronaMa().setKwotaPLN(kwotaPLN);
                nowydok.getListawierszy().add(w);
            }
        }
    }
    
    private static void ustawwierszePKSaldo(Dokfk nowydok, List stronywiersza, double[] roznicawnroznicama, WpisView wpisView, KontoDAOfk kontoDAOfk, TabelanbpDAO tabelanbpDAO) {
        nowydok.setListawierszy(new ArrayList<Wiersz>());
        int idporzadkowy = 1;
        StronaWiersza sw = (StronaWiersza) stronywiersza.get(0);
        Konto pko = kontoDAOfk.findKonto("764", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        Konto ppo = kontoDAOfk.findKonto("763", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        Konto kontodorozliczenia = sw.getKonto();
        double roznicawn = roznicawnroznicama[0];
        double roznicama = roznicawnroznicama[1];
            Wiersz w = new Wiersz(idporzadkowy++, 0);
            uzupelnijwierszWaluta(w, nowydok, tabelanbpDAO.findByTabelaPLN());
            String opiswiersza = "różnice kursowe na środkach własnych: " + sw.getKonto().getPelnynumer() + " na koniec " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt();
            w.setOpisWiersza(opiswiersza);
            if (roznicawn > 0.0) {
                StronaWiersza strWn = new StronaWiersza(w, "Wn", roznicawn, pko);
                StronaWiersza strMa = new StronaWiersza(w, "Ma", roznicawn, kontodorozliczenia);
                strWn.setKwotaPLN(roznicawn);
                strMa.setKwotaPLN(roznicawn);
                w.setStronaWn(strWn);
                w.setStronaMa(strMa);
            } else {
                StronaWiersza strWn = new StronaWiersza(w, "Wn", roznicama, kontodorozliczenia);
                StronaWiersza strMa = new StronaWiersza(w, "Ma", roznicama, ppo);
                strWn.setKwotaPLN(roznicama);
                strMa.setKwotaPLN(roznicama);
                w.setStronaWn(strWn);
                w.setStronaMa(strMa);
            }
            nowydok.getListawierszy().add(w);
    }
    
    
    private static double pobierzkwotezsumyWal(ListaSum wierszsum) {
        double zwrot = 0.0;
        if (wierszsum.getSaldoWn() > 0) {
            zwrot = wierszsum.getSaldoWn();
        } else {
            zwrot = wierszsum.getSaldoMa();
        }
        return zwrot;
    }
    
    private static double pobierzkwotezsumyPLN(ListaSum wierszsum) {
        double zwrot = 0.0;
        String wal = wierszsum.getWaluta();
        if (wierszsum.getSaldoWn() > 0) {
            zwrot = wierszsum.getSaldoWnPLN() != 0.0 ? wierszsum.getSaldoWnPLN() : wal.equals("PLN") ? wierszsum.getSaldoWn() : 0.0;
        } else {
            zwrot = wierszsum.getSaldoMaPLN() != 0.0 ? wierszsum.getSaldoMaPLN() : wal.equals("PLN") ? wierszsum.getSaldoMa() : 0.0;
        }
        
        return zwrot;
    }

    private static void uzupelnijwiersz(Wiersz w, Dokfk nd, TabelanbpDAO tabelanbpDAO) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        w.setTabelanbp(t);
        w.setDokfk(nd);
        w.setLpmacierzystego(0);
        w.setTabelanbp(w.getTabelanbp());
        w.setDataksiegowania(nd.getDatawplywu());
    }
    
    private static void uzupelnijwierszWaluta(Wiersz w, Dokfk nd, Tabelanbp tabela) {
        w.setTabelanbp(tabela);
        w.setDokfk(nd);
        w.setLpmacierzystego(0);
        w.setTabelanbp(w.getTabelanbp());
        w.setDataksiegowania(nd.getDatawplywu());
    }

    public static String zwieksznumerojeden(String nrdokumentu) {
        String[] pozycje = nrdokumentu.split("/");
        StringBuilder sb = new StringBuilder();
        if (pozycje != null) {
            Integer nowynumer = Integer.parseInt(pozycje[0]);
            nowynumer += 1;
            sb.append(String.valueOf(nowynumer));
            sb.append("/");
            sb.append(pozycje[1]);
            sb.append("/");
            sb.append(pozycje[2]);
            sb.append("/");
            sb.append(pozycje[3]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String nowy = zwieksznumerojeden("1/08/2015/AMO");
    }

    

    

}
    
