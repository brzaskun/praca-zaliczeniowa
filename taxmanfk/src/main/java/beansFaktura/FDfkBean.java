/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFaktura;

import beansFK.DFKWiersze;
import dao.DokDAOfk;
import dao.EvewidencjaDAO;
import dao.KliencifkDAO;
import dao.KontoDAOfk;
import dao.RodzajedokDAO;
import dao.TabelanbpDAO;
import dao.WalutyDAOfk;
import embeddable.EVatwpis;
import entity.Evewidencja;
import entity.Faktura;
import entity.Klienci;
import entity.Podatnik;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.Kliencifk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersz;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import msg.Msg;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */

public class FDfkBean {
    
    public static int oblicznumerkolejny(String rodzajdok, DokDAOfk dokDAOfk, Podatnik podatnik, String rokwpisu) {
        Dokfk poprzednidokumentvat = dokDAOfk.findDokfkLastofaType(podatnik, rodzajdok, rokwpisu);
        return poprzednidokumentvat == null ? 1 : poprzednidokumentvat.getNrkolejnywserii() + 1;
    }
    
    public static Dokfk stworznowydokument(int numerkolejny, Faktura faktura, String rodzajdok, Podatnik podatnik, Klienci kontrahent,  WpisView wpisView, RodzajedokDAO rodzajedokDAO,
        TabelanbpDAO tabelanbpDAO, WalutyDAOfk walutyDAOfk, KontoDAOfk kontoDAOfk, KliencifkDAO kliencifkDAO, EvewidencjaDAO evewidencjaDAO, int podatnik0kontrahent, Dokfk poprzedni) {
        Dokfk nd = new Dokfk();
        nd.setNrkolejnywserii(numerkolejny);
        nd.setRok(wpisView.getRokWpisuSt());
        ustawdaty(nd, faktura, wpisView);
        ustawkontrahenta(nd,kontrahent);
        ustawnumerwlasny(nd, faktura);
        ustawopisfaktury(nd, faktura);
        nd.setPodatnikObj(podatnik);
        nd.setWprowadzil(wpisView.getUzer().getLogin());
        ustawrodzajedok(nd, rodzajdok, rodzajedokDAO, wpisView, podatnik);
        ustawtabelenbp(nd,tabelanbpDAO, walutyDAOfk);
        podepnijEwidencjeVat(nd, faktura, evewidencjaDAO, podatnik0kontrahent);
        ustawwiersze(nd, faktura, kontoDAOfk, wpisView, tabelanbpDAO,kliencifkDAO, podatnik0kontrahent, poprzedni);
        nd.przeliczKwotyWierszaDoSumyDokumentu();
        nd.setImportowany(true);
        return nd;
    }
    
    public static Dokfk stworznowydokumentNieVAT(int numerkolejny, Faktura faktura, String rodzajdok, Podatnik podatnik, Klienci kontrahent,  WpisView wpisView, RodzajedokDAO rodzajedokDAO,
        TabelanbpDAO tabelanbpDAO, WalutyDAOfk walutyDAOfk, KontoDAOfk kontoDAOfk, KliencifkDAO kliencifkDAO, EvewidencjaDAO evewidencjaDAO, int podatnik0kontrahent, Dokfk poprzedni) {
        Dokfk nd = new Dokfk();
        nd.setNrkolejnywserii(numerkolejny);
        nd.setRok(wpisView.getRokWpisuSt());
        ustawdaty(nd, faktura, wpisView);
        ustawkontrahenta(nd,kontrahent);
        ustawnumerwlasny(nd, faktura);
        ustawopisfaktury(nd, faktura);
        nd.setPodatnikObj(podatnik);
        nd.setWprowadzil(wpisView.getUzer().getLogin());
        ustawrodzajedok(nd, rodzajdok, rodzajedokDAO, wpisView, podatnik);
        ustawtabelenbp(nd,tabelanbpDAO, walutyDAOfk);
        nd.setEwidencjaVAT(null);
        ustawwiersze(nd, faktura, kontoDAOfk, wpisView, tabelanbpDAO,kliencifkDAO, podatnik0kontrahent, poprzedni);
        nd.przeliczKwotyWierszaDoSumyDokumentu();
        nd.setImportowany(true);
        return nd;
    }
    
    private static void ustawdaty(Dokfk nd, Faktura faktura, WpisView wpisView) {
        String datadokumentu = faktura.getDatawystawienia();
        String datasprzedazy = faktura.getDatasprzedazy();
        nd.setDatadokumentu(datadokumentu);
        nd.setDataoperacji(datasprzedazy);
        nd.setDatawplywu(datadokumentu);
        nd.setDataujecia(new Date());
        nd.setDatawystawienia(datadokumentu);
        nd.setMiesiac(datadokumentu.split("-")[1]);
        if (faktura.getNettopk() != 0.0 || faktura.getVatpk() != 0.0) {
            nd.setVatM(datadokumentu.split("-")[1]);
            nd.setVatR(datadokumentu.split("-")[0]);
        } else {
            nd.setVatM(datasprzedazy.split("-")[1]);
            nd.setVatR(datasprzedazy.split("-")[0]);
        }
    }
    
     private static void ustawkontrahenta(Dokfk nd, Klienci kontrahent) {
            nd.setKontr(kontrahent);
    }
     
    private static void ustawnumerwlasny(Dokfk nd, Faktura faktura) {
        String numer = faktura.getNumerkolejny();
        nd.setNumerwlasnydokfk(numer);
    }
    
    private static void ustawopisfaktury(Dokfk nd, Faktura faktura) {
        if (faktura.getNazwa() != null && !faktura.getNazwa().equals("")) {
            nd.setOpisdokfk(faktura.getNazwa());
        } else {
            nd.setOpisdokfk(faktura.getPozycjenafakturze().get(0).getNazwa());
        }
    }
    
    private static void ustawrodzajedok(Dokfk nd, String rodzajdok, RodzajedokDAO rodzajedokDAO, WpisView wpisView,  Podatnik podatnik) {
        Rodzajedok rodzajedok = rodzajedokDAO.find(rodzajdok, podatnik, wpisView.getRokWpisuSt());
        if (rodzajedok != null) {
            nd.setSeriadokfk(rodzajedok.getSkrot());
            nd.setRodzajedok(rodzajedok);
            nd.setOznaczenie1(rodzajedok.getOznaczenie1());
            nd.setOznaczenie2(rodzajedok.getOznaczenie2());
        } else {
            Msg.msg("e", "Brak zdefiniowanego dokumentu "+rodzajdok);
        }
    }
    
    private static void ustawtabelenbp(Dokfk nd, TabelanbpDAO tabelanbpDAO, WalutyDAOfk walutyDAOfk) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        nd.setTabelanbp(t);
        Waluty w = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        nd.setWalutadokumentu(w);
    }
    
    private static void podepnijEwidencjeVat(Dokfk nd, Faktura faktura, EvewidencjaDAO evewidencjaDAO, int podatnik0kontrahent) {
        if (nd.getRodzajedok().getKategoriadokumentu() != 0 && nd.getRodzajedok().getKategoriadokumentu() != 5) {
            if (nd.iswTrakcieEdycji() == false) {
                List<EVatwpisFK> ewidencjaTransformowana = Collections.synchronizedList(new ArrayList<>());
                for (EVatwpis r : faktura.getEwidencjavat()) {
                    Evewidencja odnalezionaewidencja = evewidencjaDAO.znajdzponazwie(r.getEwidencja().getNazwa());
                    if (podatnik0kontrahent==1) {
                        odnalezionaewidencja = evewidencjaDAO.znajdzponazwie("zakup");
                    }
                    if (faktura.getEwidencjavatpk() != null) {
                        EVatwpis s  = null;
                        for (EVatwpis t : faktura.getEwidencjavatpk()) {
                            if (t.getEwidencja().equals(r.getEwidencja())) {
                                s = t;
                            }
                        }
                        if (s != null) {
                            EVatwpisFK eVatwpisFK = new EVatwpisFK(odnalezionaewidencja, s.getNetto()-r.getNetto(), s.getVat()-r.getVat(), r.getEstawka());
                            eVatwpisFK.setDokfk(nd);
                            ewidencjaTransformowana.add(eVatwpisFK);
                        } else {
                            EVatwpisFK eVatwpisFK = new EVatwpisFK(odnalezionaewidencja, -r.getNetto(), -r.getVat(), r.getEstawka());
                            eVatwpisFK.setDokfk(nd);
                            ewidencjaTransformowana.add(eVatwpisFK);
                        }
                    } else {
                        EVatwpisFK eVatwpisFK = new EVatwpisFK(odnalezionaewidencja, r.getNetto(), r.getVat(), r.getEstawka());
                        eVatwpisFK.setDokfk(nd);
                        ewidencjaTransformowana.add(eVatwpisFK);
                    }
                }
                nd.setEwidencjaVAT(ewidencjaTransformowana);
                } else {
                    Msg.msg("e", "Brak podstawowych ustawień dla podatnika dotyczących opodatkowania. Nie można wpisywać dokumentów! podepnijEwidencjeVat()");
                }
            }
    }
    
//    private static Evewidencja znajdziewidencje(Evewidencja ewidencja, EvewidencjaDAO evewidencjaDAO) {
//        Evewidencja zwrot = ewidencja;
//        if (ewidencja.getId()==0) {
//            zwrot = evewidencjaDAO.znajdzponazwie(ewidencja.getNazwa());
//        }
//        return zwrot;
//    }

    private static void ustawwiersze(Dokfk nd, Faktura faktura, KontoDAOfk kontoDAOfk, WpisView wpisView, TabelanbpDAO tabelanbpDAO, KliencifkDAO kliencifkDAO, int podatnik0kontrahent, Dokfk poprzedni) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        if (podatnik0kontrahent==0) {
            nd.getListawierszy().add(przygotujwierszNettoPrzychod(faktura, nd, kontoDAOfk, wpisView, tabelanbpDAO, kliencifkDAO));
            if (faktura.getVat() != 0) {
                nd.getListawierszy().add(przygotujwierszVatPrzychod(faktura, nd, kontoDAOfk, wpisView, tabelanbpDAO));
            }
        } else {
            nd.getListawierszy().add(przygotujwierszNettoKoszt(faktura, nd, kontoDAOfk, wpisView, tabelanbpDAO, kliencifkDAO, poprzedni));
            if (faktura.getVat() != 0) {
                nd.getListawierszy().add(przygotujwierszVatKoszt(faktura, nd, kontoDAOfk, wpisView, tabelanbpDAO, poprzedni));
            }
        }
    }
    
    
    private static Wiersz przygotujwierszNettoPrzychod(Faktura faktura, Dokfk nd, KontoDAOfk kontoDAOfk, WpisView wpisView, TabelanbpDAO tabelanbpDAO, KliencifkDAO kliencifkDAO){
        Wiersz w = new Wiersz(1, nd, 0);
        uzupelnijwiersz(w, nd, tabelanbpDAO);
        String opiswiersza = nd.getOpisdokfk(); 
        w.setOpisWiersza(opiswiersza);
        w.setLpmacierzystego(0);
        double netto = 0;
        double vat = 0;
        if (faktura.getPozycjepokorekcie() != null) {
            netto = faktura.getNettopk()-faktura.getNetto();
            vat = faktura.getVatpk()-faktura.getVat();
        } else {
            netto = faktura.getNetto();
            vat = faktura.getVat();
        }
        StronaWiersza strwn = new StronaWiersza(w, "Wn", Z.z(netto+vat), null);
        StronaWiersza strma = new StronaWiersza(w, "Ma", Z.z(netto), null);
        Konto kontonetto = kontoDAOfk.findKonto("702-2", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        try {
            Kliencifk kliencifk = kliencifkDAO.znajdzkontofk(nd.getKontr().getNip(), wpisView.getPodatnikObiekt());
            String numerkonta = "201-2-"+kliencifk.getNrkonta();
            Konto kontorozrach = kontoDAOfk.findKonto(numerkonta, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
            strwn.setKonto(kontorozrach);
        } catch (Exception e) {
        }
        strwn.setKwotaPLN(Z.z(netto+vat));
        strma.setKwotaPLN(Z.z(netto));
        strma.setKonto(kontonetto);
        w.setStronaWn(strwn);
        w.setStronaMa(strma);
        DFKWiersze.zaznaczNowaTrasakcja(w, "Wn");
        return w;
    }
    
    private static Wiersz przygotujwierszVatPrzychod(Faktura faktura, Dokfk nd,  KontoDAOfk kontoDAOfk, WpisView wpisView, TabelanbpDAO tabelanbpDAO) {
        Wiersz w = new Wiersz(2, nd, 2);
        uzupelnijwiersz(w, nd, tabelanbpDAO);
        String opiswiersza = nd.getOpisdokfk()+"- podatek vat"; 
        w.setOpisWiersza(opiswiersza);
        w.setLpmacierzystego(1);
        double vat = 0;
        if (faktura.getPozycjepokorekcie() != null) {
            vat = faktura.getVatpk()-faktura.getVat();
        } else {
            vat = faktura.getVat();
        }
        StronaWiersza strma = new StronaWiersza(w, "Ma", Z.z(vat), null);
        strma.setKwotaPLN(Z.z(vat));
        Konto kontovat = kontoDAOfk.findKonto("221-1", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        strma.setKonto(kontovat);
        w.setStronaMa(strma);
        return w;
    }
    
    private static Wiersz przygotujwierszNettoKoszt(Faktura faktura, Dokfk nd, KontoDAOfk kontoDAOfk, WpisView wpisView, TabelanbpDAO tabelanbpDAO, KliencifkDAO kliencifkDAO, Dokfk poprzedni){
        Wiersz w = new Wiersz(1, nd, 0);
        uzupelnijwiersz(w, nd, tabelanbpDAO);
        String opiswiersza = nd.getOpisdokfk(); 
        w.setOpisWiersza(opiswiersza);
        w.setLpmacierzystego(0);
        double netto = 0;
        double vat = 0;
        if (faktura.getPozycjepokorekcie() != null) {
            netto = faktura.getNettopk()-faktura.getNetto();
            vat = faktura.getVatpk()-faktura.getVat();
        } else {
            netto = faktura.getNetto();
            vat = faktura.getVat();
        }
        StronaWiersza strma = new StronaWiersza(w, "Ma", Z.z(netto+vat), null);
        StronaWiersza strwn = new StronaWiersza(w, "Wn", Z.z(netto), null);
        Konto kontonetto = poprzedni!=null? poprzedni.getListawierszy().get(0).getKontoWn() : null;
        Konto kontobrutto = poprzedni!=null? poprzedni.getListawierszy().get(0).getKontoMa() : null;
        strma.setKonto(kontobrutto);
        strma.setKwotaPLN(Z.z(netto+vat));
        strwn.setKwotaPLN(Z.z(netto));
        strwn.setKonto(kontonetto);
        w.setStronaWn(strwn);
        w.setStronaMa(strma);
        DFKWiersze.zaznaczNowaTrasakcja(w, "Ma");
        return w;
    }
    
    private static Wiersz przygotujwierszVatKoszt(Faktura faktura, Dokfk nd,  KontoDAOfk kontoDAOfk, WpisView wpisView, TabelanbpDAO tabelanbpDAO, Dokfk poprzedni) {
        Wiersz w = new Wiersz(2, nd, 1);
        uzupelnijwiersz(w, nd, tabelanbpDAO);
        String opiswiersza = nd.getOpisdokfk()+"- podatek vat"; 
        w.setOpisWiersza(opiswiersza);
        w.setLpmacierzystego(1);
        double vat = 0;
        if (faktura.getPozycjepokorekcie() != null) {
            vat = faktura.getVatpk()-faktura.getVat();
        } else {
            vat = faktura.getVat();
        }
        StronaWiersza strwn = new StronaWiersza(w, "Wn", Z.z(vat), null);
        strwn.setKwotaPLN(Z.z(vat));
        Konto kontovat = poprzedni!=null? poprzedni.getListawierszy().get(1).getKontoWn() : null;
        strwn.setKonto(kontovat);
        w.setStronaWn(strwn);
        return w;
    }
    
    private static void uzupelnijwiersz(Wiersz w, Dokfk nd, TabelanbpDAO tabelanbpDAO) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        w.setTabelanbp(t);
        w.setDokfk(nd);
        w.setTabelanbp(w.getTabelanbp());
        w.setDataksiegowania(nd.getDatawplywu());
    }
}
