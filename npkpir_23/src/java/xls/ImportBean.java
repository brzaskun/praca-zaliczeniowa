    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import beansDok.ListaEwidencjiVat;
import beansFK.PlanKontFKBean;
import beansRegon.SzukajDaneBean;
import comparator.Kliencifkcomparator;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import daoFK.DokDAOfk;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaZapisDAO;
import daoFK.UkladBRDAO;
import embeddablefk.ImportJPKSprzedaz;
import entity.Evewidencja;
import entity.Klienci;
import entity.Podatnik;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.Kliencifk;
import entityfk.Konto;
import error.E;
import gus.GUSView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import msg.Msg;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class ImportBean {
    public static Klienci ustawkontrahenta(String nip, String nazwa, List<Klienci> k, GUSView gUSView, KlienciDAO klienciDAO) {
        Klienci klient = null;
        try {
            for (Klienci p : k) {
                if (p.getNip().contains(nip.trim())) {
                    klient = p;
                    break;
                }
            }
            if (klient==null) {
                for (Klienci p : k) {
                    if (p.getNpelna().contains(nazwa.trim())) {
                        klient = p;
                        break;
                    }
                }
            }
            if (klient==null) {
                klient = znajdzdaneregonAutomat(nip.trim(), klienciDAO, gUSView);
            }
            if (klient!=null && klient.getNpelna()!=null && klient.getNip()!=null) {
                k.add(klient);
            }
        } catch (Exception e) {
            E.e(e);
        }
        if (klient==null) {
            System.out.println("");
        }
        return klient;
    }
    
     public static Klienci znajdzdaneregonAutomat(String nip, KlienciDAO klienciDAO, GUSView gUSView) {
        Klienci zwrot = null;
        try {
            zwrot = SzukajDaneBean.znajdzdaneregonAutomat(nip, gUSView);
            if (zwrot!=null && zwrot.getNip()!=null) {
                klienciDAO.dodaj(zwrot);
                Msg.msg("Zaktualizowano dane klienta pobranymi z GUS");
            }
        } catch (Exception e) {
            Msg.msg("e","Błąd, niezaktualizowano dane klienta pobranymi z GUS");
            E.e(e);
        }
        return zwrot;
    }
     
    public static int oblicznumerkolejny(String rodzajdok, DokDAOfk dokDAOfk, Podatnik podatnik, String rok) {
        Dokfk poprzednidokumentvat = dokDAOfk.findDokfkLastofaType(podatnik, rodzajdok, rok);
        return poprzednidokumentvat == null ? 1 : poprzednidokumentvat.getNrkolejnywserii() + 1;
    }
    
    public static void ustawnumerwlasny(Dokfk nd, String numer) {
        nd.setNumerwlasnydokfk(numer);
    }
    
    public static void ustawrodzajedok(Dokfk nd, String rodzajdok, RodzajedokDAO rodzajedokDAO, Podatnik podatnik, String rok) {
        Rodzajedok rodzajedok = rodzajedokDAO.find(rodzajdok, podatnik, rok);
        if (rodzajedok != null) {
            nd.setSeriadokfk(rodzajedok.getSkrot());
            nd.setRodzajedok(rodzajedok);
        } else {
            Msg.msg("e", "Brak zdefiniowanego dokumentu "+rodzajdok);
        }
    }
    
    public static Konto pobierzkontoWn(Klienci klient, KliencifkDAO kliencifkDAO, WpisView wpisView, KontoDAOfk kontoDAO, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        Konto kontoRozrachunkowe = null;
        Kliencifk klientMaKonto = kliencifkDAO.znajdzkontofk(klient.getNip(), wpisView.getPodatnikObiekt().getNip());
        if (klientMaKonto == null) {
            klientMaKonto = new Kliencifk();
            klientMaKonto.setNazwa(klient.getNpelna());
            klientMaKonto.setNip(klient.getNip());
            klientMaKonto.setPodatniknazwa(wpisView.getPodatnikWpisu());
            klientMaKonto.setPodatniknip(wpisView.getPodatnikObiekt().getNip());
            klientMaKonto.setNrkonta(pobierznastepnynumer(kliencifkDAO, wpisView.getPodatnikObiekt()));
            kliencifkDAO.dodaj(klientMaKonto);
            List<Konto> wykazkont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            PlanKontFKBean.aktualizujslownikKontrahenci(wykazkont, kliencifkDAO, klientMaKonto, kontoDAO, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
            String numerkonta = "201-2-"+klientMaKonto.getNrkonta();
            if (klient.getKrajkod()!=null && !klient.getKrajkod().equals("PL")) {
                numerkonta = "203-2-"+klientMaKonto.getNrkonta();
            }
            kontoRozrachunkowe = kontoDAO.findKonto(numerkonta, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        } else {
            String numerkonta = "201-2-"+klientMaKonto.getNrkonta();
            if (klient.getKrajkod()!=null && !klient.getKrajkod().equals("PL")) {
                numerkonta = "203-2-"+klientMaKonto.getNrkonta();
            }
            kontoRozrachunkowe = kontoDAO.findKonto(numerkonta, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        }
        return kontoRozrachunkowe;
    }
     
     public static Konto pobierzkontoMa(Klienci klient, KliencifkDAO kliencifkDAO, WpisView wpisView, KontoDAOfk kontoDAO, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        Konto kontoRozrachunkowe = null;
        Kliencifk klientMaKonto = kliencifkDAO.znajdzkontofk(klient.getNip(), wpisView.getPodatnikObiekt().getNip());
        if (klientMaKonto == null) {
            klientMaKonto = new Kliencifk();
            klientMaKonto.setNazwa(klient.getNpelna());
            klientMaKonto.setNip(klient.getNip());
            klientMaKonto.setPodatniknazwa(wpisView.getPodatnikWpisu());
            klientMaKonto.setPodatniknip(wpisView.getPodatnikObiekt().getNip());
            klientMaKonto.setNrkonta(pobierznastepnynumer(kliencifkDAO, wpisView.getPodatnikObiekt()));
            kliencifkDAO.dodaj(klientMaKonto);
            List<Konto> wykazkont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            PlanKontFKBean.aktualizujslownikKontrahenci(wykazkont, kliencifkDAO, klientMaKonto, kontoDAO, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
            String numerkonta = "202-2-"+klientMaKonto.getNrkonta();
            if (klient.getKrajkod()!=null &&!klient.getKrajkod().equals("PL")) {
                numerkonta = "204-2-"+klientMaKonto.getNrkonta();
            }
            kontoRozrachunkowe = kontoDAO.findKonto(numerkonta, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        } else {
            String numerkonta = "202-2-"+klientMaKonto.getNrkonta();
            if (klient.getKrajkod()!=null && !klient.getKrajkod().equals("PL")) {
                numerkonta = "204-2-"+klientMaKonto.getNrkonta();
            }
            kontoRozrachunkowe = kontoDAO.findKonto(numerkonta, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        }
        return kontoRozrachunkowe;
    }
     
       private static String pobierznastepnynumer(KliencifkDAO kliencifkDAO, Podatnik podatnik) {
        try {
            List<Kliencifk> przyporzadkowani = kliencifkDAO.znajdzkontofkKlient(podatnik.getNip());
            Collections.sort(przyporzadkowani, new Kliencifkcomparator());
            return String.valueOf(Integer.parseInt(przyporzadkowani.get(przyporzadkowani.size() - 1).getNrkonta()) + 1);
        } catch (Exception e) {
            E.e(e);
            return "1";
        }
    }
       
     public static void podepnijEwidencjeVat(Dokfk nd, double netto, double vat, ListaEwidencjiVat listaEwidencjiVat) {
        if (nd.getRodzajedok().getKategoriadokumentu() != 0 && nd.getRodzajedok().getKategoriadokumentu() != 5) {
            if (nd.iswTrakcieEdycji() == false) {
                nd.setEwidencjaVAT(new ArrayList<EVatwpisFK>());
                /*wyswietlamy ewidencje VAT*/
                List<Evewidencja> opisewidencji = Collections.synchronizedList(new ArrayList<>());
                opisewidencji.addAll(listaEwidencjiVat.pobierzEvewidencje(nd.getRodzajedok().getRodzajtransakcji()));
                int k = 0;
                for (Evewidencja p : opisewidencji) {
                    EVatwpisFK eVatwpisFK = new EVatwpisFK();
                    eVatwpisFK.setLp(k++);
                    eVatwpisFK.setEwidencja(p);
                    przesuniecie(nd, eVatwpisFK);
                    if (Z.z(Z.z(vat)) != 0.0) {
                        if (p.getNazwa().equals("sprzedaż 23%") || p.getNazwa().equals("zakup")) {
                            eVatwpisFK.setNettowwalucie(Z.z(Z.z(netto)));
                            eVatwpisFK.setVatwwalucie(Z.z(Z.z(vat)));
                            eVatwpisFK.setNetto(Z.z(Z.z(netto)));
                            eVatwpisFK.setVat(Z.z(Z.z(vat)));
                            eVatwpisFK.setBrutto(Z.z(Z.z(netto) + Z.z(vat)));
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                            break;
                        }
                    } else {
                        if (nd.getSeriadokfk().equals("WDT") && p.getNazwa().equals("rejestr WDT")) {
                            eVatwpisFK.setNettowwalucie(Z.z(Z.z(netto)));
                            eVatwpisFK.setVatwwalucie(Z.z(Z.z(vat)));
                            eVatwpisFK.setNetto(Z.z(Z.z(netto)));
                            eVatwpisFK.setVat(0.0);
                            eVatwpisFK.setBrutto(Z.z(Z.z(netto)));
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                            break;
                        }
                        if (nd.getSeriadokfk().equals("EXP") && p.getNazwa().equals("eksport towarów")) {
                            eVatwpisFK.setNettowwalucie(Z.z(Z.z(netto)));
                            eVatwpisFK.setVatwwalucie(Z.z(Z.z(vat)));
                            eVatwpisFK.setNetto(Z.z(Z.z(netto)));
                            eVatwpisFK.setVat(0.0);
                            eVatwpisFK.setBrutto(Z.z(Z.z(netto)));
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                            break;
                        }
                    }
                }
            }
        }
    }
     
     private static void przesuniecie(Dokfk nd, EVatwpisFK eVatwpisFK) {
        if (!nd.getMiesiac().equals(nd.getVatM())) {
            int mcdok = Integer.parseInt(nd.getMiesiac());
            int mdotrzymania =  Integer.parseInt(nd.getVatM());
            int innyokres = mdotrzymania-mcdok;
            eVatwpisFK.setInnyokres(innyokres);
        }
    }

   
}
