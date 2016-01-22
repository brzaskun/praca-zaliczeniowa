/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import dao.KlienciDAO;
import dao.RodzajedokDAO;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import data.Data;
import embeddable.Umorzenie;
import entity.Klienci;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Transakcja;
import entityfk.Waluty;
import entityfk.Wiersz;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named

public class DokumentFKBean implements Serializable {
    
    
    public static Dokfk generujdokument(WpisView wpisView, KlienciDAO klienciDAO, String symbokdok, String opisdok, RodzajedokDAO rodzajedokDAO, TabelanbpDAO tabelanbpDAO, KontoDAOfk kontoDAOfk,List wiersze, DokDAOfk dokDAOfk) {
        Dokfk nowydok = stworznowydokument(wpisView, klienciDAO, symbokdok, opisdok, rodzajedokDAO, tabelanbpDAO, dokDAOfk);
        switch (symbokdok) {
            case "RRK": 
                ustawwierszeRKK(nowydok, wiersze, wpisView, kontoDAOfk, tabelanbpDAO);
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
    
    private static Dokfk stworznowydokument(WpisView wpisView, KlienciDAO klienciDAO, String symbokdok, String opisdok, RodzajedokDAO rodzajedokDAO, TabelanbpDAO tabelanbpDAO, DokDAOfk dokDAOfk) {
        int numerkolejny = oblicznumerkolejny(dokDAOfk, wpisView, symbokdok);
        Dokfk nd = new Dokfk(symbokdok, numerkolejny, wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        ustawdaty(nd, wpisView);
        ustawkontrahenta(nd, klienciDAO, wpisView);
        ustawnumerwlasny(nd, wpisView, symbokdok);
        ustawopis(nd, wpisView, opisdok);
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        ustawrodzajedok(nd, symbokdok, rodzajedokDAO, wpisView);
        ustawtabelenbp(nd, tabelanbpDAO);
        return nd;
    }
    
    private static int oblicznumerkolejny(DokDAOfk dokDAOfk, WpisView wpisView, String symbokdok) {
        Dokfk poprzednidokumentvat = dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikObiekt(), symbokdok, wpisView.getRokWpisuSt());
        return poprzednidokumentvat == null ? 1 : poprzednidokumentvat.getDokfkPK().getNrkolejnywserii() + 1;
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
            nd.setRodzajedok(rodzajedok);
        } else {
            Msg.msg("e", "Brak zdefiniowanego dokumentu "+symbokdok);
        }
    }
     
    private static void ustawtabelenbp(Dokfk nd, TabelanbpDAO tabelanbpDAO) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        nd.setTabelanbp(t);
        nd.setWalutadokumentu(t.getWaluta());
    }
    
    private static void ustawwierszeRKK(Dokfk nd, List pobranetransakcje, WpisView wpisView, KontoDAOfk kontoDAOfk, TabelanbpDAO tabelanbpDAO) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        int idporzadkowy = 1;
        for (Iterator<Transakcja> it = pobranetransakcje.iterator(); it.hasNext();) {
            Transakcja p = it.next();
            Wiersz w = new Wiersz(idporzadkowy++, 0);
            uzupelnijwiersz(w, nd, tabelanbpDAO);
            String rozliczajacy = p.getRozliczajacy().getWiersz().getDokfk().getDokfkPK().getSeriadokfk()+"/"+p.getRozliczajacy().getWiersz().getDokfk().getDokfkPK().getNrkolejnywserii();
            String dok = p.getNowaTransakcja().getWiersz() == null ? "BO: "+p.getNowaTransakcja().getOpisBO() : p.getNowaTransakcja().getWiersz().getDokfk().getDokfkPK().getSeriadokfk()+"/"+p.getNowaTransakcja().getWiersz().getDokfk().getDokfkPK().getNrkolejnywserii(); 
            String opiswiersza = "księg. różnic kurs: "+dok+" & "+rozliczajacy+" "+p.getNowaTransakcja().getId()+"/"+p.getRozliczajacy().getId(); 
            w.setOpisWiersza(opiswiersza);
            Konto kontoRozniceKursowe = kontoDAOfk.findKonto("755", wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
            Konto przychodyfinansowe = kontoDAOfk.findKonto("756", wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
            Konto kosztyfinansowe = kontoDAOfk.findKonto("759", wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
            String walutarachunku = p.getNowaTransakcja().getSymbolWalut();
            String walutaplatnosci = p.getRozliczajacy().getSymbolWalut();
            boolean sazlotowki = walutarachunku.equals("PLN") || walutaplatnosci.equals("PLN") ? true : false;
            double roznicakursowa = Math.abs(p.getRoznicekursowe());
            if (p.getNowaTransakcja().getWnma().equals("Wn")) {
                if (p.getRoznicekursowe() < 0) {
                    StronaWiersza konto755 = new StronaWiersza(w, "Wn", roznicakursowa, kontoRozniceKursowe);
                    if (sazlotowki) {
                        konto755.setKonto(kosztyfinansowe);
                    }
                    StronaWiersza kontoRozrachunku = new StronaWiersza(w, "Ma", roznicakursowa, p.getNowaTransakcja().getKonto());
                    w.setStronaWn(konto755);
                    w.setStronaMa(kontoRozrachunku);
                    w.getStronaWn().setKwotaPLN(roznicakursowa);
                    w.getStronaMa().setKwotaPLN(roznicakursowa);
                } else {
                    StronaWiersza konto755 = new StronaWiersza(w, "Ma", roznicakursowa, kontoRozniceKursowe);
                    if (sazlotowki) {
                        konto755.setKonto(przychodyfinansowe);
                    }
                    StronaWiersza kontoRozrachunku = new StronaWiersza(w, "Wn", roznicakursowa, p.getNowaTransakcja().getKonto());
                    w.setStronaWn(kontoRozrachunku);
                    w.setStronaMa(konto755);
                    w.getStronaWn().setKwotaPLN(roznicakursowa);
                    w.getStronaMa().setKwotaPLN(roznicakursowa);
                }
                
            } else {
                if (p.getRoznicekursowe() > 0) {
                    StronaWiersza kontoRozrachunku = new StronaWiersza(w, "Ma", roznicakursowa, p.getNowaTransakcja().getKonto());
                    StronaWiersza konto755 = new StronaWiersza(w, "Wn", roznicakursowa, kontoRozniceKursowe);
                    if (sazlotowki) {
                        konto755.setKonto(kosztyfinansowe);
                    }
                    w.setStronaWn(konto755);
                    w.setStronaMa(kontoRozrachunku);
                    w.getStronaWn().setKwotaPLN(roznicakursowa);
                    w.getStronaMa().setKwotaPLN(roznicakursowa);
                } else {
                    StronaWiersza kontoRozrachunku = new StronaWiersza(w, "Wn", roznicakursowa, p.getNowaTransakcja().getKonto());
                    StronaWiersza konto755 = new StronaWiersza(w, "Ma", roznicakursowa, kontoRozniceKursowe);
                    if (sazlotowki) {
                        konto755.setKonto(przychodyfinansowe);
                    }
                    w.setStronaWn(kontoRozrachunku);
                    w.setStronaMa(konto755);
                    w.getStronaWn().setKwotaPLN(roznicakursowa);
                    w.getStronaMa().setKwotaPLN(roznicakursowa);
                }
            }
            nd.getListawierszy().add(w);
        }
    }
    
    private static void ustawwierszeAMO(Dokfk nd, List pobranetransakcje, WpisView wpisView, KontoDAOfk kontoDAOfk, TabelanbpDAO tabelanbpDAO) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        int idporzadkowy = 1;
        for (Iterator<Umorzenie> it = pobranetransakcje.iterator(); it.hasNext();) {
            Umorzenie p = it.next();
            Wiersz w = new Wiersz(idporzadkowy++, 0);
            uzupelnijwiersz(w, nd, tabelanbpDAO);
            String opiswiersza = "umorzenie: "+p.getNazwaSrodka()+" "+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisuSt();
            w.setOpisWiersza(opiswiersza);
            Konto umorzeniesrodkitrwale = kontoDAOfk.findKonto("401-1-1", wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
            Konto kontosrodka = kontoDAOfk.findKonto(p.getKontoumorzenie(), wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
            double kwota =  p.getKwota().doubleValue();
            StronaWiersza stronaumorzenie = new StronaWiersza(w, "Wn", kwota, umorzeniesrodkitrwale);
            StronaWiersza stronasrodka = new StronaWiersza(w, "Ma", kwota, kontosrodka);
            w.setStronaWn(stronaumorzenie);
            w.setStronaMa(stronasrodka);
            w.getStronaWn().setKwotaPLN(kwota);
            w.getStronaMa().setKwotaPLN(kwota);
            nd.getListawierszy().add(w);
        }
    }
    
    private static void uzupelnijwiersz(Wiersz w, Dokfk nd, TabelanbpDAO tabelanbpDAO) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        w.setTabelanbp(t);
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
    
    public static void main (String[] args) {
        String nowy = zwieksznumerojeden("1/08/2015/AMO");
        System.out.println("nowy "+nowy);
    }
}
